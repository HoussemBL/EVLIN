package prov.mybean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;

public class QueryInputForm extends ActionForm {
	private String content;

	

	public QueryInputForm() {
		
	}


	public QueryInputForm(String cc) {
		super();
		content = cc;
	}





	public String getContent() {
		return content;
	}






	public void setContent(String content) {
		this.content = content;
	}




	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		 if(     getContent() == null  )
		        
				  {
		       errors.add("common.name.err",
	                         new ActionMessage("error.common.name.required"));}
		
		  return errors;}


	
}
