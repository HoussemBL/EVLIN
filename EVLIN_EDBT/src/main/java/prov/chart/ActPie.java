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

public class ActPie extends ActionChart {

	ActChart startAngle;
	ActChart endAngle;
	ActChart innerRadius;
	ActChart outerRadius;
	ActChart stroke;
	public ActChart getStartAngle() {
		return startAngle;
	}
	public void setStartAngle(ActChart startAngle) {
		this.startAngle = startAngle;
	}
	public ActChart getEndAngle() {
		return endAngle;
	}
	public void setEndAngle(ActChart endAngle) {
		this.endAngle = endAngle;
	}
	public ActChart getInnerRadius() {
		return innerRadius;
	}
	public void setInnerRadius(ActChart innerRadius) {
		this.innerRadius = innerRadius;
	}
	public ActChart getOuterRadius() {
		return outerRadius;
	}
	public void setOuterRadius(ActChart outerRadius) {
		this.outerRadius = outerRadius;
	}
	public ActChart getStroke() {
		return stroke;
	}
	public void setStroke(ActChart stroke) {
		this.stroke = stroke;
	}
	
	public ActPie(ActChart x, ActChart y, ActChart startAngle, ActChart endAngle, ActChart innerRadius,
			ActChart outerRadius, ActChart stroke, ActChart fill) {
		this.x=x;
		this.y=y;
		this.startAngle = startAngle;
		this.endAngle = endAngle;
		this.innerRadius = innerRadius;
		this.outerRadius = outerRadius;
		this.stroke = stroke;
		this.fill=fill;
	}
	
	
}
