package com.grippaweb.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.grippaweb.ServletApplication;

@TestPropertySource(properties = {"app.profile=testcase"})
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServletApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class HttpApiDefaultErrorPageControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
	this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void A_testHandleError404() throws Exception {

	if (this.mockMvc == null)
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	mockMvc.perform(MockMvcRequestBuilders.get("/echo/-1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
    }

}
