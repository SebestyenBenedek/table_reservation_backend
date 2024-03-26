package com.bebe.place_service.service.builder;

import com.bebe.place_service.dto.NewPlaceDTO;
import com.bebe.place_service.model.Place;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class PlaceBuilder {
    public Place placeBuilder(NewPlaceDTO newPlaceDTO) {
        System.out.println(newPlaceDTO);
        return Place.builder()
                .name(newPlaceDTO.name())
                .address(newPlaceDTO.address())
                .description(newPlaceDTO.description())
                .menu(newPlaceDTO.menu())
                .characteristics(newPlaceDTO.characteristics())
                .images(newPlaceDTO.images())
                .tables(new HashSet<>())
                .adminUserId(newPlaceDTO.ownerId())
                .build();
    }
}
