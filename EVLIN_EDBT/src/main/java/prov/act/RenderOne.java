package prov.act;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.opensymphony.xwork2.inject.util.Strings;

import henry.QuerySimilarity.Condition;
import henry.QuerySimilarity.QueryFromDB;
import henry.QuerySimilarity.VisualizationFromDB;
import henry.algorithm.RecommendationAlgorithm;
import henry.database.connection.DBConnector;
import javassist.expr.NewArray;
import prov.CRUD.QueryConstructor;

import prov.CRUD.VegaJsonMapping;

import prov.CRUD.VisualRecommendation;
import prov.CRUD.VisualizationConstruction;
import prov.chart.ActChart;
import prov.chart.ActionChart;
import prov.chart.AxisChart;
import prov.chart.DataChart;
import prov.chart.Filter;
import prov.chart.LabelAxeChart;
import prov.chart.Labels;
import prov.chart.LegendChart;
import prov.chart.MarkChart;
import prov.chart.MarkGeneral;
import prov.chart.PredicatChart;
import prov.chart.PropChart;
import prov.chart.ScaleChart;
import prov.chart.SignalChart;
import prov.chart.Transformation;
import prov.chart.VegaChart;
import prov.mybean.DBForm;
import prov.mybean.DisplayForm;
import prov.mybean.FacetForm;
import prov.mybean.QueryForm;
import prov.mybean.VisualForm;
import prov.vis.Vega;
import prov.vis.VegaBars;
import prov.vis.VegaVis;

public class RenderOne extends Action {

	private final static String SUCCESS = "success";

	private final static String FAILURE = "echec";
	public static DBConnector connector;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
		/******** initialization######## */
		HttpSession ss = request.getSession();
		DBForm dbobj = (DBForm) ss.getAttribute("DB");
		
		
		// ss.removeAttribute("datajson");
		ss.setAttribute("rec1", null);
		ss.setAttribute("rec1_Slice", null);
		ss.setAttribute("rec2", null);
		ss.setAttribute("rec2full", null);
		ss.setAttribute("rec2_Slice", null);
		ss.setAttribute("rec3", null);
		ss.setAttribute("rec3_Slice", null);

		// DBConnector conn = (DBConnector) ss.getAttribute("connector");

		/******** end initialization######## */

		String info = "";
		String[] checked = request.getParameterValues("vis");
		String[] res = checked[0].replace("[", "").replace("]", "").replace(",others", "").split(",");

		String res2 = res[1].replace("[", "");
		res2 = res2.replace("]", "");
		res2 = res2.replace(",others", "");

		Object ss1 = ss.getAttribute("valueInterest");
		String webVal = ss1.toString();
		Object ss2 = ss.getAttribute("fieldInterest");
		String webField = ss2.toString();
		Object ss3 = ss.getAttribute("chiffre");

		if (res.length > 2) {

			lp: for (int k = 0; k < res.length; k++) {
				String test = res[res.length-1];
				
				//	System.out.println("val: "+test+","+ validateDateFormat(test));
					if (validateDateFormat(test)){
						info = checked[0].replace("[", "").replace("]", "").replace(",others", "")+",";
						break lp;
					}
					else if (!res[0].equals("model")&& res[k].contains("-") && res[k].split("-")[0].matches("-?\\d+(\\.\\d+)?")
						&& res[k].split("-")[1].matches("-?\\d+(\\.\\d+)?")) {
					info = info + res[k].split("-")[0] + "&" + res[k].split("-")[1] + ",";
				} else
					info = info + res[k] + ",";
			}
			info = info.substring(0, info.length() - 1);

		} else {
			String test = res[res.length-1];
			if (validateDateFormat(test)){
				info = checked[0].replace("[", "").replace("]", "").replace(",others", "")+",";
				
			}
			else if (res2.contains("-") && res2.split("-")[0].matches("-?\\d+(\\.\\d+)?")
					&& res2.split("-")[1].matches("-?\\d+(\\.\\d+)?")) {
				info = res[0] + "," + res2.split("-")[0] + "&" + res2.split("-")[1];
			} else
				info = res[0] + "," + res2;
		}

