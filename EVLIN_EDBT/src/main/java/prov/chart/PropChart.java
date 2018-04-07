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

public class PropChart  {

	
ActionChart enter;
ActionChart update;
ActionChart hover;



public ActionChart getEnter() {
	return enter;
}
public void setEnter(ActionChart enter) {
	this.enter = enter;
}
public ActionChart getUpdate() {
	return update;
}
public void setUpdate(ActionChart update) {
	this.update = update;
}
public ActionChart getHover() {
	return hover;
}
public void setHover(ActionChart hover) {
	this.hover = hover;
}
public PropChart(ActionChart enter, ActionChart update, ActionChart hover) {
	super();
	this.enter = enter;
	this.update = update;
	this.hover = hover;
}
public PropChart(ActionChart enter, ActionChart update) {
	super();
	this.enter = enter;
	this.update = update;
}


public PropChart(ActionChart enter) {
	super();
	this.enter = enter;
	
}




public PropChart() {
	// TODO Auto-generated constructor stub
}



}
