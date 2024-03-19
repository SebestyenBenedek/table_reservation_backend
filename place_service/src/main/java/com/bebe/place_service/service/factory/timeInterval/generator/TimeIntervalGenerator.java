package com.bebe.place_service.service.factory.timeInterval.generator;


import com.bebe.place_service.model.TimeInterval;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public interface TimeIntervalGenerator {
    Set<TimeInterval> generateTimeInterval(DayOfWeek day, LocalTime openingHour, LocalTime closingHour);
}
