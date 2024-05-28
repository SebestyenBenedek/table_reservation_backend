package com.bebe.admin_user_service;

import com.bebe.admin_user_service.model.AdminUser;
import com.bebe.admin_user_service.service.AdminUserService;
import com.bebe.admin_user_service.repository.AdminUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
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
        assertTrue(adminUser.isPresent());
        assertEquals(adminUser.get(), result);
        verify(adminUserRepository, times(1)).findById(adminId);
    }

    @Test
    public void testSaveAdmin() {
        // Arrange
        AdminUser admin = new AdminUser();
        admin.setId(1L);
        admin.setUserName("username");
        admin.setPassword("password");
        admin.setEmail("test@email.com");
        admin.setPhoneNumber("+36205558989");
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
        admin.setId(1L);
        admin.setUserName("username");
        admin.setPassword("password");
        admin.setEmail("test@email.com");
        admin.setPhoneNumber("+36205558989");
        AdminUser existingAdmin = new AdminUser();
        admin.setId(2L);
        admin.setUserName("username2");
        admin.setPassword("password2");
        admin.setEmail("test@email.com2");
        admin.setPhoneNumber("+36205558988");
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