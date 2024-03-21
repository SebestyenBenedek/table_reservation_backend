package com.bebe.reservation_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record NewReservationDTO(Long guestUserId,
                                Long placeId,
                                LocalDate reservationDate,
                                LocalTime reservationFrom,
                                LocalTime reservationTo) {
}
