package prov.CRUD;

import java.awt.image.RescaleOp;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.FloatType;
import org.hibernate.type.StringType;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.Result;

import henry.QuerySimilarity.QueryFromDB;
import henry.QuerySimilarity.VisualizationFromDB;
import henry.algorithm.RecommendationAlgorithm;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import prov.act.DistinctValues;
import prov.act.QueryDecryptionAction;
import prov.chart.VegaChart;
import prov.mybean.*;
import prov.util.HibernateUtil;
import prov.vis.Vega;
import prov.vis.VegaBars;
import prov.vis.VegaVis;

// give more flexibility ion computation
public class VisualRecommendation {

	int id;
	String relevantPair;
	QueryForm userQuery;
	String webVal;
	String webField;
	public Map<String, List<String>> core;
	RecommendationAlgorithm recommender; 
	DBForm db;
	private 	QueryConstructor queryBuilder;
	
	
	public VisualRecommendation(int id, String relevantPair, QueryForm userQuery, String webVal, String webField, DBForm dbms) {
		super();
		this.id = id;
		this.relevantPair = relevantPair;
		this.userQuery = userQuery;
		this.webVal = webVal;
		this.webField = webField;
		this.recommender= new RecommendationAlgorithm();
		this.db=dbms;
		if (this.db.getName().equals("soccerDB")) {
			queryBuilder = new QueryConstructor_Soccer(this.userQuery,this.relevantPair);
		}
		else if (this.db.getName().equals("formulaOne")) {
			queryBuilder = new QueryConstructor_Formula(this.userQuery,this.relevantPair);
		}else {
			queryBuilder= new QueryConstructor(this.userQuery,this.relevantPair);
		}

	}

	
	
	//it generates 4 queries: first 2 queries are used to populate visual recommendation, third is considered for a further exploration, fourth query is the union of query 1na d 2 thath should be stored in the database
	public List<QueryForm> zoomIn() {
		List<QueryForm> result = new ArrayList<QueryForm>();
		QueryConstructor qc = new QueryConstructor(this.userQuery, this.relevantPair);
		QueryForm querybase0 = qc.Formalize();
		QueryForm querybase1 = qc.update(querybase0);
		QueryForm querybase2 = qc.formalizeV22();
		QueryForm querybase11 = qc.ExtendAndAvoidDuplica(querybase1, this.webVal, this.webField);
		QueryForm querybase11other = qc.ExtendAndAvoidDuplica(querybase2, this.webVal, this.webField);

		/**********************
		 * preparation of query that will be used for next step
		 *************************/
	/*	List<String> groupclause2 = new ArrayList<String>(querybase11.getGroupbyAtt());
		groupclause2.remove(webField);
		List<String> selectclause2 = new ArrayList<String>(querybase11.getSelectedAtt());
		selectclause2.remove(webField);
		HashMap<String, String> condsupdate = new HashMap<String, String>(this.userQuery.getCondAtt());
		condsupdate.remove(webField);
		QueryForm NextQueryToPerform = new QueryForm(selectclause2, condsupdate, querybase11.getTables(), groupclause2,
				this.userQuery.getOrderbyAtt(), querybase11.getJoinTables());*/
		
		/**********************
		 * my second form of recommendation
		 *************************/
	
		HashMap<String, String> condsupdate = new HashMap<String, String>(this.userQuery.getCondAtt());
		condsupdate.remove(webField);
		QueryForm NextQueryToPerform = new QueryForm(querybase11other.getSelectedAtt(), condsupdate, querybase11.getTables(), querybase11other.getGroupbyAtt(),
				this.userQuery.getOrderbyAtt(), querybase11.getJoinTables());
		
		
		
		QueryForm NextQueryToStroeInDB = new QueryForm(querybase1.getSelectedAtt(), condsupdate, querybase11.getTables(), querybase1.getGroupbyAtt(),
				this.userQuery.getOrderbyAtt(), querybase11.getJoinTables());
	
				/************************* other queries **********************/


		result.add(querybase11);
		result.add(querybase11other);
		result.add(NextQueryToPerform);
		result.add(NextQueryToStroeInDB);
		return result;

	}

