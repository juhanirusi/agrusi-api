package com.agrusi.backendapi.repository;

import com.agrusi.backendapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByEmail(String email);

    Optional<Account> findAccountByPublicId(UUID publicId);

    Boolean existsByEmail(String email);

    /*
    * Optional<MyEntity> findById(Long id);
    *
    * List<MyEntity> findByName(String name);
    *
    * List<MyEntity> findByAgeGreaterThan(int age);
    *
    * List<MyEntity> findByStatus(String status);
    */
}
