package digital.wander.covid.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import digital.wander.covid.models.UserInfo;

@Component
public class UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public UserInfo validateUser(String userName, String pass) {
		UserInfo userInfo = null;
		try {
			userInfo = jdbcTemplate.queryForObject("select id,username from users where username=? and password=? ",
					new Object[] { userName, pass }, new RowMapper<UserInfo>() {

						@Override
						public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
							UserInfo userInfo = new UserInfo();
							userInfo.setUserName(rs.getString("username"));
							return userInfo;
						}

					});
		} catch (EmptyResultDataAccessException e) {
			userInfo = null;
		}
		return userInfo;
	}

	public Long insertUser(UserInfo userInfo) throws DuplicateKeyException {
		KeyHolder holder = new GeneratedKeyHolder();
		Long custumerId = null;

		int i = jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO users (username, password) VALUES (?,?)";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, userInfo.getUserName());
				ps.setString(2, userInfo.getPassword());

				return ps;
			}
		}, holder);
		if (i == 1) {
			custumerId = holder.getKey().longValue();
		}

		return custumerId;
	}

}
