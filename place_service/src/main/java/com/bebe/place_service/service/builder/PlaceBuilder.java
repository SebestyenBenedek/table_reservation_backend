package com.bebe.place_service.service.builder;

import com.bebe.place_service.dto.NewPlaceDTO;
import com.bebe.place_service.model.Place;
import com.bebe.place_service.model.TimeInterval;
import com.bebe.place_service.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PlaceBuilder {
    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceBuilder(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public Place placeBuilder(NewPlaceDTO newPlaceDTO, Set<TimeInterval> timeIntervals) {
        Place place = Place.builder()
                .name(newPlaceDTO.name())
                .address(newPlaceDTO.address())
                .description(newPlaceDTO.description())
                .menu(newPlaceDTO.menu())
                .characteristics(newPlaceDTO.characteristics())
                .images(newPlaceDTO.images())
                .tables(new HashSet<>())
                .adminUserId(newPlaceDTO.ownerId())
                .timeIntervals(timeIntervals)
                .build();
        placeRepository.save(place);
        return place;
    }
}
