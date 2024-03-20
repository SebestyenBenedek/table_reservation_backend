package com.bebe.guest_user_service.dto;

import java.util.Set;

public record GuestUserDTO(String userName,
                           String profilePictureUrl,
                           Set<Long> favouritePlaceIds,
                           Set<Long> reservationIds) {
}
