package digital.wander.covid;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
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
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testLogin() throws Exception {

		String mockInputJson = "";

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();
		map.put("username", "praveensoni1992@gmail.com");
		map.put("password", "network18");

		try {
			mockInputJson = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mockInputJson)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

		Map<String, Object> resultMap = mapper.readValue(result.getResponse().getContentAsString(), Map.class);
		boolean isValid = false;
		if (resultMap.get("isValid") instanceof Boolean) {
			isValid = (Boolean) resultMap.get("isValid");
		}
		assertTrue(isValid);

	}

}
