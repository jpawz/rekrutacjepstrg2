package com.example.rekrutacjepstrg2.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Train entity.
 */
@Data
@Document
@RequiredArgsConstructor
public class Train {

	@Id
	private String id;

	private final String start;
	private final String destination;

	protected Train() {
		this.start = null;
		this.destination = null;
	}
}
