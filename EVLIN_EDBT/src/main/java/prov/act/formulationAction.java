package prov.act;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.hibernate.Session;

import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import prov.CRUD.VegaJsonMapping;

import prov.mybean.DisplayForm;
import prov.mybean.FacetForm;
import prov.mybean.QueryForm;
import prov.util.HibernateUtil;

public class formulationAction extends Action {
	private final static String SUCCESS = "success";

	private final static String FAILURE = "echec";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/******************** construction of user's query ********************/
		String[] checked = request.getParameterValues("items");
		String query = "";
		
		Map<String,List<String>> elts= new HashMap<String, List<String>>();
		
		for (int i = 0; i < checked.length; ++i) {
			String[] res = checked[i].split(",");
			 List<String> vv= new ArrayList<String>();
			if (elts.keySet().contains(res[0]))
			{    vv.addAll(elts.get(res[0]));
			vv.add(res[1]);
			}
			else {
				
					vv.add(res[1]);	
				
			}
			elts.put(res[0], vv);
		}
		
	/**********************************getting dimensions***************************************/	
	/*	String[] checked1 = request.getParameterValues("dimensions");
		String query1 = "";
		
		Map<String,List<String>> elts1= new HashMap<String, List<String>>();
		
		for (int i = 0; i < checked1.length; ++i) {
			String[] rest = checked1[i].split(",");
			 List<String> vv1= new ArrayList<String>();
			if (elts1.keySet().contains(rest[0]))
			{    vv1.addAll(elts.get(rest[0]));
			vv1.add(rest[1]);
			}
			else {
				
					vv1.add(rest[1]);	
				
			}
			elts1.put(rest[0], vv1);
		}
		
		
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/***************************************************************************/
		
		
		int k=0;
		for (Map.Entry<String, List<String>> entry : elts.entrySet()) {
			if(entry.getValue().size()>1)
			{query= query+ "  "+ entry.getKey() + " in(" ;
			for(String et : entry.getValue())
			{   query=query+"'"+et+"',"     ;      }
			query=query.substring(0,query.length()-1);
					query=query+ ")";}
			else{
			query= query+ "  "+ entry.getKey() + "='" + entry.getValue().get(0) + "'";     } 
			k++;
			if(k<elts.keySet().size())
			{query = query + " AND ";}
		}

		System.out.println("================================  " + query);
		
		
	/*****************user query formalization*******************/	
		Map<String, String> conds = new HashMap<String, String>();
		for (Map.Entry<String, List<String>> entry : elts.entrySet()) {

			conds.put(entry.getKey(), String.join(",", entry.getValue()));

		}

		/*List<String> atts = new ArrayList(Arrays.asList("count(*)"));
		List<String> joinTables = new ArrayList(Arrays.asList("Airports", "Flights"));
		List<String> groupAtt = new ArrayList();
		Map<String, String> orderBy = new HashMap<String, String>();
		// conds.put("count(*)","desc");
		Map<String, String> joinss = new HashMap<String, String>();
		joinss.put("Flights.origin", "Airports.iata");

		QueryForm queryBean = new QueryForm(atts, conds, joinTables, groupAtt, orderBy, joinss);*/
		
		/********************** query result computation ********************/
		

		/*String query2 = "Select CAST (count(*) AS varchar) as cancellation_num, A.state"
				+ " from Airports A, Flights F where A.iata=F.origin and cancelled='1' "
				+ "group by A.state order by cancellation_num desc ";*/
				
		String query2 = "select CAST (count(*) AS varchar) as cancellation_num, A.state"
				+ " from Airports A, Flights F where A.iata=F.origin and "+ query +
				"group by A.state order by cancellation_num desc limit 30";	
				
		
        VegaJsonMapping vega=new VegaJsonMapping();
        String dataJson=vega.construct_exec(query2);

		HttpSession ss = request.getSession();
		/****************
		 * Sending JSON data to jsp page to be used later in vega visualization
		 ***************/
		
		ss.setAttribute("datajson", dataJson);
		ss.setAttribute("conds", conds);

		return mapping.findForward(SUCCESS);
	}

	
}
