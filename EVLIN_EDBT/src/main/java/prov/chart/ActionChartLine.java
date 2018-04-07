package prov.chart;

import java.util.Map;

public class ActionChartLine extends ActionChart  {

	










public ActionChartLine() {
	super();
	// TODO Auto-generated constructor stub
}

public ActionChartLine(ActChart x, ActChart y, ActChart stroke, ActChart strokewidth,ActChart interpolate) {
	super();
	this.x = x;
	this.y = y;
	this.stroke=stroke;
	this.strokeWidth= strokewidth;
	this. interpolate =  interpolate;
	//this.dx= new ActChart();
	//this.dy= new ActChart();
}




public ActionChartLine( ActChart stroke) {
	super();
	
	this.stroke=stroke;
	
}




	
}