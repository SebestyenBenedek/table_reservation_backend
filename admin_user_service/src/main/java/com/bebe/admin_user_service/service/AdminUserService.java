package com.bebe.admin_user_service.service;

import com.bebe.admin_user_service.model.AdminUser;
import com.bebe.admin_user_service.repository.AdminUserRepository;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

@Service
public class AdminUserService {
    private final AdminUserRepository adminUserRepository;
    private final Tracer tracer;

    @Autowired
    public AdminUserService(AdminUserRepository adminUserRepository, Tracer tracer) {
        this.adminUserRepository = adminUserRepository;
        this.tracer = tracer;
    }

    public AdminUser getAdminById(Long adminId) {
        Span span = tracer.buildSpan("getAdminById").start();
        try {
            AdminUser adminUser = adminUserRepository.findAdminUserById(adminId);
            if (adminUser == null) {
                throw new NoSuchElementException("No such admin!");
            }
            return adminUser;
        } finally {
            span.finish();
        }
    }

    public AdminUser saveAdmin(AdminUser admin) {
        Span span = tracer.buildSpan("saveAdmin").start();
        try {
            if (adminUserRepository.existsAdminUserByUserName(admin.getUserName())) {
                throw new IllegalArgumentException("Username '" + admin.getUserName() + "' is already in use");
            }
            if (adminUserRepository.existsAdminUserByEmail(admin.getEmail())) {
                throw new IllegalArgumentException("Email '" + admin.getEmail() + "' is already in use");
            }
            if (adminUserRepository.existsAdminUserByPhoneNumber(admin.getPhoneNumber())) {
                throw new IllegalArgumentException("Phone number '" + admin.getPhoneNumber() + "' is already in use");
            }
            return adminUserRepository.save(admin);
        } finally {
            span.finish();
        }
    }

    public AdminUser updateAdmin(AdminUser admin, Long adminId) {
        Span span = tracer.buildSpan("updateAdmin").start();
        try {
            AdminUser adminToUpdate = adminUserRepository.findById(adminId)
                    .orElseThrow(() -> new NoSuchElementException("AdminUser not found with ID: " + adminId));

            updateIfNotEmpty(adminToUpdate::setUserName, admin.getUserName());
            updateIfNotEmpty(adminToUpdate::setPassword, admin.getPassword());
            updateIfNotEmpty(adminToUpdate::setEmail, admin.getEmail());
            updateIfNotEmpty(adminToUpdate::setPhoneNumber, admin.getPhoneNumber());
            updateIfNotEmpty(adminToUpdate::setProfilePictureUrl, admin.getProfilePictureUrl());
            updateIfNotEmpty(adminToUpdate::setLanguage, admin.getLanguage());

            return adminUserRepository.save(adminToUpdate);
        } finally {
            span.finish();
        }
    }

    private <T> void updateIfNotEmpty(Consumer<T> setter, T value) {
        if (value != null && !value.toString().isEmpty()) setter.accept(value);
    }

    public void deleteAdminById(Long adminId) {
        Span span = tracer.buildSpan("deleteAdminById").start();
        try {
            adminUserRepository.deleteById(adminId);
            System.out.println("Deleted successfully");
        } finally {
            span.finish();
        }
    }
}
