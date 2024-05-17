package com.bebe.place_service.dto;

import java.util.Set;

public record TimeIntervalForWeekDTO(Set<TimeIntervalForDayDTO> timeIntervalForDayDTOSet) {
}
