package com.bebe.place_service.service.factory.placeTable;

import com.bebe.place_service.model.Place;
import com.bebe.place_service.model.PlaceTable;

import java.util.Set;

public interface PlaceTableGenerator {
    Set<PlaceTable> generatePlaceTable(int numberOfTables, Place place);
}
