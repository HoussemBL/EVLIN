package prov.chartLite;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import henry.visualizationGenerator.ExampleData;
import henry.visualizationGenerator.Size;

public class Selection {
	SelectionID id;
	String not;
	
	public Selection(String not) {
		this.not = not;
	}
	
	public Selection(SelectionID id){
		this.id = id;
	}
	
	public SelectionID getId() {
		return id;
	}

	public void setId(SelectionID id) {
		this.id = id;
	}

	public String getNot() {
		return not;
	}

	public void setNot(String not) {
		this.not = not;
	}
}
