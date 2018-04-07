package prov.chartLite;

public class EncodingChannel {
	
	String name = null; 
	String field = null; 
	String type = null; 
	Conditions condition = null;
	String value =null;
	Scale scale = null;
	Axis axis= null;
	Legend legend = null;
	
	
	String aggregate = null;
	public String getAggregate() {
		return aggregate;
	}
	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Conditions getCondition() {
		return condition;
	}
	public void setCondition(Conditions condition) {
		this.condition = condition;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Scale getScale() {
		return scale;
	}
	public void setScale(Scale scale) {
		this.scale = scale;
	}
	public Axis getAxis() {
		return axis;
	}
	public void setAxis(Axis axis) {
		this.axis = axis;
	}
	public Legend getLegend() {
		return legend;
	}
	public void setLegend(Legend legend) {
		this.legend = legend;
	}

	
}
