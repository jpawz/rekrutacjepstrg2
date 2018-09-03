package com.example.rekrutacjepstrg2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	 * @throws DataIntegrityViolationException when train is not valid (same start and
	 * destination, duplicate entries).
	 */
	@Override
	public Train addTrain(Train train) {
//		if (haveEqualStartAndDestination(train)) {
//			throw new DataIntegrityViolationException("Start and destination are equal");
//		}
//		if (isDuplicate(train)) {
//			throw new DataIntegrityViolationException("Duplicate entries");
//		}
		return trainRepository.save(train);
	}

//	/**
//	 * Checks if {@link com.example.rekrutacjepstrg2.domain.Train Train} is already
//	 * persisted.
//	 * @param train
//	 */
//	private boolean isDuplicate(Train train) {
//		if (trainRepository
//				.findByStartAndDestination(train.getStart(), train.getDestination())
//				.isPresent()) {
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * Checks if {@link com.example.rekrutacjepstrg2.domain.Train Train} have different
//	 * start and destination values.
//	 * @param train
//	 */
//	private boolean haveEqualStartAndDestination(Train train) {
//		if (train.getStart().equals(train.getDestination())) {
//			return true;
//		}
//		return false;
//	}
}
