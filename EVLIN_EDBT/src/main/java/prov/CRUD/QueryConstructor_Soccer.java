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
import prov.mybean.QueryForm;

public class QueryConstructor_Soccer extends QueryConstructor {

	private final static List<List<String>> recomendedjoin = new ArrayList(Arrays.asList(
			Arrays.asList("match", "country"), 
			Arrays.asList("match", "league")
			, Arrays.asList("match", "team"),
			Arrays.asList("match", "player"),
			Arrays.asList("match", "country", "team"),
			Arrays.asList("match", "country", "player"),
			Arrays.asList("match", "team", "player"),
			Arrays.asList("match", "league", "team"),
			Arrays.asList("match", "league", "player"),
			 Arrays.asList("match", "country", "league"),
			 Arrays.asList("match", "team", "team"),
				Arrays.asList("match", "player", "player"),
				Arrays.asList("match", "player", "team", "team"), 
			Arrays.asList("match", "country", "team", "team"),
			Arrays.asList("match", "league", "team", "team"),
			Arrays.asList("match", "player", "player","team"),
			Arrays.asList("match", "player", "player","league"),
			Arrays.asList("match", "player", "player","country"),	
			Arrays.asList("match", "country", "league", "team"),
			Arrays.asList("match", "country", "league", "player"),
			Arrays.asList("match", "team", "player", "country"),
			Arrays.asList("match", "team", "player", "league"), 
			Arrays.asList("match", "team", "team", "league", "player"),
			Arrays.asList("match", "team", "team", "country", "player"),
			Arrays.asList("match", "team",  "team","player", "player"),
			Arrays.asList("match", "team", "team", "country", "league"),
			Arrays.asList("match", "team",  "league","player", "player"),
			Arrays.asList("match", "team",  "country","player", "player"),		
			Arrays.asList("match", "team", "player", "team", "player", "league"),
			Arrays.asList("match", "team", "player", "team", "player", "country")

	));

	private static final Map<String, String> dep1 = new HashMap<String, String>();

	static {

		dep1.put("match.country_id", "country.cid");
	}

	private static final Map<String, String> dep2 = new HashMap<String, String>();

	static {
		dep2.put("match.league_id", "league.lid");
	}

	
	private static final Map<String, String> dep3 = new HashMap<String, String>();
	static {
		dep3.put("match.home_team_api_id", "team.team_api_id");
	}

	private static final Map<String, String> dep4 = new HashMap<String, String>();

	static {

		dep4.put("match.home_player_1", "player.player_api_id");

	}
	
	private static final Map<String, String> dep5 = new HashMap<String, String>();
	static {
		dep5.put("match.country_id", "country.cid");
		dep5.put("match.home_team_api_id", "team.team_api_id");
	}
	
	private static final Map<String, String> dep6 = new HashMap<String, String>();
	static {
		dep6.put("match.country_id", "country.cid");
		dep6.put("match.home_player_1", "player.player_api_id");
	}

	private static final Map<String, String> dep7= new HashMap<String, String>();
	static {
		dep7.put("match.home_team_api_id", "team.team_api_id");
		dep7.put("match.home_player_1", "player.player_api_id");
	}
	
	
	
	private static final Map<String, String> dep8 = new HashMap<String, String>();
	static {
		dep8.put("match.league_id", "league.lid");
		dep8.put("match.home_team_api_id", "team.team_api_id");
	}
	
	private static final Map<String, String> dep9= new HashMap<String, String>();
	static {
		dep9.put("match.league_id", "league.lid");
		dep9.put("match.home_player_1", "player.player_api_id");
	}
	
	private static final Map<String, String> dep10 = new HashMap<String, String>();
	static {
		dep10.put("match.country_id", "country.cid");
		dep10.put("match.league_id", "league.lid");
	}
	
	private static final Map<String, String> dep11 = new HashMap<String, String>();
	static {
		dep11.put("match.home_team_api_id", "team.team_api_id");
		dep11.put("match.away_team_api_id", "team.team_api_id");
	}
	
