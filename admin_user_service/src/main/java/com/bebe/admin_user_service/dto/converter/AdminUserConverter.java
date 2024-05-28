package com.bebe.admin_user_service.dto.converter;

import org.springframework.stereotype.Component;

import com.bebe.admin_user_service.dto.AdminUserDTO;
import com.bebe.admin_user_service.model.AdminUser;

@Component
public class AdminUserConverter {
    public AdminUserDTO convertToAdminUserDTO(AdminUser admin) {
        return new AdminUserDTO(admin.getUserName(),
                    admin.getPassword(),
                    admin.getEmail(),
                    admin.getPhoneNumber(),
                    admin.getProfilePictureUrl(),
                    admin.getLanguage());
    }

    public AdminUser convertToUser(AdminUserDTO adminDTO) {
        return new AdminUser()
                .setUserName(adminDTO.userName())
                .setPassword(adminDTO.password())
                .setEmail(adminDTO.email())
                .setPhoneNumber(adminDTO.phoneNumber())
                .setProfilePictureUrl(adminDTO.profilePictureUrl())
                .setLanguage(adminDTO.language());
    }
}
