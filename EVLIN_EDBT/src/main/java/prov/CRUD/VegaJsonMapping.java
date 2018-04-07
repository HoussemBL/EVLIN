package prov.CRUD;

import java.awt.font.NumericShaper.Range;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.*;

import org.hibernate.Session;

import com.google.gson.FieldNamingStrategy;

import com.google.gson.*;

import prov.act.QueryDecryptionAction;
import prov.mybean.DisplayForm;

import prov.mybean.*;
import prov.mybean.QueryForm;
import prov.mybean.RangeForm;
import prov.util.HibernateUtil;

public class VegaJsonMapping {

	public List<DisplayForm> construct(QueryForm query2) {
		DecimalFormat df2 = new DecimalFormat(".##");
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Object> iniQuery = (List<Object>) session.createSQLQuery(query2.display()).list();

		HashMap<String, String> datavis = ExportVis(iniQuery);
		List<DisplayForm> st = new ArrayList<DisplayForm>();
		for (Entry<String, String> entry : datavis.entrySet()) {
			DisplayForm a = new DisplayForm(entry.getKey(), Double.parseDouble(df2.format(Double.parseDouble(entry.getValue()) ))       );
			st.add(a);
		}
		
		
	
	/*	FieldNamingStrategy customPolicy = new FieldNamingStrategy() {
			public String translateName(Field f) {
				if (f.getName() == "state")
					return f.getName().toLowerCase();
				else
					return f.getName().toLowerCase();
			}
		};

	
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingStrategy(customPolicy);
		Gson gson = gsonBuilder.create();
		String dataJson = gson.toJson(st);*/
	


	
		session.close();
	
		return /*dataJson*/st;

	}


	
	//similar to construct dynamic except changing the value of field groupby to "other"
	public String constructDynamicV2(QueryForm query, String cst) {
		String dataJson;
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Object> iniQuery = (List<Object>) session.createSQLQuery(query.display()).list();

		Iterator itr = iniQuery.iterator();
		if (iniQuery.size()<=0) {return "empty";}
		else{
		Object[] test = (Object[]) itr.next();
		if (test.length == 2) {

			HashMap<String, String> datavis = ExportVis(iniQuery);
			List<DisplayForm> st = new ArrayList<DisplayForm>();
			for (Entry<String, String> entry : datavis.entrySet()) {
				DisplayForm a = new DisplayForm(entry.getKey(), Double.parseDouble(entry.getValue()));
				st.add(a);
			}

			dataJson = ConvertToJson(st, query);
		
		} else {
			List<DisplayForm> st =new ArrayList<DisplayForm>();
			
			if (String.join("",query.getCondAtt().keySet()).contains("range") )
			{   //special case for ranges
				String elt = "";
				for (String entry : query.getCondAtt().keySet()) {
					if (entry.contains("range,")) {
						elt = entry.split(",")[1];
						break;
					}

				}
				if (elt.length() > 3) {
					
					if (query.getSelectedAtt().contains(elt)) {
						List<RangeForm> cslis = DetermineRange(query.getCondAtt());
						
						st = ExportVisV5(iniQuery,cslis);	
						
					}
					else { st = ExportVisV3(iniQuery,cst);}
					}
				
				else { st = ExportVisV3(iniQuery,cst);}
				
			
				 }
			
			else { st = ExportVisV3(iniQuery,cst);}
			dataJson = ConvertToJsonV6(st, query);

	
	
		}

	

		session.close();

		
		return dataJson;}

	}



	





/****************similar to construct Dynamic expect the input query text is result of simplify execution********************/	
	public List<DisplayForm>  constructDynamicV4(QueryForm query) {
		String dataJson = "";
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Object> iniQuery = (List<Object>) session.createSQLQuery(query.display()).list();
	
		List<DisplayForm> st=new ArrayList<DisplayForm>();
		if (iniQuery.size() <= 0) {
			return  st;
		} else {
			Iterator itr = iniQuery.iterator();
			Object[] test = (Object[]) itr.next();
			if (test.length == 2) {
				 st = ExportVisV2(iniQuery,"others");
			
		
				
			} else {
			st = ExportVisV2(iniQuery,"rrrrr");
		
			
			}

			session.close();

		}
		return st;
	}

	
	
