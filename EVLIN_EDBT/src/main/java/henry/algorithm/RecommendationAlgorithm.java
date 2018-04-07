package henry.algorithm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import org.hibernate.Query;
import org.hibernate.Session;

import henry.QuerySimilarity.ISimilarity;
import henry.QuerySimilarity.Jaccard;
import henry.QuerySimilarity.QueryFromDB;
import henry.QuerySimilarity.VisualizationFromDB;
import henry.database.connection.DBConnector;
import henry.database.connection.vizDBUtil;
import prov.chart.VegaChart;
import prov.mybean.QueryForm;
import prov.mybean.VisualizationBean;

public class RecommendationAlgorithm {
	/**
	 * This is the main recommendation algorithmen 
	 * @param connector
	 * @param currentQuerry
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	/*public VisualizationFromDB algorithmus(DBConnector connector,QueryFromDB currentQuerry)
			throws IllegalArgumentException, IllegalAccessException {

		// Get historie from DB
		
		List<QueryFromDB> querys = new ArrayList<QueryFromDB>();
		
		for (int i = 0; i < connector.counter.vis; i++) {
			querys.add(connector.makeQuery(i));
		}

		for (QueryFromDB query : querys) {
			ISimilarity jaccard = new Jaccard();
			query.setSimilarity(jaccard.calculateSimilarity(currentQuerry, query));
		}

		// Sort by most similar query to the current query
		RecommendationAlgorithm.sortBySimilarity(querys);

		if (currentQuerry.getSelect().size() == 2) {
			RecommendationAlgorithm.sortByDimensionAscending(querys);
		} else {
			RecommendationAlgorithm.sortByDimensionDescending(querys);
		}

		Boolean completeVis = false;
		int i = 0;
		while (!completeVis && i < querys.size()) {
			VisualizationFromDB vis = querys.get(i).getVis();
			transformVisualization(currentQuerry, vis);
			completeVis = completeCheck(currentQuerry.getVis());
			i++;
		}
		if(!completeVis) {
			updateStrategy(currentQuerry);
		}
		
		return currentQuerry.getVis();
	}

	/**
	 * In case visualization is not complete, the vis has to be complete by random values
	 * @param currentQuerry
	 */
	


	
	/**
	 * Check if all information from the visualization are define oder not
	 * If one of the information are undefine method will return false
	 * If all information are define methode true
	 * @param recommendvis
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Boolean completeCheck(Object recommendvis) throws IllegalArgumentException, IllegalAccessException {
		for (Field field : recommendvis.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.get(recommendvis) == null) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Sorting history by similarity
	 * @param listOfAllQuerysInHistory
	 */

	public static void sortBySimilarity(List<QueryFromDB> listOfAllQuerysInHistory) {
		Collections.sort(listOfAllQuerysInHistory, new Comparator<QueryFromDB>() {
			public int compare(QueryFromDB q2, QueryFromDB q1) {
				return Double.compare(q1.getSimilarity(), q2.getSimilarity());
			}
		});
	}

	/**
	 * sort history descending  by there dimension
	 * @param listOfAllQuerysInHistory
	 */
	
	public static void sortByDimensionDescending(List<QueryFromDB> listOfAllQuerysInHistory) {
		Collections.sort(listOfAllQuerysInHistory, new Comparator<QueryFromDB>() {
			public int compare(QueryFromDB q2, QueryFromDB q1) {
				return Double.compare(q1.getSelect().size(), q2.getSelect().size());
			}
		});
	}
	
	/**
	 * sort history ascending  by there dimension
	 * @param listOfAllQuerysInHistory
	 */

	public static void sortByDimensionAscending(List<QueryFromDB> listOfAllQuerysInHistory) {
		Collections.sort(listOfAllQuerysInHistory, new Comparator<QueryFromDB>() {
			public int compare(QueryFromDB q2, QueryFromDB q1) {
				return Double.compare(q2.getSelect().size(), q1.getSelect().size());
			}
		});
	}
	
	
	
	
	
	
	
	/********* it is similar to algorithmus , it just return queries in order while alghoritmus constructs the new visualization********/
	public List<QueryFromDB> Rank(DBConnector connector,QueryFromDB currentQuerry)
			throws IllegalArgumentException, IllegalAccessException {

		  Session session = vizDBUtil.getSessionFactory().openSession();
	session.beginTransaction();
		
		List<QueryFromDB> querys = new ArrayList<QueryFromDB>();
          int limit= connector.getParentqueryID();
		/*for (int i = 0; i < connector.counter.vis; i++) {
			querys.add(connector.makeQuery(i));
		}*/
        		  
  		Query query1 = session.createQuery("select idquery from VizTable where idvisualization<= '" + limit + "'");
  		List<Object> rows = query1.list();
  		

  		Iterator<Object> ite = rows.iterator();
  		while (ite.hasNext()) {
  		

  		Object test = ite.next();
  		int q_id = Integer.parseInt(test.toString());
  		querys.add(connector.getQueryInfo(q_id));
  		
  		}


		for (QueryFromDB query : querys) {
			ISimilarity jaccard = new Jaccard();
			query.setSimilarity(jaccard.calculateSimilarity(currentQuerry, query));
			
		}

		// Sort by most similar query to the current query
		
		RecommendationAlgorithm.sortBySimilarity(querys);

		if (currentQuerry.getSelect().size() == 2) {
			RecommendationAlgorithm.sortByDimensionAscending(querys);
		} else {
			RecommendationAlgorithm.sortByDimensionDescending(querys);
		}

		
		session.close();
		return querys;
	}
	
	
	
	
	public VegaChart Recommendation_Viz_ZoomIn(QueryFromDB currentQuerry,List<QueryFromDB> querys)
			throws IllegalArgumentException, IllegalAccessException {

	
		VegaChart recommendation= new VegaChart();
		if (currentQuerry.getSelect().size() == 2) {
			RecommendationAlgorithm.sortByDimensionAscending(querys);
		} else {
			RecommendationAlgorithm.sortByDimensionDescending(querys);
		}

		Boolean completeVis = false;
		int i = 0;
		while (!completeVis && i < querys.size()) {
			VisualizationBean vis = querys.get(i).getVis();
			RecommendationVizGeneralProperties(currentQuerry, recommendation,querys.get(i),vis);
			
		//	completeVis = completeCheck(currentQuerry.getVis());
			i++;
		}
		if(!completeVis) {
			//updateStrategy(currentQuerry);
			//I need to complete it
		}
		
		return /*currentQuerry.getVis()*/null;
	}


	
	//general properties of visualizations like width and length
	private void RecommendationVizGeneralProperties(QueryFromDB currentQuerry, VegaChart viz_rec, QueryFromDB queryFromDB,
			VisualizationBean vis) {
	
		// TODO Auto-generated method stub
		if(viz_rec.getHeight() ==0 ) { 
			
			viz_rec.setHeight(vis.getLength()); }
			if(viz_rec.getWidth()==0) { 
			
			viz_rec.setHeight(vis.getWidth()); }
	}
	
	
	
}
