package com.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Iterator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxDemo {

	private static final Logger logger = LoggerFactory.getLogger(FluxDemo.class);

	// Más ejemplos en https://www.infoq.com/articles/reactor-by-example

	@Test
	public void emptyFluxCreation() {

		Flux<String> emptyFlux = Flux.empty();

		StepVerifier.create(emptyFlux)
			.verifyComplete();
	}

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
			.expectNext(0, 1, 2, 3, 4)
			.expectComplete()
			.verify();
	}

	@Test
	public void fluxMap() {

		Flux<Integer> flux = Flux.just("1", "2")
			.map(Integer::valueOf);

		StepVerifier.create(flux)
			.expectNext(1, 2)
			.expectComplete()
			.verify();
	}

	@Test
	public void fluxFilter() {

		Flux<Integer> flux = Flux.range(0,5)
			.filter(integer -> integer % 2 == 0);

		StepVerifier.create(flux)
			.expectNext(0, 2, 4)
			.expectComplete()
			.verify();
	}

	@Test
	public void fluxToIterable() {

		Iterator<String> namesIterator = Flux.just("foo", "bar")
			.toIterable()
			.iterator();

		assertEquals("foo", namesIterator.next());
		assertEquals("bar", namesIterator.next());
		assertFalse(namesIterator.hasNext());
	}

	@Test
	public void fluxConcat() {

		Flux<String> flux = Flux.just("A", "B")
			.concatWith(Flux.just("C", "D"));

		StepVerifier.create(flux)
			.expectNext("A", "B", "C", "D")
			.expectComplete()
			.verify();
	}

	@Test
	public void fluxZip() {

		Flux<String> names = Flux.just("John", "Laura");
		Flux<String> surnames = Flux.just("Spencer", "Manning");

		Flux<String> fullNames = Flux.zip(names, surnames)
			.map(tuple -> tuple.getT1() + ' ' + tuple.getT2());

		StepVerifier.create(fullNames)
			.expectNext("John Spencer", "Laura Manning")
			.expectComplete()
			.verify();
	}

	@Test
	public void fluxZipWith() {

		Flux<String> names = Flux.just("John", "Laura");
		Flux<String> surnames = Flux.just("Spencer", "Manning");

		Flux<String> fullNames = names.zipWith(surnames, (name, surname) -> name + ' ' + surname);

		StepVerifier.create(fullNames)
			.expectNext("John Spencer", "Laura Manning")
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
