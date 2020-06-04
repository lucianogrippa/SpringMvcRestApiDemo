package helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("appPropertiesHelper")
public class AppPropertiesHelper {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.keysize}")
	int jwtKeySize;

	@Value("${jwt.seed}")
	String jwtSeed;

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

	@Value("${app.user.firstname}")
	String appUserFirstname;

	@Value("${app.user.lastname}")
	String appUserLastName;

	@Value("${app.user.username}")
	String appUsername;

	@Value("${app.user.pwd}")
	String appUserPwd;

	@Value("${app.user.pwd}")
	String appUserEmail;

	@Value("${app.user.role}")
	String appUserRole;

	public String getJwtSecret() {
		return jwtSecret;
	}

	public int getJwtKeySize() {
		return jwtKeySize;
	}

	public String getJwtSeed() {
		return jwtSeed;
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

	public String getAppUserFirstname() {
		return appUserFirstname;
	}

	public String getAppUserLastName() {
		return appUserLastName;
	}

	public String getAppUsername() {
		return appUsername;
	}

	public String getAppUserPwd() {
		return appUserPwd;
	}

	public String getAppUserEmail() {
		return appUserEmail;
	}

	public String getAppUserRole() {
		return appUserRole;
	}

	public void setJwtSecret(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	public void setJwtKeySize(int jwtKeySize) {
		this.jwtKeySize = jwtKeySize;
	}

	public void setJwtSeed(String jwtSeed) {
		this.jwtSeed = jwtSeed;
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

	public void setAppUserFirstname(String appUserFirstname) {
		this.appUserFirstname = appUserFirstname;
	}

	public void setAppUserLastName(String appUserLastName) {
		this.appUserLastName = appUserLastName;
	}

	public void setAppUsername(String appUsername) {
		this.appUsername = appUsername;
	}

	public void setAppUserPwd(String appUserPwd) {
		this.appUserPwd = appUserPwd;
	}

	public void setAppUserEmail(String appUserEmail) {
		this.appUserEmail = appUserEmail;
	}

	public void setAppUserRole(String appUserRole) {
		this.appUserRole = appUserRole;
	}
	
}
