package prov.chartLite;

public class Conditions {
	Selection selection;
	String value; 
	
	public Conditions(Selection selection, String value){
		this.selection = selection;
		this.selection.setNot("id");
		this.value = value;
	}
	
	public Selection getSelection() {
		return selection;
	}
	public void setSelection(Selection selection) {
		this.selection = selection;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