	private static final Map<String, String> dep12 = new HashMap<String, String>();
	static {
		dep12.put("match.home_player_1", "player.player_api_id");
		dep12.put("match.away_player_1", "player.player_api_id");
	}
	
	private static final Map<String, String> dep13 = new HashMap<String, String>();
	static {
		dep13.put("match.home_player_1", "player.player_api_id");
		dep13.put("match.home_team_api_id", "team.team_api_id");
		dep13.put("match.away_team_api_id", "team.team_api_id");
	}
	
	private static final Map<String, String> dep14 = new HashMap<String, String>();
	static {
		dep14.put("match.country_id", "country.cid");
		dep14.put("match.home_team_api_id", "team.team_api_id");
		dep14.put("match.away_team_api_id", "team.team_api_id");
	}

	private static final Map<String, String> dep15 = new HashMap<String, String>();
	static {
		dep15.put("match.league_id", "league.lid");
		dep15.put("match.home_team_api_id", "team.team_api_id");
		dep15.put("match.away_team_api_id", "team.team_api_id");
	}
	
	private static final Map<String, String> dep16 = new HashMap<String, String>();
	static {
		dep16.put("match.home_team_api_id", "team.team_api_id");
		dep16.put("match.home_player_1", "player.player_api_id");
		dep16.put("match.away_player_1", "player.player_api_id");
	}
	
	
	private static final Map<String, String> dep17 = new HashMap<String, String>();
	static {
		dep17.put("match.league_id", "league.lid");
		dep17.put("match.home_player_1", "player.player_api_id");
		dep17.put("match.away_player_1", "player.player_api_id");
	}
	
	private static final Map<String, String> dep18 = new HashMap<String, String>();
	static {
		dep18.put("match.country_id", "country.cid");
		dep18.put("match.home_player_1", "player.player_api_id");
		dep18.put("match.away_player_1", "player.player_api_id");
	}
	
	
	
	private static final Map<String, String> dep19 = new HashMap<String, String>();
	static {
		dep19.put("match.country_id", "country.cid");
		dep19.put("match.league_id", "league.lid");
		dep19.put("match.home_team_api_id", "team.team_api_id");
	}
	
	private static final Map<String, String> dep20 = new HashMap<String, String>();
	static {
		dep20.put("match.country_id", "country.cid");
		dep20.put("match.league_id", "league.lid");
		dep20.put("match.home_player_1", "player.player_api_id");
	}
	
	
	private static final Map<String, String> dep21 = new HashMap<String, String>();
	static {
		dep21.put("match.home_team_api_id", "team.team_api_id");
		dep21.put("match.country_id", "country.cid");
		dep21.put("match.home_player_1", "player.player_api_id");
	}

	private static final Map<String, String> dep22 = new HashMap<String, String>();
	static {
		dep22.put("match.home_team_api_id", "team.team_api_id");
		dep22.put("match.league_id", "league.lid");
		dep22.put("match.home_player_1", "player.player_api_id");
	}

	
	private static final Map<String, String> dep23 = new HashMap<String, String>();
	static {
		dep23.put("match.league_id", "league.lid");
		dep23.put("match.home_player_1", "player.player_api_id");
		dep23.put("match.home_team_api_id", "team.team_api_id");
		dep23.put("match.away_team_api_id", "team.team_api_id");
	}
	
	
	private static final Map<String, String> dep24 = new HashMap<String, String>();
	static {
		dep24.put("match.country_id", "country.cid");
		dep24.put("match.home_player_1", "player.player_api_id");
		dep24.put("match.home_team_api_id", "team.team_api_id");
		dep24.put("match.away_team_api_id", "team.team_api_id");
	}
	
	
	
	private static final Map<String, String> dep25 = new HashMap<String, String>();
	static {
		dep25.put("match.home_player_1", "player.player_api_id");
		dep25.put("match.away_player_1", "player.player_api_id");
		dep25.put("match.home_team_api_id", "team.team_api_id");
		dep25.put("match.away_team_api_id", "team.team_api_id");
	}
	