		ss.setAttribute("vis", info);
		/********** get the full trace ***********/
		List<FacetForm> passationProv = (List<FacetForm>) ss.getAttribute("provenance");
		List<FacetForm> provenance = new ArrayList<FacetForm>();
		provenance.addAll(passationProv);

		int jj = 0;
		String importantvals = "";
		for (String hh : info.split(",")) {
			if (jj > 0) {
				importantvals = importantvals + hh + ",";
			}
			jj++;
		}
		FacetForm FF1 = new FacetForm(webField, webVal);
		FacetForm FF2 = new FacetForm(info.split(",")[0],
				/* checked[0] */ importantvals.substring(0, importantvals.length() - 1));
				/********** construction of a full trace ***********/

		/********************
		 * temporary action of user before going further after this interaction
		 ***************************/

		List<FacetForm> provenanceTempo = new ArrayList<FacetForm>();
		provenanceTempo.add(FF1);
		provenanceTempo.add(FF2);
		ss.setAttribute("temporary", provenanceTempo);
		/*************************************/

		String[] param2 = request.getParameterValues("type");
		int typeRec = Integer.parseInt(param2[0].toString());

		QueryForm queryBean = (QueryForm) ss.getAttribute("queryBean1");
		QueryForm queryOriginal = (QueryForm) ss.getAttribute("originalquery");

		String previous = (String) ss.getAttribute("datajson");
		ss.setAttribute("datajson", previous);
		ss.setAttribute("datajsonolder", previous);
		
		
		VisualRecommendation vis = new VisualRecommendation(98, info, queryBean, webVal, webField,dbobj);
		VegaJsonMapping vegaToJson = new VegaJsonMapping();
		
	
		

