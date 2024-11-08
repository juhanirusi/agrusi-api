package com.agrusi.backendapi.mapper;

import com.agrusi.backendapi.dto.response.farm.FarmResponseDto;
import com.agrusi.backendapi.dto.response.SizeMap;
import com.agrusi.backendapi.dto.response.farm.TotalFarmLandAreaResponseDto;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.model.Farm;
import com.agrusi.backendapi.service.conversion.UnitConversionService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

/*
 * componentModel = "spring" --> The generated mapper is a Spring bean
 * and can be retrieved via @Autowired
*/

@Mapper(componentModel = "spring")
public interface FarmMapper {

    FarmResponseDto toCreateFarmResponseDto(Farm farm);

    @Mapping(target = "size", expression = "java(convertSize(totalLandArea, areaUnit, unitConversionService))")
    TotalFarmLandAreaResponseDto toFarmTotalLandAreaDto(
            Farm farm,
            BigDecimal totalLandArea,
            @Context EAreaUnit areaUnit,
            @Context UnitConversionService unitConversionService
    );

    default SizeMap convertSize(
            BigDecimal size,
            @Context EAreaUnit areaUnit,
            @Context UnitConversionService unitConversionService
    ) {
        BigDecimal convertedSize =
                unitConversionService.convertFieldAreaSize(size, areaUnit);

        return new SizeMap(convertedSize, areaUnit.getUnitOfArea());
    }
}
