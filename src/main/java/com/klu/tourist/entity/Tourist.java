package com.klu.tourist.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Tourist Model representing a tourist record in the Hotel Check-In system")
public class Tourist {

    @Schema(description = "Unique Identifier of the Tourist", example = "1")
    private Long id;

    @NotBlank(message = "Tourist name is mandatory")
    @Schema(description = "Full name of the tourist", example = "Rahul Sharma")
    private String touristName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email must be valid")
    @Schema(description = "Email address", example = "rahul.sharma@example.com")
    private String email;

    @Schema(description = "Passport or Govt ID Number", example = "IND-987654")
    private String passportNumber;

    @NotBlank(message = "Visited destination is mandatory")
    @Schema(description = "Current or visited destination", example = "Goa")
    private String visitedDestination;

    @NotNull(message = "Budget is mandatory")
    @DecimalMin(value = "0.0", message = "Budget must be greater than or equal to 0")
    @Schema(description = "Tourist travel budget (in INR or USD)", example = "25000.00")
    private Double budget;

    @Schema(description = "Name of the hotel for check-in", example = "Taj Exotica Resort")
    private String hotelName;

    @Schema(description = "Hotel Check-in status", example = "CHECKED_IN")
    private String checkInStatus;

    @Schema(description = "Room type allocated", example = "Deluxe Ocean View")
    private String roomType;

    @Schema(description = "Date of check-in", example = "2026-11-17")
    private String checkInDate;

    // Default Constructor
    public Tourist() {
    }

    // Constructor with all fields (including ID for preloading)
    public Tourist(Long id, String touristName, String email, String passportNumber,
                   String visitedDestination, Double budget, String hotelName,
                   String checkInStatus, String roomType, String checkInDate) {
        this.id = id;
        this.touristName = touristName;
        this.email = email;
        this.passportNumber = passportNumber;
        this.visitedDestination = visitedDestination;
        this.budget = budget;
        this.hotelName = hotelName;
        this.checkInStatus = checkInStatus;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
    }

    // Constructor without ID
    public Tourist(String touristName, String email, String passportNumber,
                   String visitedDestination, Double budget, String hotelName,
                   String checkInStatus, String roomType, String checkInDate) {
        this(null, touristName, email, passportNumber, visitedDestination, budget, hotelName, checkInStatus, roomType, checkInDate);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTouristName() {
        return touristName;
    }

    public void setTouristName(String touristName) {
        this.touristName = touristName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
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

    public String getCheckInStatus() {
        return checkInStatus;
    }

    public void setCheckInStatus(String checkInStatus) {
        this.checkInStatus = checkInStatus;
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
}
