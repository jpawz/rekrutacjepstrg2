package com.example.rekrutacjepstrg2.controller;

import static io.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrainControllerIntegrationTest {

	@LocalServerPort
	int port;

	@Autowired
	MongoTemplate mongoTemplate;

	@Before
	public void setup() {
		RestAssured.port = port;
		mongoTemplate.getDb().drop();
	}

	@Test
	public void checkResponseForDuplicateValues() throws Exception {
		// @formatter:off
		given().
			accept(ContentType.JSON).contentType(ContentType.JSON)
				.body("{\"train\": [\"Warszawa\", \"Kraków\"]}").
		when()
			.post("/api/trains").
		then().
			statusCode(201);
		
		given().
			accept(ContentType.JSON).contentType(ContentType.JSON)
				.body("{\"train\": [\"Warszawa\", \"Kraków\"]}").
		when()
			.post("/api/trains").
		then().
			statusCode(406);
		// @formatter:on

	}

	@Test
	public void checkResponseForTheSameStartAndDestinatinValues() throws Exception {
		// @formatter:off
		given().
			accept(ContentType.JSON).contentType(ContentType.JSON)
			.body("{\"train\": [\"Warszawa\", \"Warszawa\"]}").
		when()
			.post("/api/trains").
		then().
			statusCode(406);
		// @formatter:on
	}
}
