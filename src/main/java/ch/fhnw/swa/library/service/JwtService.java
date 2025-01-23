package ch.fhnw.swa.library.service;

import java.time.Instant;
import java.util.List;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import ch.fhnw.swa.library.config.JwtConfig;
import ch.fhnw.swa.library.entity.User;

@Service
public class JwtService {

	private final JwtConfig jwtConfig;
	private final JwtEncoder jwtEncoder;

	public JwtService(JwtConfig jwtConfig, JwtEncoder jwtEncoder) {
		this.jwtConfig = jwtConfig;
		this.jwtEncoder = jwtEncoder;
	}

	// generate Access Token
	public String generateAccessToken(User user) {
		Instant now = Instant.now();
		
		JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
		
		String id = user.getId().toString();
		String username = user.getUsername();
		List<String> roles = user.getRoles();
		boolean isNonLocked = user.isAccountNonLocked();
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.subject(id)
				.claim("id", id)
				.claim("username", username)
				.claim("roles", roles)
				.claim("isNonLocked", isNonLocked)
				.issuedAt(now)
				.expiresAt(now.plusSeconds(jwtConfig.getAccessTokenExpiration()))
				.build();
		return this.jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
	}
	
	public String generateRefreshToken(User user) {
		Instant now = Instant.now();
		
		JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
		
		String id = user.getId().toString();
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.subject(id)
				.issuedAt(now)
				.expiresAt(now.plusSeconds(jwtConfig.getRefreshTokenExpiration()))
				.build();
		return this.jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
	}
	
	
}
