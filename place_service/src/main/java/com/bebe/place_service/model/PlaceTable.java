package com.bebe.place_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class PlaceTable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Place place;

    @ManyToMany(mappedBy = "placeTables")
    private Set<TimeInterval> timeIntervals;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceTable that = (PlaceTable) o;
        return Objects.equals(id, that.id) && Objects.equals(place, that.place) && Objects.equals(timeIntervals, that.timeIntervals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, place, timeIntervals);
    }
}
