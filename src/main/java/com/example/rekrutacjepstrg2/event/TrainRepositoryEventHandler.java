package com.example.rekrutacjepstrg2.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.example.rekrutacjepstrg2.cache.RouteCache;
import com.example.rekrutacjepstrg2.domain.Train;
import com.example.rekrutacjepstrg2.repository.TrainRepository;

@Component
public class TrainRepositoryEventHandler extends AbstractMongoEventListener<Train> {

	@Autowired
	private TrainRepository repository;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Train> event) {
		Train train = event.getSource();

		if (haveTheSameStartAndDestination(train)) {
			throw new DataIntegrityViolationException("Start and destination can't be the same");
		}

		if (trainExistsInRepository(train)) {
			throw new DataIntegrityViolationException("Dulicate trains are not allowed");
		}
	}

	@Override
	public void onAfterSave(AfterSaveEvent<Train> event) {
		RouteCache.INSTANCE.clearCache();
	}

	private boolean haveTheSameStartAndDestination(Train train) {
		return train.getStart().equals(train.getDestination());
	}

	private boolean trainExistsInRepository(Train train) {
		return repository.findByStartAndDestination(train.getStart(), train.getDestination())
				.isPresent();
	}
}