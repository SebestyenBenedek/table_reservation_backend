package com.bebe.guest_user_service.controller;

import com.bebe.guest_user_service.model.GuestUser;
import com.bebe.guest_user_service.service.GuestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class GuestUserController {
    private final GuestUserService guestUserService;

    @Autowired
    public GuestUserController(GuestUserService guestUserService) {
        this.guestUserService = guestUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestUser> getUserById(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(guestUserService.getUserById(userId));
    }

    @PostMapping("/")
    public GuestUser saveUser(@Validated @RequestBody GuestUser user) {
        return guestUserService.saveUser(user);
    }

    @PutMapping("/{id}")
    public GuestUser updateUser(@RequestBody GuestUser user, @PathVariable("id") Long userId) {
        return guestUserService.updateUser(user, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long userId) {
        guestUserService.deleteUserById(userId);
    }
}
