package com.example.rekrutacjepstrg2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.rekrutacjepstrg2.cache.RouteCache;
import com.example.rekrutacjepstrg2.domain.Route;
import com.example.rekrutacjepstrg2.domain.Train;
import com.example.rekrutacjepstrg2.repository.TrainRepository;

public class RouteServiceImplTest {

	private RouteService routeService;

	private String warszawa = "Warszawa";
	private String gdansk = "Gdańsk";
	private String bydgoszcz = "Bydgoszcz";
	private String olsztyn = "Olsztyn";
	private String lodz = "Łódź";
	private String poznan = "Poznań";
	private String warka = "Warka";
	private String lezajsk = "Leżajsk";
	private String tychy = "Tychy";
	private String zywiec = "Żywiec";

	// direct train
	private Train warszawaGdansTrain = new Train(warszawa, gdansk);
	private Route warszawaGdansRoute = new Route(warszawa, gdansk);

	// route 1, three stations
	private Train warszawaBydgoszczTrain = new Train(warszawa, bydgoszcz);
	private Train bydgoszczGdansTrain = new Train(bydgoszcz, gdansk);
	private Route warszawaBydgoszczGdansRoute = new Route(warszawa, bydgoszcz, gdansk);

	// route 2, three station
	private Train warszawaOlsztynTrain = new Train(warszawa, olsztyn);
	private Train olsztynGdansTrain = new Train(olsztyn, gdansk);
	private Route warszawaOlsztynGdansRoute = new Route(warszawa, olsztyn, gdansk);

	// route 3, four stations
	private Train warszawaLodzTrain = new Train(warszawa, lodz);
	private Train lodzPoznanTrain = new Train(lodz, poznan);
	private Train poznanGdansTrain = new Train(poznan, gdansk);

	// wrong direction
	private Train warszawaWarkaTrain = new Train(warszawa, warka);
	private Train warkaLezajskTrain = new Train(warka, lezajsk);

	// wrong direction
	private Train warszawaTychTrain = new Train(warszawa, tychy);
	private Train tychyZywiecTrain = new Train(tychy, zywiec);

	// loop
	private Train warkaWarszawaTrain = new Train(warka, warszawa);

	@Mock
	private TrainRepository repository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		routeService = new RouteServiceImpl(repository);
		RouteCache.INSTANCE.clearCache();
	}

	@Test
	public void shouldReturnDirectRouteAndStopSearching() {
		// given
		given(repository.findByStartAndDestination(warszawa, gdansk))
				.willReturn(Optional.of(warszawaGdansTrain));

		// when
		List<Route> routes = routeService.findShortestRoute(warszawa, gdansk);

		// then
		verify(repository, only()).findByStartAndDestination(warszawa, gdansk);
		verify(repository, never()).findByStart(any());
		assertThat(routes).containsExactly(warszawaGdansRoute);
	}

	@Test
	public void shouldReturnTwoRoutesAndStopSearching() {
		// given
		given(repository.findByStart(warszawa))
				.willReturn(Optional.of(Arrays.asList(warszawaBydgoszczTrain, warszawaOlsztynTrain,
						warszawaLodzTrain, warszawaWarkaTrain, warszawaTychTrain)));
		given(repository.findByStart(bydgoszcz))
				.willReturn(Optional.ofNullable(Arrays.asList(bydgoszczGdansTrain)));
		given(repository.findByStart(olsztyn))
				.willReturn(Optional.ofNullable(Arrays.asList(olsztynGdansTrain)));
		given(repository.findByStart(lodz))
				.willReturn(Optional.ofNullable(Arrays.asList(lodzPoznanTrain)));
		given(repository.findByStart(warka)).willReturn(
				Optional.ofNullable(Arrays.asList(warkaLezajskTrain, warkaWarszawaTrain)));
		given(repository.findByStart(tychy))
				.willReturn(Optional.ofNullable(Arrays.asList(tychyZywiecTrain)));
		given(repository.findByStart(poznan))
				.willReturn(Optional.ofNullable(Arrays.asList(poznanGdansTrain)));

		// when
		List<Route> routes = routeService.findShortestRoute(warszawa, gdansk);

		// then
		verify(repository, never()).findByStart(poznan); // longer route
		assertThat(routes).containsExactlyInAnyOrder(warszawaBydgoszczGdansRoute,
				warszawaOlsztynGdansRoute);
	}

	@Test
	public void shouldReturnEmptyRoute() {
		// given
		given(repository.findByStart(warszawa))
				.willReturn(Optional.of(Arrays.asList(warszawaBydgoszczTrain)));

		// when
		List<Route> routes = routeService.findShortestRoute(warszawa, "Moskwa");

		// then
		assertThat(routes).isEmpty();
	}

	@Test
	public void shouldReturnCachedRoutes() {
		// given
		RouteCache.INSTANCE.addRoutes(warszawa, gdansk, Arrays.asList(warszawaGdansRoute));

		// when
		List<Route> routes = routeService.findShortestRoute(warszawa, gdansk);

		// then
		verify(repository, never()).findByStartAndDestination(any(), any());
		assertThat(routes).contains(warszawaGdansRoute);
	}
}
