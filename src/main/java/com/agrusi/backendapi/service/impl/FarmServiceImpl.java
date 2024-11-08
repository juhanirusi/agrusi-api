package com.agrusi.backendapi.service.impl;

import com.agrusi.backendapi.dto.request.farm.FarmPatchDto;
import com.agrusi.backendapi.dto.request.farm.FarmPostDto;
import com.agrusi.backendapi.dto.response.farm.FarmResponseDto;
import com.agrusi.backendapi.dto.response.farm.TotalFarmLandAreaResponseDto;
import com.agrusi.backendapi.exception.farm.FarmNotFoundException;
import com.agrusi.backendapi.mapper.FarmMapper;
import com.agrusi.backendapi.model.AccountPreferences;
import com.agrusi.backendapi.model.Farm;
import com.agrusi.backendapi.repository.FarmRepository;
import com.agrusi.backendapi.service.FarmService;
import com.agrusi.backendapi.service.conversion.UnitConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class FarmServiceImpl implements FarmService {

    private final AccountPreferencesServiceImpl accountPreferencesService;

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    private final UnitConversionService unitConversionService;

    public FarmServiceImpl(
            AccountPreferencesServiceImpl accountPreferencesService,
            FarmRepository farmRepository,
            FarmMapper farmMapper,
            UnitConversionService unitConversionService
    ) {
        this.accountPreferencesService = accountPreferencesService;
        this.farmRepository = farmRepository;
        this.farmMapper = farmMapper;
        this.unitConversionService = unitConversionService;
    }

    @Override
    public FarmResponseDto getFarmByPublicId(UUID farmPublicId) {

        Farm farm = farmRepository.findByPublicId(farmPublicId)
                .orElseThrow(() -> new FarmNotFoundException(farmPublicId));

        return farmMapper.toCreateFarmResponseDto(farm);
    }

    @Override
    public TotalFarmLandAreaResponseDto getTotalLandAreaByFarmPublicId(UUID farmPublicId) {

        Farm farm = farmRepository.findByPublicId(farmPublicId)
                .orElseThrow(() -> new FarmNotFoundException(farmPublicId));

        BigDecimal totalLandArea = farmRepository.calculateTotalLandAreaOfAllFields(farm.getId());

        AccountPreferences accountPreferences = accountPreferencesService.getCurrentAuthenticatedUserPreferences();

        return farmMapper.toFarmTotalLandAreaDto(
                farm, totalLandArea,
                accountPreferences.getFieldAreaUnit(), unitConversionService
        );
    }

    @Override
    @Transactional
    public FarmResponseDto createNewFarm(FarmPostDto farmPostDto) {

        Farm farm = new Farm();

        farm.setName(farmPostDto.getFarmName());

        farmRepository.save(farm);

        return farmMapper.toCreateFarmResponseDto(farm);
    }

    @Override
    @Transactional
    public FarmResponseDto updateFarmByPublicIdPatch(UUID farmPublicId, FarmPatchDto updateDto) {

        Farm farm = farmRepository.findByPublicId(farmPublicId)
                .orElseThrow(() -> new FarmNotFoundException(farmPublicId));

        // Use Optional to avoid null checks
        Optional.ofNullable(updateDto.getFarmName()).ifPresent(farm::setName);

        farmRepository.save(farm);

        return farmMapper.toCreateFarmResponseDto(farm);
    }

    @Override
    @Transactional
    public void deleteFarmById(UUID farmPublicId) {

        Farm farm = farmRepository.findByPublicId(farmPublicId)
                .orElseThrow(() -> new FarmNotFoundException(farmPublicId));

        farmRepository.delete(farm);
    }
}
