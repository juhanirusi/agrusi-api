package com.agrusi.backendapi.controller;

import com.agrusi.backendapi.dto.request.farm.FarmPatchDto;
import com.agrusi.backendapi.dto.request.farm.FarmPostDto;
import com.agrusi.backendapi.dto.response.farm.FarmResponseDto;
import com.agrusi.backendapi.dto.response.farm.TotalFarmLandAreaResponseDto;
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

    @GetMapping(value = "/{farmPublicId}")
    public ResponseEntity<?> getFarm(@PathVariable UUID farmPublicId) {

        FarmResponseDto farm = farmService.getFarmByPublicId(farmPublicId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Farm details fetched successfully.",
                farm
        );
    }

    @GetMapping(value = "/{farmPublicId}/total-land-area")
    public ResponseEntity<?> getTotalFarmLandArea(@PathVariable UUID farmPublicId) {

        TotalFarmLandAreaResponseDto farmLandAreaInfo =
                farmService.getTotalLandAreaByFarmPublicId(farmPublicId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Farm's total land area details fetched successfully.",
                farmLandAreaInfo
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

    @PatchMapping(value = "/{farmPublicId}")
    public ResponseEntity<?> updateFarmPatch(
            @PathVariable UUID farmPublicId,
            @Valid @RequestBody FarmPatchDto farmDto
    ) {
        FarmResponseDto farm = farmService.updateFarmByPublicIdPatch(
                farmPublicId, farmDto
        );

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Farm updated successfully.",
                farm
        );
    }

    @DeleteMapping(value = "/{farmPublicId}")
    public ResponseEntity<?> deleteFarm(@PathVariable UUID farmPublicId) {

        farmService.deleteFarmById(farmPublicId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Farm deleted successfully.",
                Map.of("publicId", farmPublicId)
        );
    }
}
