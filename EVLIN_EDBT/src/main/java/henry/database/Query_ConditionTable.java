package henry.database;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Query_ConditionTable {
	int c_id;
	int q_id;
	String dimension;
	String operator;
	String value;
	
	
	public Query_ConditionTable(int c_id, int q_id, String dimension, String operator, String value) {
		super();
		this.c_id = c_id;
		this.q_id = q_id;
		this.dimension = dimension;
		this.operator = operator;
		this.value = value;
	}
	
	@Id
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public int getQ_id() {
		return q_id;
	}
	public void setQ_id(int q_id) {
		this.q_id = q_id;
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
}
