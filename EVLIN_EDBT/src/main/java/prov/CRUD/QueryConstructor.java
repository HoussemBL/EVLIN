package prov.CRUD;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javassist.runtime.Desc;
import prov.dao.Carriers;
import prov.mybean.QueryForm;

public class QueryConstructor {

	private final static List<List<String>> recomendedjoin = new ArrayList(Arrays.asList(
			Arrays.asList("Flights", "Airports"), Arrays.asList("Flights", "Airports", "Airports"),
			Arrays.asList("Flights", "Airports", "Tail"), Arrays.asList("Flights", "Tail"),
			Arrays.asList("Flights", "Airports", "Carriers"), Arrays.asList("Flights", "Carriers"),
			Arrays.asList("Flights", "Carriers", "Tail"), Arrays.asList("Flights", "Airports", "Carriers", "Tail"),
			Arrays.asList("Flights", "Airports", "Airports", "Carriers"),
			Arrays.asList("Flights", "Airports", "Airports", "Tail"),
			Arrays.asList("Flights", "Airports", "Airports", "Tail", "Carriers"), Arrays.asList("Flights", "Airports"),
			Arrays.asList("Flights", "Airports", "Tail"), Arrays.asList("Flights", "Airports", "Carriers")

	));

	private static final Map<String, String> dep1 = new HashMap<String, String>();

	static {

		dep1.put("Flights.origin", "Airports.iata");
	}

	private static final Map<String, String> dep2 = new HashMap<String, String>();

	static {

		dep2.put("Flights.origin", "Airports.iata");
		dep2.put("Flights.dest", "Airports.iata");
	}

	private static final Map<String, String> dep3 = new HashMap<String, String>();

	static {
		dep3.put("Flights.origin", "Airports.iata");
		dep3.put("Flights.tailnum", "Tail.tailnum");
		// dep3.put("Flights.uniquecarrier", "Carriers.code");

	}

	private static final Map<String, String> dep4 = new HashMap<String, String>();

	static {

		dep4.put("Flights.tailnum", "Tail.tailnum");

	}

	private static final Map<String, String> dep5 = new HashMap<String, String>();

	static {

		dep5.put("Flights.origin", "Airports.iata");
		dep5.put("Flights.uniquecarrier", "Carriers.code");
	}

	private static final Map<String, String> dep6 = new HashMap<String, String>();

	static {

		dep6.put("Flights.uniquecarrier", "Carriers.code");
	}

	private static final Map<String, String> dep7 = new HashMap<String, String>();

	static {

		dep7.put("Flights.uniquecarrier", "Carriers.code");
		dep7.put("Flights.tailnum", "Tail.tailnum");
	}

	private static final Map<String, String> dep8 = new HashMap<String, String>();

	static {

		dep8.put("Flights.uniquecarrier", "Carriers.code");
		dep8.put("Flights.tailnum", "Tail.tailnum");
		dep8.put("Flights.origin", "Airports.iata");
	}

	private static final Map<String, String> dep9 = new HashMap<String, String>();

	static {

		dep9.put("Flights.origin", "Airports.iata");
		dep9.put("Flights.dest", "Airports.iata");
		dep9.put("Flights.uniquecarrier", "Carriers.code");
	}

	private static final Map<String, String> dep10 = new HashMap<String, String>();

	static {

		dep10.put("Flights.origin", "Airports.iata");
		dep10.put("Flights.dest", "Airports.iata");
		dep10.put("Flights.tailnum", "Tail.tailnum");
	}

	private static final Map<String, String> dep11 = new HashMap<String, String>();

	static {

		dep11.put("Flights.origin", "Airports.iata");
		dep11.put("Flights.dest", "Airports.iata");
		dep11.put("Flights.tailnum", "Tail.tailnum");
		dep11.put("Flights.uniquecarrier", "Carriers.code");
	}

	private static final Map<String, String> dep12 = new HashMap<String, String>();

	static {

		dep12.put("Flights.dest", "Airports.iata");
	}

	private static final Map<String, String> dep13 = new HashMap<String, String>();

	static {
		dep13.put("Flights.dest", "Airports.iata");
		dep13.put("Flights.tailnum", "Tail.tailnum");
		// dep3.put("Flights.uniquecarrier", "Carriers.code");

	}

	private static final Map<String, String> dep14 = new HashMap<String, String>();

	static {

		dep14.put("Flights.dest", "Airports.iata");
		dep14.put("Flights.uniquecarrier", "Carriers.code");
	}

	private final static List<String> airAtts = new ArrayList(
			Arrays.asList("state", "city", "airport", "iata", "lat", "longi"));
	private final static List<String> tailAtts = new ArrayList(
			Arrays.asList("model", "manufacturer", "issue_date", "engine_type", "year_t","aircraft_type","status"));
	private final static List<String> carrierAtts = new ArrayList(Arrays.asList("code", "description"));
	private final static List<Map<String, String>> dependencies = new ArrayList(
			Arrays.asList(dep1, dep2, dep3, dep4, dep5, dep6, dep7, dep8, dep9, dep10, dep11, dep12, dep13, dep14));

	private final static List<String> flightsAtts = new ArrayList(Arrays.asList("year", "month", "dayofmonth",
			"dayofweek", "deptime", "crsdepdtime", "arrtime", "crsarrtime", "uniquecarrier", "flightnum", "tailnum",
			"actualelapsedtime", "crselapsedtime", "airtime", "arrdelay", "depdelay", "origin", "dest", "distance",
			"taxiin", "taxiout", "cancelled", "cancellationcode", "diverted", "carrierdelay", "weatherdelay",
			"nasdelay", "securitydelay", "lateaircraftdelay"));

	QueryForm queryBean;
	String relevants;

	public QueryConstructor(QueryForm query, String ss) {
		super();
		this.queryBean = new QueryForm(query.getSelectedAtt(), query.getCondAtt(), query.getTables(),
				query.getGroupbyAtt(), query.getOrderbyAtt(), query.getJoinTables());
		this.relevants = ss;
	}

	public QueryConstructor() {
	
	}
	public QueryForm getQueryBean() {
		return queryBean;
	}


	public void setQueryBean(QueryForm queryBean) {
		this.queryBean = queryBean;
	}


	public String getRelevants() {
		return relevants;
	}


	public void setRelevants(String relevants) {
		this.relevants = relevants;
	}
	
