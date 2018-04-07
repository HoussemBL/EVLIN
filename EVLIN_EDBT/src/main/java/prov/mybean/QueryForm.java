package prov.mybean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.hibernate.Session;

public class QueryForm extends ActionForm {
	private List<String> selectedAtt;
	private Map<String,String> condAtt;
	private Map<String,String> joinTables;
	

	private List<String> Tables;
	private List<String> groupbyAtt;
	private Map<String,String> orderbyAtt;
	

	public QueryForm() {
		this.selectedAtt = new ArrayList<String>();
	
		this.condAtt = new HashMap<String, String>();
	
	    this.Tables =  new ArrayList<String>();
	
	    	
		this.groupbyAtt = new ArrayList<String>();
	
		
		this.orderbyAtt =new HashMap<String, String>();
	
		this.joinTables=new HashMap<String, String>();
		
	}


	public QueryForm(List<String> selectedAtt, Map<String, String> condAtt, 
			List<String> tables, List<String> groupbyAtt, Map<String, String> orderbyAtt,Map<String, String> joinTables) {
		super();
		this.selectedAtt = new ArrayList<String>();
		this.selectedAtt.addAll(selectedAtt);
		this.condAtt = new HashMap<String, String>();
		this.condAtt.putAll(condAtt);
	    this.Tables =  new ArrayList<String>();
		this.Tables.addAll(tables);
	    	
		this.groupbyAtt = new ArrayList<String>();
		this.groupbyAtt.addAll(groupbyAtt);
		
		this.orderbyAtt =new HashMap<String, String>();
		this.orderbyAtt.putAll(orderbyAtt);
		this.joinTables=new HashMap<String, String>();
		this.joinTables.putAll(joinTables);
	}


	
	public QueryForm(List<String> selectedAtt, Map<String, String> condAtt, 
			List<String> tables, Map<String, String> joinTables) {
		super();
		this.selectedAtt = new ArrayList<String>();
		this.selectedAtt.addAll(selectedAtt);
		this.condAtt = new HashMap<String, String>();
		this.condAtt.putAll(condAtt);
	    this.Tables =  new ArrayList<String>();
		this.Tables.addAll(tables);
	    	
		this.groupbyAtt = new ArrayList<String>();
		
		
		this.orderbyAtt =new HashMap<String, String>();
		
		this.joinTables=new HashMap<String, String>();
		this.joinTables.putAll(joinTables);
	}

	
	public QueryForm(List<String> selectedAtt, 
			List<String> tables, Map<String, String> joinTables, List<String> groupbyAtt) {
		super();
		this.selectedAtt = new ArrayList<String>();
		this.selectedAtt.addAll(selectedAtt);
		this.condAtt = new HashMap<String, String>();
	
	    this.Tables =  new ArrayList<String>();
		this.Tables.addAll(tables);
	    	
		this.groupbyAtt = new ArrayList<String>();
		this.groupbyAtt.addAll(groupbyAtt);
		
		
		this.orderbyAtt =new HashMap<String, String>();
		
		this.joinTables=new HashMap<String, String>();
		this.joinTables.putAll(joinTables);
	}

	
	public QueryForm(List<String> selectedAtt, 
			List<String> tables, Map<String, String> joinTables) {
		super();
		this.selectedAtt = new ArrayList<String>();
		this.selectedAtt.addAll(selectedAtt);
		this.condAtt = new HashMap<String, String>();
	
	    this.Tables =  new ArrayList<String>();
		this.Tables.addAll(tables);
	    	
		this.groupbyAtt = new ArrayList<String>();
		
		
		
		this.orderbyAtt =new HashMap<String, String>();
		
		this.joinTables=new HashMap<String, String>();
		this.joinTables.putAll(joinTables);
	}

	
	
	
	public List<String> getSelectedAtt() {
		return selectedAtt;
	}


	public void setSelectedAtt(List<String> selectedAtt) {
		this.selectedAtt = selectedAtt;
	}
	public Map<String, String> getJoinTables() {
		return joinTables;
	}


	public void setJoinTables(Map<String, String> joinTables) {
		this.joinTables = joinTables;
	}



	public Map<String, String> getCondAtt() {
		return condAtt;
	}


	public void setCondAtt(Map<String, String> condAtt) {
		this.condAtt = condAtt;
	}




	public List<String> getTables() {
		return Tables;
	}


	public void setTables(List<String> tables) {
		Tables = tables;
	}


	public List<String> getGroupbyAtt() {
		return groupbyAtt;
	}


