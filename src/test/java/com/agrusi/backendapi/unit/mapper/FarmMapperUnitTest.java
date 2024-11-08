package com.agrusi.backendapi.unit.mapper;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.dto.response.SizeMap;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.mapper.FarmMapper;
import com.agrusi.backendapi.service.conversion.UnitConversionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@UnitTest
public class FarmMapperUnitTest {

    private final FarmMapper farmMapper = Mappers.getMapper(FarmMapper.class);

    private final UnitConversionService unitConversionService = new UnitConversionService();

    private final BigDecimal valueToConvert = BigDecimal.valueOf(1523341.93);

    // Test our area size converters for all field sizes...

    static Stream<Arguments> conversionData() {

        return Stream.of(
                arguments(BigDecimal.valueOf(152.33), EAreaUnit.HECTARE, "hectare"),
                arguments(BigDecimal.valueOf(1523341.93), EAreaUnit.SQUARE_METRE, "square metre")
        );
    }

    @ParameterizedTest
    @MethodSource("conversionData")
    @DisplayName("Test area size conversion to all area sizes.")
    public void testConvertAreaSize(
            BigDecimal expectedValue, EAreaUnit unitOfArea, String unitOfAreaAsString
    ) {
        SizeMap sizeMap = farmMapper.convertSize(
                valueToConvert,
                unitOfArea,
                unitConversionService
        );

        // Assert that the returned unit matches the expected enum value
        assertEquals(unitOfAreaAsString, sizeMap.unitOfArea());

        // Assert that the converted value matches the expected value
        assertEquals(
                expectedValue.setScale(2, RoundingMode.HALF_UP),
                sizeMap.value().setScale(2, RoundingMode.HALF_UP)
        );
    }
}
