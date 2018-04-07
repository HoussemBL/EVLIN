package prov.vis;

import java.lang.reflect.Field;
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
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import javax.print.attribute.standard.PrinterLocation;

import org.hibernate.Query;
import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import henry.QuerySimilarity.Condition;
import henry.QuerySimilarity.QueryFromDB;
import henry.algorithm.RecommendationAlgorithm;
import henry.database.AxisTable;
import henry.database.AxisUsageTable;
import henry.database.ChannelTable;
import henry.database.ChannelUsageTable;
import henry.database.DatasetTable;
import henry.database.MarkTable;
import henry.database.ScaleTable;
import henry.database.VizTable;
import henry.database.connection.DBConnector;
import henry.database.connection.vizDBUtil;
import prov.CRUD.VegaJsonMapping;
import prov.act.DistinctValues;
import prov.chart.ActChart;
import prov.chart.ActionChart;
import prov.chart.ActionChartScatter;
import prov.chart.ActionChartText;
import prov.chart.Aggregate;
import prov.chart.AxisChart;
import prov.chart.DataChart;
import prov.chart.DomainChart;
import prov.chart.LabelAxeChart;
import prov.chart.Labels;
import prov.chart.LegendChart;
import prov.chart.MarkChart;
import prov.chart.PredicatChart;
import prov.chart.PropChart;
import prov.chart.PropLegend;
import prov.chart.ScaleChart;
import prov.chart.Signal2;
import prov.chart.SignalChart;
import prov.chart.Stack;
import prov.chart.Stream;
import prov.chart.Transformation;
import prov.chart.VegaChart;
import prov.mybean.DisplayForm;
import prov.mybean.QueryForm;
import prov.mybean.VisualizationBean;
import prov.chart.MarkGeneral;

public abstract class VegaVis implements Vega {
	QueryForm query;

	List<String> colors = new ArrayList<String>(
			Arrays.asList(/* "#000000", "#FFFF00", */"steelblue", /*"tale",*/ "#7E5686", "#A5AAD9", /*"#19EACE",*/ "#DBDFAC",
					"#F8A13F", "#BA3C3D", "#1CE6FF", "#FF34FF", "#FF4A46", "#008941", /*"#006FA6",*/ "#A30059", "#353339",
					"#78AFA1", "#FEB2C6", "#75797C", "#837393", "#943A4D", "#B5F4FF", "#D2DCD5", "#9556BD", "#6A714A",
					/*"#001325",*/ "#02525F", "#0AA3F7", "#E98176", "#DBD5DD", "#5EBCD1", "#3D4F44", "#7E6405", "#02684E",
					"#962B75", "#FFDBE5", "#7A4900", "#0000A6", "#63FFAC", "#B79762", "#004D43", "#8FB0FF", "#997D87",
					"#5A0007", "#809693", /*"#FEFFE6",*/ "#1B4400", "#4FC601", "#3B5DFF", "#4A3B53", "#FF2F80", "#61615A",
					"#BA0900", "#6B7900", "#00C2A0", "#FFAA92", "#FF90C9", "#B903AA",
					"#D16100", /* "#DDEFFF", "#000035", */
					"#7B4F4B", "#A1C299", "#300018", "#0AA6D8", "#013349", "#00846F", "#372101", "#FFB500", "#C2FFED",
					"#A079BF", "#CC0744", "#C0B9B2", "#ef6548", "#99d8c9",

					/*"#34362D",*/ "#B4A8BD", "#00A6AA", "#452C2C", "#636375", "#A3C8C9", "#FF913F", "#938A81", "#575329",
					"#00FECF", "#B05B6F", "#8CD0FF", "#3B9700", "#04F757", "#C8A1A1", "#1E6E00", "#7900D7", "#A77500",
					"#6367A9", "#A05837", "#6B002C", "#772600", "#D790FF", "#9B9700", "#549E79",
					/* "#FFF69F", "#201625", */ "#72418F", "#BC23FF", "#99ADC0", /* "#3A2465", */ "#922329", "#5B4534",
					"#FDE8DC", "#404E55", "#0089A3", "#CB7E98", "#A4E804", "#324E72", "#6A3A4C", "#83AB58", "#001C1E",
					"#D1F7CE", "#004B28", "#C8D0F6", "#A3A489", "#806C66", "#222800", "#BF5650", "#E83000", "#66796D",
					"#DA007C", "#FF1A59", "#8ADBB4", /* "#1E0200", */ "#5B4E51", "#C895C5", /* "#320033", */ "#FF6832",
					"#66E1D3", "#CFCDAC", "#D0AC94", "#7ED379", "#012C58",

					"#7A7BFF", "#D68E01", "#8D8546", "#9695C5", "#E773CE", "#D86A78", "#3E89BE", "#CA834E", "#518A87",
					"#5B113C", "#55813B", "#E704C4", "#00005F", "#A97399", "#4B8160", "#59738A", "#FF5DA7", "#F7C9BF",
					"#643127", "#513A01", "#6B94AA", "#51A058", "#A45B02", /* "#1D1702", */ "#E20027", "#E7AB63",
					"#4C6001", "#9C6966", "#64547B", "#97979E", "#006A66", "#391406", "#F4D749", "#0045D2", "#006C31",
					"#DDB6D0", "#7C6571", "#9FB2A4", "#00D891", "#15A08A", "#BC65E9", /* "#FFFFFE", */ "#C6DC99",
					"#203B3C", "#C2FF99", "#001E09", "#00489C", "#6F0062", "#0CBD66", "#EEC3FF", "#456D75", "#B77B68",
					"#7A87A1", "#788D66", "#885578", "#FAD09F", "#FF8A9A", "#D157A0", "#BEC459", "#456648", "#0086ED",
					"#886F4C",

					"#671190", "#6B3A64", "#F5E1FF", "#FFA0F2", "#CCAA35", "#374527", "#8BB400", "#797868", "#C6005A",
					/* "#3B000A", */ "#C86240", "#29607C", "#402334", "#7D5A44", "#CCB87C", "#B88183", "#AA5199",
					"#B5D6C3", "#A38469", "#9F94F0", "#A74571", "#B894A6", "#71BB8C", "#00B433", "#789EC9", "#6D80BA",
					"#953F00", "#5EFF03", "#E4FFFC", "#1BE177", "#BCB1E5", "#76912F", "#003109", "#0060CD", "#D20096",
					"#895563", /*"#29201D",*/ "#5B3213", "#A76F42", "#89412E", /* "#1A3A2A", */ "#494B5A", "#A88C85",
					"#F4ABAA", "#A3F3AB", "#00C6C8", "#EA8B66", "#958A9F", "#BDC9D2", "#9FA064", "#BE4700", "#658188",
					"#83A485", "#453C23", "#47675D", "#3A3F00", /*"#061203",*/ "#DFFB71", "#868E7E", "#98D058", "#6C8F7D",
					"#D7BFC2", "#3C3E6E", "#D83D66",

					"#2F5D9B", "#6C5E46", "#D25B88", "#5B656C", "#00B57F", "#545C46", "#866097", "#365D25", "#252F99",
					"#00CCFF", "#674E60", "#FC009C", "#92896B"));

	public QueryForm getQuery() {
		return query;
	}

	public void setQuery(QueryForm query) {
		this.query = query;
	}

	public VegaVis(QueryForm query) {
		super();
		this.query = query;
	}

	public VegaVis() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String GetY() {
		String elt = "";
	if(Arrays.asList("Airports","Flights", "Carriers", "Tail").contains(this.query.getTables().get(0)))
	{
		for (String att : this.query.getSelectedAtt()) {
			if (att.toLowerCase().contains("count")) {
				String name = att.toLowerCase().replace("count(", "").replace(")", "");
				if (name.trim().equals("*"))
					return "flights_number";
				else
					return " number of " + name;
			} else if (att.toLowerCase().contains("max")) {
				String name = att.toLowerCase().replace("max(", "").replace(")", "");
				if (name.trim().equals("*"))
					return "max_flights_number";
				else
					return " max of " + name;
			}

			else if (att.toLowerCase().contains("min")) {
				String name = att.toLowerCase().replace("min(", "").replace(")", "");
				if (name.trim().equals("*"))
					return "min_flights_number";
				else
					return " min of " + name;
			} else if (att.toLowerCase().contains("avg")) {
				String name = att.toLowerCase().replace("avg(", "").replace(")", "");
				if (name.trim().equals("*"))
					return "avg_flights_number";
				else
					return " average of " + name;
			} else if (att.toLowerCase().contains("sum")) {
				String name = att.toLowerCase().replace("sum(", "").replace(")", "");
				if (name.trim().equals("*"))
					return "sum_flights_number";
				else
					return "sum of " + name;
			}
		}}
		else{
		for (String att : this.query.getSelectedAtt()) {	
		if (att.toLowerCase().contains("count")) {
			String name = att.toLowerCase().replace("count(", "").replace(")", "");
			if (name.trim().equals("*"))
				//return "flights_number";
				return "match_number";
			else
				return " number of " + name;
		} else if (att.toLowerCase().contains("max")) {
			String name = att.toLowerCase().replace("max(", "").replace(")", "");
			if (name.trim().equals("*"))
				//return "max_flights_number";
				return "max_match_number";
			else
				return " max of " + name;
		}

		else if (att.toLowerCase().contains("min")) {
			String name = att.toLowerCase().replace("min(", "").replace(")", "");
			if (name.trim().equals("*"))
				//return "min_flights_number";
				return "min_match_number";
			else
				return " min of " + name;
		} else if (att.toLowerCase().contains("avg")) {
			String name = att.toLowerCase().replace("avg(", "").replace(")", "");
			if (name.trim().equals("*"))
				//return "avg_flights_number";
				return "avg_match_number";
			else
				return " average of " + name;
		} else if (att.toLowerCase().contains("sum")) {
			String name = att.toLowerCase().replace("sum(", "").replace(")", "");
			if (name.trim().equals("*"))
				//return "sum_flights_number";
				return "sum_match_number";
			else
				return "sum of " + name;
		}
		}}
		
		return "count(*)";

	}

	public String GetX() {
		String elt = "";
		elt = query.getGroupbyAtt().get(0);
		String[] res = elt.split(Pattern.quote("."));
		if (res.length > 1) {
			if(Arrays.asList("Airports","Flights", "Carriers", "Tail").contains(this.query.getTables().get(0)))
			{if (res[0].contains("2"))
				return res[1] + " of destination";
			else if (res[0].contains("1"))
				return res[1] + " of departure";
			else
				return res[1];
			}
			else {
				
				if (res[0].contains("2"))
					//return res[1] + " of destination";
					return "away "+res[1];
				else if (res[0].contains("1"))
					// return res[1] + " of departure";
					return "home "+res[1];
				else
					return res[1];
				}
			
		} else if (elt.equals("code") || elt.equals("uniquecarrier")) {
			return "Airline_Id";
		} else
			return elt;
	}

	/********
	 * similar to getX just it return the correct name of field in database
	 *********/
	public String GetXName() {
		String elt = "";
		elt = query.getGroupbyAtt().get(0);
		String[] res = elt.split(Pattern.quote("."));
		if (res.length > 1) {

			return res[1];
		} else
			return elt;
	}

	
	/********
	 * similar to getXname but with attribute
	 *********/
	public String GetXNameV2(String elt) {		
		String[] res = elt.split(Pattern.quote("."));
		if (res.length > 1) {

			return res[1];
		} else
			return elt;
	}
	
	
	//for the case A2.state
	public String GetXRealName() {
		String elt = "";
		elt = query.getGroupbyAtt().get(0);
			return elt;
	}
	//for the case A2.state
		public String GetRealNameCond(String fieldInterest) {
			String elt = fieldInterest;
			for (String cds: query.getCondAtt().keySet())
			{
				if (cds.contains(fieldInterest))
				{ if(cds.contains(",")){
					return cds.split(",")[1];
				}
				else return cds;}
			}
				return elt;
		}
	
