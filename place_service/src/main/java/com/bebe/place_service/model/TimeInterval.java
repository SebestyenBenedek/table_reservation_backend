package com.bebe.place_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_table_id", nullable = false)
    @JsonBackReference
    private PlaceTable placeTable;

    private LocalDate date;

    private DayOfWeek day;

    private LocalTime timeStampFrom;

    private LocalTime timeStampTo;

    private boolean isReserved;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeInterval that = (TimeInterval) o;
        return isReserved == that.isReserved &&
                Objects.equals(id, that.id) &&
                Objects.equals(placeTable, that.placeTable) &&
                Objects.equals(date, that.date) &&
                day == that.day &&
                Objects.equals(timeStampFrom, that.timeStampFrom) &&
                Objects.equals(timeStampTo, that.timeStampTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, placeTable, date, day, timeStampFrom, timeStampTo, isReserved);
    }

    @Override
    public String toString() {
        return "TimeInterval{" +
                "id=" + id +
                ", placeTables=" + (placeTable != null ? placeTable.getId() : null) +
                ", date=" + date +
                ", day=" + day +
                ", timeStampFrom=" + timeStampFrom +
                ", timeStampTo=" + timeStampTo +
                ", isReserved=" + isReserved +
                '}';
    }
}
