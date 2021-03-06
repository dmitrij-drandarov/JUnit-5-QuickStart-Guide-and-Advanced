package com.dmitrijdrandarov.junit5;

import com.dmitrijdrandarov.entities.DummyFruit;
import com.dmitrijdrandarov.repositories.DummyFruitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@Transactional
@Disabled("Right now incompatible with JDK 12")
class SpringBoot5_02_JUnit5Test {

    private static final Logger LOG = LoggerFactory.getLogger(SpringBoot5_02_JUnit5Test.class);

    @Autowired
    private DummyFruitRepository fruitRepository;

    @Test
    void saveTest() {
        DummyFruit fruity = fruitRepository.save(new DummyFruit(null, DummyFruit.TYPE.APPLE, "Fruity", "Description", 10D));
        fruity = fruitRepository.findOneById(fruity.getId());
        Assertions.assertNotNull(fruity);

        LOG.info(fruity.toString());
    }

    @Test
    void dataSQLTest() {
        List<DummyFruit> dummyFruits = fruitRepository.findAllBy();
        Assertions.assertEquals(3, dummyFruits.size());

        dummyFruits.stream().map(DummyFruit::toString).forEach(LOG::info);
    }
}