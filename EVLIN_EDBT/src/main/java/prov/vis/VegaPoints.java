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
import prov.chart.MarkGeneral;
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

public  class VegaPoints extends VegaVis {



	public VegaPoints(QueryForm query) {
		super();
		this.query = query;
	}

	public VegaPoints() {
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

			

			
			/*Map<String, String> sorting = new HashMap<String, String>();
			sorting.put("field", "AggNumber");
			sorting.put("op", "max");
			DomainChart xs = new DomainChart("table", "axeX",sorting);*/
			DomainChart xs = new DomainChart("table", "axeX");
			ScaleChart x = new ScaleChart("xscale", "ordinal", "width", true, xs,(double)1);
		
		
			DomainChart ys = new DomainChart("table", "AggNumber");
			ScaleChart y = new ScaleChart("yscale", "height", ys, "linear");
		

			
			
			
			
			
			
			
			
			
			List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y));
			/********************* axis construction *************************/
			LabelAxeChart fill = new LabelAxeChart("black");
			LabelAxeChart angle = new LabelAxeChart(90);
			LabelAxeChart dx = new LabelAxeChart(20);
			LabelAxeChart font = new LabelAxeChart(15);
			Labels lab = new Labels(fill, angle, dx,font);
			
			Map<String, Labels> pr = new HashMap<String, Labels>();
			pr.put("labels", lab);
			
			LabelAxeChart fillTitle = new LabelAxeChart("black");
			LabelAxeChart fontTitle = new LabelAxeChart(20);
			Labels labTitle = new Labels(fillTitle,fontTitle);
			pr.put("title", labTitle);
			
			AxisChart axe1 = new AxisChart("x", "xscale", labelX, pr);
		
			LabelAxeChart filly = new LabelAxeChart("black");
			LabelAxeChart fonty = new LabelAxeChart(15);
			Labels laby = new Labels(filly, fonty);
			Map<String, Labels> pry = new HashMap<String, Labels>();
			pry.put("labels", laby);
			
			LabelAxeChart fillTitley = new LabelAxeChart("black");
			LabelAxeChart fontTitley = new LabelAxeChart(20);
			Labels labTitley = new Labels(fillTitley,fontTitley);
			pry.put("title", labTitley);
			
			AxisChart axe2 = new AxisChart("y", "yscale", labelY,pry);
			List<AxisChart> axis = new ArrayList<AxisChart>(Arrays.asList(axe1, axe2));
			// AxisChart ax= new AxisChart(axis);

			/********************* marks construction *************************/
			ActChart ac1x = new ActChart("xscale", "axeX");
		
			ActChart ac3y = new ActChart("yscale", "AggNumber");
			ActChart acfill = new ActChart("Teal");
			ActChart acsize = new ActChart(100);
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
			ActChart acc3 = new ActChart("xscale", "tooltip.axeX", 0);
			ActChart acc5 = new ActChart("yscale", "tooltip.AggNumber", -5);

			Map<String, String> acc6 = new HashMap<String, String>();
			acc6.put("signal", "tooltip.AggNumber");

			ActionChart acct1 = new ActionChart(acc2, acc1);
			ActionChartText acct2 = new ActionChartText(acc3, acc5, acc6);
		

			PropChart prop2 = new PropChart(acct1, acct2);
			MarkChart mark2 = new MarkChart("text", prop2);
			List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1, mark2));

			/********************* Signal construction *************************/
			Stream st0 = new Stream("click", "datum._id");
			Stream st1 = new Stream("mouseover", "datum");
			Stream st2 = new Stream("mouseout", "{}");
			
			Stream st3 = new Stream("dblclick", "open('result.jsp?cancel=" + labelX
					+ "&state='+datum.axeX+'&chiffre='+datum.AggNumber,'_self')");
			List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
			Map<String, String> init = new HashMap<String, String>();
			Signal2 sig = new Signal2("tooltip", init, sts);
			List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));
			/********************* vega construction *************************/
		
			VegaChart chart = new VegaChart(sigs,width, height, data, scls, axis, mrks);
			return chart;

			}
	
	
	
	
	
	
		public VegaChart PrintVisrec_ZoomInSlice(String ColorCode) {
			
			VegaChart chart= PrintVisrec_2D(ColorCode);
			Signal2 signal = (Signal2)chart.getSignals().get(0);
			List<Stream> streams = signal.getStreams();
			Stream interaction = streams.get(2);
			String url = interaction.getExpr();
			String link = url.split(",")[0];
			link=link+"+\'&type=ZoomIn_Slice\'";
			String urlnew = link+","+url.split(",")[1];
			interaction.setExpr(urlnew);
			
			return chart;
		}
	
	
		
		
		
		@Override
	public VegaChart PrintVisrecZoomIn(String webField,String old,String type/*,List<DisplayForm> datasetToGiveColor*/){
		//	List<DisplayForm> colorRanges=Construct_ScaleColorData(datasetToGiveColor);
			
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
		
		//List<String> mapping = new ArrayList<String>();
		DataChart src_map = new DataChart("mapping"/*, mapping*/);
		
	//	List<String> valss = new ArrayList<String>();
		DataChart src = new DataChart("source"/*, valss*/);
		

		List<String> listfields = new ArrayList<String>(Arrays.asList("axeX", "axeX2"));
		Map<String, Object> TransDetails= new HashMap<String, Object>();
		TransDetails.put("field", "AggNumber");
		List<String> listaggs = new ArrayList<String>(Arrays.asList(/*"sum"*/aggregationFct));
		TransDetails.put("ops", listaggs);
		TransDetails.put("as", "AggNumber");
		
		List<Map<String, Object>> defAgg = new ArrayList<Map<String, Object>>(Arrays.asList(TransDetails));
		Aggregate ag11 = new Aggregate("aggregate", listfields, defAgg );
	
		
		
		Transformation sortTrans = new Transformation("sort","axeX2");
		List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(sortTrans,ag11));
		
		List</*String*/DisplayForm> vals = new ArrayList</*String*/DisplayForm>();
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

		/*DomainChart zs = new DomainChart("table", "axeX2");
		List<String> colors= new ArrayList<String>(Arrays.asList("#7E5686","#A5AAD9","#19EACE","#DBDFAC","#F8A13F","#BA3C3D",
				"#f768a1","#fcc5c0","#969696","#a1d99b","#252525","#8dd3c7","#ffffb3","#bebada","#fb8072","#80b1d3","#fdb462","#b3de69","#fccde5","#d9d9d9","#bc80bd","#ccebc5","#ffed6f"));
		ScaleChart z = new ScaleChart("color", colors, zs, "ordinal");*/
	
		Map<String, String> sort = new HashMap<String, String>();
		sort.put("field", "_id");
		sort.put("op", "min");
		DomainChart colors2 = new DomainChart("mapping", "axeX", /*false*/sort);
		DomainChart colors2range = new DomainChart("mapping", "color"/*, false*/);
		ScaleChart z_test = new ScaleChart("color", "ordinal", colors2, colors2range);
	
		List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y,/* z,*/z_test));
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
		ActChart ac1 = new ActChart("x", "axeX");
		ActChart ac3 = new ActChart("y", "AggNumber");
		ActChart accolor = new ActChart("color", "axeX2");
		ActChart acsize=new ActChart(70);
		Map<String, Object> acfillopacity = new HashMap<String, Object>();
		acfillopacity.put("value", 1);
		ActChart ac6 = new ActChart("red");
	
		ActionChartScatter act1 = new ActionChartScatter(ac1,ac3,accolor,acfillopacity, acsize);
		ActionChart act2 = new ActionChart(accolor);
		ActionChart act3 = new ActionChart(ac6);
	
		PropChart prop1 = new PropChart(act1, act2, act3);
		
		MarkChart mark1 = new MarkChart("symbol",prop1);
		List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1));
		
		
				
				
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

		Stream st1 = new Stream("symbol:mouseover", "datum");
		Stream st2 = new Stream("symbol:mouseout", "{}");
		Stream st3 = new Stream("symbol:dblclick",
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
			MarkChart mark2 = new MarkChart("symbol");
			MarkChart mark3 = new MarkChart("text");
			List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark2,mark3));
			chart.setMarks(mrks);
			return chart;
		}
		
		
		
		
		//it completes all details of mark, encoding channels described in zoominviz functions
		@Override
		public void completeOssature_Viz_ZoomIn(VegaChart recommendation) {
			// TODO Auto-generated method stub
			
			ActChart ac1 = new ActChart("x", "axeX");
			ActChart ac3 = new ActChart("y", "AggNumber");
			ActChart accolor = new ActChart("color", "axeX2");
			ActChart acsize=new ActChart(70);
			Map<String, Object> acfillopacity = new HashMap<String, Object>();
			acfillopacity.put("value", 1);
			ActChart ac6 = new ActChart("red");
		
			ActionChartScatter act1 = new ActionChartScatter(ac1,ac3,accolor,acfillopacity, acsize);
			ActionChart act2 = new ActionChart(accolor);
			ActionChart act3 = new ActionChart(ac6);
		
			PropChart prop1 = new PropChart(act1, act2, act3);
			
			MarkChart mark1 = new MarkChart("symbol",prop1);
			List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1));
			
		
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
	

		// it works exactly like PrinrecZoomIn
		@Override 
		public void Complete_Rec_Viz_ZoomIn(VegaChart recommendation, String webField, String old, String type,
				List<DisplayForm> datasetToGiveColor, int visid, /*int mID*/Map<String,Integer> marksElts, Session session) {
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
				{ax.setTitle(labelX22);
				}
				}
				
			lp:	for (ScaleChart sc_x: recommendation.getScales())
				{
					if (sc_x.getName().equals("x"))
					{
						sc_x.setPadding(1);
						break lp;
					}
				}
			}
			if (!xscale) {
				DomainChart xs = new DomainChart("table", "axeX");
				ScaleChart x_sc = new ScaleChart("x", "ordinal", "width", true, xs,(double)1);
				x_sc.setPadding(1);
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
					if(m.getKey().equals("symbol") ){
				int chID = DistinctValues.connector.counter.newEncodingID();
				int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
				ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "x", "axeX", session, scID);
				ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(), idch.getChanneltype());

		
				session.saveOrUpdate(idch);
				session.saveOrUpdate(usageEncoRow);}}
			
				
			}
			
			//case "state of destination" axis inheriting  title "state"
			if(xscale)
			{
			lp:	for (AxisChart axe: recommendation.getAxes()){
					if(axe.getType().equals("x"))
					{ axe.setTitle(labelX22);
					break lp;}
				}
			}
			
			
			
			if (!yscale) {
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
					if(m.getKey().equals("symbol") ){
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
					if(m.getKey().equals("symbol") ){
				int chID = DistinctValues.connector.counter.newEncodingID();
				int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
				ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "fill", "axeX", session, scID);
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
			
			
			
			//size scale update and storage in DB
			DomainChart sizeDOM = new DomainChart("table", "AggNumber");
			List<Integer> sizeRange = new ArrayList<Integer>(Arrays.asList(0,2000));			
			ScaleChart scSize = new ScaleChart("size","linear", sizeDOM, sizeRange);
			removeScale(scls, "size");
			scls.add(scSize);
			int scID12 = DistinctValues.connector.counter.newS_ID();
			List idData12 = session.createSQLQuery("select max(iddataset) from DatasetTable where name = 'table'").list();
			Iterator<DisplayForm[]> ite21 = idData12.iterator();
			Object test21 = ite21.next();
			int d_id12 = Integer.parseInt(test21.toString());
			ScaleTable scaleRow12 = new ScaleTable(scID12, d_id12, scSize.getType(), scSize.getName(),
					scSize.getDomain().getField(), "empty", scSize.getRange().toString());
			session.save(scaleRow12);	
			
		
			
			
			
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
			
			
			lp2:	for (ScaleChart sc_x: recommendation.getScales())
			{
				if (sc_x.getName().equals("x"))
				{
					sc_x.setPadding(1);
					break lp2;
				}
			}
			
			}
			
			if (!xscale) {

				// construct x scale
				DomainChart xs = new DomainChart("table", "axeX");
				ScaleChart x_sc = new ScaleChart("x", "ordinal", "width", true, xs);
				x_sc.setPadding(1);
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
					if (m.getKey().equals("symbol")) {
						int chID = DistinctValues.connector.counter.newEncodingID();
						int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
						ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "x", "axeX", session, scID);
						ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(),
								idch.getChanneltype());


						session.saveOrUpdate(idch);
						session.saveOrUpdate(usageEncoRow);
					
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

				// store axis and scale
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
					if (m.getKey().equals("symbol")) {
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
					if (m.getKey().equals("symbol")) {

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
			Stream st0 = new Stream("click", "datum._id");
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
			CompleteSize(recommendation);
			boolean rgcolor = false;
			PropChart prop1 = null;
			lp: for (DataChart elt : recommendation.getData()) {
				if (elt.getName().equals("mapping")) {
					rgcolor = true;
					break lp;
				}
			}

			ActChart ac1x = new ActChart("x", "axeX");
			ActChart ac3y = new ActChart("y", "AggNumber");
			ActChart acsize = new ActChart(100);
			Map<String, Object> acfillopacity = new HashMap<String, Object>();
			acfillopacity.put("value", 0.5);
			ActChart ac6 = new ActChart("red");
			ActionChart act3 = new ActionChart(ac6);

			if (!rgcolor) {
				ActChart acfill = new ActChart("#728f99");
				

				ActionChartScatter act1 = new ActionChartScatter(ac1x, ac3y,acfill, acfillopacity,acsize)	;
			
				ActionChart act2 = new ActionChart(acfill);
				

				prop1 = new PropChart(act1, act2, act3);
			} else {
				ActChart ac41 = new ActChart("color", "axeX2");
				ActionChartScatter act1 = new ActionChartScatter(ac1x, ac3y,ac41, acfillopacity,acsize)	;
				
				ActionChart act2 = new ActionChart(ac41);
				

				prop1 = new PropChart(act1, act2, act3);
			}

			Map<String, String> from = new HashMap<String, String>();
			from.put("data", "table");

			MarkChart mark1 = new MarkChart("symbol", from, prop1); 
			
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
			List<MarkGeneral> mrks = new ArrayList<MarkGeneral>(Arrays.asList(mark1, mark2));
			recommendation.setMarks(mrks);


		}
		
		
		
		@Override
		public void Complete_Rec_Viz_Extension(VegaChart recommendation, String webField, String old, String type,
				List<DisplayForm> datasetToGiveColor, int visid, Map<String, Integer> marksElts, Session session) {
			// TODO Auto-generated method stub
			String label_real_name = GetXNameV2(webField);
			String real_labelX=GetXRealName();
			Complete_Rec_Viz_ZoomIn(recommendation, webField,  old,  type,
					 datasetToGiveColor, visid,  marksElts,  session);
			String labelX = GetX();
			
			Stream st1 = new Stream("symbol:mouseover", "datum");
			Stream st2 = new Stream("symbol:mouseout", "{}");
			//Stream st3 = new Stream("symbol:dblclick", "open('result.jsp?cancel=" +label_real_name+ "&state='+datum.axeX+'&chiffre='+datum.sum_nummer+'&type=" + type + "','_self')");
			Stream st3 = new Stream("symbol:dblclick", "open('result.jsp?cancel=" +label_real_name+ "&state='+datum.axeX+'&cond="+real_labelX+",'+datum.axeX2+'&type=" + type + "','_self')");
			List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
			Map<String, String> init = new HashMap<String, String>();
			Signal2 sig = new Signal2("tooltip", init, sts);
			List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));
			
			recommendation.setSignals(sigs);
		
			
			//update legend
			List<LegendChart> legends = new ArrayList<LegendChart>();
			PropLegend prL = new PropLegend();
			LegendChart leg = new LegendChart(labelX, "color", prL);
			legends.add(leg);
			
			recommendation.setLegends(legends);
		
		}

		

	
	
}