	/****************similar to construct Dynamic expect the input query text is result of simplify execution********************/	
	public List<DisplayForm> constructDynamicV5(final QueryForm query2) {
		String dataJson = "";
		List<DisplayForm> st=new ArrayList<DisplayForm>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Object> iniQuery = (List<Object>) session.createSQLQuery(query2.display()).list();
		
		if (iniQuery.size() <= 0) {
			return  st;
		} else {
			Iterator itr = iniQuery.iterator();
			Object[] test = (Object[]) itr.next();
			if (test.length == 2) {
			 st = ExportVisV6(iniQuery, "others");
			//	dataJson = ConvertToJsonV5(st);
			

			} else {
				st = new ArrayList<DisplayForm>();

				if (String.join("", query2.getCondAtt().keySet()).contains("range")) { // special
																						// case
																						// for
																						// ranges
					String elt = "";
					for (String entry : query2.getCondAtt().keySet()) {
						if (entry.contains("range,")) {
							elt = entry.split(",")[1];
						
							break;
						}

					}
					if (elt.length() > 3) {
						
						if (query2.getSelectedAtt().contains(elt)) {
							
							List<RangeForm> cslis = DetermineRange(query2.getCondAtt());
							st = ExportVisV7(iniQuery, cslis);
						}
						else
							st = ExportVisV6(iniQuery, "rrrrr");	
					} else
						st = ExportVisV6(iniQuery, "rrrrr");
				} else {
					st = ExportVisV6(iniQuery, "rrrrr");
				}
				
				
				//dataJson = ConvertToJsonV5(st);
			
			}

			session.close();

		}
		return st;
	}
	
	
	
	
	



	
	
	
	/***********it is used for dynamic visualiation of recommendation 1 stacked bar***********/
	private String ConvertToJsonV6(List<DisplayForm> st, final QueryForm query) {
	
		
	/*	for (Map.Entry<String, String> entry : query.getCondAtt().entrySet()) {
			
			
		}*/
		FieldNamingStrategy customPolicy = new FieldNamingStrategy() {
			
			public String translateName(Field f) {
				String res = "";
	
				if (f.getName() == "groupby") {
			
					return "state";
				}
	
			
	
				else {
	
					return "" + f.getName();
	
				}
			}
		};
	
		// json generation
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingStrategy(customPolicy);
		Gson gson = gsonBuilder.create();
		String dataJson = gson.toJson(st);
		return dataJson;
	
	}
	
	
	
	
	



