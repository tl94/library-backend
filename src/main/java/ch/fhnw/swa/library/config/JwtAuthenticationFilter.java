package ch.fhnw.swa.library.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimAccessor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import ch.fhnw.swa.library.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final UserService userService;
	private final JwtDecoder jwtDecoder;

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	private final List<String> protectedUrls = Arrays.asList("/library/api/books", "/library/api/images");
	
	public JwtAuthenticationFilter(UserService userService, JwtDecoder jwtDecoder) {
		this.userService = userService;
		this.jwtDecoder = jwtDecoder;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		
		if (protectedUrls.stream().anyMatch(requestUri::startsWith)) {
			String accessToken = null;
			
			Cookie[] cookies = request.getCookies();
			
			if (cookies != null) {
				for (Cookie cookie : cookies) {

					if ("accessToken".equals(cookie.getName())) {
						accessToken = cookie.getValue();
						break;
					}
				}
			}
			
			if (accessToken != null) {
				
				Jwt jwt = jwtDecoder.decode(accessToken);
				ObjectId userId = new ObjectId(jwt.getSubject());
				
				if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = this.userService.loadUserById(userId);
					
					if (jwt.getSubject().equals(userId.toString())) {
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
	                    logger.info("Authentication successful for user: {}", userDetails.getUsername());
					}
				}
			}
		}
		filterChain.doFilter(request, response);		
	}
	
	
}
