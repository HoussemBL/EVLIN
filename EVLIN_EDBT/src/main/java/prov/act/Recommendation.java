// My code implementation
package prov.act;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Reader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;

import org.supercsv.*;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.AbstractCsvReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.io.ICsvListReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import freemarker.ext.dom.Transform;
import henry.database.connection.DBConnector;
import newTypes.Tuple2;
import prov.chart.ActChart;
import prov.chart.ActionChart;
import prov.chart.AxisChart;
import prov.chart.DataChart;
import prov.chart.Filter;
import prov.chart.LabelAxeChart;
import prov.chart.Labels;
import prov.chart.MarkChart;
import prov.chart.MarkGeneral;
import prov.chart.Marks;
import prov.chart.PropChart;
import prov.chart.Transformation;
import prov.chart.VegaChart;
import prov.dao.*;
import prov.mybean.ColumnMatrixForm;
import prov.mybean.CustomerBean;
import prov.mybean.DBForm;
import prov.mybean.FacetForm;
import prov.mybean.MatrixScoreForm;
import prov.mybean.QueryForm;
import prov.util.HibernateUtil;
import prov.vis.VegaBars;

import java.awt.*;
import prov.CRUD.*;

public class Recommendation extends Action {
	public static DBConnector connector;
	private String CSV_FILENAME = "/Users/houssem/Desktop/provenanceDynamic.csv";
	/*private String CSV_db = "/Users/houssem/Desktop/db3.csv";
		private String PrecomputedFreqs_db = "/Users/houssem/Desktop/freqDb.csv";*/
	private String CSV_db = "/Users/houssem/Desktop/dbs/usflight/sampleBernoulli/denormalized_flightsBer.csv";
	private String PrecomputedFreqs_db = "/Users/houssem/Desktop/dbs/usflight/sampleBernoulli/flights_freq.csv";

	/*********** functional dependencies ***************/
	List<String> Regiolist = Arrays.asList("city", "state", "country", "lat", "longi");
	List<String> Airolist = Arrays.asList("iata", "airport");
	List<String> Carrierlist = Arrays.asList("code", "description");

	List<String> Taillist = Arrays.asList("model", "manufacturer");

	Tuple2<String, List<String>> constraint1 = new Tuple2<String, List<String>>("airports", Regiolist);
	Tuple2<String, List<String>> constraint2 = new Tuple2<String, List<String>>("airports", Airolist);
	Tuple2<String, List<String>> constraint3 = new Tuple2<String, List<String>>("carriers", Carrierlist);
	Tuple2<String, List<String>> constraint4 = new Tuple2<String, List<String>>("tail", Taillist);
	// List<String> Cancellist = Arrays.asList("cancelled", "taxiin", "taxiout",
	// "deptime", "arrtime", "airtime","arrdelay", "actualelapsedtime");
	public List<Tuple2<String, List<String>>> df_rules = Arrays.asList(constraint1, constraint2, constraint3,
			constraint4);

	private static Map<String, String> similarityList = new HashMap<String, String>();

	static {
		similarityList.put("origin", "iata");
		similarityList.put("dest", "iata");
		similarityList.put("uniquecarrier", "code");
		similarityList.put("code", "uniquecarrier");
	}

	/*************
	 * querybean will be the result of user interaction on his initial query
	 *************/
	QueryForm queryBean;

	/*************************************************/

	private final static String SUCCESS = "success";

	private final static String FAILURE = "echec";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession ss = request.getSession();
		DBForm dbobj = (DBForm) ss.getAttribute("DB");
		int i = 0;

		if (dbobj.getName().equals("USflights2")) {
			
			CSV_db = "/Users/houssem/Desktop/dbs/usflight/sampleBernoulli/denormalized_flightsBer.csv";
			PrecomputedFreqs_db = "/Users/houssem/Desktop/dbs/usflight/sampleBernoulli/flights_freq.csv";
		} else if (dbobj.getName().equals("USflightsAll")) {
			CSV_db = "/Users/houssem/Desktop/dbs/usflight/preparedData_10_years/denormalized_usflightsAll.csv";
			PrecomputedFreqs_db = "/Users/houssem/Desktop/dbs/usflight/preparedData_10_years/freqDBflightsAll.csv";
		}

		else if (dbobj.getName().equals("soccerDB")) {

			CSV_db = "/Users/houssem/Desktop/dbs/soccerDB/denormalized_soccerdb.csv";
			PrecomputedFreqs_db = "/Users/houssem/Desktop/dbs/soccerDB/freqDBsoccer.csv";

			// CSV_db =
			// "/Users/houssem/Desktop/dbs/soccerDB/denormalized_soccerdb_correction.csv";
			// PrecomputedFreqs_db =
			// "/Users/houssem/Desktop/dbs/soccerDB/freqDBsoccer_correction.csv";

			Regiolist = Arrays.asList("player_name", "player_fifa_api_id", "player_api_id", "pid");
			Airolist = Arrays.asList( "team_short_name", "team_long_name","team_api_id", "team_fifa_api_id", "tid");
			Carrierlist = Arrays.asList("countryfk_id", "league_name", "lid");
			Taillist = Arrays.asList("name", "cid");

			constraint1 = new Tuple2<String, List<String>>("player", Regiolist);
			constraint2 = new Tuple2<String, List<String>>("team", Airolist);
			constraint3 = new Tuple2<String, List<String>>("league", Carrierlist);
			constraint4 = new Tuple2<String, List<String>>("country", Taillist);
			df_rules = Arrays.asList(constraint1, constraint2, constraint3, constraint4);

			similarityList = new HashMap<String, String>();

			similarityList.put("countryfk_id", "country_id");
			similarityList.put("country_id", "cid");
			similarityList.put("league_id", "lid");
			similarityList.put("home_team_api_id", "team_api_id");
			similarityList.put("away_team_api_id", "team_api_id");

		}

		else if (dbobj.getName().equals("formulaOne")) {

			CSV_db = "/Users/houssem/Desktop/dbs/formula-1-race-data-19502017/denormalized_formula.csv";
			PrecomputedFreqs_db = "/Users/houssem/Desktop/dbs/formula-1-race-data-19502017/freq_formula.csv";

			Regiolist = Arrays.asList("circuit_ref", "circuit_name", "circuit_location", "circuit_lat", "circuit_longi",
					"circuit_id");
			List<String> Regiolist2 = Arrays.asList("race_name", "circuitfk_id", "race_id");
			Carrierlist = Arrays.asList("constructor_ref", "constructor_name", "constructor_id");
			Taillist = Arrays.asList("driver_code", "driver_forename", "driver_surname", "driver_id");

			constraint1 = new Tuple2<String, List<String>>("circuit", Regiolist);
			constraint2 = new Tuple2<String, List<String>>("race", Airolist);
			constraint3 = new Tuple2<String, List<String>>("constructor", Carrierlist);
			// constraint4 = new Tuple2<String, List<String>>("driver",
			// Taillist);
			Tuple2<String, List<String>> constraint5 = new Tuple2<String, List<String>>("circuit", Regiolist2);
			df_rules = Arrays.asList(constraint1, constraint2, constraint3/* , constraint4 */, constraint5);

			similarityList = new HashMap<String, String>();

			similarityList.put("circuitfk_id", "circuit_id");
			similarityList.put("constructorfk_id", "constructor_id");
			similarityList.put("racefk_id", "race_id");
			similarityList.put("driverfk_id", "driver_id");

		}
		long startTime = System.currentTimeMillis();
		VegaChart Inheritedchart;
		QueryForm query;
		String previous = "";

