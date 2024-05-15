package com.bebe.admin_user_service.controller;

import com.bebe.admin_user_service.model.AdminUser;
import com.bebe.admin_user_service.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @Autowired
    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminUser> getAdminById(@PathVariable("id") Long adminId) {
            return ResponseEntity.ok(adminUserService.getAdminById(adminId));
    }

    @PostMapping("/")
    public AdminUser saveAdminUser(@Validated @RequestBody AdminUser admin) {
            return adminUserService.saveAdmin(admin);
    }

    @PutMapping("/{id}")
    public AdminUser updateAdminUser(@RequestBody AdminUser admin, @PathVariable("id") Long adminId) {
            return adminUserService.updateAdmin(admin, adminId);
    }

    @DeleteMapping("/{id}")
    public void deleteAdminUserById(@PathVariable("id") Long adminId) {
            adminUserService.deleteAdminById(adminId);
            System.out.println("Deleted successfully");
    }
}
