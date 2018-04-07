package prov.act;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.hibernate.Session;

import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import henry.QuerySimilarity.QueryFromDB;

import henry.database.connection.DBConnector;
import prov.CRUD.QueryConstructor;
import prov.CRUD.QueryConstructor_Formula;
import prov.CRUD.QueryConstructor_Soccer;

import prov.vis.Vega;
import prov.vis.VegaBars;
import prov.vis.VegaBubbles;
import prov.vis.VegaPoints;
import prov.vis.VegaVis;
import prov.CRUD.VegaJsonMapping;

import prov.CRUD.VisualizationConstruction;
import prov.chart.VegaChart;
import prov.mybean.DBForm;
import prov.mybean.DisplayForm;
import prov.mybean.FacetForm;
import prov.mybean.QueryForm;
import prov.mybean.QueryInputForm;
import prov.mybean.VisualForm;
import prov.util.HibernateUtil;

public class QueryDecryptionAction extends Action {
	private final static String SUCCESS = "success";

	private final static String FAILURE = "echec";
	// public static DBConnector connector;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession ss = request.getSession();
		DBForm dbobj = (DBForm) ss.getAttribute("DB");

		/**********************************
		 * getting user's info
		 ***************************************/

		QueryInputForm query = (QueryInputForm) form;
		QueryConstructor qc;

		String content = query.getContent();
		String rest;
		String cond = " ";
		String Jcond = "";
		String groupBy = "";
		String grBy = "";

		if (content.isEmpty())

		{
			return mapping.findForward(FAILURE);
		}

