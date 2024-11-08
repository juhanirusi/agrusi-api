package com.agrusi.backendapi.dto.response;

import java.math.BigDecimal;

public record SizeMap(
        BigDecimal value,
        String unitOfArea
) { }
