package com.demo.movies;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface MovieRepository extends ReactiveMongoRepository<Movie, String> {
}
