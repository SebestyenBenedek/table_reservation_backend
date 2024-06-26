package com.bebe.place_service.controller;

import com.bebe.place_service.dto.NewPlaceDTO;
import com.bebe.place_service.dto.PlaceDTO;
import com.bebe.place_service.model.Place;
import com.bebe.place_service.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/places")
public class PlaceController {
    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/")
    public Set<Place> fetchPlaceList() {
            return placeService.getAllPlaces();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Place> fetchPlaceById(@PathVariable("id") Long placeId) {
            return ResponseEntity.ok(placeService.getPlaceById(placeId));
    }

    @PostMapping("/")
    public Place savePlace(@RequestBody NewPlaceDTO place) {
            return placeService.addPlace(place);
    }

    @PutMapping("/{id}")
    public Place updatePlace(@RequestBody PlaceDTO placeDTO, @PathVariable("id") Long placeId) {
            return placeService.updatePlace(placeDTO, placeId);
    }

    @DeleteMapping("/{id}")
    public String deletePlaceById(@PathVariable("id") Long placeId) {
            placeService.deletePlaceById(placeId);
            return "Deleted successfully";
    }

    @PutMapping("/{id}/reserve")
    public void updateReservedTimeIntervals(@PathVariable("id") Long placeId,
                                            @RequestParam Long tableId,
                                            @RequestBody Set<Long> reservedTimeIntervalIds) {
            placeService.reserveTimeIntervals(placeId, tableId, reservedTimeIntervalIds);
    }
}
