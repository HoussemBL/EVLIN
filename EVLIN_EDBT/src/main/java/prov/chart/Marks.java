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

public class Marks extends MarkGeneral {
	Map<String,Object> from;
	

	public void setFrom(Map<String, Object> from) {
		this.from = from;
	}
	public Map<String, Object> getFrom() {
		return from;
	}



	public Marks() {
	}



	public Marks(String type, PropChart properties) {
		super();
		this.type = type;
		this.properties = properties;
	}


	public Marks(String type, Map<String,Object> from, PropChart properties) {
		super();
		this.type = type;
		this.from = from;
		this.properties = properties;
	}
	
	public Marks(String type, String name, PropChart properties) {
		super();
		this.type = type;
		this.name=name;
		this.properties = properties;
	}
	
	
	
	//for group marks
	public Marks( Map<String, Object> from,
			List<MarkGeneral> marks) {
		super();
		this.type = "group";
		this.from = from;
	
		this.marks = marks;
	}
}
