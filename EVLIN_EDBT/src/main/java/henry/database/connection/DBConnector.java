package henry.database.connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import evoDb.Dataset;
import evoDb.Scale;
import henry.QuerySimilarity.Condition;
import henry.QuerySimilarity.QueryFromDB;
import henry.QuerySimilarity.VisualizationFromDB;
import henry.database.*;
import henry.visualizationGenerator.VegaTemplateLite;
import prov.act.DistinctValues;
import prov.chart.ActChart;
import prov.chart.ActionChart;
import prov.chart.AxisChart;
import prov.chart.MarkChart;
import prov.chart.MarkGeneral;
import prov.chart.PropChart;
import prov.chart.ScaleChart;
import prov.chart.VegaChart;
import prov.chartLite.EncodingChannel;
import prov.chartLite.EncodingChannels;
import prov.chartLite.Selection;
import prov.chartLite.VegaLiteChart;
import prov.mybean.DisplayForm;
import prov.mybean.QueryForm;
import prov.mybean.VisualizationBean;
import prov.util.HibernateUtil;

/**
 * 
 * @author henry This class provides methods for all purposes with the database
 *         Collecting data and request the data in the database
 * 
 */

public class DBConnector {
	Configuration config;
	public CounterInfo counter;
	public int parentqueryID;

	public int getParentqueryID() {
		return parentqueryID;
	}

	public void setParentqueryID(int parentqueryID) {
		this.parentqueryID = parentqueryID;
	}

	/**
	 * Constructor: Setup config file. Register all table classes (schema) If
	 * makeNewTable is true tables will be created
	 * 
	 * @param makeNewTable
	 */
	public DBConnector(Boolean makeNewTable) {
		parentqueryID = 0;
		counter = new CounterInfo();
		config = new AnnotationConfiguration().configure();
		config.addAnnotatedClass(QueryPath.class);
		config.addAnnotatedClass(QueryTable.class);
		config.addAnnotatedClass(Query_FromTable.class);
		config.addAnnotatedClass(Query_SelectTable.class);
		config.addAnnotatedClass(Query_ConditionTable.class);
		config.addAnnotatedClass(VizTable.class);
		config.addAnnotatedClass(MarkTable.class);
		config.addAnnotatedClass(DatasetTable.class);
		config.addAnnotatedClass(ScaleTable.class);
		config.addAnnotatedClass(ChannelTable.class);
		config.addAnnotatedClass(ChannelUsageTable.class);
		config.addAnnotatedClass(AxisTable.class);
		config.addAnnotatedClass(AxisUsageTable.class);
		config.configure("hibernateEvolutionProv.cfg.xml");

		if (makeNewTable) {
			new SchemaExport(config).create(true, true);
		}

		System.out.println("Client has conncected succesfully to Database: History");
	}

