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

public  class VegaBars extends VegaVis {




	public VegaBars(QueryForm query) {
		super();
		this.query = query;
	}

	public VegaBars() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	//is used for recommendation zoom in
	@Override
	public VegaChart PrintVisrec_2D(String ColorCode) {
		
			String labelY = GetY();
			String labelX = GetX();
			int width = 900;
			int height = 550;
			List</*String*/DisplayForm> vals = new ArrayList</*String*/DisplayForm>();
			DataChart da = new DataChart("table", vals);
			List<DataChart> data = new ArrayList<DataChart>();
			data.add(da);

		
			
			
			/*********************scales*************************/
			DomainChart xs = new DomainChart("table", "axeX"/*,sorting*/);
			ScaleChart x = new ScaleChart("xscale", "ordinal", "width", true, xs);
		
			DomainChart ys = new DomainChart("table", "AggNumber");
			ScaleChart y = new ScaleChart("yscale", "height", ys, "linear");
		
			List<ScaleChart> scls = new ArrayList<ScaleChart>(Arrays.asList(x, y));
			
			
			
			/********************* axis construction *************************/
			LabelAxeChart fill = new LabelAxeChart("black");
			LabelAxeChart angle = new LabelAxeChart(90);
			LabelAxeChart dx = new LabelAxeChart(50);
			LabelAxeChart font = new LabelAxeChart(20);
			Labels lab = new Labels(fill, angle, dx,font);
			
			Map<String, Labels> pr = new HashMap<String, Labels>();
			pr.put("labels", lab);
			
			LabelAxeChart fillTitle = new LabelAxeChart("black");
			LabelAxeChart fontTitle = new LabelAxeChart(25);
			Labels labTitle = new Labels(fillTitle,fontTitle);
			pr.put("title", labTitle);
			
			AxisChart axe1 = new AxisChart("x", "xscale", labelX, pr);
		
			LabelAxeChart filly = new LabelAxeChart("black");
			LabelAxeChart fonty = new LabelAxeChart(20);
			Labels laby = new Labels(filly, fonty);
			Map<String, Labels> pry = new HashMap<String, Labels>();
			pry.put("labels", laby);
			
			LabelAxeChart fillTitley = new LabelAxeChart("black");
			LabelAxeChart fontTitley = new LabelAxeChart(25);
			Labels labTitley = new Labels(fillTitley,fontTitley);
			pry.put("title", labTitley);
			
			AxisChart axe2 = new AxisChart("y", "yscale", labelY,pry);
			List<AxisChart> axis = new ArrayList<AxisChart>(Arrays.asList(axe1, axe2));
			// AxisChart ax= new AxisChart(axis);

			/********************* marks construction *************************/
			ActChart ac1 = new ActChart("xscale", "axeX");
			ActChart ac2 = new ActChart("xscale", true, -4);
			ActChart ac3 = new ActChart("yscale", "AggNumber");
			ActChart ac4 = new ActChart(0, "yscale", 0, 0);
			ActChart ac5 = new ActChart(ColorCode);
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
			
			Stream st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" + labelX
					+ "&state='+datum.axeX+'&chiffre='+datum.AggNumber,'_self')");
			List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
			Map<String, String> init = new HashMap<String, String>();
			Signal2 sig = new Signal2("tooltip", init, sts);
			List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));
			/********************* vega construction *************************/
			VegaChart chart = new VegaChart(sigs,width, height, data, scls, axis, mrks/*, sigs, pipi*/);

			return chart;
		}

	
	
	@Override
	// it works exactly like PrinrecZoomIn
		public void Complete_Rec_Viz_ZoomIn(VegaChart recommendation, String webField, String old, String type,
				List<DisplayForm> datasetToGiveColor, int visid, Map<String, Integer> marksElts,
				Session session) {
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

			DataChart da = new DataChart("table");

			Transformation ag0 = new Transformation("sort","axeX2");
			
			
			List<String> lis = new ArrayList<String>(Arrays.asList("axeX", "axeX2"));
			Map<String, Object> maw = new HashMap<String, Object>();
			maw.put("field", "AggNumber");
			List<String> listVal = new ArrayList<String>(Arrays.asList("sum"));
			maw.put("ops", listVal);
			List<Map<String, Object>> bee = new ArrayList<Map<String, Object>>(Arrays.asList(maw));
			Aggregate ag1 = new Aggregate("aggregate", lis, bee);

			List<String> lis4 = new ArrayList<String>(Arrays.asList("axeX"));
			Stack ag4 = new Stack("sum_AggNumber", lis4);
			List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(ag0,ag1, ag4));
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
			/***************************** data sets ******************************/
			List<DataChart> data = new ArrayList<DataChart>(Arrays.asList(da, da4, da1axis));
			/********************* scales *************************/
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
			
			if(xscale){
			
				
				for (AxisChart ax: recommendation.getAxes())
				{	if(ax.getType().equals("x"))
				{ax.setTitle(labelX22);}
				}
			}
			if (!xscale) {
				DomainChart xs = new DomainChart("table", "axeX");
				ScaleChart x_sc = new ScaleChart("x", "ordinal", "width", true, xs);
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

				int chID = DistinctValues.connector.counter.newEncodingID();
				for (Entry<String, Integer> m : marksElts.entrySet()) {
					if (m.getKey().equals("rect")) {
						int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
						ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "x", "axeX", session, scID);
						ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue() /* mID */,
								idch.getChanneltype());

						int chID2 = DistinctValues.connector.counter.newEncodingID();
						int chUsageID2 = DistinctValues.connector.counter.newchannelUsage_ID();
						ChannelTable idch2 = DistinctValues.connector.constructChannelRow(chID2, "width", null, session,
								scID);
						ChannelUsageTable usageEncoRow2 = new ChannelUsageTable(chUsageID2, chID2, m.getValue() /* mID */,
								idch2.getChanneltype());

						session.saveOrUpdate(idch);
						session.saveOrUpdate(usageEncoRow);
						session.saveOrUpdate(idch2);
						session.saveOrUpdate(usageEncoRow2);
					}
				}

			}
			
			//case "state of destination" axis inhereting  title "state"
			if(xscale)
			{
			lp:	for (AxisChart axe: recommendation.getAxes()){
					if(axe.getType().equals("x"))
					{ axe.setTitle(labelX22);
					break lp;}
				}
			}
			
			
			if (!yscale) {
				DomainChart ys = new DomainChart("foraxeY", "sum_AggNumber");
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

				for (Entry<String, Integer> m : marksElts.entrySet()) {
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

				for (Entry<String, Integer> m : marksElts.entrySet()) {
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

			/********************* marks construction *************************/
			completeOssature_Viz_ZoomIn(recommendation);

			/********************************* legends *********************************/

			List<LegendChart> legends = new ArrayList<LegendChart>();
			PropLegend prL = new PropLegend();
			LegendChart leg = new LegendChart(labelX2, "color", prL);
			legends.add(leg);

			recommendation.setLegends(legends);
			/********************* Signal construction *************************/

			Stream st1 = new Stream("rect:mouseover", "datum");
			Stream st2 = new Stream("rect:mouseout", "{}");
			//Stream st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" + old2
				//	+ "&state='+datum.axeX+'&chiffre='+datum.sum_nummer+'&type=" + type + "','_self')");
			Stream st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" +old2+ "&state='+datum.axeX+'&cond="+real_labelX+",'+datum.axeX2+'&type=" + type + "','_self')");
		
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
			LabelAxeChart dx = new LabelAxeChart(70);
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
		
		//case "state of destination" axis inhereting  title "state"
		if(xscale)
		{
		lp:	for (AxisChart axe: recommendation.getAxes()){
				if(axe.getType().equals("x"))
				{ axe.setTitle(labelX);
				break lp;}
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
				if (m.getKey().equals("rect")) {
					// store encodings
					int chID = DistinctValues.connector.counter.newEncodingID();

					int chUsageID = DistinctValues.connector.counter.newchannelUsage_ID();
					ChannelTable idch = DistinctValues.connector.constructChannelRow(chID, "y", "AggNumber", session,
							scID);
					ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, m.getValue(),
							idch.getChanneltype());

					int chID2 = DistinctValues.connector.counter.newEncodingID();
					int chUsageID2 = DistinctValues.connector.counter.newchannelUsage_ID();
					ChannelTable idch2 = DistinctValues.connector.constructChannelRow(chID2, "y2", 0, session, scID);
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
			// be careful if nothing here use fill without scale

			for (Entry<String, Integer> m : mappingMarkID.entrySet()) {
				if (m.getKey().equals("rect")) {

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
		Stream st1 = new Stream("rect:mouseover", "datum");
		Stream st2 = new Stream("rect:mouseout", "{}");
		Stream st3 ;
if(type.equals("Principal")){
	 st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" + attributeX
			+ "&state='+datum.axeX+'&chiffre='+datum.AggNumber,'_self')");
}
else{	 st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" + attributeX
				+ "&state='+datum.axeX+'&chiffre='+datum.AggNumber+'&type="+type+"','_self')");}

		List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
		Map<String, String> init = new HashMap<String, String>();
		Signal2 sig = new Signal2("tooltip", init, sts);
		List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));

		/*********************/

		recommendation.setSignals(sigs);

	}

	
	



	@Override
	public void Complete_Rec_Viz_Extension(VegaChart recommendation, String webField, String old, String type,
			List<DisplayForm> datasetToGiveColor, int visid, Map<String, Integer> marksElts, Session session) {
		// TODO Auto-generated method stub
		boolean xscale = false;
		boolean yscale = false;
		boolean colorscale = false;
		String labelY = GetY();
		String labelX = GetX();
		String labelX2 = GetX2(webField);
	String	real_labelX=GetXRealName();
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

		
		Transformation ag0 = new Transformation("sort","axeX2");
		List<String> lis4 = new ArrayList<String>(Arrays.asList("axeX"));
		Stack ag4 = new Stack("sum_AggNumber", lis4);
		List<Transformation> tt4 = new ArrayList<Transformation>(Arrays.asList(ag0,ag1, ag4));
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
			AxisUsageTable AXUsageRow = new AxisUsageTable(axeUsageID, axeID, visid, "x");
			session.saveOrUpdate(AXRow);
			session.saveOrUpdate(AXUsageRow);

			// store the new encoding channels
			int chID = DistinctValues.connector.counter.newEncodingID();
			for (Entry<String, Integer> m : marksElts.entrySet()) {
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
			AxisUsageTable AXUsageRow = new AxisUsageTable(axeUsageID, axeID, visid, "y");
			session.saveOrUpdate(AXRow);
			session.saveOrUpdate(AXUsageRow);

			for (Entry<String, Integer> m : marksElts.entrySet()) {
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

			for (Entry<String, Integer> m : marksElts.entrySet()) {
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

		/********************* marks construction *************************/
		completeOssature_Viz_Extension(recommendation);

		/********************************* legends *********************************/

		List<LegendChart> legends = new ArrayList<LegendChart>();
		PropLegend prL = new PropLegend();
		LegendChart leg = new LegendChart(labelX, "color", prL);
		legends.add(leg);
		recommendation.setLegends(legends);
		/********************* Signal construction *************************/

		Stream st1 = new Stream("rect:mouseover", "datum");
		Stream st2 = new Stream("rect:mouseout", "{}");
		
		//Stream st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" +/*webField*/label_real_name+ "&state='+datum.axeX+'&chiffre='+datum.sum_nummer+'&type=" + type + "','_self')");
		Stream st3 = new Stream("rect:dblclick", "open('result.jsp?cancel=" +label_real_name+ "&state='+datum.axeX+'&cond="+real_labelX+",'+datum.axeX2+'&type=" + type + "','_self')");
		List<Stream> sts = new ArrayList<Stream>(Arrays.asList(st1, st2, st3));
		Map<String, String> init = new HashMap<String, String>();
		Signal2 sig = new Signal2("tooltip", init, sts);
		List<SignalChart> sigs = new ArrayList<SignalChart>(Arrays.asList(sig));

		recommendation.setSignals(sigs);
		
	}

	

	
	
	
	
	
	

}
