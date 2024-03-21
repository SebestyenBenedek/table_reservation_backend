package com.bebe.reservation_service.controller;

import com.bebe.reservation_service.dto.NewReservationDTO;
import com.bebe.reservation_service.model.Reservation;
import com.bebe.reservation_service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long reservationId) {
        return ResponseEntity.ok(reservationService.getReservationById(reservationId));
    }

    @GetMapping("/place/{id}")
    public Set<Reservation> getReservationsByPlaceId(@PathVariable("id") Long placeId) {
        return reservationService.getAllReservationsByPlace(placeId);
    }

    @GetMapping("/user/{id}")
    public Set<Reservation> getReservationsByUserId(@PathVariable("id") Long userId) {
        return reservationService.getAllReservationByUser(userId);
    }

    @PostMapping("/")
    public Reservation saveReservation(@Validated @RequestBody NewReservationDTO reservation) {
        return reservationService.saveReservation(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservationById(@PathVariable("id") Long reservationId) {
        reservationService.deleteReservationById(reservationId);
    }
}
