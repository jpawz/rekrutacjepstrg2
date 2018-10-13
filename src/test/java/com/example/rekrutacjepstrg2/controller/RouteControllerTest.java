package com.example.rekrutacjepstrg2.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.rekrutacjepstrg2.domain.Route;
import com.example.rekrutacjepstrg2.repository.TrainRepository;
import com.example.rekrutacjepstrg2.service.RouteServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(RouteController.class)
public class RouteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RouteServiceImpl service;

	@MockBean
	private TrainRepository repository;

	private final String urlTemplate = "/api/shortest_route?start=%s&destination=%s";
	private final String route1Json = "{\"routes\": [[\"Warszawa\", \"Kraków\"]], \"distance\": 1 }";
	private final String route2Json = "{\"routes\": [[\"Poznań\", \"Wrocław\", \"Wadowice\"], [\"Poznań\", \"Łódź\", \"Wadowice\"]], \"distance\": 2}";
	private Route route1;
	private Route route2;
	private Route route3;

	@Before
	public void setup() {
		route1 = new Route();
		route1.addStation("Warszawa");
		route1.addStation("Kraków");
		route2 = new Route();
		route2.addStation("Poznań");
		route2.addStation("Wrocław");
		route2.addStation("Wadowice");
		route3 = new Route();
		route3.addStation("Poznań");
		route3.addStation("Łódź");
		route3.addStation("Wadowice");
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void findShortestRouteShouldReturnProperRoute() throws Exception {
		// given
		String url1 = String.format(urlTemplate, "Warszawa", "Kraków");
		String url2 = String.format(urlTemplate, "Poznań", "Wadowice");
		given(service.findShortestRoute("Warszawa", "Kraków"))
				.willReturn(Arrays.asList(route1));
		given(service.findShortestRoute("Poznań", "Wadowice"))
				.willReturn(Arrays.asList(route2, route3));

		// when
		MockHttpServletResponse response1 = mockMvc.perform(get(url1)).andReturn()
				.getResponse();
		MockHttpServletResponse response2 = mockMvc.perform(get(url2)).andReturn()
				.getResponse();

		// then
		assertThat(response1.getStatus()).isEqualTo(HttpStatus.OK.value());
		JSONAssert.assertEquals(route1Json, response1.getContentAsString(), false);
		assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
		JSONAssert.assertEquals(route2Json, response2.getContentAsString(), false);
	}

	@Test
	public void shouldReturn404WhenNoRoute() throws Exception {
		// given
		String url = String.format(urlTemplate, "Warszawa", "Szczecin");
		given(service.findShortestRoute("Warszawa", "Szczecin"))
				.willReturn(Collections.emptyList());

		// when
		MockHttpServletResponse response = mockMvc.perform(get(url)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
}