	public void setGroupbyAtt(List<String> groupbyAtt) {
		this.groupbyAtt = groupbyAtt;
	}


	public Map<String, String> getOrderbyAtt() {
		return orderbyAtt;
	}


	public void setOrderbyAtt(Map<String, String> orderbyAtt) {
		this.orderbyAtt = orderbyAtt;
	}

public boolean isDuplica(){
	Set<String> duplicat = new HashSet<String>();

	for(String att : this.selectedAtt)
	{  

		if(att.split(Pattern.quote(".")).length>1)
		{  duplicat.add(att.split(Pattern.quote("."))[1]) ;     }
		else duplicat.add(att);
	}
	
	if(duplicat.size() < this.selectedAtt.size()){
	    return true;
	}
	else  return false;
}

	public String display(){
		//this.orderbyAtt.put("1", "desc");
		String res=""; 
				res=res+"select ";
				
		/******searching similar columns name like case of A1.state and A2.state ******/		
			
		
				
			Set<String> unique = new HashSet<String>();
			List<String> duplicates = new ArrayList<String>();
			
			for( String att : this.selectedAtt ) {
				String att1="";
					
					if(att.split(Pattern.quote(".")).length>1)
					{  att1 = att.split(Pattern.quote("."))[1];	}
					else att1=att;
			    if( !unique.add( att1 ) ) {
			        duplicates.add( att1 );
			    }
			}	
		
				/******************/
		
				
		for (String att : this.selectedAtt)
		{
			if(att.split(Pattern.quote(".")).length>1 && !att.split(Pattern.quote("."))[1].equals("*"))
		{  if( duplicates.contains(att.split(Pattern.quote("."))[1])   ) 
		{ res= res+ att+" as "+att.split(Pattern.quote("."))[1]+"V"+this.selectedAtt.indexOf(att)+ " ,";    }     
		else
			res= res+ att+" ,"; 
		}
			else
			{  if( duplicates.contains(att)   )  { res= res+ att+" as "+att+"V"+this.selectedAtt.indexOf(att)+ " ,";    }     
			else
				res= res+ att+" ,"; 
			} 
		
		
		}
		res=res.substring(0,res.length()-1);
		
		res= res+" from  ";

			Set<String> set = new HashSet<String>(this.Tables);
			
		if(set.size() < this.Tables.size()){
			int j = 1;
			for (String att : this.Tables)
			{if(att.equals("Airports"))
				
				{res= res+ att+" as A"+j+" ,"; 
			j++;}
			else if( Collections.frequency( this.Tables,"team") >1 && att.equals("team"))
				
			{res= res+ att+" as T"+j+" ,"; 
			j++;}
			else if(Collections.frequency( this.Tables,"player") >1 && att.equals("player"))
				
			{res= res+ att+" as P"+j+" ,"; 
			j++;}	
			
			
			}
			for (String tab : set)
			{
				 if(Collections.frequency( this.Tables,"team") >1 && Collections.frequency( this.Tables,"player") >1){
						if (!tab.equals("player") && !tab.equals("team") && !tab.equals("Airports"))
						{
							res= res+ tab+" ,"; 	
						}
					}
			else  if(Collections.frequency( this.Tables,"team") >1){
					if (!tab.equals("team") && !tab.equals("Airports"))
					{
						res= res+ tab+" ,"; 	
					}
				}
				else if(Collections.frequency( this.Tables,"player") >1){
					if (!tab.equals("player") && !tab.equals("Airports"))
					{
						res= res+ tab+" ,"; 	
					}
				}
				 
				else if(!tab.equals("Airports"))
					{res= res+ tab+" ,"; }
			}
			
		}
		else{
		for (String att : this.Tables)
		{res= res+ att+" ,";   }}
		
		res=res.substring(0,res.length()-1);
		
		if(joinTables.size()>0 || condAtt.size()>0  )	
		{res=res+ " where ";}
		
		if(joinTables.size()>0)	{
			
			res= write(res,this.joinTables,"=","AND");
			if(condAtt.size()>0){ res=res + " AND ";}
		}	
		
	if(condAtt.size()>0)	{
		
		res=write3(res,condAtt,"=","AND");
		
		
	}
	
	
	 if(this.groupbyAtt.size()>0)	{
		res=res + " group by  ";
				for (String att : this.groupbyAtt)
		{res= res+ att+" ,";   }
		res=res.substring(0,res.length()-1);
	}
	
	
	
	if(this.orderbyAtt.size()>0)	{
		res=res + " order by  ";
		
		res = write(res,this.orderbyAtt," ",",");
		
	}
	
	if (String.join(",", this.getSelectedAtt()).contains("row_number")) {res=res+"";}
	else 	if (String.join(",", this.getSelectedAtt()).contains("as mem")) {res=res+"";}
	else	if(String.join(",", this.getSelectedAtt()).contains("remaining_values")) 
	{res=res+ " limit 50";}
	


	//else res= res+ "limit 30";
		
		return res;
		
	}


