package com.bebe.reservation_service.service;

import com.bebe.reservation_service.config.WebclientConfig;
import com.bebe.reservation_service.dto.NewReservationDTO;
import com.bebe.reservation_service.model.*;
import com.bebe.reservation_service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final WebclientConfig webclientConfig;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, WebclientConfig webclientConfig) {
        this.reservationRepository = reservationRepository;
        this.webclientConfig = webclientConfig;
    }

    public Reservation saveReservation(NewReservationDTO reservation) {
        Place place = webclientConfig.getWebClientBuilder()
                .build()
                .get()
                .uri("http://place_service/places/{id}", reservation.placeId())
                .retrieve()
                .bodyToMono(Place.class)
                .block();

        assert place != null;
        PlaceTable freeTable = getRandomFreeTable(place.getTables(), reservation.reservationDate(), reservation.reservationFrom(), reservation.reservationTo());
        Set<Long> reservedTimeIntervalIds = reserveTimeInterval(freeTable, reservation.reservationDate(), reservation.reservationFrom(), reservation.reservationTo());

        Reservation newReservation = new Reservation();

        newReservation.setGuestUserId(reservation.guestUserId());
        newReservation.setPlaceId(reservation.placeId());
        assert freeTable != null;
        newReservation.setTableId(freeTable.getId());
        newReservation.setTimeIntervalIds(reservedTimeIntervalIds);

        return reservationRepository.save(newReservation);
    }

    private boolean hasOverlappingInterval(TimeInterval interval, LocalDate reservationDate, LocalTime currentTime) {
        LocalDate intervalDate = interval.getDate();
        LocalTime intervalFrom = interval.getTimeStampFrom();
        LocalTime intervalTo = interval.getTimeStampTo();

        return intervalDate.equals(reservationDate) &&
                intervalFrom.isBefore(currentTime.plusMinutes(30)) &&
                intervalTo.isAfter(currentTime);
    }

    private boolean isTableFree(PlaceTable table, LocalDate reservationDate, LocalTime reservationFrom, LocalTime reservationTo) {
        for (LocalTime currentTime = reservationFrom; currentTime.isBefore(reservationTo); currentTime = currentTime.plusMinutes(30)) {
            boolean intervalFound = false;
            for (TimeInterval interval : table.getTimeIntervals()) {
                if (hasOverlappingInterval(interval, reservationDate, currentTime)) {
                    intervalFound = true;
                    if (interval.isReserved()) {
                        return false;
                    }
                }
            }
            if (!intervalFound) {
                return false;
            }
        }
        return true;
    }

    private Set<Long> reserveTimeInterval(PlaceTable table, LocalDate reservationDate, LocalTime reservationFrom, LocalTime reservationTo) {
        Set<Long> reservedTimeIntervals = new HashSet<>();

        for (LocalTime currentTime = reservationFrom; currentTime.isBefore(reservationTo); currentTime = currentTime.plusMinutes(30)) {
            for (TimeInterval interval : table.getTimeIntervals()) {
                if (hasOverlappingInterval(interval, reservationDate, currentTime)) {
                    interval.setReserved(true);
                    reservedTimeIntervals.add(interval.getId());
                }
            }
        }
        return reservedTimeIntervals;
    }

    private PlaceTable getRandomFreeTable(Set<PlaceTable> tables, LocalDate reservationDate, LocalTime reservationFrom, LocalTime reservationTo) {
        List<PlaceTable> freeTables = new ArrayList<>();

        for (PlaceTable table : tables) {
            if (isTableFree(table, reservationDate, reservationFrom, reservationTo)) {
                freeTables.add(table);
            }
        }

        if (!freeTables.isEmpty()) {
            return getRandomElement(freeTables);
        }

        throw new NoSuchElementException("There aren't any free tables at that time interval!");
    }


    private <T> T getRandomElement(List<T> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    public Reservation getReservationById(Long reservationId) {
        if (reservationRepository.findReservationById(reservationId) == null) {
            throw new NoSuchElementException("The place doesn't exist!");
        }
        return reservationRepository.findReservationById(reservationId);
    }

    public Set<Reservation> getAllReservationsByPlace(Long placeId) {
        return reservationRepository.findAllByPlaceId(placeId);
    }

    public Set<Reservation> getAllReservationByUser(Long guestUserId) {
        return reservationRepository.findAllByGuestUserId(guestUserId);
    }

    public void deleteReservationById(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
