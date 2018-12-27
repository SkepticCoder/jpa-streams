package ru.test.jpastreams.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.test.jpastreams.domain.FooBarEntity;
import ru.test.jpastreams.repository.FooBarRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Service
public class FooBarService {

    private FooBarRepository repository;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public FooBarService(FooBarRepository repository, PlatformTransactionManager transactionManager) {
        this.repository = repository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public Page<FooBarEntity> getFooBars(Pageable pageable) {
        pageable = getNonNullPage(pageable);
        return repository.findAllWithPage(pageable);
    }

    public List<FooBarEntity> getFooBarsList(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public Optional<FooBarEntity> getById(Long id) {
        return repository.findById(id);
    }

    private static Pageable getNonNullPage(Pageable pageable) {
        return pageable == null ? Pageable.unpaged() : pageable;
    }

    public Stream<FooBarEntity> streamAll() {
        return repository.streamAll();
    }

    public void stream(Consumer<Stream<FooBarEntity>> consumer) {
        transactionTemplate.execute(t -> {
            try (Stream<FooBarEntity> stream = repository.streamAll()) {
                consumer.accept(stream);
            }
            return null;
        });
    }
}
