package digital.wander.covid;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class DashboardControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testOverAllData() throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/overalldata").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

		Map<String, Object> resultMap = mapper.readValue(result.getResponse().getContentAsString(), Map.class);

		assertTrue(resultMap.containsKey("confirmed") && resultMap.containsKey("active")
				&& resultMap.containsKey("recovered") && resultMap.containsKey("deceased"));

	}

	@Test
	public void testStatewiseData() throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/statewisedata").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

		List<Object> results = mapper.readValue(result.getResponse().getContentAsString(), List.class);

		assertTrue(results.size() > 0);

	}

	@Test
	public void testMonthwiseData() throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/monthwisedata/confirmed")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

		List<Object> results = mapper.readValue(result.getResponse().getContentAsString(), List.class);

		assertTrue(results.size() > 0);

	}

}