	// it returns string of list, first element is vega template, two other are
	// json result of two queries
	public Map<List<List<DisplayForm>>, List<Vega>> VisZoomIn(List<QueryForm> zoomqueries) throws IllegalArgumentException, IllegalAccessException {

		QueryForm querybase11 = zoomqueries.get(0);
		QueryForm querybase11other = zoomqueries.get(1);

		VegaJsonMapping vega = new VegaJsonMapping();

		List<DisplayForm> jsonobjects = vega.constructDynamicV5(querybase11);

		List<DisplayForm> otherObjects = vega.constructDynamicV4(querybase11other);
		jsonobjects.addAll(otherObjects);


		// Collections.sort(jsonobjects, DisplayForm.COMPARE_BY_axeX);
		Collections.sort(jsonobjects, DisplayForm.COMPARE_BY_axeX2);

		/******************
		 * ??????query to search the details of other values??????
		 ****************/
		QueryForm queryTest = new QueryForm(querybase11.getSelectedAtt(), querybase11other.getCondAtt(),
				querybase11.getTables(), querybase11.getGroupbyAtt(), querybase11.getOrderbyAtt(),
				querybase11.getJoinTables());
		List<DisplayForm> otherDetails = vega.constructDynamicV5(queryTest);
		List<DisplayForm> jsonobjectsDetails = vega.constructDynamicV5(querybase11);
		jsonobjectsDetails.addAll(otherDetails);
		
		
        /**______________Recommendation of visualization________**/
		/******************** voyager principle ********************/
		VisualizationConstruction vegconst = new VisualizationConstruction(querybase11other,db);
	
		List<Vega> visobj = vegconst.decide();

		List<List<DisplayForm>> datasetsTouse = new ArrayList<List<DisplayForm>>(Arrays.asList(jsonobjects, jsonobjectsDetails));
		Map<List<List<DisplayForm>>, List<Vega>> result = new HashMap<List<List<DisplayForm>>, List<Vega>>();
		result.put(datasetsTouse, visobj);
		
		
		return result;
	}

	public String findOld() {
		String oldatt = "";
		//QueryConstructor qc = new QueryConstructor(this.userQuery, this.relevantPair);
	
			//queryBuilder.setQueryBean(userQuery);
			//queryBuilder.setRelevants(relevantPair);
		QueryForm newQuery = queryBuilder/*qc*/.Extend().get(0);
		QueryForm query = new QueryForm(newQuery.getSelectedAtt(), newQuery.getCondAtt(), newQuery.getTables(),
				newQuery.getGroupbyAtt(), newQuery.getOrderbyAtt(), newQuery.getJoinTables());
		QueryForm ExtensionBasis =queryBuilder/*qc*/.ExtendV2(query, webVal, webField);

		oldatt = ExtensionBasis.FindOld(newQuery.getSelectedAtt());
		return oldatt;
	}

