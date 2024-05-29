package com.agrusi.backendapi.repository;

import com.agrusi.backendapi.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long> {
}