	private String write(String res, Map<String, String> jj,String op, String sep) {
		int i=0;
		for (Map.Entry<String, String> entry : jj.entrySet()) {
res=res+ entry.getKey()+op+entry.getValue()+" ";

i++;
if (i+1   <= jj.size()) {
	res = res + " "+sep+" ";
}
		}
		return res;
	}

	private String write2(String res, Map<String, String> jj,String op, String sep) {
		int i=0;
		for (Map.Entry<String, String> entry : jj.entrySet()) {
if(entry.getValue().split(",").length>1)		{
	String[] vals = entry.getValue().split(",");
	res=res+ entry.getKey()+" in( ";
	 for(int k=0; k<vals.length; k++){
		res=res + "'"+vals[k].replaceAll("\\s+","")+"'," ;  
    }
	 res=res.substring(0,res.length()-1);
	res=res+")";		
	

}	
else {res=res+ entry.getKey()+op+"'"+entry.getValue()+"' ";}

i++;
if (i+1   <= jj.size()) {
	res = res + " "+sep+" ";
}
		}
		return res;
	}
	
	
	
	
	
	
	private String write3(String res, Map<String, String> jj, String op, String sep) {
		int i = 0;
		for (Map.Entry<String, String> entry : jj.entrySet()) {
			if (entry.getKey().contains("$")) {
				{
					String[] vals = entry.getKey().split("\\$");
					res = res + vals[1] + vals[0] + entry.getValue();

				}

			}

			else if (entry.getKey().contains("NotRange,")) {
				String elm="";
				if (entry.getValue().split(",").length > 1) {
					String[] vals = entry.getValue().split(",");
					
					String lower= vals[0].split("&")[0];
					String upper=vals[0].split("&")[1];
					Integer low= Integer.parseInt(vals[0].split("&")[0]);
					Integer up=Integer.parseInt(vals[0].split("&")[1]);
					for (int k = 1; k < vals.length; k++) {
						Integer low1= Integer.parseInt(vals[k].split("&")[0].replaceAll("\\s+", ""));
						Integer up1=Integer.parseInt(vals[k].split("&")[1].replaceAll("\\s+", ""));
						if (low1<low) low=low1;
						if (up1>up) up=up1;
					}
					elm=elm+"("+entry.getKey().split(",")[1] +" > "+ up + " or "+  entry.getKey().split(",")[1] +" < "+ low +")";
					
					

				} else {
					String lower= entry.getValue().split("&")[0];
					String upper=entry.getValue().split("&")[1];
					elm=elm+"("+entry.getKey().split(",")[1] +" > "+ upper + " or "+  entry.getKey().split(",")[1] +" < "+ lower +")";
				}
				res = res +elm;
			}
			
			
			else if (entry.getKey().contains("not,")) {
				if (entry.getValue().split(",").length > 1) {
					String[] vals = entry.getValue().split(",");
					res = res + entry.getKey().split(",")[1] + " not in( ";
					for (int k = 0; k < vals.length; k++) {
						res = res + "'" + vals[k]/*.replaceAll("\\s+", "")*/ + "',";
					}
					res = res.substring(0, res.length() - 1);
					res = res + ")";

				} else {
					res = res + entry.getKey().split(",")[1] + "<>'" + entry.getValue() + "' ";
				}

			}
			
			else if (entry.getKey().contains("NotIn,")) {
				
					String[] vals = entry.getValue().split(",");
					res = res + entry.getKey().split(",")[1] + " not in( "+entry.getValue()+ ")";
			

			}
			
			
			
			
			else if (entry.getKey().contains("range,")) {
				if (entry.getValue().split(",").length > 1) {
					String[] vals = entry.getValue().split(",");
					String rg="";
					
					for (int k = 0; k < vals.length; k++) {
						String inf=vals[k].split("&")[0];
						String supp=vals[k].split("&")[1];
						
						rg = rg +"("+  entry.getKey().split(",")[1]+" between "+inf + " and "+ supp +")"      ;
						if(k+1<vals.length)
						{ rg=rg+ " or ";}
					}
				res=res+"("+rg+")";	

				} else {
					String inf=entry.getValue().split("&")[0];
					String supp=entry.getValue().split("&")[1];
					res = res +"("+  entry.getKey().split(",")[1]+" between "+inf + " and "+ supp +")"      ;
					//res = res + "("+  entry.getKey().split(",")[1]+" > "+inf + " and "+    entry.getKey().split(",")[1]+" < "+supp +")"      ;
				}

			}

			else {
				 if(entry.getValue().contains("select"))
				{
					
					res=res+ entry.getKey() + " in( "+entry.getValue()+ ")";
				}
				

					else if (entry.getValue().split(",").length > 1) {
					String[] vals = entry.getValue().split(",");
					res = res + entry.getKey() + " in( ";
					for (int k = 0; k < vals.length; k++) {
						res = res + "'" + vals[k]/*.replaceAll("\\s+", "")*/ + "',";
					}
					res = res.substring(0, res.length() - 1);
					res = res + ")";

				} 
				
				
				else {
					res = res + entry.getKey() + op + "'" + entry.getValue() + "' ";
				}

			}

			i++;
			if (i + 1 <= jj.size()) {
				res = res + " " + sep + " ";
			}

		}
		return res;
	}


/********************used to grouped bar chart to avoid cluttered graphs************************/
	public String Simplify(String old) {
		
		
		String res=""; 
		res=res+"select ";
		
/******searching similar columns name like case of A1.state and A2.state ******/		
	

		
	Set<String> unique = new HashSet<String>();
	List<String> duplicates = new ArrayList<String>();
	
	for( String att : this.selectedAtt ) {
		String att1="";
			
			if(att.split(Pattern.quote(".")).length>1)
			{  att1 = att.split(Pattern.quote("."))[1];	}
			else att1=att;
	    if( !unique.add( att1 ) ) {
	        duplicates.add( att1 );
	    }
	}	

		/******************/

	
for (String att : this.selectedAtt)
{
	if(att.split(Pattern.quote(".")).length>1)
{  if( duplicates.contains(att.split(Pattern.quote("."))[1])   ) 
{ res= res+ att.split(Pattern.quote("."))[1]+"V"+this.selectedAtt.indexOf(att)+ " ,";   
}     
else
	res= res+ att.split(Pattern.quote("."))[1]+" ,"; 
}
	else
	{  if( duplicates.contains(att)   )  { res= res+ att+" as "+att+"V"+this.selectedAtt.indexOf(att)+ " ,";    }     
	else
	{if(att.contains("count")) res = res+ " subquery.cc ,";
	else if(att.toLowerCase().contains("max")) res = res+ " subquery.cc ,";
	else if(att.toLowerCase().contains("min")) res = res+ " subquery.cc ,";
	else if(att.toLowerCase().contains("avg")) res = res+ " subquery.cc ,";
	else if(att.toLowerCase().contains("sum")) res = res+ " subquery.cc ,";
	else res= res+ att+" ,"; 
	
	
	
	}
	} 


}
res=res.substring(0,res.length()-1);

res= res+" from  ";
		
		
		
		
		
		/*******************************************/
		QueryForm exchange= new QueryForm();
		exchange.setCondAtt(this.getCondAtt());
		exchange.setSelectedAtt(this.getSelectedAtt());
		exchange.setTables(this.getTables());
		exchange.setGroupbyAtt(this.getGroupbyAtt());
		exchange.setOrderbyAtt(this.getOrderbyAtt());
		exchange.setJoinTables(this.getJoinTables());
		
		
		/*if(old.contains("."))
		{old=old.split(Pattern.quote("."))[1];}*/
		
		String elt= "row_number() over ( partition by "+ old + " order by 1 DESC) ";
		List<String> prv =new ArrayList<String>();
		List<String> replace =new ArrayList<String>();
				prv.addAll(this.getSelectedAtt());
				for(String att :prv)
				{
					if(att.toLowerCase().contains("count"))
					{replace.add(att+ " as cc");}
					else 	if(att.toLowerCase().contains("max"))
					{replace.add(att+ " as cc");}	
					
					else 	if(att.toLowerCase().contains("min"))
					{replace.add(att+ " as cc");}	
					
					else 	if(att.toLowerCase().contains("avg"))
					{replace.add(att+ " as cc");}		
					
					else 	if(att.toLowerCase().contains("sum"))
					{replace.add(att+ " as cc");}	
					else replace.add(att);
				}
		replace.add(elt);
		exchange.setSelectedAtt(replace);
		
		res =res + "("+exchange.display()+") as subquery where row_number<=3 ";
	
		
		//res= res+ " limit 60";
		return res;
	}
	
	
	
	
	
	
	//remmeber which element was added as extension by comparing conditions with selected attributes
	public String FindNew() {
	
		Set<String> unique = new HashSet<String>();
		List<String> duplicates = new ArrayList<String>();
		
		for( String att : this.selectedAtt ) {
			String att1="";
				
				if(att.split(Pattern.quote(".")).length>1)
				{  att1 = att.split(Pattern.quote("."))[1];	}
				else att1=att;
		    if( !unique.add( att1 ) ) {
		        duplicates.add( att1 );
		    }
		}	

			/******************/
List<String> intermid= new ArrayList<String>();
intermid.addAll(this.getGroupbyAtt());
List<String> intermid2= new ArrayList<String>();		
		
		
		
	for (String att : intermid)
	{
		if(att.split(Pattern.quote(".")).length>1)
	{  if( duplicates.contains(att.split(Pattern.quote("."))[1])   ) 
	{ intermid2.add(att.split(Pattern.quote("."))[1]+"V"+intermid.indexOf(att));   
	}     
	else
		intermid2.add(att.split(Pattern.quote("."))[1]); 
	}
		else
		{  if( duplicates.contains(att)   )  { intermid2.add(att+"V"+this.selectedAtt.indexOf(att));    }     
		else
		{
		intermid2.add(att); 
		
		
		
		}
		} 


	}	
		
		
		
		
		
		
		
		
		
		
		
	for(String search:	intermid2)
	
	{
		if(search.contains("V")){   if(!this.getCondAtt().containsKey(search.subSequence(0, search.length()-2)))
		{
			if(search.split(Pattern.quote(".")).length>1)   return search.split(Pattern.quote("."))[1];
			else return search;
			
		}           }
		else{if(!this.getCondAtt().containsKey(search))
		{
			if(search.split(Pattern.quote(".")).length>1)   return search.split(Pattern.quote("."))[1];
			else return search;
			
		}
	}
		}
	
		return null;
	}

	
	
	
	
	
	
	
	//rlike FindNew with avoiding case of redundancy state1 and state2
		public String FindNewV2() {
		
			Set<String> unique = new HashSet<String>();
			List<String> duplicates = new ArrayList<String>();
			
			for( String att : this.selectedAtt ) {
				String att1="";
					
					if(att.split(Pattern.quote(".")).length>1)
					{  att1 = att.split(Pattern.quote("."))[1];	}
					else att1=att;
			    if( !unique.add( att1 ) ) {
			        duplicates.add( att1 );
			    }
			}	

				/******************/
	List<String> intermid= new ArrayList<String>();
	intermid.addAll(this.getGroupbyAtt());
	
		for(String search:	intermid)
		
		{
			if(search.contains("V")){   if(!this.getCondAtt().containsKey(search.subSequence(0, search.length()-2)))
			{
				if(search.split(Pattern.quote(".")).length>1)   return search.split(Pattern.quote("."))[1];
				else return search;
				
			}           }
			else{if(!this.getCondAtt().containsKey(search))
			{
				if(search.split(Pattern.quote(".")).length>1)   return search.split(Pattern.quote("."))[1];
				else return search;
				
			}
		}
			}
		
			return null;
		}

	
	//remember which element was added as extension by comparing conditions with selected attributes
	public String FindOld(List<String> firstSelects) {
		
	for(String search:	this.getSelectedAtt())
	
	{
		if(!firstSelects.contains(search))
		{
			return search;
			
		}
	}
	
		return null;
	}

