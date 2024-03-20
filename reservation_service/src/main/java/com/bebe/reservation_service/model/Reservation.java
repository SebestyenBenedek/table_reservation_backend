package com.bebe.reservation_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    private Long placeId;

    private Long guestUserId;

    private Long tableId;

    @ElementCollection
    private Set<Long> timeIntervalIds;
}
