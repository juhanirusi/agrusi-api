package com.agrusi.backendapi.service.impl;

import com.agrusi.backendapi.dto.request.field.FieldPostDto;
import com.agrusi.backendapi.dto.request.field.FieldPutDto;
import com.agrusi.backendapi.dto.response.field.FieldResponseDto;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.exception.farm.FarmNotFoundException;
import com.agrusi.backendapi.exception.field.FieldNotFoundException;
import com.agrusi.backendapi.mapper.FieldMapper;
import com.agrusi.backendapi.model.Farm;
import com.agrusi.backendapi.model.Field;
import com.agrusi.backendapi.repository.FarmRepository;
import com.agrusi.backendapi.repository.FieldRepository;
import com.agrusi.backendapi.service.FieldService;
import org.geotools.api.geometry.MismatchedDimensionException;
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.operation.MathTransform;
import org.geotools.api.referencing.operation.TransformException;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FieldMapper fieldMapper;

    public FieldServiceImpl(
            FieldRepository fieldRepository,
            FarmRepository farmRepository,
            FieldMapper fieldMapper
    ) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
        this.fieldMapper = fieldMapper;
    }

    @Override
    public FieldResponseDto getFieldByFieldId(UUID publicFarmId, Long fieldId) {

        Farm farm = farmRepository.findByPublicId(publicFarmId)
                .orElseThrow(() -> new FarmNotFoundException(publicFarmId));

        Field field = fieldRepository.findByIdAndFarm(fieldId, farm)
                .orElseThrow(() -> new FieldNotFoundException(fieldId, publicFarmId));

        return fieldMapper.toFieldResponseDto(field, EAreaUnit.HECTARE);
    }

    @Override
    @Transactional
    public FieldResponseDto createNewField(UUID publicFarmId, FieldPostDto fieldPostDto)
            throws FactoryException, TransformException {

        Farm farm = farmRepository.findByPublicId(publicFarmId)
                    .orElseThrow(() -> new FarmNotFoundException(publicFarmId));

        Field newField = new Field();

        newField.setName(fieldPostDto.getName());
        newField.setArea(convertCoordinatesToPolygon(fieldPostDto.getCoordinates()));
        newField.setCenter(calculateCenter(newField.getArea()));
        newField.setSize(calculateFieldSizeInSquareMeters(newField.getArea()));
        newField.setFarm(farm);

        fieldRepository.save(newField);

        return fieldMapper.toFieldResponseDto(newField, EAreaUnit.HECTARE);
    }

    @Override
    @Transactional
    public FieldResponseDto updateFieldByFieldIdPut(
            UUID publicFarmId, Long fieldId, FieldPutDto fieldPutDto
    ) throws FactoryException, TransformException {

        Farm farm = farmRepository.findByPublicId(publicFarmId)
                .orElseThrow(() -> new FarmNotFoundException(publicFarmId));

        Field field = fieldRepository.findByIdAndFarm(fieldId, farm)
                .orElseThrow(() -> new FieldNotFoundException(fieldId, publicFarmId));

        if (fieldPutDto.getName() != null) {
            field.setName(fieldPutDto.getName());
        }

        if (fieldPutDto.getCoordinates() != null) {
            field.setArea(convertCoordinatesToPolygon(fieldPutDto.getCoordinates()));
            field.setCenter(calculateCenter(field.getArea()));
            field.setSize(calculateFieldSizeInSquareMeters(field.getArea()));
        }

        fieldRepository.save(field);

        return fieldMapper.toFieldResponseDto(field, EAreaUnit.HECTARE);
    }

    @Override
    @Transactional
    public void deleteFieldById(UUID publicFarmId, Long fieldId) {

        Farm farm = farmRepository.findByPublicId(publicFarmId)
                .orElseThrow(() -> new FarmNotFoundException(publicFarmId));

        Field field = fieldRepository.findByIdAndFarm(fieldId, farm)
                .orElseThrow(() -> new FieldNotFoundException(fieldId, publicFarmId));

        fieldRepository.delete(field);
    }

    //--------------------------------------------|
    // HELPER METHODS FOR CREATING A NEW FIELD... |
    //--------------------------------------------|

    private Polygon convertCoordinatesToPolygon(List<List<List<Double>>> coordinates) {

        GeometryFactory geometryFactory = new GeometryFactory();

        LinearRing shell = geometryFactory.createLinearRing(
                convertListOfCoordinatesToCoordinateArray(coordinates.get(0))
        );

        LinearRing[] holes = new LinearRing[coordinates.size() - 1];

        for (int i = 1; i < coordinates.size(); i++) {
            holes[i - 1] = geometryFactory.createLinearRing(
                    convertListOfCoordinatesToCoordinateArray(coordinates.get(i))
            );
        }

        return geometryFactory.createPolygon(shell, holes);
    }

    private Coordinate[] convertListOfCoordinatesToCoordinateArray(
            List<List<Double>> coordinatesList
    ) {
        Coordinate[] coordinates = new Coordinate[coordinatesList.size()];

        for (int i = 0; i < coordinatesList.size(); i++) {
            List<Double> point = coordinatesList.get(i);
            coordinates[i] = new Coordinate(point.get(0), point.get(1));
        }

        return coordinates;
    }

    private Point calculateCenter(Polygon polygon) {

        GeometryFactory geometryFactory = new GeometryFactory();

        return geometryFactory.createPoint(polygon.getCentroid().getCoordinate());
    }

    public BigDecimal calculateFieldSizeInSquareMeters(Polygon polygon) {

        if (polygon == null) {
            throw new IllegalArgumentException("Polygon must not be null.");
        }

        try {
            Point centroid = polygon.getCentroid();
            String code = "AUTO:42001," + centroid.getX() + "," + centroid.getY();
            CoordinateReferenceSystem auto = CRS.decode(code);

            MathTransform transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, auto);
            Polygon projectedPolygon = (Polygon) JTS.transform(polygon, transform);

            BigDecimal areaInSquareMeters = BigDecimal.valueOf(projectedPolygon.getArea());

            return areaInSquareMeters.setScale(2, RoundingMode.HALF_UP);

        } catch (MismatchedDimensionException | TransformException | FactoryException e) {
            e.printStackTrace();
            throw new RuntimeException("Error calculating area", e);
        }
    }
}
