package com.bebe.reservation_service.repository;

import com.bebe.reservation_service.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findReservationById(Long reservationId);
    Set<Reservation> findAllByPlaceId(Long placeId);
    Set<Reservation> findAllByGuestUserId(Long userId);
}
