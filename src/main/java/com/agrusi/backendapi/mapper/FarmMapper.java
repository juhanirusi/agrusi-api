package com.agrusi.backendapi.mapper;

import com.agrusi.backendapi.dto.response.farm.FarmResponseDto;
import com.agrusi.backendapi.model.Farm;
import org.mapstruct.Mapper;

/*
 * componentModel = "spring" --> The generated mapper is a Spring bean
 * and can be retrieved via @Autowired
*/

@Mapper(componentModel = "spring")
public interface FarmMapper {

    FarmResponseDto toCreateFarmResponseDto(Farm farm);
}