	// zoominslice sends 3 queries: first two one are used for visualization,
	// third one is the query that we will use if user wants to go a step
	// further in the data exploration session
	public List<QueryForm> ZoomIn_Slice() {
		List<QueryForm> result = new ArrayList<QueryForm>();

		List<QueryForm> zoomqueries = zoomIn();
		QueryForm querybase11 = zoomqueries.get(0);
		QueryForm querybase11other = zoomqueries.get(1);

		Map<String, String> sliceconds11 = new HashMap<String, String>(querybase11.getCondAtt());
		sliceconds11.putAll(this.userQuery.getCondAtt());
		Map<String, String> sliceconds11other = new HashMap<String, String>(querybase11other.getCondAtt());
		sliceconds11other.putAll(this.userQuery.getCondAtt());

		QueryForm querybase11Slice = new QueryForm(querybase11.getSelectedAtt(), sliceconds11, querybase11.getTables(),
				querybase11.getGroupbyAtt(), this.userQuery.getOrderbyAtt(), querybase11.getJoinTables());

		QueryForm querybase11SliceOther = new QueryForm(/* querybase11 */querybase11other.getSelectedAtt(),
				sliceconds11other, querybase11other.getTables(), /* querybase11 */querybase11other.getGroupbyAtt(),
				this.userQuery.getOrderbyAtt(), querybase11other.getJoinTables());

		/**********************
		 * preparation of query that will be used for next step
		 *************************/
		List<String> groupclause2 = new ArrayList<String>(querybase11.getGroupbyAtt());
		groupclause2.remove(webField);
		List<String> selectclause2 = new ArrayList<String>(querybase11.getSelectedAtt());
		selectclause2.remove(webField);
		HashMap<String, String> condsupdate = new HashMap<String, String>(sliceconds11);
		condsupdate.remove(groupclause2.get(0));
		QueryForm NextQueryToPerform = new QueryForm(selectclause2, condsupdate, querybase11.getTables(), groupclause2,
				this.userQuery.getOrderbyAtt(), querybase11.getJoinTables());

		/***********************************************/

		result.add(querybase11Slice);
			System.out.println("@@@@@@@@>>> " + querybase11Slice.display());
		result.add(querybase11SliceOther);
		System.out.println("@@@@@@@@>>> " + querybase11SliceOther.display());
		result.add(NextQueryToPerform);
	
		return result;

	}

	// it returns list of two results as string and vega template
	public Map<List<List<DisplayForm>>, List<Vega>> VisZoomIn_Slice(List<QueryForm> zoomSlicequeries ) throws IllegalArgumentException, IllegalAccessException {
		Map<List<List<DisplayForm>>, List<Vega>> result = new HashMap<List<List<DisplayForm>>, List<Vega>>();
		
		VegaJsonMapping vega = new VegaJsonMapping();
		QueryForm query1 = zoomSlicequeries .get(0);
		QueryForm query1other = zoomSlicequeries .get(1);
		QueryForm querySliceFusion = zoomSlicequeries .get(2);

		
		/**************
		 * here execute other in different way to provide a logic "original"
		 * dataset used in visualizations later
		 *******************/
		List<DisplayForm> jsonObjs = vega.construct(querySliceFusion);
		String originaldataforaxeConsistency =vega.ConvertToJson_Normal(jsonObjs);
		/**************/

		List<DisplayForm> dataJs0Slice = vega.constructDynamicV2_ZoomSlice(query1, "nothing");
		List<DisplayForm> dataJs00Slice = vega.constructDynamicV2_ZoomSlice(query1other, "other");
		if (!dataJs00Slice.isEmpty()) {
			dataJs0Slice.addAll(dataJs00Slice);
		}
		Collections.sort(dataJs0Slice, DisplayForm.COMPARE_BY_agg);
		String dataJsonUnified = vega.ConvertToJson_Normal(dataJs0Slice);

		/******************
		 * ??????Visualization recommendation??????
		 ****************/
		QueryFromDB q1= DistinctValues.connector.QueryFormToQuery(zoomSlicequeries .get(2));
		List<QueryFromDB> comparisonQuery = this.recommender.Rank(DistinctValues.connector, q1);
	


		VisualizationConstruction vegconst = new VisualizationConstruction(querySliceFusion,db);
		String datatypes = "quantitative-nominal";
		List<Vega> visobj = vegconst.decideStatic(datatypes);

		//List<String> jsdata = new ArrayList<String>(Arrays.asList(dataJsonUnified, originaldataforaxeConsistency));
		List<List<DisplayForm>> jsdata  = new ArrayList<List<DisplayForm>>(Arrays.asList(dataJs0Slice, jsonObjs));
	
		
		result.put(jsdata, visobj);
		
		
		
		// storing query in db ----> Henry's work
		/*QueryDecryptionAction*/DistinctValues.connector.dataSetToDB(dataJs0Slice,"table");

		return result;
	}

