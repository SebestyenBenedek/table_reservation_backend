package com.bebe.reservation_service.service;

import com.bebe.reservation_service.config.WebclientConfig;
import com.bebe.reservation_service.dto.NewReservationDTO;
import com.bebe.reservation_service.model.*;
import com.bebe.reservation_service.repository.ReservationRepository;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final WebclientConfig webclientConfig;
    private final Tracer tracer;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, WebclientConfig webclientConfig, Tracer tracer) {
        this.reservationRepository = reservationRepository;
        this.webclientConfig = webclientConfig;
        this.tracer = tracer;
    }

    public Reservation saveReservation(NewReservationDTO reservation) {
        Span span = tracer.buildSpan("saveReservation").start();
        try {
            Place place = getPlaceFromPlaceService(reservation.placeId());

            if (place == null) {
                throw new RuntimeException("Place not found");
            }

            LocalDate reservationDate = reservation.reservationDate();
            LocalTime reservationStartTime = reservation.reservationFrom();
            LocalTime reservationEndTime = reservation.reservationTo();

            PlaceTable freeTable = getRandomFreeTable(place.getTables(), reservationDate, reservationStartTime, reservationEndTime);

            Set<Long> reservedTimeIntervalIds = reserveTimeInterval(freeTable, reservationDate, reservationStartTime, reservationEndTime);

            updatePlaceWithReservedIntervals(place, freeTable.getId(), reservedTimeIntervalIds);

            Reservation newReservation = createReservationEntity(reservation, freeTable.getId(), reservedTimeIntervalIds);

            return reservationRepository.save(newReservation);
        } finally {
            span.finish();
        }
    }

    private Reservation createReservationEntity(NewReservationDTO reservation, Long tableId, Set<Long> reservedTimeIntervalIds) {
        Span span = tracer.buildSpan("createReservationEntity").start();
        try {
            Reservation newReservation = new Reservation();
            newReservation.setGuestUserId(reservation.guestUserId());
            newReservation.setPlaceId(reservation.placeId());
            newReservation.setTableId(tableId);
            newReservation.setTimeIntervalIds(reservedTimeIntervalIds);
            return newReservation;
        } finally {
            span.finish();
        }
    }

    private Place getPlaceFromPlaceService(Long placeId) {
        Span span = tracer.buildSpan("getPlaceFromPlaceService").start();
        try {
            return webclientConfig.getWebClientBuilder()
                    .build()
                    .get()
                    .uri("http://place-service/places/{id}", placeId)
                    .retrieve()
                    .bodyToMono(Place.class)
                    .block();
        } finally {
            span.finish();
        }
    }

    private void updatePlaceWithReservedIntervals(Place place, Long tableId, Set<Long> reservedTimeIntervalIds) {
        Span span = tracer.buildSpan("updatePlaceWithReservedIntervals").start();
        try {
            place.getTables().stream()
                    .filter(table -> table.getId().equals(tableId))
                    .findFirst()
                    .ifPresent(table -> {
                        Set<TimeInterval> updatedTimeIntervals = new HashSet<>();
                        table.getTimeIntervals().stream()
                                .filter(timeInterval -> reservedTimeIntervalIds.contains(timeInterval.getId()))
                                .forEach(timeInterval -> {
                                    timeInterval.setReserved(!timeInterval.isReserved());
                                    updatedTimeIntervals.add(timeInterval);
                                });
                        reserveTimeIntervalsInPlaceService(place, table, updatedTimeIntervals);
                    });
        } finally {
            span.finish();
        }
    }

    private void reserveTimeIntervalsInPlaceService(Place place, PlaceTable table, Set<TimeInterval> timeIntervals) {
        Span span = tracer.buildSpan("reserveTimeIntervalsInPlaceService").start();
        try {
            Long tableId = table.getId();
            Set<Long> reservedTimeIntervalIds = timeIntervals.stream()
                    .map(TimeInterval::getId)
                    .collect(Collectors.toSet());
            webclientConfig.getWebClientBuilder()
                    .build()
                    .put()
                    .uri("http://place-service/places/{id}/reserve?tableId={tableId}", place.getId(), tableId)
                    .body(Mono.just(reservedTimeIntervalIds), new ParameterizedTypeReference<>() {})
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } finally {
            span.finish();
        }
    }

    private boolean hasOverlappingInterval(TimeInterval interval, LocalDate reservationDate, LocalTime currentTime) {
        Span span = tracer.buildSpan("hasOverlappingInterval").start();
        try {
            LocalDate intervalDate = interval.getDate();
            LocalTime intervalFrom = interval.getTimeStampFrom();
            LocalTime intervalTo = interval.getTimeStampTo();

            return intervalDate.equals(reservationDate) &&
                    intervalFrom.isBefore(currentTime.plusMinutes(30)) &&
                    intervalTo.isAfter(currentTime);
        } finally {
            span.finish();
        }
    }

    private boolean isTableFree(PlaceTable table, LocalDate reservationDate, LocalTime reservationFrom, LocalTime reservationTo) {
        Span span = tracer.buildSpan("isTableFree").start();
        try {
            for (LocalTime currentTime = reservationFrom; currentTime.isBefore(reservationTo); currentTime = currentTime.plusMinutes(30)) {
                LocalTime current = currentTime;
                boolean intervalFound = table.getTimeIntervals().stream()
                        .anyMatch(interval -> hasOverlappingInterval(interval, reservationDate, current) && !interval.isReserved());

                if (!intervalFound) {
                    return false;
                }
            }
            return true;
        } finally {
            span.finish();
        }
    }

    private Set<Long> reserveTimeInterval(PlaceTable table, LocalDate reservationDate, LocalTime reservationFrom, LocalTime reservationTo) {
        Span span = tracer.buildSpan("reserveTimeInterval").start();
        try {
            Set<Long> reservedTimeIntervals = new HashSet<>();

            for (LocalTime currentTime = reservationFrom; currentTime.isBefore(reservationTo); currentTime = currentTime.plusMinutes(30)) {
                LocalTime current = currentTime;
                table.getTimeIntervals().stream()
                        .filter(interval -> hasOverlappingInterval(interval, reservationDate, current) && !interval.isReserved())
                        .forEach(interval -> {
                            interval.setReserved(true);
                            reservedTimeIntervals.add(interval.getId());
                        });
            }
            return reservedTimeIntervals;
        } finally {
            span.finish();
        }
    }

    private PlaceTable getRandomFreeTable(Set<PlaceTable> tables, LocalDate reservationDate, LocalTime reservationFrom, LocalTime reservationTo) {
        Span span = tracer.buildSpan("getRandomFreeTable").start();
        try {
            List<PlaceTable> freeTables = new ArrayList<>();
            System.out.println(reservationDate);

            for (PlaceTable table : tables) {
                System.out.println(table.getTimeIntervals());
                if (isTableFree(table, reservationDate, reservationFrom, reservationTo)) {
                    System.out.println(table);
                    freeTables.add(table);
                }
            }

            if (!freeTables.isEmpty()) {
                return getRandomElement(freeTables);
            }

            throw new NoSuchElementException("There aren't any free tables at that time interval!");
        } finally {
            span.finish();
        }
    }


    private <T> T getRandomElement(List<T> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    public Reservation getReservationById(Long reservationId) {
        Span span = tracer.buildSpan("getReservationById").start();
        try {
            if (reservationRepository.findReservationById(reservationId) == null) {
                throw new NoSuchElementException("The place doesn't exist!");
            }
            return reservationRepository.findReservationById(reservationId);
        } finally {
            span.finish();
        }
    }

    public Set<Reservation> getAllReservationsByPlace(Long placeId) {
        Span span = tracer.buildSpan("getAllReservationsByPlace").start();
        try {
            return reservationRepository.findAllByPlaceId(placeId);
        } finally {
            span.finish();
        }
    }

    public Set<Reservation> getAllReservationByUser(Long guestUserId) {
        Span span = tracer.buildSpan("getAllReservationByUser").start();
        try {
            return reservationRepository.findAllByGuestUserId(guestUserId);
        } finally {
            span.finish();
        }
    }

    public void deleteReservationById(Long reservationId) {
        Span span = tracer.buildSpan("deleteReservationById").start();
        try {
            Reservation reservation = reservationRepository.findById(reservationId)
                    .orElseThrow(() -> new RuntimeException("Reservation not found"));

            Set<Long> reservedTimeIntervalIds = reservation.getTimeIntervalIds();
            Place place = getPlaceFromPlaceService(reservation.getPlaceId());
            Long tableId = reservation.getTableId();

            updatePlaceWithReservedIntervals(place, tableId, reservedTimeIntervalIds);

            reservationRepository.deleteById(reservationId);
        } finally {
            span.finish();
        }
    }
}
