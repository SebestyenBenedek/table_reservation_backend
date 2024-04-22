package com.bebe.place_service.service.factory.placeTable;

import com.bebe.place_service.model.Place;
import com.bebe.place_service.model.PlaceTable;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PlaceTableGeneratorImpl implements PlaceTableGenerator {
    private final Tracer tracer;

    public PlaceTableGeneratorImpl(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public Set<PlaceTable> generatePlaceTable(int numberOfTables, Place place) {
        Span span = tracer.buildSpan("generatePlaceTable").start();
        try {
            Set<PlaceTable> newPlaceTables = new HashSet<>();

            for (int i = 0; i <= numberOfTables; i++) {
                PlaceTable placeTable = new PlaceTable();
                placeTable.setPlace(place);
                placeTable.setTimeIntervals(new HashSet<>());
                newPlaceTables.add(placeTable);
            }

            return newPlaceTables;
        } finally {
            span.finish();
        }
    }
}
