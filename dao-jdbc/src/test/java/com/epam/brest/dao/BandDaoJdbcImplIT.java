package com.epam.brest.dao;

import com.epam.brest.SpringDataSourceTestConfig;
import com.epam.brest.dao.exception.NotUniqueException;
import com.epam.brest.dao.jdbc.BandDaoJdbcImpl;
import com.epam.brest.model.Band;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@DataJdbcTest
@Import({BandDaoJdbcImpl.class})
@ContextConfiguration(classes = SpringDataSourceTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
@ActiveProfiles({"test", "jdbc"})
@Sql({"/create-db.sql", "/init-db.sql"})
class BandDaoJdbcImplIT {

    private final Logger logger = LogManager.getLogger(BandDaoJdbcImplIT.class);

    @Autowired
    private BandDaoJdbcImpl bandDaoJdbc;

    @Test
    void testFindAllBands() {
        logger.debug("Band execute test: testFindAllBands()");
        assertNotNull(bandDaoJdbc);
        List<Band> bandList = bandDaoJdbc.findAll();
        assertNotNull(bandList);
    }

    @Test
    void testCreateBand() {
        logger.debug("Band execute test: testCreateBand()");
        assertNotNull(bandDaoJdbc);
        int bandsSizeBefore = bandDaoJdbc.count();
        Band band = Band.builder()
                .bandName("Gods Tower")
                .bandDetails("Band of metal")
                .build();
        bandDaoJdbc.findAll().forEach(System.out::println);
        Integer newBandId = bandDaoJdbc.create(band);
        assertNotNull(newBandId);
        assertEquals(bandsSizeBefore, bandDaoJdbc.count() - 1);
    }

    @Test
    void testTryToCreateBandNotUniqueException() {
        logger.debug("Band execute test: tryToCreateBandNotUniqueException()");
        assertNotNull(bandDaoJdbc);
        Band band = Band.builder()
                .bandName("Gods Tower")
                .build();
        assertThrows(NotUniqueException.class, () -> {
            bandDaoJdbc.create(band);
            bandDaoJdbc.create(band);
        });
    }

    @Test
    void testShouldCountBands() {
        logger.debug("Band execute test: testShouldCountBands()");
        assertNotNull(bandDaoJdbc);
        Integer quantity = bandDaoJdbc.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }

    @Test
    void testGetBandById() {
        logger.debug("Band execute test: getBandById()");
        List<Band> bands = bandDaoJdbc.findAll();
        if (bands.size() == 0) {
            Band band = Band.builder()
                    .bandName("5Diez")
                    .bandDetails("Band of metal")
                    .build();
            bandDaoJdbc.create(band);
            bands = bandDaoJdbc.findAll();
        }

        Band bandSrc = bands.get(0);
        Band bandDst = bandDaoJdbc.getBandById(bandSrc.getBandId());
        assertEquals(bandSrc.getBandName().toUpperCase(), bandDst.getBandName().toUpperCase());
    }

    @Test
    void testUpdateBand() {
        logger.debug("Band execute test: testUpdateBand()");
        List<Band> bands = bandDaoJdbc.findAll();
        if (bands.size() == 0) {
            Band band = Band.builder()
                    .bandName("5Diez")
                    .bandDetails("Band of metal")
                    .build();
            bandDaoJdbc.create(band);
            bands = bandDaoJdbc.findAll();
        }
        Band bandSrc = bands.get(0);
        bandSrc.setBandName(bandSrc.getBandName() + "#");
        bandSrc.setBandDetails(bandSrc.getBandDetails() + "#");
        bandDaoJdbc.update(bandSrc);
        Band bandDst = bandDaoJdbc.getBandById(bandSrc.getBandId());
        assertEquals(bandSrc.getBandName(), bandDst.getBandName());
        assertEquals(bandSrc.getBandDetails(), bandDst.getBandDetails());
    }

    @Test
    void testDeleteBand() {
        logger.debug("Band execute test: testDeleteBand()");
        Band band = Band.builder()
                .bandName("5Diez")
                .bandDetails("Band of metal")
                .build();
        bandDaoJdbc.create(band);
        List<Band> bands = bandDaoJdbc.findAll()
                .stream().sorted(Comparator.comparing(Band::getBandId)).collect(Collectors.toList());
        bandDaoJdbc.delete(bands.get(bands.size() - 1).getBandId());
        assertEquals(bands.size() - 1, bandDaoJdbc.findAll().size());
    }

    @Test
    @Sql(scripts = {"/delete-all-tracks.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testDeleteAllBands() {
        logger.debug("Band execute test: testDeleteAllBands()");
        List<Band> bandsBefore = bandDaoJdbc.findAll();
        assertEquals(bandDaoJdbc.deleteAllBands(), bandsBefore.size());
        List<Band> bandsAfter = bandDaoJdbc.findAll();
        assertNotNull(bandsAfter);
        assertEquals(bandsAfter.size(), 0);
    }

}
