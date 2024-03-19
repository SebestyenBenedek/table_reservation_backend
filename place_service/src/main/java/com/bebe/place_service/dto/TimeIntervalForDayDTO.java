package com.bebe.place_service.dto;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record TimeIntervalForDayDTO(DayOfWeek day,
                                    LocalTime openingHour,
                                    LocalTime closingHour) {
}
