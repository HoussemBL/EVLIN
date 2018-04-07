package henry.QuerySimilarity;

public class Condition {
	String dimension; 
	String operator;
	String value;
	public Condition(String dimension, String operator, String value) {
		super();
		this.dimension = dimension;
		this.operator = operator;
		this.value = value;
	}
	
	
	
	
	
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	} 
	
	
	public boolean equals(Condition b) {
		if(this.dimension.equals(b.dimension)) {
			if(this.operator.equals(b.operator)) {
				if(this.value.equals(b.value)) {
					return true;
				}
			}
		}
		
		return false;
		
	}
}