	/**
	 * @param id
	 * @return
	 */
	public List<QueryForm> Extension(int id) {
		String oldatt = "";
		List<QueryForm> result = new ArrayList<QueryForm>();

		QueryForm newQuery = ExtensionStep1(id);
		//QueryConstructor qc = new QueryConstructor(this.userQuery, this.relevantPair);
		//queryBuilder.setQueryBean(userQuery);
		//queryBuilder.setRelevants(relevantPair);
		
		QueryForm query = new QueryForm(newQuery.getSelectedAtt(), newQuery.getCondAtt(), newQuery.getTables(),
				newQuery.getGroupbyAtt(), newQuery.getOrderbyAtt(), newQuery.getJoinTables());
		QueryForm ExtensionBasis = queryBuilder/*qc*/.ExtendV2(query, webVal, webField);

		oldatt = ExtensionBasis.FindOld(newQuery.getSelectedAtt());

		// racine should contain limit 10
		String racine = ExtensionBasis.topTen(oldatt);
		String newElt = ExtensionBasis.FindNewV2();

		QueryForm queryRev = ExtensionBasis.Derivation(racine, newElt);
		QueryForm queryRevComplement = ExtensionBasis.CompleteDerivation(racine, newElt);

		/***********************
		 * second range of queries without relevant pair
		 ***********************************/
		Map<String, String> newcds = new HashMap<String, String>();
		newcds.putAll(queryRev.getCondAtt());
		newcds.remove(this.relevantPair.split(",")[0]);
		QueryForm queryRevWithoutPair = new QueryForm(queryRev.getSelectedAtt(), newcds, queryRev.getTables(),
				queryRev.getGroupbyAtt(), queryRev.getOrderbyAtt(), queryRev.getJoinTables());
		Map<String, String> newcds2 = new HashMap<String, String>();
		newcds2.putAll(queryRevComplement.getCondAtt());
		newcds2.remove(this.relevantPair.split(",")[0]);
		QueryForm queryRevComplWithoutPair = new QueryForm(queryRevComplement.getSelectedAtt(), newcds2,
				queryRevComplement.getTables(), queryRevComplement.getGroupbyAtt(), queryRevComplement.getOrderbyAtt(),
				queryRevComplement.getJoinTables());

		/***********************
		 * second range of queries without relevant pair with limit 10
		 ***********************************/
		/*
		 * Map<String,String> newcds22=new HashMap<String,String>();
		 * newcds22.putAll(newQuery.getCondAtt());
		 * newcds22.remove(this.relevantPair.split(",")[0]);
		 * 
		 * QueryForm newQuery2 = new QueryForm(newQuery.getSelectedAtt(),
		 * newcds22, newQuery.getTables(), newQuery.getGroupbyAtt(),
		 * newQuery.getOrderbyAtt(), newQuery.getJoinTables());
		 * System.out.println( "********>> "+ newQuery2.display()); QueryForm
		 * ExtensionBasis2 = qc.ExtendV2(newQuery2, webVal, webField);
		 * 
		 * String racine2 = ExtensionBasis2.topTen(oldatt); String newElt2 =
		 * ExtensionBasis2.FindNewV2(); QueryForm queryRevWithoutPair =
		 * ExtensionBasis.Derivation(racine2, newElt2); QueryForm
		 * queryRevComplWithoutPair = ExtensionBasis.CompleteDerivation(racine2,
		 * newElt2); Map<String,String> newcds=new HashMap<String,String>();
		 * newcds.putAll(queryRevWithoutPair.getCondAtt());
		 * newcds.remove(this.relevantPair.split(",")[0]);
		 * queryRevWithoutPair.setCondAtt(newcds);
		 * 
		 * 
		 * Map<String,String> newcds2=new HashMap<String,String>();
		 * newcds2.putAll(queryRevComplWithoutPair.getCondAtt());
		 * newcds2.remove(this.relevantPair.split(",")[0]);
		 * queryRevComplWithoutPair.setCondAtt(newcds2);
		 */

		/****************************
		 * going on details for query with relevant pair as condition
		 *******************************/
		Map<String, String> cdsSansTop10 = new HashMap<String, String>();

		for (Entry<String, String> kk : queryRev.getCondAtt().entrySet()) {
			if (!kk.getValue().contains(racine)) {
				cdsSansTop10.put(kk.getKey(), kk.getValue());
			}
		}
		// cdsSansTop10.putAll(queryRev.getCondAtt());
		// cdsSansTop10.remove(newElt);
		QueryForm QueryExtensionSHowAll = new QueryForm(queryRev.getSelectedAtt(), cdsSansTop10, queryRev.getTables(),
				queryRev.getGroupbyAtt(), queryRev.getOrderbyAtt(), queryRev.getJoinTables());


		/****************************
		 * going on details for query without relevant pair as condition
		 *******************************/

		cdsSansTop10.remove(this.relevantPair.split(",")[0]);

		QueryForm QueryExtensionSHowAllBigger = new QueryForm(queryRev.getSelectedAtt(), cdsSansTop10,
				queryRev.getTables(), queryRev.getGroupbyAtt(), queryRev.getOrderbyAtt(), queryRev.getJoinTables());
	

		/********************** query to perform next ***********************/
		Map<String, String> ordre = new HashMap<String, String>();
		List<String> grps = new ArrayList<String>(ExtensionBasis.getGroupbyAtt());
		grps.remove(oldatt);
		List<String> selections = new ArrayList<String>(userQuery.getSelectedAtt());
		selections.addAll(grps);
		//il ya un erreur dans group by
		ArrayList<String> xx = new ArrayList<String>(queryRevComplement.getOrderbyAtt().keySet());
		ArrayList<String> yy =new ArrayList<String>();
		lop: for (String param:xx)
		{
			if(!param.equals("1")) {
				yy.add(param);
				break lop;
			}
		}
		
	//	Arrays.asList(xx.get(0))
		QueryForm queryToPerformNext = new QueryForm(queryRevComplement.getSelectedAtt(), ExtensionBasis.getCondAtt(),
				ExtensionBasis.getTables(), yy, ordre, ExtensionBasis.getJoinTables());

		/************************query to store in the database *************/
		QueryForm queryToStorewInDB = new QueryForm(queryRev.getSelectedAtt(), ExtensionBasis.getCondAtt(),
				ExtensionBasis.getTables(), queryRev.getGroupbyAtt(), ordre, ExtensionBasis.getJoinTables());
		
		result.add(queryRev);
		result.add(queryRevComplement);

		result.add(queryRevWithoutPair);
		result.add(queryRevComplWithoutPair);
		result.add(QueryExtensionSHowAll);

		result.add(QueryExtensionSHowAllBigger);
		result.add(queryToPerformNext);
		result.add(queryToStorewInDB);

		
		return result;

	}

