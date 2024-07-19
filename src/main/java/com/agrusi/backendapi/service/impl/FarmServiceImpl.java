package com.agrusi.backendapi.service.impl;

import com.agrusi.backendapi.dto.request.farm.FarmPatchDto;
import com.agrusi.backendapi.dto.request.farm.FarmPostDto;
import com.agrusi.backendapi.dto.response.farm.FarmResponseDto;
import com.agrusi.backendapi.exception.farm.FarmNotFoundException;
import com.agrusi.backendapi.mapper.FarmMapper;
import com.agrusi.backendapi.model.Farm;
import com.agrusi.backendapi.repository.FarmRepository;
import com.agrusi.backendapi.service.FarmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    public FarmServiceImpl(FarmRepository farmRepository, FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.farmMapper = farmMapper;
    }

    @Override
    public FarmResponseDto getFarmByPublicId(UUID publicFarmId) {

        Farm farm = farmRepository.findByPublicId(publicFarmId)
                .orElseThrow(() -> new FarmNotFoundException(publicFarmId));

        return farmMapper.toCreateFarmResponseDto(farm);
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
    public FarmResponseDto updateFarmByPublicIdPatch(UUID publicFarmId, FarmPatchDto updateDto) {

        Farm farm = farmRepository.findByPublicId(publicFarmId)
                .orElseThrow(() -> new FarmNotFoundException(publicFarmId));

        if (updateDto.getFarmName() != null) {
            farm.setName(updateDto.getFarmName());
        }

        farmRepository.save(farm);

        return farmMapper.toCreateFarmResponseDto(farm);
    }

    @Override
    @Transactional
    public void deleteFarmById(UUID publicFarmId) {

        Farm farm = farmRepository.findByPublicId(publicFarmId)
                .orElseThrow(() -> new FarmNotFoundException(publicFarmId));

        farmRepository.delete(farm);
    }
}
