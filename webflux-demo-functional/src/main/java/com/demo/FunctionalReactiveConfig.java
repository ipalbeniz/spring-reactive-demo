package com.demo;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.demo.movies.Movie;
import com.demo.movies.MovieEvent;
import com.demo.movies.MovieService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FunctionalReactiveConfig {

	@Bean
	RouterFunction routerFunction(MovieService movieService) {

		return route(
			GET("/movies"), request -> ServerResponse
				.ok()
				.body(movieService.getAllMovies(), Movie.class))
			.andRoute(
				GET("/movies/{id}"), request -> ServerResponse
					.ok()
					.body(movieService.getMovieById(request.pathVariable("id")), Movie.class))
			.andRoute(
				GET("/movies/{id}/events"), request -> ServerResponse
					.ok()
					.contentType(MediaType.TEXT_EVENT_STREAM)
					.body(movieService.getEventsForMovie(request.pathVariable("id")), MovieEvent.class));
	}
}