		else {
			QueryForm UserQuery = new QueryForm();
			content = content.trim().replaceAll("(\\s)+", "$1");

			/**********************************
			 * start splitting
			 ***************************************/

			String[] ch = content.split(" ");
			if (!ch[0].toLowerCase().contains("select")) {
				return mapping.findForward(FAILURE);
			}
			ch = content.replace("select", "").replace(" > ", ">").replace(" < ", "<").split(" ");

			List<String> atts = parse(ch, "FROM");

			int last = atts.size() - 1;

			String[] ch1 = atts.get(last).trim().replaceAll("(\\s)+", "$1").split("&");

			atts.remove(last);

			UserQuery.setSelectedAtt(atts);

			int i = 0;
			String attSet = "";
			for (String entry1 : atts) {
				i++;
				if (!entry1.isEmpty()) {
					attSet = attSet + entry1;
					if (i < atts.size()) {
						attSet = attSet + " , ";
					}
				}
			}

			if (find(ch1, "WHERE"))

			{
				List<String> tables = parse(ch1, "WHERE");

				last = tables.size() - 1;
				rest = tables.get(last);

				tables.remove(last);
				UserQuery.setTables(tables);
			}

			/**** case of PA queries *****/
			else {

				List<String> tables = parse(ch1, "GROUP");

				last = tables.size() - 1;
				rest = tables.get(last);

				tables.remove(last);
				UserQuery.setTables(tables);

			}

			// the goal here is to search join conditions dependencies
			Map<String, String> joinsClues = SpecialFormat(rest);

			// QueryConstructor qc = new QueryConstructor(UserQuery, rest );
			if (dbobj.getName().equals("soccerDB")) {
				qc = new QueryConstructor_Soccer(UserQuery, rest);
			} else if (dbobj.getName().equals("formulaOne")) {
				qc = new QueryConstructor_Formula(UserQuery, rest);
			} else {
				qc = new QueryConstructor(UserQuery, rest);
			}

			/************ condition computation *************/
			if (rest.length() > 2) {
				if (rest.toLowerCase().contains("group") && rest.toLowerCase().contains("by")) {

					List<String> ggg = parse(rest.split("&"), "GROUP");
					groupBy = ggg.get(ggg.size() - 1);

					ggg.remove(ggg.size() - 1);
					System.out.println("conds are " + String.join("&", ggg));
					rest = String.join("&", ggg);
					Map<String, String> conds = ExtractConds(rest);

					List<Map<String, String>> FinalConds = OrganizeConds(conds);
					UserQuery.setJoinTables(FinalConds.get(0));
					/*** --> condition is added here ***/
					UserQuery.setCondAtt(FinalConds.get(1));
					for (Map.Entry<String, String> entry : FinalConds.get(0).entrySet()) {
						i++;
						cond = cond + entry.getKey() + "=" + entry.getValue();
						if (i < FinalConds.get(0).keySet().size()) {
							cond = cond + " AND ";
						}
					}
					// System.out.println("join conditions " + cond);

					i = 0;

					for (Map.Entry<String, String> entry : FinalConds.get(1).entrySet()) {
						i++;

						if (entry.getKey().contains("$")) {
							Jcond = Jcond + entry.getKey().split("\\$")[1] + entry.getKey().split("\\$")[0]
									+ entry.getValue();
						} else {
							Jcond = Jcond + entry.getKey() + "=" + entry.getValue();
						}
						if (i < FinalConds.get(1).keySet().size()) {
							Jcond = Jcond + " AND ";
						}
					}

				}

				else {
					groupBy = rest;
				}

				if (groupBy.length() > 2) {

					List<String> grp1 = parse(groupBy.split("&"), "ORDER");
					Set<String> grp = new HashSet<String>();
					grp.addAll(grp1);
					grp.remove(grp.size() - 1);
					List<String> grp2 = new ArrayList<String>();
					grp2.addAll(grp);

					/**************** major change here *****************/
					List<String> grp3 = new ArrayList<String>();
					lp: for (String elt : grp2) {
						if (!elt.replaceAll("\\s+", "").isEmpty()) {
							// System.out.println("--- "+ elt);
							grp3.add(elt);
							break lp;
						}
					}
					// grp2.remove(0);
					UserQuery.setGroupbyAtt(/* grp2 */grp3);
					/**********************************/
					i = 0;

					for (String entry1 : /* grp2 */grp3) {
						i++;

						if (!entry1.isEmpty()) {
							if (i < grp.size() - 1) {
								grBy = grBy + entry1 + ", ";
							} else
								grBy = grBy + entry1;
						}
					}

				}

				/***************** user query formalization *******************/
				Map<String, String> jjoinscds = qc.GetJoinConds(joinsClues);
				UserQuery.setJoinTables(jjoinscds);

				/*
				 * <<<<<<<<<<<<<<<<< Visualization
				 * Construction>>>>>>>>>>>>>>>>>>>>>>>>>>
				 */
				VisualizationConstruction vis = new VisualizationConstruction(UserQuery, dbobj);
				List<Vega> visobj = vis.decide();
				List<VisualForm> possibleVis = new ArrayList<VisualForm>();
				int k = 0;
				int qID = DistinctValues.connector.counter.getQ_ID();
				VegaChart chartVis = new VegaChart();
				String chartVis_JSON = "";

				/***************
				 * condition added to support SP queries
				 *************/
				// this.connector = new DBConnector(true);

				QueryFromDB q1 = DistinctValues.connector.QueryFormToQuery(UserQuery);
				DistinctValues.connector.queryToDB(q1);
				VegaJsonMapping vega = new VegaJsonMapping();
				List<DisplayForm> jsonObjs = vega.construct(UserQuery);
				String dataJson = vega.ConvertToJson_Normal(jsonObjs);
				DistinctValues.connector.dataSetToDB(jsonObjs, "table");

				for (Vega VegaSpecification : visobj) {

					chartVis = VegaSpecification.PrintVisrec_2D("#728f99");
					chartVis_JSON = VegaSpecification.buildJs(chartVis);

					if (k == 0) {

						if (qID == 0) {
							DistinctValues.connector.VizToDB(chartVis);
							VisualForm vi = new VisualForm(chartVis_JSON, chartVis,
									VegaSpecification.getClass().toString().replace("class prov.vis.Vega", ""), "true");
							possibleVis.add(vi);
						} else {
							System.out.println("------------recommendation-----------");
							VisualizationConstruction vegaconst = new VisualizationConstruction();
							List<QueryFromDB> comparisonQuery = vegaconst.Rank(DistinctValues.connector, q1);
							// VegaVis vv = new VegaBars(UserQuery);
							List<DisplayForm> tosend = new ArrayList<DisplayForm>();
							for (Entry<String, String> elt : UserQuery.getCondAtt().entrySet()) {
								DisplayForm dd = new DisplayForm(elt.getKey(), elt.getValue());
								tosend.add(dd);
							}
							VegaChart chartRec = VegaSpecification.Recommendation_Viz(q1, comparisonQuery, null,
									"Principal", tosend);

							String jsRec_JSON = VegaSpecification.buildJs(chartRec);
							// System.out.println(jsRec_JSON);
							System.out.println("------------recommendation-----------");
							VisualForm vi = new VisualForm(jsRec_JSON, chartRec,
									VegaSpecification.getClass().toString().replace("class prov.vis.Vega", ""), "true");
							possibleVis.add(vi);
						}

					} else {
						if (k < 3) {
							VisualForm vi = new VisualForm(chartVis_JSON, chartVis,
									VegaSpecification.getClass().toString().replace("class prov.vis.Vega", ""),
									"false");
							possibleVis.add(vi);
						}
					}

					k++;

				}

				if (UserQuery.getGroupbyAtt().size() > 0) {

					/***********************
					 * send query to database/
					 * 
					 */
					String veg = possibleVis.get(0).getJsonresult();
					VegaChart chart = possibleVis.get(0).getVegafile();
					/****************
					 * Sending JSON data to jsp page to be used later in vega
					 * visualization
					 ***************/

					ss.setAttribute("vispossible", possibleVis);
					ss.setAttribute("queryBean", UserQuery);
					ss.setAttribute("datajson", dataJson);
					ss.setAttribute("vegaSpec", chart);
					ss.setAttribute("file", veg);
					ss.setAttribute("dimensions", UserQuery.getGroupbyAtt().get(0));
					List<FacetForm> provenance = new ArrayList<FacetForm>();
					ss.setAttribute("provenance", provenance);
				}

				return mapping.findForward(SUCCESS);
			} else
				return mapping.findForward(FAILURE);
		}

	}

	/*************************
	 * ***************is very bad and need to be maintained
	 * 
	 ***********************************/
	private List<Map<String, String>> OrganizeConds(Map<String, String> conds) {
		List<Map<String, String>> res = new ArrayList<Map<String, String>>();
		Map<String, String> join = new HashMap<String, String>();
		Map<String, String> cd = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : conds.entrySet())

		{

			if (entry.getValue().matches("-?\\d+(\\.\\d+)?") || entry.getValue().contains("'")
					|| entry.getValue().toLowerCase().equals("true")
					|| entry.getValue().toLowerCase().equals("false")) {

				cd.put(entry.getKey(), entry.getValue().replace("'", ""));
			}

			else if (entry.getValue().split(",").length > 1
					&& entry.getValue().split(",")[0].matches("-?\\d+(\\.\\d+)?")
					&& entry.getValue().split(",")[1].matches("-?\\d+(\\.\\d+)?")) {
				cd.put(entry.getKey(), entry.getValue().split(",")[0] + "&" + entry.getValue().split(",")[1]);
			}

			else if (entry.getKey().startsWith("<") || entry.getKey().startsWith(">") || entry.getKey().startsWith("<=") || entry.getKey().startsWith(">=")) {

				/**********/
				cd.put(entry.getKey(), entry.getValue());
				/**********/
			} else {
				String a = entry.getKey();
				String b = entry.getValue();
				if (entry.getKey().contains(".")) {
					a = entry.getKey().split(Pattern.quote("."))[1];

				}
				if (entry.getValue().contains(".")) {
					b = entry.getValue().split(Pattern.quote("."))[1];

				}

				join.put(a, b);
			}

		}

		res.add(join);
		res.add(cd);
		return res;
	}

	private Map<String, String> ExtractConds(String hh) {

		String[] temp = hh.split("&");

		Map<String, String> res = new HashMap<String, String>();
		List<String> couples = new ArrayList<String>();
		List<String> inp = new ArrayList<String>();
		for (String elt : temp) {
			if (!elt.equals("and")) {
				couples.add(elt);
			}

		}

		for (String cp : couples) {

			if (cp.contains(">="))

			{
				inp = Work(cp, ">=");
				res.put(">=$" + inp.get(0), inp.get(1));
			}
			else if (cp.contains("<="))

			{
				inp = Work(cp, "<=");
				res.put("<=$" + inp.get(0), inp.get(1));
			}
			else if (cp.contains("=")) {
				inp = Work(cp, "=");
				res.put(inp.get(0), inp.get(1));
			} else if (cp.contains(">"))

			{
				inp = Work(cp, ">");
				res.put(">$" + inp.get(0), inp.get(1));
			} else if (cp.contains("<"))

			{
				inp = Work(cp, "<");
				res.put("<$" + inp.get(0), inp.get(1));
			}

			else if (cp.contains("$between")) {
				inp = Work(cp, "\\$between");
				res.put("range," + inp.get(0), inp.get(1).substring(1));
			}

		}
		return res;
	}

	private List<String> Work(String cp, String special) {
		// System.out.println(" ### " + cp);

		String[] els = cp.split(special);
		/*
		 * if(els.length>1) {
		 */
		if (els[0].contains(Pattern.quote("."))) {
			String a = els[0].split(Pattern.quote("."))[1];

			els[0] = a;
		}
		if (els[1].contains(Pattern.quote("."))) {
			String b = els[1].split(Pattern.quote("."))[1];

			els[1] = b;
		}
		List<String> elts = new ArrayList<String>(Arrays.asList(els[0], els[1]));
		return elts;
	}

	private List<String> parse(String[] ch, String st) {

		List<String> atts = new ArrayList<String>();
		int k = 0;
		for (String elt : ch) {
			/*
			 * if (st.equals("GROUP")) { System.out.println("### " + elt); }
			 */
			k = k + 1;
			if (elt.toUpperCase().contains(st)) {
				break;

			} else {

				if (elt.contains(".")) {
					atts.add(elt.split(Pattern.quote("."))[1]);
				} else {
					String[] echo = elt.split(",");

					if (echo.length > 1) {
						if (!echo[0].isEmpty()) {
							atts.add(echo[0]);
						}
						atts.add(echo[1]);
					} else {

						if (echo[0].toLowerCase().contains("between")) {
							atts.add(ch[k - 2] + "$" + echo[0] + "," + ch[k] + "," + ch[k + 2]);
						}

						else if (!echo[0].toUpperCase().equals("AS") && !echo[0].isEmpty() && echo[0].length() > 1
								&& !echo[0].equals("by") && echo[0].matches(".*[a-zA-Z]+.*")) {
							atts.add(echo[0]);

						}
					}

				}
			}
		}
		String repl = " ";
		for (int i = k; i <= ch.length - 1; i++) {
			repl = repl + "&" + ch[i];

		}
		// repl=repl.substring(0,repl.length()-1);
		atts.add(repl);
		return atts;
	}

	// search an element in string[]
	private boolean find(String[] ch, String st) {

		List<String> atts = new ArrayList<String>();
		boolean find = false;
		for (String elt : ch) {

			if (elt.toUpperCase().contains(st)) {

				find = true;
				break;
			}

		}

		return find;
	}

	/// take all pairs having "="
	private Map<String, String> SpecialFormat(String rest) {

		String[] temp = rest.split("&");

		Map<String, String> result = new HashMap<String, String>();

		for (String elt : temp) {
			if (elt.contains("=")) {
				String key = elt.split("=")[0];
				String value = elt.split("=")[1];
				if (key.contains("."))
					key = key.split(Pattern.quote("."))[1];
				if (value.contains("."))
					value = value.split(Pattern.quote("."))[1];

				if (!value.matches("-?\\d+(\\.\\d+)?") && !value.startsWith("'")) {
					result.put(key, value);
				}
			}

		}

		return result;
	}

}
