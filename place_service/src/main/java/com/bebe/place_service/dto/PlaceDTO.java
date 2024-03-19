package com.bebe.place_service.dto;

import com.bebe.place_service.model.Characteristic;
import com.bebe.place_service.model.PlaceTable;
import com.bebe.place_service.model.TimeInterval;
import com.bebe.place_service.model.reservation.Reservation;
import com.bebe.place_service.model.user.AdminUser;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public record PlaceDTO(String name,
                       String address,
                       Set<DayOfWeek> openDays,
                       LocalTime openingTime,
                       List<TimeInterval> openHours,
                       double rating,
                       int price,
                       String description,
                       String menu,
                       List<Characteristic> characteristics,
                       Set<String> images,
                       AdminUser owner,
                       Set<PlaceTable> tables,
                       Set<Reservation> reservations) {
}
