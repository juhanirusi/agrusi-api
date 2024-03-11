package com.agrusi.backendapi.service;

import com.agrusi.backendapi.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);
}
