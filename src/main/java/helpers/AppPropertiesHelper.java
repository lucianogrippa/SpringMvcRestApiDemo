package helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("appPropertiesHelper")
public class AppPropertiesHelper {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.issuer}")
	String jwtIssuer;

	@Value("${jwt.kid}")
	String jwtKid;

	@Value("${jwt.auth.audience}")
	String jwtAudience;

	@Value("${jwt.expire.seconds}")
	int jwtExpireSeconds;

	/// applications
	@Value("${app.key}")
	String appKey;

	@Value("${app.user.id}")
	long appUserId;
	
	public String getJwtSecret() {
		return jwtSecret;
	}

	public String getJwtIssuer() {
		return jwtIssuer;
	}

	public String getJwtKid() {
		return jwtKid;
	}

	public String getJwtAudience() {
		return jwtAudience;
	}

	public int getJwtExpireSeconds() {
		return jwtExpireSeconds;
	}

	public String getAppKey() {
		return appKey;
	}

	public long getAppUserId() {
		return appUserId;
	}

	public void setJwtSecret(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	public void setJwtIssuer(String jwtIssuer) {
		this.jwtIssuer = jwtIssuer;
	}

	public void setJwtKid(String jwtKid) {
		this.jwtKid = jwtKid;
	}

	public void setJwtAudience(String jwtAudience) {
		this.jwtAudience = jwtAudience;
	}

	public void setJwtExpireSeconds(int jwtExpireSeconds) {
		this.jwtExpireSeconds = jwtExpireSeconds;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setAppUserId(long appUserId) {
		this.appUserId = appUserId;
	}
}