	/**** extend query by going to other dimensiosn ***/
	public List<QueryForm> Extend() {
		List<QueryForm> qrs = new ArrayList<QueryForm>();

		QueryForm que = Formalize();

		List<String> tables = new ArrayList<String>();
		tables.addAll(que.getTables());

		List<Integer> ext = searchJoinV1(tables, recomendedjoin);

		/******************
		 * detection of duplication of tables
		 ******************/

		for (Integer index : ext) {
			List<String> NewTable = recomendedjoin.get(index);
			boolean duplica = false;
			QueryForm tok = new QueryForm();
			tok.setCondAtt(que.getCondAtt());
			tok.setSelectedAtt(que.getSelectedAtt());
			tok.setTables(que.getTables());
			tok.setGroupbyAtt(que.getGroupbyAtt());
			tok.setOrderbyAtt(que.getOrderbyAtt());
			tok.setJoinTables(que.getJoinTables());

			Set<String> duplicat = new HashSet<String>(NewTable);

			if (duplicat.size() < NewTable.size()) {
				duplica = true;
			}
			Map<String, String> dep = dependencies
					.get(index);

			if (duplica) {
				List<String> setAtt = UpdateAtts(tok.getSelectedAtt());
				List<String> inter = new ArrayList<String>(setAtt);
				List<String> setAtt1 = AddAtts(setAtt, NewTable, tables);
				tok.setSelectedAtt(setAtt1);

				Map<String, String> dep1 = UpdateMap(dep, queryBean);

				Map<String, String> conds = UpdateConds(tok.getCondAtt());
				List<String> grpSet = AddGroupby(inter, setAtt1);

				grpSet.addAll(tok.getGroupbyAtt());
				tok.setJoinTables(dep1);

				tok.setCondAtt(conds);
				tok.setGroupbyAtt(grpSet);
				tok.setTables(NewTable);
				qrs.add(tok);

			} else {
				List<String> oldAtts = new ArrayList<String>(tok.getSelectedAtt());
				List<String> setAtt1 = AddAtts1(oldAtts, NewTable, tables);

				List<String> grpSet = AddGroupby(tok.getSelectedAtt(), setAtt1);

				tok.setSelectedAtt(setAtt1);
				grpSet.addAll(tok.getGroupbyAtt());
				tok.setJoinTables(dep);
				tok.setTables(NewTable);
				tok.setGroupbyAtt(grpSet);
				qrs.add(tok);
			}

		}

		return qrs;

	}

	/******** extend the query by adding new dimension *********/
	public QueryForm ExtendV2(QueryForm query, String webVal, String webField) {

		Map<String, String> res = new HashMap<String, String>();
		String inter1 = "";
		String discardField = null;

		for (Map.Entry<String, String> entry : query.getCondAtt().entrySet()) {
			String[] inter = entry.getKey().split(Pattern.quote("."));
if(webField.split(Pattern.quote(".")).length==1)
			{if (inter.length > 1) {
				inter1 = inter[1];
			} else {
				inter1 = inter[0];
			}}
else{	inter1=entry.getKey();}
			if (inter1.contains("range,") || inter1.contains("not,")) {
				String rr = inter1.split(",")[1];

				inter1 = rr;
			}
			//if(inter1.equals("code")) {inter1="uniquecarrier";}
			
			 if (!(inter1.equals(webField)) || !(entry.getValue().equals(webVal))) {
				res.put(entry.getKey(), entry.getValue());

			} else {
				if (inter1.equals("code") && webField.equals("uniquecarrier")  ) {
					//res.put(entry.getKey(), entry.getValue());
					discardField = entry.getKey();
				}
				else if (inter1.equals("uniquecarrier") && webField.equals("code")  ) {
					//res.put(entry.getKey(), entry.getValue());
					discardField = entry.getKey();
				}
				
				
				else if (entry.getKey().contains("range,") || entry.getKey().contains("not,")) {
					discardField = entry.getKey().split(",")[1];
				} else
					discardField = entry.getKey();
			}
		}

		// System.out.println( " @@@@ discraded "+ discardField);
		List<String> grpset = new ArrayList<String>();
		grpset.addAll(query.getGroupbyAtt());
		if(discardField!=null)
		{grpset.add(discardField);}

		List<String> selectset = query.getSelectedAtt();
		if(discardField!=null){selectset.add(discardField);}

		Map<String, String> order = new HashMap<String, String>();
		order.putAll(query.getOrderbyAtt());
		if(discardField!=null)
		{
		order.put(discardField, "desc");}

		query.setOrderbyAtt(order);
		query.setCondAtt(res);
		query.setGroupbyAtt(grpset);
		query.setSelectedAtt(selectset);
		return query;
	}

	public List<QueryForm> ExtendV3(QueryForm que, QueryForm que2) {

		boolean duplica = false;

		List<String> tables = new ArrayList<String>();
		tables.addAll(que.getTables());
		List</* List<String> */Integer> suggestions = searchJoinV1(tables, recomendedjoin);
		/******************
		 * detection of duplication of tables
		 ******************/
		List<QueryForm> couple = new ArrayList<QueryForm>();
		for (/* List<String> */Integer index : suggestions) {
			List<String> res = recomendedjoin.get(index);
			QueryForm subquery1 = new QueryForm();
			subquery1.setCondAtt(que.getCondAtt());
			subquery1.setSelectedAtt(que.getSelectedAtt());
			subquery1.setTables(que.getTables());
			subquery1.setGroupbyAtt(que.getGroupbyAtt());
			subquery1.setOrderbyAtt(que.getOrderbyAtt());
			subquery1.setJoinTables(que.getJoinTables());

			QueryForm subquery2 = new QueryForm();
			subquery2.setCondAtt(que2.getCondAtt());
			subquery2.setSelectedAtt(que2.getSelectedAtt());
			subquery2.setTables(que2.getTables());
			subquery2.setGroupbyAtt(que2.getGroupbyAtt());
			subquery2.setOrderbyAtt(que2.getOrderbyAtt());
			subquery2.setJoinTables(que2.getJoinTables());

			Set<String> duplicat = new HashSet<String>(res);

			if (duplicat.size() < res.size()) {
				duplica = true;
			}

			if (duplica) {
				Map<String, String> dep = dependencies
						.get(/* recomendedjoin.indexOf(res) */index);
				Map<String, String> dep1 = UpdateMap(dep, queryBean);

				List<String> setAtt = UpdateAtts(subquery1.getSelectedAtt());

				List<String> inter = new ArrayList<String>(setAtt);
				List<String> setAtt1 = AddAtts(setAtt, res, tables);

				/********
				 * figure out which attribute is added as extension and add it
				 * to order by and groupby
				 *********/

				List<String> oldstf = UpdateGroupBy(subquery1.getGroupbyAtt());

				List<String> grpSet = AddGroupby(inter, setAtt1);
				Map<String, String> init = new HashMap<String, String>();

				for (String elt : grpSet) {
					init.put(elt, "desc");
				}
				grpSet.addAll(oldstf);
				init.putAll(subquery1.getOrderbyAtt());

				Map<String, String> conds = UpdateConds(subquery1.getCondAtt());
				Map<String, String> conds2 = UpdateConds(subquery2.getCondAtt());

				subquery1.setJoinTables(dep1);
				subquery2.setJoinTables(dep1);

				subquery1.setGroupbyAtt(grpSet);
				subquery2.setGroupbyAtt(grpSet);
				subquery1.setTables(res);
				subquery2.setTables(res);

				subquery1.setCondAtt(conds);
				subquery2.setCondAtt(conds2);
				subquery1.setSelectedAtt(setAtt1);
				subquery2.setSelectedAtt(setAtt1);
				subquery1.setOrderbyAtt(init);
				subquery2.setOrderbyAtt(init);

			} else {
				Map<String, String> dep = dependencies
						.get(/* recomendedjoin.indexOf(res) */index);

				List<String> inter = new ArrayList<String>(subquery1.getSelectedAtt());
				List<String> setAtt1 = AddAtts1(inter, res, tables);

				List<String> grpSet = AddGroupby(subquery1.getSelectedAtt(), setAtt1);
				Map<String, String> init = new HashMap<String, String>();

				for (String elt : grpSet) {
					init.put(elt, "desc");
				}

				init.putAll(subquery1.getOrderbyAtt());
				grpSet.addAll(subquery1.getGroupbyAtt());
				subquery1.setTables(res);
				subquery2.setTables(res);
				subquery1.setJoinTables(dep);
				subquery2.setJoinTables(dep);
				subquery1.setSelectedAtt(setAtt1);
				subquery2.setSelectedAtt(setAtt1);
				subquery1.setGroupbyAtt(grpSet);
				subquery2.setGroupbyAtt(grpSet);
				subquery1.setOrderbyAtt(init);
				subquery2.setOrderbyAtt(init);

			}

			couple.add(subquery1);
			couple.add(subquery2);
			duplica = false;

		}
		return couple;

	}