	// return preliminary result of extension
	public QueryForm ExtensionStep1(int id2) {
		//QueryConstructor qc = new QueryConstructor(this.userQuery, this.relevantPair);

		QueryForm newQuery = queryBuilder.Extend().get(id2);
		return newQuery;
	}

	public Map<List<QueryForm>, List<List<DisplayForm>>> VisExtension(int id2) throws IllegalArgumentException, IllegalAccessException {

		//QueryConstructor qc = new QueryConstructor(this.userQuery, this.relevantPair);
		QueryForm newQuery = ExtensionStep1(id2);
		List<QueryForm> Extendqueries = Extension(id2);
		String oldatt =  findOld();
		QueryForm query1 = Extendqueries.get(0);
		QueryForm query1other = Extendqueries.get(1);
		VegaJsonMapping vega = new VegaJsonMapping();

		// ===================> principle visualization
		List<DisplayForm> jsonobjectsPrincipal = vega.constructDynamicV5(query1);
		List<DisplayForm> jsonobjects2 = vega.constructDynamicV4(query1other);
		if (!jsonobjects2.isEmpty()) {
			jsonobjectsPrincipal.addAll(jsonobjects2);
		}
		

		// ===================> second visualization without relevant pair in
		// condition
		List<DisplayForm> jsonobjectsWithoutPair = vega.constructDynamicV5(Extendqueries.get(2));
		List<DisplayForm> jsonobjectsWithoutPair2 = vega.constructDynamicV4(Extendqueries.get(3));
		if (!jsonobjectsWithoutPair2.isEmpty()) {
			jsonobjectsWithoutPair.addAll(jsonobjectsWithoutPair2);
		}

		// ===================> third and fourth visualization to show all other
		List<DisplayForm> detailswithPair = vega.constructDynamicV5(Extendqueries.get(4));
		List<DisplayForm> detailsWithoutPair = vega.constructDynamicV5(Extendqueries.get(5));


		// ===================> query to execute next 
		List<DisplayForm> jsonObjs = vega.construct(Extendqueries.get(6));
	
		
		/*------------template of first + slice recommendation */
		
		Map<List<QueryForm>, List<List<DisplayForm>>> result = new HashMap<List<QueryForm>, List<List<DisplayForm>>>();

		List<List<DisplayForm>> datatorender = new ArrayList<List<DisplayForm>>(Arrays.asList(jsonobjectsPrincipal,jsonobjectsWithoutPair,
				detailswithPair,detailsWithoutPair,jsonObjs));
		List<QueryForm> queries= new ArrayList<QueryForm>();
		queries.addAll(Extendqueries);
		queries.add(newQuery);
		result.put(queries, datatorender);
		
		return result;
	}

