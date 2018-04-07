package henry.QuerySimilarity;

import java.util.ArrayList;
import java.util.List;

import prov.mybean.VisualizationBean;
/**
 * This class is a form for comparing 
 * two querys with jaccard 
 * @author henry
 *
 */
public class QueryFromDB {
	List<String> select = new ArrayList<String>();
	List<String> from = new ArrayList<String>();
	List<Condition> condition = new ArrayList<Condition>();
	VisualizationBean vis ;
	
	double similarity;
	
	
	public List<String> getSelect() {
		return select;
	}
	public void setSelect(List<String> select) {
		this.select = select;
	}
	
	public List<String> getFrom() {
		return from;
	}
	public void setFrom(List<String> from) {
		this.from = from;
	}
	public List<Condition> getCondition() {
		return condition;
	}
	public void setCondition(List<Condition> condition) {
		this.condition = condition;
	}
	public double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	public VisualizationBean getVis() {
		return vis;
	}
	public void setVis(VisualizationBean vis) {
		this.vis = vis;
	} 
	
}
