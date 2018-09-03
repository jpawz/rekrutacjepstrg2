package com.example.rekrutacjepstrg2.serializer;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.example.rekrutacjepstrg2.domain.Train;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Deserializer from JSON format: <pre>  {
 *   "train": ["start", "destination"]
 *  } </pre> into {@link com.example.rekrutacjepstrg2.domain.Train Train} object.
 */
@JsonComponent
public class TrainDeserializer extends StdDeserializer<Train> {

	private static final long serialVersionUID = 1L;

	public TrainDeserializer() {
		this(null);
	}

	public TrainDeserializer(Class<Train> c) {
		super(c);
	}

	@Override
	public Train deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		String start = node.get("train").get(0).textValue();
		String destination = node.get("train").get(1).asText();
		return new Train(start, destination);
	}

}
