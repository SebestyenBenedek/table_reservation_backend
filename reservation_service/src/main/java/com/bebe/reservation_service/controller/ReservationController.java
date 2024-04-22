package com.bebe.reservation_service.controller;

import com.bebe.reservation_service.dto.NewReservationDTO;
import com.bebe.reservation_service.model.Reservation;
import com.bebe.reservation_service.service.ReservationService;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final Tracer tracer;

    @Autowired
    public ReservationController(ReservationService reservationService, Tracer tracer) {
        this.reservationService = reservationService;
        this.tracer = tracer;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long reservationId) {
        Span span = tracer.buildSpan("getReservationById").start();
        try {
            return ResponseEntity.ok(reservationService.getReservationById(reservationId));
        } finally {
            span.finish();
        }
    }

    @GetMapping("/place/{id}")
    public Set<Reservation> getReservationsByPlaceId(@PathVariable("id") Long placeId) {
        Span span = tracer.buildSpan("getReservationsByPlaceId").start();
        try {
            return reservationService.getAllReservationsByPlace(placeId);
        } finally {
            span.finish();
        }
    }

    @GetMapping("/user/{id}")
    public Set<Reservation> getReservationsByUserId(@PathVariable("id") Long userId) {
        Span span = tracer.buildSpan("getReservationsByUserId").start();
        try {
            return reservationService.getAllReservationByUser(userId);
        } finally {
            span.finish();
        }
    }

    @PostMapping("/")
    public Reservation saveReservation(@Validated @RequestBody NewReservationDTO reservation) {
        Span span = tracer.buildSpan("saveReservation").start();
        try {
            return reservationService.saveReservation(reservation);
        } finally {
            span.finish();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReservationById(@PathVariable("id") Long reservationId) {
        Span span = tracer.buildSpan("deleteReservationById").start();
        try {
            reservationService.deleteReservationById(reservationId);
        } finally {
            span.finish();
        }
    }
}
