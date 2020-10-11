package digital.wander.covid.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import digital.wander.covid.models.GraphData;
import digital.wander.covid.models.ReportedData;
import digital.wander.covid.models.StateReportedData;

@Component
public class DashboardDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public ReportedData getOverAllData() {
		ReportedData rd = null;
		try {
			rd = jdbcTemplate.queryForObject(
					"SELECT sum(confirmed) c, sum(recovered) r , sum(active) a, sum(deceased) d  FROM covid.cases_state",
					new Object[] {}, new RowMapper<ReportedData>() {

						@Override
						public ReportedData mapRow(ResultSet rs, int rowNum) throws SQLException {
							ReportedData rd = new ReportedData();

							rd.setConfirmed(rs.getInt("c"));
							rd.setActive(rs.getInt("a"));
							rd.setRecovered(rs.getInt("r"));
							rd.setDeceased(rs.getInt("d"));

							return rd;
						}

					});
		} catch (EmptyResultDataAccessException e) {
			rd = null;
		}
		return rd;
	}

	public List<StateReportedData> getStateReportedData() {
		List<StateReportedData> rd = null;
		try {
			rd = jdbcTemplate.query("SELECT * FROM covid.cases_state", new Object[] {},
					new RowMapper<StateReportedData>() {

						@Override
						public StateReportedData mapRow(ResultSet rs, int rowNum) throws SQLException {
							StateReportedData rd = new StateReportedData();

							rd.setState(rs.getString("state"));
							rd.setConfirmed(rs.getInt("confirmed"));
							rd.setActive(rs.getInt("active"));
							rd.setRecovered(rs.getInt("recovered"));
							rd.setDeceased(rs.getInt("deceased"));

							return rd;
						}

					});
		} catch (EmptyResultDataAccessException e) {
			rd = null;
		}
		return rd;
	}

	public List<GraphData> getMonthWiseData(String type) {
		List<GraphData> gd = null;
		try {
			gd = jdbcTemplate.query(
					"SELECT "+ type +  ",concat(monthname(date),\" \",year(date)) month FROM monthwise_data",
					new Object[] {}, new RowMapper<GraphData>() {

						@Override
						public GraphData mapRow(ResultSet rs, int rowNum) throws SQLException {
							GraphData rd = new GraphData();

							rd.setX(rs.getString("month"));
							rd.setY(rs.getInt(type));

							return rd;
						}

					});
		} catch (EmptyResultDataAccessException e) {
			gd = null;
		}
		return gd;
	}

}
