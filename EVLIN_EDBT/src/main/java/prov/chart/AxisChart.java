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

public class AxisChart  {
	String type ;
	String scale ;
	String title ;
	Map<String,Labels> properties;
	Integer tickSize;
	Integer tickPadding;
    Integer offset;
    Integer ticks;

	public AxisChart() {
		this.ticks=10;
	}




	public Integer getOffset() {
		return offset;
	}




	public Integer getTicks() {
		return ticks;
	}




	public void setOffset(Integer offset) {
		this.offset = offset;
	}




	public void setTicks(Integer ticks) {
		this.ticks = ticks;
	}




	public Integer getTickSize() {
		return tickSize;
	}




	public void setTickSize(Integer tickSize) {
		this.tickSize = tickSize;
	}




	public Integer getTickPadding() {
		return tickPadding;
	}




	public void setTickPadding(Integer tickPadding) {
		this.tickPadding = tickPadding;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public String getScale() {
		return scale;
	}




	



	public void setScale(String scale) {
		this.scale = scale;
	}




	





	public Map<String, Labels> getProperties() {
		return properties;
	}




	public void setProperties(Map<String, Labels> properties) {
		this.properties = properties;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
		
	}




	public AxisChart(String type, String scale, String title) {
		super();
		this.type = type;
		this.scale = scale;
		this.title = title;
		this.ticks=10;
	}


	public AxisChart(String type, String scale, String title, Map<String,Labels> properties) {
		super();
		this.type = type;
		this.scale = scale;
		this.title = title;
		this.properties = properties;
		this.ticks=10;
	}




	public AxisChart(String type, String scale, String title, Integer tickSize, Integer tickPadding) {
		super();
		this.type = type;
		this.scale = scale;
		this.title = title;
		this.tickSize = tickSize;
		this.tickPadding = tickPadding;
		this.ticks=10;
	}




	public AxisChart(Integer tickSize,
			Integer offset,String type, String scale, String title, Map<String, Labels> properties ) {
		super();
		this.type = type;
		this.scale = scale;
		this.title = title;
		this.properties = properties;
		this.tickSize = tickSize;
		this.offset = offset;
		this.ticks=10;
	}

	
	public AxisChart(Integer tt, String type, String scale, String title) {
		super();
		this.type = type;
		this.scale = scale;
		this.title = title;
		this.ticks=tt;
	}

	
}
