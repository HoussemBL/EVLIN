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

public class Pie extends Transformation  {

	String field;

	

	public Pie(String type, String test) {
		super();
		this.type = type;
		this.field = test;
	}

	

	public Pie( String test) {
		super();
		this.type = "pie";
		this.field = test;
	}


	public String getTest() {
		return field;
	}




	public void setTest(String test) {
		this.field = test;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public Pie() {
	}



	








}
