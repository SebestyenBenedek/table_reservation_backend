package com.bebe.place_service.repository;

import com.bebe.place_service.model.PlaceTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceTableRepository extends JpaRepository<PlaceTable, Long> {
}
