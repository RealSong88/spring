package springbook.user.dao.v1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.domain.User;

public class UserDao {

//	private ConnectionMaker connectionMaker;

//	public UserDao(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}

//	public void setConnectionMaker(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void add(User user) throws ClassNotFoundException, SQLException {
//		Connection c = getConnection();
//		Connection c = simpleConnectionMaker.makeNewConnection();
//		Connection c = connectionMaker.makeConnection();
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();
		System.out.println("row 생성");
	}

	public void del(User user) throws ClassNotFoundException, SQLException {
//		Connection c = connectionMaker.makeConnection();
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("delete from users where id = ?");
		ps.setString(1, user.getId());

		ps.executeUpdate();

		ps.close();
		c.close();
		System.out.println("row 삭제");
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
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = dataSource.getConnection();
			ps = c.prepareStatement("delete from users");

			ps.executeUpdate();

		} catch (SQLException e) {
			throw e;
		} finally {
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

//	public Connection getConnection() throws ClassNotFoundException, SQLException {
//		Class.forName("org.h2.Driver");
////		Connection c = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/jpashop","sa", "");
//		Connection c = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/springbook","sa", "sa");
//		return c;
//	}
//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		UserDao dao = new UserDao();
//
//		User user = new User();
//		user.setId("nadayong");
//		user.setName("송");
//		user.setPassword("song");
//
////		dao.add(user);
//
//		System.out.println(user.getId() + " 등록 성공");
//
//		User user2 = dao.get(user.getId());
//		System.out.println(user2.getName());
//	}
}
