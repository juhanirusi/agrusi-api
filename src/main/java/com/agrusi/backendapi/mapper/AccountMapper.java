package com.agrusi.backendapi.mapper;

import com.agrusi.backendapi.dto.response.RegisterAccountResponseDto;
import com.agrusi.backendapi.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper( AccountMapper.class );

    RegisterAccountResponseDto toRegisterAccountResponseDto(Account account);
}
