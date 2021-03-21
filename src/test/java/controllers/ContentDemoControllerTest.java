package controllers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.TimeZone;

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
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

import entities.Roles;
import entities.User;
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
			properties.load(new FileInputStream(new File(
					"./docker/wildfly/standalone/configuration/webapps/application.springrestdemo.properties")));

			appPropertiesHelper.setAppKey(properties.getProperty("app.key"));
			appPropertiesHelper.setJwtSecret(properties.getProperty("jwt.secret"));
			appPropertiesHelper.setJwtIssuer(properties.getProperty("jwt.issuer"));
			appPropertiesHelper.setJwtKid(properties.getProperty("jwt.kid"));
			appPropertiesHelper.setJwtAudience(properties.getProperty("jwt.auth.audience"));
			appPropertiesHelper.setJwtExpireSeconds(Integer.valueOf(properties.getProperty("jwt.expire.seconds")));
			// appPropertiesHelper.setJwt(properties.getProperty("jwt.expire.past.seconds"));
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
	public void getBCryptPassword() {
		String password = BCrypt.hashpw("admin.01", BCrypt.gensalt(4));
		System.out.println(password);

		assertTrue(password != null && BCrypt.checkpw("admin.01", password));
	}


	@Test
	public void printJsonUser() {
		Gson json = new Gson();
		User user = new User();
		user.setUserId(-1);
		user.setActive(true);
		user.setCreationtimestamp(Calendar.getInstance().getTime());
		user.setEmail("mymail@mymail.com");
		user.setFirstname("Giacomo");
		user.setLastname("Pierri");
		user.setLastaccess(null);
		user.setLastupdate(Calendar.getInstance().getTime());
		user.setSecret("343434343434343434343");
		user.setUsername("ciclope");
		Roles role = new Roles();
		role.setId(-1);
		role.setCode("ROLE_TEST");
		role.setName("Tester");
		LinkedHashSet<Roles> roles = new LinkedHashSet<Roles>();
		roles.add(role);
		user.setRoles(roles);
		String jUser = json.toJson(user, User.class);
		System.out.println(jUser);

		assertTrue(!StringUtils.isEmpty(jUser) && jUser.contains("-1"));
	}
	
	@Test
	public void testDate() {
		// test utc date
		String utcCurrentTime ="";
		try {
			utcCurrentTime = getCurrentUtcTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(utcCurrentTime);
		assertTrue(utcCurrentTime != null && utcCurrentTime.endsWith("Z"));
	}
	
	private  String getCurrentUtcTime() throws ParseException {
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	    return simpleDateFormat.format(new Date());
	}
	
}