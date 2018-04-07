package henry.QuerySimilarity;

import java.util.ArrayList;
import java.util.List;
/**
 * This class provides methods to compare two querys with jaccard
 * @author henry
 *
 */
public class Jaccard implements ISimilarity {
	double alpha = 0.3;
	double beta = 0.5;
	double gammar = 0.2;
	
	/**
	 * This methods calculate the similarity of two queries
	 * @param q1
	 * @param q2
	 */
	public double calculateSimilarity(QueryFromDB currentQuery, QueryFromDB q2) {
		double simFrom = calcRatio(currentQuery.getFrom(), q2.getFrom());
		double simSelect= calcRatio(currentQuery.getSelect(),q2.getSelect());
		double simWhere = calcRatioCondtion(currentQuery.getCondition(),q2.getCondition());
		double similarity = simFrom * alpha  + simSelect * beta + simWhere * gammar;
		return similarity*(1+q2.getVis().getIdvisualization());
	}

	/**
	 * This methods calculates the ration(jacard) two list
	 * @param a
	 * @param b
	 * @return
	 */
	private double calcRatio( List<String>a, List<String> b){
		double numberOfUnion  = union(a, b).size();
		double numberOfIntersect  = intersect(a, b).size();

		return numberOfIntersect/numberOfUnion;
	}
	/**
	 * This methods calculates the ration for two list (condition only)
	 * @param a
	 * @param b
	 * @return
	 */
	private double calcRatioCondtion( List<Condition>a, List<Condition> b){
		double numberOfUnion  = unionCondtion(a, b).size();
		double numberOfIntersect  = intersectCondtion(a, b);

		return numberOfIntersect/numberOfUnion;
	}
	/**
	 * Unite two sets
	 * @param a
	 * @param b
	 * @return
	 */
	
	public static List<String> union(List<String>a, List<String>b){
		List<String> c = new ArrayList<String>();
		for(String content : a){
			c.add(content);
		}
		for(String content : b){
			if(!c.contains(content)){
				c.add(content);
			}
		}
		
		return c;
	}
	

	/**
	 * Intersect two list
	 * @param a
	 * @param b
	 * @return
	 */
	
	public static List<String> intersect(List<String>a, List<String>b) {
		List<String> c = new ArrayList<String>();
		for(String content : a){
			c.add(content);
		}
		c.retainAll(b);
		return c;
	}
	
	/**
	 * Intersect two list (only for condition)
	 * @param a
	 * @param b
	 * @return
	 */
	public static double intersectCondtion(List<Condition> a, List<Condition>b) {
		double c = 0;
		for(Condition contentA : a){
			for(Condition contentB : b){
				c += CompareCondition.compare(contentA, contentB);
			}
		}
		
		return c;
	}
	
	/**
	 * Unite two list (only for condition)
	 * @param a
	 * @param b
	 * @return
	 */
	public static List<Condition> unionCondtion(List<Condition>a, List<Condition>b) {
		List<Condition> c = new ArrayList<Condition>();
		for(Condition content : a){
			c.add(content);
		}
		for(Condition contentA : a){
			for(Condition contentB : b){
				if(!contentA.equals(contentB)){
					c.add(contentB);
				}
			}
		}
		
		return c;
	}
	
	

}
