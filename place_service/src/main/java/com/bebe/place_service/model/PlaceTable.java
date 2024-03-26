package com.bebe.place_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    @JsonBackReference
    private Place place;

    @OneToMany(mappedBy = "placeTable", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(name = "time_intervals")
    @JsonManagedReference
    private Set<TimeInterval> timeIntervals;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceTable that = (PlaceTable) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, place);
    }

    @Override
    public String toString() {
        return "PlaceTable{" +
                "id=" + id +
                ", place=" + (place != null ? place.getId() : null) +
                ", timeIntervals=" + timeIntervals +
                '}';
    }
}
