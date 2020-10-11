package digital.wander.covid.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import digital.wander.covid.utils.TokenUtils;


//@Component
public class SessionFilter extends OncePerRequestFilter {
	
	@Autowired
	TokenUtils tokenUtils;
	
	private static List<String> allowedPaths = Arrays.asList("/login","/register");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String path = new UrlPathHelper().getPathWithinApplication(request);
		
		//logging path
		System.out.println("user visited : "+path);
		
		//filtering allowed paths only
		if(allowedPaths.contains(path) || request.getMethod().equals("OPTIONS")) {
			filterChain.doFilter(request, response);	
		}else {
			String token = request.getHeader("Authorization");
			String username = tokenUtils.getUsernameFromToken(token);
			if(tokenUtils.validateToken(token, username)) {
				filterChain.doFilter(request, response);
			}else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		}
		
		
		
		
	}
	
		
	
	

}
