package com.agrusi.backendapi.service;

import com.agrusi.backendapi.dto.request.LoginDto;
import com.agrusi.backendapi.dto.request.RegisterDto;
import com.agrusi.backendapi.dto.response.LoginResponseDto;
import com.agrusi.backendapi.dto.response.RegisterAccountResponseDto;

public interface AuthService {

    RegisterAccountResponseDto registerNewAccount(RegisterDto registerDto);

    LoginResponseDto login(LoginDto loginDto);
}
