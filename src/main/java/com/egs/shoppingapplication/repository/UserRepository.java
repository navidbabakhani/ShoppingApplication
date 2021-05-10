package com.egs.shoppingapplication.repository;

import com.egs.shoppingapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    <S extends User> S save(S s);
}