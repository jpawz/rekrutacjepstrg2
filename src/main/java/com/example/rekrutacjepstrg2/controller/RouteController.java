package com.example.rekrutacjepstrg2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rekrutacjepstrg2.domain.Route;
import com.example.rekrutacjepstrg2.service.RouteService;

@RestController
public class RouteController {

	@Autowired
	private RouteService service;

	@GetMapping("/api/shortest_route")
	public ResponseEntity<List<Route>> findShortestRoute(@RequestParam String start,
			@RequestParam String destination) {
		List<Route> routes = service.findShortestRoute(start, destination);
		if (!routes.isEmpty()) {
			return ResponseEntity.ok().body(routes);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
}
