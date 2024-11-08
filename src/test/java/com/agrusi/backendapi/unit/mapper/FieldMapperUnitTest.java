package com.agrusi.backendapi.unit.mapper;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.dto.response.SizeMap;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.mapper.FieldMapper;
import com.agrusi.backendapi.model.Field;
import com.agrusi.backendapi.service.conversion.UnitConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.locationtech.jts.geom.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@UnitTest
public class FieldMapperUnitTest {

    private Field field;

    private final FieldMapper fieldMapper = Mappers.getMapper(FieldMapper.class);

    private final UnitConversionService unitConversionService = new UnitConversionService();

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

    // Test our field size converters for all field sizes...

    static Stream<Arguments> conversionData() {

        return Stream.of(
                arguments(BigDecimal.valueOf(1.00), EAreaUnit.HECTARE, "hectare"),
                arguments(BigDecimal.valueOf(10000.00), EAreaUnit.SQUARE_METRE, "square metre")
        );
    }

    @ParameterizedTest
    @MethodSource("conversionData")
    @DisplayName("Test field size conversion to all area sizes.")
    public void testConvertAreaSize(
            BigDecimal expectedValue, EAreaUnit unitOfArea, String unitOfAreaAsString
    ) {
        field.setSize(BigDecimal.valueOf(10_000).setScale(2, RoundingMode.HALF_UP));

        SizeMap sizeMap = fieldMapper.convertFieldSize(
                field,
                unitOfArea,
                unitConversionService
        );

        assertEquals(unitOfAreaAsString, sizeMap.unitOfArea());
        assertEquals(expectedValue.setScale(2, RoundingMode.HALF_UP), sizeMap.value());
    }
}
