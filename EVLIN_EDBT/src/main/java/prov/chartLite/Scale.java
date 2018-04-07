package prov.chartLite;

import java.util.List;

public class Scale {
	String[] range; 
	Integer rangeStep;
	String scheme; 
	
	public Scale() {
		
	}
	public Scale(String[] range){
		this.range = range;
	}
	public String[] getRange() {
		return range;
	}
	public void setRange(String[] range) {
		this.range = range;
	}
	public int getRangeStep() {
		return rangeStep;
	}
	public void setRangeStep(Integer rangeStep) {
		this.rangeStep = rangeStep;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
}