		if (typeRec == 98) {
			int i = 0;
			VegaChart chart = new VegaChart();
			String jsRecForDetails = "";
			String jsRec = "";
			String colorMapping_json = null;
			String colorMapping_for_All_json = null;
			List<QueryForm> zoomqueries = vis.zoomIn();
			String oldatt = zoomqueries.get(1).getGroupbyAtt().get(0);
			Map<List<List<DisplayForm>>, List<Vega>> vizParams = vis.VisZoomIn(zoomqueries);

			/****************
			 * Sending JSON data to jsp page to be used later in vega
			 * visualization
			 ***************/
			List<Entry<List<List<DisplayForm>>, List<Vega>>> aa = new ArrayList<Map.Entry<List<List<DisplayForm>>, List<Vega>>>(
					vizParams.entrySet());
			List<DisplayForm> principleSet = aa.get(0).getKey().get(0);
			List<Vega> chartVega = aa.get(0).getValue();
			String dataJs0 = vegaToJson.ConvertToJsonV5(principleSet);
			String dataJs_DetailsOther = vegaToJson.ConvertToJsonV5(aa.get(0).getKey().get(1));
			List<VisualForm> possibleVis = new ArrayList<VisualForm>();
			List<DisplayForm> colorRanges = null;

			for (Vega vv : chartVega) {
				if (i == 0) {
					/**************** send query to db *****************/
					QueryFromDB q1 = DistinctValues.connector.QueryFormToQuery(zoomqueries.get(3));
					DistinctValues.connector.queryToDB(q1);
					DistinctValues.connector.dataSetToDB(principleSet, "table");
					DistinctValues.connector.updateQueryPath();

					System.out.println("------------recommendation-----------");
					VisualizationConstruction vegaconst = new VisualizationConstruction();
					List<QueryFromDB> comparisonQuery = vegaconst.Rank(DistinctValues.connector, q1);
				/*****/	
					
					chart = vv.Recommendation_Viz(q1, comparisonQuery, info.split(",")[0], "Zoom", principleSet);
					jsRec = vv.buildJs(chart);
					/* &&&&&&&propagate generated colors for others &&&& */
					lpdata: for (DataChart DS : chart.getData()) {
						if (DS.getName().equals("mapping")) {
							colorRanges = DS.getValues();
							break lpdata;
						}
					}

					// --> il ya une anomalie dans la fct ci dessous
					List<DisplayForm> SetOfAllObjs = aa.get(0).getKey().get(1);
					List<DisplayForm> colorRangesForAll = vv.Construct_ScaleColorData_AxeX2_Given_Input(colorRanges,
							SetOfAllObjs);
					colorMapping_json = vegaToJson.ConvertToJsonV5(colorRanges);
					colorMapping_for_All_json = vegaToJson.ConvertToJsonV5(colorRangesForAll);
					// for others
					List<DataChart> ds = new ArrayList<DataChart>();
					for (DataChart dataset : chart.getData()) {
						if (!dataset.getName().equals("mapping")) {
							ds.add(dataset);
						}
					}
					DataChart src_map = new DataChart("mapping", colorRangesForAll);
					ds.add(src_map);
					VegaChart xx_others = new VegaChart(chart.getWidth(), chart.getHeight(), ds, chart.getScales(),
							chart.getAxes(), chart.getMarks(), chart.getSignals(), chart.getPredicates(),
							chart.getLegends());
					jsRecForDetails = vv.buildJs(xx_others);

					System.out.println("-----------------------");
					VisualForm vi = new VisualForm(jsRec, chart,
							vv.getClass().toString().replace("class prov.vis.Vega", ""), "true");
					possibleVis.add(vi);

				} else {
					VegaChart chart2 = vv.PrintVisrecZoomIn(info.split(",")[0], oldatt, "Zoom");
					String jsZoomIn2 = vv.buildJs(chart2);

					VisualForm vi = new VisualForm(jsZoomIn2, chart2,
							vv.getClass().toString().replace("class prov.vis.Vega", ""), "false");
					possibleVis.add(vi);
				}
				i++;
			}

			/***
			 * %%%%%%%%%%%%%%%%%parameteres to send to
			 * jsp%%%%%%%%%%%%%%%%%%%%%%%
			 */

			List<String> recset = new ArrayList<String>(
					Arrays.asList(dataJs0, dataJs_DetailsOther, colorMapping_json, colorMapping_for_All_json));
			List<String> files = new ArrayList<String>(Arrays.asList( jsRec, jsRecForDetails));
			/***** */

			//
			FacetForm fac = new FacetForm("s", "d");
			ss.setAttribute("rec1", fac);
			//
			/****************
			 * variables needed for next session
			 ****************/
			QueryForm currentquery = zoomqueries.get(2);

			FacetForm FF3 = new FacetForm("operator", "zoom in");
			provenance.add(FF1);
			provenance.add(FF2);
			provenance.add(FF3);

			ss.setAttribute("provenanceZoom", provenance);
			ss.setAttribute("vegaSpecZoom", chart);
			ss.setAttribute("datajsonZoom", dataJs0);
			ss.setAttribute("queryBeanZoom", currentquery);
			/***************/
			ss.setAttribute("vispossible", possibleVis);
			ss.setAttribute("file", files);
			ss.setAttribute("recset", recset);

		}

