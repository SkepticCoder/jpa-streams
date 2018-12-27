package ru.test.jpastreams.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.test.jpastreams.domain.FooBarEntity;

import javax.persistence.QueryHint;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

@Repository
public interface FooBarRepository extends CrudRepository<FooBarEntity, Long> {

    @Query("select t from FooBarEntity t")
    // or use cursor fetch, query hint depends on database)
    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + 1))
    Stream<FooBarEntity> streamAll();


    @Query("select id from FooBarEntity")
    @Transactional(readOnly = true)
    Slice<Long> findAllIds(Pageable page);

    // without count query (Result page always trigger to execute count query)
    // but page make easier to build HAOTES links
    @Transactional(readOnly = true)
    Slice<FooBarEntity> findAll(Pageable page);

    @Query("select t from FooBarEntity t")
    @Transactional(readOnly = true)
    Page<FooBarEntity> findAllWithPage(Pageable page);
}
