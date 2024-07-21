package com.agrusi.backendapi.mapper;

import com.agrusi.backendapi.dto.response.field.FieldResponseDto;
import com.agrusi.backendapi.dto.response.field.SizeMap;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.model.Field;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
 * componentModel = "spring" --> The generated mapper is a Spring bean
 * and can be retrieved via @Autowired
*/

@Mapper(componentModel = "spring")
public interface FieldMapper {

    @Mapping(source = "area", target = "coordinates", qualifiedByName = "polygonToList")
    @Mapping(source = "center", target = "center", qualifiedByName = "pointToList")
    @Mapping(target = "size", expression = "java(mapSize(field, areaUnit))")
    FieldResponseDto toFieldResponseDto(Field field, @Context EAreaUnit areaUnit);

    @Named("pointToList")
    default List<Double> pointToList(Point point) {

        List<Double> coordinates = new ArrayList<>();

        coordinates.add(point.getX());
        coordinates.add(point.getY());

        return coordinates;
    }

    @Named("polygonToList")
    default List<List<List<Double>>> polygonToList(Polygon polygon) {

        List<List<List<Double>>> coordinates = new ArrayList<>();

        // Exterior ring
        coordinates.add(linearRingToList(polygon.getExteriorRing()));

        // Interior rings (holes)
        for (int i = 0; i < polygon.getNumInteriorRing(); i++) {
            coordinates.add(linearRingToList(polygon.getInteriorRingN(i)));
        }

        return coordinates;
    }

    default List<List<Double>> linearRingToList(LineString linearRing) {

        List<List<Double>> coordinates = new ArrayList<>();

        for (Coordinate coordinate : linearRing.getCoordinates()) {
            List<Double> point = new ArrayList<>();
            point.add(coordinate.getX());
            point.add(coordinate.getY());
            coordinates.add(point);
        }

        return coordinates;
    }

    default SizeMap mapSize(Field field, @Context EAreaUnit areaUnit) {

        BigDecimal size = switch (areaUnit) {

            case HECTARE -> field.getAreaSizeInHectares();
            case ACRE -> field.getAreaSizeInAcres();
            case SQUARE_METER -> field.getSize();
            default -> throw new IllegalArgumentException("Unsupported area unit: " + areaUnit);
        };

        return new SizeMap(size, areaUnit.getUnitOfArea());
    }
}
