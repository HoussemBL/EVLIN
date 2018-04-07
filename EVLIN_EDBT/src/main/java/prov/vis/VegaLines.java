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
import prov.chart.ActionChartLine;
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
import prov.chart.MarkGeneral;

public  class VegaLines extends VegaVis {



	public VegaLines(QueryForm query) {
		super();
		this.query = query;
	}

	public VegaLines() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	//is used for recommendation zoom in
	//for this fct we have special transformations that shoul be used to be sure that x axis is int
			@Override
			public VegaChart PrintVisrec_2D(String ColorCode) {
				String labelY = GetY();
				String labelX = GetX();
				int width = 600;
				int height = 550;
				List</*String*/DisplayForm> vals = new ArrayList</*String*/DisplayForm>();
			
			
				
				/*?????? here we add some transformation specific to line chart*/
				Transformation sortTrans = new Transformation("sort","axeX");
				Formula formula = new Formula( "axeX", "parseInt(datum.axeX)" );
				List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(formula,sortTrans));
				DataChart da = new DataChart("table", vals,tt4);
				
				
				
				List<DataChart> data = new ArrayList<DataChart>();
				data.add(da);

				//// *********************scales*************************/

				DomainChart xs = new DomainChart("table", "axeX");
				ScaleChart x = new ScaleChart("xscale", "ordinal", "width", true, xs,(double)1);
			
			
				DomainChart ys = new DomainChart("table", "AggNumber");
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

				
				
				
				/********************* line mark construction *************************/
				ActChart ac1xl = new ActChart("xscale", "axeX");
			
				ActChart ac3yl = new ActChart("yscale", "AggNumber");
				//ActChart acfill_line = new ActChart("#fff");
				
				ActChart acstokewidth = new ActChart(3);
				ActChart ac5line = new ActChart("steelblue");
				ActChart ac6line = new ActChart("monotone");
				
			
				
				
				ActionChartLine act1line = new ActionChartLine(ac1xl, ac3yl,/*acfill_line,*/ ac5line, acstokewidth,ac6line)	;
			

				PropChart prop1_line = new PropChart(act1line);
				Map<String, String> from_line = new HashMap<String, String>();
				from_line.put("data", "table");

				MarkChart mark_line = new MarkChart("line", from_line, prop1_line);

				
				
		
				
				
				/********************* symbol mark construction *************************/
				ActChart ac1x = new ActChart("xscale", "axeX");
			
				ActChart ac3y = new ActChart("yscale", "AggNumber");
				ActChart acfill = new ActChart("#fff");
				//ActChart acfillopacity = new ActChart((float) 0.5);
				ActChart acsize = new ActChart(49);
				ActChart ac5 = new ActChart(ColorCode);
				ActChart ac6 = new ActChart("red");
				ActChart ac_st = new ActChart("#000");
				ActChart ac_stwidth= new ActChart(1);
				
				
			
				ActionChartScatter act1 = new ActionChartScatter(ac1x, ac3y,acfill, ac_st, ac_stwidth,acsize);
				
				ActionChart act2 = new ActionChart(ac5);
				ActionChart act3 = new ActionChart(ac6);

				PropChart prop1 = new PropChart(act1, act2, act3);
				Map<String, String> from = new HashMap<String, String>();
				from.put("data", "table");

				MarkChart mark1 = new MarkChart("symbol", from, prop1);

				/********************* text mark construction *************************/
				
				ActChart acc1 = new ActChart("center");
				ActChart acc2 = new ActChart("#333");
				ActChart acc3 = new ActChart("xscale", "tooltip.axeX", 0);
				//ActChart acc4 = new ActChart("xscale", true, (float) 0.5);
				ActChart acc5 = new ActChart("yscale", "tooltip.cancellation_num", -5);

				Map<String, String> acc6 = new HashMap<String, String>();
				acc6.put("signal", "tooltip.cancellation_num");

				ActionChart acct1 = new ActionChart(acc2, acc1);
				ActionChartText acct2 = new ActionChartText(acc3, acc5, acc6);
			

