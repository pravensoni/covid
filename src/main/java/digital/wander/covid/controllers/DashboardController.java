/**
 * 
 */
package digital.wander.covid.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import digital.wander.covid.dao.DashboardDao;
import digital.wander.covid.models.GraphData;
import digital.wander.covid.models.ReportedData;
import digital.wander.covid.models.StateReportedData;

/**
 * @author praveensoni
 *
 */

@CrossOrigin(origins = "http://wander.praveensoni.in", allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class DashboardController {

	@Autowired
	DashboardDao dashboardDao;

	@RequestMapping(method = RequestMethod.GET, path = "/overalldata")
	public String overallData() {

		ReportedData reportedData = dashboardDao.getOverAllData();

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.writeValueAsString(reportedData);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

	@RequestMapping(method = RequestMethod.GET, path = "/statewisedata")
	public String statewiseData() {

		List<StateReportedData> reportedData = dashboardDao.getStateReportedData();

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.writeValueAsString(reportedData);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/monthwisedata/{type}")
	public String monthwiseData(@PathVariable("type") String type) {

		List<GraphData> reportedData = dashboardDao.getMonthWiseData(type);

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.writeValueAsString(reportedData);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

}