	public void Truncate(){
		 
			this.selectedAtt = new ArrayList<String>();
			
			this.condAtt = new HashMap<String, String>();
		
		    this.Tables =  new ArrayList<String>();
		
		    	
			this.groupbyAtt = new ArrayList<String>();
			
			
			this.orderbyAtt =new HashMap<String, String>();
			
			this.joinTables=new HashMap<String, String>();
		
		
		
		
		
		
	}


	//to construct second part of recommendation2
	public String SimplifyV2(String old, String newatt) {

		String res = "";
		res = res + "select ";

		/******
		 * searching similar columns name like case of A1.state and A2.state
		 ******/

		for (String att : this.selectedAtt) {

			if (att.toLowerCase().contains("count"))
				res = res + "avg(subquery1.cc) ,";
			else if (att.toLowerCase().contains("max"))
				res = res + " avg(subquery1.cc) ,";
			else if (att.toLowerCase().contains("min"))
				res = res + " avg(subquery1.cc) ,";
			else if (att.toLowerCase().contains("avg"))
				res = res + " avg(subquery1.cc) ,";
			else if (att.toLowerCase().contains("sum"))
				res = res + " avg(subquery1.cc) ,";

		}
		res = res + newatt + " from  ";

		/*******************************************/
		QueryForm exchange = new QueryForm();
		exchange.setCondAtt(this.getCondAtt());
		exchange.setSelectedAtt(this.getSelectedAtt());
		exchange.setTables(this.getTables());
		exchange.setGroupbyAtt(this.getGroupbyAtt());
		exchange.setOrderbyAtt(this.getOrderbyAtt());
		exchange.setJoinTables(this.getJoinTables());

		String elt = "row_number() over ( partition by " + old + " order by 1 DESC) ";
		List<String> prv = new ArrayList<String>();
		List<String> replace = new ArrayList<String>();
		prv.addAll(this.getSelectedAtt());
		for (String att : prv) {
			if (att.toLowerCase().contains("count")) {
				replace.add(att + " as cc");
			} else if (att.toLowerCase().contains("max")) {
				replace.add(att + " as cc");
			}

			else if (att.toLowerCase().contains("min")) {
				replace.add(att + " as cc");
			}

			else if (att.toLowerCase().contains("avg")) {
				replace.add(att + " as cc");
			}

			else if (att.toLowerCase().contains("sum")) {
				replace.add(att + " as cc");
			} else
				replace.add(att);
		}
		replace.add(elt);
		exchange.setSelectedAtt(replace);

		res = res + "(" + exchange.display() + ") as subquery1 where row_number>3 and " + newatt + " in ("
				+ subpart(newatt, old) + ")";

		res = res + " group by " + newatt;

		return res;

	}

