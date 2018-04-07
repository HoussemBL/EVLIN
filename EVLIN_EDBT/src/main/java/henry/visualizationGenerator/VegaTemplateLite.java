package henry.visualizationGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import henry.QuerySimilarity.VisualizationFromDB;
import prov.chartLite.DataContainer;
import prov.chartLite.DataChart;
import prov.chartLite.EncodingChannel;
import prov.chartLite.EncodingChannels;
import prov.chartLite.Legend;
import prov.chartLite.Mark;
import prov.chartLite.Range;
import prov.chartLite.Scale;
import prov.chartLite.Selection;
import prov.chartLite.SelectionID;
import prov.chartLite.VegaLiteChart;
import prov.chartLite.Axis;
import prov.chartLite.Conditions;

public class VegaTemplateLite {
	
	


	
	public VegaLiteChart generatChart(String type,String subtyp,String interaction , String size, String colorValue,
			int numberOfData ,Boolean swap) {
		
		//Set subtyp
		Boolean subtypColor = Subtyp.calculateSubtyp(subtyp);
			
		
		//Set Axis
		String xAxis = "state";
		String yAxis = "cancellation_num";
		if (swap){
			xAxis = "cancellation_num";
			yAxis = "state";
		}
		
		// Setup size
		int[] sizes = Size.calculateSize(size);
		int width = sizes[0];
		int height = sizes[1];

		// Setup data
		DataChart data = new DataChart("table");
		
		// Setup interaction
		Interaction InteractionMaker = new Interaction();
		InteractionContainer interactionContainer = InteractionMaker.calculateInteraction(interaction);
		Selection selection = interactionContainer.getSelection();
		Boolean withInteraction = (interactionContainer.getSelection().getId().getType() != "non");

		// Setup Endcodingchannel
		EncodingChannels encoding = new EncodingChannels();

		EncodingChannel y = new EncodingChannel();
		y.setField(yAxis);
		y.setType("quantitative");
		Axis axisY = new Axis("Y-Achse",true);
		y.setAxis(axisY);
		if (subtypColor) {
			y.setAggregate("sum");
		}
		
		
		y.setField(yAxis);
		y.setType("quantitative");
		
		EncodingChannel x = new EncodingChannel();
		x.setField(xAxis);
		x.setType("ordinal");
		Axis axisX = new Axis("X-Achse",true);
		Scale scaleX = new Scale();
		x.setAxis(axisX);
		scaleX.setRangeStep(20);
		x.setScale(scaleX);
		
		
		//Colo setup
		
		
		EncodingChannel color = new EncodingChannel();
		if(!subtypColor){
			color.setValue(colorValue);
		}
		String[] array= {"red","blue","green"};
		if(subtypColor){
			color.setScale(new Scale(array));
		}
		if ( withInteraction) {
			color.setCondition(interactionContainer.getConditions());
		}
		if(subtypColor){
			color.setType("nominal");
			color.setField("c");
		}
		
		encoding.setX(x);
		encoding.setY(y);
		encoding.setColor(color);
		

		// Setup marktype
		String mark = type;

		// Create json
		if (withInteraction) {
			VegaLiteChart chart = new VegaLiteChart(width, height, data,selection, mark, encoding);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(chart);
			return chart;
		} else {
			VegaLiteChart chart = new VegaLiteChart(width, height, data, mark, encoding);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(chart);
			return chart;
		}

	}
	
	
	public VegaLiteChart generatChart2(VisualizationFromDB vis) {
		
		//Set subtyp
		Boolean subtypColor = false;
			
		
		//Set Axis
		String xAxis = vis.getxAxisDimension();
		String yAxis = vis.getyAxisDimension();
		
		
		int width = vis.getWidth() ;
		int height = vis.getHeight();

		// Setup data
		DataChart data = new DataChart("table");
		
		// Setup interaction
		Interaction InteractionMaker = new Interaction();
		InteractionContainer interactionContainer = InteractionMaker.calculateInteraction(vis.getInteraction());
		Selection selection = interactionContainer.getSelection();
		Boolean withInteraction = (interactionContainer.getSelection().getId().getType() != "non");

		// Setup Endcodingchannel
		EncodingChannels encoding = new EncodingChannels();

		EncodingChannel y = new EncodingChannel();
		y.setField(yAxis);
		y.setType(vis.getyAxisType());
		Axis axisY = new Axis(vis.getyAxisTitle(),vis.getyAxisgrid());
		Scale scaleY = new Scale();
		Integer   a = vis.getyAxisRangeStep();
		scaleY.setRangeStep(a);
		y.setScale(scaleY);
		y.setAxis(axisY);
		if (vis.getNumberOfDimensions() == 3) {
			y.setAggregate(vis.getyAggregation());
		}
		
		
		y.setField(yAxis);
		y.setType("quantitative");
		
		EncodingChannel x = new EncodingChannel();
		x.setField(xAxis);
		x.setType(vis.getxAxisType());
		Axis axisX = new Axis(vis.getxAxisTitle(),vis.getxAxisGrid());
		Scale scaleX = new Scale();
		x.setAxis(axisX);
		a = vis.getxAxisRangestep();
		scaleX.setRangeStep(a);
		x.setScale(scaleX);
		
		EncodingChannel size = new EncodingChannel(); 
		if(vis.getMark().equals("point") && vis.getNumberOfDimensions() == 3) {
			size.setField(vis.getzAxisDimension());
			size.setType("ordinal");
			encoding.setSize(size);
		}
		
		EncodingChannel shape = new EncodingChannel(); 
		if(vis.getMark().equals("scatterplott")&& vis.getNumberOfDimensions() == 3) {
			shape.setField(vis.getzAxisDimension());
			shape.setType("ordinal");
			encoding.setShape(shape);
		}
		
		
		//Colo setup
		EncodingChannel color = new EncodingChannel();
		color.setValue(vis.getColor());
		color.setCondition(interactionContainer.getConditions());
		
		
		if(vis.getNumberOfDimensions() == 3 && !vis.getMark().equals("point") && !vis.getMark().equals("scatterplott")) {
			color.setField(vis.getzAxisDimension());
			color.setType(vis.getzAxisType());
			Scale scale = new Scale();
			scale.setScheme(vis.getColor() +"s");
			color.setScale(scale);
		}
		
		encoding.setX(x);
		encoding.setY(y);
		encoding.setColor(color);
		

		// Setup marktype
		String mark;
		if (vis.getMark().equals("scatterplott")) {
			mark = "point";
		}else{
			mark= vis.getMark();
		}

		// Create json
		VegaLiteChart chart = new VegaLiteChart(width, height, data,selection, mark, encoding);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return chart;
	}
	
public VegaLiteChart generatChartForRecommendation(VisualizationFromDB vis) {

		//Set subtyp
		Boolean subtypColor = false;
			
		
		//Set Axis
		String xAxis = vis.getxAxisDimension();
		String yAxis = vis.getyAxisDimension();
		
		
		int width = vis.getWidth() ;
		int height = vis.getHeight();

		// Setup data
		DataChart data = new DataChart("table");
		
		// Setup interaction
		Interaction InteractionMaker = new Interaction();
		InteractionContainer interactionContainer = InteractionMaker.calculateInteraction(vis.getInteraction());
		Selection selection = interactionContainer.getSelection();
		Boolean withInteraction = (interactionContainer.getSelection().getId().getType() != "non");

		// Setup Endcodingchannel
		EncodingChannels encoding = new EncodingChannels();

		EncodingChannel y = new EncodingChannel();
		y.setField(yAxis);
		y.setType(vis.getyAxisType());
		Axis axisY = new Axis(vis.getyAxisTitle(),vis.getyAxisgrid());
		Scale scaleY = new Scale();
		Integer   a = vis.getyAxisRangeStep();
		scaleY.setRangeStep(a);
		y.setScale(scaleY);
		y.setAxis(axisY);
		if (vis.getNumberOfDimensions() == 3) {
			y.setAggregate(vis.getyAggregation());
		}
		
		
		y.setField("nummer");
		y.setType("quantitative");
		
		EncodingChannel x = new EncodingChannel();
		x.setField("state");
		x.setType(vis.getxAxisType());
		Axis axisX = new Axis(vis.getxAxisTitle(),vis.getxAxisGrid());
		Scale scaleX = new Scale();
		x.setAxis(axisX);
		a = vis.getxAxisRangestep();
		scaleX.setRangeStep(a);
		x.setScale(scaleX);
		
		EncodingChannel size = new EncodingChannel(); 
		if(vis.getMark().equals("point") && vis.getNumberOfDimensions() == 3) {
			size.setField("axeX");
			size.setType("ordinal");
			encoding.setSize(size);
			Legend legend = new Legend(); 
			legend.setTitle(vis.getzAxisDimension());
			size.setLegend(legend);
		}
		
		EncodingChannel shape = new EncodingChannel(); 
		if(vis.getMark().equals("scatterplott")&& vis.getNumberOfDimensions() == 3) {
			shape.setField("axeX");
			shape.setType("ordinal");
			encoding.setShape(shape);
			Legend legend = new Legend(); 
			legend.setTitle(vis.getzAxisDimension());
			shape.setLegend(legend);
		}
		
		
		//Colo setup
		EncodingChannel color = new EncodingChannel();
		color.setValue(vis.getColor());
		color.setCondition(interactionContainer.getConditions());
		
		
		if(vis.getNumberOfDimensions() == 3  && !vis.getMark().equals("point") && !vis.getMark().equals("scatterplott")) {
			color.setField("axeX");
			color.setType(vis.getzAxisType());
			Scale scale = new Scale();
			scale.setScheme(vis.getColor() +"s");
			color.setScale(scale);
			Legend legend = new Legend(); 
			legend.setTitle(vis.getzAxisDimension());
			color.setLegend(legend);
		}
		
		encoding.setX(x);
		encoding.setY(y);
		encoding.setColor(color);
		

		// Setup marktype
		String mark;
		if (vis.getMark().equals("scatterplott")) {
			mark = "point";
		}else{
			mark= vis.getMark();
		}

		// Create json
		VegaLiteChart chart = new VegaLiteChart(width, height, data,selection, mark, encoding);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return chart;
	}	

public VegaLiteChart generatChartForRecommendationThird(VisualizationFromDB vis) {

	//Set subtyp
	Boolean subtypColor = false;
		
	
	//Set Axis
	String xAxis = vis.getxAxisDimension();
	String yAxis = vis.getyAxisDimension();
	
	
	int width = vis.getWidth() ;
	int height = vis.getHeight();

	// Setup data
	DataChart data = new DataChart("table");
	
	// Setup interaction
	Interaction InteractionMaker = new Interaction();
	InteractionContainer interactionContainer = InteractionMaker.calculateInteraction(vis.getInteraction());
	Selection selection = interactionContainer.getSelection();
	Boolean withInteraction = (interactionContainer.getSelection().getId().getType() != "non");

	// Setup Endcodingchannel
	EncodingChannels encoding = new EncodingChannels();

	EncodingChannel y = new EncodingChannel();
	y.setField(yAxis);
	y.setType(vis.getyAxisType());
	Axis axisY = new Axis(vis.getyAxisTitle(),vis.getyAxisgrid());
	Scale scaleY = new Scale();
	Integer   a = vis.getyAxisRangeStep();
	scaleY.setRangeStep(a);
	y.setScale(scaleY);
	y.setAxis(axisY);
	if (vis.getNumberOfDimensions() == 3) {
		y.setAggregate(vis.getyAggregation());
	}
	
	
	y.setField("nummer");
	y.setType("quantitative");
	
	EncodingChannel x = new EncodingChannel();
	x.setField("state");
	x.setType(vis.getxAxisType());
	Axis axisX = new Axis(vis.getxAxisTitle(),vis.getxAxisGrid());
	Scale scaleX = new Scale();
	x.setAxis(axisX);
	a = vis.getxAxisRangestep();
	scaleX.setRangeStep(a);
	x.setScale(scaleX);
	
	EncodingChannel size = new EncodingChannel(); 
	if(vis.getMark().equals("point") && vis.getNumberOfDimensions() == 3) {
		size.setField("val");
		size.setType("ordinal");
		encoding.setSize(size);
		Legend legend = new Legend(); 
		legend.setTitle(vis.getzAxisDimension());
		size.setLegend(legend);
	}
	
	EncodingChannel shape = new EncodingChannel(); 
	if(vis.getMark().equals("scatterplott")&& vis.getNumberOfDimensions() == 3) {
		shape.setField("val");
		shape.setType("ordinal");
		encoding.setShape(shape);
		Legend legend = new Legend(); 
		legend.setTitle(vis.getzAxisDimension());
		shape.setLegend(legend);
	}
	
	
	//Colo setup
	EncodingChannel color = new EncodingChannel();
	color.setValue(vis.getColor());
	color.setCondition(interactionContainer.getConditions());
	
	
	if(vis.getNumberOfDimensions() == 3  && !vis.getMark().equals("point") && !vis.getMark().equals("scatterplott")) {
		color.setField("val");
		color.setType(vis.getzAxisType());
		Scale scale = new Scale();
		scale.setScheme(vis.getColor() +"s");
		color.setScale(scale);
		Legend legend = new Legend(); 
		legend.setTitle(vis.getzAxisDimension());
		color.setLegend(legend);
	}
	
	encoding.setX(x);
	encoding.setY(y);
	encoding.setColor(color);
	

	// Setup marktype
	String mark;
	if (vis.getMark().equals("scatterplott")) {
		mark = "point";
	}else{
		mark= vis.getMark();
	}

	// Create json
	VegaLiteChart chart = new VegaLiteChart(width, height, data,selection, mark, encoding);
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	return chart;
}
	
public VegaLiteChart generatChart3(VisualizationFromDB vis) {
		
		//Set subtyp
		Boolean subtypColor = false;
			
		
		//Set Axis
		String xAxis = vis.getxAxisDimension();
		String yAxis = vis.getyAxisDimension();
		
		
		int width = vis.getWidth() ;
		int height = vis.getHeight();

		// Setup data
		DataChart data = new DataChart("table");
		
		// Setup interaction
		Interaction InteractionMaker = new Interaction();
		InteractionContainer interactionContainer = InteractionMaker.calculateInteraction(vis.getInteraction());
		Selection selection = interactionContainer.getSelection();
		Boolean withInteraction = (interactionContainer.getSelection().getId().getType() != "non");

		// Setup Endcodingchannel
		EncodingChannels encoding = new EncodingChannels();

		EncodingChannel y = new EncodingChannel();
		y.setField(yAxis);
		y.setType(vis.getyAxisType());
		Axis axisY = new Axis(vis.getyAxisTitle(),vis.getyAxisgrid());
		Scale scaleY = new Scale();
		Integer   a = vis.getyAxisRangeStep();
		scaleY.setRangeStep(a);
		y.setScale(scaleY);
		y.setAxis(axisY);
		if (vis.getNumberOfDimensions() == 3) {
			y.setAggregate(vis.getyAggregation());
		}
		
		
		y.setField("cancellation_num");
		y.setType("quantitative");
		
		EncodingChannel x = new EncodingChannel();
		x.setField(xAxis);
		x.setType(vis.getxAxisType());
		Axis axisX = new Axis(vis.getxAxisTitle(),vis.getxAxisGrid());
		Scale scaleX = new Scale();
		x.setAxis(axisX);
		a = vis.getxAxisRangestep();
		scaleX.setRangeStep(a);
		x.setScale(scaleX);
		
		EncodingChannel size = new EncodingChannel(); 
		size.setField(yAxis);
		if(vis.getMark().equals("point")) {
			size.setField(yAxis);
			size.setType("ordinal");
			encoding.setSize(size);
		}
		
		
		//Colo setup
		EncodingChannel color = new EncodingChannel();
		if(vis.getNumberOfDimensions() == 2) {
			color.setValue(vis.getColor());
			color.setCondition(interactionContainer.getConditions());
		}
		
		if(vis.getNumberOfDimensions() == 3) {
			color.setField(vis.getzAxisDimension());
			color.setType(vis.getzAxisType());
			Scale scale = new Scale();
			scale.setScheme(vis.getColor() +"s");
			color.setScale(scale);
			Legend legend = new Legend(); 
			legend.setTitle(vis.getzAxisDimension());
			color.setLegend(legend);
		}
		
		encoding.setX(x);
		encoding.setY(y);
		encoding.setColor(color);
		

		// Setup marktype
		String mark = vis.getMark();

		// Create json
		VegaLiteChart chart = new VegaLiteChart(width, height, data,selection, mark, encoding);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return chart;
	}
	
	
	
	public String buildJs(VegaLiteChart chart)

	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(chart);
		// System.out.println( json );
		return json;
	}
	
	
	
}