	private String ConvertToJsonV2(List<DisplayForm> st, final QueryForm query) {

		FieldNamingStrategy customPolicy = new FieldNamingStrategy() {
			public String translateName(Field f) {
				String res = "";

				if (f.getName() == "groupby") {
					List<String> grp=new ArrayList<String>();
					
				grp.addAll(query.getOrderbyAtt().keySet());
					
					
				
							for (String field : grp) {
								if (!field.contains("count(") && !field.matches("\\d+")) {
									if (field.split(Pattern.quote(".")).length > 1) {
										res = field.split(Pattern.quote("."))[1];
										break;
									} else
										res = field;

								}
							}		
							
					return res;
					
				}

				else if (f.getName() == "val") {
					res="" + f.getName();
					for (String field : query.getSelectedAtt()) {
						if (!field.contains("count(") &&!field.contains("corr(") &&!query.getOrderbyAtt().keySet().contains(field)) {
							if (field.split(Pattern.quote(".")).length > 1) {
								if(field.split(Pattern.quote("."))[1].contains("dest") ||  field.split(Pattern.quote("."))[1].contains("origin"))
								{res="iata";
								break;}
								else{
								res = field.split(Pattern.quote("."))[1];
								break;}
							} else
							{	
								
								if(field.contains("dest") ||  field.contains("origin"))
								{res="iata";
								break;}	
								
							else{	res = field;}}

						}
					}
					return res;
					

				}

				else {

					return "" + f.getName();

				}
			}
		};

		// json generation
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingStrategy(customPolicy);
		Gson gson = gsonBuilder.create();
		String dataJson = gson.toJson(st);
		return dataJson;

	}

	
	private String ConvertToJsonV3(List<DisplayForm> st, final QueryForm query) {
		
	
		
		
		
		FieldNamingStrategy customPolicy = new FieldNamingStrategy() {
			
			List<String> duplicates=computeDuplicate(query);
			
			public String translateName(Field f) {
				String res = "";

				if (f.getName() == "groupby") {
					
					List<String> grp=new ArrayList<String>();
					
				grp.addAll(query.getOrderbyAtt().keySet());
					
				
				
							for (String field : grp) {
								if (!field.contains("count(") && !field.matches("\\d+")) {
									if (field.split(Pattern.quote(".")).length > 1) {
										res = field.split(Pattern.quote("."))[1];
										break;
									} else
										res = field;

								}
							}		
							
					return res;		
					
					
	
				}

				else if (f.getName() == "val") {
					for (String field : query.getSelectedAtt()) {
						if (!field.contains("count(") && !query.getOrderbyAtt().keySet().contains(field)) {
							if (field.split(Pattern.quote(".")).length > 1 && !(duplicates.contains(field.split(Pattern.quote("."))[1]))) {
								if(field.split(Pattern.quote("."))[1].contains("dest") ||  field.split(Pattern.quote("."))[1].contains("origin"))
								{res="iata";
								break;}
								else {
								res = field.split(Pattern.quote("."))[1];
								
								break;}
							}
							else if(field.split(Pattern.quote(".")).length > 1 && duplicates.contains(field.split(Pattern.quote("."))[1])) 
							{   
								if(field.split(Pattern.quote("."))[1].contains("dest") ||  field.split(Pattern.quote("."))[1].contains("origin"))
								
								{res="iata"+query.getSelectedAtt().indexOf(field);   
								break;}
								
								else{	res=field.split(Pattern.quote("."))[1]+query.getSelectedAtt().indexOf(field);   
							
							break;}
							}
							
							}
							
							else
								{
								if(field.contains("dest") ||  field.contains("origin"))
								{res="iata";
								break;}
								else {
									res = field;
								}

						}
					}
					return res;

				}

				else {

					return "" + f.getName();

				}
				}
			};

			
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setFieldNamingStrategy(customPolicy);
			Gson gson = gsonBuilder.create();
			String dataJson = gson.toJson(st);
			return dataJson;
	
	
	
	
		}
	
	
	private String ConvertToJson(List<DisplayForm> st, final QueryForm query) {
		FieldNamingStrategy customPolicy = new FieldNamingStrategy() {
			public String translateName(Field f) {
				if (f.getName() == "state") {
					String res = "";
					for (String field : query.getSelectedAtt()) {
						if (!field.contains("count(")) {
							if (field.split(Pattern.quote(".")).length > 1) {
								res = field.split(Pattern.quote("."))[1];
							} else
								res = field;
						}
					}
					return res;
				} else
					return "number";
			}
		};

		/****************
		 * transformation of result in JSON format
		 ***************/
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingStrategy(customPolicy);
		Gson gson = gsonBuilder.create();
		String dataJson = gson.toJson(st);

		return dataJson;
	}



	private HashMap<String, String> ExportVis(List<Object> iniQuery) {
		// TODO Auto-generated method stub

		Iterator itr = iniQuery.iterator();
		HashMap<String, String> datavis = new HashMap<String, String>();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();

			String count = String.valueOf(obj[0]);
			String state = String.valueOf(obj[1]);
			datavis.put(state, count);
			// System.out.println(state + "," + count);
		}

