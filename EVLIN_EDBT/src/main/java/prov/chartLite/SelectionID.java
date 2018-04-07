package prov.chartLite;

public class SelectionID {
	String on;
	String type;

	public SelectionID(String on, String type){
		this.on = on;
		this.type = type;
	}
	
	public SelectionID(String type ){
		this.type = type;
	}
	
	public String getOn() {
		return on;
	}

	public void setOn(String on) {
		this.on = on;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
