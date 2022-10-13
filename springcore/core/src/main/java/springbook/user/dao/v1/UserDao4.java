package springbook.user.dao.v1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.User;

public class UserDao4 {

	private JdbcTemplate jdbcTemplate;

	private JdbcContext jdbcContext;

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);

		this.jdbcContext = new JdbcContext();

		this.jdbcContext.setDataSource(dataSource);

		this.dataSource = dataSource;
	}

	public void add(User user) throws ClassNotFoundException, SQLException {
		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {

			@Override
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");

				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());

				return ps;
			}
		});
	}

	public void del(User user) throws ClassNotFoundException, SQLException {
		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
			@Override
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement("delete from users where id = ?");
				ps.setString(1, user.getId());

				ps.executeUpdate();
				return ps;
			}
		});

	}

	public User get(String id) throws ClassNotFoundException, SQLException {
//		Connection c = getConnection();
//		Connection c = simpleConnectionMaker.makeNewConnection();
//		Connection c = connectionMaker.makeConnection();
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();

		User user = null;

		if (rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}

		rs.close();
		ps.close();
		c.close();

		if (user == null)
			throw new EmptyResultDataAccessException(1);

		return user;
	}

	public void deleteAll() throws SQLException {
//		executeSql("delete from users");
		this.jdbcContext.executeSql("delete from users");
//		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
//			@Override
//			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//				PreparedStatement ps = c.prepareStatement("delete from users");
//				return ps;
//			}
//		});
	}

	public List<User> getAll() throws SQLException {
		return this.jdbcTemplate.query("select * from users order by id",
				new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		});
	}

	public int getCount() throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = dataSource.getConnection();
			ps = c.prepareStatement("select count(*) from users");

			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
				}
			}
		}
	}

//	@SuppressWarnings("unused")
//	private void executeSql(final String query) throws SQLException {
//		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
//
//			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//				return c.prepareStatement(query);
//			}
//		});
//	}
}
