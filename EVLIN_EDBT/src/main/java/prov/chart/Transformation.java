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

public class Transformation  {
	String type ;
	String by;

	

	
	
	
	public Transformation(String type, String by) {
		super();
		this.type = type;
		this.by = by;
	}




	public String getBy() {
		return by;
	}




	public void setBy(String by) {
		this.by = by;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public Transformation() {
	}



	








}
