package prov.mybean;

import java.math.BigDecimal;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;


// it is contructed to generate the following json object   {"name": "Q131","group": 4,"index": 0}
public class MatrixForm extends ActionForm {
	private String name;
	private Integer group;
	private Integer index;

	public MatrixForm() {
	}

	public MatrixForm(String name, Integer group, Integer index) {
		super();
		this.name = name;
		this.group = group;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	
	

}
