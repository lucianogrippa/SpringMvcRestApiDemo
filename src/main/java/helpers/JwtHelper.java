package helpers;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.annotation.Resource;

import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import dtos.JwtUser;
import exceptions.ApiExpairedTokenException;
import exceptions.ApiForbiddenHandlerException;

/**
 * Helper da utilizzare per la generazione del token e per il parser
 * 
 * @author luciano
 */
@Service(value = "jwtHelper")
@Scope(value = "prototype")
public class JwtHelper {

	@Resource
	private Environment env;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.issuer}")
	private String issuer;

	@Value("${jwt.auth.audience}")
	private String audience;

	@Value("${jwt.kid}")
	private String kid;
	
	@Value("${jwt.expire.seconds}")
	int jwtExpireSeconds;
	
	@Value("${jwt.expire.past.seconds}")
	int jwtExpirePastSeconds;

	@Autowired
	LogHelper logger;

	private Key secretKey;

	public JwtUser parseToken(String token) throws MalformedClaimException, JoseException, UnsupportedEncodingException, ApiForbiddenHandlerException, ApiExpairedTokenException {
		JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime().setAllowedClockSkewInSeconds(30)
				.setRequireSubject().setExpectedIssuer(issuer).setExpectedAudience(audience)
				.setVerificationKey(getSecretKey())
				.setJwsAlgorithmConstraints(ConstraintType.WHITELIST, AlgorithmIdentifiers.HMAC_SHA256)
				.build();

		try {
			JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
			logger.logDebug("JWT validation succeeded! " + jwtClaims);

			JwtUser user = new JwtUser();
			user.setUsername(jwtClaims.getSubject());
			user.setFirstName(jwtClaims.getClaimValueAsString("firstname"));
			user.setLastName(jwtClaims.getClaimValueAsString("lastname"));
			user.setEmail(jwtClaims.getStringClaimValue("email"));
			user.setId(Long.valueOf(jwtClaims.getClaimValueAsString("userId")));
			user.setRole(new String[] { jwtClaims.getClaimValueAsString("role")});
			user.setJwt(token);
			
			return user;
		} catch (InvalidJwtException e) {
			logger.logDebug("Invalid JWT! " + e);

			if (e.hasExpired()) {
				logger.logDebug("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
				throw new ApiExpairedTokenException("Token is expired "+ e.getJwtContext().getJwtClaims().getExpirationTime(), e ,e.getJwtContext().getJwtClaims().getExpirationTime().getValueInMillis());
			}

			if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
				logger.logDebug("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
				throw new ApiForbiddenHandlerException("Token audience is invalid "+ e.getJwtContext().getJwtClaims().getAudience(), e);
			}
		}
		return null;
	}

	public String generateToken(JwtUser user) throws JoseException, UnsupportedEncodingException {
		// create pauyload
		if (user != null && !StringUtils.isEmpty(user.getUsername())) {
			int expireTimeInMinutes = jwtExpireSeconds/60;
			int expireTimePasteInMinutes = jwtExpirePastSeconds/60;
			
			JwtClaims claims = new JwtClaims();
			claims.setIssuer(issuer);
			claims.setAudience(audience);
			claims.setExpirationTimeMinutesInTheFuture(expireTimeInMinutes);
			claims.setGeneratedJwtId();
			claims.setIssuedAtToNow();
			claims.setNotBeforeMinutesInThePast(expireTimePasteInMinutes);
			claims.setSubject(user.getUsername());
			claims.setClaim("userId", user.getId());
			claims.setClaim("role", user.getRole());
			claims.setClaim("firstname", user.getFirstName());
			claims.setClaim("lastname", user.getLastName());
			claims.setClaim("email", user.getEmail());

			JsonWebSignature jws = new JsonWebSignature();

			jws.setPayload(claims.toJson());
			
			Key key = getSecretKey();
			String keyVal = key.getEncoded().toString();
			
			logger.logDebug("key generated public: "+keyVal);
			
			jws.setKey(key);
			jws.setKeyIdHeaderValue(kid);
			jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

			String jwt = jws.getCompactSerialization();
			return jwt;
		}

		return null;

	}

	/**
	 * Imposta la chiave
	 * 
	 * @param key
	 * @return
	 * @throws JoseException
	 * @throws UnsupportedEncodingException 
	 */
	public Key getSecretKey() throws UnsupportedEncodingException {
		// crea la chiave private
		if (secretKey == null) {
			secretKey =  new HmacKey(secret.getBytes("UTF-8"));
			return secretKey;
		}
		
		return secretKey;
	}
}
