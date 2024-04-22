package com.bebe.place_service.controller;

import com.bebe.place_service.dto.NewPlaceDTO;
import com.bebe.place_service.dto.PlaceDTO;
import com.bebe.place_service.model.Place;
import com.bebe.place_service.service.PlaceService;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/places")
public class PlaceController {
    private final PlaceService placeService;
    private final Tracer tracer;

    @Autowired
    public PlaceController(PlaceService placeService, Tracer tracer) {
        this.placeService = placeService;
        this.tracer = tracer;
    }

    @GetMapping("/")
    public Set<Place> fetchPlaceList() {
        Span span = tracer.buildSpan("fetchPlaceList").start();
        try {
            return placeService.getAllPlaces();
        } finally {
            span.finish();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Place> fetchPlaceById(@PathVariable("id") Long placeId) {
        Span span = tracer.buildSpan("fetchPlaceById").start();
        try {
            return ResponseEntity.ok(placeService.getPlaceById(placeId));
        } finally {
            span.finish();
        }
    }

    @PostMapping("/")
    public Place savePlace(@RequestBody NewPlaceDTO place) {
        Span span = tracer.buildSpan("savePlace").start();
        try {
            return placeService.addPlace(place);
        } finally {
            span.finish();
        }
    }

    @PutMapping("/{id}")
    public Place updatePlace(@RequestBody PlaceDTO placeDTO, @PathVariable("id") Long placeId) {
        Span span = tracer.buildSpan("updatePlace").start();
        try {
            return placeService.updatePlace(placeDTO, placeId);
        } finally {
            span.finish();
        }
    }

    @DeleteMapping("/{id}")
    public String deletePlaceById(@PathVariable("id") Long placeId) {
        Span span = tracer.buildSpan("deletePlaceById").start();
        try {
            placeService.deletePlaceById(placeId);
            return "Deleted successfully";
        } finally {
            span.finish();
        }
    }

    @PutMapping("/{id}/reserve")
    public void updateReservedTimeIntervals(@PathVariable("id") Long placeId,
                                            @RequestParam Long tableId,
                                            @RequestBody Set<Long> reservedTimeIntervalIds) {
        Span span = tracer.buildSpan("updateReservedTimeIntervals").start();
        try {
            placeService.reserveTimeIntervals(placeId, tableId, reservedTimeIntervalIds);
        } finally {
            span.finish();
        }
    }
}
