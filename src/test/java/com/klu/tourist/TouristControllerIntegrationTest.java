package com.klu.tourist;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klu.tourist.dto.HotelCheckInRequest;
import com.klu.tourist.dto.LoginRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class TouristControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Helper to obtain a valid JWT token
     */
    private String obtainAccessToken(String username, String password) throws Exception {
        LoginRequest loginRequest = new LoginRequest(username, password);

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("token").asText();
    }

    @Test
    @DisplayName("1. Update API without Token should return 401 Unauthorized")
    void testUpdateWithoutToken_Returns401() throws Exception {
        HotelCheckInRequest updateRequest = new HotelCheckInRequest(
                "Paris",
                45000.0,
                "Hotel Le Bristol",
                "Deluxe",
                "2026-11-17",
                "CHECKED_IN"
        );

        mockMvc.perform(put("/api/tourists/1/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("2. Update API with Valid JWT Token should succeed with 200 OK")
    void testUpdateWithValidToken_Returns200() throws Exception {
        // Step 1: Obtain token
        String token = obtainAccessToken("admin", "admin123");

        // Step 2: Perform update
        HotelCheckInRequest updateRequest = new HotelCheckInRequest(
                "Switzerland",
                60000.0,
                "The Chedi Andermatt",
                "Executive Suite",
                "2026-11-17",
                "CHECKED_IN"
        );

        mockMvc.perform(put("/api/tourists/1/checkin")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.visitedDestination").value("Switzerland"))
                .andExpect(jsonPath("$.data.budget").value(60000.0))
                .andExpect(jsonPath("$.data.hotelName").value("The Chedi Andermatt"))
                .andExpect(jsonPath("$.data.checkInStatus").value("CHECKED_IN"));
    }

    @Test
    @DisplayName("3. Verify Tourist Record reflects updated destination and budget via GET API")
    void testGetUpdatedTourist_ReturnsUpdatedValues() throws Exception {
        String token = obtainAccessToken("admin", "admin123");

        mockMvc.perform(get("/api/tourists/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    @DisplayName("4. Update API with Non-existent ID should return 404 Not Found")
    void testUpdateNonExistentTourist_Returns404() throws Exception {
        String token = obtainAccessToken("admin", "admin123");

        HotelCheckInRequest updateRequest = new HotelCheckInRequest(
                "Tokyo",
                30000.0,
                "Tokyo Grand Hotel",
                "Deluxe",
                "2026-11-17",
                "CHECKED_IN"
        );

        mockMvc.perform(put("/api/tourists/9999/checkin")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("5. Update API with Invalid Budget (< 0) should return 400 Bad Request")
    void testUpdateWithNegativeBudget_Returns400() throws Exception {
        String token = obtainAccessToken("admin", "admin123");

        HotelCheckInRequest updateRequest = new HotelCheckInRequest(
                "Goa",
                -500.0, // Invalid budget
                "Taj Resort",
                "Deluxe",
                "2026-11-17",
                "CHECKED_IN"
        );

        mockMvc.perform(put("/api/tourists/1/checkin")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}
