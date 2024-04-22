package com.bebe.place_service.service.builder;

import com.bebe.place_service.dto.NewPlaceDTO;
import com.bebe.place_service.model.Place;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class PlaceBuilder {
    private final Tracer tracer;

    public PlaceBuilder(Tracer tracer) {
        this.tracer = tracer;
    }

    public Place placeBuilder(NewPlaceDTO newPlaceDTO) {
        Span span = tracer.buildSpan("placeBuilder").start();
        try {
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
        } finally {
            span.finish();
        }
    }
}
