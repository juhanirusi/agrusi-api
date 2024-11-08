package com.agrusi.backendapi.service.conversion;

import com.agrusi.backendapi.enums.EAreaUnit;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class UnitConversionService {

    public BigDecimal convertFieldAreaSize(BigDecimal sizeInSquareMeters, EAreaUnit targetUnit) {

        return switch (targetUnit) {
            case HECTARE -> sizeInSquareMeters.divide(BigDecimal.valueOf(10_000), 2, RoundingMode.HALF_UP);
            case SQUARE_METRE -> sizeInSquareMeters;
        };
    }
}
