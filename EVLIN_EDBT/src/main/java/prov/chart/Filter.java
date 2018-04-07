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

public class Filter extends Transformation  {

	String test;

	

	public Filter(String type, String test) {
		super();
		this.type = type;
		this.test = test;
	}

	

	public Filter( String test) {
		super();
		this.type = "filter";
		this.test = test;
	}


	public String getTest() {
		return test;
	}




	public void setTest(String test) {
		this.test = test;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public Filter() {
	}



	








}
