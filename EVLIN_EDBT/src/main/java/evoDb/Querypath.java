package evoDb;
// default package
// Generated Jan 8, 2018 2:36:58 PM by Hibernate Tools 3.4.0.CR1

/**
 * Querypath generated by hbm2java
 */
public class Querypath implements java.io.Serializable {

	private int pathId;
	private int parentQueryId;
	private int QId;

	public Querypath() {
	}

	public Querypath(int pathId, int parentQueryId, int QId) {
		this.pathId = pathId;
		this.parentQueryId = parentQueryId;
		this.QId = QId;
	}

	public int getPathId() {
		return this.pathId;
	}

	public void setPathId(int pathId) {
		this.pathId = pathId;
	}

	public int getParentQueryId() {
		return this.parentQueryId;
	}

	public void setParentQueryId(int parentQueryId) {
		this.parentQueryId = parentQueryId;
	}

	public int getQId() {
		return this.QId;
	}

	public void setQId(int QId) {
		this.QId = QId;
	}

}
