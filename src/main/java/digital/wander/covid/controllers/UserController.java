/**
 * 
 */
package digital.wander.covid.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import digital.wander.covid.dao.UserDao;
import digital.wander.covid.models.UserInfo;
import digital.wander.covid.utils.TokenUtils;

/**
 * @author praveensoni
 *
 */

@CrossOrigin(origins = "http://wander.praveensoni.in", allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class UserController {

	@Autowired
	UserDao userDao;

	@Autowired
	TokenUtils tokenUtils;

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public String login(@RequestBody Map<String, String> json) throws Exception {

		String userName = json.get("username");
		String pass = json.get("password");

		String msg = "";
		boolean isValid = false;
		String token = "";

		// validate user
		UserInfo userInfo = userDao.validateUser(userName, pass);

		// create session
		if (userInfo != null) {
			// httpSession.setAttribute("user", userInfo);
			msg = "Login Successful.";
			isValid = true;
			token = tokenUtils.generateToken(userName);
		} else {
			msg = "Login Failed. User name and password does not match";
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();
		map.put("msg", msg);
		map.put("isValid", isValid);
		map.put("token", token);

		try {
			return mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public String register(@RequestBody Map<String, String> json) throws Exception {

		String userName = json.get("username");
		String pass = json.get("password");

		String msg = "";
		boolean isSuccess = false;

		UserInfo userInfo = new UserInfo(userName, pass);
		try {
			userDao.insertUser(userInfo);
			msg = "Registered Successfully";
			isSuccess = true;

		} catch (DuplicateKeyException e) {
			msg = "Email already registered";
		} catch (Exception e) {
			msg = "Some Error Occured";
		}

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();
		map.put("msg", msg);
		map.put("isSuccess", isSuccess);

		try {
			return mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

}
