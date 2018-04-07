package prov.chart;

import java.util.HashMap;
import java.util.Map;

public class PropLegend {

	Map<String, Map<String, Object>> title;

Map<String, Map<String, Object>> labels;

Map<String, Map<String, Object>> symbols;
Map<String, Map<String, Object>>legend;




public PropLegend(Map<String, Map<String, Object>> title) {
	super();
	
	
}
public PropLegend() {
	super();
	Map<String, Map<String, Object>> lab = new HashMap<String,Map<String, Object>>();
	Map<String, Object> font = new HashMap<String, 	Object>();
	font.put("value", 18);
	lab.put("fontSize", font);
	this.title = lab;
	
	
	Map<String, Map<String, Object>> symb = new HashMap<String,Map<String, Object>>();
	Map<String, Object> str = new HashMap<String, 	Object>();
	str.put("value", "transparent");
	symb.put("stroke", str);
	this.symbols = symb;

	
	
	Map<String, Map<String, Object>> leg = new HashMap<String,Map<String, Object>>();
	Map<String, Object> str2 = new HashMap<String, 	Object>();
	str2.put("value", "#ccc");
	leg.put("stroke", str2);
	
	Map<String, Object> str3 = new HashMap<String, 	Object>();
	str2.put("value", 2.5);
	leg.put("strokeWidth", str3);
	this.legend = leg;
	
	
	
	Map<String, Map<String, Object>> labe = new HashMap<String,Map<String, Object>>();
	Map<String, Object> fonte = new HashMap<String, 	Object>();
	fonte.put("value", 18);
	labe.put("fontSize", fonte);
	this.labels = labe;
	
	
	
	
}
	
	


//used for bubblechart
public PropLegend(String title) {
	super();
	Map<String, Map<String, Object>> lab = new HashMap<String,Map<String, Object>>();
	Map<String, Object> font = new HashMap<String, 	Object>();
	font.put("value", 20);
	lab.put("fontSize", font);
	this.title = lab;
	
	
	Map<String, Map<String, Object>> symb = new HashMap<String,Map<String, Object>>();
	Map<String, Object> str = new HashMap<String, 	Object>();
	str.put("value", "steelblue");
	symb.put("fill", str);
	this.symbols = symb;

	
	
	Map<String, Map<String, Object>> leg = new HashMap<String,Map<String, Object>>();
	Map<String, Object> str2 = new HashMap<String, 	Object>();
	str2.put("value", "#ccc");
	leg.put("stroke", str2);
	
	Map<String, Object> str3 = new HashMap<String, 	Object>();
	str2.put("value", 1);
	leg.put("strokeWidth", str3);
	this.legend = leg;
	
	
	
	Map<String, Map<String, Object>> labe = new HashMap<String,Map<String, Object>>();
	Map<String, Object> fonte = new HashMap<String, 	Object>();
	fonte.put("value", 13);
	labe.put("fontSize", fonte);
	this.labels = labe;
	
	
	
	
}
	
	
}
