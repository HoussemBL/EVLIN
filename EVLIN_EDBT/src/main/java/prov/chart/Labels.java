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

public class Labels  {
	LabelAxeChart fill ;
	LabelAxeChart angle ;
	LabelAxeChart dx ;
	LabelAxeChart dy ;
	LabelAxeChart stroke;
	LabelAxeChart fontSize;

	public Labels() {
	}



	public LabelAxeChart getFill() {
		return fill;
	}



	public LabelAxeChart getStroke() {
		return stroke;
	}



	public void setStroke(LabelAxeChart stroke) {
		this.stroke = stroke;
	}



	public void setFill(LabelAxeChart fill) {
		this.fill = fill;
	}



	public LabelAxeChart getAngle() {
		return angle;
	}



	public void setAngle(LabelAxeChart angle) {
		this.angle = angle;
	}



	public LabelAxeChart getDx() {
		return dx;
	}



	public void setDx(LabelAxeChart dx) {
		this.dx = dx;
	}



	public LabelAxeChart getFontSize() {
		return fontSize;
	}



	public void setFontSize(LabelAxeChart fontSize) {
		this.fontSize = fontSize;
	}

	
	public Labels(LabelAxeChart fill, LabelAxeChart font) {
		super();
		this.fill = fill;
		this.fontSize=font;
	}

	
	
	public Labels(LabelAxeChart fill, LabelAxeChart angle, LabelAxeChart dx) {
		super();
		this.fill = fill;
		this.angle = angle;
		this.dx = dx;
		this.dy = new LabelAxeChart(-8);
		
	}

	public Labels(LabelAxeChart fill, LabelAxeChart angle, LabelAxeChart dx, LabelAxeChart font) {
		super();
		this.fill = fill;
		this.angle = angle;
		this.dx = dx;
		this.fontSize=font;
		this.dy = new LabelAxeChart(-8);
	}



	public Labels(LabelAxeChart fi) {
		super();
		this.stroke = fi;
		
	}




}
