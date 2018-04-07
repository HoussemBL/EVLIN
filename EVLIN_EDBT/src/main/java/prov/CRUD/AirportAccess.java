package prov.CRUD;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.*;

import prov.dao.Airports;
import prov.dao.AirportsId;
import prov.*;
import prov.util.HibernateUtil;;

public class AirportAccess {
	private String iata;
	private String airport;
	private String city;
	private String state;
	private String country;
	private BigDecimal lat;
	private BigDecimal longi;

	public List<AirportsId> getAirport() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List list = (List) session.createQuery("from Airports").list();
		System.out.println(list.size());
		Iterator it = list.iterator();

		List<AirportsId> maListe = new ArrayList<AirportsId>();

		while (it.hasNext()) {
			Airports ff = (Airports) it.next();
		//	System.out.println(ff.getId().getState());

			maListe.add(ff.getId());

		}

		HibernateUtil.getSessionFactory().close();

		return maListe.subList(0, 10);
	}

}
