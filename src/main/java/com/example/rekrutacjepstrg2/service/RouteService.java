package com.example.rekrutacjepstrg2.service;

import java.util.List;

import com.example.rekrutacjepstrg2.domain.Route;

public interface RouteService {

	/**
	 * Finds shortest {@link com.example.rekrutacjepstrg2.domain.Route Routes} (routes with
	 * the least number of stations) from start to destination.
	 * @param start
	 * @param destination
	 * @return List of {@link com.example.rekrutacjepstrg2.domain.Route Route} or empty
	 * List when no routes where found
	 */
	List<Route> findShortestRoute(String start, String destination);

}
