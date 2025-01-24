package ch.fhnw.swa.library.controller;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.fhnw.swa.library.entity.User;
import ch.fhnw.swa.library.entity.UserDTO;
import ch.fhnw.swa.library.repository.UserRepository;
import ch.fhnw.swa.library.service.JwtService;

@RestController
@RequestMapping("/auth")
public class LoginController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserRepository userRepository;

	public LoginController(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userRepository = userRepository;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDTO loginRequest) {
		try {
			Authentication authentication = this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
			
			User user = userRepository.findByUsername(loginRequest.username());
			
			String accessToken = jwtService.generateAccessToken(user);
			String refreshToken = jwtService.generateRefreshToken(user);
			
			HttpCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
					.httpOnly(true)
					.secure(true)
					.sameSite("Strict")
					.path("/library/api/")
					.build();
			
			HttpCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
					.httpOnly(true)
					.secure(true)
					.sameSite("Strict")
					.path("/library/api/auth/refresh")
					.build();
			
			return ResponseEntity.ok()
					.header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
					.header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
					.build();
			
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}

	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestBody String refreshToken) {
		return null;
	}
}