		return datavis;
	}
	
	// is used for json object of 3 elements
	private List<DisplayForm> ExportVisV2(List<Object> iniQuery,String further) {
		DecimalFormat df2 = new DecimalFormat(".##");

		List<DisplayForm> datavis = new ArrayList<DisplayForm>();
		Iterator itr = iniQuery.iterator();
		if(further.contains("others"))
		{while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();

			String count = String.valueOf(obj[0]);
			String elt = String.valueOf(obj[1]);
			
			String state = "other";
			DisplayForm aa = new DisplayForm(state, elt, Double.parseDouble(df2.format(Double.parseDouble(count) )));

			datavis.add(aa);
	
		}}
		else{
		
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();

			String count = String.valueOf(obj[0]);
			String state = String.valueOf(obj[2]);
			String elt = String.valueOf(obj[1]);
			DisplayForm aa = new DisplayForm(state, elt, Double.parseDouble(df2.format(Double.parseDouble(count) )));

			datavis.add(aa);
			// System.out.println(state + "," + count);
		}}

		return datavis;

	}
	
	// is used for json object of 3 elements
	//similar to exportVisV2 excepting changing value of field groupby to static value="other"
		private List<DisplayForm> ExportVisV3(List<Object> iniQuery, String cst) {
			DecimalFormat df2 = new DecimalFormat(".##");
			Iterator itr = iniQuery.iterator();
			//System.out.println( " @@@@");
			//System.out.println( " subquery1 "+subquery1.display());
			
			
			List<DisplayForm> datavis = new ArrayList<DisplayForm>();
			if(!cst.contains("nothing"))
			{while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();

				String count = String.valueOf(obj[0]);
				String state = String.valueOf(obj[2]);
				
				String elt = cst;
				DisplayForm aa = new DisplayForm(state, elt, Double.parseDouble(df2.format(Double.parseDouble(count) )));

				datavis.add(aa);
		
			}}
			else{
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();

					String count = String.valueOf(obj[0]);
					String state = String.valueOf(obj[2]);
					
					String elt =  String.valueOf(obj[1]);
					DisplayForm aa = new DisplayForm(state, elt, Double.parseDouble(df2.format(Double.parseDouble(count) )));

					datavis.add(aa);
			
				}	
				
			}

			return datavis;

		}
		
		
	
		
		
		/***********used especially for ranges**********/
		private List<DisplayForm> ExportVisV5(List<Object> iniQuery, List<RangeForm> rangelist) {
			DecimalFormat df2 = new DecimalFormat(".##");
			Iterator itr = iniQuery.iterator();
			//System.out.println( " @@@@");
			//System.out.println( " subquery1 "+subquery1.display());
			
			
			List<DisplayForm> datavis = new ArrayList<DisplayForm>();
			
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();

					String count = String.valueOf(obj[0]);
					String state = String.valueOf(obj[2]);
					
					String elt =  String.valueOf(obj[1]);
					Integer elt1=Integer.parseInt(elt);
				
					for (RangeForm entry: rangelist)
					{ Integer min= entry.getMin();
					Integer max= entry.getMax();
					
					
					
						
						DisplayForm aa = new DisplayForm(state, "["+min+","+max+"]", Double.parseDouble(df2.format(Double.parseDouble(count) )));
						datavis.add(aa);
						
					

					} 
				
			
				}	
				
			

			return datavis;

			
		}
	
	
	/*****Similar to exportVis5 but with reverse values for state and element*******/
		private List<DisplayForm> ExportVisV7(List<Object> iniQuery, List<RangeForm> rangelist) {
			DecimalFormat df2 = new DecimalFormat(".##");
			Iterator itr = iniQuery.iterator();
			//System.out.println( " @@@@");
			//System.out.println( " subquery1 "+subquery1.display());
			
			
			List<DisplayForm> datavis = new ArrayList<DisplayForm>();
			//System.out.println( " @@@@");
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();

					String count = String.valueOf(obj[0]);
					String state = String.valueOf(obj[2]);
					
					String elt =  String.valueOf(obj[1]);
					
					Integer elt1=Integer.parseInt(elt);
					for (RangeForm entry: rangelist)
					{ Integer min= entry.getMin();
					Integer max= entry.getMax();
					
	
						
						DisplayForm aa = new DisplayForm( "["+min+","+max+"]",state, Double.parseDouble(df2.format(Double.parseDouble(count) )));
						datavis.add(aa);
						
						
			

					}
					}
			
				
				
			

			return datavis;

			
		}
		
		
		
		
	
		
	//similar to exportvis2 expect switching values of state and elt	
		private List<DisplayForm> ExportVisV6(List<Object> iniQuery,String further) {
			DecimalFormat df2 = new DecimalFormat(".##");

			List<DisplayForm> datavis = new ArrayList<DisplayForm>();
			Iterator itr = iniQuery.iterator();
			if(further.contains("others"))
			{while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();

				String count = String.valueOf(obj[0]);
				String elt = String.valueOf(obj[2]);
				
				String state = "other";
				DisplayForm aa = new DisplayForm(state, elt, Double.parseDouble(df2.format(Double.parseDouble(count) )));

				datavis.add(aa);
		
			}}
			else{
			
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();

				String count = String.valueOf(obj[0]);
				String state = String.valueOf(obj[1]);
				String elt = String.valueOf(obj[2]);
				DisplayForm aa = new DisplayForm(state, elt, Double.parseDouble(df2.format(Double.parseDouble(count) )));

				datavis.add(aa);
				// System.out.println(state + "," + count);
			}}

			return datavis;

		}
		
		
		
		
		
		
		
		
		
	
	
	private Map<String, List<DisplayForm>> merge(Map<String, String> part1, Map<String, String> part2) {

		Map<String, List<DisplayForm>> result = new HashMap<String, List<DisplayForm>>();
		for (Map.Entry<String, String> entry : part1.entrySet()) {
			List<DisplayForm> intermi = new ArrayList<DisplayForm>();
			for (Map.Entry<String, String> entry2 : part2.entrySet()) {

				if (entry.getValue().equals(entry2.getKey())) {
					DisplayForm aa = new DisplayForm(entry2.getKey(), Double.parseDouble(entry2.getValue()));
					intermi.add(aa);
				}
			}
			result.put(entry.getKey(), intermi);
			intermi.clear();
		}
		return result;
	}

	
	
	//detect the topic of recommended query number 2 by comparing fields of conditions with those of select part
	public String DetectTopic(Set<String> set, Set<String> set2) {
		
		
       List<String> different = new ArrayList<String>(set2);
      different.removeAll(set);
       
      
        
		return different.get(0);
	}

	private List<String> computeDuplicate(QueryForm query) {
		Set<String> unique = new HashSet<String>();
		List<String> duplicates = new ArrayList<String>();
		
		for( String att : query.getSelectedAtt() ) {
			String att1="";
				
				if(att.split(Pattern.quote(".")).length>1)
				{  att1 = att.split(Pattern.quote("."))[1];	}
				else att1=att;
		    if( !unique.add( att1 ) ) {
		        duplicates.add( att1 );
		    }
		}


	
		
return duplicates;
}