		else if (typeRec == 99) {
			VegaChart chart =new VegaChart();
			List<QueryForm> zoomSlicequeries = vis.ZoomIn_Slice();
			Map<List<List<DisplayForm>>, List<Vega>> vizParams = vis.VisZoomIn_Slice(zoomSlicequeries);

			/****************
			 * Sending JSON data to jsp page to be used later in vega
			 * visualization
			 ***************/

			List<Entry<List<List<DisplayForm>>, List<Vega>>> aa = new ArrayList<Map.Entry<List<List<DisplayForm>>, List<Vega>>>(
					vizParams.entrySet());
			List<Vega> visobj = aa.get(0).getValue();
			String dataJs0 = vegaToJson.ConvertToJson_Normal(aa.get(0).getKey().get(0));
			String dataJsForConsistency = vegaToJson.ConvertToJson_Normal(aa.get(0).getKey().get(1));

			List<VisualForm> possibleVis = new ArrayList<VisualForm>();
			
			int k = 0;

			for (Vega visualrender : visobj) {

			

				if (k == 0) {

					/**************** send query to db *****************/
					QueryFromDB q1 = DistinctValues.connector.QueryFormToQuery(zoomSlicequeries.get(2));
					DistinctValues.connector.queryToDB(q1);
					DistinctValues.connector.dataSetToDB(/*aa.get(0).getKey().get(0)*/aa.get(0).getKey().get(1), "table");
					DistinctValues.connector.updateQueryPath();

					System.out.println("----------recommendation-------------");
					VisualizationConstruction vegaconst = new VisualizationConstruction();
					List<QueryFromDB> comparisonQuery = vegaconst.Rank(DistinctValues.connector, q1);
					/*****/	Documentation(comparisonQuery,zoomSlicequeries.get(2));
					DisplayForm dd = new DisplayForm(webField, webVal);
					List<DisplayForm> tosend = new ArrayList<DisplayForm>();
					tosend.add(dd);
					chart = visualrender.Recommendation_Viz(q1, comparisonQuery, info.split(",")[0],
							"ZoomIn_Slice", tosend);
					String jsRec = visualrender.buildJs(chart);
					System.out.println("-----------------------");

					VisualForm vi = new VisualForm(jsRec, chart,
							visualrender.getClass().toString().replace("class prov.vis.Vega", ""), "true");
					possibleVis.add(vi);

				} else {
					VegaChart chartVis = visualrender.PrintVisrec_ZoomInSlice("#bdbdbd");
					String chartVis_JSON = visualrender.buildJs(chartVis);
					VisualForm vi = new VisualForm(chartVis_JSON, chartVis,
							visualrender.getClass().toString().replace("class prov.vis.Vega", ""), "false");
					possibleVis.add(vi);
				}

				k++;

			}

		

			List<String> recset = new ArrayList<String>(Arrays.asList(dataJs0, dataJsForConsistency));

			//
			FacetForm fac = new FacetForm("s", "d");
			ss.setAttribute("rec1_Slice", fac);
			//
			/**************** for next session ****************/
			QueryForm currentquery = zoomSlicequeries.get(2);

			FacetForm FF3 = new FacetForm("operator", "zoom in/slice");
			provenance.add(FF1);
			provenance.add(FF2);
			provenance.add(FF3);

			ss.setAttribute("provenanceZoomIn_Slice", provenance);
			ss.setAttribute("vegaSpecZoomIn_Slice", /*chart3*/chart);
			ss.setAttribute("datajsonZoomIn_Slice", dataJsForConsistency);
			ss.setAttribute("queryBeanZoomIn_Slice", currentquery);

			ss.setAttribute("vispossible", possibleVis);

			/***************/
			// ss.setAttribute("file", files);
			ss.setAttribute("recset", recset);

		}

