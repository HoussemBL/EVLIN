package prov.mybean;

import java.math.BigDecimal;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import prov.chart.VegaChart;
//import org.hibernate.Session;

public class VisualForm extends ActionForm {
	private String jsonresult;
	private VegaChart vegafile;
	private String name;
	private String status;
	public VisualForm() {
	}


	
	
	
	
	public String getStatus() {
		return status;
	}






	public void setStatus(String status) {
		this.status = status;
	}






	public String getJsonresult() {
		return jsonresult;
	}


	public void setJsonresult(String jsonresult) {
		this.jsonresult = jsonresult;
	}


	public VegaChart getVegafile() {
		return vegafile;
	}


	public void setVegafile(VegaChart vegafile) {
		this.vegafile = vegafile;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public VisualForm(String jsonresult, VegaChart vegafile, String name,String status) {
		super();
		this.jsonresult = jsonresult;
		this.vegafile = vegafile;
		this.name = name;
		this.status=status;
	}


	public VisualForm(String jsonresult, VegaChart vegafile, String name) {
		super();
		this.jsonresult = jsonresult;
		this.vegafile = vegafile;
		this.name = name;
		this.status="false";
	}


	
}
