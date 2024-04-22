package com.bebe.guest_user_service.controller;

import com.bebe.guest_user_service.model.GuestUser;
import com.bebe.guest_user_service.service.GuestUserService;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class GuestUserController {
    private final GuestUserService guestUserService;
    private final Tracer tracer;

    @Autowired
    public GuestUserController(GuestUserService guestUserService, Tracer tracer) {
        this.guestUserService = guestUserService;
        this.tracer = tracer;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestUser> getUserById(@PathVariable("id") Long userId) {
        Span span = tracer.buildSpan("getUserById").start();
        try {
            return ResponseEntity.ok(guestUserService.getUserById(userId));
        } finally {
            span.finish();
        }
    }

    @PostMapping("/")
    public GuestUser saveUser(@Validated @RequestBody GuestUser user) {
        Span span = tracer.buildSpan("saveUser").start();
        try {
            return guestUserService.saveUser(user);
        } finally {
            span.finish();
        }
    }

    @PutMapping("/{id}")
    public GuestUser updateUser(@RequestBody GuestUser user, @PathVariable("id") Long userId) {
        Span span = tracer.buildSpan("updateUser").start();
        try {
            return guestUserService.updateUser(user, userId);
        } finally {
            span.finish();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long userId) {
        Span span = tracer.buildSpan("deleteUserById").start();
        try {
            guestUserService.deleteUserById(userId);
            System.out.println("Deleted successfully");
        } finally {
            span.finish();
        }
    }
}
