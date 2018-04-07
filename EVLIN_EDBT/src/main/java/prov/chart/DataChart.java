package prov.chart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;

import prov.mybean.DisplayForm;

public class DataChart  {

	private String name;
	private List<DisplayForm/*String*/> values;
	private String source;
	List<Transformation> transform;
	
	
	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public List<Transformation> getTransform() {
		return transform;
	}


	public void setTransform(List<Transformation> transform) {
		this.transform = transform;
	}



	
	
	
	
	

	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List</*String*/DisplayForm> getValues() {
		return values;
	}


	public void setValues(List</*String*/DisplayForm> values) {
		this.values = values;
	}
	
	

	public DataChart() {
		this.transform= new ArrayList<Transformation>();
	}

	public DataChart(String name) {
		super();
		this.name = name;
		this.transform= new ArrayList<Transformation>();
		
	}
	public DataChart(String name, List</*String*/DisplayForm> values) {
		super();
		this.name = name;
		this.values = values;
		this.transform= new ArrayList<Transformation>();
	}
	


	public DataChart(String name, String source, List<Transformation> transform) {
		super();
		this.name = name;
		
		this.source = source;
		this.transform = transform;
	}


	public DataChart(String name, List</*String*/DisplayForm> values, List<Transformation> transform) {
		super();
		this.name = name;		
		this.values = values;
		this.transform = transform;
	}
	

	
}
