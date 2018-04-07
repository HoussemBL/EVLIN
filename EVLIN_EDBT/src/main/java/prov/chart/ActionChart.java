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

public class ActionChart  {

	
ActChart x;
ActChart width;
ActChart y;
ActChart y2;
ActChart fill;
ActChart dx;
Map<String,String> text;
ActChart align;
ActChart dy;
Map<String,Object> fillOpacity;
ActChart size;
ActChart interpolate;
ActChart strokeWidth;
ActChart stroke;





public ActChart getDy() {
	return dy;
}
public void setDy(ActChart dy) {
	this.dy = dy;
}
public ActChart getAlign() {
	return align;
}
public void setAlign(ActChart align) {
	this.align = align;
}

public ActChart getDx() {
	return dx;
}
public void setDx(ActChart dx) {
	this.dx = dx;
}
public Map<String, String> getText() {
	return text;
}
public void setText(Map<String, String> text) {
	this.text = text;
}


public ActChart getX() {
	return x;
}
public void setX(ActChart x) {
	this.x = x;
}
public ActChart getWidth() {
	return width;
}
public void setWidth(ActChart width) {
	this.width = width;
}
public ActChart getY() {
	return y;
}
public void setY(ActChart y) {
	this.y = y;
}
public ActChart getY2() {
	return y2;
}
public void setY2(ActChart y2) {
	this.y2 = y2;
}
public ActChart getFill() {
	return fill;
}
public void setFill(ActChart fill) {
	this.fill = fill;
}















public Map<String, Object> getFillOpacity() {
	return fillOpacity;
}
public ActChart getSize() {
	return size;
}
public ActChart getInterpolate() {
	return interpolate;
}
public ActChart getStrokeWidth() {
	return strokeWidth;
}
public ActChart getStroke() {
	return stroke;
}
public void setFillOpacity(Map<String, Object> fillOpacity) {
	this.fillOpacity = fillOpacity;
}
public void setSize(ActChart size) {
	this.size = size;
}
public void setInterpolate(ActChart interpolate) {
	this.interpolate = interpolate;
}
public void setStrokeWidth(ActChart strokeWidth) {
	this.strokeWidth = strokeWidth;
}
public void setStroke(ActChart stroke) {
	this.stroke = stroke;
}





public ActionChart(ActChart x, ActChart width, ActChart y, ActChart y2, ActChart fill) {
	super();
	this.x = x;
	this.width = width;
	this.y = y;
	this.y2 = y2;
	this.fill = fill;
}



public ActionChart(ActChart x, ActChart width, ActChart y, ActChart y2) {
	super();
	this.x = x;
	this.width = width;
	this.y = y;
	this.y2 = y2;
	
}



public ActionChart(ActChart fill, ActChart align) {
	super();
	this.fill = fill;
	this.align = align;
}

public ActionChart( ActChart fill) {
	super();

	this.fill = fill;
}
public ActionChart() {
	super();
	// TODO Auto-generated constructor stub
}
public ActionChart( Map<String, Object> ddd) {
	super();

	this.fillOpacity = ddd;
}

public ActionChart(ActChart x, ActChart y, ActChart dx) {
	super();
	this.x = x;
	this.y = y;
	this.dx = dx;
}
public ActionChart(ActChart x, ActChart y, ActChart dx, Map<String, String> text) {
	super();
	this.x = x;
	this.y = y;
	this.dx = dx;
	this.text = text;
}








	
}
