package com.agrusi.backendapi.controller;

import com.agrusi.backendapi.dto.request.field.FieldPostDto;
import com.agrusi.backendapi.dto.request.field.FieldPutDto;
import com.agrusi.backendapi.dto.response.field.FieldResponseDto;
import com.agrusi.backendapi.handler.ResponseHandler;
import com.agrusi.backendapi.service.FieldService;
import jakarta.validation.Valid;
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.operation.TransformException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/farms")
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping(value = "/{publicFarmId}/fields/{fieldId}")
    public ResponseEntity<?> getField(
            @PathVariable UUID publicFarmId,
            @PathVariable Long fieldId
    ) {
        FieldResponseDto field = fieldService.getFieldByFieldId(publicFarmId, fieldId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Field details fetched successfully.",
                field
        );
    }

    @PostMapping(value = "/{publicFarmId}/fields")
    public ResponseEntity<?> createField(
            @PathVariable UUID publicFarmId, @Valid @RequestBody FieldPostDto fieldPostDTO
    ) throws FactoryException, TransformException {

        FieldResponseDto field = fieldService.createNewField(publicFarmId, fieldPostDTO);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.CREATED,
                "Field created successfully.",
                field
            );
    }

    // TODO --> RESEARCH MORE ABOUT THE "FactoryException" AND "TransformException" EXCEPTIONS !!!

    @PutMapping(value = "/{publicFarmId}/fields/{fieldId}")
    public ResponseEntity<?> updateFieldPut(
            @PathVariable UUID publicFarmId,
            @PathVariable Long fieldId,
            @Valid @RequestBody FieldPutDto fieldPutDto
    ) throws FactoryException, TransformException {

        FieldResponseDto field = fieldService.updateFieldByFieldIdPut(
                publicFarmId, fieldId, fieldPutDto
        );

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.CREATED,
                "Field updated successfully.",
                field
        );
    }

    @DeleteMapping(value = "/{publicFarmId}/fields/{fieldId}")
    public ResponseEntity<?> deleteField(
            @PathVariable UUID publicFarmId, @PathVariable Long fieldId
    ) {
        fieldService.deleteFieldById(publicFarmId, fieldId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Field deleted successfully.",
                Map.of("fieldId", fieldId)
        );
    }
}
