package prov.CRUD;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.Tuple;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.FloatType;
import org.hibernate.type.StringType;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import henry.QuerySimilarity.ISimilarity;
import henry.QuerySimilarity.Jaccard;
import henry.QuerySimilarity.QueryFromDB;
import henry.algorithm.RecommendationAlgorithm;
import henry.database.connection.DBConnector;
import henry.database.connection.vizDBUtil;
import newTypes.Tuple2;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import prov.chart.VegaChart;
import prov.mybean.*;
import prov.util.HibernateUtil;
import prov.vis.*;


// give more flexibility ion computation
public class VisualizationConstruction {
	public QueryForm queryToRender;
	public DBForm DB;
	public List<String> tempoAtts1=new ArrayList<String>(Arrays.asList("year","year_t","dayofweek","dayofmonth","deptime","arrtime","month") );
	Tuple2<String, List<String>> tempo1= new Tuple2<String, List<String>>("flights", tempoAtts1);
	public List<String> tempoAtts2=new ArrayList<String>(Arrays.asList("season") );
	Tuple2<String, List<String>> tempo2= new Tuple2<String, List<String>>("match", tempoAtts1);
	public List<Tuple2<String, List<String>> > temporal_rules=Arrays.asList( tempo1,tempo2);	
	
	private static final Map<String, List<Vega>> rule1 = new HashMap<String, List<Vega>>();
	static {
        VegaTicks tick= new VegaTicks();
		VegaPoints pt= new VegaPoints();
		List<Vega> vis= new ArrayList<Vega>(Arrays.asList(tick,pt));
		rule1.put("ordinal-nominal",vis);
		rule1.put("nominal-ordinal",vis );
		rule1.put("ordinal-ordinal",vis );
		rule1.put("nominal-nominal",vis );
		
		
	}
	
	
	//private static final Map<String, List<String>> rule2 = new HashMap<String, List<String>>();
	private static final Map<String, List<Vega>> rule2 = new HashMap<String, List<Vega>>();
	static {
		 VegaBars bars= new VegaBars();
			VegaPoints pt= new VegaPoints();
			VegaBubbles bub= new VegaBubbles();
			List<Vega> vis= new ArrayList<Vega>(Arrays.asList(bars,pt,bub));
			rule2.put("quantitative-nominal",vis );
			rule2.put("nominal-quantitative",vis );
	}
	
	
	//private static final Map<String, List<String>> rule3 = new HashMap<String, List<String>>();
	private static final Map<String, List<Vega>> rule3 = new HashMap<String, List<Vega>>();
	static {
		VegaLines line= new VegaLines();
		 VegaBars bars= new VegaBars();
			VegaPoints pt= new VegaPoints();
			VegaBubbles bub= new VegaBubbles();
			List<Vega> vis= new ArrayList<Vega>(Arrays.asList(line,bars,pt,bub));
		
		rule3.put("quantitative-temporal",vis);
		rule3.put("quantitative-ordinal",vis );
		rule3.put("temporal-quantitative",vis);
		rule3.put("ordinal-quantitative",vis );
		
	}
	
	private static final Map<String, List<Vega>> rule4 = new HashMap<String, List<Vega>>();

	static {
		VegaPoints pt= new VegaPoints();
		VegaBubbles bub= new VegaBubbles();
		List<Vega> vis= new ArrayList<Vega>(Arrays.asList(pt,bub));
		rule4.put("quantitative-quantitative",vis);

		
	}
	
	
	
	private final static List<Map<String, List<Vega>>> VoyagerRules = new ArrayList(
			Arrays.asList(rule1, rule2, rule3, rule4));
	
	
	
	/****************************3D rules ********************************/
	private static final Map<List<String>, List<Vega>> rule1_3D = new HashMap<List<String>, List<Vega>>();
	static {
       VegaBars bars= new VegaBars();
		VegaPoints pt= new VegaPoints();
		VegaBubbles bb= new VegaBubbles();
		List<Vega> vis1= new ArrayList<Vega>(Arrays.asList(bars,pt,bb));
		List<String> types1= new ArrayList<String>(Arrays.asList("nominal","nominal","quantitative"));
		List<String> types12= new ArrayList<String>(Arrays.asList("nominal","quantitative","quantitative"));
		rule1_3D.put(types1,vis1 );
		rule1_3D.put(types12,vis1 );	
	}
	
	
	private static final Map<List<String>, List<Vega>> rule2_3D = new HashMap<List<String>, List<Vega>>();
	static {
		 VegaBars bars2= new VegaBars();
			VegaPoints pt2= new VegaPoints();
			VegaBubbles bub2= new VegaBubbles();
			VegaLines line2= new VegaLines();
			List<Vega> vis2= new ArrayList<Vega>(Arrays.asList(line2,bars2,pt2,bub2));
			List<String> types2= new ArrayList<String>(Arrays.asList("temporal","nominal","quantitative"));
			rule2_3D.put(types2,vis2 );
	}
	
	

	
	private static final  Map<List<String>, List<Vega>> rule3_3D = new HashMap<List<String>, List<Vega>>();
	static {
		
	
		VegaBubbles bub3= new VegaBubbles();
		VegaPoints pt3= new VegaPoints();
		VegaBars bars3= new VegaBars();
		List<Vega> vis3= new ArrayList<Vega>(Arrays.asList(bub3,bars3,pt3));
		List<String> types31= new ArrayList<String>(Arrays.asList("temporal","temporal","quantitative"));
		List<String> types32= new ArrayList<String>(Arrays.asList("temporal","quantitative","quantitative"));
		List<String> types33= new ArrayList<String>(Arrays.asList("quantitative","quantitative","quantitative"));
		rule3_3D.put(types31,vis3 );
		rule3_3D.put(types32,vis3 );
		rule3_3D.put(types33,vis3 );	
	}
	

	
	private static final Map<List<String>, List<Vega>> rule4_3D = new HashMap<List<String>, List<Vega>>();
	static {
      
		VegaPoints pt4= new VegaPoints();
		VegaBars bars4= new VegaBars();
		List<Vega> vis1= new ArrayList<Vega>(Arrays.asList(pt4,bars4));
	
		List<String> types4= new ArrayList<String>(Arrays.asList("quantitative","nominal","quantitative"));
		rule4_3D.put(types4,vis1 );
		
	}
	
