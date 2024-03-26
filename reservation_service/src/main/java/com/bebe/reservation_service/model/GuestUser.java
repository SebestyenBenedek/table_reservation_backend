package com.bebe.reservation_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class GuestUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String userName;

    @NonNull
    private String password;

    @NonNull
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private String profilePictureUrl;

    private Languages language;

    @ElementCollection
    private Set<Long> favouritePlaceIds;

    @ElementCollection
    private Set<Long> reservationIds;

    private boolean premiumUser;
}
