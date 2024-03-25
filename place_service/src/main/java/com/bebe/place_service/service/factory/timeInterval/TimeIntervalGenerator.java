package com.bebe.place_service.service.factory.timeInterval;


import com.bebe.place_service.model.Place;
import com.bebe.place_service.model.PlaceTable;
import com.bebe.place_service.model.TimeInterval;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public interface TimeIntervalGenerator {
    Set<TimeInterval> generateTimeInterval(DayOfWeek day, LocalTime openingHour, LocalTime closingHour, PlaceTable table);
}
