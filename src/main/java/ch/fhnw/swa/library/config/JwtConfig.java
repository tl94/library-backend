package ch.fhnw.swa.library.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class JwtConfig {

	private final Environment environment;
	
	private final String JWT_SECRET = "jwt.secret";
	private final String JWT_ALGORITHM = "HmacSHA256";
	private final String JWT_EXPIRATION_ACCESS_TOKEN = "jwt.expiration.access-token";
	private final String JWT_EXPIRATION_REFRESH_TOKEN = "jwt.expiration.refresh-token";
	
	public JwtConfig(Environment environment) {
		this.environment = environment;
	}

	@Bean
	public JwtEncoder jwtEncoder() {
		String secret = environment.getProperty(JWT_SECRET);
		SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), JWT_ALGORITHM);
		return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		String secret = environment.getProperty(JWT_SECRET);
		SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), JWT_ALGORITHM);
		return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
	}
	
	public long getAccessTokenExpiration() {
		return Long.parseLong(environment.getProperty(JWT_EXPIRATION_ACCESS_TOKEN));
	}
	
	public long getRefreshTokenExpiration() {
		return Long.parseLong(environment.getProperty(JWT_EXPIRATION_REFRESH_TOKEN));
	}
}
