package com.agrusi.backendapi.repository;

import com.agrusi.backendapi.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    Boolean existsByPublicId(UUID publicId);

    Optional<Farm> findByPublicId(UUID publicId);
}
