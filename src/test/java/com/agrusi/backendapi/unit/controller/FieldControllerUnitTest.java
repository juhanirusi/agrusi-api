package com.agrusi.backendapi.unit.controller;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.controller.FieldController;
import com.agrusi.backendapi.dto.request.field.FieldPostDto;
import com.agrusi.backendapi.dto.request.field.FieldPutDto;
import com.agrusi.backendapi.dto.response.field.FieldResponseDto;
import com.agrusi.backendapi.dto.response.field.SizeMap;
import com.agrusi.backendapi.exception.farm.FarmNotFoundException;
import com.agrusi.backendapi.exception.field.FieldNotFoundException;
import com.agrusi.backendapi.service.FieldService;
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
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
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
@WebMvcTest(FieldController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FieldControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FieldService fieldService;

    private UUID publicFarmId;
    private UUID nonExistentPublicFarmId;
    private Long fieldId;
    private Long nonExistentFieldId;

    private FieldResponseDto fieldResponseDto;
    private FieldResponseDto expectedCreateFieldResponseDto;
    private FieldResponseDto expectedUpdateFieldPutResponseDto;

    private FieldPostDto fieldPostDto;
    private FieldPostDto invalidPostDto;
    private FieldPutDto fieldPutDto;

    @BeforeEach
    void setUp() {

        publicFarmId = UUID.randomUUID();
        nonExistentPublicFarmId = UUID.randomUUID();

        fieldId = 1L;
        nonExistentFieldId = 2L;

        fieldResponseDto = new FieldResponseDto(
                "Farm field",
                List.of(
                        List.of(
                                List.of(-104.99404, 39.75621),
                                List.of(-104.99404, 39.75212),
                                List.of(-104.98999, 39.75212),
                                List.of(-104.98999, 39.75621),
                                List.of(-104.99404, 39.75621)
                        )
                ),
                List.of(-104.98999, 39.75621),
                new SizeMap(BigDecimal.valueOf(10.0), "hectare")
        );

        fieldPostDto = new FieldPostDto(
                "New farm field",
                List.of(
                        List.of(
                                List.of(-104.99404, 39.75621),
                                List.of(-104.99404, 39.75212),
                                List.of(-104.98999, 39.75212),
                                List.of(-104.98999, 39.75621),
                                List.of(-104.99404, 39.75621)
                        )
                )
        );

        expectedCreateFieldResponseDto = new FieldResponseDto(
                "New farm field",
                List.of(
                        List.of(
                                List.of(-104.99404, 39.75621),
                                List.of(-104.99404, 39.75212),
                                List.of(-104.98999, 39.75212),
                                List.of(-104.98999, 39.75621),
                                List.of(-104.99404, 39.75621)
                        )
                ),
                List.of(-104.98999, 39.75621),
                new SizeMap(BigDecimal.valueOf(100.0), "hectare")
        );

        fieldPutDto = new FieldPutDto(
                "Updated farm field",
                List.of(
                        List.of(
                                List.of(-121.4963, 37.7771),
                                List.of(-121.4932, 37.7792),
                                List.of(-121.4901, 37.7753),
                                List.of(-121.4925, 37.7734),
                                List.of(-121.4945, 37.7744),
                                List.of(-121.4963, 37.7771)
                        )
                )
        );

        expectedUpdateFieldPutResponseDto = new FieldResponseDto(
                "Updated farm field",
                List.of(
                        List.of(
                                List.of(-121.4963, 37.7771),
                                List.of(-121.4932, 37.7792),
                                List.of(-121.4901, 37.7753),
                                List.of(-121.4925, 37.7734),
                                List.of(-121.4945, 37.7744),
                                List.of(-121.4963, 37.7771)
                        )
                ),
                List.of(-105.98999, 40.75621),
                new SizeMap(BigDecimal.valueOf(1_000.0), "acre")
        );

        invalidPostDto = new FieldPostDto(
                "F",
                List.of(
                        List.of(
                                List.of(-104.99404, 39.75621),
                                List.of(-104.99404, 39.75212),
                                List.of(-104.98999, 39.75212),
                                List.of(-104.98999, 39.75621)
//                                List.of(-104.99404, 39.75621)
                        )
                )
        );
    }

    @Test
    @DisplayName("Get a field by its ID.")
    public void testGetField() throws Exception {

        when(fieldService.getFieldByFieldId(publicFarmId, fieldId)).thenReturn(fieldResponseDto);

        mockMvc.perform(get("/api/v1/farms/{publicFarmId}/fields/{fieldId}", publicFarmId, fieldId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Field details fetched successfully."))
                .andExpect(jsonPath("$.data.name").value("Farm field"))

                // Make sure that the center coordinates are as expected...

                .andExpect(jsonPath("$.data.center[0]").value(-104.98999))
                .andExpect(jsonPath("$.data.center[1]").value(39.75621))

                // Make sure that the coordinates array has a size of 5,
                // containing the coordinates for creating a polygon...

                .andExpect(jsonPath("$.data.coordinates[0]", hasSize(5)))

                // Make sure that the field size info is correct...

                .andExpect(jsonPath("$.data.size.value").value(10.0))
                .andExpect(jsonPath("$.data.size.unitOfArea").value("hectare"));
    }

    @Test
    @DisplayName("Get a field by its ID for farm that doesn't exist.")
    public void testGetField_FarmNotFound() throws Exception {

        when(fieldService.getFieldByFieldId(nonExistentPublicFarmId, fieldId)).thenThrow(
                new FarmNotFoundException(nonExistentPublicFarmId)
        );

        mockMvc.perform(get("/api/v1/farms/{publicFarmId}/fields/{fieldId}", nonExistentPublicFarmId, fieldId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Farm not found."))
                .andExpect(jsonPath("$.errors[0].message").value(
                        "Farm with public ID '" + nonExistentPublicFarmId + "' not found."
                ));
    }

    @Test
    @DisplayName("Get a field by its ID. Field that doesn't exist.")
    public void testGetField_FieldNotFound() throws Exception {

        when(fieldService.getFieldByFieldId(publicFarmId, nonExistentFieldId)).thenThrow(
                new FieldNotFoundException(nonExistentFieldId)
        );

        mockMvc.perform(get("/api/v1/farms/{publicFarmId}/fields/{fieldId}", publicFarmId, nonExistentFieldId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Field not found."))
                .andExpect(jsonPath("$.errors[0].message").value(
                        "Field with the provided field ID '" + nonExistentFieldId + "' not found."
                ));
    }

    @Test
    @DisplayName("Create a new field.")
    void testCreateField() throws Exception {

        when(fieldService.createNewField(any(UUID.class), any(FieldPostDto.class))).thenReturn(
                expectedCreateFieldResponseDto
        );

        mockMvc.perform(post("/api/v1/farms/{publicFarmId}/fields", publicFarmId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fieldPostDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Field created successfully."))
                .andExpect(jsonPath("$.data.name").value("New farm field"))

                // Make sure that the center coordinates are as expected...

                .andExpect(jsonPath("$.data.center[0]").value(-104.98999))
                .andExpect(jsonPath("$.data.center[1]").value(39.75621))

                // Make sure that the coordinates array has a size of 5,
                // containing the coordinates for creating a polygon

                .andExpect(jsonPath("$.data.size.value").value(100.0))
                .andExpect(jsonPath("$.data.size.unitOfArea").value("hectare"));
    }

    @Test
    @DisplayName("Try creating a new farm field with INVALID data.")
    public void testCreateFieldValidationError() throws Exception {

        // No need to use "when", because this test is designed to check the
        // controller's behavior when invalid data is provided.

        mockMvc.perform(post("/api/v1/farms/{publicFarmId}/fields", publicFarmId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPostDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(2)));

        verify(fieldService, never()).createNewField(any(UUID.class), any(FieldPostDto.class));
    }

    @Test
    @DisplayName("Update an existing field.")
    void testUpdateFieldPut() throws Exception {

        when(fieldService.updateFieldByFieldIdPut(any(UUID.class), any(Long.class), any(FieldPutDto.class))).thenReturn(
                expectedUpdateFieldPutResponseDto
        );

        mockMvc.perform(put("/api/v1/farms/{publicFarmId}/fields/{fieldId}", publicFarmId, fieldId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fieldPutDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Field updated successfully."))
                .andExpect(jsonPath("$.data.name").value("Updated farm field"))

                // Make sure that the center coordinates are as expected...

                .andExpect(jsonPath("$.data.center[0]").value(-105.98999))
                .andExpect(jsonPath("$.data.center[1]").value(40.75621))

                // Make sure that the coordinates array has a size of 5,
                // containing the coordinates for creating a polygon...

                .andExpect(jsonPath("$.data.coordinates[0]", hasSize(6)))

                // Make sure that the field size info is correct...

                .andExpect(jsonPath("$.data.size.value").value(1000.0))
                .andExpect(jsonPath("$.data.size.unitOfArea").value("acre"));
    }

    @Test
    @DisplayName("Delete a field by its ID.")
    void testDeleteField() throws Exception {

        doNothing().when(fieldService).deleteFieldById(publicFarmId, fieldId);

        mockMvc.perform(delete("/api/v1/farms/{publicFarmId}/fields/{fieldId}", publicFarmId, fieldId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Field deleted successfully."))
                .andExpect(jsonPath("$.data.fieldId").value(fieldId));
    }
}

