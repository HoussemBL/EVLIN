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

public class Formula extends Transformation  {

	String field;
	String expr;

	

	

	public String getField() {
		return field;
	}





	public void setField(String field) {
		this.field = field;
	}





	public String getExpr() {
		return expr;
	}





	public void setExpr(String expr) {
		this.expr = expr;
	}





	public Formula(String field, String expr) {
		super();
		this.type="formula";
		this.field = field;
		this.expr = expr;
	}





	public Formula() {
	}



	








}
