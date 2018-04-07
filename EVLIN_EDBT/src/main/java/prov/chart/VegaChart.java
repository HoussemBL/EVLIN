package prov.chart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;

public class VegaChart  {
	private int width;
	private int height;
	private Object padding;
	private List<DataChart> data;
	private List<ScaleChart> scales;
	private List<AxisChart> axes;
	List<MarkGeneral> marks;
	private List<SignalChart> signals;
	private List<PredicatChart> predicates;
	private List<LegendChart> legends;
	
	
	

	public List<MarkGeneral> getMarks() {
		return marks;
	}


	public void setMarks(List<MarkGeneral> marks) {
		this.marks = marks;
	}





	public List<LegendChart> getLegends() {
		return legends;
	}


	public void setLegends(List<LegendChart> legends) {
		this.legends = legends;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public List<DataChart> getData() {
		return data;
	}


	public void setData(List<DataChart> data) {
		this.data = data;
	}


	

	public List<ScaleChart> getScales() {
		return scales;
	}


	public void setScales(List<ScaleChart> scales) {
		this.scales = scales;
	}


	




	public List<SignalChart> getSignals() {
		return signals;
	}


	public void setSignals(List<SignalChart> signals) {
		this.signals = signals;
	}


	public List<PredicatChart> getPredicates() {
		return predicates;
	}


	public void setPredicates(List<PredicatChart> predicates) {
		this.predicates = predicates;
	}


	public List<AxisChart> getAxes() {
		return axes;
	}


	public void setAxes(List<AxisChart> axes) {
		this.axes = axes;
	}



	public VegaChart(int width, int height, List<DataChart> data, List<ScaleChart> scales, List<AxisChart> axis, List<MarkGeneral> mark,List<SignalChart> sig, List<PredicatChart> per) {
		super();
	//	this.padding="auto";
		this.width = width;
		this.height = height;
		this.data = data;
		this.scales = scales;
		this.axes=axis;
		this.marks=mark;
		this.signals=sig;
		this.predicates=per;
	}

	public VegaChart(int width, int height, List<DataChart> data, List<ScaleChart> scales, List<AxisChart> axis, List<MarkGeneral> mark,List<LegendChart> legends) {
		super();
		//this.padding="auto";
		this.width = width;
		this.height = height;
		this.data = data;
		this.scales = scales;
		this.axes=axis;
		this.marks=mark;
		this.legends=legends;
	
	}


	public VegaChart(List<SignalChart> sig, int width, int height, List<DataChart> data, List<ScaleChart> scales, List<AxisChart> axis, List<MarkGeneral> mark) {
		super();
		//this.padding=50;
		this.width = width;
		this.height = height;
		this.data = data;
		this.scales = scales;
		this.axes=axis;
		this.marks=mark;
		this.signals=sig;
		
	}
	
	public VegaChart(int width, int height, List<DataChart> data, List<ScaleChart> scales,  List<MarkGeneral> mark,List<LegendChart> legends) {
		super();
		//this.padding="auto";
		this.width = width;
		this.height = height;
		this.data = data;
		this.scales = scales;
		this.legends=legends;
		this.marks=mark;
		
	}
	
	public VegaChart(int width, int height, List<DataChart> data, List<ScaleChart> scales, List<AxisChart> axis, List<MarkGeneral> mark,List<SignalChart> sig, List<PredicatChart> per,List<LegendChart> legs) {
		super();
		//this.padding="auto";
		this.width = width;
		this.height = height;
		this.data = data;
		this.scales = scales;
		this.axes=axis;
		this.marks=mark;
		this.signals=sig;
		//this.predicates=per;
		this.legends=legs;
	}
	
	
	
	public VegaChart( List<DataChart> data, List<ScaleChart> scales, List<AxisChart> axis, List<MarkGeneral> mark,List<LegendChart> legends) {
		super();
		
	
		this.data = data;
		this.scales = scales;
		this.axes=axis;
		this.marks=mark;
		this.legends=legends;
	
	}

	
	public VegaChart() {
		this.padding="auto";
		this.width = 0;
		this.height = 0;
		this.data= new ArrayList<DataChart>();
		this.axes=new ArrayList<AxisChart>();
		this.scales = new ArrayList<ScaleChart>();
		this.marks = new ArrayList<MarkGeneral>();
		
	}
	

	public Object getPadding() {
		return padding;
	}


	public void setPadding(Object padding) {
		this.padding = padding;
	}
	
	
	
}
