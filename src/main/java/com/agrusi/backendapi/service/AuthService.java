package com.agrusi.backendapi.service;

import com.agrusi.backendapi.dto.RegisterDto;
import com.agrusi.backendapi.dto.response.RegisterAccountResponseDto;

public interface AuthService {

    RegisterAccountResponseDto register(RegisterDto registerDto);
}