		else if (typeRec == 100 || typeRec == 102 || typeRec == 104) {

			List<DisplayForm> colorRangesPair = new ArrayList<DisplayForm>();
			List<List<DisplayForm>> QueriesResultToView = new ArrayList<List<DisplayForm>>();
			List<QueryForm> Queries = new ArrayList<QueryForm>();
			VegaJsonMapping serialization = new VegaJsonMapping();
			int id = (typeRec - 100) / 2;

			Map<List<QueryForm>, List<List<DisplayForm>>> computedresult = vis.VisExtension(id);
			for (Entry<List<QueryForm>, List<List<DisplayForm>>> kk : computedresult.entrySet()) {
				QueriesResultToView.addAll(kk.getValue());
				Queries.addAll(kk.getKey());

			}

			QueryForm queryToDoNext = Queries.get(6);
			System.out.println("@@@@@@@@>>> " + queryToDoNext.display());
			/****************
			 * Sending JSON data to jsp page to be used later in vega
			 * visualization
			 ***************/

			String dataJs0 = serialization.ConvertToJsonV5(QueriesResultToView.get(0));
			String dataJs0WithoutPair2 = serialization.ConvertToJsonV5(QueriesResultToView.get(1));
			String Details_dataJs0 = serialization.ConvertToJsonV5(QueriesResultToView.get(2));
			String Details_dataJs0WithoutPair2 = serialization.ConvertToJsonV5(QueriesResultToView.get(3));
			String dataForConsistency = serialization.ConvertToJsonV5(QueriesResultToView.get(4));

			// --->store query in database
			QueryFromDB q1 = DistinctValues.connector.QueryFormToQuery(Queries.get(7));
			DistinctValues.connector.queryToDB(q1);
			DistinctValues.connector.dataSetToDB(QueriesResultToView.get(0), "table");
			DistinctValues.connector.updateQueryPath();

			System.out.println("------------recommendation-----------");
			VisualizationConstruction vegaconst = new VisualizationConstruction();
			List<QueryFromDB> comparisonQuery = vegaconst.Rank(DistinctValues.connector, q1);
			/*****/	Documentation(comparisonQuery,Queries.get(7));
		    /**______________Recommendation of visualization________**/
			/******************** voyager principle ********************/
			VisualizationConstruction vegconst = new VisualizationConstruction(Queries.get(7),dbobj);
			List<Vega> visobj = vegconst.decide_3D();
			Vega vv_recommmended =		visobj.get(0);
			 /**______________End of Recommendation of visualization________**/
			VegaVis vv = new VegaBars(Queries.get(8));
				
			VegaChart chartRec = /*vv*/vv_recommmended.Recommendation_Viz(q1, comparisonQuery, queryToDoNext.getGroupbyAtt().get(0) , "Extension",
					QueriesResultToView.get(0));
			String jsRec = vv.buildJs(chartRec);
			// System.out.println("----------- "+jsRec);
			System.out.println("------------recommendation-----------");

			/* &&&&&&&propagate generated colors for others &&&& */
			lpdata: for (DataChart DS : chartRec.getData()) {
				if (DS.getName().equals("mapping")) {
					colorRangesPair.addAll(DS.getValues());
					break lpdata;
				}
			}


			
			
			List<DisplayForm> colorRangesPairWIthDetails = vv
					.Construct_ScaleColorData_AxeX2_Given_Input(colorRangesPair, QueriesResultToView.get(/*3*/2));
			List<DisplayForm> colorRangesPairWithoutPair = vv
					.Construct_ScaleColorData_AxeX2_Given_Input(colorRangesPair, QueriesResultToView.get(1));
			List<DisplayForm> colorRangesPairWithPairDetails = vv
					.Construct_ScaleColorData_AxeX2_Given_Input(colorRangesPairWithoutPair, QueriesResultToView.get(/*2*/3));
			String Colors1 = "";
			String Colors2 = serialization.ConvertToJsonV5(colorRangesPairWIthDetails);
			String Colors3 = serialization.ConvertToJsonV5(colorRangesPairWithoutPair);
			String Colors4 = serialization.ConvertToJsonV5(colorRangesPairWithPairDetails);

			// for others
			List<DataChart> ds = new ArrayList<DataChart>();
			for (DataChart dataset : chartRec.getData()) {
				if (!dataset.getName().equals("mapping")) {
					ds.add(dataset);
				}
			}
			DataChart src_map = new DataChart("mapping");
			ds.add(src_map);
			VegaChart xx_others = new VegaChart(chartRec.getWidth(), chartRec.getHeight(), ds, chartRec.getScales(),
					chartRec.getAxes(), chartRec.getMarks(), chartRec.getSignals(), chartRec.getPredicates(),
					chartRec.getLegends());
			String jsRecForRemainings = vv.buildJs(xx_others);

			String oldatt = vis.findOld();
			VegaChart chartReverse = vv.PrintVisReverseExTension(oldatt, "Extension");
			VegaChart chartBubble = vv.PrintVisExTensionBubble(oldatt, "Extension");
			String jsExtReverse = vv.buildJs(chartReverse);
			String jsExtBubble = vv.buildJs(chartBubble);

			/*************** data to send to JSP *************/
			List<String> files = new ArrayList<String>(Arrays.asList(jsRecForRemainings,
					jsExtReverse, jsExtBubble, jsRec));
			List<String> recset = new ArrayList<String>(Arrays.asList(dataJs0, dataJs0WithoutPair2, Details_dataJs0,
					Details_dataJs0WithoutPair2, Colors1, Colors2, Colors3, Colors4));

			FacetForm fac = new FacetForm("s", "d");
			ss.setAttribute("rec2", fac);

			FacetForm FF3 = new FacetForm("operator", "Extension");
			provenance.add(FF1);
			provenance.add(FF2);
			provenance.add(FF3);

		/*	QueryForm currentquery = Queries.get(4);
			System.out.println("@@@@@@@@>>> " + currentquery.display());*/
			
			
			ss.setAttribute("provenanceExtension", provenance);
			ss.setAttribute("vegaSpecExtension", chartRec);
			ss.setAttribute("datajsonExtension", dataJs0);
			ss.setAttribute("queryBeanExtension", queryToDoNext);

			ss.setAttribute("file", files);
			ss.setAttribute("recset", recset);

		}