				PropChart prop2 = new PropChart(acct1, acct2);
				MarkChart mark2 = new MarkChart("text", prop2);
				List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark_line,mark1, mark2));

				/********************* Signal construction *************************/
				Stream st0 = new Stream("click", "datum._id");
				Stream st1 = new Stream("mouseover", "datum");
				Stream st2 = new Stream("mouseout", "{}");
				
				Stream st3 = new Stream("dblclick", "open('result.jsp?cancel=" + labelX
						+ "&state='+datum.axeX+'&chiffre='+datum.cancellation_num,'_self')");
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
			public VegaChart PrintVisrecZoomIn(String webField,String old,String type/*,List<DisplayForm> datasetToGiveColor*/){
				String	aggregationFct= GetOperation();
				String labelY = GetY();
				String old2 =old;
				String labelX2 = GetX2(webField);
				
						String[] res = old.split(Pattern.quote("."));
				if (res.length > 1) {
					old2 =old.split(Pattern.quote("."))[1];
				}
				String labelX22 = GetX2(old2);
				int width = 600;
				int height = 500;
				
				
				DataChart src_map = new DataChart("mapping");
				DataChart src = new DataChart("source");
				
				
				Transformation sortTrans = new Transformation("sort","axeX2");
				
				List<String> listfields = new ArrayList<String>(Arrays.asList("axeX", "axeX2"));
				Map<String, Object> TransDetails= new HashMap<String, Object>();
				TransDetails.put("field", "AggNumber");
				List<String> listaggs = new ArrayList<String>(Arrays.asList(aggregationFct));
				TransDetails.put("ops", listaggs);
				TransDetails.put("as", "AggNumber");
				
				List<Map<String, Object>> defAgg = new ArrayList<Map<String, Object>>(Arrays.asList(TransDetails));
				Aggregate ag11 = new Aggregate("aggregate", listfields, defAgg );
			
				List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(sortTrans,ag11));
				
				List<DisplayForm> vals = new ArrayList<DisplayForm>();
				DataChart da = new DataChart("table", vals,tt4);
			

				List<DataChart> data = new ArrayList<DataChart>(Arrays.asList(src_map,src,da));
			
				/*********************scales*************************/
				

				DomainChart xs = new DomainChart("source", "axeX");
				ScaleChart x = new ScaleChart("x", "ordinal", "width", true, xs,(double)1);
			
			
				DomainChart ys = new DomainChart("table", "AggNumber");
				ScaleChart y = new ScaleChart("y", "height", ys, "linear");
			
				Map<String, String> sorting = new HashMap<String, String>();
				sorting.put("field", "axeX2");
				sorting.put("op", "max"); 
			
				Map<String, String> sort = new HashMap<String, String>();
				sort.put("field", "_id");
				sort.put("op", "min");
				DomainChart colors2 = new DomainChart("mapping", "axeX", sort);
				DomainChart colors2range = new DomainChart("mapping", "color");
				ScaleChart z_test = new ScaleChart("color", "ordinal", colors2, colors2range);
				
				
				
				
				List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y, /*z,*/z_test));
				/********************* axis construction *************************/
				LabelAxeChart fill = new LabelAxeChart("black");
				LabelAxeChart angle = new LabelAxeChart(90);
				LabelAxeChart dx = new LabelAxeChart(22);
				LabelAxeChart font = new LabelAxeChart(12);
				Labels lab = new Labels(fill, angle, dx,font);
			
				Map<String, Labels> pr = new HashMap<String, Labels>();
				pr.put("labels", lab);
				
				LabelAxeChart fillTitle = new LabelAxeChart("black");
				LabelAxeChart fontTitle = new LabelAxeChart(17);
				Labels labTitle = new Labels(fillTitle,fontTitle);
				pr.put("title", labTitle);
				
				AxisChart axe1 = new AxisChart("x", "x", labelX22, pr);
			
				
				
				LabelAxeChart filly = new LabelAxeChart("black");
				LabelAxeChart fonty = new LabelAxeChart(12);
				Labels laby = new Labels(filly, fonty);
				Map<String, Labels> pry = new HashMap<String, Labels>();
				pry.put("labels", laby);
				
				LabelAxeChart fillTitley = new LabelAxeChart("black");
				LabelAxeChart fontTitley = new LabelAxeChart(17);
				Labels labTitley = new Labels(fillTitley,fontTitley);
				pry.put("title", labTitley);
				
				AxisChart axe2 = new AxisChart("y", "y", labelY,pry);
			
				List<AxisChart> axis = new ArrayList<AxisChart>(Arrays.asList(axe1, axe2));
				// AxisChart ax= new AxisChart(axis);
				/********************* marks construction *************************/
				
				
				/********************* line mark construction *************************/
				ActChart ac1xl = new ActChart("x", "axeX");
				ActChart ac3yl = new ActChart("y", "AggNumber");
				ActChart acstokewidth = new ActChart(3);
				ActChart ac5stroke = new ActChart("color", "axeX2");
				ActChart ac6line = new ActChart("monotone");
		
				ActionChartLine act1line = new ActionChartLine(ac1xl, ac3yl, ac5stroke, acstokewidth,ac6line)	;
				ActionChart act2line = new ActionChartLine(ac5stroke);
				
				ActChart ac6stroke = new ActChart("red");
				ActionChart act3line = new ActionChartLine(ac6stroke);

				PropChart prop1_line = new PropChart(act1line,act2line,act3line);
			

				MarkChart mark_line = new MarkChart("line",  prop1_line);

				
				
				/********************* symbol mark construction *************************/
				ActChart ac1 = new ActChart("x", "axeX");
				ActChart ac3 = new ActChart("y", "AggNumber");
				ActChart accolor = new ActChart("black");
				ActChart acsize=new ActChart(50);
				ActChart ac6 = new ActChart("red");
				ActChart acstrokeWidth=new ActChart(0.2);
				
				
				ActionChartScatter act1 = new ActionChartScatter(ac1,ac3,accolor,accolor, acstrokeWidth,acsize);
				
				ActionChart act2 = new ActionChart(accolor);
				ActionChart act3 = new ActionChart(ac6);
			
				PropChart prop1 = new PropChart(act1, act2, act3);
				
				MarkChart mark1 = new MarkChart("symbol",prop1);
				List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark_line,mark1));
				
				
	
						List<String> listVal = new ArrayList<String>(Arrays.asList("axeX2"));
						//construction below is for facet
						Aggregate ag1 = new Aggregate(listVal);
						List<Transformation> trans = new ArrayList<Transformation>(Arrays.asList(ag1));	
						
						
						Map<String, Object> from = new HashMap<String, Object>();
						from.put("data", "table");
						from.put("transform", trans);
						Marks groupMrk= new Marks(from, mrks);
						
						
				/******text mark*******/		
						ActChart acc1 = new ActChart("center");
						ActChart acc2 = new ActChart("#333");
						ActChart acc3 = new ActChart("x", "tooltip.axeX", 25);
			
						ActChart acc5 = new ActChart("y", "tooltip.AggNumber", 0);

						Map<String, String> acc6 = new HashMap<String, String>();
						acc6.put("signal", "tooltip.AggNumber");

						ActionChart acct1 = new ActionChart(acc2, acc1);
						ActionChartText acct2 = new ActionChartText(acc3, acc5, acc6);
					

						PropChart prop2 = new PropChart(acct1, acct2);
						Marks mark2 = new Marks("text", prop2);
						
						
				/*++++++++++mark final********* */		
						
						List<MarkGeneral> finalmrks = new ArrayList<MarkGeneral>(Arrays.asList(groupMrk, mark2));		
						
						
				/********************************* legends *********************************/
			
				List<LegendChart> legends = new ArrayList<LegendChart>();
				PropLegend prL = new PropLegend();
				LegendChart leg = new LegendChart(labelX2, "color", prL);
				legends.add(leg);
				
				
				/********************* Signal construction *************************/

				Stream st1 = new Stream("line:mouseover", "datum");
				Stream st2 = new Stream("line:mouseout", "{}");
				Stream st3 = new Stream("line:dblclick",
						"open('result.jsp?cancel="+old2+"&state='+datum.axeX+'&chiffre='+datum.sum_AggNumber+'&type="+type+"','_self')");
				List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
				Map<String, String> init = new HashMap<String, String>();
				Signal2 sig = new Signal2("tooltip", init, sts);
				List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));

				
				/********************* vega construction *************************/
				List<PredicatChart> preid=new ArrayList<PredicatChart>();
				VegaChart chart = new VegaChart(700,600, data, scls,  axis, finalmrks,sigs,preid, legends);
				
				
				return chart;
			}

		
		
	
			
			
			
			
			
			// it works exactly like PrinrecZoomIn
			@Override 
			public void Complete_Rec_Viz_ZoomIn(VegaChart recommendation, String webField, String old, String type,
					List<DisplayForm> datasetToGiveColor, int visid, Map<String,Integer> marksElts, Session session) {
				// TODO Auto-generated method stub
				boolean xscale = false;
				boolean yscale = false;
				boolean colorscale = false;

				String	real_labelX=/*GetXRealName();*/GetRealNameCond(webField);
				String labelY = GetY();
				String old2 = old;
				String labelX2 = GetX2(webField);
				String aggregationFct = GetOperation();
				String[] res = old.split(Pattern.quote("."));
				// int mID = DistinctValues.connector.counter.newMarkPropID()-1;
				if (res.length > 1) {
					old2 = old.split(Pattern.quote("."))[1];
				}
				String labelX22 = GetX2(old2);

				if (recommendation.getWidth() == 0) {
					recommendation.setWidth(600);
				}
				if (recommendation.getHeight() == 0) {
					recommendation.setHeight(500);
				}

			/*
				Transformation sortTrans = new Transformation("sort","axeX2");
				
				List<String> listfields = new ArrayList<String>(Arrays.asList("axeX", "axeX2"));
				Map<String, Object> TransDetails= new HashMap<String, Object>();
				TransDetails.put("field", "AggNumber");
				List<String> listaggs = new ArrayList<String>(Arrays.asList(aggregationFct));
				TransDetails.put("ops", listaggs);
				TransDetails.put("as", "AggNumber");
				
				List<Map<String, Object>> defAgg = new ArrayList<Map<String, Object>>(Arrays.asList(TransDetails));
				Aggregate ag11 = new Aggregate("aggregate", listfields, defAgg );
			
				List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(sortTrans,ag11));
					List<DisplayForm> vals = new ArrayList<DisplayForm>();
				*/
				List<DisplayForm> vals = new ArrayList<DisplayForm>();
				Transformation sortTrans = new Transformation("sort","axeX");
				Formula formula = new Formula( "axeX", "parseInt(datum.axeX)" );
				List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(formula,sortTrans));
				
				
				
				DataChart da = new DataChart("table", vals,tt4);
				
			
			
				
				List<DataChart> data = new ArrayList<DataChart>(Arrays.asList(da));
				/********************* scales *************************/
				List<ScaleChart> scls = recommendation.getScales();
				List<AxisChart> tempo_axis = recommendation.getAxes();
				for (ScaleChart sca : recommendation.getScales()) {
					if (sca.getName().equals("x") && sca.getType() != null) {
						sca.setPadding((double)1);
						xscale = true;

					}
					if (sca.getName().equals("y") && sca.getType() != null) {
						yscale = true;
					}
					if (sca.getName().equals("color") && sca.getType() != null) {
						colorscale = true;
					}
				}
				
				if(xscale){
					
					
					for (AxisChart ax: recommendation.getAxes())
					{	if(ax.getType().equals("x"))
					{ax.setTitle(labelX22);}
					}
				}
				
				
				if (!xscale) {
					DomainChart xs = new DomainChart("table", "axeX");
					ScaleChart x_sc = new ScaleChart("x", "ordinal", "width", true, xs,(double) 1);
					
					// remove x scale constructed in skeleton
					removeScale(scls, "x");
					scls.add(x_sc);

					// automatically also add axis
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
					AxisTable AXRow = DistinctValues.connector.constructAxisRow(axeID, labelX22, 10, "x", session, "x");
					int axeUsageID = DistinctValues.connector.counter.newaxis_Usage_ID();
					AxisUsageTable AXUsageRow = new AxisUsageTable(axeUsageID, axeID, visid, "x");
					session.saveOrUpdate(AXRow);
					session.saveOrUpdate(AXUsageRow);
					
					
				//==> store x encoding
					for (  Entry<String, Integer> m : marksElts.entrySet()) { 
						if(m.getKey().equals("line") || m.getKey().equals("symbol"))
						{		int chID = DistinctValues.connector.counter.newEncodingID();
					int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
					ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "x", "axeX", session, scID);
					ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(), idch.getChanneltype());

			
					session.saveOrUpdate(idch);
					session.saveOrUpdate(usageEncoRow);
						}}
					
					
					
					
					
					
				
					
				}
				if (!yscale) {
					//DomainChart ys = new DomainChart("foraxeY", "sum_AggNumber");
					DomainChart ys = new DomainChart("table", "AggNumber");
					ScaleChart y_sc = new ScaleChart("y", "height", ys, "linear");
					removeScale(scls, "y");
					scls.add(y_sc);

					// automatically also add axis
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
					AxisTable AXRow = DistinctValues.connector.constructAxisRow(axeID, labelY, 10, "y", session, "y");
					int axeUsageID = DistinctValues.connector.counter.newaxis_Usage_ID();
					AxisUsageTable AXUsageRow = new AxisUsageTable(axeUsageID, axeID, visid, "y");
					session.saveOrUpdate(AXRow);
					session.saveOrUpdate(AXUsageRow);
					
					
					//store y encoding
					for (  Entry<String, Integer> m : marksElts.entrySet()) { 
						if(m.getKey().equals("line") || m.getKey().equals("symbol")){
					
					int chID = DistinctValues.connector.counter.newEncodingID();
					int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
					ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "y", "AggNumber", session, scID);
					ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(), idch.getChanneltype());
					session.saveOrUpdate(idch);
					session.saveOrUpdate(usageEncoRow);}}
				
					
					
					
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
					// store axis inDB
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
					
					//--> store stroke encdoing
					for (  Entry<String, Integer> m : marksElts.entrySet()) { 
						if(m.getKey().equals("line") ){
					int chID = DistinctValues.connector.counter.newEncodingID();
					int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
					ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "stroke", "axeX", session, scID);
					ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(), idch.getChanneltype());
					session.saveOrUpdate(idch);
					session.saveOrUpdate(usageEncoRow);}}
					
				}

				List<DataChart> dats = new ArrayList<DataChart>(recommendation.getData());
				dats.addAll(data);
				recommendation.setData(dats);

				/********************* marks construction *************************/
				completeOssature_Viz_ZoomIn(recommendation);


				/********************************* legends *********************************/

				List<LegendChart> legends = new ArrayList<LegendChart>();
				PropLegend prL = new PropLegend();
				LegendChart leg = new LegendChart(labelX2, "color", prL);
				legends.add(leg);

				recommendation.setLegends(legends);
				/********************* Signal construction *************************/
				
				Stream st1 = new Stream("symbol:mouseover", "datum");
				Stream st2 = new Stream("symbol:mouseout", "{}");
			//Stream st3 = new Stream("symbol:dblclick",
						//"open('result.jsp?cancel="+old2+"&state='+datum.axeX+'&chiffre='+datum.sum_AggNumber+'&type="+type+"','_self')");
			Stream st3 = new Stream("symbol:dblclick", "open('result.jsp?cancel=" +old2+ "&state='+datum.axeX+'&cond="+real_labelX+",'+datum.axeX2+'&type=" + type + "','_self')");
			
				List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
				Map<String, String> init = new HashMap<String, String>();
				Signal2 sig = new Signal2("tooltip", init, sts);
				List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));
				
				recommendation.setSignals(sigs);
			}			
			
			
			
			
			
			
			
			
			
			
			
		
			
			
			//it completes all details of mark, encoding channels described in zoominviz functions
			@Override
			public void completeOssature_Viz_ZoomIn(VegaChart recommendation) {
				// TODO Auto-generated method stub
				/********************* line mark construction *************************/
				ActChart ac1xl = new ActChart("x", "axeX");
				ActChart ac3yl = new ActChart("y", "AggNumber");
				ActChart acstokewidth = new ActChart(3);
				ActChart ac5stroke = new ActChart("color", "axeX2");
				ActChart ac6line = new ActChart("monotone");
		
				ActionChartLine act1line = new ActionChartLine(ac1xl, ac3yl, ac5stroke, acstokewidth,ac6line)	;
				ActionChart act2line = new ActionChartLine(ac5stroke);
				
				ActChart ac6stroke = new ActChart("red");
				ActionChart act3line = new ActionChartLine(ac6stroke);

				PropChart prop1_line = new PropChart(act1line,act2line,act3line);
			

				MarkChart mark_line = new MarkChart("line",  prop1_line);

				
				
				/********************* symbol mark construction *************************/
				ActChart ac1 = new ActChart("x", "axeX");
				ActChart ac3 = new ActChart("y", "AggNumber");
				ActChart accolor = new ActChart("black");
				ActChart acsize=new ActChart(50);
				ActChart ac6 = new ActChart("red");
				ActChart acstrokeWidth=new ActChart(0.2);
				
				
				ActionChartScatter act1 = new ActionChartScatter(ac1,ac3,accolor,accolor, acstrokeWidth,acsize);
				
				ActionChart act2 = new ActionChart(accolor);
				ActionChart act3 = new ActionChart(ac6);
			
				PropChart prop1 = new PropChart(act1, act2, act3);
				
				MarkChart mark1 = new MarkChart("symbol",prop1);
				List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark_line,mark1));
				
	
						List<String> listVal = new ArrayList<String>(Arrays.asList("axeX2"));
						//construction below is for facet
						Aggregate ag1 = new Aggregate(listVal);
						List<Transformation> trans = new ArrayList<Transformation>(Arrays.asList(ag1));	
						
						
						Map<String, Object> from = new HashMap<String, Object>();
						from.put("data", "table");
						from.put("transform", trans);
						Marks groupMrk= new Marks(from, mrks);
						
						
				/******text mark*******/		
						ActChart acc1 = new ActChart("center");
						ActChart acc2 = new ActChart("#333");
						ActChart acc3 = new ActChart("x", "tooltip.axeX", 25);
			
						ActChart acc5 = new ActChart("y", "tooltip.AggNumber", 0);

						Map<String, String> acc6 = new HashMap<String, String>();
						acc6.put("signal", "tooltip.AggNumber");

						ActionChart acct1 = new ActionChart(acc2, acc1);
						ActionChartText acct2 = new ActionChartText(acc3, acc5, acc6);
					

						PropChart prop2 = new PropChart(acct1, acct2);
						Marks mark2 = new Marks("text", prop2);
						
						
				/*++++++++++mark final********* */		
						
						List<MarkGeneral> finalmrks = new ArrayList<MarkGeneral>(Arrays.asList(groupMrk, mark2));

				recommendation.setMarks(finalmrks);	
			}	
			
			
			
			
			/**********
			 * construct vega specification with minimum specification
			 *************/
			@Override
			public VegaChart BuildSekelton() {
				VegaChart chart = new VegaChart();
				
		//scales
				ScaleChart x = new ScaleChart("x");
				ScaleChart y = new ScaleChart("y");

				ScaleChart z_test = new ScaleChart("color");
				List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y, z_test));

				chart.setScales(scls);
				
				
				//marks
				MarkChart mark1 = new MarkChart("line");
				MarkChart mark2 = new MarkChart("symbol");
				List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1,mark2));
				chart.setMarks(mrks);
				return chart;
			}
		
	
			@Override
			public void Complete_Rec_Viz_2D(VegaChart recommendation, List<DisplayForm> datasetToGiveColor,
					int visID, Map<String, Integer> mappingMarkID, Session session,String type) {

				boolean xscale = false;
				boolean yscale = false;
				boolean colorscale = false;
				String labelY = GetY();
				String labelX = GetX();
				String attributeX = GetXName();
				if (recommendation.getWidth() == 0) {
					recommendation.setWidth(900);
				}
				if (recommendation.getHeight() == 0) {
					recommendation.setHeight(550);
				}

				DataChart da = new DataChart("table");
				List<DataChart> data = new ArrayList<DataChart>(Arrays.asList(da));
				List<DataChart> dats = new ArrayList<DataChart>(recommendation.getData());
				dats.addAll(data);
				recommendation.setData(dats);

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

				
				//case "state of destination" axis inheriting  title "state"
				if(xscale)
				{
				lp:	for (AxisChart axe: recommendation.getAxes()){
						if(axe.getType().equals("x"))
						{ axe.setTitle(labelX);
						break lp;}
					}
				}
				
				
				if (!xscale) {

					// construct x scale
					DomainChart xs = new DomainChart("table", "axeX");
					ScaleChart x_sc = new ScaleChart("x", "ordinal", "width", true, xs,(double)1);
					// remove x scale constructed in skeleton
					removeScale(scls, "x");
					scls.add(x_sc);

					// automatically also add axis
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

					AxisChart axe1 = new AxisChart("x", "x", labelX, pr);
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
					AxisTable AXRow = DistinctValues.connector.constructAxisRow(axeID, labelX, 7, "x", session, "x");
					int axeUsageID = DistinctValues.connector.counter.newaxis_Usage_ID();
					AxisUsageTable AXUsageRow = new AxisUsageTable(axeUsageID, axeID, visID, "x");
					session.saveOrUpdate(AXRow);
					session.saveOrUpdate(AXUsageRow);

					// store the new encoding channels
				
					for (Entry<String, Integer> m : mappingMarkID.entrySet()) {
						if (m.getKey().equals("symbol")|| m.getKey().equals("line")) {
							int chID = DistinctValues.connector.counter.newEncodingID();
							int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
							ChannelTable idch_X_encoding = DistinctValues.connector.constructChannelRow(chID, "x", "axeX", session, scID);
							ChannelUsageTable usageEncoRowX = new ChannelUsageTable(chUsageID, chID, m.getValue(),
									idch_X_encoding.getChanneltype());

							
							session.saveOrUpdate(idch_X_encoding);
							session.saveOrUpdate(usageEncoRowX);
				
						}
					}
				}

				if (!yscale) {
					// building y axis
					DomainChart ys = new DomainChart("table", "AggNumber");
					ScaleChart y_sc = new ScaleChart("y", "height", ys, "linear");
					removeScale(scls, "y");
					scls.add(y_sc);

					// automatically also add axis
					LabelAxeChart filly = new LabelAxeChart("black");
					LabelAxeChart fonty = new LabelAxeChart(13);
					Labels laby = new Labels(filly, fonty);
					Map<String, Labels> pry = new HashMap<String, Labels>();
					pry.put("labels", laby);

					LabelAxeChart fillTitley = new LabelAxeChart("black");
					LabelAxeChart fontTitley = new LabelAxeChart(17);
					Labels labTitley = new Labels(fillTitley, fontTitley);
					pry.put("title", labTitley);

					AxisChart axe2 = new AxisChart("y", "y", labelY, pry);
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
					AxisTable AXRow = DistinctValues.connector.constructAxisRow(axeID, labelY, 7, "y", session, "y");
					int axeUsageID = DistinctValues.connector.counter.newaxis_Usage_ID();
					AxisUsageTable AXUsageRow = new AxisUsageTable(axeUsageID, axeID, visID, "y");
					session.saveOrUpdate(AXRow);
					session.saveOrUpdate(AXUsageRow);

					for (Entry<String, Integer> m : mappingMarkID.entrySet()) {
						if (m.getKey().equals("symbol")|| m.getKey().equals("line")) {
							// store encodings
							int chID = DistinctValues.connector.counter.newEncodingID();

							int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
							ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "y", "AggNumber", session,
									scID);
							ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(),
									idch.getChanneltype());

							
							session.saveOrUpdate(idch);
							session.saveOrUpdate(usageEncoRow);
						
						}
					}
				}
				if (!colorscale) {
					// be careful if nothing here use fill without scale

					for (Entry<String, Integer> m : mappingMarkID.entrySet()) {
						if ( m.getKey().equals("line")) {

							int chID22 = DistinctValues.connector.counter.newEncodingID();
							int chUsageID22 = DistinctValues.connector.counter.newchannelUsage_ID();
							ChannelTable idch22 = new ChannelTable("stroke", "steelblue", chID22);
							ChannelUsageTable usageEncoRow22 = new ChannelUsageTable(chUsageID22, chID22, m.getValue(),
									idch22.getChanneltype());
							session.saveOrUpdate(idch22);
							session.saveOrUpdate(usageEncoRow22);
						}
						if(m.getKey().equals("symbol")){
							int chID22 = DistinctValues.connector.counter.newEncodingID();
							int chUsageID22 = DistinctValues.connector.counter.newchannelUsage_ID();
							ChannelTable idch22 = new ChannelTable("fill", "#728f99", chID22);
							ChannelUsageTable usageEncoRow22 = new ChannelUsageTable(chUsageID22, chID22, m.getValue(),
									idch22.getChanneltype());
							session.saveOrUpdate(idch22);
							session.saveOrUpdate(usageEncoRow22);
						}

					}
				}

				/********************* marks construction *************************/
			
				completeOssature_Viz_2D(recommendation);
				/********************* Signal construction *************************/
				Stream st1 = new Stream("mouseover", "datum");
				Stream st2 = new Stream("mouseout", "{}");
				Stream st3 ;
		if(type.equals("Principal")){
			 st3 = new Stream("dblclick", "open('result.jsp?cancel=" + attributeX
					+ "&state='+datum.axeX+'&chiffre='+datum.AggNumber,'_self')");
		}
		else{	 st3 = new Stream("dblclick", "open('result.jsp?cancel=" + attributeX
						+ "&state='+datum.axeX+'&chiffre='+datum.AggNumber+'&type="+type+"','_self')");
		
		
		}

				List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
				Map<String, String> init = new HashMap<String, String>();
				Signal2 sig = new Signal2("tooltip", init, sts);
				List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));

				/*********************/

				recommendation.setSignals(sigs);

			}
			
			
			
			@Override
			protected void completeOssature_Viz_2D(VegaChart recommendation) {
				ActionChartLine act1line;
				CompleteSize(recommendation);
				boolean rgcolor = false;
				//PropChart prop1 = null;
				lp: for (DataChart elt : recommendation.getData()) {
					if (elt.getName().equals("mapping")) {
						rgcolor = true;
						break lp;
					}
				}

				lp: for (DataChart elt : recommendation.getData()) {
					if (elt.getName().equals("table")) {
					
						Transformation sortTrans = new Transformation("sort","axeX");
						Formula formula = new Formula( "axeX", "parseInt(datum.axeX)" );
						List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(formula,sortTrans));
						elt.setTransform(tt4);
						break lp;
					}
				}
				
				
				
				
				/********************* line mark construction *************************/
				ActChart ac1xl = new ActChart("x", "axeX");			
				ActChart ac3yl = new ActChart("y", "AggNumber");
				ActChart acstokewidth = new ActChart(3);
				
				ActChart ac6line = new ActChart("monotone");
			
				if (!rgcolor) {
					ActChart ac5line = new ActChart("steelblue");
		
					act1line = new ActionChartLine(ac1xl, ac3yl, ac5line, acstokewidth, ac6line);
		
				} else {
					ActChart acline = new ActChart("color", "axeX2");
					act1line = new ActionChartLine(ac1xl, ac3yl, acline, acstokewidth, ac6line);
				}
				
				PropChart prop1_line = new PropChart(act1line);
				Map<String, String> from_line = new HashMap<String, String>();
				from_line.put("data", "table");

				MarkChart mark_line = new MarkChart("line", from_line, prop1_line);
			


				
				
				/********************* symbol mark construction *************************/
				ActChart ac1x = new ActChart("x", "axeX");
				
				ActChart ac3y = new ActChart("y", "AggNumber");
				ActChart acfill = new ActChart("#fff");
				ActChart acsize = new ActChart(49);
				ActChart ac5 = new ActChart("#fff");
				ActChart ac6 = new ActChart("red");
				ActChart ac_st = new ActChart("#000");
				ActChart ac_stwidth= new ActChart(1);
				
				
			
				ActionChartScatter act1 = new ActionChartScatter(ac1x, ac3y,acfill, ac_st, ac_stwidth,acsize);		
				ActionChart act2 = new ActionChart(ac5);
				ActionChart act3 = new ActionChart(ac6);

				PropChart propSYmbol = new PropChart(act1, act2, act3);
				Map<String, String> from = new HashMap<String, String>();
				from.put("data", "table");

				MarkChart mark1 = new MarkChart("symbol", from, propSYmbol );
				/****text mark*****/
				ActChart acc1 = new ActChart("center");
				ActChart acc2 = new ActChart("#333");
				ActChart acc3 = new ActChart("x", "tooltip.axeX2", 0);
				ActChart acc5 = new ActChart("y", "tooltip.AggNumber", -5);
				Map<String, String> acc6 = new HashMap<String, String>();
				acc6.put("signal", "tooltip.AggNumber");
				ActionChart acct1 = new ActionChart(acc2, acc1);
				ActionChartText acct2 = new ActionChartText(acc3, acc5, acc6);
			

				PropChart prop2 = new PropChart(acct1, acct2);
				MarkChart mark2 = new MarkChart("text", prop2);
				List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark_line,mark1, mark2));
				recommendation.setMarks(mrks);

			}
			
			
			
			
			// it is the code of Zoom in
						@Override 
						public void Complete_Rec_Viz_Extension(VegaChart recommendation, String webField, String old, String type,
								List<DisplayForm> datasetToGiveColor, int visid, Map<String,Integer> marksElts, Session session) {
							// TODO Auto-generated method stub
							boolean xscale = false;
							boolean yscale = false;
							boolean colorscale = false;
							String label_real_name = GetXNameV2(webField);
							String labelY = GetY();
							String old2 = old;
							String labelX2 = GetX2(webField);
							String labelX = GetX();
							String real_labelX =	GetXRealName() ;
							String aggregationFct = GetOperation();
							
							String[] res = old.split(Pattern.quote("."));
							// int mID = DistinctValues.connector.counter.newMarkPropID()-1;
							if (res.length > 1) {
								old2 = old.split(Pattern.quote("."))[1];
							}
							String labelX22 = GetX2(old2);

							if (recommendation.getWidth() == 0) {
								recommendation.setWidth(600);
							}
							if (recommendation.getHeight() == 0) {
								recommendation.setHeight(500);
							}

							
							List<DisplayForm> vals = new ArrayList<DisplayForm>();
							Transformation sortTrans = new Transformation("sort","axeX");
							Formula formula = new Formula( "axeX", "parseInt(datum.axeX)" );
							List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(formula,sortTrans));
							DataChart da = new DataChart("table", vals,tt4);
							
							List<DataChart> data = new ArrayList<DataChart>(Arrays.asList(da));
							/********************* scales *************************/
							List<ScaleChart> scls = recommendation.getScales();
							List<AxisChart> tempo_axis = recommendation.getAxes();
							for (ScaleChart sca : recommendation.getScales()) {
								if (sca.getName().equals("x") && sca.getType() != null) {
									sca.setPadding((double)1);
									xscale = true;

								}
								if (sca.getName().equals("y") && sca.getType() != null) {
									yscale = true;
								}
								if (sca.getName().equals("color") && sca.getType() != null) {
									colorscale = true;
								}
							}
							
							if(xscale){
								
								
								for (AxisChart ax: recommendation.getAxes())
								{	if(ax.getType().equals("x"))
								{ax.setTitle(labelX22);}
								}
							}
							
							
							if (!xscale) {
								DomainChart xs = new DomainChart("table", "axeX");
								ScaleChart x_sc = new ScaleChart("x", "ordinal", "width", true, xs,(double)1);
								// remove x scale constructed in skeleton
								removeScale(scls, "x");
								scls.add(x_sc);

								// automatically also add axis
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
								AxisTable AXRow = DistinctValues.connector.constructAxisRow(axeID, labelX22, 10, "x", session, "x");
								int axeUsageID = DistinctValues.connector.counter.newaxis_Usage_ID();
								AxisUsageTable AXUsageRow = new AxisUsageTable(axeUsageID, axeID, visid, "x");
								session.saveOrUpdate(AXRow);
								session.saveOrUpdate(AXUsageRow);
								
								
							//==> store x encoding
								for (  Entry<String, Integer> m : marksElts.entrySet()) { 
									if(m.getKey().equals("line") || m.getKey().equals("symbol"))
									{		int chID = DistinctValues.connector.counter.newEncodingID();
								int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
								ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "x", "axeX", session, scID);
								ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(), idch.getChanneltype());

						
								session.saveOrUpdate(idch);
								session.saveOrUpdate(usageEncoRow);
									}}
						
							}
							if (!yscale) {
								//DomainChart ys = new DomainChart("foraxeY", "sum_AggNumber");
								DomainChart ys = new DomainChart("table", "AggNumber");
								ScaleChart y_sc = new ScaleChart("y", "height", ys, "linear");
								removeScale(scls, "y");
								scls.add(y_sc);

								// automatically also add axis
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
								AxisTable AXRow = DistinctValues.connector.constructAxisRow(axeID, labelY, 10, "y", session, "y");
								int axeUsageID = DistinctValues.connector.counter.newaxis_Usage_ID();
								AxisUsageTable AXUsageRow = new AxisUsageTable(axeUsageID, axeID, visid, "y");
								session.saveOrUpdate(AXRow);
								session.saveOrUpdate(AXUsageRow);
								
								
								//store y encoding
								for (  Entry<String, Integer> m : marksElts.entrySet()) { 
									if(m.getKey().equals("line") || m.getKey().equals("symbol")){
								
								int chID = DistinctValues.connector.counter.newEncodingID();
								int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
								ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "y", "AggNumber", session, scID);
								ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(), idch.getChanneltype());
								session.saveOrUpdate(idch);
								session.saveOrUpdate(usageEncoRow);}}
							
								
								
								
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
								// store axis inDB
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
								
								//--> store stroke encdoing
								for (  Entry<String, Integer> m : marksElts.entrySet()) { 
									if(m.getKey().equals("line") ){
								int chID = DistinctValues.connector.counter.newEncodingID();
								int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
								ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "stroke", "axeX", session, scID);
								ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(), idch.getChanneltype());
								session.saveOrUpdate(idch);
								session.saveOrUpdate(usageEncoRow);}}
								
							}

							List<DataChart> dats = new ArrayList<DataChart>(recommendation.getData());
							dats.addAll(data);
							recommendation.setData(dats);

							/********************* marks construction *************************/
							completeOssature_Viz_ZoomIn(recommendation);


							/********************************* legends *********************************/

							List<LegendChart> legends = new ArrayList<LegendChart>();
							PropLegend prL = new PropLegend();
							LegendChart leg = new LegendChart(labelX, "color", prL);
							legends.add(leg);

							recommendation.setLegends(legends);
							/********************* Signal construction *************************/
							
							Stream st1 = new Stream("symbol:mouseover", "datum");
							Stream st2 = new Stream("symbol:mouseout", "{}");
					
							
						Stream st3 = new Stream("symbol:dblclick", "open('result.jsp?cancel=" +label_real_name+ "&state='+datum.axeX+'&cond="+real_labelX+",'+datum.axeX2+'&type=" + type + "','_self')");
						
							List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
							Map<String, String> init = new HashMap<String, String>();
							Signal2 sig = new Signal2("tooltip", init, sts);
							List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));
							
							recommendation.setSignals(sigs);
						}			
						
			
			
			
			
	
			}