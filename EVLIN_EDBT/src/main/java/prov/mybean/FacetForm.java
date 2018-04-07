package prov.mybean;

import java.math.BigDecimal;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;

public class FacetForm extends ActionForm {
	private String Key;
	private String Value;
	

	public FacetForm() {
		
	}

	public FacetForm(String k, String val) {

		this.Key=k;
		this.Value=val;
	}

	
	

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.Value = null;
		this.Key = null;
		
	}

}
