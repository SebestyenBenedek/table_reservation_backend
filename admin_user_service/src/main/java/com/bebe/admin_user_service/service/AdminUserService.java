package com.bebe.admin_user_service.service;

import com.bebe.admin_user_service.model.AdminUser;
import com.bebe.admin_user_service.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AdminUserService {
    private final AdminUserRepository adminUserRepository;

    @Autowired
    public AdminUserService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    public AdminUser saveUser(AdminUser admin) {
        return adminUserRepository.save(admin);
    }

    public AdminUser updateUser(AdminUser admin, Long adminId) {
        AdminUser userToUpdate = adminUserRepository.findById(adminId)
                .orElseThrow(() -> new NoSuchElementException("AdminUser not found with ID: " + adminId));

        userToUpdate.setUserName(admin.getUserName());
        userToUpdate.setPassword(admin.getPassword());
        userToUpdate.setEmail(admin.getEmail());
        userToUpdate.setPhoneNumber(admin.getPhoneNumber());
        userToUpdate.setProfilePictureUrl(admin.getProfilePictureUrl());
        userToUpdate.setLanguage(admin.getLanguage());

        return adminUserRepository.save(userToUpdate);
    }

    public void deleteUserById(Long adminId) {
        adminUserRepository.deleteById(adminId);
    }
}
