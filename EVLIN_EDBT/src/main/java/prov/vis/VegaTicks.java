package prov.vis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.hibernate.Session;

import prov.chart.MarkGeneral;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import henry.database.AxisTable;
import henry.database.AxisUsageTable;
import henry.database.ChannelTable;
import henry.database.ChannelUsageTable;
import henry.database.ScaleTable;
import prov.act.DistinctValues;
import prov.chart.ActChart;
import prov.chart.ActPie;
import prov.chart.ActYheight;
import prov.chart.ActionChart;
import prov.chart.ActionChartScatter;
import prov.chart.ActionChartText;
import prov.chart.Aggregate;
import prov.chart.AxisChart;
import prov.chart.DataChart;
import prov.chart.DomainChart;
import prov.chart.Facet;
import prov.chart.Filter;
import prov.chart.Formula;
import prov.chart.LabelAxeChart;
import prov.chart.Labels;
import prov.chart.LegendChart;
import prov.chart.MarkChart;
import prov.chart.Marks;
import prov.chart.Pie;
import prov.chart.PredicatChart;
import prov.chart.PropChart;
import prov.chart.PropLegend;
import prov.chart.ScaleChart;
import prov.chart.Signal;
import prov.chart.Signal2;
import prov.chart.SignalChart;
import prov.chart.Stack;
import prov.chart.Stream;
import prov.chart.Transformation;
import prov.chart.VegaChart;
import prov.mybean.DisplayForm;
import prov.mybean.QueryForm;

public  class VegaTicks extends VegaVis {



	public VegaTicks(QueryForm query) {
		super();
		this.query = query;
	}

	public VegaTicks() {
		super();
		// TODO Auto-generated constructor stub
	}

	//is used for recommendation zoom in
			@Override
			public VegaChart PrintVisrec_2D(String ColorCode) {
				String labelY = GetY();
				String labelX = GetX();
				int width = 600;
				int height = 550;
				//List<String> vals = new ArrayList<String>();
				DataChart da = new DataChart("table"/*, vals*/);
				List<DataChart> data = new ArrayList<DataChart>();
				data.add(da);

				//// *********************scales*************************/
				/*Map<String, String> xs = new HashMap<String, String>();
				xs.put("data", "table");
				xs.put("field", "state");
				ScaleChart x = new ScaleChart("xscale", "ordinal", "width", true, xs);

				Map<String, String> ys = new HashMap<String, String>();
				ys.put("data", "table");
				ys.put("field", "cancellation_num");
				ScaleChart y = new ScaleChart("yscale", "height", true, ys);*/
				
				
				DomainChart xs = new DomainChart("table", "state");
				ScaleChart x = new ScaleChart("xscale", "ordinal", "width", true, xs,(double)1);
			
			
				DomainChart ys = new DomainChart("table", "cancellation_num");
				ScaleChart y = new ScaleChart("yscale", "height", ys, "linear");
			
				
				List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y));
				/********************* axis construction *************************/
				LabelAxeChart fill = new LabelAxeChart("black");
				LabelAxeChart angle = new LabelAxeChart(90);
				LabelAxeChart dx = new LabelAxeChart(20);
				LabelAxeChart font = new LabelAxeChart(13);
				Labels lab = new Labels(fill, angle, dx,font);
				
				Map<String, Labels> pr = new HashMap<String, Labels>();
				pr.put("labels", lab);
				
				LabelAxeChart fillTitle = new LabelAxeChart("black");
				LabelAxeChart fontTitle = new LabelAxeChart(17);
				Labels labTitle = new Labels(fillTitle,fontTitle);
				pr.put("title", labTitle);
				
				AxisChart axe1 = new AxisChart("x", "xscale", labelX, pr);
			
				LabelAxeChart filly = new LabelAxeChart("black");
				LabelAxeChart fonty = new LabelAxeChart(13);
				Labels laby = new Labels(filly, fonty);
				Map<String, Labels> pry = new HashMap<String, Labels>();
				pry.put("labels", laby);
				
				LabelAxeChart fillTitley = new LabelAxeChart("black");
				LabelAxeChart fontTitley = new LabelAxeChart(17);
				Labels labTitley = new Labels(fillTitley,fontTitley);
				pry.put("title", labTitley);
				
				AxisChart axe2 = new AxisChart("y", "yscale", labelY,pry);
				List<AxisChart> axis = new ArrayList<AxisChart>(Arrays.asList(axe1, axe2));
				// AxisChart ax= new AxisChart(axis);

				/********************* marks construction *************************/
				ActChart ac1x = new ActChart("xscale", "state");
			
				ActChart ac3y = new ActChart("yscale", "cancellation_num");
				ActChart acfill = new ActChart("Teal");
				//ActChart acfillopacity = new ActChart((float) 0.5);
				ActChart acsize = new ActChart(13);
				ActChart ac5 = new ActChart(ColorCode);
				ActChart ac6 = new ActChart("red");
				
				Map<String, Object> acfillopacity = new HashMap<String, Object>();
				acfillopacity.put("value", 0.5);
				
				
				ActionChartScatter act1 = new ActionChartScatter(ac1x, ac3y,acfill, acfillopacity,acsize)	;
				ActionChart act2 = new ActionChart(ac5);
				ActionChart act3 = new ActionChart(ac6);

				PropChart prop1 = new PropChart(act1, act2, act3);
				Map<String, String> from = new HashMap<String, String>();
				from.put("data", "table");

				MarkChart mark1 = new MarkChart("symbol", from, prop1);

				
				
				ActChart acc1 = new ActChart("center");
				ActChart acc2 = new ActChart("#333");
				ActChart acc3 = new ActChart("xscale", "tooltip.state", 0);
				//ActChart acc4 = new ActChart("xscale", true, (float) 0.5);
				ActChart acc5 = new ActChart("yscale", "tooltip.cancellation_num", -5);

				Map<String, String> acc6 = new HashMap<String, String>();
				acc6.put("signal", "tooltip.cancellation_num");

				ActionChart acct1 = new ActionChart(acc2, acc1);
				ActionChartText acct2 = new ActionChartText(acc3, acc5, acc6);
			

				PropChart prop2 = new PropChart(acct1, acct2);
				MarkChart mark2 = new MarkChart("text", prop2);
				List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1, mark2));

				/********************* Signal construction *************************/

				Stream st1 = new Stream("rect:mouseover", "datum");
				Stream st2 = new Stream("rect:mouseout", "{}");
				
				Stream st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" + labelX
						+ "&state='+datum.state+'&chiffre='+datum.cancellation_num,'_self')");
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

			@Override
			public void Complete_Rec_Viz_ZoomIn(VegaChart recommendation, String webField, String old, String type,
					List<DisplayForm> datasetToGiveColor, int visid, Map<String, Integer> marksElts, Session session) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void Complete_Rec_Viz_2D(VegaChart recommendation, List<DisplayForm> datasetToGiveColor,
					int visID, Map<String, Integer> mappingMarkID, Session session, String type) {
				// TODO Auto-generated method stub
				
			}
		
		
		
			@Override
			public void Complete_Rec_Viz_Extension(VegaChart recommendation, String webField, String old, String type,
					List<DisplayForm> datasetToGiveColor, int visid, Map<String, Integer> marksElts, Session session) {
				// TODO Auto-generated method stub
				
				
			}

			

		
	
	
	
	

}
