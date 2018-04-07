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

public class ActionChartText extends ActionChart  {

	

public ActionChartText() {
	super();
	// TODO Auto-generated constructor stub
}

public ActionChartText(ActChart x, ActChart y, Map<String,String> text) {
	super();
	this.x = x;
	this.y = y;
	this.text=text;

}








	
}
