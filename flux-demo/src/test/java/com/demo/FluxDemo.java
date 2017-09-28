package com.demo;

import java.util.Arrays;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxDemo {

	private static final Logger logger = LoggerFactory.getLogger(FluxDemo.class);

	// Más ejemplos en https://www.infoq.com/articles/reactor-by-example

	@Test
	public void simpleFluxCreation() {

		Flux<String> flux = Flux.just("foo", "bar");

		StepVerifier.create(flux)
			.expectNext("foo")
			.expectNext("bar")
			.expectComplete()
			.verify();
	}

	@Test
	public void fluxCreationFromIterable() {

		Flux<String> flux = Flux.fromIterable(Arrays.asList("foo", "bar"));

		StepVerifier.create(flux)
			.expectNext("foo")
			.expectNext("bar")
			.expectComplete()
			.verify();
	}

	@Test
	public void fluxCreationRange() {

		Flux<Integer> flux = Flux.range(0,5);

		StepVerifier.create(flux)
			.expectNext(0)
			.expectNext(1)
			.expectNext(2)
			.expectNext(3)
			.expectNext(4)
			.expectComplete()
			.verify();
	}

	@Test
	public void fluxMap() {

		Flux<Integer> flux = Flux.just("1", "2")
			.map(Integer::valueOf);

		StepVerifier.create(flux)
			.expectNext(1)
			.expectNext(2)
			.expectComplete()
			.verify();
	}

	@Test
	public void fluxSubscribe() {

		Flux.just("foo", "bar")
			.subscribe();
	}

	@Test
	public void fluxSubscribeWithConsumer() {

		Flux.just("foo", "bar")
			.subscribe(string -> logger.info("Element: " + string));
	}

	@Test
	public void fluxDoOnNext() {

		Flux.just("foo", "bar")
			.doOnNext(string -> logger.info("Element: " + string))
			.subscribe();
	}

	@Test
	public void fluxDoOnError() {

		Flux.just("foo", "bar")
			.doOnNext(string -> {
				if (string.equals("bar")) {
					throw new RuntimeException();
				}
				logger.info("Element: " + string);
			})
			.doOnError(throwable -> logger.error("Error: " + throwable))
			.subscribe();
	}

	@Test
	public void fluxLog() {

		Flux.just("foo", "bar")
			.log()
			.subscribe();
	}
}
