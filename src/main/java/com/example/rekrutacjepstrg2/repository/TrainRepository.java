package com.example.rekrutacjepstrg2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.rekrutacjepstrg2.domain.Train;

@Repository
public interface TrainRepository extends MongoRepository<Train, String> {
	Optional<List<Train>> findByStart(String start);

	Optional<List<Train>> findByDestination(String destinatio);

	Optional<Train> findByStartAndDestination(String start, String destination);
}
