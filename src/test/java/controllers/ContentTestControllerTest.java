package controllers;



import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ContentTestControllerTest {

	@InjectMocks
	private ContentTestController contentTestController;

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
		}
	}

	@Test
	public void test() {
		try {
			this.mockMvc.perform(get("/api/test/10")).andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(content().string(Matchers.containsString("\"id\":10")));
		} catch (Exception e) {
			e.printStackTrace();
			fail("test fail");
		}
	}
}
