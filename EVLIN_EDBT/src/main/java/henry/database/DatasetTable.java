package henry.database;

import java.io.Serializable;
import java.sql.Array;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;


import com.google.gson.JsonObject;

import prov.mybean.DisplayForm;

@Entity
	public class DatasetTable implements java.io.Serializable {

		private int iddataset;
		@Column
		@Type(type = "MyDisplayFormType")
		private DisplayForm[] value;
		private String name;
		private String transformation;
		private String literal;
		private String source;
	

		public DatasetTable() {
		}

		
		public DatasetTable(int iddataset) {
			this.iddataset = iddataset;
		}
		
		public DatasetTable(int iddataset, String lit) {
			this.iddataset = iddataset;
			this.literal=lit;
		}
		
		public DatasetTable(int iddataset, String nam, DisplayForm[]  value) {
			this.iddataset = iddataset;
			this.name = nam;
			this.value=value;
		}
		
		
		public DatasetTable(int iddataset, String nam, DisplayForm[]  value,String source) {
			this.iddataset = iddataset;
			this.name = nam;
			this.value=value;
			this.source=source;
		}
		
		
		public DatasetTable(int iddataset, DisplayForm[]  value, String name, String transformation, String literal,
				String source) {
			this.iddataset = iddataset;
			this.value = value;
			this.name = name;
			this.transformation = transformation;
			this.literal = literal;
			this.source = source;
			
		}

		@Id
		public int getIddataset() {
			return this.iddataset;
		}

		public void setIddataset(int iddataset) {
			this.iddataset = iddataset;
		}

		public DisplayForm[]  getValue() {
			return this.value;
		}

		public void setValue(/*Serializable*/DisplayForm[]  value) {
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

		public void setTransformation(String transformation) {
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


	}
