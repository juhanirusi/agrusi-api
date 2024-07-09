package com.agrusi.backendapi.service;

import com.agrusi.backendapi.dto.request.field.FieldPostDto;
import com.agrusi.backendapi.dto.request.field.FieldPutDto;
import com.agrusi.backendapi.dto.response.field.FieldResponseDto;
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.operation.TransformException;

import java.util.UUID;

public interface FieldService {

    FieldResponseDto getFieldByFieldId(UUID publicFarmId, Long fieldId);

    FieldResponseDto createNewField(
            UUID publicFarmId, FieldPostDto fieldPostDto
    ) throws FactoryException, TransformException;

    FieldResponseDto updateFieldByFieldIdPut(
            UUID publicFarmId, Long fieldId, FieldPutDto fieldPutDto
    ) throws FactoryException, TransformException;

    void deleteFieldById(UUID publicFarmId, Long fieldId);
}
