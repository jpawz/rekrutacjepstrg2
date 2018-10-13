package com.example.rekrutacjepstrg2.serializer;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.jackson.JsonComponent;

import com.example.rekrutacjepstrg2.domain.Route;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@JsonComponent
public class RouteListSerializer extends StdSerializer<List<Route>> {

	public RouteListSerializer() {
		this(null);
	}

	protected RouteListSerializer(Class<List<Route>> t) {
		super(t);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(List<Route> values, JsonGenerator gen,
			SerializerProvider provider) throws IOException {
		// @formatter:off
		gen.writeStartObject();
			gen.writeArrayFieldStart("routes");
			for(Route route : values) {
				gen.writeStartArray();
				for(String station : route.getRoute()) {
					gen.writeString(station);
				}
				gen.writeEndArray();
			}
			gen.writeEndArray();
		gen.writeNumberField("distance", values.get(0).getDistance());
		gen.writeEndObject();
		// @formatter:on
	}

}
