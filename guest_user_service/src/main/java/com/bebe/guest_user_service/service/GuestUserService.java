package com.bebe.guest_user_service.service;

import com.bebe.guest_user_service.model.GuestUser;
import com.bebe.guest_user_service.repository.GuestUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

@Service
public class GuestUserService {
    private final GuestUserRepository guestUserRepository;

    @Autowired
    public GuestUserService(GuestUserRepository guestUserRepository) {
        this.guestUserRepository = guestUserRepository;
    }

    public GuestUser getUserById(Long userId) {
            if (guestUserRepository.findGuestUserById(userId) == null) {
                throw new NoSuchElementException("No such user!");
            }
            return guestUserRepository.findGuestUserById(userId);
    }

    public GuestUser saveUser(GuestUser user) {
            if (guestUserRepository.existsGuestUserByUserName(user.getUserName())) {
                throw new IllegalArgumentException("Username '" + user.getUserName() + "' is already in use");
            }
            if (guestUserRepository.existsGuestUserByEmail(user.getEmail())) {
                throw new IllegalArgumentException("Email '" + user.getEmail() + "' is already in use");
            }
            if (guestUserRepository.existsGuestUserByPhoneNumber(user.getPhoneNumber())) {
                throw new IllegalArgumentException("Phone number '" + user.getPhoneNumber() + "' is already in use");
            }
            return guestUserRepository.save(user);
    }

    public GuestUser updateUser(GuestUser user, Long userId) {
            GuestUser userToUpdate = guestUserRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("AdminUser not found with ID: " + userId));

            updateIfNotEmpty(userToUpdate::setUserName, user.getUserName());
            updateIfNotEmpty(userToUpdate::setPassword, user.getPassword());
            updateIfNotEmpty(userToUpdate::setEmail, user.getEmail());
            updateIfNotEmpty(userToUpdate::setPhoneNumber, user.getPhoneNumber());
            updateIfNotEmpty(userToUpdate::setProfilePictureUrl, user.getProfilePictureUrl());
            updateIfNotEmpty(userToUpdate::setLanguage, user.getLanguage());
            updateIfNotEmpty(userToUpdate::setPremiumUser, user.isPremiumUser());

            return guestUserRepository.save(userToUpdate);
    }

    private <T> void updateIfNotEmpty(Consumer<T> setter, T value) {
        if (value != null && !value.toString().isEmpty()) setter.accept(value);
    }

    public void deleteUserById(Long userId) {
            guestUserRepository.deleteById(userId);
            System.out.println("Deleted successfully");
    }
}
