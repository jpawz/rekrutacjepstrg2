package com.example.rekrutacjepstrg2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rekrutacjepstrg2.domain.Train;
import com.example.rekrutacjepstrg2.repository.TrainRepository;

@Service
public class TrainServiceImpl implements TrainService {

	private final TrainRepository trainRepository;

	@Autowired
	public TrainServiceImpl(TrainRepository trainRepository) {
		this.trainRepository = trainRepository;
	}

	/**
	 * Saves {@link com.example.rekrutacjepstrg2.domain.Train Train} into repository.
	 * @param train
	 */
	@Override
	public Train addTrain(Train train) {
		return trainRepository.save(train);
	}
}
