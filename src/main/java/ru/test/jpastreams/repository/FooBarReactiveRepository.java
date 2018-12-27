package ru.test.jpastreams.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.test.jpastreams.domain.FooBarEntity;

//@Repository only for Cassandra, Mongo, Redis
public interface FooBarReactiveRepository /*extends ReactiveCrudRepository<FooBarEntity, Long>*/ {

}
