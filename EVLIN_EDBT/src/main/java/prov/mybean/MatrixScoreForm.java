package prov.mybean;

import java.math.BigDecimal;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;


// it is contructed to generate the following      {"source": 0,"target": 24,"value": 6.8}
public class MatrixScoreForm extends ActionForm {
	private Integer source;
	private Integer target;
	private Float value;

	public MatrixScoreForm() {
	}

	public MatrixScoreForm(Integer source, Integer target, Float value) {
		super();
		this.source = source;
		this.target = target;
		this.value = value;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}


}
