package ru.test.jpastreams;


import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.test.jpastreams.domain.FooBarEntity;
import ru.test.jpastreams.repository.FooBarRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ExtendWith(RandomBeansExtension.class)
public class JpaStreamsApplicationTests {

    private static final int COUNT = 17;

    @Autowired
    private FooBarRepository fooBarRepository;

    @Random(size = COUNT, type = FooBarEntity.class)
    private List<FooBarEntity> entities;

    @BeforeEach
    @Transactional
    public void setUp() throws Exception {
        fooBarRepository.deleteAll();
        fooBarRepository.saveAll(entities);
    }

    @Test
    @Transactional
    public void testStream() {
        assertEquals(COUNT, fooBarRepository.streamAll().count());
    }
}