	private static final Map<String, String> dep26 = new HashMap<String, String>();
	static {
		dep26.put("match.country_id", "country.cid");
		dep26.put("match.league_id", "league.lid");
		dep26.put("match.home_team_api_id", "team.team_api_id");
		dep26.put("match.away_team_api_id", "team.team_api_id");
	}
	


	private static final Map<String, String> dep27 = new HashMap<String, String>();
	static {
		dep27.put("match.home_player_1", "player.player_api_id");
		dep27.put("match.away_player_1", "player.player_api_id");
		dep27.put("match.home_team_api_id", "team.team_api_id");
		dep27.put("match.league_id", "league.lid");
	}
	
	private static final Map<String, String> dep28 = new HashMap<String, String>();
	static {
		dep28.put("match.home_player_1", "player.player_api_id");
		dep28.put("match.away_player_1", "player.player_api_id");
		dep28.put("match.home_team_api_id", "team.team_api_id");
		dep28.put("match.country_id", "country.cid");
	}
	
	private static final Map<String, String> dep29 = new HashMap<String, String>();
	static {
		dep29.put("match.home_player_1", "player.player_api_id");
		dep29.put("match.away_player_1", "player.player_api_id");
		dep29.put("match.league_id", "league.lid");
		dep29.put("match.home_team_api_id", "team.team_api_id");
		dep29.put("match.away_team_api_id", "team.team_api_id");
	}
	
	
	
	private static final Map<String, String> dep30 = new HashMap<String, String>();
	static {
		dep30.put("match.home_player_1", "player.player_api_id");
		dep30.put("match.away_player_1", "player.player_api_id");
		dep30.put("match.country_id", "country.cid");
		dep30.put("match.home_team_api_id", "team.team_api_id");
		dep30.put("match.away_team_api_id", "team.team_api_id");
	}

	
	/*private static final Map<String, String> dep20 = new HashMap<String, String>();

	static {
		dep20.put("match.away_team_api_id", "team.team_api_id");
	}*/

	private final static List<String> leagueAtts = new ArrayList(Arrays.asList("league_name", "countryfk_id", "lid"));
	private final static List<String> countryAtts = new ArrayList(Arrays.asList("name", "cid"));
	private final static List<String> playerAtts = new ArrayList(
			Arrays.asList("player_name", "birthday", "height", "weight", "player_fifa_api_id", "pid", "player_api_id"));
	private final static List<String> teamAtts = new ArrayList(
			Arrays.asList("team_long_name", "team_short_name", "team_api_id", "team_fifa_api_id", "tid"));
	private final static List<Map<String, String>> dependencies = new ArrayList(
			Arrays.asList(dep1, dep2, dep3, dep4, dep5, dep6, dep7, dep8, dep9, dep10, dep11, dep12, dep13, dep14,dep15, dep16, dep17, dep18, dep19,
					dep20, dep21, dep22, dep23, dep24, dep25, dep26, dep27, dep28, dep29, dep30));

	private final static List<String> matchAtts = new ArrayList(Arrays.asList("season", "stage", "date", "match_api_id",
			"home_team_api_id", "away_team_api_id", "home_team_goal", "away_team_goal",  "home_player_1", "home_player_2",
			"home_player_3", "home_player_4", "home_player_5", "home_player_6", "home_player_7", "home_player_8",
			"home_player_9", "home_player_10", "home_player_11", "away_player_1", "away_player_2", "away_player_3",
			"away_player_4", "away_player_5", "away_player_6", "away_player_7", "away_player_8", "away_player_9",
			"away_player_10", "away_player_11", "goal", "shoton", "shotoff", "foulcommit", "card", "corner", "b365h",
			"b365d", "b365a", "result", "id"));



	public QueryConstructor_Soccer(QueryForm query, String relevantfield) {
		super();
		this.queryBean = new QueryForm(query.getSelectedAtt(), query.getCondAtt(), query.getTables(),
				query.getGroupbyAtt(), query.getOrderbyAtt(), query.getJoinTables());
		this.relevants = relevantfield;
	}

	
	public QueryConstructor_Soccer() {
		super();
	
	}
	
	
	
	



