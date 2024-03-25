package com.bebe.place_service.service.factory.placeTable;

import com.bebe.place_service.model.Place;
import com.bebe.place_service.model.PlaceTable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PlaceTableGeneratorImpl implements PlaceTableGenerator {
    @Override
    public Set<PlaceTable> generatePlaceTable(int numberOfTables, Place place) {
        Set<PlaceTable> newPlaceTables = new HashSet<>();

        for (int i = 0; i <= numberOfTables; i++) {
            PlaceTable placeTable = new PlaceTable();
            placeTable.setPlace(place);
            placeTable.setTimeIntervals(new HashSet<>());
            newPlaceTables.add(placeTable);
        }

        return newPlaceTables;
    }
}