	/****************
	 * when we extend query we add the new selected attribute to group by part
	 *********************/
	private List<String> AddGroupby(List<String> setAtt, List<String> setAtt1) {
		List<String> res = new ArrayList<String>();
		Collection<String> similar = new HashSet<String>(setAtt);
		Collection<String> different = new HashSet<String>();
		different.addAll(setAtt);
		different.addAll(setAtt1);

		similar.retainAll(setAtt1);
		different.removeAll(similar);

		for (String att : different) {
			res.add(att);

		}

		return res;
	}

	/*******
	 * add randomly attributes with taking into account redundancy
	 **********/
	private List<String> AddAtts(List<String> setAtt, List<String> res, List<String> tables) {
		Random rand = new Random();
		int n = 0;
		List<String> similar = new ArrayList<String>();
		List<String> different = new ArrayList<String>();
		different.addAll(res);
		different.addAll(tables);
		similar.addAll(res);
		similar.retainAll(tables);
		different.removeAll(similar);
		if (different.isEmpty()) {

			/*
			 * n = rand.nextInt(3) + 0; setAtt.add("A2." + airAtts.get(n));
			 */
			setAtt.add("A2.state");
		} else {
			for (String table : different) {
				if (table.equals("Carriers")) {
					/*
					 * n = rand.nextInt(1) + 0; setAtt.add(carrierAtts.get(n));
					 */
					setAtt.add("code");
				} else {
					/*
					 * n = rand.nextInt(3) + 0; setAtt.add(tailAtts.get(n));
					 */
					setAtt.add("manufacturer");
				}
			}
		}

		return setAtt;
	}

	/*********** add attributes without redundancy ***********/

	private List<String> AddAtts1(List<String> setAtt, List<String> res, List<String> tables) {
		Random rand = new Random();
		int n = 0;
		List<String> similar = new ArrayList<String>();
		List<String> different = new ArrayList<String>();
		different.addAll(res);
		different.addAll(tables);
		similar.addAll(res);
		similar.retainAll(tables);
		different.removeAll(similar);
		if (different.isEmpty()) {

			n = rand.nextInt(3) + 0;
			setAtt.add(airAtts.get(n));
		} else {
			for (String table : different) {
				if (table.equals("Carriers")) {
					/*
					 * n = rand.nextInt(1) + 0; setAtt.add(carrierAtts.get(n));
					 */
					setAtt.add("code");

				} else if (table.equals("Airports")) {
					// n = rand.nextInt(3) + 0;
					setAtt.add("state");
				} else {
					// n = rand.nextInt(3) + 0;
					setAtt.add("manufacturer");
				}
			}
		}

		return setAtt;
	}

