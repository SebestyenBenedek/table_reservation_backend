package com.bebe.place_service.service;

import com.bebe.place_service.dto.NewPlaceDTO;
import com.bebe.place_service.dto.PlaceDTO;
import com.bebe.place_service.dto.TimeIntervalForDayDTO;
import com.bebe.place_service.model.Place;
import com.bebe.place_service.model.PlaceTable;
import com.bebe.place_service.model.TimeInterval;
import com.bebe.place_service.repository.PlaceRepository;
import com.bebe.place_service.repository.PlaceTableRepository;
import com.bebe.place_service.repository.TimeIntervalRepository;
import com.bebe.place_service.service.builder.PlaceBuilder;
import com.bebe.place_service.service.factory.placeTable.PlaceTableGeneratorImpl;
import com.bebe.place_service.service.factory.timeInterval.HalfHourIntervalGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class PlaceService {
    private static final double MIN_RATING = 0.0;
    private static final double MAX_RATING = 5.0;
    private static final int MIN_AVERAGE_PRICE = 0;
    private static final int MAX_AVERAGE_PRICE = 3;
    private final PlaceRepository placeRepository;
    private final PlaceTableRepository placeTableRepository;
    private final TimeIntervalRepository timeIntervalRepository;
    private final HalfHourIntervalGenerator timeIntervalGenerator;
    private final PlaceTableGeneratorImpl placeTableGenerator;
    private final PlaceBuilder placeBuilder;

    @Autowired
    public PlaceService(PlaceRepository placeRepository, PlaceTableRepository placeTableRepository, TimeIntervalRepository timeIntervalRepository, HalfHourIntervalGenerator timeIntervalGenerator, PlaceTableGeneratorImpl placeTableGenerator, PlaceBuilder placeBuilder) {
        this.placeRepository = placeRepository;
        this.placeTableRepository = placeTableRepository;
        this.timeIntervalRepository = timeIntervalRepository;
        this.timeIntervalGenerator = timeIntervalGenerator;
        this.placeTableGenerator = placeTableGenerator;
        this.placeBuilder = placeBuilder;
    }

    public Set<Place> getAllPlaces() {
        return new HashSet<>(placeRepository.findAll());
    }

    public Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new NoSuchElementException("The place doesn't exist!"));
    }

    public Place addPlace(NewPlaceDTO placeDto) {
        Place newPlace = placeBuilder.placeBuilder(placeDto);
        newPlace = placeRepository.save(newPlace);

        Set<PlaceTable> tables = placeTableGenerator.generatePlaceTable(placeDto.numberOfTables(), newPlace);
        tables = new HashSet<>(placeTableRepository.saveAll(tables));

        Set<TimeInterval> timeIntervals = createTimeIntervals(placeDto, tables);
        timeIntervalRepository.saveAll(timeIntervals);
        newPlace.setTables(tables);

        return placeRepository.save(newPlace);
    }

    private Set<TimeInterval> createTimeIntervals(NewPlaceDTO placeDto, Set<PlaceTable> tables) {
        Set<TimeIntervalForDayDTO> openHoursPerDays = placeDto.timeIntervalForWeek().timeIntervalForDayDTOSet();
        Set<TimeInterval> timeIntervals = new HashSet<>();

        for (PlaceTable table : tables) {
                timeIntervals.addAll(timeIntervalGenerator.generateTimeInterval(openHoursPerDays, table));
        }

        setTimeIntervalsForTables(tables, timeIntervals);
        return timeIntervals;
    }

    private void setTimeIntervalsForTables(Set<PlaceTable> tables, Set<TimeInterval> timeIntervals) {
        for (PlaceTable table : tables) {
            table.setTimeIntervals(timeIntervals);
        }
    }

    public Place updatePlace(PlaceDTO place, Long placeId) {
        Place placeToUpdate = placeRepository.findById(placeId)
                .orElseThrow(() -> new NoSuchElementException("Place not found with ID: " + placeId));

        updateIfNotEmpty(placeToUpdate::setName, place.name());
        updateIfNotEmpty(placeToUpdate::setAddress, place.address());
        updateIfInRange(placeToUpdate::setRating, place.rating(), MIN_RATING, MAX_RATING, "Invalid rating value");
        updateIfInRange(placeToUpdate::setPrice, place.price(), MIN_AVERAGE_PRICE, MAX_AVERAGE_PRICE, "Invalid price value");
        updateIfNotEmpty(placeToUpdate::setDescription, place.description());
        updateIfNotEmpty(placeToUpdate::setMenu, place.menu());
        updateIfNotEmpty(placeToUpdate::setCharacteristics, place.characteristics());
        updateIfNotEmpty(placeToUpdate::setImages, place.images());
        updateIfNotEmpty(placeToUpdate::setTables, place.tables());
        updateIfNotNull(placeToUpdate::setAdminUserId, place.ownerId());
        updateIfNotEmpty(placeToUpdate::setReservationIds, place.reservationIds());

        return placeRepository.save(placeToUpdate);
    }

    public void deletePlaceById(Long placeId) {
        System.out.println("Deleted successfully");
        placeRepository.deleteById(placeId);
    }

    public void reserveTimeIntervals(Long placeId, Long tableId, Set<Long> reservedTimeIntervalIds) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NoSuchElementException("Place not found with ID: " + placeId));

        PlaceTable tableToUpdate = place.getTables().stream()
                .filter(table -> table.getId().equals(tableId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Table not found with ID: " + tableId));

        tableToUpdate.getTimeIntervals().forEach(timeInterval -> {
            if (reservedTimeIntervalIds.contains(timeInterval.getId())) {
                timeInterval.setReserved(!timeInterval.isReserved());
            }
        });

        placeRepository.save(place);
    }

    private <T> void updateIfNotEmpty(Consumer<T> setter, T value) {
        if (value != null && !value.toString().isEmpty()) setter.accept(value);
    }

    private <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) setter.accept(value);
    }

    private <T extends Number> void updateIfInRange(Consumer<T> setter, T value, T minValue, T maxValue, String errorMessage) {
        if (value == null || value.doubleValue() < minValue.doubleValue() || value.doubleValue() > maxValue.doubleValue()) {
            throw new IllegalArgumentException(errorMessage);
        }
        setter.accept(value);
    }
}
