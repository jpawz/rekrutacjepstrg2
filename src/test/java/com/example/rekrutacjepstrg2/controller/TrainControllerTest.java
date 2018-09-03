package com.example.rekrutacjepstrg2.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.rekrutacjepstrg2.domain.Train;
import com.example.rekrutacjepstrg2.service.TrainServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(TrainController.class)
public class TrainControllerTest {

	private static final String start = "Warszawa";
	private static final String destination = "Kraków";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TrainServiceImpl service;

	private Train train;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		train = new Train(start, destination);
	}

	@Test
	public void correctInputShouldReturn201AndTrain() throws Exception {
		// given
		given(service.addTrain(train)).willReturn(train);

		// when
		MockHttpServletResponse response = mockMvc
				.perform(post("/api/trains").contentType(MediaType.APPLICATION_JSON)
						.content("{\"train\": [\"Warszawa\", \"Kraków\"]}"))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).containsSubsequence("start", start,
				"destination", destination);
	}
}
