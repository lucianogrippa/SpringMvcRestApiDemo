package helpers;

import java.security.SecureRandom;

import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import dtos.JwtUser;

/**
 * Helper da utilizzare per la generazione del token e per il parser
 * 
 * @author luciano
 */
@Service(value = "jwtHelper")
public class JwtHelper {
	private String secret;
	private RsaJsonWebKey secretKey;
	private int keySize = 2048;
	private byte[] seed = "5cb9cae9a461d5704d952f4734784e2a".getBytes();
	private String issuer = "com.grippaweb";
	private String audience = "api.auth";
	private String kid = "8697f9370b18982509478415760c2735";
	
	LogHelper logger;
	
	@Autowired
	public JwtHelper(LogHelper loggerHelper) {
		this.logger = loggerHelper;
	}

	public JwtUser parseToken(String token) throws MalformedClaimException {
		JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime() // the JWT must have an expiration
																						// time
				.setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for
													// clock skew
				.setRequireSubject() // the JWT must have a subject claim
				.setExpectedIssuer(issuer) // whom the JWT needs to have been issued by
				.setExpectedAudience(audience) // to whom the JWT is intended for
				.setVerificationKey(secretKey.getKey()) // verify the signature with the public key
				.setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
						ConstraintType.WHITELIST, AlgorithmIdentifiers.RSA_USING_SHA256) // which is only RS256 here
				.build(); // create the JwtConsumer instance

		try {
			// Validate the JWT and process it to the Claims
			JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
			logger.logDebug("JWT validation succeeded! " + jwtClaims);
			
			JwtUser user=new JwtUser();
			user.setUsername(jwtClaims.getSubject());
			user.setId(Long.valueOf(jwtClaims.getStringClaimValue("userId")));
			user.setRole(jwtClaims.getStringClaimValue("role"));
			
			return user;
		} catch (InvalidJwtException e) {
			// InvalidJwtException will be thrown, if the JWT failed processing or
			// validation in anyway.
			// Hopefully with meaningful explanations(s) about what went wrong.
			logger.logDebug("Invalid JWT! " + e);

			// Programmatic access to (some) specific reasons for JWT invalidity is also
			// possible
			// should you want different error handling behavior for certain conditions.

			// Whether or not the JWT has expired being one common reason for invalidity
			if (e.hasExpired()) {
				logger.logDebug("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
			}

			// Or maybe the audience was invalid
			if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
				logger.logDebug("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
			}
		}
		return null;
	}

	public String generateToken(JwtUser user) throws JoseException {
		// create pauyload
		if (user != null && !StringUtils.isEmpty(user.getUsername())) {
			
			// Create the Claims, which will be the content of the JWT
			JwtClaims claims = new JwtClaims();
			claims.setIssuer(issuer); // who creates the token and signs it
			claims.setAudience(audience); // to whom the token is intended to be sent
			claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
			claims.setGeneratedJwtId(); // a unique identifier for the token
			claims.setIssuedAtToNow(); // when the token was issued/created (now)
			claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
			claims.setSubject(user.getUsername()); // the subject/principal is whom the token is about
			claims.setClaim("userId", user.getId());// additional claims/attributes about the subject can be added
			claims.setClaim("role", user.getRole());

			// A JWT is a JWS and/or a JWE with JSON claims as the payload.
			// In this example it is a JWS so we create a JsonWebSignature object.
			JsonWebSignature jws = new JsonWebSignature();

			// The payload of the JWS is JSON content of the JWT Claims
			jws.setPayload(claims.toJson());

			// The JWT is signed using the private key
			jws.setKey(secretKey.getPrivateKey());

			// Set the Key ID (kid) header because it's just the polite thing to do.
			// We only have one key in this example but a using a Key ID helps
			// facilitate a smooth key rollover process
			jws.setKeyIdHeaderValue(secretKey.getKeyId());

			// Set the signature algorithm on the JWT/JWS that will integrity protect the
			// claims
			jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

			// Sign the JWS and produce the compact serialization or the complete JWT/JWS
			// representation, which is a string consisting of three dot ('.') separated
			// base64url-encoded parts in the form Header.Payload.Signature
			// If you wanted to encrypt it, you can simply set this jwt as the payload
			// of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
			String jwt = jws.getCompactSerialization();

			// Now you can do something with the JWT. Like send it to some other party
			// over the clouds and through the interwebs.
			return jwt;
		}
		else
		{
			
		}
		return null;

	}
	/**
	 * Imposta la chiave 
	 * @param key
	 * @throws JoseException 
	 */
	public void setSecret(String key) throws JoseException {
		secret = key;
		// crea la chiave private
		SecureRandom secureRandom = new SecureRandom(seed);
		// Generate an RSA key pair, which will be used for signing and verification of
		// the JWT, wrapped in a JWK
		secretKey = RsaJwkGenerator.generateJwk(keySize, secret, secureRandom);

		// Give the JWK a Key ID (kid), which is just the polite thing to do
		secretKey.setKeyId(kid);
	}
}
