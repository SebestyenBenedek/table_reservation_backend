package com.bebe.admin_user_service.dto;

import com.bebe.admin_user_service.model.Languages;

public record AdminUserDTO(String userName,
                           String password,
                           String email,
                           String phoneNumber,
                           String profilePictureUrl,
                           Languages language) {
}
