package com.bebe.admin_user_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AdminUser {
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