package com.demo.movies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
public class MovieController {

	private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

	private final MovieService movieService;

	public MovieController(MovieService movieService) {

		this.movieService = movieService;
	}

	@GetMapping
	public Flux<Movie> getMovies() {

		return this.movieService.getAllMovies();
	}

	@GetMapping("/{id}")
	public Mono<Movie> getMovie(@PathVariable String id) {

		return this.movieService.getMovieById(id);
	}

	@GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<MovieEvent> getEvents(@PathVariable String id) {

		return this.movieService.getEventsForMovie(id);
	}
}
