package prov.mybean;

import java.math.BigDecimal;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;



public class userForm extends ActionForm {
	/*private String iata;
	private String airport;
	private String city;
	private String state;
	private String country;
	private BigDecimal lat;
	private BigDecimal longi;
	
	public airportForm(){}
	
	
	
	public identificationForm( String iata,
	String airport,
	 String city,
	 String state,
String country,
	 BigDecimal lat,
 BigDecimal longi){
		
		this.iata=iata;
		this.airport=airport;
		this.city=city;
		this.state=state;
		this.country=country;
		this.lat=lat;
		this.longi=longi;
	}
	
	public String getiata() {
		return this.iata;
	}

	public void setiata(String iata) {
		this.iata = iata;
	}

	public String getAirport() {
		return this.airport;
	}

	public void setAirport(String air) {
		this.airport = air;
	}
	
	public String getCity() {
		return this.city;
	}

	public void setCity(String ci) {
		this.city= ci;
	}

	public String getStatet() {
		return this.state;
	}

	public void setState(String ss) {
		this.State = ss;
	}
	
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.iata = null;
		this.city=null;
		this.state=null;
		this.airport=null;
		}*/
	
	/*public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		 if(     getLogin() == null || ("".equals(getLogin()))||
				 
				 getMotDePasse() == null || ("".equals(getMotDePasse())) )
		        
				  {
		       errors.add("common.name.err",
	                         new ActionMessage("error.common.name.required"));}
		
		  return errors;}*/
	
	private String username;
	 
	
	public userForm(){}
	public userForm(String aa){this.username=aa;}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	/*public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.username = "";
		
		}*/
	
	
}
