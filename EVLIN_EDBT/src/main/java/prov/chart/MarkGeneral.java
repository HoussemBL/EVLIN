package prov.chart;

import java.util.List;
import java.util.Map;

public class MarkGeneral {
 String type;
	PropChart properties;
	List<ScaleChart> scales;
List<MarkGeneral> marks;
	String name;

	
	

	public List<ScaleChart> getScales() {
		return scales;
	}


	public void setScales(List<ScaleChart> scales) {
		this.scales = scales;
	}





	public List<MarkGeneral> getMarks() {
		return marks;
	}


	public void setMarks(List<MarkGeneral> marks) {
		this.marks = marks;
	}



	public String getName() {
		return name;
	}


	public void setName(String name) {
		name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	





	public PropChart getProperties() {
		return properties;
	}


	public void setProperties(PropChart properties) {
		this.properties = properties;
	}


	
	
}
