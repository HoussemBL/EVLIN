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

public class ActChart  {

	
String scale;
Object field;
Object value;
Boolean band;
int offset;
float mult;
String signal;





public float getMulti() {
	return mult;
}
public void setMulti(float multi) {
	this.mult = multi;
}
public String getSignal() {
	return signal;
}
public void setSignal(String signal) {
	this.signal = signal;
}




public String getScale() {
	return scale;
}
public void setScale(String scale) {
	this.scale = scale;
}
public Object getField() {
	return field;
}
public void setField(String field) {
	this.field = field;
}
public Object getValue() {
	return value;
}
public void setValue(Object value) {
	this.value = value;
}
public Boolean getBand() {
	return band;
}
public void setBand(Boolean band) {
	this.band = band;
}
public int getOffset() {
	return offset;
}
public void setOffset(int offset) {
	this.offset = offset;
}



public ActChart(String scale, String field, Object value, Boolean band, int offset, float multi, String signal) {
	super();
	this.scale = scale;
	this.field = field;
	this.value = value;
	this.band = band;
	this.offset = offset;
	this.mult = multi;
	this.signal = signal;
}







public ActChart(String scale, String field, Object value, Boolean band, int offset) {
	super();
	this.scale = scale;
	this.field = field;
	this.value = value;
	this.band = band;
	this.offset = offset;
	
}


public ActChart(String scale, String field) {
	super();
	this.scale = scale;
	this.field = field;
	
}

public ActChart(Object value) {
	super();
	this.value = value;
	
}


public ActChart(String scale, Boolean band, float multi) {
	super();
	this.scale = scale;
	this.band = band;
	this.mult = multi;
}


public ActChart( float multi,Object field) {
	super();
	this.field = field;
	
	this.mult = multi;
}
public ActChart(float multi,int offset, String signal) {
	super();
	this.offset = 0;
	this.signal = signal;
	this.mult = multi;
}

public ActChart(String scale,  String signal,int offset) {
	super();
	this.scale = scale;
	this.offset = offset;
	this.signal = signal;
}
public ActChart(int offset, String signal) {
	super();
	this.offset = 0;
	this.signal = signal;
}
public ActChart(String scale,Boolean band, int offset) {
	super();
	this.scale = scale;
	
	this.band = band;
	this.offset = offset;
}
public ActChart(String scale,Object value) {
	super();

	this.scale = scale;
	this.value = value;
	
}


public ActChart(Boolean band, int offset,String f) {
	super();
	this.field = f;
	
	this.band = band;
	this.offset = offset;
}

public ActChart(Boolean band,String f) {
	super();
	this.field = f;
	this.band = band;
	
}


public ActChart(float mult, String f,int offset,Object val) {
	super();
	this.scale = f;
	this.value=val;
	this.mult=mult;
	this.offset = offset;
}

public ActChart(int offset, int ml) {
	super();
	this.offset = offset;
	this.mult = ml;
}




public ActChart() {
	super();
	// TODO Auto-generated constructor stub
}
	
}
