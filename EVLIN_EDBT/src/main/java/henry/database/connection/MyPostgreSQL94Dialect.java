package henry.database.connection;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL9Dialect;

public class MyPostgreSQL94Dialect extends PostgreSQL9Dialect {

	public MyPostgreSQL94Dialect() {
		this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
	}
}