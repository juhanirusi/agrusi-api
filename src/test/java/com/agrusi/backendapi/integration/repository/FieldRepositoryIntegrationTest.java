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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * @IntegrationTest --> Our custom annotation allowing us to run only
 * integration tests if we want to
*/

@IntegrationTest
@DataJpaTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class FieldRepositoryIntegrationTest {

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FarmRepository farmRepository;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    private Farm farm;
    private Farm farmWithoutField;

    private Field field;

    @BeforeEach
    public void setUp() {

        // Set the spatial reference identifier to 4326 (WGS 84)
        int spatialReferenceIdentifier = 4326;

        farm = new Farm();
        farm.setName("Jack's Farm");

        farmWithoutField = new Farm();
        farmWithoutField.setName("Jane's Ranch");

        farm = farmRepository.save(farm);

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

        field = new Field();
        field.setName("Farm field");
        field.setArea(areaOfField);
        field.setCenter(centerOfField);
        field.setSize(BigDecimal.valueOf(100_000));
        field.setFarm(farm);

        field = fieldRepository.save(field);
        farmWithoutField = farmRepository.save(farmWithoutField);
    }

    @Test
    @DisplayName("Find field by field Id and farm.")
    public void testFindFieldByIdAndFarm_ExistingField() {

        Optional<Field> foundField = fieldRepository.findByIdAndFarm(field.getId(), farm);

        assertTrue(foundField.isPresent());
        assertEquals(field.getName(), foundField.get().getName());
    }

    @Test
    @DisplayName("Find field by field Id and farm - Farm without field.")
    public void testFindFieldByIdAndFarm_FarmWithoutField() {

        Optional<Field> foundField = fieldRepository.findByIdAndFarm(field.getId(), farmWithoutField);

        assertTrue(foundField.isEmpty());
    }
}
