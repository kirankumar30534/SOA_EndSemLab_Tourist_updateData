package com.klu.tourist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body to update tourist visited destination, budget, and hotel check-in details")
public class HotelCheckInRequest {

    @NotBlank(message = "Visited destination is required")
    @Schema(description = "Updated visited destination", example = "Switzerland")
    private String visitedDestination;

    @NotNull(message = "Budget is required")
    @DecimalMin(value = "0.0", message = "Budget must be non-negative")
    @Schema(description = "Updated remaining or allocated budget", example = "45000.00")
    private Double budget;

    @NotBlank(message = "Hotel name is required")
    @Schema(description = "Hotel where tourist is checking in", example = "The Chedi Andermatt")
    private String hotelName;

    @Schema(description = "Type of room allocated", example = "Executive Suite")
    private String roomType;

    @Schema(description = "Check-in date (YYYY-MM-DD)", example = "2026-11-17")
    private String checkInDate;

    @Schema(description = "Status of check-in", example = "CHECKED_IN")
    private String checkInStatus = "CHECKED_IN";

    public HotelCheckInRequest() {
    }

    public HotelCheckInRequest(String visitedDestination, Double budget, String hotelName,
                               String roomType, String checkInDate, String checkInStatus) {
        this.visitedDestination = visitedDestination;
        this.budget = budget;
        this.hotelName = hotelName;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkInStatus = checkInStatus != null ? checkInStatus : "CHECKED_IN";
    }

    public String getVisitedDestination() {
        return visitedDestination;
    }

    public void setVisitedDestination(String visitedDestination) {
        this.visitedDestination = visitedDestination;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckInStatus() {
        return checkInStatus;
    }

    public void setCheckInStatus(String checkInStatus) {
        this.checkInStatus = checkInStatus;
    }
}
