package com.bebe.place_service.repository;

import com.bebe.place_service.model.Place;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    @NonNull Optional<Place> findById(@NonNull Long placeId);
}