	public String GetX2(String webField) {
		String elt = webField;

		String[] res = elt.split(Pattern.quote("."));
		if (res.length > 1) {
			if(Arrays.asList("Airports","Flights", "Carriers", "Tail").contains(this.query.getTables().get(0)))
			{
			if (res[0].contains("2"))
				return res[1] + " of destination";
			else if (res[0].contains("1"))
				return res[1] + " of departure";
			else
				return res[1];}
			
			else {
				
					if (res[0].contains("2"))
						// return res[1] + " of destination";
						return "away "+ res[1] ;
					else if (res[0].contains("1"))
						//return res[1] + " of departure";
						return "away "+ res[1];
					else
						return res[1];
				
			}
		} else if (elt.equals("code") || elt.equals("uniquecarrier")) {
			return "Airline_Id";
		} else
			{return elt;}
	
	}

	public abstract VegaChart PrintVisrec_2D(String ColorCode);

	public String GetOperation() {
		String elt = "";
		for (String att : this.query.getSelectedAtt()) {
			if (att.toLowerCase().contains("count")) {
				return "sum";
			} else if (att.toLowerCase().contains("max")) {
				return "max";
			}

			else if (att.toLowerCase().contains("min")) {
				return "max";
			} else if (att.toLowerCase().contains("avg")) {
				return "average";
			}

			else if (att.toLowerCase().contains("sum")) {
				return "sum";
			}
		}
		return "sum";
	}

	/********* for extension visualization ********/
	public VegaChart PrintVisrecExTension(String webField, String OperationType) {
		String labelY = GetY();
		String labelX = GetX();
		String labelX2 = GetX2(webField);
		

		int width = 850;
		int height = 700;

		DataChart src_map = new DataChart("mapping");
		DataChart src = new DataChart("source");
		DataChart da = new DataChart("table");

		List<String> lis = new ArrayList<String>(Arrays.asList("axeX", "axeX2"));
		Map<String, Object> maw = new HashMap<String, Object>();
		maw.put("field", "AggNumber");
		List<String> listVal = new ArrayList<String>(Arrays.asList("sum"));
		maw.put("ops", listVal);
		List<Map<String, Object>> bee = new ArrayList<Map<String, Object>>(Arrays.asList(maw));
		Aggregate ag1 = new Aggregate("aggregate", lis, bee);

		List<String> lis3 = new ArrayList<String>(Arrays.asList("axeX"));
		Map<String, Object> maw3 = new HashMap<String, Object>();
		maw3.put("field", "AggNumber");
		List<String> listVal3 = new ArrayList<String>(Arrays.asList("sum"));
		maw3.put("ops", listVal3);
		List<Map<String, Object>> bee3 = new ArrayList<Map<String, Object>>(Arrays.asList(maw3));
		Aggregate ag3 = new Aggregate("aggregate", lis3, bee3);
		List<Transformation> tt3 = new ArrayList<Transformation>(Arrays.asList(ag3));
		DataChart da3 = new DataChart("stats3", "table", tt3);

		List<String> lis4 = new ArrayList<String>(Arrays.asList("axeX"));
		Stack ag4 = new Stack("sum_AggNumber", lis4);
		List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(ag1, ag4));
		DataChart da4 = new DataChart("stats4", "table", tt4);

		List<DataChart> data = new ArrayList<DataChart>(Arrays.asList(src_map, src, da, da3, da4));

		/********************* scales *************************/

		DomainChart xs = new DomainChart("source", "axeX");
		ScaleChart x = new ScaleChart("x", "ordinal", "width", true, xs);

		DomainChart ys = new DomainChart("stats3", "sum_AggNumber");
		ScaleChart y = new ScaleChart("y", "height", ys, "linear");

		Map<String, String> sort = new HashMap<String, String>();
		sort.put("field", "_id");
		sort.put("op", "min");
		DomainChart colors2 = new DomainChart("mapping", "axeX", sort);
		DomainChart colors2range = new DomainChart("mapping", "color");
		ScaleChart z_test = new ScaleChart("color", "ordinal", colors2, colors2range);

