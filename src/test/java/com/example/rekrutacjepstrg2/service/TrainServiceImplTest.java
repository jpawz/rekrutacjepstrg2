package com.example.rekrutacjepstrg2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.rekrutacjepstrg2.domain.Train;
import com.example.rekrutacjepstrg2.repository.TrainRepository;

public class TrainServiceImplTest {

	private TrainServiceImpl service;

	@Mock
	private TrainRepository trainRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new TrainServiceImpl(trainRepository);
	}

	@Test
	public void correctInputShouldReturnTrain() {
		// given
		Train train = new Train("Warszawa", "Kraków");

		// when
		when(trainRepository.save(train)).thenReturn(train);
		Train train2 = service.addTrain(train);

		// then
		assertThat(train2).isNotNull();
		assertThat(train.getStart()).isEqualTo(train2.getStart());
		assertThat(train.getDestination()).isEqualTo(train2.getDestination());
	}
}
