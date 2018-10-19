package com.example.rekrutacjepstrg2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.rekrutacjepstrg2.domain.Train;

@Repository
public interface TrainRepository extends MongoRepository<Train, String> {

	@Cacheable
	Optional<List<Train>> findByStart(String start);

	@Cacheable
	Optional<List<Train>> findByDestination(String destinatio);

	@Cacheable
	Optional<Train> findByStartAndDestination(String start, String destination);
}
