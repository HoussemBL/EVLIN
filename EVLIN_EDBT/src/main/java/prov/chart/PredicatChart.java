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

public class PredicatChart  {

	
String name;
String type;
List<Map<String,String>> operands;








public PredicatChart(String name, String type, List<Map<String, String>> ope) {
	super();
	this.name = name;
	this.type = type;
	operands = ope;
}








public String getName() {
	return name;
}








public void setName(String name) {
	this.name = name;
}








public String getType() {
	return type;
}








public void setType(String type) {
	this.type = type;
}








public List<Map<String, String>> getOperands() {
	return operands;
}








public void setOperands(List<Map<String, String>> operands) {
	operands = operands;
}








public PredicatChart() {
	super();
	// TODO Auto-generated constructor stub
}
	
}
