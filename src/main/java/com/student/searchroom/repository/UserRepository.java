package com.student.searchroom.repository;

import com.student.searchroom.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
    Page<User> findAll(Pageable pageable);
}
