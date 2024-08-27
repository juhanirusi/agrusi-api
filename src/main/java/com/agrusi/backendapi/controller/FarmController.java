package com.agrusi.backendapi.controller;

import com.agrusi.backendapi.dto.request.farm.FarmPatchDto;
import com.agrusi.backendapi.dto.request.farm.FarmPostDto;
import com.agrusi.backendapi.dto.response.farm.FarmResponseDto;
import com.agrusi.backendapi.handler.ResponseHandler;
import com.agrusi.backendapi.service.FarmService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/farms")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @GetMapping(value = "/{publicFarmId}")
    public ResponseEntity<?> getFarm(@PathVariable UUID publicFarmId) {

        FarmResponseDto farm = farmService.getFarmByPublicId(publicFarmId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Farm details fetched successfully.",
                farm
        );
    }

    @PostMapping(value = "")
    public ResponseEntity<?> createFarm(@Valid @RequestBody FarmPostDto farmPostDto) {

        FarmResponseDto farm = farmService.createNewFarm(farmPostDto);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.CREATED,
                "Farm created successfully.",
                farm
        );
    }

    @PatchMapping(value = "/{publicFarmId}")
    public ResponseEntity<?> updateFarmPatch(
            @PathVariable UUID publicFarmId,
            @Valid @RequestBody FarmPatchDto farmDto
    ) {
        FarmResponseDto farm = farmService.updateFarmByPublicIdPatch(
                publicFarmId, farmDto
        );

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Farm updated successfully.",
                farm
        );
    }

    @DeleteMapping(value = "/{publicFarmId}")
    public ResponseEntity<?> deleteFarm(@PathVariable UUID publicFarmId) {

        farmService.deleteFarmById(publicFarmId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Farm deleted successfully.",
                Map.of("publicId", publicFarmId)
        );
    }
}