	/**** extend query by going to other dimension ***/
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
			Map<String, String> dep = dependencies.get(index);

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
			if (webField.split(Pattern.quote(".")).length == 1) {
				if (inter.length > 1) {
					inter1 = inter[1];
				} else {
					inter1 = inter[0];
				}
			} else {
				inter1 = entry.getKey();
			}
			if (inter1.contains("range,") || inter1.contains("not,")) {
				String rr = inter1.split(",")[1];

				inter1 = rr;
			}
			// if(inter1.equals("code")) {inter1="uniqueplayer";}

			if (!(inter1.equals(webField)) || !(entry.getValue().equals(webVal))) {
				res.put(entry.getKey(), entry.getValue());

			} else {
				if (inter1.equals("code") && webField.equals("uniqueplayer")) {
					// res.put(entry.getKey(), entry.getValue());
					discardField = entry.getKey();
				} else if (inter1.equals("uniqueplayer") && webField.equals("code")) {
					// res.put(entry.getKey(), entry.getValue());
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
		if (discardField != null) {
			grpset.add(discardField);
		}

		List<String> selectset = query.getSelectedAtt();
		if (discardField != null) {
			selectset.add(discardField);
		}

		Map<String, String> order = new HashMap<String, String>();
		order.putAll(query.getOrderbyAtt());
		if (discardField != null) {
			order.put(discardField, "desc");
		}

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
				if (Collections.frequency(similar, "team") >1)
			{setAtt.add("T2.team_short_name");}
				else {
					setAtt.add("P"+Collections.frequency(similar, "player")+".player_name");	
				}
		} else {
			for (String table : different) {
				if (table.equals("players")) {
			
					setAtt.add("player_name");
				} else {
					 if (table.equals("league") ) {
						// n = rand.nextInt(3) + 0;
						setAtt.add("league_name");
					} else if ( table.equals("country")) {
						
						setAtt.add("name");
					}
					else {
						
						setAtt.add("team_short_name");
					}
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
			setAtt.add("team_short_name");
		} else {
			for (String table : different) {
				if (table.equals("player")) {
	
					setAtt.add("player_name");

				} else if (table.equals("league") ) {
					
					setAtt.add("league_name");
				} else if ( table.equals("country")) {
					
					setAtt.add("name");
				}
				else {
				
					setAtt.add("team_short_name");
				}
			}
		}

		return setAtt;
	}

	public Map<String, String> UpdateConds(Map<String, String> condAtt) {
		Map<String, String> res = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : condAtt.entrySet()) {

			if (entry.getKey().startsWith("not,")) {
				if (teamAtts.contains(entry.getKey().split(",")[1])) {
					res.put("not, T1." + entry.getKey().split(",")[1], entry.getValue());
				} else if (playerAtts.contains(entry.getKey().split(",")[1])) {
					res.put("not, P1." + entry.getKey().split(",")[1], entry.getValue());
				} else
					res.put(entry.getKey(), entry.getValue());
			} else {
				if (teamAtts.contains(entry.getKey())) {

					res.put("T1." + entry.getKey(), entry.getValue());

				} else if (playerAtts.contains(entry.getKey())) {

					res.put("P1." + entry.getKey(), entry.getValue());

				} else
					res.put(entry.getKey(), entry.getValue());

			}
		}
		return res;
	}

	private List<String> UpdateGroupBy(List<String> grp) {
		List<String> res = new ArrayList<String>();
		for (String entry : grp) {
			if (teamAtts.contains(entry)) {
				res.add("T1." + entry);
			} else if (playerAtts.contains(entry)) {
				res.add("P1." + entry);
			} else
				res.add(entry);

		}
		return res;
	}

	// updat atts names in case of redundancy
	private List<String> UpdateAtts(List<String> setatts) {

		List<String> res = new ArrayList<String>();
		for (String att : setatts) {
			if (teamAtts.contains(att))
				res.add("T1." + att);
			else if (playerAtts.contains(att)) {
				res.add("P1." + att);
			} else
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
				if (entry.getKey().contains("team.")) {
					String[] res1 = entry.getKey().split(Pattern.quote("."));

					res.put("T1." + res1[1], entry.getValue());

				} else if (entry.getValue().contains("team.")) {
					String[] res1 = entry.getValue().split(Pattern.quote("."));

					res.put(entry.getKey(), "T1." + res1[1]);

				}
				else if (entry.getKey().contains("player.")) {
					String[] res1 = entry.getKey().split(Pattern.quote("."));

					res.put("P1." + res1[1], entry.getValue());

				} else if (entry.getValue().contains("player.")) {
					String[] res1 = entry.getValue().split(Pattern.quote("."));

					res.put(entry.getKey(), "P1." + res1[1]);

				}
				else {
					res.put(entry.getKey(), entry.getValue());
				}

			}

			else if (que.getJoinTables().containsKey(entry.getKey().split(Pattern.quote("."))[1])
					&& que.getJoinTables().get(entry.getKey()).equals(entry.getValue().split(Pattern.quote("."))[1])) {
				if (entry.getKey().contains("team.")) {
					String[] res1 = entry.getKey().split(Pattern.quote("."));

					res.put("T1." + res1[1], entry.getValue());

				} else if (entry.getValue().contains("team.")) {
					String[] res1 = entry.getValue().split(Pattern.quote("."));

					res.put(entry.getKey(), "T1." + res1[1]);

				} 
				
				if (entry.getKey().contains("player.")) {
					String[] res1 = entry.getKey().split(Pattern.quote("."));

					res.put("P1." + res1[1], entry.getValue());

				} else if (entry.getValue().contains("player.")) {
					String[] res1 = entry.getValue().split(Pattern.quote("."));

					res.put(entry.getKey(), "P1." + res1[1]);

				}
				
				else {
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
					if (entry.getKey().contains("team.")) {
						String[] res1 = entry.getKey().split(Pattern.quote("."));

						res.put("T1." + res1[1], entry.getValue());

					} else if (entry.getValue().contains("team.")) {
						String[] res1 = entry.getValue().split(Pattern.quote("."));

						res.put(entry.getKey(), "T1." + res1[1]);

					}
					else if (entry.getKey().contains("player.")) {
						String[] res1 = entry.getKey().split(Pattern.quote("."));

						res.put("P1." + res1[1], entry.getValue());

					} else if (entry.getValue().contains("player.")) {
						String[] res1 = entry.getValue().split(Pattern.quote("."));

						res.put(entry.getKey(), "P1." + res1[1]);

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
					if (entry.getKey().contains("team.")) {
						String[] res1 = entry.getKey().split(Pattern.quote("."));

						res.put("T1." + res1[1], entry.getValue());

					} else if (entry.getValue().contains("team.")) {
						String[] res1 = entry.getValue().split(Pattern.quote("."));

						res.put(entry.getKey(), "T1." + res1[1]);

					}
					else 	if (entry.getKey().contains("player.")) {
						String[] res1 = entry.getKey().split(Pattern.quote("."));

						res.put("P1." + res1[1], entry.getValue());

					} else if (entry.getValue().contains("player.")) {
						String[] res1 = entry.getValue().split(Pattern.quote("."));

						res.put(entry.getKey(), "P1." + res1[1]);

					}
				}
			}

			else {

				if (entry.getKey().contains("team.")) {
					String[] res1 = entry.getKey().split(Pattern.quote("."));

					res.put("T2." + res1[1], entry.getValue());

				} else if (entry.getValue().contains("team.")) {
					String[] res1 = entry.getValue().split(Pattern.quote("."));

					res.put(entry.getKey(), "T2." + res1[1]);

				}
				else 	if (entry.getKey().contains("player.")) {
					String[] res1 = entry.getKey().split(Pattern.quote("."));

					res.put("P2." + res1[1], entry.getValue());

				} else if (entry.getValue().contains("player.")) {
					String[] res1 = entry.getValue().split(Pattern.quote("."));

					res.put(entry.getKey(), "P2." + res1[1]);

				}
				else {
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

		List<Integer> ext = searchJoinV1(tables, recomendedjoin);

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
					.get( index);

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
			if ( Collections.frequency(similar, "player")  >1    ) {
				setAtt.add("P2.pid");
			}
			if (Collections.frequency(similar, "team")  >1 ) {
				setAtt.add("T2.team_api_id");
			}
		} else {

			for (String table : different) {
				if (table.equals("team")) {
					setAtt.add("team_api_id");
				} else if (table.equals("player")) {
					setAtt.add("player_api_id");
				}else if(table.equals("league")) {
					setAtt.add("lid");
				}
				else if(table.equals("country")) {
					setAtt.add("cid");
				}
				else {setAtt.add("id");}
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
		String currentGranularity = Initialuserquery.getGroupbyAtt().get(0);
		String currentselectattribute = Initialuserquery.getSelectedAtt().get(1);

		if (currentGranularity.split(Pattern.quote(".")).length > 1) {
			currentGranularity = currentGranularity.split(Pattern.quote("."))[1];
		}

		if (currentselectattribute.split(Pattern.quote(".")).length > 1) {
			currentselectattribute = currentselectattribute.split(Pattern.quote("."))[1];
		}

		if (teamAtts.contains(currentGranularity)) {
			studiedDimension = "team";
		} else if (playerAtts.contains(currentGranularity)) {
			studiedDimension = "player";
		} else if (leagueAtts.contains(currentGranularity)) {
			studiedDimension = "league";
		} else if (countryAtts.contains(currentGranularity)) {
			studiedDimension = "country";
		} else {
			studiedDimension = "match";
		}

		if (studiedDimension.equals("match")) {
			return new QueryForm();
		}

		String newGranularity = "";
		if (studiedDimension.equals("team")) {

			int index = teamAtts.indexOf(currentGranularity);
			if (index < teamAtts.size() - 1) {
				newGranularity = teamAtts.get(index + 1);
			} else {
				newGranularity = teamAtts.get(0);
			}
		} else if (studiedDimension.equals("player")) {
			int index = playerAtts.indexOf(currentGranularity);
			if (index < playerAtts.size() - 1) {
				newGranularity = playerAtts.get(index + 1);
			} else {
				newGranularity = playerAtts.get(0);
			}

		} else if (studiedDimension.equals("league")) {
			int index = leagueAtts.indexOf(currentGranularity);
			if (index < leagueAtts.size() - 1) {
				newGranularity = leagueAtts.get(index + 1);
			} else {
				newGranularity = leagueAtts.get(0);
			}
		} else if (studiedDimension.equals("country")) {
			int index = countryAtts.indexOf(currentGranularity);
			if (index < countryAtts.size() - 1) {
				newGranularity = countryAtts.get(index + 1);
			} else {
				newGranularity = countryAtts.get(0);
			}
		}

		if (Initialuserquery.getGroupbyAtt().get(0).split(Pattern.quote(".")).length > 1) {
			newGranularity = Initialuserquery.getGroupbyAtt().get(0).split(Pattern.quote("."))[0] + "."
					+ newGranularity;
		}

		List<String> newSelectionpart = new ArrayList<String>();
		List<String> newGrouppart = new ArrayList<String>();
		if (!newGranularity.isEmpty()) {
			for (String att : query.getSelectedAtt()) {
				String att1 = att;

				if (att.split(Pattern.quote(".")).length > 1) {
					att1 = att.split(Pattern.quote("."))[1];
				}

				if ((currentselectattribute.equals("code") && att1.equals("uniqueplayer"))
						|| (currentselectattribute.equals("uniqueplayer") && att1.equals("code"))) {
					newSelectionpart.add(newGranularity);
				} else if (currentselectattribute.equals(att1)) {
					newSelectionpart.add(newGranularity);
				} else if (!att1.equals(currentselectattribute)) {
					newSelectionpart.add(att);
				} else
					newSelectionpart.add(newGranularity);
			}

			for (String grp : query.getGroupbyAtt()) {
				String grp1 = grp;

				if (grp.split(Pattern.quote(".")).length > 1) {
					grp1 = grp.split(Pattern.quote("."))[1];
				}

				if ((currentGranularity.equals("code") && grp1.equals("uniqueplayer"))
						|| (currentGranularity.equals("uniqueplayer") && grp1.equals("code"))) {
					newGrouppart.add(newGranularity);
				} else if (currentGranularity.equals(grp1)) {
					newGrouppart.add(newGranularity);
				} else if (!grp1.equals(currentGranularity)) {
					newGrouppart.add(grp);
				} else
					newGrouppart.add(newGranularity);
			}

		}

		// here I need reformulation of alias
		QueryForm newquery = new QueryForm(query.getSelectedAtt(), query.getCondAtt(), query.getTables(),
				query.getGroupbyAtt(), Initialuserquery.getOrderbyAtt(), query.getJoinTables());

		if (!newSelectionpart.isEmpty()) {

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
			result = "flights_players";
		else if (index == 2)
			result = "flights_league";

		return result;
	}

	// find the name of table given the name of column
	public String findTable(String colName) {
		if (teamAtts.contains(colName))
			return "team";
		else if (leagueAtts.contains(colName))
			return "league";
		else if (playerAtts.contains(colName))
			return "player";
		else if (countryAtts.contains(colName))
			return "country";
		else
			return "match";
	}

	// putting each selected attribute in the following format :
	// nametable.nameCOlumn
	private List<String> Reformation(QueryForm query, List<String> newSelectionpart) {
		boolean duplicaTabs = query.checkDuplicatedTables();
		String tableAbbreviation = "";
		if (duplicaTabs) {
			String oldTable = "";
			for (String cond : query.getCondAtt().keySet()) {
				if (cond.matches("*\\d.*")) {
					oldTable = cond.split(Pattern.quote("."))[0];
				}

				if (oldTable.equals("P1")) {
					tableAbbreviation = "P2";
				} else if (oldTable.equals("T1")) {
					tableAbbreviation = "T2";
				} else {
					if (playerAtts.contains(cond)) {
						tableAbbreviation = "P1";
					} else
						tableAbbreviation = "T1";
				}

			}

		}

		List<String> result = new ArrayList<String>();
		for (String att : newSelectionpart) {
			if (teamAtts.contains(att)) {
				if (!tableAbbreviation.isEmpty()) {
					result.add(tableAbbreviation + "." + att);
				} else {
					String att1 = "team." + att;
					result.add(att1);
				}
			} else if (playerAtts.contains(att)) {
				String att1 = "players." + att;
				result.add(att1);
			} else if (leagueAtts.contains(att)) {
				String att1 = "league." + att;
				result.add(att1);
			} else if (countryAtts.contains(att)) {
				String att1 = "country." + att;
				result.add(att1);
			} else if (matchAtts.contains(att)) {
				String att1 = "match." + att;
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
	public QueryForm avoidDoublecolumn(QueryForm query, boolean tableRedundancy) {
		List<String> updatedSelectionclause = new ArrayList<String>();
		int i = 1;
		for (String table : query.getTables()) {
			if (table.equals("match")) {
				//updatedSelectionclause.add(table + ".*");
				
				  for (String att : matchAtts) { if (!att.equals("id")) {
				  updatedSelectionclause.add("match" + "." + att); } }
				
			} else if (table.equals("team")) {

				for (String att : teamAtts) {
					if (!att.equals("id")) {
						if (tableRedundancy) {
							updatedSelectionclause.add("T" + i + "." + att);
						} else
							updatedSelectionclause.add("team." + att);
					}
				}
				i++;
			} else if (table.equals("player")) {

				for (String att : playerAtts) {
					if (!att.equals("id")) {
						if (tableRedundancy) {
							updatedSelectionclause.add("P" + i + "." + att);
						} else
							updatedSelectionclause.add("player." + att);
					}
				}
				i++;
			}

			else if (table.equals("country")) {
				// updatedSelectionclause.add(table + ".*");
				for (String att : countryAtts) {
					if (!att.equals("id")) {
						updatedSelectionclause.add("country" + "." + att);
					}
				}
			}

			else {
				for (String att : leagueAtts) {
					if (!att.equals("id")) {
						updatedSelectionclause.add("league" + "." + att);
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
