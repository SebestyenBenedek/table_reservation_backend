package com.bebe.place_service.service.factory.timeInterval;


import com.bebe.place_service.dto.TimeIntervalForDayDTO;
import com.bebe.place_service.model.PlaceTable;
import com.bebe.place_service.model.TimeInterval;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class HalfHourIntervalGenerator implements TimeIntervalGenerator {
    private static final int HALF_HOUR_IN_MINUTES = 30;

    @Override
    public Set<TimeInterval> generateTimeInterval(Set<TimeIntervalForDayDTO> timeIntervalsForWeek, PlaceTable table) {
            Set<TimeInterval> newTimeIntervals = new HashSet<>();

            LocalDate currentDate = LocalDate.now();
            LocalDate endDate = currentDate.plusMonths(1);

            while (currentDate.isBefore(endDate)) {
                for (TimeIntervalForDayDTO timeIntervalForDay : timeIntervalsForWeek) {
                    if (timeIntervalForDay.day().equals(currentDate.getDayOfWeek())) {
                        LocalTime currentTime = timeIntervalForDay.openingHour();
                        LocalTime closingHour = timeIntervalForDay.closingHour();

                        while (currentTime.isBefore(closingHour)) {
                            TimeInterval timeInterval = setFieldsOfTimeInterval(timeIntervalForDay.day(), currentDate, currentTime, table);
                            newTimeIntervals.add(timeInterval);
                            currentTime = currentTime.plusMinutes(HALF_HOUR_IN_MINUTES);
                        }
                    }
                }
                currentDate = currentDate.plusDays(1);
            }

            return newTimeIntervals;
    }

    private TimeInterval setFieldsOfTimeInterval(DayOfWeek day, LocalDate date, LocalTime startTime, PlaceTable table) {
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.setDay(day);
        timeInterval.setDate(date);
        timeInterval.setTimeStampFrom(startTime);
        timeInterval.setTimeStampTo(startTime.plusMinutes(HALF_HOUR_IN_MINUTES));
        timeInterval.setPlaceTable(table);

        return timeInterval;
    }
}
