package prov.mybean;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;

public class DBForm extends ActionForm {
	private String name;
	private String real_name;
	private Boolean selected;

	public DBForm() {
		this.real_name="USflights2";
		this.name="USflights2";
		this.selected=true;
	}

	public DBForm(String iata, String original) {

		this.name = iata;
		this.real_name = original;
		this.selected=false;
	
	}



	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.name = "USflights2";
		this.real_name = "USflights2";
	
	}

}
