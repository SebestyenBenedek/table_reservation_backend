package com.bebe.place_service.service.factory.placeTable.generator;

import com.bebe.place_service.model.Place;
import com.bebe.place_service.model.PlaceTable;
import com.bebe.place_service.model.TimeInterval;

import java.util.Set;

public interface PlaceTableGenerator {
    Set<PlaceTable> generatePlaceTable(int numberOfTables, Place place, Set<TimeInterval> timeIntervals);
}
