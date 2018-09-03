package com.example.rekrutacjepstrg2.serializer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.rekrutacjepstrg2.domain.Train;

@RunWith(SpringRunner.class)
@JsonTest
public class TrainDeserializerTest {

	@Autowired
	private JacksonTester<Train> json;

	@Test
	public void testTrainDeserialization() throws Exception {
		// given
		String content = "{\"train\": [\"Warszawa\", \"Kraków\"]}";

		// when
		Train train = json.parseObject(content);

		// then
		assertThat(train.getStart()).isEqualTo("Warszawa");
		assertThat(train.getDestination()).isEqualTo("Kraków");
	}

}