	private final static List<Map<List<String>, List<Vega>>> MyRules = new ArrayList(
			Arrays.asList(rule1_3D, rule2_3D, rule3_3D,rule4_3D));
	
	
	

	public VisualizationConstruction() {
		super();

	}

	public VisualizationConstruction(QueryForm queryToRender,DBForm DB) {
		super();
		this.queryToRender = queryToRender;
		this.DB=DB;
	}

	public QueryForm getQueryToRender() {
		return queryToRender;
	}

	public void setQueryToRender(QueryForm queryToRender) {
		this.queryToRender = queryToRender;
	}

	public  List<Vega> decide() {
		List<Vega> result= new ArrayList<Vega>();
		String axisX= GetX();
		String axisY= GetY();
		
		String datatypes=axisX+"-"+axisY;
		for ( Map<String, List<Vega>> kk : VoyagerRules)
		{
			if (kk.keySet().contains(datatypes)) { 
				List<List<Vega>> PossibleVis= new ArrayList<List<Vega>>(kk.values()); 
				

				for (Vega veg :PossibleVis.get(0))
				{ 
					if (veg.getClass().toString().contains("Points")) {
					VegaPoints	tempvega= new VegaPoints(queryToRender);
						result.add(tempvega);
					}
					else if (veg.getClass().toString().contains("Ticks")) {
						VegaTicks	tempvega= new VegaTicks(queryToRender);
							result.add(tempvega);
						}
					else if (veg.getClass().toString().contains("Bubbles")) {
						VegaBubbles	tempvega= new VegaBubbles(queryToRender);
							result.add(tempvega);
						}
					else if (veg.getClass().toString().contains("Lines")) {
						VegaLines tempvega= new VegaLines(queryToRender);
							result.add(tempvega);
						}
					else  {
						VegaBars tempvega= new VegaBars(queryToRender);
							result.add(tempvega);
						}
				}
			
			}
			
		}
		
		return result;
	}

	
	
	
	
	
	
	
	
	public /*Vega*/ List<Vega> decideStatic(String datatypes) {
		List<Vega> result= new ArrayList<Vega>();
		
		
		
		for ( Map<String, List<Vega>> kk : VoyagerRules)
		{
			if (kk.keySet().contains(datatypes)) { 
				List<List<Vega>> PossibleVis= new ArrayList<List<Vega>>(kk.values()); 
				

				for (Vega veg :PossibleVis.get(0))
				{ 
					if (veg.getClass().toString().contains("Points")) {
					VegaPoints	tempvega= new VegaPoints(queryToRender);
						result.add(tempvega);
					}
					else if (veg.getClass().toString().contains("Ticks")) {
						VegaTicks	tempvega= new VegaTicks(queryToRender);
							result.add(tempvega);
						}
					else if (veg.getClass().toString().contains("Bubbles")) {
						VegaBubbles	tempvega= new VegaBubbles(queryToRender);
							result.add(tempvega);
						}
					else if (veg.getClass().toString().contains("Lines")) {
						VegaLines tempvega= new VegaLines(queryToRender);
							result.add(tempvega);
						}
					else  {
						VegaBars tempvega= new VegaBars(queryToRender);
							result.add(tempvega);
						}
				}
				//return result;
			}
			
		}
		
		return result;
	}
	
	
	
	private String GetY() {
		return "quantitative";
	}
	private String GetY2() {
		String colName = queryToRender.getSelectedAtt().get(2);
	return GetTYPE(colName,"");}
	
