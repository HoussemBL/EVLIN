package prov.chart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;

public class MarkChart extends MarkGeneral  {


	Map<String,String> from;
	

	public void setFrom(Map<String, String> from) {
		this.from = from;
	}
	public Map<String, String> getFrom() {
		return from;
	}

	
	
	
	



	public MarkChart() {
	}



	public MarkChart(String type, PropChart properties) {
		super();
		this.type = type;
		this.properties = properties;
	}


	public MarkChart(String type, Map<String, String> from, PropChart properties) {
		super();
		this.type = type;
		this.from = from;
		this.properties = properties;
	}


	public MarkChart(String type, Map<String, String> from, PropChart properties, List<ScaleChart> scales,
			List<MarkGeneral> marks) {
		super();
		this.type = type;
		this.from = from;
		this.properties = properties;
		this.scales = scales;
		this.marks = marks;
	}

	
	public MarkChart(String type) {
		super();
		this.type = type;
		
	}

	
}
