package ch.fhnw.swa.library.service;

import java.time.Instant;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	private final JwtEncoder jwtEncoder;

	public JwtService(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}

	// generate JWT
	public String generateToken(String username) {
		Instant now = Instant.now();
		
		JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.subject(username)
				.issuedAt(now)
				.expiresAt(now.plusSeconds(3600 * 24 * 7))
				.build();
		return this.jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
	}
}
