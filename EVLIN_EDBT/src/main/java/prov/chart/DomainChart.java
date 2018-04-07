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

public class DomainChart  {

	
String data;
String field;
//Map<String,String> sort;
Object sort;






public DomainChart(String data, String field, /*Map<String, String>*/Object sort) {
	super();
	this.data = data;
	this.field = field;
	this.sort = sort;
}


public DomainChart(String data, String field) {
	super();
	this.data = data;
	this.field = field;
	
}





public String getData() {
	return data;
}







public void setData(String data) {
	this.data = data;
}







public String getField() {
	return field;
}







public void setField(String field) {
	this.field = field;
}







public /*Map<String, String>*/ Object getSort() {
	return sort;
}







public void setSort(/*Map<String, String>*/ Object sort) {
	this.sort = sort;
}







public DomainChart() {
	super();
	// TODO Auto-generated constructor stub
}
	
}
