package com.example.rekrutacjepstrg2.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rekrutacjepstrg2.domain.Route;
import com.example.rekrutacjepstrg2.domain.Train;
import com.example.rekrutacjepstrg2.repository.TrainRepository;

@Service
public class RouteServiceImpl implements RouteService {

	private TrainRepository repository;

	@Autowired
	public RouteServiceImpl(TrainRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Route> findShortestRoute(final String start, final String destination) {
		List<Route> routes = new ArrayList<>();

		Route directRoute = findDirectRoute(start, destination);
		if (directRoute != null) {
			routes.add(directRoute);
			return routes;
		}

		routes = findShortestUndirectRoutes(start, destination);

		return routes;
	}

	/**
	 * Finds direct route between start and destination.
	 * @param start - start of route
	 * @param destination - destination of route
	 * @return {@link com.example.rekrutacjepstrg2.domain.Route Route} object or null if
	 * direct route didn't exist
	 */
	private Route findDirectRoute(final String start, final String destination) {
		Train train = repository.findByStartAndDestination(start, destination)
				.orElse(null);
		if (train != null)
			return new Route(start, destination);
		else
			return null;
	}

	/**
	 * Finds shortest routes from start to destination.
	 * @param start - start of route
	 * @param destination - destination of route
	 * @return List of {@link com.example.rekrutacjepstrg2.domain.Route Route} or empty
	 * list when no routes were found
	 */
	private List<Route> findShortestUndirectRoutes(final String start,
			final String destination) {

		List<Route> routesFromStartToDestination = new ArrayList<>();
		Set<String> visitedStations = new HashSet<>();
		Queue<List<String>> routes = new LinkedList<>();
		List<String> stations = new ArrayList<>();
		stations.add(start);
		routes.add(stations);

		boolean routeWasFound = false;

		while (!routes.isEmpty()) {
			stations = routes.poll();

			String lastStation = stations.get(stations.size() - 1);

			if (routeWasFound && !lastStation.equals(destination)) {
				continue;
			}

			visitedStations.add(lastStation);

			if (lastStation.equals(destination)) {
				routeWasFound = true;
				routesFromStartToDestination.add(makeRouteFromStations(stations));
				continue;
			}

			List<String> nextUnvisitedStations = repository.findByStart(lastStation)
					.orElse(Collections.emptyList()).stream()
					.map(train -> train.getDestination())
					.filter(station -> !visitedStations.contains(station))
					.collect(Collectors.toList());

			if (nextUnvisitedStations.isEmpty()) {
				continue;
			}

			for (String station : nextUnvisitedStations) {
				List<String> newRoute = new ArrayList<>(stations);
				newRoute.add(station);
				routes.add(newRoute);
			}

		}
		return routesFromStartToDestination;
	}

	/**
	 * Makes {@link com.example.rekrutacjepstrg2.domain.Route Route} object from List of
	 * Strings.
	 * @param stations - stations on the route
	 * @return {@link com.example.rekrutacjepstrg2.domain.Route Route} from stations with
	 * preserving order
	 */
	private Route makeRouteFromStations(final List<String> stations) {
		Route route = new Route();
		for (String station : stations) {
			route.addStation(station);
		}
		return route;
	}
}