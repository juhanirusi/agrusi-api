package com.agrusi.backendapi.integration.repository;

import com.agrusi.backendapi.IntegrationTest;
import com.agrusi.backendapi.model.Farm;
import com.agrusi.backendapi.model.Field;
import com.agrusi.backendapi.repository.FarmRepository;
import com.agrusi.backendapi.repository.FieldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Set;
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

    @Autowired
    FieldRepository fieldRepository;

    private Farm farm;

    private UUID farmPublicId;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    @BeforeEach
    public void setUp() {

        // Set the spatial reference identifier to 4326 (WGS 84)
        int spatialReferenceIdentifier = 4326;

        Polygon areaOfField = geometryFactory.createPolygon(new Coordinate[] {
                new Coordinate(0, 0),
                new Coordinate(1, 0),
                new Coordinate(1, 1),
                new Coordinate(0, 1),
                new Coordinate(0, 0)
        });
        areaOfField.setSRID(spatialReferenceIdentifier);

        Point centerOfField = geometryFactory.createPoint(
                new Coordinate(0,1)
        );
        centerOfField.setSRID(spatialReferenceIdentifier);

        //-----------------------------------------------------------

        farm = new Farm();
        farm.setName("Jack's Farm");

        Field field1 = new Field();
        field1.setName("Field name 1");
        field1.setArea(areaOfField);
        field1.setCenter(centerOfField);
        field1.setSize(BigDecimal.valueOf(11.50));
        field1.setFarm(farm);

        Field field2 = new Field();
        field2.setName("Field name 2");
        field2.setArea(areaOfField);
        field2.setCenter(centerOfField);
        field2.setSize(BigDecimal.valueOf(21.12));
        field2.setFarm(farm);

        fieldRepository.save(field1);
        fieldRepository.save(field2);

        Set<Field> fields = Set.of(field1, field2);

        farm.setFields(fields);

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

    @Test
    @DisplayName("Get a farm's total land area.")
    public void testCalculateTotalLandAreaOfAllFieldsOfFarm() {

        BigDecimal totalLandArea = farmRepository.calculateTotalLandAreaOfAllFields(farm.getId());

        assertEquals(
                BigDecimal.valueOf(32.62).setScale(2, RoundingMode.UP),
                totalLandArea
        );
    }
}