/***********search in conditions the attribute that corresponds to range and return range as string*******/
	private List<RangeForm> DetermineRange(Map<String, String> conditions) {
		List<RangeForm> res = new ArrayList<RangeForm>();
		for (String entry : conditions.keySet()) {
			if (entry.contains("range,")) {
				String elt = conditions.get(entry);
				for (String it : elt.split(",")) {
					if (it.split("&").length > 1) {
						RangeForm m1 = new RangeForm(Integer.parseInt(it.split("&")[0].replaceAll("\\s+","")),
								Integer.parseInt(it.split("&")[1].replaceAll("\\s+","")));
						res.add(m1);
					}

				}
			}

		}
		return res;
	}
	
	
	

	/***********it is used for dynamic visualiation of recommendation 2
	 * it is used by constructDynamic 4 and 5 fcts
	 * and only for class DisplayFormV4***********/
	public String ConvertToJsonV5(List<DisplayForm> jsonobjects) {
	List<DisplayForm> st1= new ArrayList<DisplayForm>();
	/*for(DisplayForm k:jsonobjects){
		DisplayForm aa=new DisplayForm(k.getGroupby(),k.getVal(),k.getNummer());
				st1.add(aa);
	}*/
		/*FieldNamingStrategy customPolicy = new FieldNamingStrategy() {
			public String translateName(Field f) {
				String res = "";
	
				
				if (f.getName() == "groupby") {
			
					return "axeX";
				}
	
				else if (f.getName() == "val") {
			
					return "state";
	
				}
	
				else  if(f.getName()=="state") {
					return null;
				}
				else  if(f.getName()=="cancellation_num") {
					return null;
				}
	
				else	return "nummer";
	
				}
				
			
		};*/
	
		// json generation
		GsonBuilder gsonBuilder = new GsonBuilder();
	//	gsonBuilder.setFieldNamingStrategy(customPolicy);
		Gson gson = gsonBuilder.create();
		String dataJson = gson.toJson(jsonobjects/*st1*/);
		return dataJson;
	
	}
	
	
	
	
	
	

	
	
	
	
	
	
	//similar to construct dynamic except changing the value of field groupby to "other", this fct is used by drill,  it simplifies the sturcture of JSON
	public List<DisplayForm> constructDynamicV2_ZoomSlice(QueryForm query, String cst) {
		String dataJson;
		List<DisplayForm> equivalent= new ArrayList<DisplayForm>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Object> iniQuery = (List<Object>) session.createSQLQuery(query.display()).list();

		Iterator itr = iniQuery.iterator();
		if (iniQuery.size()<=0) {return equivalent;}
		else{
		Object[] test = (Object[]) itr.next();
		if (test.length == 2) {

			HashMap<String, String> datavis = ExportVis(iniQuery);
			//
			for (Entry<String, String> entry : datavis.entrySet()) {
				DisplayForm a = new DisplayForm( "other", Double.parseDouble(entry.getValue()));
				equivalent.add(a);
			}

		
		
		} else {
			List<DisplayForm> st =new ArrayList<DisplayForm>();
			
			if (String.join("",query.getCondAtt().keySet()).contains("range") )
			{   //special case for ranges
				String elt = "";
				for (String entry : query.getCondAtt().keySet()) {
					if (entry.contains("range,")) {
						elt = entry.split(",")[1];
						break;
					}

				}
				if (elt.length() > 3) {
					
					if (query.getSelectedAtt().contains(elt)) {
						List<RangeForm> cslis = DetermineRange(query.getCondAtt());
						
						st = ExportVisV5(iniQuery,cslis);	
						
					}
					else { st = ExportVisV3(iniQuery,cst);}
					}
				
				else { st = ExportVisV3(iniQuery,cst);}
				
			
				 }
			
			else { st = ExportVisV3(iniQuery,cst);}
			
			
			
			
			
			for (DisplayForm entry : st) {
			//	DisplayForm	kk = new DisplayForm(entry.getaxeX2()/*getval*/,entry./*getNummer()*/getAggNumber());
				DisplayForm	kk = new DisplayForm(entry.getAxeX()/*getval*/,entry./*getNummer()*/getAggNumber());
				equivalent.add(kk);
				
			}
			

	
	
		}

	//	dataJson = ConvertToJson_Normal(equivalent);

		session.close();

		
		return equivalent /*dataJson*/;}

	}



	
	
	/***********without any special encoding***********/
	public String  ConvertToJson_Normal(List<DisplayForm> st) {
	
		
	
	
		// json generation
		GsonBuilder gsonBuilder = new GsonBuilder();
		//gsonBuilder.setFieldNamingStrategy(customPolicy);
		Gson gson = gsonBuilder.create();
		String dataJson = gson.toJson(st);
		return dataJson;
	
	}



	
	//it is a copy of function construct, the only difference is in the input
	public String construct_exec(String query2) {
		DecimalFormat df2 = new DecimalFormat(".##");
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Object> iniQuery = (List<Object>) session.createSQLQuery(query2).list();

		HashMap<String, String> datavis = ExportVis(iniQuery);
		List<DisplayForm> st = new ArrayList<DisplayForm>();
		for (Entry<String, String> entry : datavis.entrySet()) {
			DisplayForm a = new DisplayForm(entry.getKey(), Double.parseDouble(df2.format(Double.parseDouble(entry.getValue()) ))       );
			st.add(a);
		}
		
		
	
		FieldNamingStrategy customPolicy = new FieldNamingStrategy() {
			public String translateName(Field f) {
				if (f.getName() == "state")
					return f.getName().toLowerCase();
				else
					return f.getName().toLowerCase();
			}
		};

		/****************
		 * transformation of result in JSON format
		 ***************/
		// json generation
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingStrategy(customPolicy);
		Gson gson = gsonBuilder.create();
		String dataJson = gson.toJson(st);
		
	
	
		session.close();
		return dataJson;
	
	}
	
	
	
	
}