	public Map<String, String> UpdateConds(Map<String, String> condAtt) {
		Map<String, String> res = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : condAtt.entrySet()) {

			if (entry.getKey().startsWith("not,")) {
				if (airAtts.contains(entry.getKey().split(",")[1])) {
					res.put("not, A1." + entry.getKey().split(",")[1], entry.getValue());
				} else
					res.put(entry.getKey(), entry.getValue());
			} else {
				if (airAtts.contains(entry.getKey())) {
					
					res.put("A1." + entry.getKey(), entry.getValue());
					
				} else
					res.put(entry.getKey(), entry.getValue());

			}
		}
		return res;
	}

	private List<String> UpdateGroupBy(List<String> grp) {
		List<String> res = new ArrayList<String>();
		for (String entry : grp) {
			if (airAtts.contains(entry)) {
				res.add("A1." + entry);
			} else
				res.add(entry);

		}
		return res;
	}

	// updat atts names in case of redundancy
	private List<String> UpdateAtts(List<String> setatts) {

		List<String> res = new ArrayList<String>();
		for (String att : setatts) {
			if (airAtts.contains(att))
				res.add("A1." + att);
			else
				res.add(att);

		}
		return res;
	}

	// update conditions attributes in case of redundancy of tables like two
	// tables airports
	// it contains many cases since we compare key to key and key to values
	private Map<String, String> UpdateMap(Map<String, String> dep, QueryForm que) {

		Map<String, String> res = new HashMap<String, String>();

		for (Map.Entry<String, String> entry : dep.entrySet()) {
			if (que.getJoinTables().containsKey(entry.getKey())
					&& que.getJoinTables().get(entry.getKey()).equals(entry.getValue())) {
				if (entry.getKey().contains("Airports")) {
					String[] res1 = entry.getKey().split(Pattern.quote("."));

					res.put("A1." + res1[1], entry.getValue());

				} else if (entry.getValue().contains("Airports")) {
					String[] res1 = entry.getValue().split(Pattern.quote("."));

					res.put(entry.getKey(), "A1." + res1[1]);

				} else {
					res.put(entry.getKey(), entry.getValue());
				}

			}

			else if (que.getJoinTables().containsKey(entry.getKey().split(Pattern.quote("."))[1])
					&& que.getJoinTables().get(entry.getKey()).equals(entry.getValue().split(Pattern.quote("."))[1])) {
				if (entry.getKey().contains("Airports")) {
					String[] res1 = entry.getKey().split(Pattern.quote("."));

					res.put("A1." + res1[1], entry.getValue());

				} else if (entry.getValue().contains("Airports")) {
					String[] res1 = entry.getValue().split(Pattern.quote("."));

					res.put(entry.getKey(), "A1." + res1[1]);

				} else {
					res.put(entry.getKey(), entry.getValue());
				}

			}

			// search in values not key
			else if (que.getJoinTables().containsValue(entry.getKey())) {
				String key = "";
				for (Entry<String, String> cds : que.getJoinTables().entrySet()) {
					if (cds.getValue().equals(entry.getKey())) {
						key = cds.getKey();
					}
				}
				if (key.equals(entry.getValue())) {
					if (entry.getKey().contains("Airports")) {
						String[] res1 = entry.getKey().split(Pattern.quote("."));

						res.put("A1." + res1[1], entry.getValue());

					} else if (entry.getValue().contains("Airports")) {
						String[] res1 = entry.getValue().split(Pattern.quote("."));

						res.put(entry.getKey(), "A1." + res1[1]);

					}
				}
			}

			else if (que.getJoinTables().containsValue(entry.getKey().split(Pattern.quote("."))[1])) {
				String key = "";
				for (Entry<String, String> cds : que.getJoinTables().entrySet()) {
					if (cds.getValue().equals(entry.getKey().split(Pattern.quote("."))[1])) {
						key = cds.getKey();
					}
				}

				if (key.equals(entry.getValue().split(Pattern.quote("."))[1])) {
					if (entry.getKey().contains("Airports")) {
						String[] res1 = entry.getKey().split(Pattern.quote("."));

						res.put("A1." + res1[1], entry.getValue());

					} else if (entry.getValue().contains("Airports")) {
						String[] res1 = entry.getValue().split(Pattern.quote("."));

						res.put(entry.getKey(), "A1." + res1[1]);

					}
				}
			}

			else {

				if (entry.getKey().contains("Airports")) {
					String[] res1 = entry.getKey().split(Pattern.quote("."));

					res.put("A2." + res1[1], entry.getValue());

				} else if (entry.getValue().contains("Airports")) {
					String[] res1 = entry.getValue().split(Pattern.quote("."));

					res.put(entry.getKey(), "A2." + res1[1]);

				} else {
					res.put(entry.getKey(), entry.getValue());
				}
			}
		}

		return res;
	}

	public QueryForm Formalize() {
		Boolean duplica = false;
		QueryForm query = new QueryForm();
		String[] res = relevants.split(",");

		Map<String, String> conds = new HashMap<String, String>();

		for (Map.Entry<String, String> entry : queryBean.getCondAtt().entrySet()) {
			// System.out.println( " @@@@- "+entry.getKey());
			String elt = "";
			if (entry.getKey().contains("$")) {
				elt = entry.getKey().split("\\$")[1];
			} else if (entry.getKey().contains(",")) {
				elt = entry.getKey().split(",")[1];
			} else
				elt = entry.getKey();

			if (!elt.equals(res[0])) {
				conds.put(entry.getKey(), entry.getValue());
			}

		}
		if (res.length == 2) {

			if (res[1].contains("&") && res[1].split("&")[1].matches("-?\\d+(\\.\\d+)?")) {

				conds.put("range," + res[0], res[1]);
			} else {
				conds.put(res[0], res[1]);
			}
			List<String> tables = new ArrayList<String>();
			tables.addAll(queryBean.getTables());
			Map<String, String> joins = new HashMap<String, String>();
			joins.putAll(queryBean.getJoinTables());

			List<String> select = new ArrayList<String>();
			select.addAll(queryBean.getSelectedAtt());
			query.setSelectedAtt(select);
			query.setJoinTables(joins);
			query.setTables(tables);

			query.setCondAtt(conds);
			query.setGroupbyAtt(queryBean.getGroupbyAtt());
			query.setOrderbyAtt(queryBean.getOrderbyAtt());

		}
		// relevant attribute has more one relevant value
		else {
			if (relevants.contains("&") && relevants.split("&")[1].split(",")[0].matches("-?\\d+(\\.\\d+)?")) {
				List<String> select = new ArrayList<String>();
				select.addAll(queryBean.getSelectedAtt());
				query.setSelectedAtt(select);
				query.setTables(queryBean.getTables());
				query.setJoinTables(queryBean.getJoinTables());

				if (queryBean.getCondAtt().keySet().contains(res[0])) {
					conds.remove(res[0]);
				}

				String vals = "";
				for (int k = 1; k < res.length; k++) {
					if (res[k].contains("&"))

					{
						vals = vals + res[k] + ",";
					}
				}
				vals = vals.substring(0, vals.length() - 1).trim();
				conds.put("range," + res[0], vals);

				query.setCondAtt(conds);
				List<String> aa = new ArrayList<String>();
				aa.addAll(queryBean.getGroupbyAtt());

				query.setGroupbyAtt(aa);
				query.setOrderbyAtt(queryBean.getOrderbyAtt());
			} else {

				String vals = "";
				for (int k = 1; k < res.length; k++) {
					vals = vals + res[k] + ",";
				}

				vals = vals.substring(0, vals.length() - 1).trim();
				conds.put(res[0], vals);

				query.setCondAtt(conds);
				List<String> aa = new ArrayList<String>();
				aa.addAll(queryBean.getGroupbyAtt());
				query.setGroupbyAtt(aa);
				query.setOrderbyAtt(queryBean.getOrderbyAtt());

				List<String> select = new ArrayList<String>();
				select.addAll(queryBean.getSelectedAtt());
				List<String> tables = new ArrayList<String>();
				tables.addAll(queryBean.getTables());
				Map<String, String> joins = new HashMap<String, String>();
				joins.putAll(queryBean.getJoinTables());
				query.setSelectedAtt(select);
				query.setJoinTables(joins);
				query.setTables(tables);

			}

		}

		return query;

	}

	/*******
	 * similar to searchjoin, it returns two proposition of extension
	 ***********/
	private /* List<List<String>> */List<Integer> searchJoinV1(List<String> tables,
			List<List<String>> recomendedjoin2) {
		List<List<String>> badil = new ArrayList<List<String>>();
		List<Integer> result = new ArrayList<Integer>();
		badil.addAll(recomendedjoin2);
		List<List<String>> aa = new ArrayList<List<String>>();
		Integer index = 0;

		int i = 0;
		Integer badilnum = 0;
		Iterator<List<String>> iter = badil.iterator();

		while (iter.hasNext() && i < 3) {
			List<String> entry = iter.next();

			Collection<String> similar = new HashSet<String>(tables);
			Collection<String> consta = new HashSet<String>(tables);

			similar.retainAll(entry);

			if (similar.size() == consta.size() && tables.contains("Airports")
					&& Collections.frequency(entry, "Airports") >= Collections.frequency(tables, "Airports")
					&& tables.size() < entry.size()) {
				/***** important condition proposed here ******/
				if (tables.size() + 1 == entry.size()) {
					aa.add(entry);

					index = /* badil.indexOf(entry) */badilnum;
					result.add(index);
					i++;
				}
			}

			else if (similar.size() == consta.size() && !tables.contains("Airports") && tables.size() < entry.size()) {
				/***** important condition proposed here ******/
				if (tables.size() + 1 == entry.size()) {
					aa.add(entry);

					index = /* badil.indexOf(entry) */badilnum;
					result.add(index);
					i++;
				}
			}

			badilnum++;
			similar.clear();

		}

		return /* aa */ result;
	}

	// adding some semantic for these 2 special subqueries

	public QueryForm FormalizeV4(QueryForm query, String webVal, String webField, int i) {

		Map<String, String> res = new HashMap<String, String>();
		res.putAll(query.getCondAtt());

		String inter1 = "";
		String discardField = "";

		List<String> grpset = new ArrayList<String>();
		grpset.addAll(query.getGroupbyAtt());
		// grpset.add(discardField);

		List<String> selectset = new ArrayList<String>();
		// selectset.addAll(query.getSelectedAtt());

		for (String att : query.getSelectedAtt())

		{
			if (att.contains("count")) {
				if (i == 1)
					selectset.add(att + " as relevant_values");
				else
					selectset.add(att + " as remaining_values");
			}

			else if (att.contains(",")) {
				selectset.add("'" + att.split(",")[1] + "' as " + att.split(",")[1]);
			}

			else {
				selectset.add(att);
			}

		}

		// selectset.add(discardField+" as var"+i);
		query.setCondAtt(res);
		query.setSelectedAtt(selectset);
		query.setGroupbyAtt(grpset);

		return query;
	}

	public QueryForm formalizeV2() {
		QueryForm query = new QueryForm();
		String[] res = relevants.split(",");

		Map<String, String> conds = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : queryBean.getCondAtt().entrySet()) {
			String elt = "";
			if (entry.getKey().contains("$")) {
				elt = entry.getKey().split("\\$")[1];
			} else if (entry.getKey().contains(",")) {
				elt = entry.getKey().split(",")[1];
			} else
				elt = entry.getKey();
			if (!elt.equals(res[0])) {
				conds.put(entry.getKey(), entry.getValue());
			}

		}

		if (res.length == 2) {

			if (res[1].contains("&")) {

				conds.put("NotRange," + res[0], res[1]);
			} else
				conds.put("not," + res[0], res[1]);
			query.setCondAtt(conds);

		} else {

			if (!relevants.contains("&")) {

				String vals = "";
				for (int k = 1; k < res.length; k++) {
					vals = vals + res[k] + ",";
				}
				vals = vals.substring(0, vals.length() - 1).trim();
				conds.put("not," + res[0], vals);

				query.setCondAtt(conds);
			}

			else {

				String vals = "";
				for (int k = 1; k < res.length; k++) {
					if (res[k].contains("&")) {
						vals = vals + res[k] + ",";
					}

				}
				vals = vals.substring(0, vals.length() - 1).trim();
				conds.put("NotRange," + res[0], vals);

				query.setCondAtt(conds);
			}

		}

		List<String> select = new ArrayList<String>();
		select.addAll(queryBean.getSelectedAtt());
		select.add(res[0]);
		query.setSelectedAtt(select);
		query.setTables(queryBean.getTables());
		query.setJoinTables(queryBean.getJoinTables());

		List<String> aa = new ArrayList<String>();
		aa.addAll(queryBean.getGroupbyAtt());
		aa.add(res[0]);

		query.setGroupbyAtt(aa);
		query.setOrderbyAtt(queryBean.getOrderbyAtt());

		return query;
	}

	// similar to formalizeV2 expect we don't add not in for relevant
	// information to select
	// and group by parts
	public QueryForm formalizeV22() {
		QueryForm query = new QueryForm();
		String[] res = relevants.split(",");

		Map<String, String> conds = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : queryBean.getCondAtt().entrySet()) {
			String elt = "";
			if (entry.getKey().contains("$")) {
				elt = entry.getKey().split("\\$")[1];
			} else if (entry.getKey().contains(",")) {
				elt = entry.getKey().split(",")[1];
			} else
				elt = entry.getKey();
			if (!elt.equals(res[0])) {
				conds.put(entry.getKey(), entry.getValue());
			}

		}
		List<String> tables = new ArrayList<String>();
		tables.addAll(queryBean.getTables());
		Map<String, String> joins = new HashMap<String, String>();
		joins.putAll(queryBean.getJoinTables());

		if (res.length == 2) {
			if (res[0].contains("dest") && !queryBean.checkConds("dest")) {

				tables.add("Airports");
				tables = IdentifyTables(tables);

				joins = dependencies.get(recomendedjoin.indexOf(tables));

			}
			if (res[1].contains("&") && res[1].split("&")[0].matches("-?\\d+(\\.\\d+)?")) {

				conds.put("NotRange," + res[0], res[1]);
			} else
				conds.put("not," + res[0], res[1]);
			query.setCondAtt(conds);

		} else {
			if (res[0].contains("dest") && !queryBean.checkConds("dest")) {

				tables.add("Airports");
				tables = IdentifyTables(tables);

				joins = dependencies.get(recomendedjoin.indexOf(tables));

			}

			if (!relevants.contains("&")) {

				String vals = "";
				for (int k = 1; k < res.length; k++) {
					vals = vals + res[k] + ",";
				}
				vals = vals.substring(0, vals.length() - 1).trim();
				conds.put("not," + res[0], vals);

				query.setCondAtt(conds);
			} else if (relevants.contains("&") && !relevants.split("&")[1].split(",")[0].matches("-?\\d+(\\.\\d+)?")) {
				String vals = "";
				for (int k = 1; k < res.length; k++) {
					vals = vals + res[k] + ",";
				}
				vals = vals.substring(0, vals.length() - 1).trim();
				conds.put("not," + res[0], vals);

				query.setCondAtt(conds);
			} else {

				String vals = "";
				for (int k = 1; k < res.length; k++) {
					if (res[k].contains("&")) {
						vals = vals + res[k] + ",";
					}

				}
				vals = vals.substring(0, vals.length() - 1).trim();
				conds.put("NotRange," + res[0], vals);

				query.setCondAtt(conds);
			}

		}

		List<String> select = new ArrayList<String>();
		select.addAll(queryBean.getSelectedAtt());
		// select.add(res[0]);
		query.setSelectedAtt(select);
		query.setTables(tables);
		query.setJoinTables(joins);

		List<String> aa = new ArrayList<String>();
		aa.addAll(queryBean.getGroupbyAtt());
		// aa.add(res[0]);

		query.setGroupbyAtt(aa);
		query.setOrderbyAtt(queryBean.getOrderbyAtt());

		return query;
	}

	// remove the relevant attribute from the query and replace it with a
	// constant
	public QueryForm updateV2(QueryForm query) {
		String[] res = this.relevants.split(",");
		boolean detect = false;
		int index = 0;
		String elt = "";
		List<String> select = new ArrayList<String>();
		select.addAll(query.getSelectedAtt());
		for (String att : query.getSelectedAtt()) {
			if (att.contains(res[0])) {
				index = query.getSelectedAtt().indexOf(att);
				elt = att;
				detect = true;
				break;
			}
		}
		if (detect) {
			select.remove(index);
			select.add(res[0] + "," + res[0]);

			List<String> aa = new ArrayList<String>();
			aa.addAll(query.getGroupbyAtt());
			aa.remove(elt);

			query.setSelectedAtt(select);
			query.setGroupbyAtt(aa);
		}

		return query;
	}

	// similar to formalizeV2 expect we don't add relevant att to conds
	public QueryForm formalizeV3(String cancelVal) {
		QueryForm query = new QueryForm();
		String[] res = relevants.split(",");

		query.setTables(queryBean.getTables());
		query.setJoinTables(queryBean.getJoinTables());

		Map<String, String> conds = new HashMap<String, String>();
		conds.putAll(queryBean.getCondAtt());

		query.setCondAtt(conds);
		List<String> aa = new ArrayList<String>();
		aa.addAll(queryBean.getGroupbyAtt());
		aa.add(res[0]);

		query.setGroupbyAtt(aa);

		List<String> select = new ArrayList<String>();
		select.addAll(queryBean.getSelectedAtt());

		String item = "";
		for (String elt : queryBean.getSelectedAtt()) {
			if (elt.toLowerCase().contains("count")) {
				item = elt;
				break;
			} else if (elt.toLowerCase().contains("max")) {
				item = elt;
				break;
			} else if (elt.toLowerCase().contains("min")) {
				item = elt;
				break;
			} else if (elt.toLowerCase().contains("avg")) {
				item = elt;
				break;
			} else if (elt.toLowerCase().contains("sum")) {
				item = elt;
				break;
			}

		}
		select.remove(item);
		select.add("CAST ( round((count(*)/" + cancelVal + "\\:\\:float)*100) as varchar)");
		select.add("corr(1,1)");
		select.add(res[0]);
		query.setSelectedAtt(select);

		Map<String, String> grps = new HashMap<String, String>();
		grps.putAll(queryBean.getOrderbyAtt());
		grps.put(res[0], "desc");
		query.setOrderbyAtt(grps);

		return query;
	}

	public Map<String, String> GetJoinConds(Map<String, String> conds) {
		List<Integer> possibleindex = new ArrayList<Integer>();
		boolean find = false;
		Map<String, String> dep = queryBean.getJoinTables();
		int index = 0;
		for (List<String> elt : recomendedjoin) {
			find = false;
			/*
			 * if (queryBean.getTables().size() == elt.size()) { for (String tab
			 * : queryBean.getTables()){ if (!elt.contains(tab)) { find = false;
			 * break; } else { find = true; index = recomendedjoin.indexOf(elt);
			 * } }
			 */

			find = equalityList(elt, queryBean.getTables());
			if (find) {
				possibleindex.add(index);
				// break;
			}
			index++;
		}

		if (possibleindex.size() == 0)
			return dep;

		if (/* find && */ possibleindex.size() == 1) {
			dep = dependencies.get(/* index */possibleindex.get(0));
		} else {

			int rightindex = 0;

			for (Integer essayindex : possibleindex) {
				String keys = "";
				boolean search = true;

				List<String> cds_val = new ArrayList<String>();
				cds_val.addAll(conds.values());
				List<String> cds_key = new ArrayList<String>();
				cds_key.addAll(conds.keySet());
				List<String> dep_keys = new ArrayList<String>();
				dep_keys.addAll(dependencies.get(essayindex).keySet());

				List<String> dep_val = new ArrayList<String>();
				dep_val.addAll(dependencies.get(essayindex).values());

				if ((!ContainsTables(dep_keys, cds_key)) && (!ContainsTables(dep_keys, cds_val))) {
					search = false;
					// break;
				}

				if ((!ContainsTables(dep_val, cds_key)) && (!ContainsTables(dep_val, cds_val))) {
					search = false;
					// break;
				}

				if (search) {
					rightindex = essayindex;
					break;
				}

			}
			dep = dependencies.get(rightindex);
		}
		return dep;
	}

	private boolean equalityList(List<String> l1, List<String> tables) {
		List<String> similar = new ArrayList<String>(tables);
		int k = similar.size();
		similar.retainAll(l1);
		if (tables.size() == l1.size() && k == similar.size()) {
			return true;
		} else
			return false;
	}

	/**********
	 * problem of order how to identify that Flights, Airport= Airport,Flights
	 *****************/
	private List<String> IdentifyTables(List<String> tables) {
		for (List<String> entry : recomendedjoin) {

			List<String> similar = new ArrayList<String>(tables);
			int k = similar.size();
			similar.retainAll(entry);
			if (tables.size() == entry.size() && k == similar.size()) {
				return entry;
			}
		}
		return null;
	}

	/**** update names of airports for case of duplicat ***/
	public QueryForm ExtendAndAvoidDuplica(QueryForm que, String webVal, String webField) {
		QueryForm tok = ExtendV2(que, webVal, webField);

		/******************
		 * detection of duplication of tables
		 ******************/

		boolean duplica = false;

		Set<String> duplicat = new HashSet<String>(tok.getTables());

		if (duplicat.size() < tok.getTables().size()) {
			duplica = true;
		}

		if (duplica) {
			List<String> setAtt = UpdateAtts(tok.getSelectedAtt());

			tok.setSelectedAtt(setAtt);

			Map<String, String> dep1 = UpdateMap(tok.getJoinTables(), queryBean);

			Map<String, String> conds = UpdateConds(tok.getCondAtt());
			List<String> grpSet = UpdateGroupBy(tok.getGroupbyAtt());

			tok.setJoinTables(dep1);

			tok.setCondAtt(conds);
			tok.setGroupbyAtt(grpSet);
			Map<String, String> me = new HashMap<String, String>();
			tok.setOrderbyAtt(me);

		}

		return tok;

	}

	// here we add relevant information in the selection and order by parts of
	// the query

	public QueryForm update(QueryForm query) {
		String[] res = this.relevants.split(",");
		List<String> select = new ArrayList<String>();
		select.addAll(query.getSelectedAtt());
		List<String> aa = new ArrayList<String>();
		aa.addAll(queryBean.getGroupbyAtt());
		Boolean change = false;
		for (String elt : select) {
			if (elt.split(",").length > 1) {
				if (elt.split(",")[1].equals(res[0]))
					change = true;
			} else {
				if (elt.equals(res[0]))
					change = true;
			}
		}

		if (query.isDuplica()) {
			List<String> old = new ArrayList<String>();
			if (!change) {
				old.add(res[0]);
			}
			List<String> newli = UpdateAtts(old);
			select.add(newli.get(0));
			aa.add(newli.get(0));
		} else {

			if (!change) {
				select.add(res[0]);
			}
			aa.add(res[0]);
		}

		query.setGroupbyAtt(aa);
		query.setSelectedAtt(select);

		return query;
	}

	/****
	 * extend query by going to other dimension, the primarykey of selected
	 * dimension will be added to select part
	 ***/
	public List<QueryForm> ExtendTablePlusId() {
		List<QueryForm> qrs = new ArrayList<QueryForm>();

		QueryForm que = Formalize();

		List<String> tables = new ArrayList<String>();
		tables.addAll(que.getTables());

		List</* List<String> */Integer> ext = searchJoinV1(tables, recomendedjoin);

		/******************
		 * detection of duplication of tables
		 ******************/

		for (/* List<String> NewTable */ Integer index : ext) {
			List<String> NewTable = recomendedjoin.get(index);
			boolean duplica = false;
			QueryForm tok = new QueryForm();
			tok.setCondAtt(que.getCondAtt());
			tok.setSelectedAtt(que.getSelectedAtt());
			tok.setTables(que.getTables());
			tok.setGroupbyAtt(que.getGroupbyAtt());
			tok.setOrderbyAtt(que.getOrderbyAtt());
			tok.setJoinTables(que.getJoinTables());

			Set<String> duplicat = new HashSet<String>(NewTable);

			if (duplicat.size() < NewTable.size()) {
				duplica = true;
			}
			Map<String, String> dep = dependencies
					.get(/* recomendedjoin.indexOf(NewTable) */ index);

			if (duplica) {
				List<String> setAtt = UpdateAtts(tok.getSelectedAtt());
				List<String> inter = new ArrayList<String>(setAtt);
				List<String> setAtt1 = AddDimkey(setAtt, NewTable, tables);
				tok.setSelectedAtt(setAtt1);

				Map<String, String> dep1 = UpdateMap(dep, queryBean);
				Map<String, String> conds = UpdateConds(tok.getCondAtt());
				List<String> grpSet = AddGroupby(inter, setAtt1);

				grpSet.addAll(tok.getGroupbyAtt());
				tok.setJoinTables(dep1);

				tok.setCondAtt(conds);
				tok.setGroupbyAtt(grpSet);
				tok.setTables(NewTable);
				qrs.add(tok);

			} else {
				List<String> oldAtts = new ArrayList<String>(tok.getSelectedAtt());
				List<String> setAtt1 = AddDimkey(oldAtts, NewTable, tables);

				List<String> grpSet = AddGroupby(tok.getSelectedAtt(), setAtt1);

				tok.setSelectedAtt(setAtt1);
				grpSet.addAll(tok.getGroupbyAtt());
				tok.setJoinTables(dep);
				tok.setTables(NewTable);
				tok.setGroupbyAtt(grpSet);
				qrs.add(tok);
			}

		}

		return qrs;

	}

	/******* similar to addAtt : it adds key of new dimension **********/
	private List<String> AddDimkey(List<String> setAtt, List<String> res, List<String> tables) {

		Random rand = new Random();
		int n = 0;
		List<String> similar = new ArrayList<String>();
		List<String> different = new ArrayList<String>();
		different.addAll(res);
		different.addAll(tables);
		similar.addAll(res);
		similar.retainAll(tables);
		different.removeAll(similar);
		if (different.isEmpty()) {

			setAtt.add("A2.iata");
		} else {

			for (String table : different) {
				if (table.equals("Carriers")) {
					/*
					 * n = rand.nextInt(1) + 0; setAtt.add(carrierAtts.get(n));
					 */
					setAtt.add("code");
				} else if (table.equals("Airports")) {
					// n = rand.nextInt(3) + 0;
					setAtt.add("iata");
				} else {
					/*
					 * n = rand.nextInt(3) + 0; setAtt.add(tailAtts.get(n));
					 */
					setAtt.add("Tail.tailnum");
				}
			}
		}

		return setAtt;

	}

	/*****
	 * find the dimension selected by user and go down in the granularity
	 * 
	 * @param userquery
	 ****/
	public QueryForm drill(QueryForm query, QueryForm Initialuserquery) {
		String studiedDimension = "";
	
	
		List<String> newSelectionpart = new ArrayList<String>();
		List<String> newGrouppart = new ArrayList<String>();
		String currentGranularity = Initialuserquery.getGroupbyAtt().get(0);
		String currentselectattribute = Initialuserquery.getSelectedAtt().get(1);
		

		if (currentGranularity.split(Pattern.quote(".")).length > 1) {
			currentGranularity = currentGranularity.split(Pattern.quote("."))[1];
		}

		if (currentselectattribute.split(Pattern.quote(".")).length > 1) {
			currentselectattribute = currentselectattribute.split(Pattern.quote("."))[1];
		}

		if (airAtts.contains(currentGranularity)) {studiedDimension = "Airports";}
		else if  (carrierAtts.contains(currentGranularity)) {studiedDimension = "Carriers";}
		else if  (tailAtts.contains(currentGranularity)) {studiedDimension = "Tail";}
		else {studiedDimension = "flights";}
		
		if(studiedDimension.equals("flights"))
			{
			return new QueryForm();}
		
		String newGranularity = "";
		if (studiedDimension.equals("Airports")) {

			int index = airAtts.indexOf(currentGranularity);
			if (index < airAtts.size() - 1) {
				newGranularity = airAtts.get(index + 1);
			} else {
				newGranularity = airAtts.get(0);
			}
		} else if (studiedDimension.equals("Carriers")) {
			int index = carrierAtts.indexOf(currentGranularity);
			if (index < carrierAtts.size() - 1) {
				newGranularity = carrierAtts.get(index + 1);
			} else {
				newGranularity = carrierAtts.get(0);
			}

		} else if (studiedDimension.equals("Tail")) {
			int index = tailAtts.indexOf(currentGranularity);
			if (index < tailAtts.size() - 1) {
				newGranularity = tailAtts.get(index + 1);
			} else {
				newGranularity = tailAtts.get(0);
			}
		}

		if (Initialuserquery.getGroupbyAtt().get(0).split(Pattern.quote(".")).length > 1) {
			newGranularity = Initialuserquery.getGroupbyAtt().get(0).split(Pattern.quote("."))[0] + "." + newGranularity;
		}

		
		if (!newGranularity.isEmpty()) {
			for (String att : query.getSelectedAtt()) {
				String att1=att;
				
				if (att.split(Pattern.quote(".")).length > 1) {att1=att.split(Pattern.quote("."))[1];}
				
				
				if ((currentselectattribute.equals("code") && att1.equals("uniquecarrier"))
						|| (currentselectattribute.equals("uniquecarrier") && att1.equals("code"))) {
					newSelectionpart.add(newGranularity);
				} 
				else if(currentselectattribute.equals(att1) ) {
					newSelectionpart.add(newGranularity);
				}else if (!att1.equals(currentselectattribute)) {
					newSelectionpart.add(att);
				} else
					newSelectionpart.add(newGranularity);
			}

			for (String grp : query.getGroupbyAtt()) {
				String grp1=grp;
			
				if (grp.split(Pattern.quote(".")).length > 1) {grp1=grp.split(Pattern.quote("."))[1];}
				
				if ((currentGranularity.equals("code") && grp1.equals("uniquecarrier"))
						|| (currentGranularity.equals("uniquecarrier") && grp1.equals("code"))) {
					newGrouppart.add(newGranularity);
				} 
				else if(currentGranularity.equals(grp1) ) {
					newGrouppart.add(newGranularity);
				}
				else if (!grp1.equals(currentGranularity)) {
					newGrouppart.add(grp);
				} else
					newGrouppart.add(newGranularity);
			}
		

		}

		// here I need reformulation of alias
		QueryForm newquery = new QueryForm(query.getSelectedAtt(), query.getCondAtt(), query.getTables(),
				query.getGroupbyAtt(), Initialuserquery.getOrderbyAtt(), query.getJoinTables());

		if (!newSelectionpart.isEmpty()) {
			//newSelectionpart2.addAll(newSelectionpart);
			newquery.setSelectedAtt(newSelectionpart);
			newquery.setGroupbyAtt(newGrouppart);
		}

		return newquery;
	}

	public String findView() {
		int index = dependencies.indexOf(this.queryBean.getJoinTables());
		String result = null;
		if (index == 0)
			result = "flights_airports_dep";
		else if (index == 11)
			result = "flights_airports_dest";
		else if (index == 5)
			result = "flights_carriers";
		else if (index == 2)
			result = "flights_tail";

		return result;
	}

	// find the name of table given the name of column
	public String findTable(String colName) {
		if (airAtts.contains(colName))
			return "airports";
		else if (tailAtts.contains(colName))
			return "tail";
		else if (carrierAtts.contains(colName))
			return "carriers";
		else
			return "flights";
	}

	// putting each selected attribute in the following format :
	// nametable.nameCOlumn
	private List<String> Reformation(QueryForm query, List<String> newSelectionpart) {
		boolean duplicaTabs = query.checkDuplicatedTables();
		String tableAbbreviation = "";
		if (duplicaTabs) {
			String oldTable = "";
			for (String cond : query.getCondAtt().keySet()) {
				if (cond.matches("A\\d.*")) {
					oldTable = cond.split(Pattern.quote("."))[0];
				}

				if (oldTable.equals("A1")) {
					tableAbbreviation = "A2";
				} else {
					tableAbbreviation = "A1";
				}

			}

		}

		List<String> result = new ArrayList<String>();
		for (String att : newSelectionpart) {
			if (airAtts.contains(att)) {
				if (!tableAbbreviation.isEmpty()) {
					result.add(tableAbbreviation + "." + att);
				} else {
					String att1 = "Airports." + att;
					result.add(att1);
				}
			} else if (carrierAtts.contains(att)) {
				String att1 = "Carriers." + att;
				result.add(att1);
			} else if (tailAtts.contains(att)) {
				String att1 = "Tail." + att;
				result.add(att1);
			} else if (flightsAtts.contains(att)) {
				String att1 = "Flights." + att;
				result.add(att1);
			}

			else
				result.add(att);
		}

		return result;
	}

	public QueryForm alias(QueryForm query) {

		List<String> newSelectionpartV1 = Reformation(query, query.getSelectedAtt());
		List<String> newGrouppartV1 = Reformation(query, query.getGroupbyAtt());
		query.setSelectedAtt(newSelectionpartV1);
		query.setGroupbyAtt(newGrouppartV1);
		
		return query;
	}

	// this is implemented for the case select* from airport1, airport2
	public QueryForm avoidDoublecolumn(QueryForm query,boolean tableRedundancy) {
		List<String> updatedSelectionclause = new ArrayList<String>();
		int i = 1;
		for (String table : query.getTables()) {
			if (!table.equals("Airports") && !table.equals("Tail")) {
				updatedSelectionclause.add(table + ".*");
			} else if (table.equals("Airports")) {

				for (String att : airAtts) {
					if (!att.equals("iata")) {
						if(tableRedundancy)
						{updatedSelectionclause.add("A" + i + "." + att);}
						else updatedSelectionclause.add("Airports." + att);
					}
				}
				i++;
			} else {
				for (String att : tailAtts) {
					if (!att.equals("tailnum")) {
						updatedSelectionclause.add("Tail" + "." + att);
					}
				}
			}

		}

		QueryForm que = new QueryForm(updatedSelectionclause, query.getCondAtt(), query.getTables(),
				query.getJoinTables());
		return que;
	}

	private boolean ContainsTables(List<String> dep_keys, List<String> cds_val) {
		List<String> table1 = new ArrayList<String>();
		List<String> table2 = new ArrayList<String>();
		boolean result = true;
		for (String att : dep_keys) {

			if (att.split(Pattern.quote(".")).length > 1) {
				table1.add(att.split(Pattern.quote("."))[1]);
			} else
				table1.add(att);
		}

		for (String att : cds_val) {
			if (att.split(Pattern.quote(".")).length > 1) {
				table2.add(att.split(Pattern.quote("."))[1]);
			} else
				table2.add(att);
		}

		for (String att : table1) {
			if (!table2.contains(att)) {
				result = false;
				break;
			}
		}
		return result;

	}

}
