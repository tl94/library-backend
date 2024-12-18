package ch.fhnw.swa.library.service;

import java.time.Instant;
import java.util.List;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import ch.fhnw.swa.library.entity.User;

@Service
public class JwtService {

	private final JwtEncoder jwtEncoder;

	public JwtService(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}

	// generate JWT
	public String generateToken(User user) {
		Instant now = Instant.now();
		
		JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
		
		String id = user.getId().toString();
		String username = user.getUsername();
		List<String> roles = user.getRoles();
		boolean isNonLocked = user.isAccountNonLocked();
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.subject(username)
				.claim("id", id)
				.claim("username", username)
				.claim("roles", roles)
				.claim("isNonLocked", isNonLocked)
				.issuedAt(now)
				.expiresAt(now.plusSeconds(3600 * 24 * 7))
				.build();
		return this.jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
	}
}