	private QueryForm Extension_Slice(int id) {
		String oldatt = "";
		
		QueryForm newQuery = ExtensionStep1(id);
	

		String top10slicequery = newQuery.topTen(oldatt);
		String newElt1 = newQuery.FindNewV2();
		QueryForm ExtensionSlicequery = newQuery.Derivation(top10slicequery, newElt1);

		/*********
		 * &&&&&&&&&&&simplification of slice in extension if we make condition
		 * state=IL , we don't need other for state of departure &&&&&&&&
		 */
		Map<String, String> simpleConditions = new HashMap<String, String>(ExtensionSlicequery.getCondAtt());
		List<String> transtionvalues = new ArrayList<String>(simpleConditions.values());
		List<String> transtionkeys = new ArrayList<String>(simpleConditions.keySet());
		int ind = transtionvalues.indexOf("select  query1.mem from (" + top10slicequery + ") as query1   ");
		simpleConditions.remove(transtionkeys.get(ind));
		QueryForm UniExtensionSlicequery = new QueryForm(ExtensionSlicequery.getSelectedAtt(), simpleConditions,
				ExtensionSlicequery.getTables(), ExtensionSlicequery.getGroupbyAtt(),
				ExtensionSlicequery.getOrderbyAtt(), ExtensionSlicequery.getJoinTables());

	
		return UniExtensionSlicequery;

		// return result;

	}

	public Map<List<List<DisplayForm>>, List<Vega>> VisExtension_Slice(int id2) {

		QueryForm SLice_ExtQuery = Extension_Slice(id2);

		VegaJsonMapping vega = new VegaJsonMapping();
		List<DisplayForm> jsonObjs = vega.construct(SLice_ExtQuery);
		List<List<DisplayForm>> datasetsToRender= new ArrayList<List<DisplayForm>>(Arrays.asList(jsonObjs));
	

		//********* no schema rendered, for this case we use previous schema
	//----->	we should use decide fct
		VegaBars BarChart=new VegaBars(SLice_ExtQuery);
		List<Vega> VegaSchemes= new ArrayList<Vega>(Arrays.asList(BarChart));
	

		Map<List<List<DisplayForm>>, List<Vega>> result = new HashMap<List<List<DisplayForm>>, List<Vega>>();
		result.put(datasetsToRender, VegaSchemes);
		return result;

	
	}

