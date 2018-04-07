package prov.CRUD;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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
import org.apache.struts.action.ActionForm;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.FloatType;
import org.hibernate.type.StringType;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import prov.mybean.*;
import prov.util.HibernateUtil;

// give more flexibility ion computation
public class MatrixConstruction {

	public Map<String, List<String>> core;
	public Float min_score;
	public Float max_score;
	public String db_name;
	private 	QueryConstructor queryBuilder;

	public MatrixConstruction(Map<String, List<String>> core) {
		super();
		this.core = core;
		this.min_score = (float) 0;
		this.max_score = (float) 0;
		this.db_name="USflight";
		if (this.db_name.equals("soccerDB")) {
			queryBuilder = new QueryConstructor_Soccer();
		}
		else if (this.db_name.equals("formulaOne")) {
			queryBuilder = new QueryConstructor_Formula();
		}
		else {
			queryBuilder= new QueryConstructor();
		}
	}

	public MatrixConstruction(Map<String, List<String>> core,String dbName) {
		super();
		this.core = core;
		this.min_score = (float) 0;
		this.max_score = (float) 0;
		this.db_name=dbName;
		if (this.db_name.equals("soccerDB")) {
			queryBuilder = new QueryConstructor_Soccer();
		}
		else if (this.db_name.equals("formulaOne")) {
			queryBuilder = new QueryConstructor_Formula();
		}
		
		else {
			queryBuilder= new QueryConstructor();
		}
	}
	public String construct() {

		List<MatrixForm> result = new ArrayList<MatrixForm>();
		Integer i = 0;
		for (Entry<String, List<String>> entry : this.core.entrySet()) {

			String name = "[" + entry.getKey() + ",[" + String.join(",", entry.getValue()) + "]]";

			MatrixForm a = new MatrixForm(name, 1, i);
			i++;
			result.add(a);
		}

		// function below is used to rename fields in your json file
		FieldNamingStrategy customPolicy = new FieldNamingStrategy() {
			public String translateName(Field f) {

				return f.getName().toLowerCase();
			}
		};

		/****************
		 * transformation of result in JSON format
		 ***************/
		// json generation
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingStrategy(customPolicy);
		Gson gson = gsonBuilder.create();
		String resultJson = gson.toJson(result);

		return resultJson;

	}

	public String RandomScore() {

		List<MatrixScoreForm> result = new ArrayList<MatrixScoreForm>();
		Integer i = 0;
		for (Entry<String, List<String>> entry : this.core.entrySet()) {
			float rand = randInt(0, 10);
			MatrixScoreForm a = new MatrixScoreForm(i, 98, rand);
			result.add(a);
			float rand2 = randInt(0, 10);
			MatrixScoreForm a2 = new MatrixScoreForm(i, 99, rand2);
			result.add(a2);
			float rand3 = randInt(0, 10);
			MatrixScoreForm a3 = new MatrixScoreForm(i, 100, rand3);
			result.add(a3);

			i++;
		}

		/****************
		 * transformation of result in JSON format
		 ***************/
		// json generation
		GsonBuilder gsonBuilder = new GsonBuilder();
		// gsonBuilder.setFieldNamingStrategy(customPolicy);
		Gson gson = gsonBuilder.create();
		String resultJson = gson.toJson(result);

		return resultJson;

	}

	public QueryForm ZoomInQuery(QueryForm userquery, String state) {
	
		/**************************
		 * query adjustement
		 *****************************/
		Map<String, String> conds = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : userquery.getCondAtt().entrySet()) {
			if (!entry.getValue().contains(state)) {

				conds.put(entry.getKey(), entry.getValue());
			}
		}

		List<String> SelectAll = new ArrayList<String>(Arrays.asList("*"));
		QueryForm ZoomInQuery = new QueryForm(SelectAll, conds, userquery.getTables(), userquery.getJoinTables());
		
		/*****************************/	
		
		/*if (this.db_name.equals("soccerDB")) {
			QueConst = new QueryConstructor_Soccer(ZoomInQuery, "ss");
		} else {
			QueConst = new QueryConstructor(ZoomInQuery, "ss");
		}*/
		queryBuilder.setQueryBean(ZoomInQuery);
		queryBuilder.setRelevants("ss");
	/*****************************/	

		boolean duplicaTabs = ZoomInQuery.checkDuplicatedTables();
		boolean duplicaAtts = ZoomInQuery.checkDuplicatedAttributes();
	
		if (duplicaAtts||duplicaTabs) {
			QueryForm ZoomInQuery2 = /*QueConst*/queryBuilder.avoidDoublecolumn(ZoomInQuery,duplicaTabs);

			//System.out.println("xxxxx " + ZoomInQuery.display());
			//System.out.println("xxxxx " + ZoomInQuery2.display());
			return ZoomInQuery2;

		}
		
	

		/****************** table construction **************************/

