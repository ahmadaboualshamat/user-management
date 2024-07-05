package com.usermanagement.domain.repository;

import com.usermanagement.domain.entity.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserActionRepository extends JpaRepository<UserAction, Long> {
    Optional<UserAction> findById(Long id);
}
