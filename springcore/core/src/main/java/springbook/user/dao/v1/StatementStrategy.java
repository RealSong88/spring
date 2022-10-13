package springbook.user.dao.v1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {

	PreparedStatement makePreparedStatement(Connection c) throws SQLException;
}
