package prov.CRUD;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProvCompute {

	public static void main(String[] argv) {

	Connection connection=connection();

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}

		execute(connection);

	}

	private static void execute(Connection connection) {
		String requete = "Copy (Select provenance count(*) as cancellation_num from airports, flights where airports.iata=flights.origin "
				+ "and cancelled='1' and state='CA' )  TO '/Users/houssem/Desktop/provenance.csv' DELIMITER ',' CSV HEADER;";
		try {

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(requete);

			rs.close();
			stmt.close();
		}
		 catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private static Connection connection() {
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

			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/USflight");
			return connection;

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;

		}
	}

}