	/**
	 * Takes an VegaLite Object and put all information into the database
	 * 
	 * @param chart
	 * @param i
	 *            is the id of dataset that should be used for the scales
	 */
	public void VizToDB(VegaChart chart) {
		Session session = vizDBUtil.getSessionFactory().openSession();
		session.beginTransaction();

		// Vistable
		int visID = counter.newVisID();
		int dataset_id = counter.getData_ID() - 1;
		VizTable visRow = new VizTable(visID, counter.getQ_ID() - 1, chart.getHeight(), chart.getWidth());
		session.save(visRow);
		
		//which datasets are related to viz
	

		// scale table
		List<ScaleChart> scales = chart.getScales();
		for (ScaleChart sc : scales) {
			int sID = counter.newS_ID();
			
			if (sc.getRange().getClass().toString().contains("String")) {
				//sc.getDomain().getField()
				List idData = session.createSQLQuery("select max(iddataset) from DatasetTable where name = 'table'").list();
				Iterator<DisplayForm[]> ite =idData.iterator();
				Object test = ite.next();
				int d_id = Integer.parseInt(test.toString());
				ScaleTable scaleRow = new ScaleTable(sID, d_id, sc.getType(), sc.getName(), sc.getDomain().getField(),
						"empty", sc.getRange().toString());
				session.save(scaleRow);
			} else {
				
				List idData = session.createSQLQuery("select max(iddataset) from DatasetTable where name = 'mapping'").list();
				Iterator<DisplayForm[]> ite =idData.iterator();
				Object test = ite.next();
				int d_id = Integer.parseInt(test.toString());
				
				
				ScaleTable scaleRow = new ScaleTable(sID, d_id, sc.getType(), sc.getName(), sc.getDomain().getField(),
						sc.getRange().toString(), "empty");
				session.save(scaleRow);
			}

		}

		// axis table
		List<AxisChart> axis = chart.getAxes();
		for (AxisChart axe : axis) {
			int axeID = counter.newaxis_ID();
			int axeUsageID = counter.newaxis_Usage_ID();
			String title = axe.getTitle();
			Integer ticks = axe.getTicks();
			String type = axe.getType();
			String scalename = axe.getScale();

			AxisTable AXRow = constructAxisRow(axeID, title, ticks, type, session, scalename);
			AxisUsageTable AXUsageRow = new AxisUsageTable(axeUsageID, axeID, visID, type);

			session.saveOrUpdate(AXRow);
			session.saveOrUpdate(AXUsageRow);

		}

		session.getTransaction().commit();
		session.close();

		// mark table
		List<MarkGeneral> marks = chart.getMarks();
		store_marks_toDB(marks, visID);
	}

	/**
	 * Takes an QueryForm object and put all information into the database
	 * 
	 * @param query
	 */
	public void objectToDB(QueryForm query, VegaLiteChart vis) {

		Session session = vizDBUtil.getSessionFactory().openSession();
		session.beginTransaction();

		int q_id = counter.newQ_ID();
		int v_id = counter.newVisID();

		QueryTable queryRow = new QueryTable(q_id/* , v_id */);
		session.save(queryRow);

		for (String dim : query.getSelectedAtt()) {
			int s_id = counter.newSelectionID();
			if (dim.contains("count(*)")) {
				dim = "count(*)";
			} else if (dim.contains(".")) {
				String[] dims = dim.split("\\.");
				dim = dims[1];
			}
			Query_SelectTable selectRow = new Query_SelectTable(s_id, q_id, dim);
			session.save(selectRow);
		}

		for (String table : query.getTables()) {
			int f_id = counter.newF_ID();
			Query_FromTable fromRow = new Query_FromTable(f_id, q_id, table);
			session.save(fromRow);

		}

		for (Entry<String, String> entry : query.getCondAtt().entrySet()) {
			int w_id = counter.newW_ID();
			Query_ConditionTable condition;
			if (entry.getKey().contains("$")) {
				String[] dimOp = entry.getKey().split("\\$");
				String value = entry.getValue();
				condition = new Query_ConditionTable(w_id, q_id, dimOp[1], dimOp[0], value);
			} else if (entry.getKey().contains("not,")) {
				condition = new Query_ConditionTable(w_id, q_id, entry.getKey().replace("not,", ""), "!=",
						entry.getValue());
			} else {
				condition = new Query_ConditionTable(w_id, q_id, cleanDimension(entry.getKey()), "=", entry.getValue());
			}
			session.save(condition);
		}

		// VizToDB(vis, v_id);

		session.getTransaction().commit();
	}

