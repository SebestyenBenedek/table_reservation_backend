package com.bebe.place_service.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public record TimeIntervalForWeekDTO(Set<TimeIntervalForDayDTO> timeIntervalForDayDTOSet) {
}
