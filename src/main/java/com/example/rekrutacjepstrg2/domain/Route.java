package com.example.rekrutacjepstrg2.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Route {

	private final Set<String> route = new LinkedHashSet<>();

	/**
	 * Constructs route with given stations. Stations order is preserved.
	 * @param stations - station names.
	 */
	public Route(String... stations) {
		for (String station : stations) {
			route.add(station);
		}
	}

	/**
	 * Default constructor. In order to make {@link Route} object call
	 * {@link #addStation(String)} method.
	 */
	public Route() {

	}

	/**
	 * Insert station at the end of a route.
	 * @param station - name of station.
	 */
	public void addStation(String station) {
		route.add(station);
	}

	/**
	 * Obtain number of routes (number of stations - 1).
	 * @return number of routes.
	 */
	public int getDistance() {
		return route.size() - 1;
	}

}