		List<FacetForm> provenance = new ArrayList<FacetForm>();

		/*********************
		 * is it initial recommendation or it comes from other recommend
		 * visualizations?
		 **************************/
		String[] typePreviousviz = request.getParameterValues("type");

		if (typePreviousviz == null) {
			Inheritedchart = (VegaChart) ss.getAttribute("vegaSpec");

			query = (QueryForm) ss.getAttribute("queryBean");
			previous = (String) ss.getAttribute("datajson");

		} else {
			String type = typePreviousviz[0];

			String olddata = (String) ss.getAttribute("datajsonolder");
			ss.setAttribute("datajsonolder", olddata);
			provenance = (List<FacetForm>) ss.getAttribute("provenance" + type);
			ss.setAttribute("provenance", provenance);
			Inheritedchart = (VegaChart) ss.getAttribute("vegaSpec" + type);
			query = (QueryForm) ss.getAttribute("queryBean" + type);
			previous = (String) ss.getAttribute("datajson" + type);

			if (type.equals("Extension") || type.equals("Zoom")) {
				String[] cond = request.getParameterValues("cond");
				String cond1 = cond[0].split(",")[0];
				String val1 = cond[0].split(",")[1];
				if (!val1.contains("other")) {
					Map<String, String> updateconds = query.getCondAtt();
					updateconds.put(cond1, val1);
					query.setCondAtt(updateconds);
				}

			}
		}
		/*********************
		 * is it initial recommendation or it comes from other recommend
		 * visualizations?
		 **************************/

		String chiffre = "";
		String[] cancellation = request.getParameterValues("cancel");
		String attOfInterest = cancellation[0];
		if (attOfInterest.contains("Airline_Id")) {
			attOfInterest = "uniquecarrier";
		}

		String[] ss1 = request.getParameterValues("state");
		String valueOfInterest = ss1[0];

		String[] chif = request.getParameterValues("chiffre");
		if (chif != null)
			chiffre = chif[0];

		/****************** user's query formulation *********************/

		ss.setAttribute("twoDataSrcToFill", null);
		ss.setAttribute("oneDataSrcToFill", null);

		/*********************
		 * search aggregation function inside attributes of queryBean
		 ***************************/

		String agg = FindAGGreg(query.getSelectedAtt());