		else if (typeRec == 101 || typeRec == 103 || typeRec == 105) {

			int id = (typeRec - 101) / 2;

			Map<List<List<DisplayForm>>, List<Vega>> vizParams = vis.VisExtension_Slice(id);
			/****************
			 * Sending JSON data to jsp page to be used later in vega
			 * visualization
			 ***************/
			List<Entry<List<List<DisplayForm>>, List<Vega>>> aa = new ArrayList<Map.Entry<List<List<DisplayForm>>, List<Vega>>>(
					vizParams.entrySet());
			VegaBars visobj = (VegaBars) aa.get(0).getValue().get(0);
			List<DisplayForm> jsonObjs = aa.get(0).getKey().get(0);

			String dataJs0 = vegaToJson.ConvertToJson_Normal(jsonObjs);

			// --->store query in database
			QueryFromDB q1 = DistinctValues.connector.QueryFormToQuery(visobj.getQuery());
			DistinctValues.connector.queryToDB(q1);
			DistinctValues.connector.updateQueryPath();
			DistinctValues.connector.dataSetToDB(jsonObjs, "table");

			System.out.println("----------recommendation-------------");
			VisualizationConstruction vegaconst = new VisualizationConstruction();
			List<QueryFromDB> comparisonQuery = vegaconst.Rank(DistinctValues.connector, q1);
			/*****/	Documentation(comparisonQuery,visobj.getQuery());
			DisplayForm dd = new DisplayForm(webField, webVal);
			List<DisplayForm> tosend = new ArrayList<DisplayForm>();
			tosend.add(dd);
			VegaChart chartRec = visobj.Recommendation_Viz(q1, comparisonQuery, info.split(",")[0],
					"Extension_Slice", tosend);
			String jsRec = visobj.buildJs(chartRec);
			
			System.out.println("-----------------------");

			/******** data to send to jsp **********/
			List<String> recset = new ArrayList<String>(Arrays.asList(dataJs0));
			List<String> files = new ArrayList<String>(Arrays.asList(/* chart */jsRec));

			//
			FacetForm fac = new FacetForm("s", "d");
			ss.setAttribute("rec2_Slice", fac);
			//

			FacetForm FF3 = new FacetForm("operator", "Extension_Slice");
			provenance.add(FF1);
			provenance.add(FF2);
			provenance.add(FF3);

			ss.setAttribute("provenanceExtension_Slice", provenance);
			ss.setAttribute("vegaSpecExtension_Slice", chartRec);
			ss.setAttribute("datajsonExtension_Slice", dataJs0);
			ss.setAttribute("queryBeanExtension_Slice", visobj.getQuery());

			ss.setAttribute("file", files);
			ss.setAttribute("recset", recset);

		}

