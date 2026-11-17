package com.klu.tourist.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klu.tourist.dto.ApiResponse;
import com.klu.tourist.dto.HotelCheckInRequest;
import com.klu.tourist.entity.Tourist;
import com.klu.tourist.service.TouristService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tourists")
@Tag(name = "2. Tourist Records & Hotel Check-In API", description = "Protected endpoints for managing tourist records and processing hotel check-in updates. Requires Bearer Token validation.")
@SecurityRequirement(name = "BearerAuth")
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    /**
     * Core Exam API: Update tourist visited destination and budget during hotel check-in
     */
    @PutMapping("/{id}/checkin")
    @Operation(
        summary = "Update Tourist Destination & Budget (Hotel Check-In System)",
        description = "Core Lab Exam API: Validates the JWT Bearer token in the request header, updates the tourist's visited destination, budget, hotel name, and check-in status in the database."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tourist record successfully updated",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input or validation error"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid Bearer token"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Tourist record not found")
    })
    public ResponseEntity<ApiResponse> updateHotelCheckIn(
            @Parameter(description = "Tourist ID to update", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody HotelCheckInRequest request) {

        Tourist updatedTourist = touristService.updateHotelCheckIn(id, request);
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Tourist visited destination and budget successfully updated in Hotel Check-In system!",
                        updatedTourist
                )
        );
    }

    /**
     * Standard REST Update endpoint alias
     */
    @PutMapping("/{id}")
    @Operation(
        summary = "Update Tourist Record (General Update)",
        description = "Standard REST update endpoint. Validates token and updates visited destination and budget."
    )
    public ResponseEntity<ApiResponse> updateTourist(
            @PathVariable Long id,
            @Valid @RequestBody HotelCheckInRequest request) {
        return updateHotelCheckIn(id, request);
    }

    /**
     * Get tourist details by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Tourist by ID", description = "Retrieves tourist details including visited destination and budget. Requires Bearer token.")
    public ResponseEntity<ApiResponse> getTouristById(@PathVariable Long id) {
        Tourist tourist = touristService.getTouristById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Tourist retrieved successfully", tourist));
    }

    /**
     * Get all tourist records
     */
    @GetMapping
    @Operation(summary = "Get All Tourist Records", description = "Retrieves all tourist records stored in the database. Requires Bearer token.")
    public ResponseEntity<ApiResponse> getAllTourists() {
        List<Tourist> list = touristService.getAllTourists();
        return ResponseEntity.ok(new ApiResponse(true, "All tourists retrieved successfully", list));
    }

    /**
     * Add new tourist record
     */
    @PostMapping
    @Operation(summary = "Add New Tourist Record", description = "Registers a new tourist with initial destination and budget. Requires Bearer token.")
    public ResponseEntity<ApiResponse> createTourist(@Valid @RequestBody Tourist tourist) {
        Tourist saved = touristService.createTourist(tourist);
        return new ResponseEntity<>(
                new ApiResponse(true, "New tourist record created successfully", saved),
                HttpStatus.CREATED
        );
    }

    /**
     * Delete tourist record
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Tourist Record", description = "Removes a tourist record by ID. Requires Bearer token.")
    public ResponseEntity<ApiResponse> deleteTourist(@PathVariable Long id) {
        touristService.deleteTourist(id);
        return ResponseEntity.ok(new ApiResponse(true, "Tourist record deleted successfully with ID: " + id, null));
    }
}
