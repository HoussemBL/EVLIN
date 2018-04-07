package prov.util;


import org.hibernate.*;

import java.util.*;

import prov.*;

public class inserer {

	 public static void main(String[] args)
		throws HibernateException {
		 
		 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		 session.beginTransaction();
		 	
		
		String	log="ali1990" ; 
		
		 List<String>  list = (List) session.createQuery(" from Inscription where login='"+log+"'").list();
		
		if(! list.isEmpty()) {
			
			System.out.print("vrai");
		}	
		 session.getTransaction().commit();
		HibernateUtil.getSessionFactory().close();	
		 
	 }
}
