package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.controller.CustomerRestController_ike;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextHierarchy({
//	@ContextConfiguration(classes = AppConfig.class)
//})

//@WebAppConfiguration
public class ControllerTest {

	MockMvc mockMvc;
	
	@InjectMocks
	CustomerRestController_ike controller;
	
	@Before
	public void setupMockMvc() {
		System.out.println("setupMockMvc()");
//		mockMvc = MockMvcBuilders.standaloneSetup(new CustomerRestController()).build();
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		System.out.println("mockMvc="+mockMvc);
	}
	
	@Test
	public void test01() throws Exception {
		try {
			System.out.println("test01()");
			System.out.println("mockMvc="+mockMvc);
			mockMvc.perform(get("/customerapi/aa@zz.com/find"))
			.andExpect(status().isOk());
		}
		catch (Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
}
