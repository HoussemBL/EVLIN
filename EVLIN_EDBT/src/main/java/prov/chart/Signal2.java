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

public class Signal2 extends SignalChart{


	Map<String, String> init;
	List<Stream> streams;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getInit() {
		return init;
	}

	public void setInit(Map<String, String> init) {
		this.init = init;
	}

	public List<Stream> getStreams() {
		return streams;
	}

	public void setStreams(List<Stream> streams) {
		this.streams = streams;
	}

	public Signal2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Signal2(String name, Map<String, String> init, List<Stream> streams) {
		super();
		this.name = name;
		this.init = init;
		this.streams = streams;
	}

	public Signal2(String name, Map<String, String> init) {
		super();
		this.name = name;
		this.init = init;

	}

}
