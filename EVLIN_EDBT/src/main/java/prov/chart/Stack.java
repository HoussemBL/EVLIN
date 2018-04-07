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

public class Stack extends Transformation  {

	String field;
	List<String> groupby;
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public String getField() {
		return field;
	}

















	public void setField(String field) {
		this.field = field;
	}

















	public List<String> getGroupby() {
		return groupby;
	}

















	public void setGroupby(List<String> groupby) {
		this.groupby = groupby;
	}

















	public Stack(String field, List<String> groupby) {
		super();
		this.type="stack";
		this.field = field;
		this.groupby = groupby;
	}

















	public Stack() {
	}



	








}
