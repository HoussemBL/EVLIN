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

public class Signal extends SignalChart {
  
	Object init;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Signal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Signal(String name, Object init) {
		super();
		this.name = name;
		this.init = init;
	}

}
