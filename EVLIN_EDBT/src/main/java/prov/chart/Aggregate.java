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

public class Aggregate extends Transformation  {

	List<String> groupby;
	List<Map<String,Object>> summarize;

	




	public List<String> getTest() {
		return groupby;
	}




	public void setTest(List<String> test) {
		this.groupby = test;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}



	
	
	
	
	
	

	public List<String> getGroupby() {
		return groupby;
	}




	public void setGroupby(List<String> groupby) {
		this.groupby = groupby;
	}




	public List<Map<String, Object>> getSummarize() {
		return summarize;
	}




	public void setSummarize(List<Map<String, Object>> summarize) {
		this.summarize = summarize;
	}




	public Aggregate() {
	}



	
	public Aggregate(String type, List<String> grp, List<Map<String, Object>> summarize) {
		super();
		this.type = type;
		this.groupby = grp;
		this.summarize=summarize;
	}



	
	public Aggregate( List<String> grp) {
		super();
		this.type = "facet";
		this.groupby = grp;
	
	}






}
