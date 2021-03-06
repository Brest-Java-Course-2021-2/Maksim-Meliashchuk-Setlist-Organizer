package com.epam.brest.dao;

import com.epam.brest.SpringDataSourceTestConfig;
import com.epam.brest.dao.jdbc.BandDtoDaoJdbcImpl;
import com.epam.brest.model.BandDto;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJdbcTest
@Import({BandDtoDaoJdbcImpl.class})
@ContextConfiguration(classes = SpringDataSourceTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
@ActiveProfiles({"test", "jdbc"})
@Sql({"/create-db.sql", "/init-db.sql"})
class BandDtoDaoJdbcImplIT {

    private final Logger logger = LogManager.getLogger(BandDtoDaoJdbcImplIT.class);

    @Autowired
    private BandDtoDaoJdbcImpl bandDtoDaoJdbc;

    @Test
    void testFindAllWithCountTrack() {
        logger.debug("Track execute test: findAllWithCountTrack()");
        assertNotNull(bandDtoDaoJdbc);
        assertNotNull(bandDtoDaoJdbc.findAllWithCountTrack());
        List<BandDto> bands = bandDtoDaoJdbc.findAllWithCountTrack();
        assertNotNull(bands);
        assertTrue(bands.size() > 0);
    }
}
