package prov.mybean;

public class CustomerBean {
String field;
Float val;
public String getField() {
	return field;
}
public void setField(String field) {
	this.field = field;
}
public Float getVal() {
	return val;
}
public void setVal(Float val) {
	this.val = val;
}
public CustomerBean(String field, Float val) {
	super();
	this.field = field;
	this.val = val;
}
}