		List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y, /* z, */z_test));
		/********************* axis construction *************************/
		LabelAxeChart fill = new LabelAxeChart("black");
		LabelAxeChart angle = new LabelAxeChart(90);
		LabelAxeChart dx = new LabelAxeChart(50);
		Labels lab = new Labels(fill, angle, dx);

		Map<String, Labels> pr = new HashMap<String, Labels>();
		pr.put("labels", lab);
		AxisChart axe1 = new AxisChart("x", "x", labelX2, pr);

		AxisChart axe2 = new AxisChart("y", "y", labelY);

		List<AxisChart> axis = new ArrayList<AxisChart>(Arrays.asList(axe1, axe2));
		// AxisChart ax= new AxisChart(axis);
		/********************* marks construction *************************/
		ActChart ac1 = new ActChart("x", "axeX");
		ActChart ac2 = new ActChart("x", true, -4);
		ActChart ac3 = new ActChart("y", "layout_start");
		ActChart ac4 = new ActChart("y", "layout_end");
		ActChart ac41 = new ActChart("color", "axeX2");

		Map<String, Object> ac5 = new HashMap<String, Object>();
		ac5.put("value", (double) 1);

		Map<String, Object> ac6 = new HashMap<String, Object>();
		ac6.put("value", 0.5);

		ActionChart act1 = new ActionChart(ac1, ac2, ac3, ac4, ac41);
		ActionChart act2 = new ActionChart(ac5);
		ActionChart act3 = new ActionChart(ac6);

		PropChart prop1 = new PropChart(act1, act2, act3);
		Map<String, String> from = new HashMap<String, String>();
		from.put("data", "stats4");

		MarkChart mark1 = new MarkChart("rect", from, prop1);

		List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1));
		/********************************* legends *********************************/

		List<LegendChart> legends = new ArrayList<LegendChart>();
		PropLegend prL = new PropLegend();
		LegendChart leg = new LegendChart(labelX, "color", prL);
		legends.add(leg);

		/********************* Signal construction *************************/

		Stream st1 = new Stream("rect:mouseover", "datum");
		Stream st2 = new Stream("rect:mouseout", "{}");
		Stream st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" + webField
				+ "&state='+datum.axeX+'&chiffre='+datum.sum_nummer+'&type=" + OperationType + "','_self')");
		List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
		Map<String, String> init = new HashMap<String, String>();
		Signal2 sig = new Signal2("tooltip", init, sts);
		List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));

		List<PredicatChart> predicates = new ArrayList<PredicatChart>();
		/********************* vega construction *************************/
		VegaChart chart = new VegaChart(width, height, data, scls, axis, mrks, sigs, predicates, legends);

		return chart;

	}

	// for extension visualization
	public VegaChart PrintVisReverseExTension(String webField, String OperationType) {
		String labelY = GetY();
		String labelX = GetX();
		String labelX2 = GetX2(webField);
		String attributeX = GetXName();

		int width = 850;
		int height = 700;

		// List<String> vals = new ArrayList<String>();
		DataChart da = new DataChart("table"/* , vals */);

		List<String> lis3 = new ArrayList<String>(Arrays.asList("axeX", "axeX2"));
		Map<String, Object> maw3 = new HashMap<String, Object>();
		maw3.put("field", "AggNumber");
		List<String> listVal3 = new ArrayList<String>(Arrays.asList("sum"));
		maw3.put("ops", listVal3);

		List<Map<String, Object>> bee3 = new ArrayList<Map<String, Object>>(Arrays.asList(maw3));
		Aggregate ag3 = new Aggregate("aggregate", lis3, bee3);

		List<String> lis4 = new ArrayList<String>(Arrays.asList("axeX2"));
		Stack ag4 = new Stack("sum_AggNumber", lis4);

		List<Transformation> tt = new ArrayList<Transformation>(Arrays.asList(ag3, ag4));
		DataChart da3 = new DataChart("stats3", "table", tt);

		DataChart da1axis = new DataChart();

		{
			List<String> lisaxis = new ArrayList<String>(Arrays.asList("axeX2"));
			Map<String, Object> mawaxis = new HashMap<String, Object>();
			mawaxis.put("field", "AggNumber");
			List<String> listValaxis = new ArrayList<String>(Arrays.asList("sum"));
			mawaxis.put("ops", listValaxis);

			List<Map<String, Object>> beeaxis = new ArrayList<Map<String, Object>>(Arrays.asList(mawaxis));
			Aggregate ag1axis = new Aggregate("aggregate", lisaxis, beeaxis);
			List<Transformation> ttaxis = new ArrayList<Transformation>(Arrays.asList(ag1axis));
			da1axis = new DataChart("foraxeY", "table", ttaxis);
		}

		List<DataChart> data = new ArrayList<DataChart>(Arrays.asList(da, da3, da1axis));

		/********************* scales *************************/

		DomainChart xs = new DomainChart("table", "axeX2");
		ScaleChart x = new ScaleChart("x", "ordinal", "width", true, xs);

		DomainChart ys = new DomainChart("foraxeY", "sum_AggNumber");
		ScaleChart y = new ScaleChart("y", "height", ys, "linear");

		Map<String, String> sorting = new HashMap<String, String>();
		sorting.put("field", "AggNumber");
		sorting.put("op", "max");
		List<String> colors = new ArrayList<String>(Arrays.asList( // "#ffff33",
				// "#b3cde3","#ccebc5","#decbe4",
				"#a65628", "#f781bf", "#999999",
				// "#a6cee3",
				"#1f78b4",
				// "#e41a1c",
				"#b2df8a",
				// "#33a02c",
				"#fb9a99", "#e31a1c",
				// "#b2abd2",
				"#fdbf6f", "#ff7f00", "#cab2d6", "#8dd3c7", "#35978f",
				// "#ffffb3",
				// "#bebada",
				// "#fb8072",
				"#80b1d3", "#fdb462", "#b3de69", "#fccde5", "#d9d9d9",

				"#bf812d", "#377eb8", "#4daf4a", "#984ea3", "#ff7f00", "#fbb4ae", "#fed9a6", "#ffffcc", "#e5d8bd",
				"#fddaec", "#f2f2f2"

		));
		DomainChart zs = new DomainChart("table", "axeX", sorting);
		ScaleChart z = new ScaleChart("color", colors, zs, "ordinal");

		List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y, z));
		/********************* axis construction *************************/
		LabelAxeChart fill = new LabelAxeChart("black");
		LabelAxeChart angle = new LabelAxeChart(90);
		LabelAxeChart dx = new LabelAxeChart(50);
		Labels lab = new Labels(fill, angle, dx);

		Map<String, Labels> pr = new HashMap<String, Labels>();
		pr.put("labels", lab);
		AxisChart axe1 = new AxisChart("x", "x", labelX, pr);

		AxisChart axe2 = new AxisChart("y", "y", labelY);

		List<AxisChart> axis = new ArrayList<AxisChart>(Arrays.asList(axe1, axe2));
		// AxisChart ax= new AxisChart(axis);
		/********************* marks construction *************************/
		ActChart ac1 = new ActChart("x", "axeX2");
		ActChart ac2 = new ActChart("x", true, -4);
		ActChart ac3 = new ActChart("y", "layout_start");
		ActChart ac4 = new ActChart("y", "layout_end");
		ActChart ac41 = new ActChart("color", "axeX");

		Map<String, Object> ac5 = new HashMap<String, Object>();
		ac5.put("value", (double) 1);

		Map<String, Object> ac6 = new HashMap<String, Object>();
		ac6.put("value", 0.5);

		ActionChart act1 = new ActionChart(ac1, ac2, ac3, ac4, ac41);
		ActionChart act2 = new ActionChart(ac5);
		ActionChart act3 = new ActionChart(ac6);

		PropChart prop1 = new PropChart(act1, act2, act3);
		Map<String, String> from = new HashMap<String, String>();
		from.put("data", "stats3");

		MarkChart mark1 = new MarkChart("rect", from, prop1);

		List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1));
		/********************************* legends *********************************/

		List<LegendChart> legends = new ArrayList<LegendChart>();
		PropLegend prL = new PropLegend();
		LegendChart leg = new LegendChart(labelX2, "color", prL);
		legends.add(leg);

		/********************* Signal construction *************************/

		Stream st1 = new Stream("rect:mouseover", "datum");
		Stream st2 = new Stream("rect:mouseout", "{}");
		Stream st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" + labelX
				+ "&state='+datum.axeX2+'&chiffre='+datum.sum_nummer+'&type=" + OperationType + "','_self')");
		List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
		Map<String, String> init = new HashMap<String, String>();
		Signal2 sig = new Signal2("tooltip", init, sts);
		List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));

		List<PredicatChart> predicates = new ArrayList<PredicatChart>();
		/********************* vega construction *************************/
		VegaChart chart = new VegaChart(width, height, data, scls, axis, mrks, sigs, predicates, legends);

		return chart;

	}

	/********* for extension visualization ********/
	public VegaChart PrintVisExTensionBubble(String webField,
			String OperationType) {
		String labelY = GetY();
		String labelX = GetX();
		String labelX2 = GetX2(webField);
		//String attributeX = GetXName();

		int width = 1000;
		int height = 700;

		DataChart da = new DataChart("table");

		List<DataChart> data = new ArrayList<DataChart>(Arrays.asList(da));

		/********************* scales *************************/

		DomainChart xs = new DomainChart("table", "axeX");
		ScaleChart x = new ScaleChart("x", "ordinal", "width", true, true, xs, 1, true);

		DomainChart ys = new DomainChart("table", "axeX2");
		ScaleChart y = new ScaleChart("y", "ordinal", "height", true, true, ys, 1, true);

		List<Integer> range = new ArrayList<Integer>(Arrays.asList(5, 500));
		DomainChart sizedomain = new DomainChart("table", "AggNumber");
		ScaleChart scSize = new ScaleChart("size", "linear", sizedomain, range);
		List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y, scSize));

		/********************* axis construction *************************/
		LabelAxeChart fill = new LabelAxeChart("black");
		LabelAxeChart angle = new LabelAxeChart(90);
		LabelAxeChart dx = new LabelAxeChart(50);
		Labels lab = new Labels(fill, angle, dx);

		Map<String, Labels> pr = new HashMap<String, Labels>();
		pr.put("labels", lab);
		AxisChart axe1 = new AxisChart("x", "x", labelX2, pr);
		

		AxisChart axe2 = new AxisChart("y", "y", labelX);

		List<AxisChart> axis = new ArrayList<AxisChart>(Arrays.asList(axe1, axe2));
		// AxisChart ax= new AxisChart(axis);
		/********************* marks construction *************************/
		ActChart ac1 = new ActChart("x", "axeX");

		ActChart ac3 = new ActChart("y", "axeX2");
		ActChart acsize = new ActChart("size", "AggNumber");
		ActChart ac41 = new ActChart("steelblue");
		Map<String, Object> acfillopacity = new HashMap<String, Object>();
		acfillopacity.put("value", 1);
		ActChart ac6 = new ActChart("red");

		ActionChartScatter act1 = new ActionChartScatter(ac1, ac3, ac41, acfillopacity, acsize);
		ActionChart act2 = new ActionChart(ac41);
		ActionChart act3 = new ActionChart(ac6);
		PropChart prop1 = new PropChart(act1, act2, act3);
		Map<String, String> from = new HashMap<String, String>();
		from.put("data", "table");

		MarkChart mark1 = new MarkChart("symbol", from, prop1);

		// text mark
		ActChart acc1 = new ActChart("center");
		ActChart acc2 = new ActChart("#333");
		ActChart acc3 = new ActChart(500, 0);

		ActChart acc5 = new ActChart(0, 0);

		Map<String, String> acc6 = new HashMap<String, String>();
		acc6.put("signal", "tooltip.AggNumber");

		ActionChart acct1 = new ActionChart(acc2, acc1);
		ActionChartText acct2 = new ActionChartText(acc3, acc5, acc6);

		PropChart prop2 = new PropChart(acct1, acct2);
		MarkChart mark2 = new MarkChart("text", prop2);

		List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1, mark2));
		/********************************* legends *********************************/

		List<LegendChart> legends = new ArrayList<LegendChart>();
		PropLegend prL = new PropLegend("this is bubble");
		LegendChart leg = new LegendChart(labelY, prL, "size");
		legends.add(leg);

		/********************* Signal construction *************************/

		Stream st1 = new Stream("symbol:mouseover", "datum");
		Stream st2 = new Stream("symbol:mouseout", "{}");
		Stream st3 = new Stream("symbol:dblclick", "open('result.jsp?cancel=" + labelX
				+ "&state='+datum.axeX2+'&chiffre='+datum.sum_nummer+'&type=" + OperationType + "','_self')");
		List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
		Map<String, String> init = new HashMap<String, String>();
		Signal2 sig = new Signal2("tooltip", init, sts);
		List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));

		List<PredicatChart> predicates = new ArrayList<PredicatChart>();
		/********************* vega construction *************************/
		VegaChart chart = new VegaChart(width, height, data, scls, axis, mrks, sigs, predicates, legends);

		return chart;

	}

	public VegaChart PrintExtension_Slice() {
		String labelY = GetY();
		String labelX = GetX();
		String attributeX = GetXName();
		int width = 900;
		int height = 550;
		// List<String> vals = new ArrayList<String>();
		DataChart da = new DataChart("table"/* , vals */);
		List<DataChart> data = new ArrayList<DataChart>();
		data.add(da);

		//// *********************scales*************************/
		DomainChart xs = new DomainChart("table", "axeX");
		ScaleChart x = new ScaleChart("xscale", "ordinal", "width", true, xs);

		DomainChart ys = new DomainChart("table", "AggNumber");
		ScaleChart y = new ScaleChart("yscale", "height", ys, "linear");

		List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y));
		/********************* axis construction *************************/
		LabelAxeChart fill = new LabelAxeChart("black");
		LabelAxeChart angle = new LabelAxeChart(90);
		LabelAxeChart dx = new LabelAxeChart(20);
		LabelAxeChart font = new LabelAxeChart(13);
		Labels lab = new Labels(fill, angle, dx, font);

		Map<String, Labels> pr = new HashMap<String, Labels>();
		pr.put("labels", lab);

		LabelAxeChart fillTitle = new LabelAxeChart("black");
		LabelAxeChart fontTitle = new LabelAxeChart(17);
		Labels labTitle = new Labels(fillTitle, fontTitle);
		pr.put("title", labTitle);

		AxisChart axe1 = new AxisChart("x", "xscale", labelX, pr);

		LabelAxeChart filly = new LabelAxeChart("black");
		LabelAxeChart fonty = new LabelAxeChart(13);
		Labels laby = new Labels(filly, fonty);
		Map<String, Labels> pry = new HashMap<String, Labels>();
		pry.put("labels", laby);

		LabelAxeChart fillTitley = new LabelAxeChart("black");
		LabelAxeChart fontTitley = new LabelAxeChart(17);
		Labels labTitley = new Labels(fillTitley, fontTitley);
		pry.put("title", labTitley);

		AxisChart axe2 = new AxisChart("y", "yscale", labelY, pry);
		List<AxisChart> axis = new ArrayList<AxisChart>(Arrays.asList(axe1, axe2));
		// AxisChart ax= new AxisChart(axis);

		/********************* marks construction *************************/
		ActChart ac1 = new ActChart("xscale", "axeX");
		ActChart ac2 = new ActChart("xscale", true, -4);
		ActChart ac3 = new ActChart("yscale", "AggNumber");
		ActChart ac4 = new ActChart(0, "yscale", 0, 0);
		ActChart ac5 = new ActChart("#728f99");
		ActChart ac6 = new ActChart("Teal");

		ActionChart act1 = new ActionChart(ac1, ac2, ac3, ac4);
		ActionChart act2 = new ActionChart(ac5);
		ActionChart act3 = new ActionChart(ac6);

		PropChart prop1 = new PropChart(act1, act2, act3);
		Map<String, String> from = new HashMap<String, String>();
		from.put("data", "table");

		MarkChart mark1 = new MarkChart("rect", from, prop1);

		ActChart acc1 = new ActChart("center");
		ActChart acc2 = new ActChart("#333");
		ActChart acc3 = new ActChart("xscale", "tooltip.axeX", 0);
		ActChart acc4 = new ActChart("xscale", true, (float) 0.5);
		ActChart acc5 = new ActChart("yscale", "tooltip.AggNumber", -5);

		Map<String, String> acc6 = new HashMap<String, String>();
		acc6.put("signal", "tooltip.AggNumber");

		ActionChart acct1 = new ActionChart(acc2, acc1);
		ActionChart acct2 = new ActionChart(acc3, acc5, acc4, acc6);
		// ActionChart acct3= new ActionChart(acc6);

		PropChart prop2 = new PropChart(acct1, acct2);
		MarkChart mark2 = new MarkChart("text", prop2);
		List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1, mark2));

		/********************* Signal construction *************************/

		Stream st1 = new Stream("rect:mouseover", "datum");
		Stream st2 = new Stream("rect:mouseout", "{}");

		Stream st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" + attributeX
				+ "&state='+datum.axeX+'&chiffre='+datum.AggNumber+'&type=Extension_Slice','_self')");

		List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
		Map<String, String> init = new HashMap<String, String>();
		Signal2 sig = new Signal2("tooltip", init, sts);
		List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));

		/*********************
		 * predicates construction
		 *************************/
		Map<String, String> prs = new HashMap<String, String>();
		prs.put("arg", "id");

		Map<String, String> prs1 = new HashMap<String, String>();
		prs1.put("signal", "tooltip._id");

		List<Map<String, String>> li = new ArrayList<Map<String, String>>(Arrays.asList(prs, prs1));
		PredicatChart per = new PredicatChart("tooltip", "==", li);
		List<PredicatChart> pipi = new ArrayList<PredicatChart>(Arrays.asList(per));

		/********************* vega construction *************************/
		VegaChart chart = new VegaChart(width, height, data, scls, axis, mrks, sigs, pipi);

		return chart;
	}

	public VegaChart PrintVisrecSlice_Drill(String webField, String xAxisField, String type) {

		String labelY = GetY();
		String old2 = xAxisField;
		String labelX2 = GetX2(webField);
		String aggregationFct = GetOperation();
		String[] res = xAxisField.split(Pattern.quote("."));
		if (res.length > 1) {
			old2 = xAxisField.split(Pattern.quote("."))[1];
		}
		String labelX22 = GetX2(old2);
		int width = 600;
		int height = 500;

		DataChart da_map = new DataChart("mapping");
		DataChart da = new DataChart("table");

		List<String> lis = new ArrayList<String>(Arrays.asList("axeX", "axeX2"));
		Map<String, Object> maw = new HashMap<String, Object>();
		maw.put("field", "AggNumber");
		List<String> listVal = new ArrayList<String>(Arrays.asList("sum"));
		maw.put("ops", listVal);
		List<Map<String, Object>> bee = new ArrayList<Map<String, Object>>(Arrays.asList(maw));
		Aggregate ag1 = new Aggregate("aggregate", lis, bee);

		List<String> lis4 = new ArrayList<String>(Arrays.asList("axeX"));
		Stack ag4 = new Stack("sum_AggNumber", lis4);
		List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(ag1, ag4));
		DataChart da4 = new DataChart("stats3", "table", tt4);

		/**********************
		 * special data to make y axis correctly
		 ************************/

		DataChart da1axis = new DataChart();
		if (aggregationFct.equals("sum")) {
			List<String> lisaxis = new ArrayList<String>(Arrays.asList("axeX"));
			Map<String, Object> mawaxis = new HashMap<String, Object>();
			mawaxis.put("field", "AggNumber");
			List<String> listValaxis = new ArrayList<String>(Arrays.asList("sum" /* aggregationFct */));
			mawaxis.put("ops", listValaxis);
			mawaxis.put("as", "sum_AggNumber");
			List<Map<String, Object>> beeaxis = new ArrayList<Map<String, Object>>(Arrays.asList(mawaxis));
			Aggregate ag1axis = new Aggregate("aggregate", lisaxis, beeaxis);
			List<Transformation> ttaxis = new ArrayList<Transformation>(Arrays.asList(ag1axis));
			da1axis = new DataChart("foraxeY", "table", ttaxis);
		}

		/************ if aggregation is min max , avg *******/
		else {
			List<String> lisaxis = new ArrayList<String>(Arrays.asList("axeX2"));
			Map<String, Object> mawaxis = new HashMap<String, Object>();
			mawaxis.put("field", "AggNumber");
			List<String> listValaxis = new ArrayList<String>(Arrays.asList(aggregationFct));
			mawaxis.put("ops", listValaxis);
			mawaxis.put("as", "param");
			List<Map<String, Object>> beeaxis = new ArrayList<Map<String, Object>>(Arrays.asList(mawaxis));
			Aggregate ag1axis = new Aggregate("aggregate", lisaxis, beeaxis);

			List<String> lisaxis2 = new ArrayList<String>(Arrays.asList("axeX"));
			Map<String, Object> mawaxis2 = new HashMap<String, Object>();
			mawaxis2.put("field", "param");
			List<String> listValaxis2 = new ArrayList<String>(Arrays.asList("sum"));
			mawaxis2.put("ops", listValaxis2);
			mawaxis2.put("as", "sum_AggNumber");
			List<Map<String, Object>> beeaxis2 = new ArrayList<Map<String, Object>>(Arrays.asList(mawaxis2));
			Aggregate ag1axis2 = new Aggregate("aggregate", lisaxis2, beeaxis2);

			List<Transformation> ttaxis = new ArrayList<Transformation>(Arrays.asList(ag1axis, ag1axis2));
			da1axis = new DataChart("foraxeY", "table", ttaxis);

		}

		List<DataChart> data = new ArrayList<DataChart>(Arrays.asList(da_map, da, da4, da1axis));

		/********************* scales *************************/

		DomainChart xs = new DomainChart("table", "axeX");
		ScaleChart x = new ScaleChart("x", "ordinal", "width", true, xs);

		DomainChart ys = new DomainChart("foraxeY", "sum_AggNumber");
		ScaleChart y = new ScaleChart("y", "height", ys, "linear");

		Map<String, String> sort = new HashMap<String, String>();
		sort.put("field", "_id");
		sort.put("op", "min");
		DomainChart colors2 = new DomainChart("mapping", "axeX", sort);
		DomainChart colors2range = new DomainChart("mapping", "color");
		ScaleChart z = new ScaleChart("color", "ordinal", colors2, colors2range);

		List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y, z));
		/********************* axis construction *************************/
		LabelAxeChart fill = new LabelAxeChart("black");
		LabelAxeChart angle = new LabelAxeChart(90);
		LabelAxeChart dx = new LabelAxeChart(22);
		LabelAxeChart font = new LabelAxeChart(12);
		Labels lab = new Labels(fill, angle, dx, font);

		Map<String, Labels> pr = new HashMap<String, Labels>();
		pr.put("labels", lab);

		LabelAxeChart fillTitle = new LabelAxeChart("black");
		LabelAxeChart fontTitle = new LabelAxeChart(17);
		Labels labTitle = new Labels(fillTitle, fontTitle);
		pr.put("title", labTitle);

		AxisChart axe1 = new AxisChart("x", "x", labelX22, pr);

		LabelAxeChart filly = new LabelAxeChart("black");
		LabelAxeChart fonty = new LabelAxeChart(12);
		Labels laby = new Labels(filly, fonty);
		Map<String, Labels> pry = new HashMap<String, Labels>();
		pry.put("labels", laby);

		LabelAxeChart fillTitley = new LabelAxeChart("black");
		LabelAxeChart fontTitley = new LabelAxeChart(17);
		Labels labTitley = new Labels(fillTitley, fontTitley);
		pry.put("title", labTitley);

		AxisChart axe2 = new AxisChart("y", "y", labelY, pry);

		List<AxisChart> axis = new ArrayList<AxisChart>(Arrays.asList(axe1, axe2));
		// AxisChart ax= new AxisChart(axis);
		/********************* marks construction *************************/
		ActChart ac1 = new ActChart("x", "axeX");
		ActChart ac2 = new ActChart("x", true, -4);
		ActChart ac3 = new ActChart("y", "layout_start");
		ActChart ac4 = new ActChart("y", "layout_end");
		ActChart ac41 = new ActChart("color", "axeX2");

		Map<String, Object> ac5 = new HashMap<String, Object>();
		ac5.put("value", (double) 1);

		Map<String, Object> ac6 = new HashMap<String, Object>();
		ac6.put("value", 0.5);

		ActionChart act1 = new ActionChart(ac1, ac2, ac3, ac4, ac41);
		ActionChart act2 = new ActionChart(ac5);
		ActionChart act3 = new ActionChart(ac6);

		PropChart prop1 = new PropChart(act1, act2, act3);
		Map<String, String> from = new HashMap<String, String>();
		from.put("data", "stats3");

		MarkChart mark1 = new MarkChart("rect", from, prop1);

		List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1));
		/********************************* legends *********************************/

		List<LegendChart> legends = new ArrayList<LegendChart>();
		PropLegend prL = new PropLegend();
		LegendChart leg = new LegendChart(labelX2, "color", prL);
		legends.add(leg);

		/********************* Signal construction *************************/

		Stream st1 = new Stream("rect:mouseover", "datum");
		Stream st2 = new Stream("rect:mouseout", "{}");
		Stream st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" + old2
				+ "&state='+datum.axeX+'&chiffre='+datum.sum_nummer+'&type=" + type + "','_self')");
		List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
		Map<String, String> init = new HashMap<String, String>();
		Signal2 sig = new Signal2("tooltip", init, sts);
		List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));

		/*********************
		 * predicates construction
		 *************************/
		Map<String, String> prs = new HashMap<String, String>();
		prs.put("arg", "id");

		Map<String, String> prs1 = new HashMap<String, String>();
		prs1.put("signal", "tooltip._id");

		List<Map<String, String>> li = new ArrayList<Map<String, String>>(Arrays.asList(prs, prs1));
		PredicatChart per = new PredicatChart("tooltip", "==", li);
		List<PredicatChart> pipi = new ArrayList<PredicatChart>(Arrays.asList(per));

		/********************* vega construction *************************/
		VegaChart chart = new VegaChart(width, height, data, scls, axis, mrks, sigs, pipi, legends);

		return chart;

	}

	// used to generate first recommendation which is zoom in,drill-down,
	// another version where we tried to simplify as possible set of data used
	public VegaChart PrintVisrecZoomIn(String webField, String old, String type) {

		
		String labelY = GetY();
		String old2 = old;
		String labelX2 = GetX2(webField);
		String aggregationFct = GetOperation();
		String[] res = old.split(Pattern.quote("."));
		if (res.length > 1) {
			old2 = old.split(Pattern.quote("."))[1];
		}
		String labelX22 = GetX2(old2);
		int width = 600;
		int height = 500;

		// List<String> mapping = new ArrayList<String>();
		DataChart src_map = new DataChart("mapping"/* , mapping */);

		// List<String> valss = new ArrayList<String>();
		DataChart src = new DataChart("source"/* , valss */);

		// List<String> vals = new ArrayList<String>();
		DataChart da = new DataChart("table"/* , vals */);

		List<String> lis = new ArrayList<String>(Arrays.asList("axeX", "axeX2"));
		Map<String, Object> maw = new HashMap<String, Object>();
		maw.put("field", "AggNumber");
		List<String> listVal = new ArrayList<String>(Arrays.asList("sum"));
		maw.put("ops", listVal);
		List<Map<String, Object>> bee = new ArrayList<Map<String, Object>>(Arrays.asList(maw));
		Aggregate ag1 = new Aggregate("aggregate", lis, bee);

		List<String> lis4 = new ArrayList<String>(Arrays.asList("axeX"));
		Stack ag4 = new Stack("sum_AggNumber", lis4);
		List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(ag1, ag4));
		DataChart da4 = new DataChart("stats3", "table", tt4);

		/**********************
		 * special data to make y axis correctly
		 ************************/

		DataChart da1axis = new DataChart();
		if (aggregationFct.equals("sum")) {
			List<String> lisaxis = new ArrayList<String>(Arrays.asList("axeX"));
			Map<String, Object> mawaxis = new HashMap<String, Object>();
			mawaxis.put("field", "AggNumber");
			List<String> listValaxis = new ArrayList<String>(Arrays.asList("sum"));
			mawaxis.put("ops", listValaxis);
			mawaxis.put("as", "sum_AggNumber");
			List<Map<String, Object>> beeaxis = new ArrayList<Map<String, Object>>(Arrays.asList(mawaxis));
			Aggregate ag1axis = new Aggregate("aggregate", lisaxis, beeaxis);
			List<Transformation> ttaxis = new ArrayList<Transformation>(Arrays.asList(ag1axis));
			da1axis = new DataChart("foraxeY", "table", ttaxis);
		}

		/************ if aggregation is min max , avg *******/
		else {
			List<String> lisaxis = new ArrayList<String>(Arrays.asList("axeX2"));
			Map<String, Object> mawaxis = new HashMap<String, Object>();
			mawaxis.put("field", "AggNumber");
			List<String> listValaxis = new ArrayList<String>(Arrays.asList(aggregationFct));
			mawaxis.put("ops", listValaxis);
			mawaxis.put("as", "param");
			List<Map<String, Object>> beeaxis = new ArrayList<Map<String, Object>>(Arrays.asList(mawaxis));
			Aggregate ag1axis = new Aggregate("aggregate", lisaxis, beeaxis);

			List<String> lisaxis2 = new ArrayList<String>(Arrays.asList("axeX"));
			Map<String, Object> mawaxis2 = new HashMap<String, Object>();
			mawaxis2.put("field", "param");
			List<String> listValaxis2 = new ArrayList<String>(Arrays.asList("sum"));
			mawaxis2.put("ops", listValaxis2);
			mawaxis2.put("as", "sum_AggNumber");
			List<Map<String, Object>> beeaxis2 = new ArrayList<Map<String, Object>>(Arrays.asList(mawaxis2));
			Aggregate ag1axis2 = new Aggregate("aggregate", lisaxis2, beeaxis2);

			List<Transformation> ttaxis = new ArrayList<Transformation>(Arrays.asList(ag1axis, ag1axis2));
			da1axis = new DataChart("foraxeY", "table", ttaxis);

		}

		List<DataChart> data = new ArrayList<DataChart>(Arrays.asList(src_map, src, da, da4, da1axis));

		/********************* scales *************************/

		DomainChart xs = new DomainChart("source", "axeX");
		ScaleChart x = new ScaleChart("x", "ordinal", "width", true, xs);

		DomainChart ys = new DomainChart("foraxeY", "sum_AggNumber");
		ScaleChart y = new ScaleChart("y", "height", ys, "linear");

		Map<String, String> sorting = new HashMap<String, String>();
		sorting.put("field", "table");
		sorting.put("op", "max");
		DomainChart zs = new DomainChart("table", "axeX2", sorting);

		Map<String, String> sort = new HashMap<String, String>();
		sort.put("field", "_id");
		sort.put("op", "min");
		DomainChart colors2 = new DomainChart("mapping", "axeX", /* false */sort);
		DomainChart colors2range = new DomainChart("mapping", "color"/* , false */);
		ScaleChart z_test = new ScaleChart("color", "ordinal", colors2, colors2range);

		List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y, z_test));
		/********************* axis construction *************************/
		LabelAxeChart fill = new LabelAxeChart("black");
		LabelAxeChart angle = new LabelAxeChart(90);
		LabelAxeChart dx = new LabelAxeChart(22);
		LabelAxeChart font = new LabelAxeChart(12);
		Labels lab = new Labels(fill, angle, dx, font);

		Map<String, Labels> pr = new HashMap<String, Labels>();
		pr.put("labels", lab);

		LabelAxeChart fillTitle = new LabelAxeChart("black");
		LabelAxeChart fontTitle = new LabelAxeChart(17);
		Labels labTitle = new Labels(fillTitle, fontTitle);
		pr.put("title", labTitle);

		AxisChart axe1 = new AxisChart("x", "x", labelX22, pr);

		LabelAxeChart filly = new LabelAxeChart("black");
		LabelAxeChart fonty = new LabelAxeChart(12);
		Labels laby = new Labels(filly, fonty);
		Map<String, Labels> pry = new HashMap<String, Labels>();
		pry.put("labels", laby);

		LabelAxeChart fillTitley = new LabelAxeChart("black");
		LabelAxeChart fontTitley = new LabelAxeChart(17);
		Labels labTitley = new Labels(fillTitley, fontTitley);
		pry.put("title", labTitley);

		AxisChart axe2 = new AxisChart("y", "y", labelY, pry);

		List<AxisChart> axis = new ArrayList<AxisChart>(Arrays.asList(axe1, axe2));
		// AxisChart ax= new AxisChart(axis);
		/********************* marks construction *************************/
		ActChart ac1 = new ActChart("x", "axeX");
		ActChart ac2 = new ActChart("x", true, -4);
		ActChart ac3 = new ActChart("y", "layout_start");
		ActChart ac4 = new ActChart("y", "layout_end");
		ActChart ac41 = new ActChart("color", "axeX2");

		Map<String, Object> ac5 = new HashMap<String, Object>();
		ac5.put("value", (double) 1);

		Map<String, Object> ac6 = new HashMap<String, Object>();
		ac6.put("value", 0.5);

		ActionChart act1 = new ActionChart(ac1, ac2, ac3, ac4, ac41);
		ActionChart act2 = new ActionChart(ac5);
		ActionChart act3 = new ActionChart(ac6);

		PropChart prop1 = new PropChart(act1, act2, act3);
		Map<String, String> from = new HashMap<String, String>();
		from.put("data", "stats3");

		MarkChart mark1 = new MarkChart("rect", from, prop1);

		List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1));
		/********************************* legends *********************************/

		List<LegendChart> legends = new ArrayList<LegendChart>();
		PropLegend prL = new PropLegend();
		LegendChart leg = new LegendChart(labelX2, "color", prL);
		legends.add(leg);

		/********************* Signal construction *************************/

		Stream st1 = new Stream("rect:mouseover", "datum");
		Stream st2 = new Stream("rect:mouseout", "{}");
		Stream st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" + old2
				+ "&state='+datum.axeX+'&chiffre='+datum.sum_nummer+'&type=" + type + "','_self')");
		List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
		Map<String, String> init = new HashMap<String, String>();
		Signal2 sig = new Signal2("tooltip", init, sts);
		List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));

		/*********************
		 * predicates construction
		 *************************/
		Map<String, String> prs = new HashMap<String, String>();
		prs.put("arg", "id");

		Map<String, String> prs1 = new HashMap<String, String>();
		prs1.put("signal", "tooltip._id");

		List<Map<String, String>> li = new ArrayList<Map<String, String>>(Arrays.asList(prs, prs1));
		PredicatChart per = new PredicatChart("tooltip", "==", li);
		List<PredicatChart> pipi = new ArrayList<PredicatChart>(Arrays.asList(per));

		/********************* vega construction *************************/
		VegaChart chart = new VegaChart(width, height, data, scls, axis, mrks, sigs, pipi, legends);

		return chart;

	}

	public VegaChart PrintVisrec_ZoomInMoreDetails(String webField, String old, String type,
			List<DisplayForm> datasetToGiveColor) {
		VegaChart chart = PrintVisrecZoomIn(webField, old, type/*, datasetToGiveColor*/);
		ScaleChart colorsc = chart.getScales().get(2);
		Map<String, String> sorting = new HashMap<String, String>();
		sorting.put("field", "axeX2");
		sorting.put("op", "max");
		DomainChart zs = new DomainChart("table", "axeX2", sorting);
		colorsc.setDomain(zs);
		return chart;

	}

	public VegaChart PrintVisrec_ZoomInSlice(String ColorCode) {

		VegaChart chart = PrintVisrec_2D(ColorCode);
		Signal2 signal = (Signal2) chart.getSignals().get(0);
		List<Stream> streams = signal.getStreams();
		Stream interaction = streams.get(2);
		String url = interaction.getExpr();
		String link = url.split(",")[0];
		link = link + "+\'&type=ZoomIn_Slice\'";
		String urlnew = link + "," + url.split(",")[1];
		interaction.setExpr(urlnew);

		Map<String, String> sorting = new HashMap<String, String>();
		sorting.put("field", "AggNumber");
		sorting.put("op", "max");
		DomainChart newDomain = new DomainChart("table", "axeX", sorting);

		ScaleChart xscale = chart.getScales().get(0);
		xscale.setDomain(newDomain);

		return chart;
	}

	public List<DisplayForm> Construct_ScaleColorData(List<DisplayForm> datasetToGiveColor) {
		List<DisplayForm> result = new ArrayList<DisplayForm>();
		Set<String> insertion = new HashSet<String>();
		Set<String> insertionColor = new HashSet<String>();
		for (DisplayForm elt : datasetToGiveColor) {
			if (!insertion.contains(elt.getAxeX())) {

				int rand2 = randInt(0, colors.size() - 1);
				while (insertionColor.contains(colors.get(rand2))) {
					rand2 = randInt(0, colors.size() - 1);
				}
				DisplayForm ff = new DisplayForm(elt.getAxeX(), colors.get(rand2));
				result.add(ff);
				insertion.add(elt.getAxeX());
				insertionColor.add(colors.get(rand2));
			}
		}

		return result;
	}

	public List<DisplayForm> Construct_ScaleColorData_AxeX2(List<DisplayForm> datasetToGiveColor) {
		List<DisplayForm> result = new ArrayList<DisplayForm>();
		Set<String> insertion = new HashSet<String>();
		Set<String> insertionColor = new HashSet<String>();
		for (DisplayForm elt : datasetToGiveColor) {
			if (!insertion.contains(elt.getAxeX2())) {

				int rand2 = randInt(0, colors.size() - 1);
				while (insertionColor.contains(colors.get(rand2))) {
					rand2 = randInt(0, colors.size() - 1);
				}
				DisplayForm ff = new DisplayForm(elt.getAxeX2(), colors.get(rand2));
				result.add(ff);
				insertion.add(elt.getAxeX2());
				insertionColor.add(colors.get(rand2));
			}
		}

		/************
		 * the following two lines are very important to keep consistency
		 ***********/
		Collections.sort(result, DisplayForm.COMPARE_BY_axeX);
		Collections.reverse(result);
		return result;
	}

	// given the first parameter which contains a definition of color- re-use to
	// give consistent colors for parameter 2
	public List<DisplayForm> Construct_ScaleColorData_AxeX2_Given_Input(List<DisplayForm> colorRanges,
			List<DisplayForm> datasetToGiveColor) {

		if(datasetToGiveColor.size()>220) {datasetToGiveColor=datasetToGiveColor.subList(0, 100);}
		List<DisplayForm> result = new ArrayList<DisplayForm>();
		result.addAll(colorRanges);
		String colorFor_Other = "#D68E01";

		Map<String, String> colorsValues = new HashMap<String, String>();
		colorRanges.forEach(x -> {
			colorsValues.put(x.getAxeX(), x.getColor());
		});

		List<String> dataToRenderValue = new ArrayList<String>();
		datasetToGiveColor.forEach(x -> {
			dataToRenderValue.add(x.getAxeX2());
		});

		Set<String> distinct_colors_values = new HashSet<String>();
		distinct_colors_values.addAll(colorsValues.values());
		Set<String> distinct_dataToRender_values = new HashSet<String>();
		distinct_dataToRender_values.addAll(dataToRenderValue);

		if (!distinct_dataToRender_values.contains("other")) {

			Iterator<DisplayForm> i = result.iterator();
			loop: while (i.hasNext()) {
				DisplayForm o = i.next();
				if (o.getAxeX().equals("other")) {

					colorFor_Other = o.getColor();
					i.remove();
					break loop;
				}

			}
		}

		if (distinct_dataToRender_values.size() == distinct_colors_values.size()) {

			lp: for (DisplayForm elt : datasetToGiveColor) {
				if (!colorsValues.keySet().contains(elt.getAxeX2())) {
					DisplayForm ff = new DisplayForm(elt.getAxeX2(), colorFor_Other);
					result.add(ff);
					break lp;
				}
			}

			return result;
		} else {

			Set<String> insertion = new HashSet<String>();
			Set<String> insertionColor = new HashSet<String>();

			for (DisplayForm colo : result) {

				insertion.add(colo.getAxeX());
				insertionColor.add(colo.getColor());
			}

			for (DisplayForm elt : datasetToGiveColor) {
				if (!insertion.contains(elt.getAxeX2())) {

					int rand2 = randInt(0, colors.size() - 1);
					while (insertionColor.contains(colors.get(rand2))) {
						rand2 = randInt(0, colors.size() - 1);
					}
					DisplayForm ff = new DisplayForm(elt.getAxeX2(), colors.get(rand2));
					result.add(ff);
					insertion.add(elt.getAxeX2());
					insertionColor.add(colors.get(rand2));
				}
			}

			return result;
		}

	}

	/**********************
	 * recommendation part
	 ***********************************************/
	public VegaChart Recommendation_Viz(QueryFromDB currentQuerry, List<QueryFromDB> querys, String webField,
			 String type, List<DisplayForm> datasetToGiveColor) {
		Session session = vizDBUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Map<String, Integer> mappingMarkID = new HashMap<String, Integer>();
		// initialization
		VegaChart recommendation = BuildSekelton();

		// store visualization and mark which is rect
		int visID = DistinctValues.connector.counter.newVisID();
		int qid = DistinctValues.connector.counter.getQ_ID() - 1;
		int mID = 0;
		VizTable visRow = new VizTable(visID, qid, recommendation.getHeight(), recommendation.getWidth());
		session.save(visRow);
		for (MarkGeneral m : recommendation.getMarks()) {

			int mID2 = DistinctValues.connector.counter.newMarkPropID();
			MarkTable markRow = new MarkTable(mID2, visID, m.getType());
			session.save(markRow);
			mappingMarkID.put(m.getType(), mID2);
		}

		Boolean completeVis = false;
		int i = 0;
		while (!completeVis && i < querys.size()) {
			VisualizationBean vis = querys.get(i).getVis();
			if (!type.equals("ZoomIn_Slice")){
			Reuse_XScale(recommendation, session, querys.get(i), currentQuerry, visID, mappingMarkID,  datasetToGiveColor);
			Reuse_YScale(recommendation, session, querys.get(i), currentQuerry, visID, mappingMarkID);}
			if (type.equals("Zoom") || type.equals("Extension") || type.equals("Slice") || type.equals("Drill_Slice")) {
				Reuse_ColorScale(recommendation, session, querys.get(i), currentQuerry, datasetToGiveColor,
						mappingMarkID);
			} else if (type.equals("ZoomIn_Slice") || type.equals("Extension_Slice")||type.equals("Principal")) {
				Reuse_One_ColorScale(recommendation, session, querys.get(i), currentQuerry, datasetToGiveColor,
						mappingMarkID);
			}

			completeVis = completeCheck(recommendation);

			i++;
		}

		if (!completeVis) {
			if (type.equals("Zoom") || type.equals("Drill_Slice") || type.equals("Slice")) {
				String old = currentQuerry.getSelect().get(2);
				Complete_Rec_Viz_ZoomIn(recommendation, webField, old, type, datasetToGiveColor, visID, mappingMarkID,
						session);
			} 
		
			else if (type.equals("Extension")) {
				String old = currentQuerry.getSelect().get(2);
				Complete_Rec_Viz_Extension(recommendation, webField, old,type, datasetToGiveColor, visID, mappingMarkID,
						session);
				
			} else if (type.equals("Extension_Slice")|| type.equals("ZoomIn_Slice")||type.equals("Principal")) {
				Complete_Rec_Viz_2D(recommendation, datasetToGiveColor, visID, mappingMarkID, session,type);
			}
	
		}

		session.getTransaction().commit();
		session.close();

		return recommendation;
	}

	

	/**********
	 * construct vega specification with minimum specification
	 *************/
	public VegaChart BuildSekelton() {
		VegaChart chart = new VegaChart();

		// scales
		ScaleChart x = new ScaleChart("x");
		ScaleChart y = new ScaleChart("y");

		ScaleChart z_test = new ScaleChart("color");
		List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y, z_test));

		chart.setScales(scls);

		// marks
		MarkChart mark1 = new MarkChart("rect");
		List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1));
		chart.setMarks(mrks);
		return chart;
	}

	// general properties of visualizations like width and length
	private void RecommendationVizGeneralProperties(QueryFromDB currentQuerry, VegaChart viz_rec,
			QueryFromDB queryFromDB, VisualizationBean vis) {

		// TODO Auto-generated method stub
		if (viz_rec.getHeight() == 0) {

			viz_rec.setHeight(vis.getLength());
		}
		if (viz_rec.getWidth() == 0) {

			viz_rec.setWidth(vis.getWidth());
		}
	}

	// used by recommendation algos: case where we re-use scale x

	private void Reuse_XScale(VegaChart recommendation, Session session, QueryFromDB querys, QueryFromDB currentQuerry,
			int visID, Map<String, Integer> mappingMarkID, List<DisplayForm> datasetToRenderNow) {

		VegaJsonMapping mapping = new VegaJsonMapping();
		VisualizationBean vis = querys.getVis();
		for (ScaleChart scale : recommendation.getScales())

		{

			/**** important condition ****/
			if (scale.getName() == "x" && scale.getType() == null) {

				if (recommendation.getWidth() == 0) {

					recommendation.setWidth(vis.getWidth());
				}

				List<String> selectionpart = new ArrayList<String>();
				for (String elt : querys.getSelect()) {
					if (elt.equals("code"))
						selectionpart.add("uniquecarrier");
					else
						selectionpart.add(elt);
				}
				List<String> selectionpartRec = new ArrayList<String>()/*currentQuerry.getSelect()*/;
				for (String elt :currentQuerry.getSelect()) {
					if (elt.equals("code"))
						selectionpartRec.add("uniquecarrier");
					else
						selectionpartRec.add(elt);
				}
				// ----> important condition
				if (selectionpart.get(selectionpart.size() - 1)
						.equals(selectionpartRec.get(selectionpartRec.size() - 1))) {

					UpdateChartAndSaveReusedAxis("x", session, vis, recommendation, visID);
				
					ScaleTable scaleobject = FindAppropriateScale("x", "ordinal", vis, session);
					
					if (scaleobject != null) {
						DomainChart domain = new DomainChart("xrange", scaleobject.getFielddom());
						scale.setDomain(domain);

						if (scaleobject.getLiteral().equals("empty")) {
						} else {
							scale.setRange(scaleobject.getLiteral());
						}

						scale.setType("ordinal");

						/***** taking dataset ********/
						// this part is important to re-use a dataset
						List<DisplayForm> xrange = getPreviousRange(scaleobject.getDataset(), session);
						/****new function****/
						xrange= UpdateXrange(xrange,datasetToRenderNow);
						
						
						String currentData = mapping.ConvertToJson_Normal(xrange);
						DataChart input = new DataChart("xrange", xrange);
						/*** decide to add dataset or not **********/
						List<DataChart> l_data = recommendation.getData();
						boolean found = searchDataSetInVegaSpec(currentData, l_data, domain);
						if (!found) {
							l_data.add(input);
							recommendation.setData(l_data);
						}

						/******** store channels *******/
						// re-use encoding channel -->store in DB
						useOfPreviousEncodingChannel(session, scaleobject.getIdscale(), mappingMarkID,
								vis.getMarksID());
						// vis.getMarks to take only its encoding channel , not
						// all
						// encoding channels that use scale 3,
						// il se peut il y a beaucoup

					}
				}
			}
		}

	}

	

	private void Reuse_YScale(VegaChart recommendation, Session session, QueryFromDB querys, QueryFromDB currentQuerry,
			int visID, Map<String, Integer> mappingMarkID) {
		// TODO Auto-generated method stub
		VegaJsonMapping mapping = new VegaJsonMapping();
		VisualizationBean vis = querys.getVis();
		boolean simMarks = CompareMarks(vis.getMarksID(), recommendation.getMarks(), session);
		if (simMarks) {
			for (ScaleChart scale : recommendation.getScales())

			{
				if (scale.getName() == "y" && scale.getType() == null) {
					String selectionpart = querys.getSelect().get(0);
					String selectionpartRec = currentQuerry.getSelect().get(0);

					//String grpBy = querys.getSelect().get(querys.getSelect().size() - 1);
					//String grpByRec = currentQuerry.getSelect().get(currentQuerry.getSelect().size() - 1);
					String grpBy = String.join(",", querys.getSelect());
					String grpByRec = String.join(",",currentQuerry.getSelect());
					List<String> conds = new ArrayList<String>();
					List<String> condsRec = new ArrayList<String>();

					querys.getCondition().forEach(x -> {
						conds.add(x.getDimension() + "," + x.getOperator() + "," + x.getValue());
					});
					currentQuerry.getCondition().forEach(x -> {
						condsRec.add(x.getDimension() + "," + x.getOperator() + "," + x.getValue());
					});

					/**** compare two lists *******/
					Boolean similarity = CompareSimilarity(conds, condsRec);

					// ****important condition
					if (grpBy.equals(grpByRec) && selectionpart.equals(selectionpartRec) && similarity) {

						if (recommendation.getHeight() == 0) {

							recommendation.setHeight(vis.getLength());
						}

						UpdateChartAndSaveReusedAxis("y", session, vis, recommendation, visID);
						ScaleTable scaleobject = FindAppropriateScale("y", "linear", vis, session);
						if(scaleobject!=null){
						DomainChart domain = new DomainChart("yrange",
								"AggNumber");
						scale.setDomain(domain);

						if (scaleobject.getLiteral().equals("empty")) {
						} else {
							scale.setRange(scaleobject.getLiteral());
						}

						scale.setType("linear");

						/***** re-using dataset ********/
						List<DisplayForm> yrange = getPreviousRange(scaleobject.getDataset(), session);
						String PrevDomain = mapping.ConvertToJson_Normal(yrange);
						DataChart input = new DataChart("yrange", yrange);
						List<DataChart> l_data = recommendation.getData();
						boolean found = searchDataSetInVegaSpec(PrevDomain, l_data, domain);

						if (!found) {
							l_data.add(input);
							recommendation.setData(l_data);
						}
						/******* store encodings ********/

						// re-use encoding channel -->store in DB
						useOfPreviousEncodingChannel(session, scaleobject.getIdscale(), mappingMarkID,
								vis.getMarksID());
					}}

				}
			}
		}
	}

	private void Reuse_ColorScale(VegaChart recommendation, Session session, QueryFromDB querys,
			QueryFromDB currentQuerry, List<DisplayForm> datasetToGiveColor, Map<String, Integer> mappingMarkID) {
		VegaJsonMapping mapping = new VegaJsonMapping();
		VisualizationBean vis = querys.getVis();

		ScaleTable scaleobject = FindAppropriateScale("color", "ordinal", vis, session);

		if (scaleobject != null && querys.getSelect().size() > 2)

		{

			for (ScaleChart scale : recommendation.getScales())

			{
				if (scale.getName() == "color" && scale.getType() == null) {
					String groupBy1 = querys.getSelect().get(1);
					String groupBy1Rec = currentQuerry.getSelect().get(1);

					String groupBy2 = querys.getSelect().get(2);
					String groupBy2Rec = currentQuerry.getSelect().get(2);

					// ****important condition

					if (querys.getSelect().size() == currentQuerry.getSelect().size() && groupBy1.equals(groupBy1Rec)) {

						Map<String, String> sort = new HashMap<String, String>();
						sort.put("field", "_id");
						sort.put("op", "min");
						DomainChart domain = new DomainChart("mapping", scaleobject.getFielddom(), sort);
						scale.setDomain(domain);

						if (scaleobject.getLiteral().equals("empty")) {
							DomainChart colors2range = new DomainChart("mapping", "color");

							scale.setRange(colors2range);
						}
						scale.setType("ordinal");

						/***** taking dataset ********/
						List<DisplayForm> colorrange1 = getPreviousRange(scaleobject.getDataset(), session);
						List<DisplayForm> currentcolors = Construct_ScaleColorData_AxeX2_Given_Input(colorrange1,
								datasetToGiveColor);
						DataChart input = new DataChart("mapping", currentcolors);

						List<DataChart> l_data = recommendation.getData();
						l_data.add(input);

						// store dataset color in database

						int dID = DistinctValues.connector.counter.newdataID();
						DisplayForm[] dataArr = new DisplayForm[currentcolors.size()];
						dataArr = currentcolors.toArray(dataArr);
						DatasetTable datasetRow = new DatasetTable(dID, "mapping", dataArr,
								Integer.toString(scaleobject.getDataset()));
						session.save(datasetRow);

						// save also the scale

						int scID = DistinctValues.connector.counter.newS_ID();
						ScaleTable scaleRow = new ScaleTable(scID, dID, "ordinal", "color", scaleobject.getFielddom(),
								scaleobject.getFieldrange(), "empty");
						session.save(scaleRow);

						/***************/

						// re-use encoding channel -->store in DB
						useOfPreviousEncodingChannel(session,
								/* scaleobject.getIdscale() */scID, mappingMarkID, vis.getMarksID());

					}

				}
			}
		}
	}

	// for zoom in slice functions we want one color used previously to present
	// cancelled flights
	private void Reuse_One_ColorScale(VegaChart recommendation, Session session, QueryFromDB querys,
			QueryFromDB currentQuerry, List<DisplayForm> datasetToGiveColor, Map<String, Integer> mappingMarkID) {

		// determine we search color for which value
		VisualizationBean vis = querys.getVis();
		for (ScaleChart scale : recommendation.getScales())

		{
			if (scale.getName() == "color" && scale.getType() == null) {
				String groupBy1 = querys.getSelect().get(1);
				String groupBy1Rec = currentQuerry.getSelect().get(1);
				// String groupBy2 = querys.getSelect().get(2);

				// ****important condition
				// this applies only to previous zoom in
				if (querys.getSelect().size() == 3 /* && groupBy1.equals(groupBy1Rec) */
						&& querys.getSelect().get(1).equals(datasetToGiveColor.get(0).getAxeX())) {

					ScaleTable scaleobject = FindAppropriateScale("color", "ordinal", vis, session);
					boolean search = false;
					if (scaleobject != null) {
						List<DisplayForm> colorrangePrevious = getPreviousRange(scaleobject.getDataset(), session);
						List<DisplayForm> colorrangeNew = new ArrayList<DisplayForm>();

						lpsearch: for (DisplayForm elt : colorrangePrevious) {
							if (elt.getAxeX().equals(datasetToGiveColor.get(0).getColor())) {
								colorrangeNew.add(elt);
								search = true;
								break lpsearch;
							}

						}

						if (search) {
							/***** add dataset to chart ********/
							DataChart input = new DataChart("mapping", colorrangeNew);

							List<DataChart> l_data = recommendation.getData();
							l_data.add(input);

							Map<String, String> sort = new HashMap<String, String>();
							sort.put("field", "_id");
							sort.put("op", "min");
							DomainChart domain = new DomainChart("mapping", scaleobject.getFielddom(), sort);
							scale.setDomain(domain);

							if (scaleobject.getLiteral().equals("empty")) {
								DomainChart colors2range = new DomainChart("mapping", "color");

								scale.setRange(colors2range);
							}
							scale.setType("ordinal");

							// store dataset color in database
							int dID = DistinctValues.connector.counter.newdataID();
							DisplayForm[] dataArr = new DisplayForm[colorrangeNew.size()];
							dataArr = colorrangeNew.toArray(dataArr);
							DatasetTable datasetRow = new DatasetTable(dID, "mapping", dataArr,
									Integer.toString(scaleobject.getDataset()));
							session.save(datasetRow);

							// save also the scale
							int scID = DistinctValues.connector.counter.newS_ID();
							ScaleTable scaleRow = new ScaleTable(scID, dID, "ordinal", "color",
									scaleobject.getFielddom(), scaleobject.getFieldrange(), "empty");
							session.save(scaleRow);

							// re-use encoding channel -->store in DB
							useOfPreviousEncodingChannel(session,scID, mappingMarkID, vis.getMarksID());
							
							
							/*********************************add just legends to put a user in the context why he gets such color *********************************/

							List<LegendChart> legends = new ArrayList<LegendChart>();
							PropLegend prL = new PropLegend();
							LegendChart leg = new LegendChart(datasetToGiveColor.get(0).getAxeX(), "color", prL);
							legends.add(leg);

							recommendation.setLegends(legends);
						}
					}
				}

			}
		}
	}
	public abstract void Complete_Rec_Viz_ZoomIn(VegaChart recommendation, String webField, String old, String type,
			List<DisplayForm> datasetToGiveColor, int visid, Map<String, Integer> marksElts,
			Session session);
	
	
	
	public abstract void Complete_Rec_Viz_2D(VegaChart recommendation, List<DisplayForm> datasetToGiveColor,
			int visID, Map<String, Integer> mappingMarkID, Session session,String type)	;
	
	public abstract void Complete_Rec_Viz_Extension(VegaChart recommendation, String webField, String old, String type,
			List<DisplayForm> datasetToGiveColor, int visid, Map<String,Integer> marksElts, Session session) ;
	/*public void Complete_Rec_Viz_Extension(VegaChart recommendation, String webField, String typeOp,
			List<DisplayForm> datasetToGiveColor, int visID, Map<String, Integer> mappingMarkID, Session session) {
		// TODO Auto-generated method stub
		boolean xscale = false;
		boolean yscale = false;
		boolean colorscale = false;
		String labelY = GetY();
		String labelX = GetX();
		String labelX2 = GetX2(webField);
		String label_real_name = GetXNameV2(webField);
		if (recommendation.getWidth() == 0) {
			recommendation.setWidth(600);
		}
		if (recommendation.getHeight() == 0) {
			recommendation.setHeight(500);
		}

		// DataChart src_map = new DataChart("mapping");
		DataChart da = new DataChart("table");

		List<String> lis = new ArrayList<String>(Arrays.asList("axeX", "axeX2"));
		Map<String, Object> maw = new HashMap<String, Object>();
		maw.put("field", "AggNumber");
		List<String> listVal = new ArrayList<String>(Arrays.asList("sum"));
		maw.put("ops", listVal);
		List<Map<String, Object>> bee = new ArrayList<Map<String, Object>>(Arrays.asList(maw));
		Aggregate ag1 = new Aggregate("aggregate", lis, bee);

		List<String> lis3 = new ArrayList<String>(Arrays.asList("axeX"));
		Map<String, Object> maw3 = new HashMap<String, Object>();
		maw3.put("field", "AggNumber");
		List<String> listVal3 = new ArrayList<String>(Arrays.asList("sum"));
		maw3.put("ops", listVal3);
		List<Map<String, Object>> bee3 = new ArrayList<Map<String, Object>>(Arrays.asList(maw3));
		Aggregate ag3 = new Aggregate("aggregate", lis3, bee3);
		List<Transformation> tt3 = new ArrayList<Transformation>(Arrays.asList(ag3));
		DataChart da3 = new DataChart("stats3", "table", tt3);

		List<String> lis4 = new ArrayList<String>(Arrays.asList("axeX"));
		Stack ag4 = new Stack("sum_AggNumber", lis4);
		List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(ag1, ag4));
		DataChart da4 = new DataChart("stats4", "table", tt4);

		List<DataChart> data = new ArrayList<DataChart>(Arrays.asList(da, da3, da4));

		/// beginning of recommendation modif following what it was done ine
		/// recommendation
		List<ScaleChart> scls = recommendation.getScales();
		List<AxisChart> tempo_axis = recommendation.getAxes();
		for (ScaleChart sca : recommendation.getScales()) {
			if (sca.getName().equals("x") && sca.getType() != null) {
				xscale = true;

			}
			if (sca.getName().equals("y") && sca.getType() != null) {
				yscale = true;
			}
			if (sca.getName().equals("color") && sca.getType() != null) {
				colorscale = true;
			}
		}
		if (!xscale) {

			// construct x scale
			DomainChart xs = new DomainChart("table", "axeX");
			ScaleChart x_sc = new ScaleChart("x", "ordinal", "width", true, xs);
			// remove x scale constructed in skeleton
			removeScale(scls, "x");
			scls.add(x_sc);

			// automatically also add axis
			LabelAxeChart fill = new LabelAxeChart("black");
			LabelAxeChart angle = new LabelAxeChart(90);
			LabelAxeChart dx = new LabelAxeChart(50);
			Labels lab = new Labels(fill, angle, dx);

			Map<String, Labels> pr = new HashMap<String, Labels>();
			pr.put("labels", lab);
			AxisChart axe1 = new AxisChart("x", "x", labelX2, pr);
			// add this axis to current chart
			tempo_axis.add(axe1);

			// store scale and axis in DB
			int scID = DistinctValues.connector.counter.newS_ID();
			List idData = session.createSQLQuery("select max(iddataset) from DatasetTable where name = 'table'").list();
			Iterator<DisplayForm[]> ite = idData.iterator();
			Object test = ite.next();
			int d_id = Integer.parseInt(test.toString());
			ScaleTable scaleRow = new ScaleTable(scID, d_id, x_sc.getType(), x_sc.getName(),
					x_sc.getDomain().getField(), "empty", x_sc.getRange().toString());
			session.save(scaleRow);

			int axeID = DistinctValues.connector.counter.newaxis_ID();
			AxisTable AXRow = DistinctValues.connector.constructAxisRow(axeID, labelX2, 5, "x", session, "x");
			int axeUsageID = DistinctValues.connector.counter.newaxis_Usage_ID();
			AxisUsageTable AXUsageRow = new AxisUsageTable(axeUsageID, axeID, visID, "x");
			session.saveOrUpdate(AXRow);
			session.saveOrUpdate(AXUsageRow);

			// store the new encoding channels
			int chID = DistinctValues.connector.counter.newEncodingID();
			for (Entry<String, Integer> m : mappingMarkID.entrySet()) {
				if (m.getKey().equals("rect")) {
					int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
					ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "x", "axeX", session, scID);
					ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(),
							idch.getChanneltype());

					int chID2 = DistinctValues.connector.counter.newEncodingID();
					int chUsageID2 = DistinctValues.connector.counter.newchannelUsage_ID();
					ChannelTable idch2 = DistinctValues.connector.constructChannelRow(chID2, "width", null, session,
							scID);
					ChannelUsageTable usageEncoRow2 = new ChannelUsageTable(chUsageID2, chID2, m.getValue(),
							idch2.getChanneltype());

					session.saveOrUpdate(idch);
					session.saveOrUpdate(usageEncoRow);
					session.saveOrUpdate(idch2);
					session.saveOrUpdate(usageEncoRow2);
				}
			}
		}
		if (!yscale) {
			// building y axis
			DomainChart ys = new DomainChart("stats3", "sum_AggNumber");
			ScaleChart y_sc = new ScaleChart("y", "height", ys, "linear");
			removeScale(scls, "y");
			scls.add(y_sc);

			// automatically also add axis
			AxisChart axe2 = new AxisChart("y", "y", labelY);
			tempo_axis.add(axe2);

			// store axis
			int scID = DistinctValues.connector.counter.newS_ID();
			List idData = session.createSQLQuery("select max(iddataset) from DatasetTable where name = 'table'").list();
			Iterator<DisplayForm[]> ite = idData.iterator();
			Object test = ite.next();
			int d_id = Integer.parseInt(test.toString());
			ScaleTable scaleRow = new ScaleTable(scID, d_id, y_sc.getType(), y_sc.getName(),
					y_sc.getDomain().getField(), "empty", y_sc.getRange().toString());
			session.save(scaleRow);

			int axeID = DistinctValues.connector.counter.newaxis_ID();
			AxisTable AXRow = DistinctValues.connector.constructAxisRow(axeID, labelY, 5, "y", session, "y");
			int axeUsageID = DistinctValues.connector.counter.newaxis_Usage_ID();
			AxisUsageTable AXUsageRow = new AxisUsageTable(axeUsageID, axeID, visID, "y");
			session.saveOrUpdate(AXRow);
			session.saveOrUpdate(AXUsageRow);

			for (Entry<String, Integer> m : mappingMarkID.entrySet()) {
				if (m.getKey().equals("rect")) {
					// store encodings
					int chID = DistinctValues.connector.counter.newEncodingID();

					int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
					ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "y", "layout_start", session,
							scID);
					ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(),
							idch.getChanneltype());

					int chID2 = DistinctValues.connector.counter.newEncodingID();
					int chUsageID2 = DistinctValues.connector.counter.newchannelUsage_ID();
					ChannelTable idch2 = DistinctValues.connector.constructChannelRow(chID2, "y2", "layout_end",
							session, scID);
					ChannelUsageTable usageEncoRow2 = new ChannelUsageTable(chUsageID2, chID2, m.getValue(),
							idch2.getChanneltype());

					session.saveOrUpdate(idch);
					session.saveOrUpdate(usageEncoRow);
					session.saveOrUpdate(idch2);
					session.saveOrUpdate(usageEncoRow2);
				}
			}
		}
		if (!colorscale) {
			// define dataset and add it to chart
			List<DisplayForm> colorRgs = Construct_ScaleColorData_AxeX2(datasetToGiveColor);
			DataChart src_map = new DataChart("mapping", colorRgs);
			data.add(src_map);

			// define scale and add it to chart object
			Map<String, String> sort = new HashMap<String, String>();
			sort.put("field", "_id");
			sort.put("op", "min");
			DomainChart colors2 = new DomainChart("mapping", "axeX", sort);
			DomainChart colors2range = new DomainChart("mapping", "color");
			ScaleChart z_test = new ScaleChart("color", "ordinal", colors2, colors2range);

			removeScale(scls, "color");
			scls.add(z_test);

			// store dataset in DB
			DistinctValues.connector.dataSetToDB(colorRgs, "mapping");
			// store scale inDB
			int scID = DistinctValues.connector.counter.newS_ID();
			List idData = session.createSQLQuery("select max(iddataset) from DatasetTable where name = 'mapping'")
					.list();
			Iterator<DisplayForm[]> ite = idData.iterator();
			Object test = ite.next();
			// store scale in DB
			int d_id = 0;
			if (test == null) {
				d_id = DistinctValues.connector.counter.newdataID();
			} else {
				d_id = Integer.parseInt(test.toString());
			}
			ScaleTable scaleRow = new ScaleTable(scID, d_id, z_test.getType(), z_test.getName(),
					z_test.getDomain().getField(), z_test.getRange().toString(), "empty");
			session.save(scaleRow);

			for (Entry<String, Integer> m : mappingMarkID.entrySet()) {
				if (m.getKey().equals("rect")) {
					int chID = DistinctValues.connector.counter.newEncodingID();
					int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
					ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "fill", "axeX2", session,
							scID);
					ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(),
							idch.getChanneltype());
					session.saveOrUpdate(idch);
					session.saveOrUpdate(usageEncoRow);
				}
			}
		}

		List<DataChart> dats = new ArrayList<DataChart>(recommendation.getData());
		dats.addAll(data);
		recommendation.setData(dats);


		completeOssature_Viz_Extension(recommendation);



		List<LegendChart> legends = new ArrayList<LegendChart>();
		PropLegend prL = new PropLegend();
		LegendChart leg = new LegendChart(labelX, "color", prL);
		legends.add(leg);
		recommendation.setLegends(legends);
	
		

		Stream st1 = new Stream("rect:mouseover", "datum");
		Stream st2 = new Stream("rect:mouseout", "{}");
	
		Stream st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" +label_real_name+ "&state='+datum.axeX+'&chiffre='+datum.sum_nummer+'&type=" + typeOp + "','_self')");
		List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
		Map<String, String> init = new HashMap<String, String>();
		Signal2 sig = new Signal2("tooltip", init, sts);
		List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));

		recommendation.setSignals(sigs);

	}*/
	
	protected void completeOssature_Viz_2D(VegaChart recommendation) {

		CompleteSize(recommendation);
		boolean rgcolor = false;
		PropChart prop1 = null;
		lp: for (DataChart elt : recommendation.getData()) {
			if (elt.getName().equals("mapping")) {
				rgcolor = true;
				break lp;
			}
		}

		ActChart ac1 = new ActChart("x", "axeX");
		ActChart ac2 = new ActChart("x", true, -4);
		ActChart ac3 = new ActChart("y", "AggNumber");
		ActChart ac4 = new ActChart(0, "y", 0, 0);

		if (!rgcolor) {
			ActChart ac5 = new ActChart(/*"#bdbdbd"*/"#728f99");
			ActChart ac6 = new ActChart("Teal");

			ActionChart act1 = new ActionChart(ac1, ac2, ac3, ac4);
			ActionChart act2 = new ActionChart(ac5);
			ActionChart act3 = new ActionChart(ac6);

			prop1 = new PropChart(act1, act2, act3);
		} else {
			ActChart ac41 = new ActChart("color", "axeX2");
			ActionChart act1 = new ActionChart(ac1, ac2, ac3, ac4, ac41);
			prop1 = new PropChart(act1);
		}

		Map<String, String> from = new HashMap<String, String>();
		from.put("data", "table");

		MarkChart mark1 = new MarkChart("rect", from, prop1);

		ActChart acc1 = new ActChart("center");
		ActChart acc2 = new ActChart("#333");
		ActChart acc3 = new ActChart("x", "tooltip.axeX", 0);
		ActChart acc4 = new ActChart("x", true, (float) 0.5);
		ActChart acc5 = new ActChart("y", "tooltip.AggNumber", -5);

		Map<String, String> acc6 = new HashMap<String, String>();
		acc6.put("signal", "tooltip.AggNumber");

		ActionChart acct1 = new ActionChart(acc2, acc1);
		ActionChart acct2 = new ActionChart(acc3, acc5, acc4, acc6);

		PropChart prop2 = new PropChart(acct1, acct2);
		MarkChart mark2 = new MarkChart("text", prop2);
		List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1, mark2));
		recommendation.setMarks(mrks);

	}

	public void completeOssature_Viz_Extension(VegaChart recommendation) {
		CompleteSize(recommendation);
		// TODO Auto-generated method stub
		ActChart ac1 = new ActChart("x", "axeX");
		ActChart ac2 = new ActChart("x", true, -4);
		ActChart ac3 = new ActChart("y", "layout_start");
		ActChart ac4 = new ActChart("y", "layout_end");

		ActChart ac41 = new ActChart("color", "axeX2");

		Map<String, Object> ac5 = new HashMap<String, Object>();
		ac5.put("value", (double) 1);

		Map<String, Object> ac6 = new HashMap<String, Object>();
		ac6.put("value", 0.5);

		ActionChart act1 = new ActionChart(ac1, ac2, ac3, ac4, ac41);
		ActionChart act2 = new ActionChart(ac5);
		ActionChart act3 = new ActionChart(ac6);

		PropChart prop1 = new PropChart(act1, act2, act3);
		Map<String, String> from = new HashMap<String, String>();
		from.put("data", "stats4");

		MarkChart mark1 = new MarkChart("rect", from, prop1);

		List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1));

		recommendation.setMarks(mrks);
	}

	// it completes all details of mark, encoding channels described in
	// zoominviz functions
	public void completeOssature_Viz_ZoomIn(VegaChart recommendation) {
		CompleteSize(recommendation);

		ActChart ac1 = new ActChart("x", "axeX");
		ActChart ac2 = new ActChart("x", true, -4);
		ActChart ac3 = new ActChart("y", "layout_start");
		ActChart ac4 = new ActChart("y", "layout_end");

		ActChart ac41 = new ActChart("color", "axeX2");

		Map<String, Object> ac5 = new HashMap<String, Object>();
		ac5.put("value", (double) 1);

		Map<String, Object> ac6 = new HashMap<String, Object>();
		ac6.put("value", 0.5);

		ActionChart act1 = new ActionChart(ac1, ac2, ac3, ac4, ac41);
		ActionChart act2 = new ActionChart(ac5);
		ActionChart act3 = new ActionChart(ac6);

		PropChart prop1 = new PropChart(act1, act2, act3);
		Map<String, String> from = new HashMap<String, String>();
		from.put("data", "stats3");

		MarkChart mark1 = new MarkChart("rect", from, prop1);

		List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1));

		recommendation.setMarks(mrks);
	}

	public void removeScale(List<ScaleChart> scls, String nameSca) {
		// TODO Auto-generated method stub
		// remove x scale constructed in skeleton
		Iterator<ScaleChart> iteSCA = scls.iterator();
		lpSCA: while (iteSCA.hasNext()) {
			ScaleChart objscale = iteSCA.next();
			if (objscale.getName().equals(nameSca)) {
				iteSCA.remove();
				break lpSCA;
			}
		}
	}

	public static int randInt(int min, int max) {
		int result = ThreadLocalRandom.current().nextInt(min, max + 1);
		return result;
	}

	public String buildJs(VegaChart chart) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(chart);
		return json;
	}

	/**
	 * Check if all information from the visualization are define oder not If
	 * one of the information are undefine method will return false If all
	 * information are define methode true
	 * 
	 * @param recommendvis
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Boolean completeCheck(VegaChart recommendvis) {
		for (Field field : recommendvis.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				if (field.get(recommendvis) == null) {
					return false;
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	private Boolean CompareSimilarity(List<String> conds, List<String> condsRec) {
		Collection<String> similar = new HashSet<String>(conds);
		Collection<String> different = new HashSet<String>();
		different.addAll(conds);
		different.addAll(condsRec);

		similar.retainAll(condsRec);
		different.removeAll(similar);
		if (different.size() == 0)
			return true;
		else
			return false;
	}

	// check whether the second list is a sub list of param 1
	private boolean CompareMarks(List<Integer> marksID, List<MarkGeneral> marks, Session session) {
		List<String> conds = new ArrayList<String>();
		List<String> condsRec = new ArrayList<String>();
		String ids = "";
		marks.forEach(x -> {
			condsRec.add(x.getType());
		});
		marksID.forEach(x -> {
			List mm = session.createQuery("select marktype from MarkTable where idmark= '" + x + "'").list();
			Iterator ite = mm.iterator();
			Object test = ite.next();
			conds.add(test.toString());
		});
		if (Collections.indexOfSubList(conds, condsRec) == -1)
			return false;
		else
			return true;
		// return CompareSimilarity(conds,condsRec);
	}

	// save reused axe and add it to vega chart also
	private void UpdateChartAndSaveReusedAxis(String typeAxe, Session session, VisualizationBean vis,
			VegaChart recommendation, int visID) {
		// take axis
		String idaxis = "";
		for (Integer k : vis.getAxisID()) {
			idaxis = k + "," + idaxis;
		}
		idaxis = idaxis.substring(0, idaxis.length() - 1);
		List<AxisTable> ax = session
				.createQuery(" from AxisTable where idaxis in (" + idaxis + ")  and type='" + typeAxe + "'   ").list();
		Iterator<AxisTable> iteAX = ax.iterator();
		AxisTable AXobject = iteAX.next();
		AxisChart newAxe = new AxisChart(AXobject.getTick(), typeAxe, typeAxe, AXobject.getTitle());
	
		
		if(newAxe.getType().equals("x"))
		{LabelAxeChart fill = new LabelAxeChart("black");
		LabelAxeChart angle = new LabelAxeChart(90);
		LabelAxeChart dx = new LabelAxeChart(22);
		LabelAxeChart font = new LabelAxeChart(12);
		Labels lab = new Labels(fill, angle, dx, font);
		Map<String, Labels> pr = new HashMap<String, Labels>();
		pr.put("labels", lab);
		newAxe.setProperties(pr);}
		
		
		
		List<AxisChart> tempoAX = recommendation.getAxes();
		tempoAX.add(newAxe);
		recommendation.setAxes(tempoAX);

		// store axis
		int axeUsageID = DistinctValues.connector.counter.newaxis_Usage_ID();
		AxisUsageTable AXUsageRow = new AxisUsageTable(axeUsageID, AXobject.getIdaxis(), visID, typeAxe);
		session.saveOrUpdate(AXUsageRow);
	}

	// before adding such dataset, verify whether it is already in the vega
	// specification or not.
	// if yes , modify vegachart spec by using the existing name during domain
	// construction of scale
	private boolean searchDataSetInVegaSpec(String currentData, List<DataChart> l_data, DomainChart domain) {
		VegaJsonMapping mapping = new VegaJsonMapping();
		// List<DataChart> l_data = recommendation.getData();
		boolean found = false;
		if (l_data.size() > 0) {
			lp: for (DataChart dset : l_data) {

				String test = mapping.ConvertToJson_Normal(dset.getValues());
				if (test.equals(currentData)) {
					domain.setData(dset.getName());
					found = true;
					break lp;

				}

			}
		}
		return found;
	}

	// find the appropriate previous scale
	private ScaleTable FindAppropriateScale(String namescale, String typescale, VisualizationBean vis,
			Session session) {
		String idscales = "";
		for (Integer k : vis.getScalesID()) {
			idscales = k + "," + idscales;
		}
		idscales = idscales.substring(0, idscales.length() - 1);
		List<ScaleTable> mm = session.createQuery(" from ScaleTable where idscale in (" + idscales
				+ ")  and typescale='" + typescale + "' and namescale like '" + namescale + "%'   ").list();
		if (mm.isEmpty())
			return null;
		else {
			Iterator<ScaleTable> ite = mm.iterator();
			ScaleTable scaleobject = ite.next();
			return scaleobject;
		}
	}

	// get a dataset already stored in DB
	private List<DisplayForm> getPreviousRange(int datasetID, Session session) {
		List<DisplayForm> yrange = new ArrayList<DisplayForm>();
		Query query = session.createQuery("select value from DatasetTable where iddataset = '" + datasetID + "'");
		query.setMaxResults(1);

		List<DisplayForm[]> rows = (List<DisplayForm[]>) query.list();

		Iterator<DisplayForm[]> iterator = rows.iterator();

		while (iterator.hasNext()) {

			DisplayForm[] obj = (DisplayForm[]) iterator.next();
			yrange.addAll(Arrays.asList(obj));

		}

		return yrange;
	}

	// if a scale is re-used, re-use also encoding channel with assumption
	// previous and current chart have the same mark
	private void useOfPreviousEncodingChannel(Session session, int scaleID, Map<String, Integer> mappingMarkID,
			List<Integer> marksReused) {
		String listMark = "";

		for (Integer x : marksReused) {
			listMark = listMark + x + ",";
		}

		for (Entry<String, Integer> elt : mappingMarkID.entrySet()) {
			List channels_X = session.createSQLQuery("select ChannelTable.idchannel, ChannelTable.channeltype "
					+ "from ChannelTable,ChannelUsageTable,MarkTable where "
					+ "ChannelTable.idchannel=ChannelUsageTable.idchannel and MarkTable.idmark= ChannelUsageTable.idmark"
					+ " and scale= '" + scaleID + "' " + "and   marktype='" + elt.getKey() + "' "
					+ "and   ChannelUsageTable.idmark in (" + listMark.substring(0, listMark.length() - 1) + ")")
					.list();
			Iterator<Object> ite12 = channels_X.iterator();
			while (ite12.hasNext()) {
				Object[] idch = (Object[]) ite12.next();

				// update here channel usage table,
				int chID = Integer.parseInt(idch[0].toString());
				String chIDType = idch[1].toString();
				int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
				
				ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, elt.getValue(),
						chIDType);
				if(elt.getKey().equals("line")){usageEncoRow = new ChannelUsageTable(chUsageID, chID, elt.getValue(),
						"stroke");}
				session.saveOrUpdate(usageEncoRow);

			}
		}
	}

	protected void CompleteSize(VegaChart viz_rec) {
		// TODO Auto-generated method stub
		if (viz_rec.getHeight() == 0) {

			viz_rec.setHeight(450);
		}
		if (viz_rec.getWidth() == 0) {

			viz_rec.setWidth(750);
		}
	}
	
	
	//this is for the case where porevious X axis does not cover all current data e.g. previous={a,b} current {a,b,c,d}
//in this case we need to preserve order of a and b and put randomly c and d
	private List<DisplayForm> UpdateXrange(List<DisplayForm> oldrange, List<DisplayForm> currentrange) {
		// TODO Auto-generated method stub
		Set<String> oldValues = new HashSet<String>();
		oldrange.forEach(x -> {
			oldValues.add(x.getAxeX());
		});

		Set<String> newValues = new HashSet<String>();
		currentrange.forEach(x -> {
			newValues.add(x.getAxeX());
		});
		
		if (oldValues.size()>=newValues.size()) {
			return oldrange;
		}
		else {
			for (String elt:newValues)
			{
				if (!oldValues.contains(elt))
				{
					DisplayForm pp= new DisplayForm(elt, (double)1234);
					oldrange.add(pp);
				}
			}
			return oldrange;
		}
		
	}

}