		return ZoomInQuery;
	}

	public List<MatrixScoreForm> ZoomInScore(QueryForm userquery, String state) {

		/**************************
		 * query adjustement
		 *****************************/
		QueryForm ZoomInQuery = ZoomInQuery(userquery, state);

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		String zoominquuery = "create MATERIALIZED VIEW view_stat1 as " + ZoomInQuery.display();
		/************* check tables duplic *****************/

		/****************** table construction **************************/

		session.createSQLQuery(" DROP MATERIALIZED VIEW if exists view_stat1 ").executeUpdate();
		session.createSQLQuery(zoominquuery).executeUpdate();

		session.createSQLQuery(" analyze  view_stat1 ").executeUpdate();

		List<Object> scoreList = (List<Object>) session.createSQLQuery(
				"select * from public.\"KullbackcomputationZoom\"('{" + String.join(", ", core.keySet()) + "}')")
				.list();

		tx.commit();
		session.close();

		/****************** table construction **************************/

		/***************************************/
		List<MatrixScoreForm> result = DecryptObject(scoreList, 98);

		return result;

	}

	public static float randInt(float min, float max) {

		Random rand = new Random();

		float result = rand.nextFloat() * (max - min) + min;

		return result;

	}

	private List<MatrixScoreForm> DecryptObject(List<Object> iniQuery, int j) {
		DecimalFormat df2 = new DecimalFormat(".##");
		Integer i = 0;
		List<MatrixScoreForm> datavis = new ArrayList<MatrixScoreForm>();
		Iterator itr = iniQuery.iterator();
		Map<Integer, String> zoomverif = new HashMap<Integer, String>();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();

			if (obj[1] == null) {
				MatrixScoreForm a = new MatrixScoreForm(i, j, (float) 0);
				datavis.add(a);
			} else {
				String kullbackResult = String.valueOf(obj[1]);

				MatrixScoreForm aa = new MatrixScoreForm(i, j, Float.parseFloat(kullbackResult));

				datavis.add(aa);
				if (this.max_score < Float.parseFloat(kullbackResult)) {
					this.max_score = Float.parseFloat(kullbackResult);
				}
			}

			i++;
		}

		return datavis;
	}

	public Map<String, QueryForm> generateSliceQueries(QueryForm queryBean, String webField, String webVal) {
		Map<String, QueryForm> slicequeries = new HashMap<String, QueryForm>();

		for (Entry<String, List<String>> entry : this.core.entrySet()) {
			List<String> temporaire = entry.getValue();
			temporaire.remove(temporaire.size() - 1);
			String pair = entry.getKey() + "," + String.join(", ", temporaire);
		
		
			/*QueryConstructor qc = new QueryConstructor(queryBean, pair);*/
			queryBuilder.setQueryBean(queryBean);
			queryBuilder.setRelevants(pair);
		

			QueryForm extendedUserQuery = queryBuilder/*qc*/.ExtendTablePlusId().get(0);

			/********************
			 * before inserting remove all selection part and group by
			 ****************************/
			List<String> SelectAll = new ArrayList<String>(Arrays.asList("Flights.*"));

			Map<String, String> conds = new HashMap<String, String>();
			for (Map.Entry<String, String> entry1 : extendedUserQuery.getCondAtt().entrySet()) {
				if (!entry1.getValue().contains(webVal)) {

					conds.put(entry1.getKey(), entry1.getValue());
				}
			}

			QueryForm queryStatsSlice = new QueryForm(extendedUserQuery.getSelectedAtt(), conds,
					extendedUserQuery.getTables(), extendedUserQuery.getGroupbyAtt(), extendedUserQuery.getOrderbyAtt(),
					extendedUserQuery.getJoinTables());
			slicequeries.put(entry.getKey(), queryStatsSlice);

			/********** compute directly here Kullback *******************/
			List<String> Selectcount = new ArrayList<String>(Arrays.asList("count(*)"));

			QueryForm queryLinNumberSlice = new QueryForm(Selectcount, conds, extendedUserQuery.getTables(),
					extendedUserQuery.getJoinTables());

			String Klfrag = "cast (count(*) as real)/(" + queryLinNumberSlice.display() + ")  as freq1 ";
			List<String> part1KL = new ArrayList<String>();

			for (String att : extendedUserQuery.getSelectedAtt()) {
				if (!att.toLowerCase().contains("count") && !att.toLowerCase().contains("avg")
						&& !att.toLowerCase().contains("sum") && !att.toLowerCase().contains("min"))

					part1KL.add(att);
			}
			part1KL.add(Klfrag);

			queryStatsSlice.setSelectedAtt(part1KL);

			/********************************************************/

			slicequeries.put(entry.getKey(), queryStatsSlice);

		}

		return slicequeries;
	}

	/*** generate query to compute distribution of the column Slice ***/

	public QueryForm generateSliceDB(Map<String, QueryForm> slicequeries) {

		List<String> exampel = new ArrayList<String>();
		exampel.addAll(slicequeries.keySet());
		QueryForm randquery = slicequeries.get(exampel.get(0));
		QueryForm sliceDBquery = new QueryForm(randquery.getSelectedAtt(), randquery.getTables(),
				randquery.getJoinTables(), randquery.getGroupbyAtt());

		List<String> part1KL = new ArrayList<String>();

		part1KL.add("count(*)");

		QueryForm queryAllDB2 = new QueryForm(part1KL, sliceDBquery.getTables(), sliceDBquery.getJoinTables());

		String Klfrag = "cast (count(*) as real)/(" + queryAllDB2.display() + ")  as freq2 ";

		List<String> part2KL = new ArrayList<String>();
		for (String att : randquery.getSelectedAtt()) {
			if (!att.toLowerCase().contains("count") && !att.toLowerCase().contains("avg")
					&& !att.toLowerCase().contains("sum") && !att.toLowerCase().contains("min"))

				part2KL.add(att);
		}
		part2KL.add(Klfrag);
		// queryAllDB.setSelectedAtt(part2KL);

		sliceDBquery.setSelectedAtt(part2KL);

		// System.out.println(sliceDBquery.display());

		return sliceDBquery;
	}

	public Map<String, QueryForm> generateSliceQueries2(QueryForm queryBean, String webField, String webVal) {
		Map<String, QueryForm> slicequeries = new HashMap<String, QueryForm>();

		//QueryConstructor qc = new QueryConstructor(queryBean, "");
		queryBuilder.setQueryBean(queryBean);
		

		QueryForm extendedUserQuery = queryBuilder/*qc*/.ExtendTablePlusId().get(0);

		/********** compute directly here Kullback *******************/
		List<String> Selectcount = new ArrayList<String>(Arrays.asList("count(*)"));
		Map<String, String> conds = new HashMap<String, String>();
		for (Map.Entry<String, String> entry1 : extendedUserQuery.getCondAtt().entrySet()) {
			if (!entry1.getValue().contains(webVal) || !entry1.getKey().isEmpty()) {

				conds.put(entry1.getKey(), entry1.getValue());
			}
		}

		QueryForm queryLinNumberSlice = new QueryForm(Selectcount, conds, extendedUserQuery.getTables(),
				extendedUserQuery.getJoinTables());

		for (Entry<String, List<String>> entry : this.core.entrySet()) {
			List<String> temporaire = entry.getValue();
			temporaire.remove(temporaire.size() - 1);
			String pair = entry.getKey() + "," + String.join(", ", temporaire);

			QueryConstructor qc1 = new QueryConstructor(extendedUserQuery, pair);

			QueryForm queryStatsSlice = qc1.Formalize();

			String Klfrag = "count(*)/(" + queryLinNumberSlice.display() + ") ::real as freq1 ";
			List<String> part1KL = new ArrayList<String>();

			for (String att : queryStatsSlice.getSelectedAtt()) {
				if (!att.toLowerCase().contains("count") && !att.toLowerCase().contains("avg")
						&& !att.toLowerCase().contains("sum") && !att.toLowerCase().contains("min"))

					part1KL.add(att);
			}
			part1KL.add(Klfrag);

			queryStatsSlice.setSelectedAtt(part1KL);
			slicequeries.put(entry.getKey(), queryStatsSlice);

			/********************************************************/

		}

		return slicequeries;
	}

	private String cleaningandReformulationRelvantPair(String entry1, List<String> temporaire1) {
		// List<String> temporaire1 = entry1.getValue();

		String pair1 = entry1 + "," + String.join(", ", temporaire1).replace(", others", "");
		/***************** more reformulation of pair1 *************/
		String info3 = "";
		String[] pair2 = pair1.replace("[", "").replace("]", "").split(",");
		if (pair2.length > 2) {

		lp:	for (int k = 0; k < pair2.length; k++) {
				//String test = pair2[k];
				String test = pair2[ pair2.length-1];
				if (validateDateFormat(test)){
					info3 = pair1.replace("[", "").replace("]", "")+",";
					break lp;
				}
				else	if (pair2[k].contains("-") && pair2[k].split("-")[1].replaceAll("\\s+", "").matches("-?\\d+(\\.\\d+)?")
						&& pair2[k].split("-")[0].replaceAll("\\s+", "").matches("-?\\d+(\\.\\d+)?")) {

					info3 = info3 + pair2[k].split("-")[0] + "&" + pair2[k].split("-")[1] + ",";
				} else
					info3 = info3 + pair2[k] + ",";
			}
			info3 = info3.substring(0, info3.length() - 1);

		} else {
			String test = pair2[ pair2.length-1];
			
			if (validateDateFormat(test)){
				info3 = pair1.replace("[", "").replace("]", "");
				
			}
			else if (pair1.contains("-") && pair2[1].split("-")[1].replaceAll("\\s+", "").matches("-?\\d+(\\.\\d+)?")
					&& pair2[1].split("-")[0].replaceAll("\\s+", "").matches("-?\\d+(\\.\\d+)?")) {
				info3 = pair2[0] + "," + pair2[1].split("-")[0] + "&" + pair2[1].split("-")[1];
			} else {
				info3 = pair2[0] + "," + pair2[1];
			}
		}
		return info3;
	}

	/********
	 * this fct searches the foreign key of new table to add in fact table
	 ********/
	private String SearchEquivalentInFactTable(String idTableToAdd, Map<String, String> joinconditions) {
		if (idTableToAdd.split(Pattern.quote(".")).length >1) {
			for (Entry<String, String> entry : joinconditions.entrySet()) {

				if (entry.getKey().equals(idTableToAdd)) {
					return entry.getValue();
				} else if (entry.getValue().equals(idTableToAdd)) {
					return entry.getKey();
				}

			}
		}

		else {
			for (Entry<String, String> entry : joinconditions.entrySet()) {

				if (entry.getKey().split(Pattern.quote("."))[1].equals(idTableToAdd)) {
					return entry.getValue().split(Pattern.quote("."))[1];
				} else if (entry.getValue().split(Pattern.quote("."))[1].equals(idTableToAdd)) {
					return entry.getKey().split(Pattern.quote("."))[1];
				}

			}

		}
		return null;
	}

	/********************
	 * slice score 6 returns list of two json : the first one describes edges
	 * links second json defines extensions columns needed to be render
	 ************************/
	public Map<String, List<MatrixScoreForm>> ExtensionScore(QueryForm queryBean, QueryForm originalquery,
			String cancel, String state, String typeOfColumn) {
		List<QueryForm> queryExts = new ArrayList<QueryForm>();
		int ColumnId = 100;
		List<MatrixScoreForm> COlumnSliceResult = new ArrayList<MatrixScoreForm>();

		/**
		 * &&&&&&&&&&&&&&&&&&&&&& automatic computation for scores of possible
		 * extensions &&&&&&&&&&&&&&&&&&&&&&&&&&
		 **/
		if (typeOfColumn.equals("slice")) {
			queryExts = constructViewforUserRegionPlusSlice(queryBean, cancel, state);
			ColumnId = 101;
		} else {

			queryExts = constructViewforUserRegion(queryBean, originalquery, cancel, state);
		}

		/*
		 * ?????????????????????automatic generation of extension
		 * columns?????????????????????
		 */
		List<ColumnMatrixForm> ColDefinition = new ArrayList<ColumnMatrixForm>();
		int t = 1;
		int index = ColumnId;
		if (typeOfColumn.equals("slice")) {

			for (int k = 0; k < queryExts.size() * 2; k += 2) {

				ColumnMatrixForm col = new ColumnMatrixForm("Extension_Slice" + t, 1, index);
				ColDefinition.add(col);
				index = index + 2;
				t++;
			}

		}

		else {

			for (int k = 0; k < queryExts.size() * 2; k += 2) {

				ColumnMatrixForm col = new ColumnMatrixForm("Extension" + t, 1, index);
				ColDefinition.add(col);
				index = index + 2;
				t++;
			}

		}

		/*
		 * ?????????????????????automatic generation of extension
		 * columns?????????????????????
		 */
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		/*************
		 * use this special query to construct recommended queries
		 **************/
		for (QueryForm queryDBSlice : queryExts) {

			// System.out.println("------- "+queryDBSlice.display());

			/** --first step get the id of the table added to user query---- */
			String joinkey = queryDBSlice.getSelectedAtt().get(queryDBSlice.getSelectedAtt().size() - 1);

			String equivalent = SearchEquivalentInFactTable(joinkey, queryDBSlice.getJoinTables());
			if (joinkey.split(Pattern.quote(".")).length > 1) {
				joinkey = joinkey.split(Pattern.quote("."))[1];
			}

			if (equivalent.split(Pattern.quote(".")).length > 1) {
				equivalent = equivalent.split(Pattern.quote("."))[1];
			}

			/************ get frequency from all database ************/

			SQLQuery queryDB = session
					.createSQLQuery("select coltwo, colthree from public.\"KullbackDB\"('" + equivalent + "')");
			queryDB.addScalar("coltwo", StringType.INSTANCE);
			queryDB.addScalar("colthree", FloatType.INSTANCE);
			List freqdb = queryDB.list();

			Map<String, Float> SliceDBFreq = new HashMap<String, Float>();
			for (Object o : freqdb) {
				Object[] obj2 = (Object[]) o;
				SliceDBFreq.put(String.valueOf(obj2[0]), Float.parseFloat(String.valueOf(obj2[1])));
			}
			/************************/

			String slicedbViewquery = " CREATE VIEW slicealldb_" + ColumnId + " AS  " + queryDBSlice.display();

			/************** construction of special queryform *************/
			List<String> sel = new ArrayList<String>(Arrays.asList("count(*)"));
			List<String> tables = new ArrayList<String>(Arrays.asList("slicealldb_" + ColumnId + " "));

			ArrayList<String> groupbyAtt = new ArrayList<String>();
			HashMap<String, String> joinTables = new HashMap<String, String>();

			QueryForm querySliceView = new QueryForm(sel, tables, joinTables, groupbyAtt);

			session.createSQLQuery(" DROP  VIEW if exists slicealldb_" + ColumnId + " ").executeUpdate();
			session.createSQLQuery(slicedbViewquery).executeUpdate();
			SQLQuery query = session.createSQLQuery("select * from slicealldb_" + ColumnId + " ");
			int n=0;
			for (String entry : core.keySet()) {
				query.addScalar(entry, StringType.INSTANCE);
				n++;
			}
		//	if (n<queryDBSlice.getSelectedAtt().size())
						//{
				query.addScalar(joinkey, StringType.INSTANCE);
				//}
			List computation = query.list();

			int i = 0;
			List<String> relevancelist = new ArrayList<String>();

			for (Entry<String, List<String>> entry1 : core.entrySet()) {
				/************* construction of slice query ****************/

				String relevantPair = cleaningandReformulationRelvantPair(entry1.getKey(), entry1.getValue());
				relevancelist.add(relevantPair);
				List partialres = new ArrayList<Object>();
				Iterator<Object> ite = computation.iterator();
				if (!relevantPair.contains("&")) {
					if (relevantPair.split(",").length == 2)

					{

						while (ite.hasNext()) {
							Object[] obj = (Object[]) ite.next();
							if (obj[i] != null && obj[i].equals(relevantPair.split(",")[1]))
								partialres.add(obj[obj.length - 1]);
						}
					} else {
						while (ite.hasNext()) {
							Object[] obj = (Object[]) ite.next();
							String[] temporary = relevantPair.split(",");

							outerloop: for (int k = 1; k < temporary.length; k++) {
								if (obj[i] != null && obj[i].equals(relevantPair.split(",")[k])) {
									partialres.add(obj[obj.length - 1]);
									break outerloop;
								}
							}
						}
					}
				} else {
					if (relevantPair.split(",").length == 2)

					{

						while (ite.hasNext()) {
							Object[] obj = (Object[]) ite.next();
							String range = relevantPair.split(",")[1];
						if(	range.matches("-?\\d+(\\.\\d+)?") ) 

							{if (Float.parseFloat(String.valueOf(obj[i])) > (Float.parseFloat(range.split("&")[0]))
									&& Float.parseFloat(
											String.valueOf(obj[i])) < (Float.parseFloat(range.split("&")[1])))
								partialres.add(obj[obj.length - 1]);
						}}
					} 
					
					else {
						while (ite.hasNext()) {
							Object[] obj = (Object[]) ite.next();
							String[] temporary = relevantPair.split(",");

							outerloop: for (int k = 1; k < temporary.length; k++) {
								String range = relevantPair.split(",")[k];
								
								if (range.contains("&") &&range.split("&")[0].matches("-?\\d+(\\.\\d+)?")  
										&& range.split("&")[1].matches("-?\\d+(\\.\\d+)?") && String.valueOf(obj[i]).matches("-?\\d+(\\.\\d+)?")) {
								
									if (Float.parseFloat(String.valueOf(obj[i])) > (Float.parseFloat(range.split("&")[0]))
											&& Float.parseFloat(
													String.valueOf(obj[i])) < (Float.parseFloat(range.split("&")[1])))

									{
										partialres.add(obj[obj.length - 1]);
										break outerloop;
									}
								}

								else {
									if (range.matches("-?\\d+(\\.\\d+)?") && String.valueOf(obj[i]).matches("-?\\d+(\\.\\d+)?") &&
									Float.parseFloat(String.valueOf(obj[i])) == (Float.parseFloat(range)) )

									{
										partialres.add(obj[obj.length - 1]);
										break outerloop;
									}
								}
								
								
							}
						}

					}

				}

				/************** kl slice computation ***************/
				Set<String> uniqueids = new HashSet<String>(partialres);
				Map<String, Float> SLiceFreq = new HashMap<String, Float>();
				for (String elt : uniqueids) {
					if (SliceDBFreq.containsKey(elt)) {

						float freqS = (float) Collections.frequency(partialres, elt) / partialres.size();

						float freqD = (float) SliceDBFreq.get(elt);

						SLiceFreq.put(elt, (float) (freqS * Math.log((float) freqS / freqD)));
					}
				}

				float kl = 0;
				for (Float klelt : SLiceFreq.values()) {
					kl += klelt;
				}
				/****************************/

				MatrixScoreForm mm = new MatrixScoreForm(i, ColumnId, kl);
				COlumnSliceResult.add(mm);

				if (this.max_score < kl) {
					this.max_score = kl;
				}

				i++;

			}
			ColumnId = ColumnId + 2;
		}

		tx.commit();
		session.close();

		/*********** convert automatic names of columns to json **********/
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		String ColJson = gson.toJson(ColDefinition);

		Map<String, List<MatrixScoreForm>> pair1 = new HashMap<String, List<MatrixScoreForm>>();
		pair1.put(ColJson, COlumnSliceResult);

		return pair1;

	}

	public List<MatrixScoreForm> ZoomIn_Slice_Score(QueryForm userquery) {
		String importantItemList = "";
		/**************************
		 * query adjustement
		 *****************************/
		QueryForm queryZoomin_Slice = ZoomIn_Slice_Query(userquery);

		String execquery = "create MATERIALIZED VIEW view_stat2 as " + queryZoomin_Slice.display();

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		/****************** table construction **************************/

		session.createSQLQuery(" DROP MATERIALIZED VIEW if exists view_stat2 ").executeUpdate();
		session.createSQLQuery(execquery).executeUpdate();

		session.createSQLQuery(" analyze  view_stat2 ").executeUpdate();
		/****************** table construction **************************/

		List<Object> scoreList = (List<Object>) session.createSQLQuery(
				"select * from public.\"KullbackcomputationZoom_Slice\"('{" + String.join(", ", core.keySet()) + "}')")
				.list();

		tx.commit();
		session.close();

		/****************** table construction **************************/

		/***************************************/
		List<MatrixScoreForm> result = DecryptObject(scoreList, 99);

		return result;

	}

	// update the materlized view to studay paris disctribution in this slice
	public QueryForm ZoomIn_Slice_Query(QueryForm userquery) {

		/**************************
		 * query adjustement
		 *****************************/

		Map<String, String> conds = new HashMap<String, String>();

		List<String> SelectAll = new ArrayList<String>(Arrays.asList("*"));
		QueryForm queryStatsZoomin_Slice = new QueryForm(SelectAll, userquery.getCondAtt(), userquery.getTables(),
				userquery.getJoinTables());

		boolean duplicaTabs = queryStatsZoomin_Slice.checkDuplicatedTables();
		boolean duplicaAtts = queryStatsZoomin_Slice.checkDuplicatedAttributes();

		//QueryConstructor QueConst = new QueryConstructor(queryStatsZoomin_Slice, "ss");
		queryBuilder.setQueryBean(queryStatsZoomin_Slice);
		queryBuilder.setRelevants("ss");
	
		if (duplicaTabs || duplicaAtts) {
			QueryForm ZoomInQuery2 =	queryBuilder/*QueConst*/.avoidDoublecolumn(queryStatsZoomin_Slice,duplicaTabs);
			QueryForm queryNorEdundancy = new QueryForm(ZoomInQuery2.getSelectedAtt(), queryStatsZoomin_Slice.getCondAtt(), ZoomInQuery2.getTables(),
					ZoomInQuery2.getJoinTables());

			return queryNorEdundancy;

		}

		return queryStatsZoomin_Slice;

	}

	private QueryForm constructDrillDownView(QueryForm queryBean, QueryForm originalquery) {
		/************* generation of db query ***********/

		List<String> relevantcolumns = new ArrayList<String>(core.keySet());
		List<String> entry = this.core.get(relevantcolumns.get(0));

		QueryForm InterQuery = new QueryForm(relevantcolumns, originalquery.getCondAtt(), originalquery.getTables(),
				originalquery.getGroupbyAtt(), originalquery.getOrderbyAtt(), originalquery.getJoinTables());

		String pair2 = cleaningandReformulationRelvantPair(relevantcolumns.get(0), entry);

		//QueryConstructor qc = new QueryConstructor(queryBean, pair2);
		queryBuilder.setQueryBean(queryBean);
		queryBuilder.setRelevants(pair2);
		

		QueryForm queryDBDrill = queryBuilder/*qc*/.drill(InterQuery, originalquery);

		return queryDBDrill;
	}

	/***
	 * take attribute in group by and concat it to all element in select
	 * 
	 * It returns one query which computes frequency in all db the second one
	 * concerns user's query
	 ***/
	private List<QueryForm> TransformAndConcatQuery(QueryForm query) {

		String attconcatenation = query.getGroupbyAtt().get(0);
		List<String> newselection = new ArrayList<String>();
		//Set<String> newselection = new HashSet<String>();
		if (!attconcatenation.isEmpty()) {
			for (String elt : query.getSelectedAtt()) {
				if (!elt.equals(attconcatenation)) {
					if (elt.split(Pattern.quote(".")).length > 1) {
						newselection.add(
								"concat(" + elt + "," + attconcatenation + ") as " + elt.split(Pattern.quote("."))[1]);
					} else {
						newselection.add("concat(" + elt + "," + attconcatenation + ") as " + elt);
					}
				} else{
					if(newselection.contains(elt)) { newselection.add(elt+" as granularityChange ");}
					else	newselection.add(elt);}
			}
		}

		//List<String> newSelection2=new ArrayList<String>();
		//newSelection2.addAll(newselection);
		QueryForm queryresult = new QueryForm(newselection/*newSelection2*/, query.getCondAtt(), query.getTables(),
				query.getJoinTables());

		Map<String, String> emptyconditions = new HashMap<String, String>();
		QueryForm queryDB = new QueryForm(newselection, emptyconditions, query.getTables(), query.getJoinTables());

		ArrayList<QueryForm> result = new ArrayList<QueryForm>();
		result.add(queryDB);
		result.add(queryresult);
		return result;
	}

	/***
	 * change select part, put only the current relevant element and new
	 * granularity e..g city
	 * 
	 * @param field
	 * 
	 * 
	 ***/
	private List<QueryForm> TransformQueryOneElt(QueryForm query, String field) {

		String attconcatenation = query.getGroupbyAtt().get(0);
		List<String> newselection = new ArrayList<String>();

		if (!field.equals(attconcatenation)) {
			newselection.add(field);
			newselection.add(attconcatenation);
		} else
			newselection.add(field);

		QueryForm queryresult = new QueryForm(newselection, query.getCondAtt(), query.getTables(),
				query.getJoinTables());

		Map<String, String> emptyconditions = new HashMap<String, String>();
		QueryForm queryDB = new QueryForm(newselection, emptyconditions, query.getTables(), query.getJoinTables());

		ArrayList<QueryForm> result = new ArrayList<QueryForm>();
		result.add(queryDB);
		result.add(queryresult);
		return result;
	}

	/**
	 * this function prepare the user 's region of interest with extension of
	 * new dimension
	 * 
	 * @param state
	 * @param cancel
	 * @param originalquery
	 * @param queryBean
	 *******************************************/
	private List<QueryForm> constructViewforUserRegion(QueryForm queryBean, QueryForm originalquery, String cancel,
			String state) {
		/************* generation of db query ***********/
		List<QueryForm> result = new ArrayList<QueryForm>();
		List<String> relevantcolumns = new ArrayList<String>(core.keySet());
		List<String> entry = this.core.get(relevantcolumns.get(0));

		QueryForm InterQuery = new QueryForm(relevantcolumns, originalquery.getCondAtt(), queryBean.getTables(),
				queryBean.getGroupbyAtt(), queryBean.getOrderbyAtt(), queryBean.getJoinTables());

		String pair2 = cleaningandReformulationRelvantPair(relevantcolumns.get(0), entry);

	//	QueryConstructor qc = new QueryConstructor(InterQuery, pair2);
		queryBuilder.setQueryBean(InterQuery);
		queryBuilder.setRelevants(pair2);
	

		List<QueryForm> extendedqueries = 	queryBuilder.ExtendTablePlusId();
		for (QueryForm extendedUserQuery : extendedqueries) {
			Map<String, String> tempo = extendedUserQuery.getCondAtt();
			tempo.remove(pair2.split(",")[0]);
			QueryForm queryextension = new QueryForm(extendedUserQuery.getSelectedAtt(),
					tempo, extendedUserQuery.getTables(),
					extendedUserQuery.getJoinTables());

			result.add(queryextension);
		}

		/********************
		 * end db generation
		 ****************************/
		return result;
	}

	/**
	 * this function prepare the user 's region of interest with extension of
	 * new dimension and slice
	 * 
	 * @param state
	 * @param cancel
	 * @param originalquery
	 * @param queryBean
	 *******************************************/
	private List<QueryForm> constructViewforUserRegionPlusSlice(QueryForm queryBean, String cancel, String state) {
		/************* generation of db query ***********/
		List<QueryForm> result = new ArrayList<QueryForm>();
		List<String> relevantcolumns = new ArrayList<String>(core.keySet());
		List<String> entry = this.core.get(relevantcolumns.get(0));

		QueryForm InterQuery = new QueryForm(relevantcolumns, queryBean.getCondAtt(), queryBean.getTables(),
				queryBean.getGroupbyAtt(), queryBean.getOrderbyAtt(), queryBean.getJoinTables());

		String pair2 = cleaningandReformulationRelvantPair(relevantcolumns.get(0), entry);

		//QueryConstructor qc = new QueryConstructor(InterQuery, pair2);
		queryBuilder.setQueryBean(InterQuery);
		queryBuilder.setRelevants(pair2);
	

		List<QueryForm> extendedSlices = 	queryBuilder/*qc*/.ExtendTablePlusId();
		for (QueryForm extendedUserQuery : extendedSlices) {
			Map<String, String> tempo = extendedUserQuery.getCondAtt();
			tempo.remove(pair2.split(",")[0]);
			QueryForm queryDBSlice = new QueryForm(extendedUserQuery.getSelectedAtt(), tempo,
					extendedUserQuery.getTables(), extendedUserQuery.getJoinTables());
			result.add(queryDBSlice);

		}
		/********************
		 * end db generation
		 ****************************/
		return result;
	}

	// compute kl given a list of query result and map of databse frequencies
	private List<MatrixScoreForm> ComputeKLDrill(int ColumnId, List computationPartDrill,
			Map<String, Float> SliceDBFreq) {
		int k = 0;
		ArrayList<MatrixScoreForm> COlumnDrillResult = new ArrayList<MatrixScoreForm>();
		Map<String, Float> SLiceFreq = new HashMap<String, Float>();
		for (String entry : this.core.keySet()) {

			/*****************
			 * work on a specific column
			 ***********************/
			List<String> partialres = new ArrayList<String>();
			Iterator<Object> ite = computationPartDrill.iterator();
			if (this.core.keySet().size()>1)
			{while (ite.hasNext()) {
				Object[] obj = (Object[]) ite.next();
				partialres.add(String.valueOf(obj[k]));

			}
			}
			else {
				while (ite.hasNext()) {
					Object obj =  ite.next();
					partialres.add(String.valueOf(obj));

				}
			}

			/************** kl slice computation ***************/
			Set<String> uniqueids = new HashSet<String>(partialres);

			for (String elt : uniqueids) {
				if (SliceDBFreq.containsKey(entry + "###" + elt)) {

					float freqS = (float) Collections.frequency(partialres, elt) / partialres.size();

					float freqD = (float) SliceDBFreq.get(entry + "###" + elt);

					SLiceFreq.put(elt, (float) (freqS * Math.log((float) freqS / freqD)));
				}
			}

			float kl = 0;
			for (Float klelt : SLiceFreq.values()) {
				kl += klelt;
			}
			/****************************/

			MatrixScoreForm mm = new MatrixScoreForm(k, ColumnId, kl);

			COlumnDrillResult.add(mm);
			if (this.max_score < kl) {
				this.max_score = kl;
			}
			k++;
		}
		return COlumnDrillResult;

	}

	// compute kl fct, used by drilldownscore2
	private List<MatrixScoreForm> ComputeKLDrill2(int ColumnId, List computationPartDrill, List SliceDBFreq,
			String RelevantPair) {
		List<String> ranking = new ArrayList<String>(this.core.keySet());
		int k = ranking.indexOf(RelevantPair);
		ArrayList<MatrixScoreForm> COlumnDrillResult = new ArrayList<MatrixScoreForm>();
		Map<String, Float> DrillDWFreq = new HashMap<String, Float>();

		/*****************
		 * work on a specific column
		 ***********************/
		List<String> partialres = new ArrayList<String>(/* computationPartDrill */);
		Iterator<Object> ite = computationPartDrill.iterator();
		Object test = ite.next();
		if (test instanceof Object[]) {

			while (ite.hasNext()) {

				Object[] obj = (Object[]) ite.next();
				partialres.add(String.valueOf(obj[0]).concat(String.valueOf(obj[1])));

			}
		} else {
			while (ite.hasNext()) {

				String obj = String.valueOf(ite.next());

				partialres.add(obj);
			}

		}

		List<String> partialresDB = new ArrayList<String>(/* SliceDBFreq */);
		Iterator<Object> iteDB = SliceDBFreq.iterator();
		Object test1 = iteDB.next();
		if (test1 instanceof Object[]) {

			while (iteDB.hasNext()) {

				Object[] obj = (Object[]) iteDB.next();
				partialresDB.add(String.valueOf(obj[0]).concat(String.valueOf(obj[1])));

			}
		} else {
			while (iteDB.hasNext()) {

				String obj = String.valueOf(iteDB.next());

				partialresDB.add(obj);
			}
		}
		/************** kl slice computation ***************/
		Set<String> uniqueids = new HashSet<String>(partialres);

		for (String elt : uniqueids) {
			if (partialresDB.contains(elt)) {

				float freqS = (float) Collections.frequency(partialres, elt) / partialres.size();

				float freqD = (float) Collections.frequency(partialresDB, elt) / partialresDB.size();

				DrillDWFreq.put(elt, (float) (freqS * Math.log((float) freqS / freqD)));
			}
		}

		float kl = 0;
		for (Float klelt : DrillDWFreq.values()) {
			kl += klelt;
		}
		/****************************/

		MatrixScoreForm mm = new MatrixScoreForm(k, ColumnId, kl);

		COlumnDrillResult.add(mm);

		return COlumnDrillResult;

	}

	/***
	 * take attribute in group by and conact it to all element in select
	 * 
	 * It returns one query which computes frequency in all db the second one
	 * concerns user's query
	 ***/
	private List<QueryForm> PrepareQuery(QueryForm query) {
		boolean found = false;
		String attconcatenation = query.getGroupbyAtt().get(0);
		// List<String> newselection = new ArrayList<String>();
		List<String> newselection = new ArrayList<String>(query.getSelectedAtt());
		if (!attconcatenation.isEmpty()) {
			lp: for (String elt : query.getSelectedAtt()) {
				if (elt.equals(attconcatenation)) {
					found = true;
					break lp;
				}

			}

			if (!found) {
				newselection.add(attconcatenation);
			}
		}

		QueryForm queryresult = new QueryForm(newselection, query.getCondAtt(), query.getTables(),
				query.getJoinTables());

		Map<String, String> emptyconditions = new HashMap<String, String>();
		QueryForm queryDB = new QueryForm(newselection, emptyconditions, query.getTables(), query.getJoinTables());

		ArrayList<QueryForm> result = new ArrayList<QueryForm>();
		result.add(queryDB);
		result.add(queryresult);
		return result;
	}

	// similar to drilldownscore2 but it gets all values of all relevant
	// element, while drilldwscore2 trates kl score of one relevant pair
	private List<MatrixScoreForm> ComputeKLDrill3(int ColumnId, List computationPartDrill, List SliceDBFreq,
			String granularity) {
		int k = 0;
		int indexGranularity = 0;
		if (this.core.keySet().contains(granularity)) {
			List<String> ranking = new ArrayList<String>(this.core.keySet());
			indexGranularity = ranking.indexOf(granularity);
		} else
			indexGranularity = this.core.keySet().size();

		ArrayList<MatrixScoreForm> COlumnDrillResult = new ArrayList<MatrixScoreForm>();

		for (Entry<String, List<String>> importantPair : this.core.entrySet()) {

			/*****************
			 * work on a specific column
			 ***********************/
			Map<String, Float> DrillDWFreq = new HashMap<String, Float>();
			List<String> partialres = new ArrayList<String>();
			Iterator<Object> ite = computationPartDrill.iterator();
			Object test = ite.next();

			/*
			 * while (ite.hasNext()) {
			 * 
			 * Object[] obj = (Object[]) ite.next();
			 */
			for (Object obj1 : computationPartDrill) {
				Object[] obj = (Object[]) obj1;
				partialres.add(String.valueOf(obj[k]).concat(String.valueOf(obj[indexGranularity])));

			}

			List<String> partialresDB = new ArrayList<String>();
			/*
			 * Iterator<Object> iteDB = SliceDBFreq.iterator();
			 * 
			 * 
			 * 
			 * while (iteDB.hasNext()) {
			 * 
			 * Object[] obj = (Object[]) iteDB.next();
			 */
			for (Object obj2 : SliceDBFreq) {
				Object[] obj3 = (Object[]) obj2;
				partialresDB.add(String.valueOf(obj3[k]).concat(String.valueOf(obj3[indexGranularity])));

			}

			/************** kl slice computation ***************/
			Set<String> uniqueids = new HashSet<String>(partialres);

			for (String elt : uniqueids) {
				if (partialresDB.contains(elt)) {

					float freqS = (float) Collections.frequency(partialres, elt) / partialres.size();

					float freqD = (float) Collections.frequency(partialresDB, elt) / partialresDB.size();

					DrillDWFreq.put(elt, (float) (freqS * Math.log((float) freqS / freqD)));
				}
			}

			float kl = 0;
			for (Float klelt : DrillDWFreq.values()) {
				kl += klelt;
			}
			/****************************/

			MatrixScoreForm mm = new MatrixScoreForm(k, ColumnId, kl);

			COlumnDrillResult.add(mm);
			k++;
		}

		return COlumnDrillResult;

	}

	/********************
	 * Drill down query : go down in the granularity
	 ************************/
	public List<MatrixScoreForm> DrillDownScore4V2(QueryForm queryBean, QueryForm originalquery, String cancel,
			String state) {

		Map<String, Float> SLiceFreq = new HashMap<String, Float>();
		List<MatrixScoreForm> COlumnDrillResult = new ArrayList<MatrixScoreForm>();

		QueryForm queryDBdrill = constructDrillDownView(queryBean, originalquery);
		if (queryDBdrill.getTables().size() == 0)
			return COlumnDrillResult;

		//QueryConstructor QueConst = new QueryConstructor(queryDBdrill, "");
		queryBuilder.setQueryBean(queryDBdrill);
	
		//String View_Name = QueConst.findView();

		/*
		 * if (View_Name!=null) {
		 * 
		 * List<String> view= new ArrayList<String>(Arrays.asList(View_Name));
		 * Map<String,String> emptyjoincds= new HashMap<String, String>();
		 * QueryForm queryDBdrill2 = new
		 * QueryForm(queryDBdrill.getSelectedAtt(),queryDBdrill.getCondAtt(),
		 * view,queryDBdrill.getGroupbyAtt(),queryDBdrill.getOrderbyAtt(),
		 * emptyjoincds); queryDBdrill=queryDBdrill2;
		 * 
		 * }
		 */

		List<QueryForm> queriesToUse = TransformAndConcatQuery(queryDBdrill);
		QueryForm queryNewGranularity = queriesToUse.get(1);

		// QueryForm queryNewGran_DB = queriesToUse.get(0);

		QueryForm qq = 	queryBuilder.alias(queryDBdrill);
		List<QueryForm> queriesToUseAlias = TransformAndConcatQuery(qq);
		QueryForm queryNewGran_DB = queriesToUseAlias.get(0);
		String drillDB = "create MATERIALIZED VIEW view_drill as " + queryNewGran_DB.display();

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		session.createSQLQuery(" DROP MATERIALIZED VIEW if exists view_drill ").executeUpdate();
		session.createSQLQuery(drillDB).executeUpdate();
		session.createSQLQuery(" analyze  view_drill ").executeUpdate();

		

		/******** computation of kl part of drill down *************/
		if (!queryNewGranularity.checkDuplicatedTables())

		{
			queryNewGranularity = UpdateQuerywithView(queryNewGranularity, 1);
		}

		else {
			queryNewGranularity = queriesToUseAlias.get(1);
		}

		SQLQuery queryDrillDw = session.createSQLQuery(queryNewGranularity.display());

		List computationPartDrill = queryDrillDw.list();

		
		
		/******** computation of kl part of DB *************/

		SQLQuery queryDB = session.createSQLQuery("select colone,coltwo,colthree from public.\"Kullback_DrillV2\"('{"
				+ String.join(", ", core.keySet()) + "}')");
		queryDB.addScalar("colone", StringType.INSTANCE);
		queryDB.addScalar("coltwo", StringType.INSTANCE);
		queryDB.addScalar("colthree", FloatType.INSTANCE);
		List<Object> DBscores = queryDB.list();
		Map<String, Float> SliceDBFreq = new HashMap<String, Float>();
		Iterator<Object> ite2 = DBscores.iterator();
		while (ite2.hasNext()) {
			Object[] obj2 = (Object[]) ite2.next();

			SliceDBFreq.put(String.valueOf(obj2[0]) + "###" + String.valueOf(obj2[1]),
					Float.parseFloat(String.valueOf(obj2[2])));
		}
		
		
		

		List<MatrixScoreForm> res = ComputeKLDrill(110, computationPartDrill, SliceDBFreq);
		COlumnDrillResult.addAll(res);

		QueryForm q1 = queriesToUse.get(1);
		QueryForm queryDrillSlice = new QueryForm(q1.getSelectedAtt(), queryBean.getCondAtt(), q1.getTables(),
				q1.getJoinTables());
		if (!q1.checkDuplicatedTables())

		{
			queryDrillSlice = UpdateQuerywithView(queryDrillSlice, 2);
		} else {
			q1 = queriesToUseAlias.get(1);
			queryDrillSlice = new QueryForm(q1.getSelectedAtt(), queryBean.getCondAtt(), q1.getTables(),
					q1.getJoinTables());
			Map<String, String> kkO = 	queryBuilder/*QueConst*/.UpdateConds(queryDrillSlice.getCondAtt());
			queryDrillSlice.setCondAtt(kkO);

		}

		SQLQuery queryDrill_Slice_Dw = session.createSQLQuery(queryDrillSlice.display());
		for (String entry : core.keySet()) {
			queryDrillDw.addScalar(entry, StringType.INSTANCE);
		}

		List computationPartDrill_Slice = queryDrill_Slice_Dw.list();
		List<MatrixScoreForm> res2 = ComputeKLDrill(111, computationPartDrill_Slice, SliceDBFreq);
		COlumnDrillResult.addAll(res2);
		/********************************************/

		tx.commit();
		session.close();

		return COlumnDrillResult;

	}

	// simplify the user slice query by using the materialized view constructed
	// for zoom in query
	private QueryForm UpdateQuerywithView(QueryForm query, int i) {

		String Matview = "view_stat" + i;
		List<String> view = new ArrayList<String>(Arrays.asList(Matview));
		Map<String, String> emptyjoincds = new HashMap<String, String>();
		Map<String, String> emptyconds = new HashMap<String, String>();
		QueryForm query2 = new QueryForm(query.getSelectedAtt(), emptyconds, view, query.getGroupbyAtt(),
				query.getOrderbyAtt(), emptyjoincds);

		return query2;
	}

	private String writeJSON(List<MatrixScoreForm> result) {
		GsonBuilder gsonBuilder = new GsonBuilder();

		Gson gson = gsonBuilder.create();
		String resultJson = gson.toJson(result);

		return resultJson;
	}

	// normalize and write json as string
	public String Normalize(List<MatrixScoreForm> zoomInObjs) {
		List<MatrixScoreForm> newlistobj = new ArrayList<MatrixScoreForm>();
		for (MatrixScoreForm matrix : zoomInObjs) {
			// float kk = (float) ((float)(matrix.getValue()-this.min_score) /
			// (this.max_score-this.min_score));
			float kk = (float) (1 - Math.exp(-1 * matrix.getValue()));
			matrix.setValue(kk);
		}
		String result = writeJSON(zoomInObjs);
		return result;
	}

	public List<MatrixScoreForm> RandomScore_Drill() {

		List<MatrixScoreForm> result = new ArrayList<MatrixScoreForm>();
		Integer i = 0;
		for (Entry<String, List<String>> entry : this.core.entrySet()) {
			float rand = randInt(1, 10);

			MatrixScoreForm a = new MatrixScoreForm(i, 110, rand);
			result.add(a);
			float rand2 = randInt(1, 10);

			MatrixScoreForm a2 = new MatrixScoreForm(i, 111, rand2);
			result.add(a2);

			i++;
		}

		return result;

	}

	
	 public final boolean validateDateFormat(final String date) {
	      //  String[] formatStrings = {"MM-dd-yyyy HH:mm:ss"};
	String	 formatStrings = "yyyy-MM-dd HH:mm:ss";
	

			SimpleDateFormat sdf = new SimpleDateFormat(formatStrings );
			sdf.setLenient(false);

			try {

				//if not valid, it will throw ParseException
				Date dateObj = sdf.parse(date);
				System.out.println(dateObj);

			} catch (ParseException e) {

				e.printStackTrace();
				return false;
			}

			return true;
	        
	        
	        
	    }
	
}
