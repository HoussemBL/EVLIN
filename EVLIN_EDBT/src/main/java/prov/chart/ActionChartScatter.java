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

public class ActionChartScatter extends ActionChart  {

	













public ActionChartScatter() {
	super();
	// TODO Auto-generated constructor stub
}

public ActionChartScatter(ActChart x, ActChart y, ActChart fill, Map<String,Object> fillOpacity,ActChart size) {
	super();
	this.x = x;
	this.y = y;
	this.fill=fill;
	this.size= size;
	this.fillOpacity = fillOpacity;
}


public ActionChartScatter(ActChart x, ActChart y, ActChart fill, ActChart stroke, ActChart strokeWitdh,ActChart size) {
	super();
	this.x = x;
	this.y = y;
	this.fill=fill;
	this.size= size;
	this.stroke=stroke;
	this.strokeWidth=strokeWitdh;
}





	
}
