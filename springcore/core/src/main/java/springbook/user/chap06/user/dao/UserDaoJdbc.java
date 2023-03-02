package springbook.user.chap06.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.chap06.user.domain.Level;
import springbook.user.chap06.user.domain.User;



public class UserDaoJdbc implements UserDao {

	private JdbcTemplate jdbcTemplate;

	private RowMapper<User> userMapper =
			new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			user.setEmail(rs.getString("email"));
			return user;
		}
	};
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void add(User user) {
		this.jdbcTemplate.update("insert into user(id, name, password, level, login, recommend, email) values(?,?,?,?,?,?,?)"
				, user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue()
				, user.getLogin(), user.getRecommend(), user.getEmail());

	}

	@Override
	public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from user where id = ?",
				new Object[] {id}, this.userMapper);
	}

	@Override
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from user order by id", this.userMapper);
	}

	@Override
	public void deleteAll() {
		this.jdbcTemplate.update("delete from user");

	}

	@Override
	public int getCount() {
		return this.jdbcTemplate.queryForObject("select count(*) from user", Integer.class);
	}

	@Override
	public void update(User user) {
		this.jdbcTemplate.update("update user set name = ?, password = ?, level = ?, login = ?, "
				+ "recommend = ? where id = ? ", user.getName(), user.getPassword(), user.getLevel().intValue()
				, user.getLogin(), user.getRecommend(), user.getId());
//		this.jdbcTemplate.update("update user set name = ?, password = ?, level = ?, login = ?, "
//				+ "recommend = ?", user.getName(), user.getPassword(), user.getLevel().intValue()
//				, user.getLogin(), user.getRecommend());
		
	}

}
