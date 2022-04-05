package com.epam.brest.dao.jpa.repository;

import com.epam.brest.dao.jpa.entity.BandEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@EnableAutoConfiguration
@EntityScan("com.epam.brest.*")
@ContextConfiguration(classes=BandEntity.class)
@ActiveProfiles("test")
@Slf4j
class BandRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BandRepository repository;

    @Test
    void injectedComponentsAreNotNull(){
        log.debug("injectedComponentsAreNotNull()");
        assertThat(entityManager).isNotNull();
        assertThat(repository).isNotNull();
    }

    @Test
    public void testFindAll() throws Exception {
        log.debug("testFindAll()");
        BandEntity band1 = BandEntity.builder()
                .bandName("test band1")
                .build();
        entityManager.persist(band1);
        BandEntity band2 = BandEntity.builder()
                .bandName("test band2")
                .build();
        entityManager.persist(band2);
        Iterable<BandEntity> iterable = repository.findAll();
        assertThat(iterable).isNotNull();
        List<BandEntity> bands = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(bands.size(), 2);
        assertThat(bands.contains(band1)).isTrue();
        assertThat(bands.contains(band2)).isTrue();
    }

}