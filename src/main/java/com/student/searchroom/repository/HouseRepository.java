package com.student.searchroom.repository;

import com.student.searchroom.entity.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {
    Page<House> findAllByCreatedBy(String username, Pageable pageable);
}
