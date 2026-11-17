package com.klu.tourist.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.klu.tourist.dto.HotelCheckInRequest;
import com.klu.tourist.entity.Tourist;
import com.klu.tourist.exception.ResourceNotFoundException;
import com.klu.tourist.repository.TouristRepository;
import com.klu.tourist.service.TouristService;

@Service
public class TouristServiceImpl implements TouristService {

    private final TouristRepository touristRepository;

    public TouristServiceImpl(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    @Override
    public Tourist updateHotelCheckIn(Long id, HotelCheckInRequest request) {
        Tourist tourist = touristRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist record not found with ID: " + id));

        // Update visited destination and budget as required by exam problem statement
        tourist.setVisitedDestination(request.getVisitedDestination());
        tourist.setBudget(request.getBudget());

        // Update hotel check-in details
        if (request.getHotelName() != null && !request.getHotelName().isBlank()) {
            tourist.setHotelName(request.getHotelName());
        }

        if (request.getRoomType() != null && !request.getRoomType().isBlank()) {
            tourist.setRoomType(request.getRoomType());
        }

        if (request.getCheckInStatus() != null && !request.getCheckInStatus().isBlank()) {
            tourist.setCheckInStatus(request.getCheckInStatus());
        } else {
            tourist.setCheckInStatus("CHECKED_IN");
        }

        if (request.getCheckInDate() != null && !request.getCheckInDate().isBlank()) {
            tourist.setCheckInDate(request.getCheckInDate());
        } else if (tourist.getCheckInDate() == null || tourist.getCheckInDate().isBlank()) {
            tourist.setCheckInDate(LocalDate.now().toString());
        }

        // Save updated tourist into the in-memory store
        return touristRepository.save(tourist);
    }

    @Override
    public Tourist getTouristById(Long id) {
        return touristRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist record not found with ID: " + id));
    }

    @Override
    public List<Tourist> getAllTourists() {
        return touristRepository.findAll();
    }

    @Override
    public Tourist createTourist(Tourist tourist) {
        return touristRepository.save(tourist);
    }

    @Override
    public void deleteTourist(Long id) {
        if (!touristRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tourist record not found with ID: " + id);
        }
        touristRepository.deleteById(id);
    }
}
