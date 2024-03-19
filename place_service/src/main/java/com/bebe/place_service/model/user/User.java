package com.bebe.place_service.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
public abstract class User {
    @Id
    @GeneratedValue
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

    private String language;
}
