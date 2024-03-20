package com.bebe.guest_user_service.dto;

import com.bebe.guest_user_service.model.Languages;

public record NewGuestUserDTO(String username,
                              String password,
                              String email,
                              String phoneNumber,
                              String profilePictureUrl,
                              Languages language,
                              boolean premiumUser) {
}
