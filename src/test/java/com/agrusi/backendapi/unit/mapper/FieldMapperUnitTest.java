package com.agrusi.backendapi.unit.mapper;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.dto.response.field.SizeMap;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.mapper.FieldMapper;
import com.agrusi.backendapi.model.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
public class FieldMapperUnitTest {

    private Field field;

    private final FieldMapper fieldMapper = Mappers.getMapper(FieldMapper.class);

    @BeforeEach
    public void setUp() {
        field = new Field();
    }

    @Test
    @DisplayName("Test turning the center Point coordinates into a list.")
    public void testPointToList() {

        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(-104.99404, 39.75621));

        List<Double> coordinates = fieldMapper.pointToList(point);

        assertEquals(2, coordinates.size());
        assertEquals(-104.99404, coordinates.get(0));
        assertEquals(39.75621, coordinates.get(1));
    }

    @Test
    @DisplayName("Test turning the area Polygon coordinates into a list of coordinates lists.")
    public void testPolygonToList() {

        GeometryFactory geometryFactory = new GeometryFactory();

        // Create a simple square polygon
        Coordinate[] coordinates = new Coordinate[] {
                new Coordinate(-104.99404, 39.75621),
                new Coordinate(-104.99404, 39.75212),
                new Coordinate(-104.98999, 39.75212),
                new Coordinate(-104.98999, 39.75621),
                new Coordinate(-104.99404, 39.75621)
        };

        LinearRing shell = geometryFactory.createLinearRing(coordinates);
        Polygon polygon = geometryFactory.createPolygon(shell, null);

        List<List<List<Double>>> result = fieldMapper.polygonToList(polygon);

        assertEquals(1, result.size());  // One exterior ring
        assertEquals(5, result.getFirst().size());  // 5 points in the exterior ring
    }

    @Test
    @DisplayName("Test getting area size in hectares.")
    public void testMapSizeHectares() {

        field.setSize(BigDecimal.valueOf(10_000));

        SizeMap sizeMap = fieldMapper.mapSize(field, EAreaUnit.HECTARE);

        assertEquals("hectare", sizeMap.unitOfArea());
        assertEquals(BigDecimal.valueOf(1.00).setScale(2, RoundingMode.HALF_UP), sizeMap.value());
    }

    @Test
    @DisplayName("Test getting area size in acres.")
    public void testMapSizeAcres() {

        field.setSize(BigDecimal.valueOf(10_000));

        SizeMap sizeMap = fieldMapper.mapSize(field, EAreaUnit.ACRE);

        assertEquals("acre", sizeMap.unitOfArea());
        assertEquals(BigDecimal.valueOf(2.47).setScale(2, RoundingMode.HALF_UP), sizeMap.value());
    }

    @Test
    @DisplayName("Test getting area size in square metres.")
    public void testMapSizeSquareMeters() {

        field.setSize(BigDecimal.valueOf(10_000).setScale(2, RoundingMode.HALF_UP));

        SizeMap sizeMap = fieldMapper.mapSize(field, EAreaUnit.SQUARE_METER);

        assertEquals("square metre", sizeMap.unitOfArea());
        assertEquals(BigDecimal.valueOf(10000.00).setScale(2, RoundingMode.HALF_UP), sizeMap.value());
    }
}
