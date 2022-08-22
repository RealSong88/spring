package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {

	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
//		Connection c = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/jpashop","sa", "");
		Connection c = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/springbook","sa", "sa");
		return c;
	}


}
