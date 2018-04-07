
package evoDb;
// default package
// Generated Jan 8, 2018 2:36:58 PM by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;

/**
 * Dataset generated by hbm2java
 */
public class Dataset implements java.io.Serializable {

	private int iddataset;
	private Serializable value;
	private String name;
	private Serializable transformation;
	private String literal;
	private String source;
	private Set scales = new HashSet(0);

	public Dataset() {
	}

	public Dataset(int iddataset) {
		this.iddataset = iddataset;
	}

	public Dataset(int iddataset, Serializable value, String name, Serializable transformation, String literal,
			String source, Set scales) {
		this.iddataset = iddataset;
		this.value = value;
		this.name = name;
		this.transformation = transformation;
		this.literal = literal;
		this.source = source;
		this.scales = scales;
	}
	
	@Id
	public int getIddataset() {
		return this.iddataset;
	}

	public void setIddataset(int iddataset) {
		this.iddataset = iddataset;
	}

	public Serializable getValue() {
		return this.value;
	}

	public void setValue(Serializable value) {
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Serializable getTransformation() {
		return this.transformation;
	}

	public void setTransformation(Serializable transformation) {
		this.transformation = transformation;
	}

	public String getLiteral() {
		return this.literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Set getScales() {
		return this.scales;
	}

	public void setScales(Set scales) {
		this.scales = scales;
	}

}
