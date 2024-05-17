package com.bebe.guest_user_service.repository;

import com.bebe.guest_user_service.model.GuestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestUserRepository extends JpaRepository<GuestUser, Long> {
    boolean existsGuestUserByUserName(String userName);
    boolean existsGuestUserByEmail(String email);
    boolean existsGuestUserByPhoneNumber(String phoneNumber);
}