	//verify whether two data sets are suitable for recommendation2
	private String subpart(String newatt, String old) {
String res = "select "+newatt+" from  ";
		
/*******************************************/
		QueryForm exchange= new QueryForm();
		exchange.setCondAtt(this.getCondAtt());
		exchange.setSelectedAtt(this.getSelectedAtt());
		exchange.setTables(this.getTables());
		exchange.setGroupbyAtt(this.getGroupbyAtt());
		exchange.setOrderbyAtt(this.getOrderbyAtt());
		exchange.setJoinTables(this.getJoinTables());
		
	
		
		String elt= "row_number() over ( partition by "+ old + " order by 1 DESC) ";
		List<String> prv =new ArrayList<String>();
		List<String> replace =new ArrayList<String>();
				prv.addAll(this.getSelectedAtt());
				for(String att :prv)
				{
					if(att.toLowerCase().contains("count"))
					{replace.add(att+ " as cc");}
					else 	if(att.toLowerCase().contains("max"))
					{replace.add(att+ " as cc");}	
					
					else 	if(att.toLowerCase().contains("min"))
					{replace.add(att+ " as cc");}	
					
					else 	if(att.toLowerCase().contains("avg"))
					{replace.add(att+ " as cc");}		
					
					else 	if(att.toLowerCase().contains("sum"))
					{replace.add(att+ " as cc");}	
					else replace.add(att);
				}
		replace.add(elt);
		exchange.setSelectedAtt(replace);
		
		return res =res + "("+exchange.display()+") as subquery where row_number<=3 ";
		
	
		
		
	}
	
	
	
