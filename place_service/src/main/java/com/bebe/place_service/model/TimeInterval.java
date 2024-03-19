package com.bebe.place_service.model;

import com.bebe.place_service.model.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TimeInterval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PlaceTable placeTable;

    private LocalDate date;

    private DayOfWeek day;

    private LocalTime timeStampFrom;

    private LocalTime timeStampTo;

    private boolean isReserved;

    @ManyToOne
    private Place place;

    @ManyToOne
    private Reservation reservations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeInterval that = (TimeInterval) o;
        return isReserved == that.isReserved && Objects.equals(id, that.id) && Objects.equals(placeTable, that.placeTable) && Objects.equals(date, that.date) && day == that.day && Objects.equals(timeStampFrom, that.timeStampFrom) && Objects.equals(timeStampTo, that.timeStampTo) && Objects.equals(place, that.place) && Objects.equals(reservations, that.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, placeTable, date, day, timeStampFrom, timeStampTo, isReserved, place, reservations);
    }
}
