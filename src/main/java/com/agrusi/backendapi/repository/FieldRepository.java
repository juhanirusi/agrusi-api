package com.agrusi.backendapi.repository;

import com.agrusi.backendapi.model.Farm;
import com.agrusi.backendapi.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

    Optional<Field> findByIdAndFarm(Long id, Farm farm);
}
