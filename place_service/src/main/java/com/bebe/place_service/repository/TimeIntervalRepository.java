package com.bebe.place_service.repository;

import com.bebe.place_service.model.TimeInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeIntervalRepository extends JpaRepository<TimeInterval, Long> {
}
