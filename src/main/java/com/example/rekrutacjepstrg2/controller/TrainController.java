package com.example.rekrutacjepstrg2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.rekrutacjepstrg2.domain.Train;
import com.example.rekrutacjepstrg2.service.TrainService;

@RestController
public class TrainController {

	private final TrainService trainService;

	@Autowired
	public TrainController(final TrainService trainService) {
		this.trainService = trainService;
	}

	@PostMapping("/api/trains")
	@ResponseStatus(HttpStatus.CREATED)
	public Train addTrain(@RequestBody Train train) {
		return trainService.addTrain(train);
	}
}