	private String GetX() {
		String colName = queryToRender.getGroupbyAtt().get(0);
	//return GetTYPE(colName,"bname");
	return GetTYPE(colName,"");
	}
	
	
	private String GetX2() {
		String colName = queryToRender.getGroupbyAtt().get(1);
	return GetTYPE(colName,"");}
	
	
	private String GetTYPE(String colName,String fact_table) {
		QueryConstructor queryBuilder;
		//String tableName="flights";	
		String tableName=fact_table;	
		List<String> tempoAttsCurrent =new ArrayList<String>();
		
		if(DB!=null && DB.getName().contains("soccer"))
				{tempoAttsCurrent.addAll(tempo2._1);}
		else {tempoAttsCurrent.addAll(tempo1._1);}
	
		/*if (tempoAttsCurrent==null){
			tempoAttsCurrent.addAll(tempoAtts1);
		}*/
		if (DB!=null && this.DB.getName().equals("soccerDB")) {
			queryBuilder = new QueryConstructor_Soccer(queryToRender, "");
		} else {
			queryBuilder= new QueryConstructor(queryToRender, "");
		}
		
		tableName= queryBuilder.findTable(colName);
		
		
		String[] inter = colName.split(Pattern.quote("."));
		if (inter.length > 1) colName = inter[1]; 
	
		// if (colName.equals("year") ||colName.equals("year_t") ||colName.equals("dayofweek") || colName.equals("dayofmonth")  || colName.equals("deptime") ||  colName.equals("arrtime")  )
		if(tempoAttsCurrent.contains(colName))
		 {return "temporal";}
		 
		 else {
				Session session = HibernateUtil.getSessionFactory().openSession();
				Transaction tx = session.beginTransaction(); 
				String query="select * from public.\"TypeColumn\"('"+ tableName+"','"+colName+ "')" ;
				SQLQuery queryDB = session.createSQLQuery(query);
			String type =queryDB.list().get(0).toString(); 
			 session.close();
			 
			 
			 if(type.equals("integer")) return "quantitative";
			 else return "nominal";
		 }		
	}
	
	
	
	
	
	
	public  List<Vega> decide_3D() {
		List<Vega> result= new ArrayList<Vega>();
		String axisX= GetX2();
		String axisY= GetY();
		String axisX2= GetX();
		Boolean similar=false;
		
	List<String> DataTypes= new ArrayList<String>(Arrays.asList(axisX,axisX2,axisY));
	
	lp:	for ( Map<List<String>, List<Vega>> rls : MyRules)
		{
		//int i=0;
		for (Entry<List<String>, List<Vega>> ss : rls.entrySet() )
		//Entry<List<String>, List<Vega>> ss = rls.entrySet().iterator().next();
		{
			
				List<String> typeatts=ss.getKey();
				similar=isDuplicaV2(typeatts, DataTypes);
				
			if(similar ) { 
				List<Vega> PossibleVis= new ArrayList<Vega>(ss.getValue()); 
				

				for (Vega veg :PossibleVis)
				{ 
					if (veg.getClass().toString().contains("Points")) {
					VegaPoints	tempvega= new VegaPoints(queryToRender);
						result.add(tempvega);
					}
					else if (veg.getClass().toString().contains("Ticks")) {
						VegaTicks	tempvega= new VegaTicks(queryToRender);
							result.add(tempvega);
						}
					else if (veg.getClass().toString().contains("Bubbles")) {
						VegaBubbles	tempvega= new VegaBubbles(queryToRender);
							result.add(tempvega);
						}
					else if (veg.getClass().toString().contains("Lines")) {
						VegaLines tempvega= new VegaLines(queryToRender);
							result.add(tempvega);
						}
					else  {
						VegaBars tempvega= new VegaBars(queryToRender);
							result.add(tempvega);
						}
				}
			break lp;
			}
			
		}}
		
		return result;
	}
	
	
	
	
	//simialrity in elements and order
	public boolean isDuplicaV2(List<String>listOne , List<String> listTwo){
		 if (listTwo.size()==listOne.size()) {
			 int i=0;
	for (String typeElt: listOne)
	{
		if (typeElt.equals(listTwo.get(i)))
		{i++;}
		else { return false;}
	}
        
      
       	  return true;
         }
		 
		
	
		 else return false;
	}
	
	
	
	
	public boolean isDuplica(List<String>listOne , List<String> listTwo){
		 if (listTwo.size()==listOne.size()) {
		  Collection<String> similar = new HashSet<String>( listOne );
          Collection<String> different = new HashSet<String>();
          different.addAll( listOne );


          similar.retainAll( listTwo );
          different.removeAll( similar );
        if (different.size()>0  ) return false;
          else {
        	/*if(similar.size()==listOne.size())  
        	{ return true; }
        	else return false;	*/
        	  return true;
          }
		 
		
	}
		 else return false;
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

		//update similarity using recentness
	/*	for (QueryFromDB query : querys) {
			
			query.setSimilarity(query.getSimilarity()*(1+query.getVis().getIdvisualization()));
			
		}*/
		
		
		sortBySimilarity(querys);

		if (currentQuerry.getSelect().size() == 2) {
			sortByDimensionAscending(querys);
		} else {
			sortByDimensionDescending(querys);
		}

		
		session.close();
		return querys;
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
	

	
	
	
}
