package prov.chart;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;

public class ScaleChart  {

	private String name;
	private String type;
	private Object range;
	private boolean nice;
	private boolean round;
	private DomainChart domain;
	private double padding;
private boolean points;


	public ScaleChart() {
	}


	public boolean isPoints() {
		return points;
	}


	public void setPoints(boolean points) {
		this.points = points;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public boolean isRound() {
		return round;
	}


	public void setRound(boolean round) {
		this.round = round;
	}




	public double getPadding() {
		return padding;
	}


	public void setPadding(double padding) {
		this.padding = padding;
	}


	public Object getRange() {
		return range;
	}


	public void setRange(Object range) {
		this.range = range;
	}


	public boolean isNice() {
		return nice;
	}


	public void setNice(boolean nice) {
		this.nice = nice;
	}


	public DomainChart getDomain() {
		return domain;
	}


	public void setDomain(DomainChart domain) {
		this.domain = domain;
	}

	




	public ScaleChart(String name, String type, String range, boolean nice, DomainChart domain) {
		super();
		this.name = name;
		this.type = type;
		this.range = range;
		this.nice = nice;
		this.domain = domain;
		//this.padding=1;
	}


	public ScaleChart(String name, String range, boolean nice, DomainChart domain) {
		super();
		this.name = name;
	this.range = range;
		this.nice = nice;
		this.domain = domain;
	}

	
	

	public ScaleChart(String name, String type, String range, DomainChart domain, double padding) {
		super();
		this.name = name;
		this.type = type;
		this.range = range;
		this.domain = domain;
		this.padding = padding;
	}


	public ScaleChart(String name, String type, String range, boolean nice, boolean round, DomainChart domain) {
		super();
		this.name = name;
		this.type = type;
		this.range = range;
		this.nice = nice;
		this.round = round;
		this.domain = domain;
		this.padding=1;
	}

	public ScaleChart(String name, String range,  DomainChart domain, String type) {
		super();
		this.name = name;
	this.range = range;
		this.type = type;
		this.domain = domain;
		this.padding=1;
	}

	public ScaleChart(String name, String type, String range, boolean nice, boolean round, DomainChart domain, double padding,boolean pt) {
		super();
		this.name = name;
		this.type = type;
		this.range = range;
		this.nice = nice;
		this.round = round;
		this.domain = domain;
		this.points=pt;
		this.padding=padding;
	}
	
	
	public ScaleChart(String name, List<String> range,  DomainChart domain, String type) {
		super();
		this.name = name;
	this.range = range;
		this.type = type;
		this.domain = domain;
		this.padding=1;
	}

	///scale associate to bubble chart
	public ScaleChart(String name,String type,  DomainChart domain, List<Integer> range ) {
		super();
		this.name = name;
	this.range = range;
		this.type = type;
		this.domain = domain;
		this.padding=1;
	}


	public ScaleChart(String name, String type, String range, boolean nice, DomainChart domain,double padding) {
		super();
		this.name = name;
		this.type = type;
		this.range = range;
		this.nice = nice;
		this.domain = domain;
		this.padding=padding;
	}
	
	
	
	
	public ScaleChart(String name,String type,  DomainChart domain, DomainChart range) {
		super();
		this.name = name;
		this.range = range;
		this.type = type;
		this.domain = domain;
		this.padding=1;
	}
	
	

	public ScaleChart(String name) {
		super();
		this.name = name;
		
	}

	
	
}
