# spring-reactive-demo

## Introducción

Demos de Spring 5 Reactive.

## ¿Qué trae Spring 5?

https://github.com/spring-projects/spring-framework/wiki/What%27s-New-in-the-Spring-Framework#whats-new-in-spring-framework-5x

Importante:

> Entire framework codebase based on Java 8 source code level now.

> New spring-webflux module, an alternative to spring-webmvc built on a reactive foundation -- fully asynchronous and non-blocking, intended for use in an event-loop execution model vs traditional large thread pool with thread-per-request execution model.

> @Controller style, annotation-based, programming model, similar to Spring MVC, but supported in WebFlux, running on a reactive stack, e.g. capable of supporting reactive types as controller method arguments, never blocking on I/O, respecting backpressure all the way to the HTTP socket, and running on extra, non-Servlet containers such as Netty and Undertow.

> New functional web framework ("WebFlux.fn") as an alternative to the @Controller, annotation-based, programming model -- minimal and transparent with an endpoint routing API, running on the same reactive stack and WebFlux infrastructure.

> New WebClient with a functional and reactive API for HTTP calls, comparable to the RestTemplate but through a fluent API and also excelling in non-blocking and streaming scenarios based on WebFlux infrastructure; in 5.0 the AsyncRestTemplate is deprecated in favor of the WebClient.

## Programación Reactiva

https://www.reactivemanifesto.org/

### ¿Por qué usar programación reactiva?

La programación asíncrona, con las herramientas tradicionales se puede complicar (Callbacks, Futures)
Además, no hay una forma de procesar de forma asíncrona un flujo de datos.
Como mucho podemos tener un Future<List<?>> pero no devuelve el resultado hasta que no tiene la lista completa

Con la programación reactiva podemos trabajar de forma asíncrona con un flujo de datos, que podría ser infinito, sin esperar a tener todo el flujo.

### Reactive Extensions

Todo empezó con Microsoft creando Reactive Extensions (Rx) para .NET (https://github.com/Reactive-Extensions/Rx.NET)
Después se crearon versiones para otros lenguajes, por ejemplo:
- RxJS https://github.com/reactivex/rxjs
- RxJava https://github.com/ReactiveX/RxJava
- RxScala https://github.com/ReactiveX/RxScala

### Reactive Streams

http://www.reactive-streams.org/

Se trata de una iniciativa que surgió para estandarizar la programación reactiva.
Al poco tiempo la mayoría de implementaciones ya se basan en este estandar:
- RxJava
- Akka Streams
- Project Reactor
- Vert.x

Java 9 lo incluye en *java.util.concurrent.Flow* http://download.java.net/java/jdk9/docs/api/java/util/concurrent/Flow.html

3 Interfaces importantes:
- Publisher: proveedor de elementos secuenciales (puede ser un numero infinito de elementos). Estos elementos se publican de acuerdo a la demanda del Subscriber.
http://www.reactive-streams.org/reactive-streams-1.0.0-javadoc/org/reactivestreams/Publisher.html

- Subscriber: consumidor de los elementos del Publisher.
http://www.reactive-streams.org/reactive-streams-1.0.0-javadoc/org/reactivestreams/Subscriber.html

- Subscription: representa la relación entre Publisher y Subscriber.
http://www.reactive-streams.org/reactive-streams-1.0.0-javadoc/org/reactivestreams/Subscription.html

```java
public static interface Publisher<T> {
	public void subscribe(Subscriber<? super T> subscriber);
}

public static interface Subscriber<T> {
	public void onSubscribe(Subscription subscription);
	public void onNext(T item);
	public void onError(Throwable throwable);
	public void onComplete();
}

public static interface Subscription {
	public void request(long n);
	public void cancel();
}
``` 
### Project Reactor

Web oficial: https://projectreactor.io/

Implementación de *Reactive Streams* de Spring. Va por la versión 3; la versión 1 es del 2013.

En Reactor hay dos implementaciones de *Publishers* que nos facilitan la vida:
- Mono: Publisher de 0-1 Items
- Flux: Publisher de 0-n Items

La documentación de Project Reactor es muy completa:
https://projectreactor.io/docs/core/release/reference/

### Spring Data Reactive

*Spring Data Kay M1* es la primera release que incluye soporte para acceso a datos de forma reactiva.
De momento solo da soporte a MongoDB, Apache Cassandra y Redis ya que estos disponen de drivers reactivos.

#### MongoDB

Drive Reactive: https://mongodb.github.io/mongo-java-driver-reactivestreams/ 
Ejemplos Spring Data: https://github.com/spring-projects/spring-data-examples/tree/master/mongodb/reactive

Utiliza la interfaz *ReactiveMongoRepository* https://github.com/spring-projects/spring-data-mongodb/blob/master/spring-data-mongodb/src/main/java/org/springframework/data/mongodb/repository/ReactiveMongoRepository.java

#### Cassandra 

Ejemplos Spring Data: https://github.com/spring-projects/spring-data-examples/tree/master/cassandra/reactive

#### Redis
 
Ejemplos Spring Data: https://github.com/spring-projects/spring-data-examples/tree/master/redis/reactive

#### JPA...

Oracle está trabajando en un driver no bloqueante para acceder a bases de datos relacionales: https://t.co/qetXWqaJF9
Hasta que no esté listo, no habrá una versión de Spring Data JPA totalmente reactiva

### WebClient reactive

*spring-webflux* incluye un cliente HTTP no bloqueante: *WebClient*
WebClient acepta un Publisher como input y devuelve un Mono o Flux.
 
Documentación: https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-client	