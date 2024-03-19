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
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TimeInterval {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "time_interval_table",
            joinColumns = @JoinColumn(name = "time_interval_id"),
            inverseJoinColumns = @JoinColumn(name = "place_table_id")
    )
    private Set<PlaceTable> placeTables;

    private LocalDate date;

    private DayOfWeek day;

    private LocalTime timeStampFrom;

    private LocalTime timeStampTo;

    private boolean isReserved;

    @ManyToOne
    private Place place;

    @ManyToOne
    private Reservation reservation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeInterval that = (TimeInterval) o;
        return isReserved == that.isReserved && Objects.equals(id, that.id) && Objects.equals(placeTables, that.placeTables) && Objects.equals(date, that.date) && day == that.day && Objects.equals(timeStampFrom, that.timeStampFrom) && Objects.equals(timeStampTo, that.timeStampTo) && Objects.equals(place, that.place) && Objects.equals(reservation, that.reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, placeTables, date, day, timeStampFrom, timeStampTo, isReserved, place, reservation);
    }
}
