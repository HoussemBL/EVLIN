package henry.database;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class QueryTable implements java.io.Serializable {

	private int idquery;
	private String datasetname;
	private Serializable transformation;
	private Serializable datasetvalue;
	private String literal;
	

	public QueryTable() {
	}

	public QueryTable(int idquery) {
		this.idquery = idquery;
	}

	public QueryTable(int idquery, String datasetname, Serializable transformation, Serializable datasetvalue,
			String literal) {
		this.idquery = idquery;
		this.datasetname = datasetname;
		this.transformation = transformation;
		this.datasetvalue = datasetvalue;
		this.literal = literal;
		
	}
	
@Id
	public int getIdquery() {
		return this.idquery;
	}

	public void setIdquery(int idquery) {
		this.idquery = idquery;
	}

	public String getDatasetname() {
		return this.datasetname;
	}

	public void setDatasetname(String datasetname) {
		this.datasetname = datasetname;
	}

	public Serializable getTransformation() {
		return this.transformation;
	}

	public void setTransformation(Serializable transformation) {
		this.transformation = transformation;
	}

	public Serializable getDatasetvalue() {
		return this.datasetvalue;
	}

	public void setDatasetvalue(Serializable datasetvalue) {
		this.datasetvalue = datasetvalue;
	}

	public String getLiteral() {
		return this.literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}



}
