package com.demo.movies;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class MovieDataLoader {

	private static final Logger logger = LoggerFactory.getLogger(MovieDataLoader.class);

	private final MovieRepository movieRepository;

	public MovieDataLoader(MovieRepository movieRepository) {

		this.movieRepository = movieRepository;
	}

	@PostConstruct
	private void loadData() {

		List<Movie> moviesToSave = Arrays.asList(
			new Movie("Rocky"),
			new Movie("Wall·e"));

		Flux.concat(this.movieRepository.deleteAll(),
			this.movieRepository.saveAll(moviesToSave))
			.subscribe();
	}
}
