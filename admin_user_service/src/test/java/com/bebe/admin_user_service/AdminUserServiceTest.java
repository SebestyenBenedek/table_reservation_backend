package com.bebe.admin_user_service;

import com.bebe.admin_user_service.model.AdminUser;
import com.bebe.admin_user_service.service.AdminUserService;
import com.bebe.admin_user_service.repository.AdminUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminUserServiceTest {

    @Mock
    private AdminUserRepository adminUserRepository;

    @InjectMocks
    private AdminUserService adminUserService;

    @Test
    public void testGetAdminById() {
        // Arrange
        Long adminId = 1L;
        Optional <AdminUser> adminUser = Optional.ofNullable(new AdminUser());
        when(adminUserRepository.findById(adminId)).thenReturn(adminUser);

        // Act
        AdminUser result = adminUserService.getAdminById(adminId);

        // Assert
        assertEquals(adminUser, result);
        verify(adminUserRepository, times(1)).findById(adminId);
    }

    @Test
    public void testSaveAdmin() {
        // Arrange
        AdminUser admin = new AdminUser();
        when(adminUserRepository.existsAdminUserByUserName(admin.getUserName())).thenReturn(false);
        when(adminUserRepository.existsAdminUserByEmail(admin.getEmail())).thenReturn(false);
        when(adminUserRepository.existsAdminUserByPhoneNumber(admin.getPhoneNumber())).thenReturn(false);
        when(adminUserRepository.save(admin)).thenReturn(admin);

        // Act
        AdminUser result = adminUserService.saveAdmin(admin);

        // Assert
        assertEquals(admin, result);
        verify(adminUserRepository, times(1)).save(admin);
    }

    @Test
    public void testUpdateAdmin() {
        // Arrange
        Long adminId = 1L;
        AdminUser admin = new AdminUser();
        AdminUser existingAdmin = new AdminUser();
        when(adminUserRepository.findById(adminId)).thenReturn(Optional.of(existingAdmin));
        when(adminUserRepository.save(existingAdmin)).thenReturn(existingAdmin);

        // Act
        AdminUser result = adminUserService.updateAdmin(admin, adminId);

        // Assert
        assertEquals(existingAdmin, result);
        verify(adminUserRepository, times(1)).findById(adminId);
        verify(adminUserRepository, times(1)).save(existingAdmin);
    }

    @Test
    public void testDeleteAdminById() {
        // Arrange
        Long adminId = 1L;

        // Act
        adminUserService.deleteAdminById(adminId);

        // Assert
        verify(adminUserRepository, times(1)).deleteById(adminId);
    }

}