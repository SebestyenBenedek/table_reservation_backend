package com.bebe.admin_user_service.controller;

import com.bebe.admin_user_service.model.AdminUser;
import com.bebe.admin_user_service.service.AdminUserService;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
public class AdminUserController {
    private final AdminUserService adminUserService;
    private final Tracer tracer;

    @Autowired
    public AdminUserController(AdminUserService adminUserService, Tracer tracer) {
        this.adminUserService = adminUserService;
        this.tracer = tracer;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminUser> getAdminById(@PathVariable("id") Long adminId) {
        Span span = tracer.buildSpan("getAdminById").start();
        try {
            return ResponseEntity.ok(adminUserService.getAdminById(adminId));
        } finally {
            span.finish();
        }
    }

    @PostMapping("/")
    public AdminUser saveAdminUser(@Validated @RequestBody AdminUser admin) {
        Span span = tracer.buildSpan("saveAdminUser").start();
        try {
            return adminUserService.saveAdmin(admin);
        } finally {
            span.finish();
        }
    }

    @PutMapping("/{id}")
    public AdminUser updateAdminUser(@RequestBody AdminUser admin, @PathVariable("id") Long adminId) {
        Span span = tracer.buildSpan("updateAdminUser").start();
        try {
            return adminUserService.updateAdmin(admin, adminId);
        } finally {
            span.finish();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAdminUserById(@PathVariable("id") Long adminId) {
        Span span = tracer.buildSpan("deleteAdminUserById").start();
        try {
            adminUserService.deleteAdminById(adminId);
            System.out.println("Deleted successfully");
        } finally {
            span.finish();
        }
    }
}
