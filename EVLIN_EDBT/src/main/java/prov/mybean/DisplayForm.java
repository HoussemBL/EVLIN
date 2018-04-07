package prov.mybean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;

public class DisplayForm extends ActionForm implements Serializable  {
	private String axeX;
	private Double AggNumber;
	public String axeX2;
	public String color;
	
	
	
	

	public DisplayForm() {
	}

	public DisplayForm(String k, Double val) {

		this.axeX=k;
		this.AggNumber=val;
	}

	
	public DisplayForm(String groupby, String val, Double nummer) {
		super();
		this.axeX2 = groupby;
		this.axeX= val;
		this.AggNumber = nummer;
	}


	
	public DisplayForm(String k, String color) {

		this.axeX=k;
		this.color=color;
	}

	
	


	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getaxeX2() {
		return axeX2;
	}




	public void setaxeX2(String groupby) {
		this.axeX2 = groupby;
	}




	


	public Double getAggNumber() {
		return AggNumber;
	}




	public String getAxeX2() {
		return axeX2;
	}




	public void setAggNumber(Double aggNumber) {
		AggNumber = aggNumber;
	}




	public void setAxeX2(String axeX2) {
		this.axeX2 = axeX2;
	}






	public String getAxeX() {
		return axeX;
	}



	public void setAxeX(String axeX) {
		this.axeX = axeX;
	}





	public Double getCancellation_num() {
		return AggNumber;
	}

	public void setCancellation_num(Double cancellation_num) {
		this.AggNumber = cancellation_num;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.AggNumber = null;
		this.axeX = null;
		
	}

	
	//cancel previously
	public static Comparator<DisplayForm> COMPARE_BY_agg = new Comparator<DisplayForm>() {
        public int compare(DisplayForm one, DisplayForm other) {
            return one.AggNumber.compareTo(other.AggNumber);
        }
    };
    
    
    public static Comparator<DisplayForm> COMPARE_BY_axeX = new Comparator<DisplayForm>() {
        public int compare(DisplayForm one, DisplayForm other) {
            return one.axeX.compareTo(other.axeX);
        }
    };
  
    public static Comparator<DisplayForm> COMPARE_BY_axeX2 = new Comparator<DisplayForm>() {
        public int compare(DisplayForm one, DisplayForm other) {
            return one.axeX2.compareTo(other.axeX2);
        }
    };
  
}