	public void queryToDB(QueryFromDB query) {

		Session session = vizDBUtil.getSessionFactory().openSession();
		session.beginTransaction();

		int q_id = counter.newQ_ID();
		// int v_id = counter.newVisID();

		QueryTable queryRow = new QueryTable(q_id);
		session.save(queryRow);

		for (String dim : query.getSelect()) {
			int s_id = counter.newSelectionID();
			Query_SelectTable selectRow = new Query_SelectTable(s_id, q_id, dim);
			session.save(selectRow);
		}

		for (String table : query.getFrom()) {
			int f_id = counter.newF_ID();
			Query_FromTable fromRow = new Query_FromTable(f_id, q_id, table);
			session.save(fromRow);

		}

		for (Condition con : query.getCondition()) {
			int w_id = counter.newW_ID();
			Query_ConditionTable condition = new Query_ConditionTable(w_id, q_id, con.getDimension(), con.getOperator(),
					con.getValue());
			session.save(condition);
		}

		// objectToDB(new VegaTemplateLite().generatChart2(query.getVis()),
		// v_id);

		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Creates a QueryFromDB object from information from the database
	 * 
	 * @param number
	 * @return
	 */
	public QueryFromDB getQueryInfo(int number) {
		// SessionFactory factory = config.buildSessionFactory();
		// Session session = factory.getCurrentSession();
		Session session = vizDBUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Object> querySelectResult;

		// Select
		Query select = session.createQuery("SELECT dimension FROM Query_SelectTable WHERE q_id=" + number);
		QueryFromDB query = new QueryFromDB();
		querySelectResult = select.list();
		
		for (Object selectRow : querySelectResult) {
			query.getSelect().add((String) selectRow);
		}

		// From
		Query from = session.createQuery("SELECT froms FROM Query_FromTable WHERE q_id=" + number);
		querySelectResult = from.list();
		for (Object selectRow : querySelectResult) {

			query.getFrom().add((String) ((String) selectRow));
		}

		// Where
		Query condition = session.createQuery(
				"SELECT new List(dimension, operator, value) FROM Query_ConditionTable WHERE q_id=" + number);
		querySelectResult = condition.list();
		for (Object conditionRow : querySelectResult) {
			List<Object> row = (List<Object>) conditionRow;
			String dimension = (String) row.get(0);
			String operator = (String) row.get(1);
			String value = (String) row.get(2);
			Condition conditionList = new Condition(dimension, operator, value);
			query.getCondition().add(conditionList);
		}

		query.setVis(GetVisFromDB(number));
		session.close();
		return query;
	}

	/**
	 * converts QueryFrom object to QueryFromDB object
	 * 
	 * @param query
	 * @return
	 */

	public QueryFromDB QueryFormToQuery(QueryForm query) {
		QueryFromDB queryOutPut = new QueryFromDB();
		for (String dim : query.getSelectedAtt()) {
			if (dim.contains("count(*)")) {
				dim = "count(*)";
			} else if (dim.contains(".")) {
				String[] dims = dim.split("\\.");
				dim = dims[1];
			}
			queryOutPut.getSelect().add(dim);
		}

		for (String table : query.getTables()) {
			queryOutPut.getFrom().add(table);
		}

		for (Entry<String, String> entry : query.getCondAtt().entrySet()) {
			Condition con0;
			if (entry.getKey().contains("$")) {
				String[] dimOp = entry.getKey().split("\\$");
				String value = entry.getValue();
				con0 = new Condition(dimOp[1], dimOp[0], value);
			} else if (entry.getKey().contains("not,")) {

				con0 = new Condition(entry.getKey().replace("not,", ""), "!=", entry.getValue());
			} else {
				con0 = new Condition(cleanDimension(entry.getKey()), "=", entry.getValue());
			}
			queryOutPut.getCondition().add(con0);
		}

		return queryOutPut;

	}

	public static String cleanDimension(String dimension) {
		if (dimension.contains(".")) {
			String[] dims = dimension.split("\\.");
			dimension = dims[1];
		}
		return dimension;
	}

	/*******
	 * update racine it should be only used at the level of rendering matrix
	 *******/
	public void updateRacinePath() {
		int intermidiar = counter.getQ_ID() - 1;
		this.setParentqueryID(intermidiar);
	}

	/*********** store user's navigation **********/

	public void updateQueryPath() {
		Session session = vizDBUtil.getSessionFactory().openSession();
		session.beginTransaction();

		int q_id = counter.getQ_ID() - 1;

		int path_id = counter.newpathID();
		QueryPath queryPath = new QueryPath(path_id, q_id, this.parentqueryID);
		session.save(queryPath);
		session.getTransaction().commit();
		session.close();
	}

	/************* store data to render in the database *******************/
	public void dataSetToDB(List<DisplayForm> data,String nameDS) {

		Session session = vizDBUtil.getSessionFactory().openSession();
		session.beginTransaction();

		int data_id = counter.newdataID();
		//String name = "dataset" + data_id;

		DisplayForm[] dataArr = new DisplayForm[data.size()];
		dataArr = data.toArray(dataArr);
		DatasetTable datasetRow = new DatasetTable(data_id, nameDS, dataArr);
		session.save(datasetRow);

		// this part is important to re-use a dataset
		Query query = session.createQuery("select value from DatasetTable where iddataset = '" + data_id + "'");
		query.setMaxResults(1);

		List<DisplayForm[]> rows = (List<DisplayForm[]>) query.list();

		Iterator<DisplayForm[]> ite = rows.iterator();
		List<DisplayForm> rowss = new ArrayList<DisplayForm>();
		while (ite.hasNext()) {

			DisplayForm[] obj = (DisplayForm[]) ite.next();
			rowss.addAll(Arrays.asList(obj));

		}

		session.getTransaction().commit();
		session.close();
	}

	// initialization of dataset table with width and lentgh literals
	public void Initialization_dataSetToDB() {
		Session session = vizDBUtil.getSessionFactory().openSession();
		session.beginTransaction();

		int data_id = counter.newdataID();
		int data_id2 = counter.newdataID();
		String literal = "width";
		String literal2 = "height";

		DatasetTable datasetRow = new DatasetTable(data_id, literal);
		DatasetTable datasetRow2 = new DatasetTable(data_id2, literal2);
		session.save(datasetRow);
		session.save(datasetRow2);
		session.getTransaction().commit();
		session.close();

	}

	// to insert channel information in channel table
	public ChannelTable constructChannelRow(int chID, String type, Object field, Session sess, int scid) {

		String fieldname;
		ChannelTable ch = new ChannelTable();
		int i = /*Integer.parseInt(test.toString())*/scid;
		if (field != null) {
			fieldname = field.toString();
			ch = new ChannelTable(chID, i, fieldname, type);

		} else {
			ch = new ChannelTable(chID, i, type);
		}

		return ch;
	}

	// construct axis tuples
	public AxisTable constructAxisRow(int axeID, String title, Integer ticks, String type, Session sess,
			String scalename) {

		Query query = sess.createQuery("select idscale from ScaleTable where namescale = '" + scalename + "'");

		query.setMaxResults(1);

		AxisTable ch = new AxisTable();
		List<Object> rows = query.list();

		Iterator<Object> ite = rows.iterator();
		Object test = ite.next();

		int i = Integer.parseInt(test.toString());
		if (ticks != null) {

			ch = new AxisTable(axeID, i, ticks, title, type);

		} else {

			ch = new AxisTable(axeID, i, title, type);

		}

		return ch;

	}

	/********* update last rendered visualization ************/
	public void update_Viz_DB(VegaChart chart) {
		// TODO Auto-generated method stub
		Session session = vizDBUtil.getSessionFactory().openSession();
		session.beginTransaction();

		int v_id = counter.getVis_ID() - 1;

		Query query1 = session.createQuery("select idmark from MarkTable where idvis= '" + v_id + "'");
		List<Object> rows = query1.list();

		Iterator<Object> ite = rows.iterator();
		while (ite.hasNext()) {

			Object test = ite.next();
			int m_id = Integer.parseInt(test.toString());

			Query q1 = session.createQuery("select idchannel from ChannelUsageTable  where idmark= '" + m_id + "'");
			List<Object> chs = q1.list();

			Iterator<Object> ids = chs.iterator();
			while (ids.hasNext()) {

				Object code = ids.next();
				int ch_id = Integer.parseInt(code.toString());
				Query query11 = session.createQuery("delete ChannelTable where idchannel= '" + ch_id + "'");
				int result11 = query11.executeUpdate();
			}
			Query query = session.createQuery("delete ChannelUsageTable where idmark= '" + m_id + "'");
			int result = query.executeUpdate();
		}

		Query query = session.createQuery("delete MarkTable where idvis= '" + v_id + "'");
		int result = query.executeUpdate();


		session.getTransaction().commit();
		session.close();
		store_marks_toDB(chart.getMarks(), v_id);

	}

	private void store_marks_toDB(List<MarkGeneral> marks, int visID) {
		// TODO Auto-generated method stub
		Session session = vizDBUtil.getSessionFactory().openSession();
		session.beginTransaction();
		for (MarkGeneral ma : marks) {

			int mID = counter.newMarkPropID();
			MarkTable markRow = new MarkTable(mID, visID, ma.getType());

			session.save(markRow);

			PropChart encoding = ma.getProperties();
			if (encoding != null) {
				ActionChart enc = encoding.getEnter();
				ActChart dx = enc.getDx();
				if (dx != null) {
					if (dx.getScale() != null) { // store provenance
						int chID = counter.newEncodingID();
						int chUsageID = counter.newchannelUsage_ID();
						String namescale = dx.getScale();

						int scid2= getscaleID(session, namescale);
						
						ChannelTable chRow = constructChannelRow(chID, "dx", dx.getField(), session, scid2);

						ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID,
								mID, chRow.getChanneltype());

						session.saveOrUpdate(chRow);
						session.saveOrUpdate(usageEncoRow);
					}

					else {
						int chID = counter.newEncodingID();
						int chUsageID = counter.newchannelUsage_ID();
						ChannelTable chRow = new ChannelTable("dx", dx.getValue().toString(), chID);
						session.saveOrUpdate(chRow);
					}
				}
				ActChart dy = encoding.getEnter().getDy();
				if (dy != null) {
					if (dy.getScale() != null) { // store encoding
						int chID = counter.newEncodingID();
						int chUsageID = counter.newchannelUsage_ID();
						String namescale = dy.getScale();
						int scid= getscaleID(session, namescale);
						ChannelTable chRow = constructChannelRow(chID, "dy", dy.getField(), session, /*, namescale*/scid);

						ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID,
								mID, chRow.getChanneltype());

						session.saveOrUpdate(chRow);
						session.saveOrUpdate(usageEncoRow);
					}

					else {
						int chID = counter.newEncodingID();
						int chUsageID = counter.newchannelUsage_ID();
						ChannelTable chRow = new ChannelTable("dy", dy.getValue().toString(), chID);
						session.saveOrUpdate(chRow);
					}
				}

				ActChart wi = encoding.getEnter().getWidth();
				if (wi != null) {
					if (wi.getScale() != null) {
						int chID = counter.newEncodingID();
						int chUsageID = counter.newchannelUsage_ID();
						String namescale = wi.getScale();
						int scid= getscaleID(session, namescale);
						ChannelTable chRow = constructChannelRow(chID, "witdh", wi.getField(), session, /*namescale*/scid);
						ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID,
								mID, chRow.getChanneltype());

						session.saveOrUpdate(chRow);
						session.saveOrUpdate(usageEncoRow);
					}

					else {
						int chID = counter.newEncodingID();
						int chUsageID = counter.newchannelUsage_ID();
						ChannelTable chRow = new ChannelTable("witdh", wi.getValue().toString(), chID);
						ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID,
								mID, chRow.getChanneltype());

						session.saveOrUpdate(chRow);
						session.saveOrUpdate(usageEncoRow);
					}

				}
				ActChart fill = encoding.getEnter().getFill();
				if (fill != null) {
					if (fill.getScale() != null) { // store provenance
						int chID = counter.newEncodingID();
						int chUsageID = counter.newchannelUsage_ID();
						String namescale = fill.getScale();
						int scid= getscaleID(session, namescale);
						ChannelTable chRow = constructChannelRow(chID, "fill", fill.getField(), session,/* namescale*/scid);

						ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID,
								mID, chRow.getChanneltype());

						session.saveOrUpdate(chRow);
						session.saveOrUpdate(usageEncoRow);
					}

