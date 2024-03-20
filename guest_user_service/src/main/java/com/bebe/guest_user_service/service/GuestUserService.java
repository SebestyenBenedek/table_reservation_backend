package com.bebe.guest_user_service.service;

import com.bebe.guest_user_service.model.GuestUser;
import com.bebe.guest_user_service.repository.GuestUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class GuestUserService {
    private final GuestUserRepository guestUserRepository;

    @Autowired
    public GuestUserService(GuestUserRepository guestUserRepository) {
        this.guestUserRepository = guestUserRepository;
    }

    public GuestUser saveUser(GuestUser user) {
        return guestUserRepository.save(user);
    }

    public GuestUser updateUser(GuestUser user, Long userId) {
        GuestUser userToUpdate = guestUserRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("AdminUser not found with ID: " + userId));

        userToUpdate.setUserName(user.getUserName());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        userToUpdate.setProfilePictureUrl(user.getProfilePictureUrl());
        userToUpdate.setLanguage(user.getLanguage());
        userToUpdate.setPremiumUser(user.isPremiumUser());

        return guestUserRepository.save(userToUpdate);
    }

    public void deleteUserById(Long userId) {
        guestUserRepository.deleteById(userId);
    }
}
