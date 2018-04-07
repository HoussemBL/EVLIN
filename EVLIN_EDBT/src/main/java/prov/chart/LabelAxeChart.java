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

public class LabelAxeChart  {
	Object value ;


	

	public LabelAxeChart() {
	}




	public Object getValue() {
		return value;
	}




	public void setValue(Object value) {
		this.value = value;
	}




	public LabelAxeChart(Object value) {
		super();
		this.value = value;
	}







}
