package com.bebe.place_service.model;

import com.bebe.place_service.model.reservation.Reservation;
import com.bebe.place_service.model.user.AdminUser;
import com.bebe.place_service.model.user.GuestUser;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String address;

    @OneToMany(mappedBy = "place", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TimeInterval> timeIntervals;

    private double rating;

    private int price;

    private String description;

    private String menu;

    @Enumerated(EnumType.STRING)
    private List<Characteristic> characteristics;

    @ElementCollection
    private Set<String> images;

    @NonNull
    @OneToOne
    private AdminUser owner;

    @NonNull
    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PlaceTable> tables;

    @OneToMany(mappedBy = "place")
    private Set<Reservation> reservations;

    @ManyToOne
    private GuestUser user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Double.compare(rating, place.rating) == 0 && price == place.price && Objects.equals(id, place.id) && Objects.equals(name, place.name) && Objects.equals(address, place.address) && Objects.equals(timeIntervals, place.timeIntervals) && Objects.equals(description, place.description) && Objects.equals(menu, place.menu) && Objects.equals(characteristics, place.characteristics) && Objects.equals(images, place.images) && Objects.equals(owner, place.owner) && Objects.equals(tables, place.tables) && Objects.equals(reservations, place.reservations) && Objects.equals(user, place.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, timeIntervals, rating, price, description, menu, characteristics, images, owner, tables, reservations, user);
    }
}