	public String Partition(String old) {
String res= " ";

		/*******************************************/
		QueryForm exchange = new QueryForm();
		exchange.setCondAtt(this.getCondAtt());
		exchange.setSelectedAtt(this.getSelectedAtt());
		exchange.setTables(this.getTables());
		exchange.setGroupbyAtt(this.getGroupbyAtt());
		exchange.setOrderbyAtt(this.getOrderbyAtt());
		exchange.setJoinTables(this.getJoinTables());

		/*
		 * if(old.contains(".")) {old=old.split(Pattern.quote("."))[1];}
		 */

		String elt = "row_number() over ( partition by " + old + " order by 1 DESC) ";
		List<String> prv = new ArrayList<String>();
		List<String> replace = new ArrayList<String>();
		prv.addAll(this.getSelectedAtt());
		for (String att : prv) {
			if (att.toLowerCase().contains("count")) {
				replace.add(att + " as cc");
			} else if (att.toLowerCase().contains("max")) {
				replace.add(att + " as cc");
			}

			else if (att.toLowerCase().contains("min")) {
				replace.add(att + " as cc");
			}

			else if (att.toLowerCase().contains("avg")) {
				replace.add(att + " as cc");
			}

			else if (att.toLowerCase().contains("sum")) {
				replace.add(att + " as cc");
			} else
				replace.add(att + " as mem" + prv.indexOf(att));
		}
		// replace.add(elt);
		exchange.setSelectedAtt(replace);
		
		res = "select count,el2,el1 from (select row_number() "
				+ "  over ( partition by query1.mem2 order by query1.cc desc)  as rowNumber, "
				+ " query1.mem1 as el1 ,query1.mem2 as el2,query1.cc as count " + " from (" + exchange.display()
				+ ") as query1 ) as query2 where" + " query2.rowNumber <=2 order by   1 desc ";
		

		 res= res+ " limit 60";
		return res;
	}

	
	//to construct second part of recommendation2
	public String CompletePartition(String old, String newatt) {

		String res= " ";

		/*******************************************/
		QueryForm exchange = new QueryForm();
		exchange.setCondAtt(this.getCondAtt());
		exchange.setSelectedAtt(this.getSelectedAtt());
		exchange.setTables(this.getTables());
		exchange.setGroupbyAtt(this.getGroupbyAtt());
		exchange.setOrderbyAtt(this.getOrderbyAtt());
		exchange.setJoinTables(this.getJoinTables());

		/*
		 * if(old.contains(".")) {old=old.split(Pattern.quote("."))[1];}
		 */

		String elt = "row_number() over ( partition by " + old + " order by 1 DESC) ";
		List<String> prv = new ArrayList<String>();
		List<String> replace = new ArrayList<String>();
		prv.addAll(this.getSelectedAtt());
		for (String att : prv) {
			if (att.toLowerCase().contains("count")) {
				replace.add(att + " as cc");
			} else if (att.toLowerCase().contains("max")) {
				replace.add(att + " as cc");
			}

			else if (att.toLowerCase().contains("min")) {
				replace.add(att + " as cc");
			}

			else if (att.toLowerCase().contains("avg")) {
				replace.add(att + " as cc");
			}

			else if (att.toLowerCase().contains("sum")) {
				replace.add(att + " as cc");
			} else
				replace.add(att + " as mem" + prv.indexOf(att));
		}
		
		exchange.setSelectedAtt(replace);
		
		res = "select sum(count),el2 from (select row_number() "
				+ "  over ( partition by query1.mem2 order by query1.cc desc)  as rowNumber, "
				+ " query1.mem1 as el1 ,query1.mem2 as el2,query1.cc as count " + " from (" + exchange.display()
				+ ") as query1 ) as query2 where" + " query2.rowNumber >=3 and query2.el2 in ( "+
				" select query4.el2 from  ("
				+ " select row_number() "
				+ " over ( partition by query3.mem2 order by query3.cc desc)  as rowNumber,"
				+ " query3.mem1 as el1 ,query3.mem2 as el2,query3.cc as count"
				+ " from ("+ exchange.display()+" )  as query3 ) as query4 "
						+ "where query4.rowNumber<=2 limit 60)"
						+ "group by el2 ";
				
			
	

		
	

		return res;

	}


