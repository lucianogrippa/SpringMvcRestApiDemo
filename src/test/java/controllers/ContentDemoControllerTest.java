package controllers;



import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import helpers.AppPropertiesHelper;
import helpers.LogHelper;
import repositories.UserRepository;
import services.UserService;

@RunWith(MockitoJUnitRunner.class)
public class ContentDemoControllerTest {

	@Mock
	private LogHelper logHelper;
	
	@Mock
	private UserRepository userRepository;
	
	@Spy
	private UserService userService;
	
	@Spy
	private AppPropertiesHelper appPropertiesHelper;
	
	@InjectMocks
	private ContentDemoController contentTestController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		if (this.mockMvc == null) {
			System.setProperty("jboss.server.home.dir", "./src/test/server");
			System.setProperty("jboss.server.log.dir", "./src/test/server/log");
			
			// Process mock annotations
			MockitoAnnotations.initMocks(this);

			// Setup Spring test in standalone mode
			this.mockMvc = MockMvcBuilders.standaloneSetup(contentTestController).build();
			Properties properties = new Properties();
			properties.load(new FileInputStream(new File("./docker/wildfly/standalone/configuration/webapps/application.springrestdemo.properties")));
			
			appPropertiesHelper.setAppKey(properties.getProperty("app.key"));
			appPropertiesHelper.setJwtSecret(properties.getProperty("jwt.secret"));
			appPropertiesHelper.setJwtIssuer(properties.getProperty("jwt.issuer"));
			appPropertiesHelper.setJwtKid(properties.getProperty("jwt.kid"));
			appPropertiesHelper.setJwtAudience(properties.getProperty("jwt.auth.audience"));
			appPropertiesHelper.setJwtExpireSeconds(Integer.valueOf(properties.getProperty("jwt.expire.seconds")));
			//appPropertiesHelper.setJwt(properties.getProperty("jwt.expire.past.seconds"));
			appPropertiesHelper.setAppUserId(Long.valueOf(properties.getProperty("app.user.id")));
			
		}
	}
	
	@Test
	public void echo() {
		try {
			this.mockMvc.perform(get("/api/echo/10")).andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(content().string(Matchers.containsString("\"id\":10")));
		} catch (Exception e) {
			e.printStackTrace();
			fail("test fail");
		}
	}
	
	@Test
	@Ignore
	public void getBCryptPassword()
	{
		String password = BCrypt.hashpw("admin.01",BCrypt.gensalt(4));
		System.out.println(password);
		
		assertTrue(password != null && BCrypt.checkpw("admin.01", password));
	}
}