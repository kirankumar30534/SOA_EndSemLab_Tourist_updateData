package com.klu.tourist.service;

import java.util.List;

import com.klu.tourist.dto.HotelCheckInRequest;
import com.klu.tourist.entity.Tourist;

public interface TouristService {

    /**
     * Updates a tourist's visited destination and budget during hotel check-in
     */
    Tourist updateHotelCheckIn(Long id, HotelCheckInRequest request);

    Tourist getTouristById(Long id);

    List<Tourist> getAllTourists();

    Tourist createTourist(Tourist tourist);

    void deleteTourist(Long id);
}
