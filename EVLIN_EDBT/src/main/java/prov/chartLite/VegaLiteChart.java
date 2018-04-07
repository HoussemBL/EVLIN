package prov.chartLite;

import java.util.List;
import java.util.Set;



public class VegaLiteChart {
	private String $schema  ="https://vega.github.io/schema/vega-lite/v2.json";
	private int width;
	private int height;
	private String padding;
	private DataChart data = null;
	private Selection selection = null;
	private String mark = null;
	private EncodingChannels encoding = null;
	
	
	public VegaLiteChart(int width, int height ,DataChart data,Selection selection ,String mark,EncodingChannels encoding){
		this.width = width;
		this.height = height;
		this.padding="auto";
		this.mark = mark;
		this.data = data;
		this.encoding = encoding;
		this.selection = selection;
	}
	public VegaLiteChart(int width, int height ,DataChart data,String mark,EncodingChannels encoding){
		this.width = width;
		this.height = height;
		this.padding="auto";
		this.mark = mark;
		this.data = data;
		this.encoding = encoding;
	}
	
	public String get$schema() {
		return $schema;
	}
	public void set$schema(String $schema) {
		this.$schema = $schema;
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
	public DataChart getData() {
		return data;
	}
	public void setData(DataChart data) {
		this.data = data;
	}
	public Selection getSelection() {
		return selection;
	}
	public void setSelection(Selection selection) {
		this.selection = selection;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public EncodingChannels getEncoding() {
		return encoding;
	}
	public void setEncoding(EncodingChannels encoding) {
		this.encoding = encoding;
	}
}