	/*************search key attribute whether it is envolved in join tables conditions***************/
	public boolean checkConds(String attribute) {
		
		for (Map.Entry<String, String> entry : this.joinTables.entrySet()) {
		if(entry.getKey().split(Pattern.quote("."))[1].contains(attribute)  ||  entry.getValue().split(Pattern.quote("."))[1].contains(attribute)   )
		{return true ;
		}	
		}
		
		return false;
	}


	public String topTen(String old) {
	//	System.out.println( " @@@@ "+old);
		String res = " ";
		String remove = "";
		/*******************************************/
		QueryForm exchange = new QueryForm();
		exchange.setCondAtt(this.getCondAtt());

		List<String> ee = new ArrayList<String>();
		ee.addAll(this.getSelectedAtt());

		remove = Drop(ee, old);
	//	System.out.println( " @@@@1 "+remove);
		if (remove.length() > 3) {
			ee.remove(remove);
		}

		List<String> rep = new ArrayList<String>();
		
		for (String att :ee) {
			if (att.toLowerCase().contains("count")) {
				rep.add(att + " as cc");
			} else if (att.toLowerCase().contains("max")) {
				rep.add(att + " as cc");
			}

			else if (att.toLowerCase().contains("min")) {
				rep.add(att + " as cc");
			}

			else if (att.toLowerCase().contains("avg")) {
				rep.add(att + " as cc");
			}

			else if (att.toLowerCase().contains("sum")) {
				rep.add(att + " as cc");
			} else
				rep.add(att + " as mem" );
		}
		
		
		
		exchange.setSelectedAtt(rep);
		remove = "";

		List<String> grps = new ArrayList<String>();
		grps.addAll(this.getGroupbyAtt());
		remove = Drop(grps, old);
		if (remove.length() > 3) {
			grps.remove(remove);
		}
	
		exchange.setGroupbyAtt(grps);
	
	//	Map<String,String> order= new HashMap<String, String>();
		//order.put("1", "desc");
		/*******************************************/
		Map<String, String> orderForLimit = new HashMap<String, String>();
		orderForLimit.put("1", "desc");
		exchange.setOrderbyAtt(orderForLimit );
		/*******************************************/
		exchange.setJoinTables(this.getJoinTables());
		exchange.setTables(this.getTables());

		res = exchange.display() + " limit 10";
		return res;
	}


	
	/*********determine which element should be removed********/
	private String Drop(List<String> ee, String old) {
		String remove="";
		for(String elt:ee)
		{
			  
				
			if(elt.equals(old)) {remove=elt;
			break;}             
				
				
			}
		return remove;
	}	
	
	
	
	
//select elements having values on top 10	
	//fon't forget order by count(*) is very important here
	public QueryForm Derivation(String param,String newatt) {
		
		String res= " ";
		String at=newatt;

			
				QueryForm exchange = new QueryForm();
				
				exchange.setSelectedAtt(this.getSelectedAtt());
				exchange.setTables(this.getTables());
				exchange.setGroupbyAtt(this.getGroupbyAtt());
				/*******************************************/
				Map<String, String> orderForLimit = new HashMap<String, String>(this.getOrderbyAtt());
				orderForLimit.put("1", "desc");
				exchange.setOrderbyAtt(orderForLimit );
				/*******************************************/
				exchange.setJoinTables(this.getJoinTables());

			
				List<String> prv = new ArrayList<String>();
				Map<String,String> cds = new HashMap<String, String>();
				cds.putAll(this.getCondAtt());
				
			for(String att: this.getSelectedAtt())
			{
				if(att.contains("."))
				{
					if (att.split(Pattern.quote("."))[1].equals(newatt)) {
						at=att;
						break;
					}
				}
			}
				
				res = "select  query1.mem from (" + param
						+ ") as query1   ";
				cds.put(at, res);
				exchange.setCondAtt(cds);

				return exchange;
			}

