package com.agrusi.backendapi.repository;

import com.agrusi.backendapi.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field, Long> {
}
