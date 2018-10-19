package com.example.rekrutacjepstrg2.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.rekrutacjepstrg2.domain.Route;

/**
 * Provides cache for list of {@link com.example.rekrutacjepstrg2.domain.Route Route}.
 */
public enum RouteCache {

	INSTANCE;

	private final Map<String, List<Route>> cache = new ConcurrentHashMap<>();

	public synchronized void addRoutes(String start, String destination, List<Route> routes) {
		cache.put(start + destination, routes);
	}

	public List<Route> getRoutes(String start, String destination) {
		return new ArrayList<>(cache.get(start + destination));
	}

	public boolean hasCachedRoutes(String start, String destination) {
		return cache.containsKey(start + destination);
	}

	public void clearCache() {
		cache.clear();
	}
}