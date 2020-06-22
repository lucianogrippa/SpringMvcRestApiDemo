package controllers;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;

import dtos.PropertiesListData;
import helpers.AppPropertiesHelper;
import services.UserService;

public class CommonControllerTest {

	@InjectMocks
	private CommonController commonController;
	
	@Mock
	private UserService userService;
	
	@Mock
	private AppPropertiesHelper appPropertiesHelper;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		if (this.mockMvc == null) {
			System.setProperty("jboss.server.home.dir", "./src/test/server");
			System.setProperty("jboss.server.log.dir", "./src/test/server/log");

			// Process mock annotations
			MockitoAnnotations.initMocks(this);

			// Setup Spring test in standalone mode
			this.mockMvc = MockMvcBuilders.standaloneSetup(commonController).build();
		}
	}

	@Test
	public void getDirVar() {
		try {
			this.mockMvc.perform(get("/api/jbossdir")).andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(content().string(Matchers.containsString("jboss.server.home.dir")));
		} catch (Exception e) {
			e.printStackTrace();
			fail("test fail");
		}
	}

	@Test
	public void logfilesList() {
		try {
			this.mockMvc.perform(get("/api/logfilesList")).andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(content().string(Matchers.containsString("data")));
		} catch (Exception e) {
			e.printStackTrace();
			fail("test fail");
		}
	}

	@Test
	public void changeLogLevel() {
		try {
			this.mockMvc.perform(put("/api/logger/change/debug")).andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(content().string(Matchers.containsString("true")));
		} catch (Exception e) {
			e.printStackTrace();
			fail("test fail");
		}
	}

	@Test
	public void createPropertiesFile() {
		try {
			Gson jsonData = new Gson();
			PropertiesListData propertyList = new PropertiesListData();
			propertyList.setFileComment("#create file in test case");
			propertyList.setFileName("test.properties");
			String homeDir = System.getProperty("jboss.server.home.dir") + "/properties";

			propertyList.setPath(homeDir);

			Map<String, String> mapProperty = new HashMap<String, String>();
			mapProperty.put("data", "prova001");
			mapProperty.put("myprop.val", "data-val");
			mapProperty.put("myprop.val2", "data-val-2");

			propertyList.setProperties(mapProperty);
			String contetData = jsonData.toJson(propertyList, PropertiesListData.class);

			this.mockMvc
					.perform(post("/api/properties/create").content(contetData).header(HttpHeaders.CONTENT_TYPE,
							MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(content().string(Matchers.containsString("true")));
		} catch (Exception e) {
			e.printStackTrace();
			fail("test fail");
		}
	}
	
//	@Ignore
	@Test
	public void deletePropertiesFile() {
		try {
			String homeDir = System.getProperty("jboss.server.home.dir") + "/properties/test";
			
			this.mockMvc.perform(delete("/api/properties/delete")
					.param("filename",homeDir)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)).andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(content().string(Matchers.containsString("true")));
		} catch (Exception e) {
			e.printStackTrace();
			fail("test fail");
		}
	}
}
