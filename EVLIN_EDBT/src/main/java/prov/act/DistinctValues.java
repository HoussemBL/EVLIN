package prov.act;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import henry.database.connection.DBConnector;
import henry.database.connection.vizDBUtil;
import prov.dao.*;
import prov.mybean.DBForm;
import prov.mybean.FacetForm;
import prov.mybean.QueryInputForm;
import prov.util.*;

public class DistinctValues extends Action {
	public static DBConnector connector; 
	private final static String SUCCESS = "success";

	private final static String FAILURE = "echec";
	public static   FileWriter pw1 ;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession ss = request.getSession();
		DBForm DBobj = (DBForm) form;
		DBForm DBobj2;
		DBForm DBobj3;
		DBForm DBobj4;
		DBForm DBobj5;
		List<DBForm> lis_db = (List<DBForm>) ss.getAttribute("listDB");
		
		pw1	= new FileWriter("/Users/houssem/Documents/workspaceDemo/stats.txt", false); 
		List<String> columns = new ArrayList<String>(Arrays.asList("year", "month","Cancellationcode","Cancelled", "dayofmonth","dayofweek"));
		String fact_Table="flights";
		Session session;
	
		
		if(DBobj!=null && DBobj.getName().equals("USflights05"))
		{
		session=  HibernateUtil.getSessionFactoryvariable("hibernateFlights2.cfg.xml");
		DBobj.setReal_name("USflights2");
		DBobj.setName("USflights2");
	
		DBobj.setSelected(true);
		for (DBForm elt :lis_db){
			if (elt.getName().equals("USflights05"))
			{
				elt.setSelected(true);
			}
			else {
				if (elt.getSelected())
				{elt.setSelected(false);}
			}
		}
		
		}
		else if(DBobj!=null && DBobj.getName().equals("USflights10Years"))
		{
		session=  HibernateUtil.getSessionFactoryvariable("hibernateFlightsAll.cfg.xml");
			 DBobj.setName("USflightsAll");
			DBobj.setReal_name("USflightsAll");
			DBobj.setSelected(true);
			for (DBForm elt :lis_db){
				if (elt.getName().equals("USflights10Years"))
				{
					elt.setSelected(true);
				}
				else {
					if (elt.getSelected())
					{elt.setSelected(false);}
				}
			}
		
		}
		else if(DBobj!=null && DBobj.getName().equals("Soccer"))
		{
		session=  HibernateUtil.getSessionFactoryvariable("hibernateSoccer.cfg.xml");
			DBobj.setName("soccerDB");
			columns = new ArrayList<String>(Arrays.asList("season", "date","goal","card", "result"));
			fact_Table="match";
			DBobj.setReal_name("soccerDB");
			DBobj.setSelected(true);
			for (DBForm elt :lis_db){
				if (elt.getName().equals("Soccer"))
				{
					elt.setSelected(true);
				}
				else {
					if (elt.getSelected())
					{elt.setSelected(false);}
				}
			}
		}
		
		else if(DBobj!=null && DBobj.getName().equals("Formula1"))
		{
		session=  HibernateUtil.getSessionFactoryvariable("hibernateFormula.cfg.xml");
		DBobj.setName("formulaOne");
		columns = new ArrayList<String>(Arrays.asList("status", "rank","drivers_number","points", "laps_done"));
		fact_Table="result";
		DBobj.setReal_name("formulaOne");
		DBobj.setSelected(true);
		for (DBForm elt :lis_db){
			if (elt.getName().equals("Formula1"))
			{
				elt.setSelected(true);
			}
			else {
				if (elt.getSelected())
				{elt.setSelected(false);}
			}
		}
		
		}
		
		else{	
			session= HibernateUtil.getSessionFactory().getCurrentSession();
		}
		
		/**initialization of DB**/
		if(DBobj==null){
			DBobj=new DBForm();
			// DBobj2 = new DBForm("USflights05","USflights2");
			DBobj3=new DBForm("USflights10Years","USflightsAll");
			DBobj4=new DBForm("Soccer","soccerDB");
			DBobj5=new DBForm("Formula1","formulaOne");
			lis_db=new ArrayList<DBForm>(Arrays.asList(DBobj/*,DBobj2*/,DBobj3,DBobj4,DBobj5));
		}
		
		
		session.beginTransaction();
		
		/*********************searching dimensions**********************/
		String req1="Select distinct table_name from information_schema.columns WHERE table_schema = 'public'";
		List<String> res = (List<String>) session.createSQLQuery(req1).list();
		HashMap<String, List<FacetForm>> dimensions = new HashMap<String, List<FacetForm>>();
		List<FacetForm> temp1 = new ArrayList<FacetForm>();
		for (String Table : res) {
       if(!Table.equals("structure")&& !Table.equals(fact_Table) ){
			List<String> att1 = (List<String>) session
					.createSQLQuery("SELECT column_name FROM information_schema.columns WHERE table_schema = 'public'  AND table_name ='"+Table+"' ").list();

			
			Iterator<String> elt1 = att1.iterator();

			while (elt1.hasNext()) {
			    String str1 = elt1.next();
			{  if(att1.hashCode()!=0)
				{ FacetForm f1 = new FacetForm(Table, String.valueOf(str1));  
				temp1.add(f1);}
			
			}
			}
		
		}}
		
	
		
		
		/*************************************************************/

	
		
		
		HashMap<String, List<FacetForm>> distincts = new HashMap<String, List<FacetForm>>();
		
		for (String item : columns) {

			List<String> att = (List<String>) session
					.createSQLQuery("select distinct CAST (" + item + " AS varchar) from "+fact_Table+" where " + item + " IS NOT NULL").list();

			List<FacetForm> temp = new ArrayList<FacetForm>();
			Iterator<String> elt = att.iterator();

			while (elt.hasNext()) {
			    String str = elt.next();
			{  if(att.hashCode()!=0)
				{ FacetForm f1 = new FacetForm(item, String.valueOf(str));  
				temp.add(f1);}
			
			}}
		
			
			distincts.put(item, temp);

		}

		session.close();
		//System.out.println("*************** ooooooooooooooo***************");
		
		
		/************announcement of beginning database storage of queries/ visualization**************/
		if(this.connector==null)
		{	this.connector  = new DBConnector(true); }
		
		
	
		ss.setAttribute("dimensions", temp1);
		ss.setAttribute("attList", distincts);
		ss.setAttribute("DB", DBobj);
		ss.setAttribute("listDB", lis_db);
		return mapping.findForward(SUCCESS);
	}

}
