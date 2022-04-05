package com.epam.brest.dao.jpa.repository;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.entity.TrackEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@EnableAutoConfiguration
@EntityScan("com.epam.brest.*")
@ContextConfiguration(classes= TrackEntity.class)
@ActiveProfiles("test")
@Slf4j
class TrackRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TrackRepository repository;

    @Test
    void injectedComponentsAreNotNull(){
        log.debug("injectedComponentsAreNotNull()");
        assertThat(entityManager).isNotNull();
        assertThat(repository).isNotNull();
    }


    @Test
    void findByBand_BandIdEquals() {
        log.debug("findByBand_BandIdEquals()");
        BandEntity band1 = BandEntity.builder()
                .bandName("test band1")
                .build();
        entityManager.persist(band1);
        BandEntity band2 = BandEntity.builder()
                .bandName("test band2")
                .build();
        entityManager.persist(band2);
        TrackEntity track1 = TrackEntity.builder()
                .band(band1)
                .trackName("Test track1")
                .build();
        entityManager.persist(track1);
        TrackEntity track2 = TrackEntity.builder()
                .band(band2)
                .trackName("Test track2")
                .build();
        entityManager.persist(track2);
        Iterable<TrackEntity> iterable = repository.findByBand_BandIdEquals(band2.getBandId());
        assertThat(iterable).isNotNull();
        List<TrackEntity> trackEntityList = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(trackEntityList.size(), 1);
        assertThat(trackEntityList.contains(track1)).isFalse();
        assertThat(trackEntityList.contains(track2)).isTrue();
    }

    @Test
    void findByTrackReleaseDateBetween() {
        log.debug("findByTrackReleaseDateBetween()");
        LocalDate from = LocalDate.parse("2020-01-01");
        LocalDate to = LocalDate.parse("2021-01-01");
        BandEntity band1 = BandEntity.builder()
                .bandName("test band1")
                .build();
        entityManager.persist(band1);
        BandEntity band2 = BandEntity.builder()
                .bandName("test band2")
                .build();
        entityManager.persist(band2);
        TrackEntity track1 = TrackEntity.builder()
                .band(band1)
                .trackReleaseDate(LocalDate.parse("2019-02-02"))
                .trackName("Test track1")
                .build();
        entityManager.persist(track1);
        TrackEntity track2 = TrackEntity.builder()
                .band(band2)
                .trackReleaseDate(LocalDate.parse("2020-02-02"))
                .trackName("Test track2")
                .build();
        entityManager.persist(track2);
        Iterable<TrackEntity> iterable = repository.findByTrackReleaseDateBetween(from, to);
        assertThat(iterable).isNotNull();
        List<TrackEntity> trackEntityList = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(trackEntityList.size(), 1);
        assertThat(trackEntityList.contains(track1)).isFalse();
        assertThat(trackEntityList.contains(track2)).isTrue();
    }

    @Test
    void findByTrackReleaseDateBefore() {
        log.debug("findByTrackReleaseDateBefore()");
        LocalDate to = LocalDate.parse("2021-01-01");
        BandEntity band1 = BandEntity.builder()
                .bandName("test band1")
                .build();
        entityManager.persist(band1);
        BandEntity band2 = BandEntity.builder()
                .bandName("test band2")
                .build();
        entityManager.persist(band2);
        TrackEntity track1 = TrackEntity.builder()
                .band(band1)
                .trackReleaseDate(LocalDate.parse("2019-02-02"))
                .trackName("Test track1")
                .build();
        entityManager.persist(track1);
        TrackEntity track2 = TrackEntity.builder()
                .band(band2)
                .trackReleaseDate(LocalDate.parse("2020-02-02"))
                .trackName("Test track2")
                .build();
        entityManager.persist(track2);
        Iterable<TrackEntity> iterable = repository.findByTrackReleaseDateBefore(to);
        assertThat(iterable).isNotNull();
        List<TrackEntity> trackEntityList = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(trackEntityList.size(), 2);
        assertThat(trackEntityList.contains(track2)).isTrue();
        assertThat(trackEntityList.contains(track1)).isTrue();
    }

    @Test
    void findByTrackReleaseDateAfter() {
        LocalDate from = LocalDate.parse("2020-01-01");
        BandEntity band1 = BandEntity.builder()
                .bandName("test band1")
                .build();
        entityManager.persist(band1);
        BandEntity band2 = BandEntity.builder()
                .bandName("test band2")
                .build();
        entityManager.persist(band2);
        TrackEntity track1 = TrackEntity.builder()
                .band(band1)
                .trackReleaseDate(LocalDate.parse("2019-02-02"))
                .trackName("Test track1")
                .build();
        entityManager.persist(track1);
        TrackEntity track2 = TrackEntity.builder()
                .band(band2)
                .trackReleaseDate(LocalDate.parse("2020-02-02"))
                .trackName("Test track2")
                .build();
        entityManager.persist(track2);
        Iterable<TrackEntity> iterable = repository.findByTrackReleaseDateAfter(from);
        assertThat(iterable).isNotNull();
        List<TrackEntity> trackEntityList = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(trackEntityList.size(), 1);
        assertThat(trackEntityList.contains(track1)).isFalse();
        assertThat(trackEntityList.contains(track2)).isTrue();
    }
}