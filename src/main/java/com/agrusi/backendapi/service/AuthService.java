package com.agrusi.backendapi.service;

import com.agrusi.backendapi.dto.request.auth.LoginDto;
import com.agrusi.backendapi.dto.request.auth.RegisterDto;
import com.agrusi.backendapi.dto.response.auth.AccountRegistrationResponseDto;
import com.agrusi.backendapi.dto.response.auth.LoginResponseDto;

public interface AuthService {

    AccountRegistrationResponseDto registerNewAccount(RegisterDto registerDto);

    LoginResponseDto login(LoginDto loginDto);
}
