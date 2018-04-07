
package evoDb;
// default package
// Generated Jan 8, 2018 2:36:58 PM by Hibernate Tools 3.4.0.CR1

/**
 * QueryConditiontable generated by hbm2java
 */
public class QueryConditiontable implements java.io.Serializable {

	private int CId;
	private String dimension;
	private String operator;
	private int QId;
	private String value;

	public QueryConditiontable() {
	}

	public QueryConditiontable(int CId, int QId) {
		this.CId = CId;
		this.QId = QId;
	}

	public QueryConditiontable(int CId, String dimension, String operator, int QId, String value) {
		this.CId = CId;
		this.dimension = dimension;
		this.operator = operator;
		this.QId = QId;
		this.value = value;
	}

	public int getCId() {
		return this.CId;
	}

	public void setCId(int CId) {
		this.CId = CId;
	}

	public String getDimension() {
		return this.dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getQId() {
		return this.QId;
	}

	public void setQId(int QId) {
		this.QId = QId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