		else if (typeRec == 110) {

			Map<List<DisplayForm>, List<QueryForm>> vizParams = vis.VisDrillDown(queryOriginal);

			List<Entry<List<DisplayForm>, List<QueryForm>>> record = new ArrayList<Map.Entry<List<DisplayForm>, List<QueryForm>>>(
					vizParams.entrySet());
			List<QueryForm> zoomqueries = record.get(0).getValue();
			List<DisplayForm> jsonObjs = record.get(0).getKey();
			QueryForm drillquery = zoomqueries.get(0);
			QueryForm drillqueryComplement = zoomqueries.get(1);
			QueryForm queryDrillDB = zoomqueries.get(2);
			QueryForm queryToPerformNext = zoomqueries.get(3);

			//VegaVis template1 = new VegaBars(drillquery);
			//VegaChart chartDrill = template1.PrintVisrecSlice_Drill(info.split(",")[0],drillqueryComplement.getGroupbyAtt().get(0), "Slice");
			//String jsZoomIn = template1.buildJs(chartDrill);
			String dataJs0 = vegaToJson.ConvertToJsonV5(jsonObjs);
	

			// --->store query in database
			QueryFromDB q1 = DistinctValues.connector.QueryFormToQuery(queryDrillDB);
			DistinctValues.connector.queryToDB(q1);
			DistinctValues.connector.updateQueryPath();
			DistinctValues.connector.dataSetToDB(jsonObjs, "table");

			
			System.out.println("----------recommendation-------------");
			VisualizationConstruction vegaconst = new VisualizationConstruction();
			List<QueryFromDB> comparisonQuery = vegaconst.Rank(DistinctValues.connector, q1);
			/*****/	Documentation(comparisonQuery,queryDrillDB);
			VegaVis visRecObj = new VegaBars(drillquery);
			VegaChart chartRec = visRecObj.Recommendation_Viz(q1, comparisonQuery, info.split(",")[0],
					"Slice",jsonObjs);
			String jsRec = visRecObj.buildJs(chartRec);
			// System.out.println(jsRec);
			System.out.println("-----------------------");
			
			
			/****************
			 * Sending JSON data to jsp page to be used later in vega
			 * visualization
			 ***************/
			List<String> recset = new ArrayList<String>(Arrays.asList(dataJs0/*, scaleColorInfo*/));
			List<String> files = new ArrayList<String>(Arrays.asList(/*jsZoomIn*/jsRec));

			//
			FacetForm fac = new FacetForm("s", "d");
			ss.setAttribute("rec3", fac);
			//

			FacetForm FF3 = new FacetForm("operator", "Extension_Slice");
			provenance.add(FF1);
			provenance.add(FF2);
			provenance.add(FF3);

			ss.setAttribute("provenanceSlice", provenance);
			ss.setAttribute("vegaSpecSlice", /*template1*/chartRec);
			ss.setAttribute("datajsonSlice", dataJs0);
			ss.setAttribute("queryBeanSlice", queryToPerformNext);

			ss.setAttribute("file", files);
			ss.setAttribute("recset", recset);

		}

