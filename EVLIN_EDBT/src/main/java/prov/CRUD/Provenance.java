package prov.CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import prov.mybean.DBForm;
import prov.mybean.QueryForm;

public class Provenance {

	DBForm dbobj;
	
	
	
	public Connection connex() {
		Connection connection = connection();

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			return connection;
		} else {
			System.out.println("Failed to make connection!");
			return null;
		}

		
	}
	
	private  Connection connection() {
		System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
			return null;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			//connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/USflight");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+this.dbobj.getName());
			return connection;

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;

		}
	}

	
	public void ConstructCsv( String state ){
		Connection connection=connex();
		
		String requete = "Copy (Select provenance count(*) as cancellation_num from airports, flights where airports.iata=flights.origin "
				+ "and cancelled='1' and state='" + state
				+ "' )  TO '/Users/houssem/Desktop/provenanceDynamic.csv' DELIMITER ',' CSV HEADER;";
		System.out.println(requete);
		try {

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(requete);

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void ConstructCsvV2(QueryForm query) {
	Connection connection=connex();
		
	QueryForm qq= new QueryForm();
	qq.setJoinTables(query.getJoinTables());
	qq.setCondAtt(query.getCondAtt());
	qq.setGroupbyAtt(query.getGroupbyAtt());
	qq.setOrderbyAtt(query.getOrderbyAtt());
	qq.setTables(query.getTables());

		List<String> select=new ArrayList<String>();
		for ( String elt : query.getSelectedAtt() )
		{
			
			select.add(" provenance "+elt);
		}
		qq.setSelectedAtt(select);
		
		try {
String requete2="Copy("+qq.display().trim()+")  TO '/Users/houssem/Desktop/provenanceDynamic.csv' DELIMITER ',' CSV HEADER;";

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(requete2);

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public DBForm getDbobj() {
		return dbobj;
	}

	public void setDbobj(DBForm dbobj) {
		this.dbobj = dbobj;
	}

	public Provenance(DBForm dbobj) {
		super();
		this.dbobj = dbobj;
	}
	
	
	
}
