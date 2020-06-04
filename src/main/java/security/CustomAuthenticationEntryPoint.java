package security;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import helpers.LogHelper;

public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	public static final String REALM_NAME = "memorynotfound.com";
	LogHelper logger = LogHelper.getLogger();
	
    @Override
    public void afterPropertiesSet() {
        setRealmName(REALM_NAME);
        try {
			super.afterPropertiesSet();
		} catch (Exception e) {
			logger.logException(e);
		}
    }
}