		else if (typeRec == 111) {
			List<DisplayForm> colorRanges= new ArrayList<DisplayForm>();
			Map<List<QueryForm>, List<DisplayForm>> vizParams = vis.VisDrillDown_Slice(queryOriginal);
			Set<Entry<List<QueryForm>, List<DisplayForm>>> aa1 = vizParams.entrySet();
			Iterator<Entry<List<QueryForm>, List<DisplayForm>>> ite = aa1.iterator();
			Entry<List<QueryForm>, List<DisplayForm>> aa = ite.next();

			List<QueryForm> queries = aa.getKey();
			List<DisplayForm> objs = aa.getValue();
			QueryForm secondPartOfDrillSliceQuery = queries.get(1);
			QueryForm queryNext = queries.get(2);
			QueryForm queryDB = queries.get(3);
			String oldatt = secondPartOfDrillSliceQuery.getGroupbyAtt().get(0);


			// --->store query in database
			QueryFromDB q1 = DistinctValues.connector.QueryFormToQuery(queryDB);
			DistinctValues.connector.queryToDB(q1);
			DistinctValues.connector.updateQueryPath();
			DistinctValues.connector.dataSetToDB(objs, "table");

		
			VegaBars templateVega = new VegaBars(queryNext); 
			String dataJs0 = vegaToJson.ConvertToJsonV5(objs);

			  /**______________Recommendation of visualization________**/
			/******************** voyager principle ********************/
			VisualizationConstruction vegconst = new VisualizationConstruction(secondPartOfDrillSliceQuery,dbobj);
			List<Vega> visobj = vegconst.decide();
			System.out.println("----------recommendation-------------");
			VisualizationConstruction vegaconst = new VisualizationConstruction();
			List<QueryFromDB> comparisonQuery = vegaconst.Rank(DistinctValues.connector, q1);
			/*****/	Documentation(comparisonQuery,queryDB);
			VegaChart chartRec = templateVega.Recommendation_Viz(q1, comparisonQuery, info.split(",")[0],
					"Drill_Slice",objs);
			String jsRec = templateVega.buildJs(chartRec);
			// System.out.println(jsRec);
			System.out.println("-----------------------");
			
			
			
			
			int k = 0;
			List<VisualForm> possibleVis = new ArrayList<VisualForm>();
			

			for (Vega visualspec : visobj) {

			

				if (k == 0) {

					System.out.println("----------recommendation-------------");
					 chartRec = visualspec.Recommendation_Viz(q1, comparisonQuery, info.split(",")[0],
							"Drill_Slice",objs);
					 jsRec = visualspec.buildJs(chartRec);
					System.out.println("-----------------------");

					VisualForm vi = new VisualForm(jsRec, chartRec,
							visualspec.getClass().toString().replace("class prov.vis.Vega", ""), "true");
					possibleVis.add(vi);
					
					lpdata: for (DataChart DS : chartRec.getData()) {
						if (DS.getName().equals("mapping")) {
							 colorRanges = DS.getValues();
							break lpdata;
						}
					}

				} else {
					VegaChart chartVis = visualspec.PrintVisrecZoomIn(/*webField*/info.split(",")[0],oldatt,"Drill_Slice");
					for (DataChart dataset : chartVis.getData()) {
						if (dataset.getName().equals("mapping")) {
							dataset.setValues(colorRanges);
						}
					}
					String chartVis_JSON = visualspec.buildJs(chartVis);
					VisualForm vi = new VisualForm(chartVis_JSON, chartVis,
							visualspec.getClass().toString().replace("class prov.vis.Vega", ""), "false");
					possibleVis.add(vi);
				}

				k++;

			}
			
			
		
			

			/****************
			 * Sending JSON data to jsp page to be used later in vega
			 * visualization
			 ***************/
			
			List<String> recset = new ArrayList<String>(Arrays.asList(dataJs0/*, colorMapping_json*/));

			List<String> files = new ArrayList<String>(Arrays.asList(/*jsschema*/jsRec));
			//
			FacetForm fac = new FacetForm("s", "d");
			ss.setAttribute("rec3_Slice", fac);
			//
			FacetForm FF3 = new FacetForm("operator", "Slice");
			provenance.add(FF1);
			provenance.add(FF2);
			provenance.add(FF3);
			
			ss.setAttribute("provenanceDrill_Slice", provenance);
			ss.setAttribute("vegaSpecDrill_Slice", chartRec);
			ss.setAttribute("datajsonDrill_Slice", dataJs0);
			ss.setAttribute("queryBeanDrill_Slice", queryNext);

			ss.setAttribute("file", files);
			ss.setAttribute("recset", recset);
			ss.setAttribute("vispossible", possibleVis);

		}


		return mapping.findForward("success");
	}

	private void Documentation(List<QueryFromDB> comparisonQuery, QueryForm queryForm) throws IOException {
		// TODO Auto-generated method stub
		FileWriter pw1 = new FileWriter("/Users/houssem/Documents/workspaceDemo/stats.txt", true); 
		int rank=0;
		String text ="\n @@@@@@@ recommended query @@@@@ \n";
		text= text+ queryForm.display();
		 text =text+"----\n";
		for (QueryFromDB q : comparisonQuery) {
			 text = text+"\n"
			 		+ "->  query rank  " + rank + " : ";
			text = text + " select " + String.join(",", q.getSelect()) + " from "
					+ String.join(",", q.getFrom()) + " where ";
			for (Condition x: q.getCondition())
				{
				text =text+ x.getDimension() + x.getOperator() + x.getValue()+" , ";
			};
			text=text+" score: "+q.getSimilarity()+"\n";
			text = text + "----\n";

		rank++;	
		}
		text= text+ " @@@@@@@ end recommendation process @@@@@ \n";
		pw1.write(text);
		pw1.close();
	}

	
	 public final boolean validateDateFormat(final String date) {
	      //  String[] formatStrings = {"MM-dd-yyyy HH:mm:ss"};
	String formatStrings = "yyyy-MM-dd HH:mm:ss";
	

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