	private List<QueryForm> DrillDown(QueryForm queryOriginal) {

		//QueryConstructor qc = new QueryConstructor(this.userQuery, this.relevantPair);
		List<QueryForm> zoomqueries = zoomIn();
		QueryForm querybase11 = zoomqueries.get(0);
		QueryForm querybase11other = zoomqueries.get(1);

		QueryForm drillquery = queryBuilder/*qc*/.drill(querybase11, queryOriginal);
		QueryForm drillqueryComplement = queryBuilder/*qc*/.drill(querybase11other, queryOriginal);
		
		
	
		Map<String, String> newcds2 = new HashMap<String, String>();
		newcds2.putAll(drillquery.getCondAtt());
		newcds2.remove(this.relevantPair.split(",")[0]);
		//query to store in DB
		QueryForm queryDrillDB = new QueryForm(drillquery.getSelectedAtt(), newcds2,
				drillquery.getTables(), drillquery.getGroupbyAtt(),drillquery.getOrderbyAtt(),
				drillquery.getJoinTables());
		//System.out.println(queryDrillDB.display());
		

		//query to perform next
		QueryForm queryToPerformNext = new QueryForm(drillqueryComplement.getSelectedAtt(), newcds2,
				drillqueryComplement.getTables(), drillqueryComplement.getGroupbyAtt(),drillqueryComplement.getOrderbyAtt(),
				drillqueryComplement.getJoinTables());
	//	System.out.println(queryToPerformNext.display());
		
		
		List<QueryForm> result = new ArrayList<QueryForm>(Arrays.asList(drillquery, drillqueryComplement,queryDrillDB,queryToPerformNext));
		return result;

	}

	// it returns string of list, first element is vega template, two other are
	// json result of two queries
	public Map<List<DisplayForm>, List<QueryForm>> VisDrillDown(QueryForm queryOriginal) {
		Map<List<DisplayForm>, List<QueryForm>> result = new HashMap<List<DisplayForm>, List<QueryForm>>();
		VegaJsonMapping vega = new VegaJsonMapping();
		List<QueryForm> zoomqueries = DrillDown(queryOriginal);
		QueryForm drillquery = zoomqueries.get(0);
		QueryForm drillqueryComplement = zoomqueries.get(1);
		/*QueryForm queryDrillDB = zoomqueries.get(2);
		QueryForm queryToPerformNext = zoomqueries.get(3);*/
	

		List<DisplayForm> jsonobjects = vega.constructDynamicV5(drillquery);
		int reduction = jsonobjects.size() / 2;
		jsonobjects.remove(reduction);
		if(jsonobjects.size()>50)
		{jsonobjects=jsonobjects.subList(0, 49);}
		List<DisplayForm> jsonobjectsComplement=   vega.constructDynamicV4(drillqueryComplement);
		if (jsonobjectsComplement.size()>0)
		{jsonobjects.addAll(jsonobjectsComplement);}
	
	
		
		//String dataJsonDrill = vega.ConvertToJsonV5(jsonobjects);

		/*------------template of viz recommendation */
		/*QueryConstructor qc = new QueryConstructor(this.userQuery, this.relevantPair);
		QueryForm newQuery = qc.Extend().get(0);
		String oldatt = findOld();*/

		/*VegaVis template1 = new VegaBars(drillquery);
List<Vega> tosendVIZoBJ= new ArrayList<Vega>(Arrays.asList(template1));
		VegaChart chartDrill = template1.PrintVisrecSlice_Drill(this.relevantPair.split(",")[0],
				drillqueryComplement.getGroupbyAtt().get(0), "Slice");
		String jsDrillDown = template1.buildJs(chartDrill);*/

		
		/*%%%%%%%%%%%%%%%%%       explicit scale            %%%%%%%%%%%%%%%%%%*/
		//List<DisplayForm> colorRanges=template1.Construct_ScaleColorData_AxeX2(jsonobjects);
		//String colorMapping_json = 	vega.ConvertToJsonV5(colorRanges);
		
		
	//	List<String> result = new ArrayList<String>(Arrays.asList(jsDrillDown, dataJsonDrill,colorMapping_json ));
		
		result.put(jsonobjects,  zoomqueries);
		
		// storing query in db ----> Henry's work
			//	QueryDecryptionAction.connector.dataSetToDB(jsonobjects);
		
		return result;
	}

