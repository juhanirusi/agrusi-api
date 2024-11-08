package com.agrusi.backendapi.unit.controller;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.controller.FarmController;
import com.agrusi.backendapi.dto.request.farm.FarmPatchDto;
import com.agrusi.backendapi.dto.request.farm.FarmPostDto;
import com.agrusi.backendapi.dto.response.SizeMap;
import com.agrusi.backendapi.dto.response.farm.FarmResponseDto;
import com.agrusi.backendapi.dto.response.farm.TotalFarmLandAreaResponseDto;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.exception.farm.FarmNotFoundException;
import com.agrusi.backendapi.service.FarmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @UnitTest --> Our custom annotation allowing us to run only
 * unit tests if we want to.
 *
 * @AutoConfigureMockMvc(addFilters = false) --> Disable security
 * filters for these tests, and test Spring Security in separate
 * tests, this class is for testing controller logic only.
*/

@UnitTest
@WebMvcTest(FarmController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FarmControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FarmService farmService;

    private UUID publicId;
    private UUID nonExistentPublicId;

    private FarmResponseDto expectedFarmResponseDto;
    private FarmResponseDto expectedUpdateFarmResponseDto;

    private FarmPostDto farmPostDto;
    private FarmPostDto invalidPostDto;

    private FarmPatchDto updateFarmPatchDto;

    @BeforeEach
    public void setUp() {

        publicId = UUID.randomUUID();
        nonExistentPublicId = UUID.randomUUID();

        farmPostDto = new FarmPostDto(
                "Jack's Farm"
        );

        invalidPostDto = new FarmPostDto("J");

        expectedFarmResponseDto = new FarmResponseDto(
                publicId,
                "Jack's Farm"
        );

        updateFarmPatchDto = new FarmPatchDto(
                "Jane's Ranch"
        );

        expectedUpdateFarmResponseDto = new FarmResponseDto(
                publicId,
                "Jane's Ranch"
        );
    }

    @Test
    @DisplayName("Get a farm.")
    public void testGetFarm() throws Exception {

        when(farmService.getFarmByPublicId(publicId)).thenReturn(
                expectedFarmResponseDto
        );

        mockMvc.perform(get("/api/v1/farms/{publicFarmId}", publicId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Farm details fetched successfully."))
                .andExpect(jsonPath("$.data.publicId").value(publicId.toString()))
                .andExpect(jsonPath("$.data.name").value("Jack's Farm"));
    }

    @Test
    @DisplayName("Get a farm that doesn't exist.")
    public void testGetFarm_NotFound() throws Exception {

        when(farmService.getFarmByPublicId(nonExistentPublicId)).thenThrow(
                new FarmNotFoundException(nonExistentPublicId)
        );

        mockMvc.perform(get("/api/v1/farms/{publicFarmId}", nonExistentPublicId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Farm not found."))
                .andExpect(jsonPath("$.errors[0].message").value(
                        "Farm with public ID '" + nonExistentPublicId + "' not found."
                ));
    }
    
    @Test
    @DisplayName("Get a farm's total land area.")
    public void testGetFarmTotalLandArea() throws Exception {

        TotalFarmLandAreaResponseDto expectedResponse = new TotalFarmLandAreaResponseDto(
                publicId,
                "Jack's Farm",
                new SizeMap(
                        BigDecimal.valueOf(10.00),
                        EAreaUnit.HECTARE.getUnitOfArea()
                )
        );

        when(farmService.getTotalLandAreaByFarmPublicId(publicId)).thenReturn(
                expectedResponse
        );

        mockMvc.perform(get("/api/v1/farms/{publicFarmId}/total-land-area", publicId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Farm's total land area details fetched successfully."))
                .andExpect(jsonPath("$.data.publicId").value(publicId.toString()))
                .andExpect(jsonPath("$.data.name").value("Jack's Farm"))
                .andExpect(jsonPath("$.data.size.value").value(10.0))
                .andExpect(jsonPath("$.data.size.unitOfArea").value("hectare"));
    }

    @Test
    @DisplayName("Create a new farm.")
    public void testCreateNewFarm() throws Exception {

        when(farmService.createNewFarm(any(FarmPostDto.class))).thenReturn(
                expectedFarmResponseDto
        );

        mockMvc.perform(post("/api/v1/farms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(farmPostDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Farm created successfully."))
                .andExpect(jsonPath("$.data.publicId").value(publicId.toString()))
                .andExpect(jsonPath("$.data.name").value("Jack's Farm"));
    }

    @Test
    @DisplayName("Try creating a new farm with INVALID data.")
    public void testCreateFarmValidationError() throws Exception {

        // No need to use "when", because this test is designed to check the
        // controller's behavior when invalid data is provided.

        mockMvc.perform(post("/api/v1/farms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPostDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").value("farmName"))
                .andExpect(jsonPath("$.errors[0].reason").value(
                        "Farm name is required and needs to be between 2 and 255 characters long.")
                );

        verify(farmService, never()).createNewFarm(any(FarmPostDto.class));
    }

    @Test
    @DisplayName("Update (PATCH) a farm.")
    public void testUpdateFarmPatch() throws Exception {

        when(farmService.updateFarmByPublicIdPatch(
                any(UUID.class), any(FarmPatchDto.class)
        )).thenReturn(expectedUpdateFarmResponseDto);

        mockMvc.perform(patch("/api/v1/farms/{publicFarmId}", publicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateFarmPatchDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Farm updated successfully."))
                .andExpect(jsonPath("$.data.publicId").value(publicId.toString()))
                .andExpect(jsonPath("$.data.name").value("Jane's Ranch"));
    }

    @Test
    @DisplayName("Delete a farm.")
    public void testDeleteFarm() throws Exception {

        doNothing().when(farmService).deleteFarmById(publicId);

        mockMvc.perform(delete("/api/v1/farms/{publicFarmId}", publicId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Farm deleted successfully."))
                .andExpect(jsonPath("$.data.publicId").value(publicId.toString()));
    }
}
