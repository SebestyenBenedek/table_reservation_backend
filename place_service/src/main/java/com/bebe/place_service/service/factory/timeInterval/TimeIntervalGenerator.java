package com.bebe.place_service.service.factory.timeInterval;


import com.bebe.place_service.dto.TimeIntervalForDayDTO;
import com.bebe.place_service.model.Place;
import com.bebe.place_service.model.PlaceTable;
import com.bebe.place_service.model.TimeInterval;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public interface TimeIntervalGenerator {
    Set<TimeInterval> generateTimeInterval(Set<TimeIntervalForDayDTO> timeIntervalForDayDTOSet, LocalDate currentDate, PlaceTable table);
}
