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

public class ActYheight extends ActionChart {

	ActChart height;
	ActChart yc;
	Map<String, Object> x2;
	Map<String,String> baseline;
	Map<String,Integer> size;
	ActChart stroke;
	
	
	
	
	public ActChart getHeight() {
		return height;
	}

	public void setHeight(ActChart height) {
		this.height = height;
	}


	
	

	
	
	
	public ActChart getYc() {
		return yc;
	}

	public void setYc(ActChart yc) {
		this.yc = yc;
	}

	public Map<String, String> getBaseline() {
		return baseline;
	}

	public void setBaseline(Map<String, String> baseline) {
		this.baseline = baseline;
	}

	

	

	public Map<String, Object> getX2() {
		return x2;
	}

	public void setX2(Map<String, Object> x2) {
		this.x2 = x2;
	}

	public ActYheight(ActChart x,  ActChart y, ActChart dy, ActChart fill, Map<String,String> baseline, Map<String,String> text ) {
	
		super(fill);
		this.text = text;
		this.x = x;
		this.dy = dy;
		this.y = y;
		this.fill = fill;
		this.baseline=baseline;
	}	
	
	
	public ActYheight(ActChart x,  ActChart y, ActChart dy, ActChart ali, Map<String,String> baseline, Map<String,String> text,Map<String,Object> fillOpacity ) {
		
		super();
		this.text = text;
		this.x = x;
		this.dy = dy;
		this.y = y;
		this.align=ali;
		this.baseline=baseline;
		this.fillOpacity=fillOpacity;
	}
	



	public ActYheight(Map<String,Object> x2 ,ActChart y,ActChart height, ActChart x,  ActChart fill ) {
		super(fill);
		this.height = height;
		this.x = x;
		this.x2 = x2;
		this.y = y;
		this.fill = fill;
	}	
	


	public ActYheight( ActChart y,  ActChart height) {
		super(y);
		this.height = height;
		this.y=y;
	}



	public ActYheight(ActChart y,Map<String,Object> x2 ,ActChart height, ActChart x,  ActChart fill ) {
		super(fill);
		this.height = height;
		this.x2 = x2;
		this.x = x;
		this.y=y;
		this.fill = fill;
	}

	public ActYheight(ActChart x,  ActChart fill,  ActChart yc,Map<String, Integer> size, ActChart stroke) {
		super(x,  fill);
	    this.x=x;
	    this.fill=fill;
		this.yc = yc;
		this.size = size;
		this.stroke = stroke;
	}	

	public ActYheight(ActChart x, ActChart width,ActChart yc, ActChart height,  ActChart fill) {
		super();
		this.height = height;
		this.yc = yc;
		this.width=width;
		this.fill=fill;
		this.x=x;
	}

	
}
