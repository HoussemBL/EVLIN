package prov.chart;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;

public class LegendChart  {
String title;
String fill;
PropLegend properties;
String size;

	
	
	
	
	
	
	
	
	public LegendChart() {
		super();
	}










	public String getTitle() {
		return title;
	}










	public void setTitle(String title) {
		this.title = title;
	}










	public String getFill() {
		return fill;
	}










	public void setFill(String fill) {
		this.fill = fill;
	}










	public PropLegend getProperties() {
		return properties;
	}










	public void setProperties(PropLegend properties) {
		this.properties = properties;
	}










	public LegendChart(String title, String fill, PropLegend properties) {
		super();
		this.title = title;
		this.fill = fill;
		this.properties = properties;
	}




	public LegendChart(String title, PropLegend properties, String size) {
		super();
		this.title = title;
		this.size = size;
		this.properties = properties;
	}



	
}
