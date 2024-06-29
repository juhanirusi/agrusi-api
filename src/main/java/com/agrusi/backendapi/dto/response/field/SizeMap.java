package com.agrusi.backendapi.dto.response.field;

import java.math.BigDecimal;

public record SizeMap(
        BigDecimal value,
        String unitOfArea
) { }
