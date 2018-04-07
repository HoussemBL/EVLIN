package prov.chartLite;

import java.util.List;

public class DataChart {
	private String name;
	private List<DataContainer> values;
	public List<DataContainer> getValues() {
		return values;
	}

	public void setValues(List<DataContainer> values) {
		this.values = values;
	}
	private String source;
	
	public DataChart(String name, List<DataContainer> values) {
		super();
		//this.name = name;
		this.values = values;
	}
	public DataChart(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}


}
