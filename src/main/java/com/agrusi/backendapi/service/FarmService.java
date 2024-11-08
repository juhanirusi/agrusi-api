package com.agrusi.backendapi.service;

import com.agrusi.backendapi.dto.request.farm.FarmPostDto;
import com.agrusi.backendapi.dto.request.farm.FarmPatchDto;
import com.agrusi.backendapi.dto.response.farm.FarmResponseDto;
import com.agrusi.backendapi.dto.response.farm.TotalFarmLandAreaResponseDto;

import java.util.UUID;

public interface FarmService {

    FarmResponseDto getFarmByPublicId(UUID farmPublicId);

    TotalFarmLandAreaResponseDto getTotalLandAreaByFarmPublicId(UUID farmPublicId);

    FarmResponseDto createNewFarm(FarmPostDto farmPostDto);

    FarmResponseDto updateFarmByPublicIdPatch(
            UUID farmPublicId, FarmPatchDto updateDto
    );

    void deleteFarmById(UUID farmPublicId);
}