					else {

						int chID = counter.newEncodingID();
						int chUsageID = counter.newchannelUsage_ID();
						ChannelTable chRow = new ChannelTable("fill", fill.getValue().toString(), chID);
						ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID, mID,
								chRow.getChanneltype());

						session.saveOrUpdate(chRow);
						session.saveOrUpdate(usageEncoRow);

					}
				}
				ActChart x = encoding.getEnter().getX();
				if (x != null) {

					// store provenance
					int chID = counter.newEncodingID();
					int chUsageID = counter.newchannelUsage_ID();
					String namescale = x.getScale();
					int scid= getscaleID(session, namescale);
					ChannelTable chRow = constructChannelRow(chID, "x", x.getField(), session,scid/* namescale*/);

					ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID,
							mID, chRow.getChanneltype());

					session.saveOrUpdate(chRow);
					session.saveOrUpdate(usageEncoRow);

				}

				ActChart y = encoding.getEnter().getY();
				if (y != null) {

					// store provenance
					int chID = counter.newEncodingID();
					int chUsageID = counter.newchannelUsage_ID();
					String namescale = y.getScale();
					int scid= getscaleID(session, namescale);
					ChannelTable chRow = constructChannelRow(chID, "y", y.getField(), session,scid/* namescale*/);

					ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID,
							mID, chRow.getChanneltype());

					session.saveOrUpdate(chRow);
					session.saveOrUpdate(usageEncoRow);

				}

				ActChart y2 = encoding.getEnter().getY2();
				if (y2 != null) {

					// store provenance
					int chID = counter.newEncodingID();
					int chUsageID = counter.newchannelUsage_ID();
					String namescale = y2.getScale();
					int scid= getscaleID(session, namescale);
					ChannelTable chRow = constructChannelRow(chID, "y2", y2.getField(), session,scid /*namescale*/);

					ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID,
							mID, chRow.getChanneltype());

					session.saveOrUpdate(chRow);
					session.saveOrUpdate(usageEncoRow);

				}

				ActChart size = encoding.getEnter().getSize();
				if (size != null) {
					if (size.getScale() != null) {// store provenance
						int chID = counter.newEncodingID();
						int chUsageID = counter.newchannelUsage_ID();
						String namescale = size.getScale();
						int scid= getscaleID(session, namescale);
						ChannelTable chRow = constructChannelRow(chID, "size", size.getField(), session,scid /* namescale*/);
						ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID,
								mID, chRow.getChanneltype());

						session.saveOrUpdate(chRow);
						session.saveOrUpdate(usageEncoRow);
					}

					else {
						int chID = counter.newEncodingID();
						int chUsageID = counter.newchannelUsage_ID();
						ChannelTable chRow = new ChannelTable("size", size.getValue().toString(), chID);
						ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID,
								mID, chRow.getChanneltype());

						session.saveOrUpdate(chRow);
						session.saveOrUpdate(usageEncoRow);
					}
				}
				ActChart stroke = encoding.getEnter().getStroke();
				if (stroke != null) {
					if (stroke.getScale() != null) { // store provenance
						int chID = counter.newEncodingID();
						int chUsageID = counter.newchannelUsage_ID();
						String namescale = stroke.getScale();
						int scid= getscaleID(session, namescale);
						ChannelTable chRow = constructChannelRow(chID, "stroke", stroke.getField(), session,scid /*namescale*/);
						ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID,
								mID, chRow.getChanneltype());

						session.saveOrUpdate(chRow);
						session.saveOrUpdate(usageEncoRow);
					}

					else {
						int chID = counter.newEncodingID();
						int chUsageID = counter.newchannelUsage_ID();
						ChannelTable chRow = new ChannelTable("stroke", stroke.getValue().toString(), chID);
						ChannelUsageTable usageEncoRow = new ChannelUsageTable(chUsageID, chID,
								mID, chRow.getChanneltype());

						session.saveOrUpdate(chRow);
						session.saveOrUpdate(usageEncoRow);

					}

				}

			}
		}
		session.getTransaction().commit();
		session.close();
	}

	private int getscaleID(Session sess,String namescale) {
		int i =0;
		Query query = sess.createQuery("select max(idscale) from ScaleTable where namescale = '" + namescale + "'");
		query.setMaxResults(1);

		List<Object> rows = query.list();

		Iterator<Object> ite = rows.iterator();
		Object test = ite.next();
	
		
		i=Integer.parseInt(test.toString());
		return i;
	}

	/********* remove a rendered visualization ************/
	public void Remove_Viz_DB() {
		// TODO Auto-generated method stub
		Session session = vizDBUtil.getSessionFactory().openSession();
		session.beginTransaction();

		int v_id = counter.getVis_ID() - 1;

		Query query1 = session.createQuery("select idmark from MarkTable where idvis= '" + v_id + "'");
		List<Object> rows = query1.list();

		Iterator<Object> ite = rows.iterator();
		while (ite.hasNext()) {

			Object test = ite.next();
			int m_id = Integer.parseInt(test.toString());
			Query query = session.createQuery("delete ChannelUsageTable where idmark= '" + m_id + "'");
			int result = query.executeUpdate();
		}

		Query query = session.createQuery("delete MarkTable where idvis= '" + v_id + "'");
		int result = query.executeUpdate();

		Query query2 = session.createQuery("delete AxisUsageTable where idvisualization= '" + v_id + "'");
		int result2 = query2.executeUpdate();

		Query query3 = session.createQuery("delete VizTable where idvisualization= '" + v_id + "'");
		int result3 = query3.executeUpdate();

		DistinctValues.connector.counter.setVis(v_id);

		session.getTransaction().commit();
		session.close();
	}

	/************ get visualization from database ***************/
	private VisualizationBean GetVisFromDB(int v_id) {
		// TODO Auto-generated method stub
		List<Integer> marks = new ArrayList<Integer>();
		List<Integer> axis = new ArrayList<Integer>();
		List<Integer> scales = new ArrayList<Integer>();
		Set<Integer> scalesDistincts = new HashSet<Integer>();
		Session session = vizDBUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query1 = session.createQuery("from VizTable where idvisualization= '" + v_id + "'");
		List<VizTable> rows = query1.list();

		Iterator<VizTable> iterator = rows.iterator();
		VizTable viz = iterator.next();

		List mm = session.createQuery("select idmark from MarkTable where idvis= '" + v_id + "'").list();
		Iterator ite = mm.iterator();

		while (ite.hasNext()) {

			Object test = ite.next();
			int m_id = Integer.parseInt(test.toString());
			marks.add(m_id);
			
			List scs = session.createSQLQuery("select ChannelTable.scale from ChannelTable,ChannelUsageTable where ChannelTable.idchannel=ChannelUsageTable.idchannel "
					+ "and   ChannelUsageTable.idmark= '" + m_id + "'").list();
			
			Iterator ite12 = scs.iterator();
			while (ite12.hasNext()) {
			Object idsca = ite12.next();
			int sc_id = Integer.parseInt(idsca.toString());
			//scales.add(sc_id);
			scalesDistincts.add(sc_id);}
			
		}
		List axs = session.createSQLQuery(
				"select AxisTable.idaxis from AxisTable, AxisUsageTable  where AxisTable.idaxis=AxisUsageTable.idaxis and AxisUsageTable.idvisualization= '"
						+ v_id + "'")
				.list();
		Iterator ite1 = axs.iterator();
		while (ite1.hasNext()) {
			Object test = ite1.next();
			int ax_id = Integer.parseInt(test.toString());
			axis.add(ax_id);	
		}

		session.close();
		scales.addAll(scalesDistincts);
		VisualizationBean vv = new VisualizationBean(v_id, viz.getWidth(), viz.getHeight(), axis, marks, scales);
		return vv;

	}

}
