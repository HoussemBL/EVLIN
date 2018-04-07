package henry.database;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class ScaleTable implements java.io.Serializable {

	private int idscale;
	private int idDataset;
	private String typescale;
	private String namescale;
	private String fielddom;
	private String fieldrange;
	private String literal;


	public ScaleTable() {
	}

	public ScaleTable(int idscale, int dataset) {
		this.idscale = idscale;
		this.idDataset = dataset;
	}

	public ScaleTable(int idscale, int dataset, String typescale, String namescale, String fielddom, String fieldrange,String literal) {
		this.idscale = idscale;
		this.idDataset = dataset;
		this.typescale = typescale;
		this.namescale = namescale;
		this.fielddom = fielddom;
		this.fieldrange = fieldrange;
		this.literal = literal;

	}
	
@Id
	public int getIdscale() {
		return this.idscale;
	}

	public void setIdscale(int idscale) {
		this.idscale = idscale;
	}

	public int getDataset() {
		return this.idDataset;
	}

	public void setDataset(int dataset) {
		this.idDataset = dataset;
	}

	public String getTypescale() {
		return this.typescale;
	}

	public void setTypescale(String typescale) {
		this.typescale = typescale;
	}

	public String getNamescale() {
		return this.namescale;
	}

	public void setNamescale(String namescale) {
		this.namescale = namescale;
	}

	public String getFielddom() {
		return this.fielddom;
	}

	public void setFielddom(String fielddom) {
		this.fielddom = fielddom;
	}

	public String getFieldrange() {
		return this.fieldrange;
	}

	public void setFieldrange(String fieldrange) {
		this.fieldrange = fieldrange;
	}

	public String getLiteral() {
		return this.literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}

	

}
