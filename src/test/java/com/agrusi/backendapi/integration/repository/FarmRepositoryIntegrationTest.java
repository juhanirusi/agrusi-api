package com.agrusi.backendapi.integration.repository;

import com.agrusi.backendapi.IntegrationTest;
import com.agrusi.backendapi.model.Farm;
import com.agrusi.backendapi.repository.FarmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/*
* @IntegrationTest --> Our custom annotation allowing us to run only
* integration tests if we want to
*/

@IntegrationTest
@DataJpaTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class FarmRepositoryIntegrationTest {

    @Autowired
    private FarmRepository farmRepository;

    private Farm farm;

    private UUID farmPublicId;

    @BeforeEach
    public void setUp() {

        farm = new Farm();

        farm.setName("Jack's Farm");

        farmRepository.save(farm);

        farmPublicId = farm.getPublicId();
    }

    @Test
    @DisplayName("Get a farm by public ID.")
    public void testFindFarmByPublicId() {

        Optional<Farm> foundFarm = farmRepository.findByPublicId(farmPublicId);

        assertTrue(foundFarm.isPresent());
        assertEquals(farm.getName(), foundFarm.get().getName());
    }

    @Test
    @DisplayName("Find (non-existing) farm by public ID.")
    public void testFindFarmByPublicId_NonExistingFarm() {

        Optional<Farm> foundFarm = farmRepository.findByPublicId(UUID.randomUUID());

        assertTrue(foundFarm.isEmpty());
    }

    @Test
    @DisplayName("Farm exists by public ID.")
    public void testFarmExistsByPublicId_ExistingFarm() {

        Boolean exists = farmRepository.existsByPublicId(farmPublicId);

        assertTrue(exists);
    }

    @Test
    @DisplayName("Farm (doesn't) exists by public ID.")
    public void testFarmExistsByPublicId_NonExistingFarm() {

        Boolean exists = farmRepository.existsByPublicId(UUID.randomUUID());

        assertFalse(exists);
    }
}