		/********************
		 * find the exact format of attribute removed from selection clause e.g.
		 * select A2.state ==> attOfInbtereset becomes equal to A2.state
		 ************************/
		lip: for (String selectionElt : query.getSelectedAtt()) {
			if (!agg.equals(selectionElt)) {
				attOfInterest = selectionElt;
				break lip;
			}
		}
		/***************** avoid the case where state=''CA'' ************/
		Map<String, String> conds = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : query.getCondAtt().entrySet()) {
			if (entry.getValue().contains("'")) {
				String k = entry.getValue().replace("'", "");
				if (!entry.getKey().equals(attOfInterest)
						&& !(entry.getKey().equals("code") && attOfInterest.equals("uniquecarrier"))
						&& !(entry.getKey().equals("uniquecarrier") && attOfInterest.equals("code"))) {
					conds.put(entry.getKey(), k);
				}
			} else {
				if (!entry.getKey().equals(attOfInterest)
						&& !(entry.getKey().equals("code") && attOfInterest.equals("uniquecarrier"))
						&& !(entry.getKey().equals("uniquecarrier") && attOfInterest.equals("code"))) {
					conds.put(entry.getKey(), entry.getValue());
				}
			}
		}

		conds.put(attOfInterest, valueOfInterest);

		/*****************************
		 * PROVENANCE QUERY CONSTRUCTION
		 **************************************/
		List<String> atts = new ArrayList(Arrays.asList(agg));
		List<String> groupAtt = new ArrayList();
		Map<String, String> orderBy = new HashMap<String, String>();

		/***
		 * condition below is written to avoid adding user interaction to SP
		 * queries
		 */
		if (query.getSelectedAtt().size() > 1) {
			queryBean = new QueryForm(atts, conds, query.getTables(), groupAtt, orderBy, query.getJoinTables());
		} else {
			queryBean = new QueryForm(atts, query.getCondAtt(), query.getTables(), groupAtt, orderBy,
					query.getJoinTables());
		}

		/**************** collecting all recommended query *****************/
		// System.out.println("old query " +query.display());
		String text = "";
		text = text + "\n   *********************   user interaction   " + attOfInterest + "," + valueOfInterest
				+ "********************* ";
		text = text + "\n   new user query query--->    " + queryBean.display();
		FileWriter pw1 = new FileWriter("/Users/houssem/Documents/workspaceDemo/stats.txt", true);
		pw1.write(text);
		pw1.close();

		Provenance prov = new Provenance(dbobj);
		prov.ConstructCsvV2(queryBean);

		/************** parsing the csv file of provenance ***************/

		Map<String, Integer> provset = CSvRead(CSV_FILENAME);

		/********** compute size of lineage **********/
		Integer lineageSize = SizeCsv(CSV_FILENAME);

		/********************* compute frequency for each candidate ********/
		Map<String, Float> freqlineageset = ComputeFreq(provset, lineageSize);

		/**************** remove foreign keys ***********/
		Map<String, Float> freqlineagesetA = FK(freqlineageset);

		/****************
		 * remove users attribute conditions from recommendation
		 ***********/

		Map<String, Float> freqlineagesetAB = RemoveUserConds(freqlineagesetA, queryBean.getCondAtt().keySet());
		for (Entry<String, Float> elt : freqlineagesetAB.entrySet()) {
			if(elt.getKey().contains("result") ||elt.getKey().contains("goal") )
			System.out.println(elt.getKey()+","+elt.getValue());
		} 

		/*********************************
		 * mean and standard deviation computation
		 **************/
		Float mean = (float) 0;
		for (Entry<String, Float> elt : freqlineagesetAB.entrySet()) {
			mean = mean + elt.getValue();
		}
		mean = (float) mean / freqlineagesetAB.entrySet().size();
		System.out.println(mean);
		Float standard_dev = (float) 0;
		for (Entry<String, Float> elt : freqlineagesetAB.entrySet()) {
			float dev = elt.getValue() - mean;
			double calcul = (double) Math.pow(dev, 2) / freqlineagesetAB.entrySet().size();
			standard_dev = (float) (standard_dev + Math.sqrt(calcul));
		}
		System.out.println(standard_dev);

		double threshold_lineage =0.07/* 0.1*/;
		System.out.println(freqlineagesetAB.entrySet().size());
		if (dbobj.getName().equals("soccerDB")) {
			threshold_lineage = mean * 4;
			if (mean==1)
			{ threshold_lineage = 0.3;}
			// threshold_lineage= (double) standard_dev/10;
			// threshold_lineage=0.01;
		}
		if (dbobj.getName().equals("formulaOne")) {
			threshold_lineage = mean * 6;

		}
		/*
		 * if (dbobj.getName().equals("USflights2")){
		 * 
		 * threshold_lineage=0.12; }
		 */
		Map<String, Float> freqlineageset2 = FilterLineage(freqlineagesetAB, threshold_lineage);

		/************** full block to compute freq db offline ***************/
		/*
		 * if (dbobj.getName().equals("formulaOne")) { Map<String, Integer>
		 * dbset = CSvRead(CSV_db); Integer sizeDb = 23777;
		 * 
		 * 
		 * Map<String, Float> freqdbset2 = ComputeFreq(dbset, sizeDb);
		 * WriteCsv(freqdbset2);}
		 */
		/*
		 * if (dbobj.getName().equals("soccerDB")) { Map<String, Integer> dbset
		 * = CSvRead(CSV_db); Integer sizeDb = 25979;
		 * 
		 * 
		 * Map<String, Float> freqdbset2 = ComputeFreq(dbset, sizeDb);
		 * WriteCsv(freqdbset2);}
		 */

		/*
		 * if (dbobj.getName().equals("USflightsAll")){ Map<String, Integer>
		 * dbset = CSvRead(CSV_db); Integer sizeDb = 192906;
		 * 
		 * 
		 * Map<String, Float> freqdbset2 = ComputeFreq(dbset, sizeDb);
		 * WriteCsv(freqdbset2); }
		 */
		/*
		 * if (dbobj.getName().equals("USflights2")){ Map<String, Integer> dbset
		 * = CSvRead(CSV_db); Integer sizeDb = 358896;
		 * 
		 * 
		 * Map<String, Float> freqdbset2 = ComputeFreq(dbset, sizeDb);
		 * WriteCsv(freqdbset2); }
		 */
		/*********************
		 * full block to compute freq db offline
		 ********/
		Map<String, Float> freqdbset = ReadFreqCsv(PrecomputedFreqs_db);
		Float mean_DB = (float) 0;
		for (Entry<String, Float> elt : freqdbset.entrySet()) {
			mean_DB = mean_DB + elt.getValue();
		}
		mean_DB = (float) mean_DB / freqdbset.entrySet().size();
		System.out.println(mean_DB);
		/********************* Join lineage and database ********/
		Map<String, Float> candidatSet = join(freqdbset, freqlineageset2);
		double threshold_db = 0.7;
		if (dbobj.getName().equals("soccerDB")) {
			threshold_db = 0.4;
		}
		Map<String, Float> recommendSet = FilterLineage(candidatSet, threshold_db);
		// System.out.println("----------------------------------------------------------------");
		// System.out.println("size of recommendation set after compare to DB "
		// + recommendSet.keySet().size());

		/********************* grouping candidates set ************************/

		Map<String, List<String>> attValues = Group(recommendSet);

		/***************************
		 * replacing similar attributes names
		 ********************************/
		Map<String, List<String>> attValues_clean = Resolve_pb_Foreign_keys(attValues);
		/**********************
		 * discard candidates having functional dependencies
		 *************************/
		System.out.println("functional dependencies detection*********");
		attValues_clean = Insert(attValues_clean, attOfInterest, valueOfInterest);
		Map<String, List<String>> attValues2 = DF_Optimized(attValues_clean);
		attValues2 = RemoveUserCds(attValues2, attOfInterest, valueOfInterest);
		/*********************
		 * remove duplicate from candidate set= final set of candidates
		 ************************/
		Map<String, List<String>> GemsSet = Remove(/* attValues */attValues_clean, attValues2);

		/**************************************
		 * -------------------------?????????????????? before grouping i need to
		 * remove user selection from recommended set
		 */
		Map<String, List<String>> GemsSet22 = RemoveUserSelectionV3(GemsSet, attOfInterest, valueOfInterest, query);
		Map<String, List<String>> GemsSet_final = AddOtherVal(GemsSet22);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("***************************************");
		System.out.println("elapsed time for recommendation- " + elapsedTime + "");
		System.out.println("***************************************");

		if (GemsSet_final.size() == 0) {
			return mapping.findForward(FAILURE);
		}

		/****************
		 * make some modification in vega chart of previous web page
		 *******************/
		List<String> condsTOHighlight = new ArrayList<String>(Arrays.asList(valueOfInterest));
		String[] cond = request.getParameterValues("cond");
		if (cond != null) {
			String val1 = cond[0].split(",")[1];
			condsTOHighlight.add(val1);
		}
		VegaChart chartDuplication = new VegaChart(Inheritedchart.getWidth(), Inheritedchart.getHeight(),
				Inheritedchart.getData(), Inheritedchart.getScales(), Inheritedchart.getAxes(),
				Inheritedchart.getMarks(), Inheritedchart.getSignals(), Inheritedchart.getPredicates(),
				Inheritedchart.getLegends());
		VegaChart InheritedchartHighlighted = HighlightBar(chartDuplication, /* valueOfInterest */condsTOHighlight);
		VegaBars template = new VegaBars(queryBean);
		String vegTorenderNow = template.buildJs(InheritedchartHighlighted);

		// -->how to display
		boolean twosets = false;
		lp: for (DataChart dataset : InheritedchartHighlighted.getData()) {

			if (dataset.getName().equals("source")) {
				twosets = true;
				break lp;
			}
		}
		if (twosets) {
			FacetForm fac = new FacetForm("s", "d");
			ss.setAttribute("twoDataSrcToFill", fac);
		}

		else {
			FacetForm fac = new FacetForm("s", "d");
			ss.setAttribute("oneDataSrcToFill", fac);
		}
		/**************************************
		 * computation of Zoom in column matrix
		 *******************************************/

		MatrixConstruction Impactmatrix = new MatrixConstruction(GemsSet_final, dbobj.getName());
		String PairsList = Impactmatrix.construct();

		List<MatrixScoreForm> ZoomInObjs = Impactmatrix.ZoomInScore(queryBean, valueOfInterest);
		/**************************************
		 * computation of Zoom in +slice column matrix
		 *******************************************/

		List<MatrixScoreForm> ZoomIn_SliceObjs = Impactmatrix.ZoomIn_Slice_Score(queryBean);

		/***************************************
		 * Computation of Drill down column in matrix
		 ******************************************/
		ss.setAttribute("colsDrill", null);
		List<MatrixScoreForm> DrillDW_Objs = Impactmatrix.DrillDownScore4V2(queryBean, query, /* cancel */attOfInterest,
				/* state */valueOfInterest);
		// List<MatrixScoreForm> DrillDW_Objs =
		// Impactmatrix.RandomScore_Drill();
		if (DrillDW_Objs.size() > 0) {
			/*
			 * ?????????????????????automatic generation of extension
			 * columns?????????????????????
			 */
			List<ColumnMatrixForm> ColDefinition = new ArrayList<ColumnMatrixForm>();
			ColumnMatrixForm col1 = new ColumnMatrixForm("DrillDown", 1, 110);
			ColumnMatrixForm col2 = new ColumnMatrixForm("DrillDown_Slice", 1, 111);
			ColDefinition.add(col1);
			ColDefinition.add(col2);
			GsonBuilder gsonBuilder = new GsonBuilder();

			Gson gson = gsonBuilder.create();
			String colsDrill = gson.toJson(ColDefinition);

			ss.setAttribute("colsDrill", colsDrill);
			/*
			 * ?????????????????????automatic generation of extension
			 * columns?????????????????????
			 */

		}
		/***************************************
		 * Computation of extension column in matrix
		 ******************************************/

		Map.Entry<String, List<MatrixScoreForm>> entry = Impactmatrix
				.ExtensionScore(queryBean, query, attOfInterest, valueOfInterest, "").entrySet().iterator().next();

		String Columns1 = entry.getKey();
		List<MatrixScoreForm> Extensions_Objs = entry.getValue();

		/***************************************
		 * Computation of slice/ dice column in matrix
		 ******************************************/

		Map.Entry<String, List<MatrixScoreForm>> entry2 = Impactmatrix
				.ExtensionScore(queryBean, query, attOfInterest, valueOfInterest, "slice").entrySet().iterator().next();
		String Columns2 = entry2.getKey();
		List<MatrixScoreForm> Extensions_Objs_Slice = entry2.getValue();

		/*************
		 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<score
		 * normalization>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		 * >>>
		 **************/

		String ColumnZoomInScores = Impactmatrix.Normalize(ZoomInObjs);
		String ColumnZoomIn_Slice_Scores = Impactmatrix.Normalize(ZoomIn_SliceObjs);
		String ColumnExtensionScores = Impactmatrix.Normalize(Extensions_Objs);
		String ColumnExtension_slice_Scores = Impactmatrix.Normalize(Extensions_Objs_Slice);
		String ColumnDrill_Scores = Impactmatrix.Normalize(DrillDW_Objs);

		ss.removeAttribute("conds");
		ss.setAttribute("original", vegTorenderNow);
		ss.setAttribute("queryBean1", queryBean);
		ss.setAttribute("originalquery", query);
		ss.setAttribute("vegaSpec", Inheritedchart);
		ss.setAttribute("valueInterest", valueOfInterest);
		ss.setAttribute("fieldInterest", attOfInterest);
		ss.setAttribute("chiffre", chiffre);
		ss.setAttribute("datajson", previous);

		/*
		 * update dependencies between queries
		 */
		DistinctValues.connector.updateRacinePath();

		ss.setAttribute("recset", PairsList);
		ss.setAttribute("scoresZoom", ColumnZoomInScores);
		ss.setAttribute("scoresZoomSLice", ColumnZoomIn_Slice_Scores);
		ss.setAttribute("scoresExtension", ColumnExtensionScores);
		ss.setAttribute("scoresExtension_Slice", ColumnExtension_slice_Scores);
		ss.setAttribute("scoresDrill", ColumnDrill_Scores);

		ss.setAttribute("colsExtension_Slice", Columns2);
		ss.setAttribute("colsExtension", Columns1);

		return mapping.findForward(SUCCESS);
	}

	// similar to two, it removes user'interaction and attribute in group by
	// user's query
	private Map<String, List<String>> RemoveUserSelectionV3(Map<String, List<String>> gemsSet, String cancel,
			String state, QueryForm userquery) {
		boolean exist = true;
		Map<String, List<String>> result = new HashMap<String, List<String>>();

		for (Map.Entry<String, List<String>> entry : gemsSet.entrySet()) {

			exist = false;
			if (entry.getKey().contains(cancel) || (entry.getKey().equals("code") && cancel.equals("uniquecarrier"))) {
				loop: for (String cst : entry.getValue()) {
					if (cst.contains(state)) {

						exist = true;
						List<String> xx = entry.getValue();
						xx.remove(entry.getValue().indexOf(cst));
						List<String> yy = xx;
						if (yy.size() > 0) {
							result.put(entry.getKey(), xx);
						}
						break loop;
					}

				}
			}

			if (!exist && !entry.getKey().contains(userquery.getGroupbyAtt().get(0))) {
				result.put(entry.getKey(), entry.getValue());
			}

		}

		return result;
	}

	// fct used to remove user interaction from recommendation pair list
	private Map<String, List<String>> RemoveUserSelection2(Map<String, List<String>> gemsSet, String cancel,
			String state) {
		boolean exist = true;
		Map<String, List<String>> result = new HashMap<String, List<String>>();

		for (Map.Entry<String, List<String>> entry : gemsSet.entrySet()) {

			exist = false;
			if (entry.getKey().contains(cancel) || (entry.getKey().equals("code") && cancel.equals("uniquecarrier"))) {
				loop: for (String cst : entry.getValue()) {
					if (cst.contains(state)) {

						exist = true;
						List<String> xx = entry.getValue();
						xx.remove(entry.getValue().indexOf(cst));
						List<String> yy = xx;
						if (yy.size() > 0) {
							result.put(entry.getKey(), xx);
						}
						break loop;
					}

				}
			}

			if (!exist) {
				result.put(entry.getKey(), entry.getValue());
			}

		}

		return result;
	}

	// used to discard recommendations about the same attribute putted in
	// condition of user's initial query
	private Map<String, Float> RemoveUserConds(Map<String, Float> freqset, Set<String> keySet) {

		boolean exist = true;
		Map<String, Float> result = new HashMap<String, Float>();

		for (Map.Entry<String, Float> entry : freqset.entrySet()) {
			// System.out.println( " 099 "+entry.getKey().split(",")[0]);
			exist = false;

			loop: for (String cst : keySet) {
				if (cst.endsWith(entry.getKey().split(",")[0])) {

					exist = true;
					break loop;
				}
				/*
				 * if (cst.equals("code") &&
				 * entry.getKey().split(",")[0].equals("uniquecarrier")) {
				 * 
				 * exist = true; break loop; } if (cst.equals("uniquecarrier")
				 * && entry.getKey().split(",")[0].equals("code")) {
				 * 
				 * exist = true; break loop; }
				 */
				if (!exist) {
					lp2: for (Entry<String, String> sims : similarityList.entrySet()) {
						if (cst.contains(sims.getKey()) && sims.getValue().equals(entry.getKey().split(",")[0])) {

							exist = true;
							break loop;
						} else if (cst.contains(sims.getValue())
								&& sims.getKey().equals(entry.getKey().split(",")[0])) {

							exist = true;
							break lp2;
						}

					}

				}
			}
			if (!exist) {

				result.put(entry.getKey(), entry.getValue());
			}

		}
		return result;
	}

	// to discard foreign keys
	private Map<String, Float> FK(Map<String, Float> freqset) {

		Map<String, Float> result = new HashMap<String, Float>();

		for (Map.Entry<String, Float> entry : freqset.entrySet()) {
			if (!entry.getKey().contains("iata")) {

				result.put(entry.getKey(), entry.getValue());
			}

		}
		return result;

	}

	private Map<String, Float> ReadFreqCsv(String file) throws Exception {
		ICsvMapReader mapReader = null;

		try {
			mapReader = new CsvMapReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);

			// the header columns are used as the keys to the Map
			final String[] header = mapReader.getHeader(true);
			final CellProcessor[] processors = new CellProcessor[] { null, null };
			Map<String, Float> result = new HashMap<String, Float>();
			Map<String, Object> customerMap;
			String key = "";
			String value = "";
			while ((customerMap = mapReader.read(header, processors)) != null) {
				for (Map.Entry<String, Object> entry : customerMap.entrySet()) {
					String inter = entry.getKey() + " " + entry.getValue();
					if (entry.getKey().equals("field")) {
						key = "" + entry.getValue();

					} else {
						value = "" + entry.getValue();

					}
					if (!key.isEmpty() && !value.isEmpty()) {
						result.put(key.replace("(", "").replace(")", ""), Float.valueOf(value));
						key = "";
						value = "";

					}

				}

			}
			return result;
		} finally {
			if (mapReader != null) {
				mapReader.close();
			}
		}
	}

	/***************
	 * get rid of functional dependencies attributes
	 *************/
	private Map<String, List<String>> Remove(Map<String, List<String>> attValues, Map<String, List<String>> DFlist) {

		Iterator<String> iterkey1 = DFlist.keySet().iterator();

		while (iterkey1.hasNext()) {
			String key = iterkey1.next();

			Iterator<String> iterkey = attValues.keySet().iterator();

			while (iterkey.hasNext()) {
				String str = iterkey.next();
				if (key.equals(str)) {
					List<String> result = new ArrayList<String>();

					for (String val : DFlist.get(key)) {
						{
							if (!attValues.get(key).contains(val)) {
								result.add(val);
							}
						}

					}
					attValues.put(key, result);
					result.clear();

				}
			}

		}

		Iterator<String> iter = attValues.keySet().iterator();

		while (iter.hasNext()) {
			String key = iter.next();
			if (attValues.get(key).isEmpty()) {
				iter.remove();
			}

		}
		return attValues;

	}

	/*******************
	 * discard candidates having funct dependencies
	 *****************/
	private Map<String, List<String>> DF_Optimized(Map<String, List<String>> attValues) {

		Map<String, List<String>> result = new HashMap<String, List<String>>();
		List<String> li = new ArrayList<String>(similarityList.keySet());
		for (Map.Entry<String, List<String>> entry : attValues.entrySet()) {
			String att = entry.getKey();
			/*****
			 * special case for equivalence of iata , code tec...
			 **********/

			if (li.contains(att)) {

				att = similarityList.get(att);
			}

			for (Tuple2<String, List<String>> rule : df_rules) {
				if (rule._1.contains(att)) {

					if (rule._1.indexOf(att) > 0)

					{

						for (String nn : rule._1) {

							if (rule._1.indexOf(nn) < rule._1.indexOf(att))

							{
								if (attValues.keySet().contains(nn))

								{
									Map<String, List<String>> partial = new HashMap<String, List<String>>();
									for (String temp : entry.getValue()) {

										partial = exec(nn, att, temp, rule._0);

										breakLoop: for (String per : partial.get(nn)) {
											if (attValues.get(nn).contains(per)) {

												List<String> fi = new ArrayList<String>();

												if (result.keySet().contains(att)) {
													HashSet hs = new HashSet();
													hs.add(temp);
													hs.addAll(result.get(entry.getKey()));
													fi.addAll(hs);

													result.put(entry.getKey(), fi);
												} else {
													fi.add(temp);
													result.put(entry.getKey(), fi);
												}
												break breakLoop;
											}
										}
									}

								}

							}

						}

					}

				}
			}

			/**********************************/

		}
		return result;
	}

	/*******************
	 * discard candidates having funct dependencies
	 *****************/
	private Map<String, List<String>> DF(Map<String, List<String>> attValues) {

		Map<String, List<String>> result = new HashMap<String, List<String>>();
		for (Map.Entry<String, List<String>> entry : attValues.entrySet()) {
			String att = entry.getKey();
			/*****
			 * special case for equivalence of iata , code tec...
			 **********/
			List<String> li = new ArrayList<String>(similarityList.keySet());
			if (li.contains(att)) {

				att = similarityList.get(att);
			}

			if (Airolist.contains(att)) {

				if (Airolist.indexOf(att) > 0)

				{

					for (String nn : Airolist) {

						if (Airolist.indexOf(nn) < Airolist.indexOf(att))

						{
							if (attValues.keySet().contains(nn))

							{
								Map<String, List<String>> partial = new HashMap<String, List<String>>();
								for (String temp : entry.getValue()) {

									partial = exec(nn, att, temp, "Airports");

									breakLoop: for (String per : partial.get(nn)) {
										if (attValues.get(nn).contains(per)) {

											List<String> fi = new ArrayList<String>();

											if (result.keySet().contains(att)) {
												HashSet hs = new HashSet();
												hs.add(temp);
												hs.addAll(result.get(entry.getKey()));
												fi.addAll(hs);

												result.put(entry.getKey(), fi);
											} else {
												fi.add(temp);
												result.put(entry.getKey(), fi);
											}
											break breakLoop;
										}
									}
								}

							}

						}

					}

				}

			}

			else if (Regiolist.contains(att)) {
				if (Regiolist.indexOf(entry.getKey()) > 0)

				{

					for (String nn : Regiolist) {

						if (Regiolist.indexOf(nn) < Regiolist.indexOf(att))

						{
							if (attValues.keySet().contains(nn))

							{
								for (String temp : entry.getValue()) {

									Map<String, List<String>> partial = exec(nn,
											/* entry.getKey() */att, temp, "Airports");
									breakLoop: for (String per : partial.get(nn)) {
										if (attValues.get(nn).contains(per)) {

											List<String> fi = new ArrayList<String>();

											if (result.keySet().contains(entry.getKey())) {
												HashSet hs = new HashSet();
												hs.add(temp);
												hs.addAll(result.get(entry.getKey()));
												fi.addAll(hs);
												result.put(entry.getKey(), fi);
											} else {
												fi.add(temp);
												result.put(entry.getKey(), fi);
											}
											break breakLoop;
										}
									}

								}

							}

						}

					}
				}
			}

			else if (Carrierlist.contains(att)) {
				if (Carrierlist.indexOf(att) > 0)

				{

					for (String nn : Carrierlist) {

						if (Carrierlist.indexOf(nn) < Carrierlist.indexOf(att))

						{
							if (attValues.keySet().contains(nn))

							{
								for (String temp : entry.getValue()) {

									Map<String, List<String>> partial = exec(nn,
											/* entry.getKey() */att, temp, "Carriers");

									breakLoop: for (String per : partial.get(nn)) {
										if (attValues.get(nn).contains(per)) {

											List<String> fi = new ArrayList<String>();

											if (result.keySet().contains(entry.getKey())) {
												HashSet hs = new HashSet();
												hs.add(temp);
												hs.addAll(result.get(entry.getKey()));
												fi.addAll(hs);
												result.put(entry.getKey(), fi);
											} else {
												fi.add(temp);
												result.put(entry.getKey(), fi);
											}
											break breakLoop;
										}
									}

								}

							}

						}

					}
				}
			} else if (Taillist.contains(att)) {
				if (Taillist.indexOf(att) > 0)

				{

					for (String nn : Taillist) {

						if (Taillist.indexOf(nn) < Taillist.indexOf(att))

						{
							if (attValues.keySet().contains(nn))

							{
								for (String temp : entry.getValue()) {

									Map<String, List<String>> partial = exec(nn,
											/* entry.getKey() */att, temp, "Tail");

									breakLoop: for (String per : partial.get(nn)) {
										if (attValues.get(nn).contains(per)) {

											List<String> fi = new ArrayList<String>();

											if (result.keySet().contains(entry.getKey())) {
												HashSet hs = new HashSet();
												hs.add(temp);
												hs.addAll(result.get(entry.getKey()));
												fi.addAll(hs);
												result.put(entry.getKey(), fi);
											} else {
												fi.add(temp);
												result.put(entry.getKey(), fi);
											}
											break breakLoop;
										}
									}

								}

							}

						}

					}
				}
			}
			/**********************************/

		}
		return result;
	}

	/******************* grouping candidats by name of field *****************/
	private Map<String, List<String>> Group(Map<String, Float> recommendSet) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();

		for (Map.Entry<String, Float> entry : recommendSet.entrySet()) {
			String[] inter = entry.getKey().split(",");
			if (result.keySet().contains(inter[0])) {
				List<String> ll = new ArrayList<String>();
				ll.addAll(result.get(inter[0]));
				if (String.join("", ll).contains("[") && String.join("", ll).contains("]")) {
					if (inter[1].contains("[") && inter[1].contains("]")) {
						ll.add(inter[1]);
					}

				} else {
					ll.add(inter[1]);
				}
				result.put(inter[0], ll);

			}

			else if (!entry.getKey().isEmpty()) {

				List<String> newList = new ArrayList<String>();
				// System.out.println( ","+entry.getKey());
				newList.add(inter[1]);
				result.put(inter[0], newList);
			}

		}

		return result;

	}

	private Map<String, Float> join(Map<String, Float> freqdbset, Map<String, Float> freqlineageset2) {
		Map<String, Float> result = new HashMap<String, Float>();
		for (Map.Entry<String, Float> entry : freqlineageset2.entrySet()) {

			if (freqdbset.keySet().contains(entry.getKey())) {
				Float xx = entry.getValue();
				Float xx1 = freqdbset.get(entry.getKey());
				float dissimilarity = (float) Math
						.abs(Math.log((float) entry.getValue() / freqdbset.get(entry.getKey())));
				result.put(entry.getKey(), dissimilarity);
			}

		}
		return result;
	}

	private Map<String, Float> FilterLineage(Map<String, Float> freqset, double d) {
		// double pp = 1.0;
		Map<String, Float> result = new HashMap<String, Float>();

		for (Map.Entry<String, Float> entry : freqset.entrySet()) {
			if (entry.getValue() > d) {
				result.put(entry.getKey(), entry.getValue());
			}

		}
		return result;
	}

	private Map<String, Float> Reduce(Map<String, Float> freqset, double d) {

		Map<String, Float> result = new HashMap<String, Float>();

		for (Map.Entry<String, Float> entry : freqset.entrySet()) {
			if (entry.getValue() < d) {
				result.put(entry.getKey(), entry.getValue());
			}

		}
		return result;
	}

	private Map<String, Float> ComputeFreq(Map<String, Integer> provset, Integer setsize) {
		// TODO Auto-generated method stub
		Map<String, Float> result = new HashMap<String, Float>();

		for (Map.Entry<String, Integer> entry : provset.entrySet()) {

			result.put(entry.getKey(), (float) entry.getValue() / setsize);

		}

		return result;
	}

	private Integer SizeCsv(String CSV_FILENAME2) throws IOException {

		ICsvMapReader mapReader = null;
		Integer i = 0;
		try {
			mapReader = new CsvMapReader(new FileReader(CSV_FILENAME2), CsvPreference.STANDARD_PREFERENCE);

			// the header columns are used as the keys to the Map
			final String[] header = mapReader.getHeader(true);

			CellProcessor[] processors = new CellProcessor[header.length];
			for (int k = 0; k < header.length - 1; k++) {
				processors[k] = null;
			}
			Map<String, Integer> result = new HashMap<String, Integer>();
			Map<String, Object> customerMap;

			while ((customerMap = mapReader.read(header, processors)) != null) {
				i++;

			}

			return i;
		} finally {
			if (mapReader != null) {
				mapReader.close();
			}
		}
	}

	private Map<String, Integer> CSvRead(String CSV_FILENAME2) throws IOException {
		// TODO Auto-generated method stub
		ICsvMapReader mapReader = null;
		try {
			mapReader = new CsvMapReader(new FileReader(CSV_FILENAME2), CsvPreference.STANDARD_PREFERENCE);

			// the header columns are used as the keys to the Map
			final String[] header = mapReader.getHeader(true);

			CellProcessor[] processors = new CellProcessor[header.length];
			for (int i = 0; i < header.length - 1; i++) {
				processors[i] = null;
			}

			Map<String, Integer> result = new HashMap<String, Integer>();
			Map<String, Object> customerMap;
			while ((customerMap = mapReader.read(header, processors)) != null) {
				for (Map.Entry<String, Object> entry : customerMap.entrySet()) {
					String title = "";
					String distance = "";
					String inter = entry.getKey();
					String[] inter1 = inter.split("_");
					String[] inter2 = inter.split("#");
					// if (inter1.length == 4) {
					if (inter2.length == 2) {
						if (entry.getValue() == null) {
							title = inter2[0] + ",null";
						} else {
							title = inter2[0] + "," + entry.getValue();

						}

						if (result.keySet().contains(title)) {
							result.put(title, result.get(title) + 1);
						}

						else {
							result.put(title, 1);
						}

					} else if (inter1.length > 3) {
						if (!inter1[3].equals("code") && !inter1[3].equals("tailnum")) {
							if (entry.getValue() == null) {
								title = inter1[3] + ",null";
							} else {
								// title = inter1[3] + "," + entry.getValue();

								for (int i = 3; i < inter1.length; i++) {
									if (inter1[i] != null && inter1[i] != " " && inter1[i] != ""
											&& !inter1[i].isEmpty()) {
										title = title + inter1[i] + "_";
									}
								}
								title = title.substring(0, title.length() - 1) + "," + entry.getValue();

								/*
								 * if (inter1[3].contains("distance")) { if
								 * (Integer.parseInt("" + entry.getValue()) <=
								 * 500) { distance = inter1[3] + ",[0-500]"; }
								 * else if (Integer.parseInt("" +
								 * entry.getValue()) <= 1000 &&
								 * Integer.parseInt("" + entry.getValue()) >
								 * 500) { distance = inter1[3] + ",[500-1000]";
								 * } else if (Integer.parseInt("" +
								 * entry.getValue()) <= 2000 &&
								 * Integer.parseInt("" + entry.getValue()) >
								 * 1000) { distance = inter1[3] +
								 * ",[1000-2000]"; } else if
								 * (Integer.parseInt("" + entry.getValue()) >
								 * 2000 && Integer.parseInt("" +
								 * entry.getValue()) <= 3000) { distance =
								 * inter1[3] + ",[2000-3000]"; } else if
								 * (Integer.parseInt("" + entry.getValue()) >
								 * 3000 && Integer.parseInt("" +
								 * entry.getValue()) <= 4000) { distance =
								 * inter1[3] + ",[3000-4000]"; } else if
								 * (Integer.parseInt("" + entry.getValue()) >
								 * 4000) { distance = inter1[3] + ",[4000-0]"; }
								 * 
								 * } else if (inter1[3].equals("deptime")) { if
								 * (Integer.parseInt("" + entry.getValue()) <=
								 * 400) { distance = inter1[3] + ",[0-400]"; }
								 * else if (Integer.parseInt("" +
								 * entry.getValue()) <= 800 &&
								 * Integer.parseInt("" + entry.getValue()) >
								 * 400) { distance = inter1[3] + ",[400-800]"; }
								 * else if (Integer.parseInt("" +
								 * entry.getValue()) <= 1200 &&
								 * Integer.parseInt("" + entry.getValue()) >
								 * 800) { distance = inter1[3] + ",[800-1200]";
								 * } else if (Integer.parseInt("" +
								 * entry.getValue()) > 1200 &&
								 * Integer.parseInt("" + entry.getValue()) <=
								 * 1600) { distance = inter1[3] +
								 * ",[1200-1600]"; } else if
								 * (Integer.parseInt("" + entry.getValue()) >
								 * 1600 && Integer.parseInt("" +
								 * entry.getValue()) <= 2000) { distance =
								 * inter1[3] + ",[1600-2000]"; } else if
								 * (Integer.parseInt("" + entry.getValue()) >
								 * 2000) { distance = inter1[3] +
								 * ",[2000-2400]"; }
								 * 
								 * } else if (inter1[3].equals("arrtime")) { if
								 * (Integer.parseInt("" + entry.getValue()) <=
								 * 400) { distance = inter1[3] + ",[0-400]"; }
								 * else if (Integer.parseInt("" +
								 * entry.getValue()) <= 800 &&
								 * Integer.parseInt("" + entry.getValue()) >
								 * 400) { distance = inter1[3] + ",[400-800]"; }
								 * else if (Integer.parseInt("" +
								 * entry.getValue()) <= 1200 &&
								 * Integer.parseInt("" + entry.getValue()) >
								 * 800) { distance = inter1[3] + ",[800-1200]";
								 * } else if (Integer.parseInt("" +
								 * entry.getValue()) > 1200 &&
								 * Integer.parseInt("" + entry.getValue()) <=
								 * 1600) { distance = inter1[3] +
								 * ",[1200-1600]"; } else if
								 * (Integer.parseInt("" + entry.getValue()) >
								 * 1600 && Integer.parseInt("" +
								 * entry.getValue()) <= 2000) { distance =
								 * inter1[3] + ",[1600-2000]"; } else if
								 * (Integer.parseInt("" + entry.getValue()) >
								 * 2000) { distance = inter1[3] +
								 * ",[2000-2400]"; }
								 * 
								 * }
								 */
							}
							if (result.keySet().contains(title)) {
								result.put(title, result.get(title) + 1);
							}

							else {
								result.put(title, 1);
							}
							/*
							 * if (distance.length() > 3) { if
							 * (result.keySet().contains(distance)) {
							 * result.put(distance, result.get(distance) + 1); }
							 * 
							 * else result.put(distance, 1); }
							 */

						}
					}

					else {
						if (!inter.equals("code") && !inter.equals("tailnum")) {
							if (entry.getValue() == null) {
								title = inter + ",null";
							} else {
								title = inter + "," + entry.getValue();
								if (inter.contains("distance")) {
									if (Integer.parseInt("" + entry.getValue()) < 500) {
										distance = inter + ",[0-500]";
									} else if (Integer.parseInt("" + entry.getValue()) < 1000
											&& Integer.parseInt("" + entry.getValue()) > 500) {
										distance = inter + ",[500-1000]";
									} else if (Integer.parseInt("" + entry.getValue()) < 2000
											&& Integer.parseInt("" + entry.getValue()) > 1000) {
										distance = inter + ",[1000-2000]";
									} else if (Integer.parseInt("" + entry.getValue()) > 2000
											&& Integer.parseInt("" + entry.getValue()) < 3000) {
										distance = inter + ",[2000-3000]";
									} else if (Integer.parseInt("" + entry.getValue()) > 3000
											&& Integer.parseInt("" + entry.getValue()) < 4000) {
										distance = inter + ",[3000-4000]";
									} else if (Integer.parseInt("" + entry.getValue()) > 4000) {
										distance = inter + ",[4000,0]";
									}

								}

								else if (inter.equals("deptime")) {
									if (Integer.parseInt("" + entry.getValue()) <= 400) {
										distance = inter + ",[0-400]";
									} else if (Integer.parseInt("" + entry.getValue()) <= 800
											&& Integer.parseInt("" + entry.getValue()) > 400) {
										distance = inter + ",[400-800]";
									} else if (Integer.parseInt("" + entry.getValue()) <= 1200
											&& Integer.parseInt("" + entry.getValue()) > 800) {
										distance = inter + ",[800-1200]";
									} else if (Integer.parseInt("" + entry.getValue()) > 1200
											&& Integer.parseInt("" + entry.getValue()) <= 1600) {
										distance = inter + ",[1200-1600]";
									} else if (Integer.parseInt("" + entry.getValue()) > 1600
											&& Integer.parseInt("" + entry.getValue()) <= 2000) {
										distance = inter + ",[1600-2000]";
									} else if (Integer.parseInt("" + entry.getValue()) > 2000) {
										distance = inter + ",[2000-2400]";
									}

								}

								if (inter.equals("arrtime")) {
									if (Integer.parseInt("" + entry.getValue()) <= 400) {
										distance = inter + ",[0-400]";
									} else if (Integer.parseInt("" + entry.getValue()) <= 800
											&& Integer.parseInt("" + entry.getValue()) > 400) {
										distance = inter + ",[400-800]";
									} else if (Integer.parseInt("" + entry.getValue()) <= 1200
											&& Integer.parseInt("" + entry.getValue()) > 800) {
										distance = inter + ",[800-1200]";
									} else if (Integer.parseInt("" + entry.getValue()) > 1200
											&& Integer.parseInt("" + entry.getValue()) <= 1600) {
										distance = inter + ",[1200-1600]";
									} else if (Integer.parseInt("" + entry.getValue()) > 1600
											&& Integer.parseInt("" + entry.getValue()) <= 2000) {
										distance = inter + ",[1600-2000]";
									} else if (Integer.parseInt("" + entry.getValue()) > 2000) {
										distance = inter + ",[2000-2400]";
									}

								}
							}
							if (result.keySet().contains(title)) {
								result.put(title, result.get(title) + 1);
							}

							else {
								result.put(title, 1);
							}
							if (result.keySet().contains(distance)) {
								result.put(distance, result.get(distance) + 1);
							}

							else {
								result.put(distance, 1);
							}
						}
					}
				}

			}

			return result;
		} finally {
			if (mapReader != null) {
				mapReader.close();
			}
		}
	}

	/******************* JDBC connection *******************/

	/*****************
	 * provenance computation using JDBC
	 **************************/

	private Map<String, List<String>> exec(String nn, String key, String temp, String db) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		String query2 = "Select distinct CAST ( " + nn + " AS varchar) from " + db + " where " + key + "='"
				+ temp.replace("'", "") + "'";
		// System.out.println(query2);
		List<String> iniQuery = (List<String>) session.createSQLQuery(query2).list();
		Map<String, List<String>> result = new HashMap<String, List<String>>();

		result.put(nn, iniQuery);

		session.close();
		return result;

	}

	private void WriteCsv(Map<String, Float> freqdbset) throws Exception {
		final List<CustomerBean> customers = new ArrayList<CustomerBean>();
		for (Map.Entry<String, Float> entry : freqdbset.entrySet()) {

			CustomerBean a = new CustomerBean("(" + entry.getKey() + ")", entry.getValue());
			customers.add(a);

		}

		final CellProcessor[] proces = new CellProcessor[] { new NotNull(), // firstName
				new NotNull() };
		ICsvBeanWriter beanWriter = null;
		try {
			beanWriter = new CsvBeanWriter(new FileWriter(PrecomputedFreqs_db), CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the bean values to each
			// column (names must match)
			final String[] header = new String[] { "field", "val" };

			// write the header
			beanWriter.writeHeader(header);

			// write the beans
			for (final CustomerBean customer : customers) {
				beanWriter.write(customer, header, proces);
			}

		} finally {
			if (beanWriter != null) {
				beanWriter.close();
			}
		}

	}

	private String FindAGGreg(List<String> selectedAtt) {
		for (String entry : selectedAtt) {
			if (entry.contains("(") && entry.contains(")"))
				return entry;
		}

		return selectedAtt.get(0);
	}

	private Map<String, List<String>> AddOtherVal(Map<String, List<String>> gemsSet) throws IOException {

		Map<String, List<String>> tempo = new LinkedHashMap<String, List<String>>(gemsSet);
		Map<String, List<String>> result = new HashMap<String, List<String>>();

		Set keySet = tempo.keySet();
		// convert it to an array
		Object[] keyArray = keySet.toArray();

		for (int i = keyArray.length - 1; i >= 0; i--) {
			String key = (String) keyArray[i];

			List<String> rr = tempo.get(key);
			Integer k = CSvSearchVals(CSV_FILENAME, key);

			if (k > 1 && rr.size() < k) {
				rr.add("others");
			}
			result.put(key, rr);

		}
		return result;

	}

	// return number of possible values of attribute inside lineage
	private Integer CSvSearchVals(String CSV_FILENAME2, String att) throws IOException {
		// TODO Auto-generated method stub
		ICsvMapReader mapReader = null;
		try {
			mapReader = new CsvMapReader(new FileReader(CSV_FILENAME2), CsvPreference.STANDARD_PREFERENCE);

			// the header columns are used as the keys to the Map
			final String[] header = mapReader.getHeader(true);

			CellProcessor[] processors = new CellProcessor[header.length];
			for (int i = 0; i < header.length - 1; i++) {
				processors[i] = null;
			}

			List<String> result = new ArrayList<String>();

			Map<String, Object> customerMap;
			while ((customerMap = mapReader.read(header, processors)) != null) {
				for (Map.Entry<String, Object> entry : customerMap.entrySet()) {
					String inter = entry.getKey();
					String[] inter1 = inter.split("_");
					if (inter1.length == 4) {
						if (inter1[3].equals(att))

						{
							if (!result.contains("" + entry.getValue()))
								result.add("" + entry.getValue());
						}

					} else {

						if (inter.equals(att))

						{
							if (!result.contains("" + entry.getValue()))
								result.add("" + entry.getValue());
						}

					}

				}

			}

			return result.size();
		} finally {
			if (mapReader != null) {
				mapReader.close();
			}
		}
	}

	private VegaChart HighlightBar(VegaChart ch, List<String> condsTOHighlight) {
		Map<String, String> fromwhichdata = new HashMap<String, String>();

		String typeMark = "rect";
		VegaChart chart = new VegaChart(ch.getWidth(), ch.getHeight(), ch.getData(), ch.getScales(), ch.getAxes(),
				ch.getMarks(), ch.getSignals(), ch.getPredicates(), ch.getLegends());
		chart.setWidth(600);
		chart.setHeight(500);
		/***************************
		 * reduction of x axis font
		 ***************************************/
		/********************* axis construction *************************/
		LabelAxeChart fill = new LabelAxeChart("black");
		LabelAxeChart angle = new LabelAxeChart(90);
		LabelAxeChart dx = new LabelAxeChart(20);
		LabelAxeChart font = new LabelAxeChart(10);
		Labels lab = new Labels(fill, angle, dx, font);

		Map<String, Labels> prNew = new HashMap<String, Labels>();
		prNew.put("labels", lab);

		LabelAxeChart fillTitle = new LabelAxeChart("black");
		LabelAxeChart fontTitle = new LabelAxeChart(17);
		Labels labTitle = new Labels(fillTitle, fontTitle);
		prNew.put("title", labTitle);

		List<AxisChart> oldaxis = chart.getAxes();
		oldaxis.get(0).setProperties(prNew);
		/*****************************
		 * mark used to highlight user's selection
		 ****************************************/
		ActChart ac5 = new ActChart("#fc9272");
		ActionChart act2 = new ActionChart(ac5);

		PropChart propnew = new PropChart();
		List<MarkGeneral> marks = chart.getMarks();

		for (MarkGeneral m2 : marks) {

			if (m2 instanceof MarkChart) {
				MarkChart m = (MarkChart) m2;
				if (m.getType().equals("rect") || m.getType().equals("symbol") || m.getType().equals("line")) {
					propnew = m.getProperties();
					ActionChart enterprop = propnew.getEnter();
					ActionChart hoverprp = propnew.getHover();
					propnew = new PropChart(enterprop, act2, hoverprp);
					fromwhichdata = m.getFrom();
					typeMark = m.getType();
				}
			}

			else if (m2 instanceof MarkGeneral) {
				MarkGeneral m = (MarkGeneral) m2;
				if (m.getType().equals("group")) {
					marks = m.getMarks();
					for (MarkGeneral m3 : marks) {

						if (m2 instanceof MarkGeneral) {
							MarkGeneral m4 = (MarkGeneral) m3;
							if (m4.getType().equals("rect") || m4.getType().equals("symbol")
									/*|| m4.getType().equals("line")*/) {
								propnew = m4.getProperties();
								ActionChart enterprop = propnew.getEnter();
								ActionChart hoverprp = propnew.getHover();
								propnew = new PropChart(enterprop, act2, hoverprp);
								//fromwhichdata = m.getFrom();
								typeMark = m4.getType();
							}
						}

					}
				}
			}
		}
		Map<String, String> from = new HashMap<String, String>();
		from.put("data", "filter");
		MarkChart mark1 = new MarkChart(typeMark, from, propnew);

		List<MarkGeneral> newMarks = new ArrayList<MarkGeneral>();
		newMarks.addAll(chart.getMarks());
		newMarks.add(mark1);

		List<Transformation> tts = new ArrayList<Transformation>();

		/***************
		 * we need now all transformations done for other data
		 ***********/
		// which dataset we have used
		if (fromwhichdata.size() > 0) {
			List<String> datasetusedforBarMark = new ArrayList<String>(fromwhichdata.keySet());
			String nameofthisdataSet = fromwhichdata.get(datasetusedforBarMark.get(0));

			for (DataChart dataset : chart.getData()) {
				if (dataset.getName().equals(nameofthisdataSet)) {
					tts.addAll(dataset.getTransform());

				}

			}

		}
		/*************** filter data ***********/
		Filter ff;
		if (condsTOHighlight.size() > 1) {
			ff = new Filter("filter", "(datum.axeX == '" + condsTOHighlight.get(0) + "' && datum.axeX2 == '"
					+ condsTOHighlight.get(1) + "')");
		} else {
			ff = new Filter("filter", "(datum.axeX == '" + condsTOHighlight.get(0) + "')");
		}
		List<Transformation> tt = new ArrayList<Transformation>(/*
																 * Arrays.asList
																 * (ff)
																 */);
		tt.addAll(tts);
		tt.add(ff);
		DataChart da = new DataChart("filter", "table", tt);
		List<DataChart> data = new ArrayList<DataChart>();
		data.addAll(chart.getData());
		data.add(da);

		VegaChart chart2 = new VegaChart(700, 500, data, ch.getScales(), ch.getAxes(), newMarks, ch.getSignals(),
				ch.getPredicates(), ch.getLegends());

		return chart2;
	}

	private Map<String, List<String>> Resolve_pb_Foreign_keys(Map<String, List<String>> attValues) {
		HashMap<String, List<String>> Result = new HashMap<String, List<String>>();
		List<String> li = new ArrayList<String>(similarityList.keySet());
		for (Map.Entry<String, List<String>> entry : attValues.entrySet()) {
			String att = entry.getKey();
			if (li.contains(att) && attValues.containsKey(similarityList.get(att))) {
				att = similarityList.get(att);
			}
			Result.put(att, entry.getValue());
		}

		return Result;
	}

	// insert user interaction in list of candidats to get rid of its functional
	// dependencies ,later we apply remove function
	private Map<String, List<String>> Insert(Map<String, List<String>> attValues_clean, String attOfInterest,
			String valueOfInterest) {
		// TODO Auto-generated method stub
		for (Map.Entry<String, List<String>> entry : attValues_clean.entrySet()) {
			if (entry.getKey().equals(attOfInterest)) {

				if (!entry.getValue().contains(valueOfInterest)) {
					entry.getValue().add(valueOfInterest);
				}

				return attValues_clean;
			}

		}
		List<String> updateList = new ArrayList<String>(Arrays.asList(valueOfInterest));
		attValues_clean.put(attOfInterest, updateList);
		return attValues_clean;
	}

	// remove user's interaction from candidates list
	private Map<String, List<String>> RemoveUserCds(Map<String, List<String>> attValues_clean, String attOfInterest,
			String valueOfInterest) {
		// TODO Auto-generated method stub
		lp: for (Map.Entry<String, List<String>> entry : attValues_clean.entrySet()) {
			if (entry.getKey().equals(attOfInterest)) {
				List<String> updateList = new ArrayList<String>();
				for (String elt : entry.getValue()) {
					if (!elt.equals(valueOfInterest)) {
						updateList.add(elt);
					}
				}
				String key = entry.getKey();
				attValues_clean.remove(entry.getKey());
				attValues_clean.put(key, updateList);
				break lp;
			}
		}
		return attValues_clean;
	}
}
