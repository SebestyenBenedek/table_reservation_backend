package com.bebe.place_service.service.factory.timeInterval.generator;


import com.bebe.place_service.model.TimeInterval;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class HalfHourIntervalGenerator implements TimeIntervalGenerator {
    private static final int HALF_HOUR_IN_MINUTES = 30;

    @Override
    public Set<TimeInterval> generateTimeInterval(DayOfWeek day, LocalTime openingHour, LocalTime closingHour) {
        Set<TimeInterval> newTimeIntervals = new HashSet<>();

        LocalTime currentTime = openingHour;
        while (currentTime.isBefore(closingHour)) {
            TimeInterval timeInterval = new TimeInterval();
            timeInterval.setDay(day);
            timeInterval.setTimeStampFrom(currentTime);
            timeInterval.setTimeStampTo(currentTime.plusMinutes(HALF_HOUR_IN_MINUTES));
            newTimeIntervals.add(timeInterval);

            currentTime = currentTime.plusMinutes(HALF_HOUR_IN_MINUTES);
        }

        return newTimeIntervals;
    }
}
