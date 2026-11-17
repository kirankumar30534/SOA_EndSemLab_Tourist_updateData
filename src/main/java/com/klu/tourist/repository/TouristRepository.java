package com.klu.tourist.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.klu.tourist.entity.Tourist;

import jakarta.annotation.PostConstruct;

/**
 * In-Memory Preloaded Tourist Repository.
 * Requires ZERO database setup. All records are stored in memory and immediately
 * interactive via Swagger UI.
 */
@Repository
public class TouristRepository {

    private final Map<Long, Tourist> touristStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    /**
     * Preload initial tourist records into memory at startup
     */
    @PostConstruct
    public void initPreloadedData() {
        saveInitial(new Tourist(
                1L,
                "Rahul Sharma",
                "rahul.sharma@example.com",
                "IND-987654",
                "Mumbai",
                20000.0,
                "Hotel Sea Breeze",
                "NOT_CHECKED_IN",
                "Standard Room",
                null
        ));

        saveInitial(new Tourist(
                2L,
                "Emily Watson",
                "emily.watson@example.com",
                "UK-123456",
                "London",
                50000.0,
                "Grand Palace Hotel",
                "NOT_CHECKED_IN",
                "Deluxe Suite",
                null
        ));

        saveInitial(new Tourist(
                3L,
                "Kenji Sato",
                "kenji.sato@example.com",
                "JPN-556677",
                "Tokyo",
                35000.0,
                "City View Inn",
                "NOT_CHECKED_IN",
                "Single Room",
                null
        ));

        saveInitial(new Tourist(
                4L,
                "Sophia Miller",
                "sophia.miller@example.com",
                "USA-998877",
                "New York",
                40000.0,
                "Manhattan Lodge",
                "NOT_CHECKED_IN",
                "Executive Suite",
                null
        ));

        System.out.println(">>> Preloaded " + touristStore.size() + " tourist records into In-Memory store.");
    }

    private void saveInitial(Tourist tourist) {
        touristStore.put(tourist.getId(), tourist);
        idGenerator.set(Math.max(idGenerator.get(), tourist.getId()));
    }

    public Optional<Tourist> findById(Long id) {
        return Optional.ofNullable(touristStore.get(id));
    }

    public List<Tourist> findAll() {
        List<Tourist> list = new ArrayList<>(touristStore.values());
        Collections.sort(list, (a, b) -> Long.compare(a.getId(), b.getId()));
        return list;
    }

    public Tourist save(Tourist tourist) {
        if (tourist.getId() == null) {
            tourist.setId(idGenerator.incrementAndGet());
        }
        touristStore.put(tourist.getId(), tourist);
        return tourist;
    }

    public boolean existsById(Long id) {
        return touristStore.containsKey(id);
    }

    public void deleteById(Long id) {
        touristStore.remove(id);
    }

    public long count() {
        return touristStore.size();
    }
}