	public QueryForm CompleteDerivation(String param,String newatt) {
		String res= " ";
String at=newatt;
for(String att: this.getSelectedAtt())
{
	if(att.contains("."))
	{
		if (att.split(Pattern.quote("."))[1].equals(newatt)) {
			at=att;
			break;
		}
	}
}
				/*******************************************/
				QueryForm exchange = new QueryForm();
				
				List<String> sel = new ArrayList<String>();
						sel.addAll(this.getSelectedAtt());
				sel.remove(at);
				exchange.setSelectedAtt(sel);
				exchange.setTables(this.getTables());
				exchange.setGroupbyAtt(this.getGroupbyAtt());
				/*******************************************/
				Map<String, String> orderForLimit = new HashMap<String, String>(this.getOrderbyAtt());
				orderForLimit.put("1", "desc");
				exchange.setOrderbyAtt(orderForLimit );
				/*******************************************/
				exchange.setJoinTables(this.getJoinTables());

			
			
				Map<String,String> cds = new HashMap<String, String>();
				cds.putAll(this.getCondAtt());
							
				
				
			
			
			
				res = "select  query1.mem from (" + param
						+ ") as query1   ";
				cds.put("NotIn,"+at, res);
				exchange.setCondAtt(cds);

				return exchange;
			}
	
	
	
	
	
	public boolean checkDuplicatedTables()
	{
		boolean duplica = false;
		Set<String> duplicat = new HashSet<String>(this.getTables());

	if (duplicat.size() < this.getTables().size()) {
		duplica = true;
	}
	return duplica;
	}
	
	public boolean checkDuplicatedAttributes()
	{
		

	if ( this.getTables().contains("Tail") && this.getTables().contains("Flights") ) {
		return true;
	}
	else if ( this.getTables().contains("match") ) {
		return false;
	}
	else return false;
	}
	
	
	
}
