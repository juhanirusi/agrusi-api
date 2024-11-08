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

    @GetMapping(value = "/{farmPublicId}/fields/{fieldId}")
    public ResponseEntity<?> getField(
            @PathVariable UUID farmPublicId,
            @PathVariable Long fieldId
    ) {
        FieldResponseDto field = fieldService.getFieldByFieldId(farmPublicId, fieldId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Field details fetched successfully.",
                field
        );
    }

    @PostMapping(value = "/{farmPublicId}/fields")
    public ResponseEntity<?> createField(
            @PathVariable UUID farmPublicId, @Valid @RequestBody FieldPostDto fieldPostDTO
    ) throws FactoryException, TransformException {

        FieldResponseDto field = fieldService.createNewField(farmPublicId, fieldPostDTO);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.CREATED,
                "Field created successfully.",
                field
            );
    }

    @PutMapping(value = "/{farmPublicId}/fields/{fieldId}")
    public ResponseEntity<?> updateFieldPut(
            @PathVariable UUID farmPublicId,
            @PathVariable Long fieldId,
            @Valid @RequestBody FieldPutDto fieldPutDto
    ) throws FactoryException, TransformException {

        FieldResponseDto field = fieldService.updateFieldByFieldIdPut(
                farmPublicId, fieldId, fieldPutDto
        );

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.CREATED,
                "Field updated successfully.",
                field
        );
    }

    @DeleteMapping(value = "/{farmPublicId}/fields/{fieldId}")
    public ResponseEntity<?> deleteField(
            @PathVariable UUID farmPublicId, @PathVariable Long fieldId
    ) {
        fieldService.deleteFieldById(farmPublicId, fieldId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Field deleted successfully.",
                Map.of("fieldId", fieldId)
        );
    }
}
