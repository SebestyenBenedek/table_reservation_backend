package com.bebe.admin_user_service.repository;

import com.bebe.admin_user_service.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    boolean existsAdminUserByUserName(String userName);
    boolean existsAdminUserByEmail(String email);
    boolean existsAdminUserByPhoneNumber(String phoneNumber);
}