	private List<QueryForm> DrillDown_Slice(QueryForm queryOriginal) {

		//QueryConstructor qc = new QueryConstructor(this.userQuery, this.relevantPair);
		List<QueryForm> zoomqueries = DrillDown(queryOriginal);
		QueryForm drillquery = zoomqueries.get(0);
		QueryForm drillqueryComplement = zoomqueries.get(1);

		Map<String, String> drillconds = new HashMap<String, String>(drillquery.getCondAtt());
		drillconds.putAll(this.userQuery.getCondAtt());
		Map<String, String> drillcondsother = new HashMap<String, String>(drillqueryComplement.getCondAtt());
		drillcondsother.putAll(this.userQuery.getCondAtt());

		QueryForm drillSlicequery = new QueryForm(drillquery.getSelectedAtt(), drillconds, drillquery.getTables(),
				drillquery.getGroupbyAtt(), drillquery.getOrderbyAtt(), drillquery.getJoinTables());

		QueryForm drillSlicequeryComplement = new QueryForm(drillqueryComplement.getSelectedAtt(), drillcondsother,
				drillqueryComplement.getTables(), drillqueryComplement.getGroupbyAtt(),
				drillqueryComplement.getOrderbyAtt(), drillqueryComplement.getJoinTables());
		/**********************
		 * preparation of query that will be used for next step
		 *************************/
	
		QueryForm NextQueryToPerform = new QueryForm(drillqueryComplement.getSelectedAtt(), this.userQuery.getCondAtt(),
				drillqueryComplement.getTables(), drillqueryComplement.getGroupbyAtt(),
				drillqueryComplement.getOrderbyAtt(), drillqueryComplement.getJoinTables());

		//System.out.println(NextQueryToPerform.display());
		/******************query to store in DB************************/
		
		QueryForm QueryDB = new QueryForm(drillSlicequery.getSelectedAtt(), /*condsupdate*/ this.userQuery.getCondAtt(),
				drillSlicequery.getTables(),drillSlicequery.getGroupbyAtt(),
				drillSlicequery.getOrderbyAtt(), drillSlicequery.getJoinTables());

		//System.out.println(QueryDB.display());
		/******************query to store in DB************************/
		
		List<QueryForm> result = new ArrayList<QueryForm>(Arrays.asList(drillSlicequery, drillSlicequeryComplement,NextQueryToPerform,QueryDB));

		return result;

	}

	// it returns string of list, first element is vega template, the second one
	// is json result of two queries
	public 	 Map< /*Vega*/List<QueryForm>,List<DisplayForm>> VisDrillDown_Slice(QueryForm queryOriginal) {
		Map< /*Vega*/List<QueryForm>,List<DisplayForm>> result = new HashMap</*Vega*/List<QueryForm>,List<DisplayForm>>();
		
		List<QueryForm> queries = DrillDown_Slice(queryOriginal);
		QueryForm query1 = queries.get(0);
		QueryForm query2 = queries.get(1);

		
		
		VegaJsonMapping vega = new VegaJsonMapping();
		List<DisplayForm> jsonobjects = vega.constructDynamicV5(query1);
		jsonobjects.addAll(vega.constructDynamicV4(query2));
		result.put(queries, jsonobjects);
		
		
		
      

		
		return result;
	}

}
