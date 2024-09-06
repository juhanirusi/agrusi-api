package com.agrusi.backendapi.unit.service;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.dto.request.field.FieldPostDto;
import com.agrusi.backendapi.dto.request.field.FieldPutDto;
import com.agrusi.backendapi.dto.response.field.FieldResponseDto;
import com.agrusi.backendapi.dto.response.field.SizeMap;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.exception.farm.FarmNotFoundException;
import com.agrusi.backendapi.exception.field.FieldNotFoundException;
import com.agrusi.backendapi.mapper.FieldMapper;
import com.agrusi.backendapi.model.Farm;
import com.agrusi.backendapi.model.Field;
import com.agrusi.backendapi.repository.FarmRepository;
import com.agrusi.backendapi.repository.FieldRepository;
import com.agrusi.backendapi.service.impl.FieldServiceImpl;
import com.agrusi.backendapi.unit.util.ReflectionTestUtils;
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.operation.TransformException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
 * @UnitTest --> Our custom annotation allowing us to run only
 * unit tests if we want to
*/

@UnitTest
@ExtendWith(MockitoExtension.class)
public class FieldServiceImplUnitTest {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FieldMapper fieldMapper;

    @InjectMocks
    private FieldServiceImpl fieldService;

    private UUID publicFarmId;
    private Farm farm;

    private Long fieldId;
    private Field field;

    private List<List<List<Double>>> fieldCoordinates;
    private List<Double> fieldCenter;

    private FieldResponseDto expectedFieldResponseDto;
    private FieldResponseDto expectedFieldPutUpdateResponseDto;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {

        ReflectionTestUtils reflectionTestUtils = new ReflectionTestUtils();

        publicFarmId = UUID.randomUUID();
        fieldId = 1L;

        farm = new Farm(
                1L,
                "Jack's Farm",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        reflectionTestUtils.setField(
                farm,
                "publicId",
                publicFarmId
        );

        field = new Field();

        field.setName("Farm field");

        reflectionTestUtils.setField(field, "id", fieldId);
        field.setFarm(farm);

        fieldCoordinates = new ArrayList<>();
        List<List<Double>> outerBoundary = new ArrayList<>();

        outerBoundary.add(Arrays.asList(-104.99404, 39.75621));
        outerBoundary.add(Arrays.asList(-104.99404, 39.75212));
        outerBoundary.add(Arrays.asList(-104.98999, 39.75212));
        outerBoundary.add(Arrays.asList(-104.98999, 39.75621));
        outerBoundary.add(Arrays.asList(-104.99404, 39.75621));

        fieldCoordinates.add(outerBoundary);

        expectedFieldResponseDto = new FieldResponseDto(
                "Farm field",
                fieldCoordinates,
                Arrays.asList(-104.99404, 39.75621),
                new SizeMap(BigDecimal.valueOf(10.0), "hectare")
        );

        expectedFieldPutUpdateResponseDto = new FieldResponseDto(
                "Updated farm field",
                fieldCoordinates,
                Arrays.asList(-104.99404, 39.75621),
                new SizeMap(BigDecimal.valueOf(10.0), "hectare")
        );
    }

    @Test
    @DisplayName("Create a new farm field.")
    public void testCreateNewFarmField() throws FactoryException, TransformException {

        FieldPostDto fieldPostDto = new FieldPostDto("Farm field", fieldCoordinates);

        when(farmRepository.findByPublicId(publicFarmId)).thenReturn(Optional.of(farm));

        when(fieldMapper.toFieldResponseDto(
                any(Field.class),
                eq(EAreaUnit.HECTARE)
        )).thenReturn(expectedFieldResponseDto);

        FieldResponseDto responseDto = fieldService.createNewField(publicFarmId, fieldPostDto);

        assertNotNull(responseDto);
        assertEquals(expectedFieldResponseDto, responseDto);

        verify(farmRepository).findByPublicId(publicFarmId);
        verify(fieldRepository).save(any(Field.class));
        verify(fieldMapper).toFieldResponseDto(any(Field.class), eq(EAreaUnit.HECTARE));
    }

    @Test
    @DisplayName("Get a field by field ID and farm.")
    public void testGetFieldByFieldId() {

        when(farmRepository.findByPublicId(publicFarmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.findByIdAndFarm(fieldId, farm)).thenReturn(Optional.of(field));
        when(fieldMapper.toFieldResponseDto(field, EAreaUnit.HECTARE)).thenReturn(expectedFieldResponseDto);

        FieldResponseDto responseDto = fieldService.getFieldByFieldId(publicFarmId, fieldId);

        assertNotNull(responseDto);
        assertEquals(expectedFieldResponseDto, responseDto);

        verify(farmRepository).findByPublicId(publicFarmId);
        verify(fieldRepository).findByIdAndFarm(fieldId, farm);
        verify(fieldMapper).toFieldResponseDto(field, EAreaUnit.HECTARE);
    }

    @Test
    @DisplayName("Get a field by field ID and farm (FarmNotFound).")
    public void testGetFieldByFieldIdFarmNotFound() {

        when(farmRepository.findByPublicId(publicFarmId)).thenReturn(Optional.empty());

        assertThrows(FarmNotFoundException.class, () ->
                fieldService.getFieldByFieldId(publicFarmId, 1L)
        );

        verify(farmRepository).findByPublicId(publicFarmId);
        verifyNoMoreInteractions(fieldRepository, fieldMapper);
    }

    @Test
    @DisplayName("Get a field by field ID and farm (FieldNotFound).")
    public void testGetFieldByFieldIdFieldNotFound() {

        when(farmRepository.findByPublicId(publicFarmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.findByIdAndFarm(fieldId, farm)).thenReturn(Optional.empty());

        assertThrows(FieldNotFoundException.class, () ->
                fieldService.getFieldByFieldId(publicFarmId, fieldId)
        );

        verify(farmRepository).findByPublicId(publicFarmId);
        verify(fieldRepository).findByIdAndFarm(fieldId, farm);
        verifyNoMoreInteractions(fieldMapper);
    }

    @Test
    @DisplayName("Update (PUT) a farm field.")
    public void testUpdateFieldByFieldIdPut() throws Exception {

        FieldPutDto fieldPutDto = new FieldPutDto("Updated farm field", fieldCoordinates);

        when(farmRepository.findByPublicId(publicFarmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.findByIdAndFarm(fieldId, farm)).thenReturn(Optional.of(field));
        when(fieldMapper.toFieldResponseDto(any(Field.class), eq(EAreaUnit.HECTARE)))
                .thenReturn(expectedFieldPutUpdateResponseDto);

        FieldResponseDto responseDto = fieldService.updateFieldByFieldIdPut(
                publicFarmId, fieldId, fieldPutDto
        );

        assertNotNull(responseDto);
        assertEquals(expectedFieldPutUpdateResponseDto, responseDto);

        verify(farmRepository).findByPublicId(publicFarmId);
        verify(fieldRepository).findByIdAndFarm(fieldId, farm);
        verify(fieldRepository).save(field);
        verify(fieldMapper).toFieldResponseDto(field, EAreaUnit.HECTARE);
    }

    @Test
    @DisplayName("Delete a field by field ID and farm.")
    public void testDeleteFieldById() {

        when(farmRepository.findByPublicId(publicFarmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.findByIdAndFarm(fieldId, farm)).thenReturn(Optional.of(field));

        fieldService.deleteFieldById(publicFarmId, fieldId);

        verify(fieldRepository).delete(field);
    }
}
