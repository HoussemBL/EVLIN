package prov.chart;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;

public class Stream  {

	private String type;
	private String expr;
	

	public Stream() {
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getExpr() {
		return expr;
	}


	public void setExpr(String expr) {
		this.expr = expr;
	}


	public Stream(String type, String expr) {
		super();
		this.type = type;
		this.expr = expr;
	}



	
}
