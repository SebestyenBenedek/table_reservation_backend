package com.bebe.place_service.dto;

import com.bebe.place_service.model.Characteristic;

import java.util.List;
import java.util.Set;

public record NewPlaceDTO(String name,
                          String address,
                          String description,
                          String menu,
                          List<Characteristic> characteristics,
                          Set<String> images,
                          int numberOfTables,
                          Long ownerId,
                          TimeIntervalForWeekDTO timeIntervalForWeek) {
}
