package com.bebe.place_service.model.user;


import com.bebe.place_service.model.Place;
import com.bebe.place_service.model.reservation.Reservation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class GuestUser extends User {
    @OneToMany(mappedBy = "user")
    private Set<Place> favouritePlaces;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<>();

    private boolean premiumUser;
}
