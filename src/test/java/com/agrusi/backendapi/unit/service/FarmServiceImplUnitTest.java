package com.agrusi.backendapi.unit.service;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.dto.request.farm.FarmPatchDto;
import com.agrusi.backendapi.dto.request.farm.FarmPostDto;
import com.agrusi.backendapi.dto.response.farm.FarmResponseDto;
import com.agrusi.backendapi.exception.farm.FarmNotFoundException;
import com.agrusi.backendapi.mapper.FarmMapper;
import com.agrusi.backendapi.model.Farm;
import com.agrusi.backendapi.repository.FarmRepository;
import com.agrusi.backendapi.service.impl.FarmServiceImpl;
import com.agrusi.backendapi.unit.util.ReflectionTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * @UnitTest --> Our custom annotation allowing us to run only
 * unit tests if we want to
*/

@UnitTest
@ExtendWith(MockitoExtension.class)
public class FarmServiceImplUnitTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmMapper farmMapper;

    @InjectMocks
    private FarmServiceImpl farmService;

    private UUID publicId;
    private Farm farm;

    private FarmPostDto farmPostDto;
    private FarmPatchDto farmPatchDto;

    private FarmResponseDto expectedFarmResponseDto;
    private FarmResponseDto expectedFarmPatchUpdateResponseDto;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {

        publicId = UUID.randomUUID();

        ReflectionTestUtils reflectionTestUtils = new ReflectionTestUtils();

        farm = new Farm(
                1L,
                "Jack's Farm",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        reflectionTestUtils.setField(
                farm,
                "publicId",
                publicId
        );

        farmPostDto = new FarmPostDto("Jack's Farm");

        farmPatchDto = new FarmPatchDto(
                "Jane's Farm"
        );

        expectedFarmResponseDto = new FarmResponseDto(
                publicId,
                "Jack's Farm"
        );

        expectedFarmPatchUpdateResponseDto = new FarmResponseDto(
                publicId,
                "Jane's Farm"
        );
    }

    @Test
    @DisplayName("Test creating a new farm.")
    public void testCreateNewFarm() {

        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        when(farmMapper.toCreateFarmResponseDto(any(Farm.class))).thenReturn(expectedFarmResponseDto);

        FarmResponseDto responseDto = farmService.createNewFarm(farmPostDto);

        verify(farmRepository).save(any(Farm.class));
        verify(farmMapper).toCreateFarmResponseDto(any(Farm.class));

        assertNotNull(responseDto);
        assertEquals(expectedFarmResponseDto, responseDto);
    }

    @Test
    @DisplayName("Test getting Farm by public ID SUCCESSFULLY.")
    public void testGetFarmByPublicId() {

        when(farmRepository.findByPublicId(publicId)).thenReturn(Optional.of(farm));
        when(farmMapper.toCreateFarmResponseDto(farm)).thenReturn(expectedFarmResponseDto);

        FarmResponseDto responseDto = farmService.getFarmByPublicId(publicId);

        assertNotNull(responseDto);
        assertEquals(expectedFarmResponseDto, responseDto);
    }

    @Test
    @DisplayName("Test getting Farm by public ID UNSUCCESSFULLY - Farm Not Found")
    public void testGetFarmByPublicIdNotFound() {

        when(farmRepository.findByPublicId(publicId)).thenReturn(Optional.empty());

        assertThrows(FarmNotFoundException.class, () -> {
            farmService.getFarmByPublicId(publicId);
        });
    }

    @Test
    @DisplayName("Test PATCH updating Farm.")
    public void testUpdateFarmByPublicIdPatch() {

        when(farmRepository.findByPublicId(publicId)).thenReturn(Optional.of(farm));
        when(farmMapper.toCreateFarmResponseDto(farm)).thenReturn(expectedFarmPatchUpdateResponseDto);

        FarmResponseDto responseDto = farmService.updateFarmByPublicIdPatch(publicId, farmPatchDto);

        // Check that the response contains correct data...
        assertNotNull(responseDto);
        assertEquals(publicId, responseDto.publicId());
        assertEquals("Jane's Farm", responseDto.name());

        // Check that the new account has been updated...
        assertEquals("Jane's Farm", farm.getName());

        verify(farmRepository).findByPublicId(publicId);
        verify(farmMapper).toCreateFarmResponseDto(farm);
    }

    @Test
    @DisplayName("Test deleting a Farm by public ID.")
    public void testDeleteFarmByPublicId() {

        when(farmRepository.findByPublicId(publicId)).thenReturn(Optional.of(farm));

        farmService.deleteFarmById(publicId);

        verify(farmRepository).delete(farm);
    }
}
