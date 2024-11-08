package com.agrusi.backendapi.repository;

import com.agrusi.backendapi.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    Boolean existsByPublicId(UUID farmPublicId);

    Optional<Farm> findByPublicId(UUID farmPublicId);

    @Query("SELECT COALESCE(SUM(f.size), 0) FROM field f WHERE f.farm.id = :farmId")
    BigDecimal calculateTotalLandAreaOfAllFields(@Param("farmId") Long farmId);
}
