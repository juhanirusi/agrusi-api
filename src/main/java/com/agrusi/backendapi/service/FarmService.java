package com.agrusi.backendapi.service;

import com.agrusi.backendapi.dto.request.farm.FarmPostDto;
import com.agrusi.backendapi.dto.request.farm.FarmPatchDto;
import com.agrusi.backendapi.dto.response.farm.FarmResponseDto;

import java.util.UUID;

public interface FarmService {

    FarmResponseDto getFarmByPublicId(UUID publicFarmId);

    FarmResponseDto createNewFarm(FarmPostDto farmPostDto);

    FarmResponseDto updateFarmByPublicIdPatch(
            UUID publicFarmId, FarmPatchDto updateDto
    );

    void deleteFarmById(UUID publicFarmId);
}
