package com.bebe.place_service.model.reservation;


import com.bebe.place_service.model.Place;
import com.bebe.place_service.model.PlaceTable;
import com.bebe.place_service.model.TimeInterval;
import com.bebe.place_service.model.user.GuestUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Place place;

    @ManyToOne
    private GuestUser user;

    @OneToMany(mappedBy = "reservation")
    private Set<TimeInterval> timeIntervals;
}
