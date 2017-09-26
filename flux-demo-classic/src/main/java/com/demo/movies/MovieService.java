package com.demo.movies;

import java.time.Duration;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieService {

	private final MovieRepository movieRepository;

	private static final Logger logger = LoggerFactory.getLogger(MovieRepository.class);

	public MovieService(MovieRepository movieRepository) {

		this.movieRepository = movieRepository;
	}

	public Flux<Movie> getAllMovies() {

		return this.movieRepository.findAll();
	}

	public Mono<Movie> getMovieById(String id) {

		return this.movieRepository.findById(id);
	}

	public Flux<MovieEvent> getEventsForMovie(String id) {

		return Flux.interval(Duration.ofSeconds(1))
			.map(index -> new MovieEvent(id, LocalDateTime.now()));
	}
}
