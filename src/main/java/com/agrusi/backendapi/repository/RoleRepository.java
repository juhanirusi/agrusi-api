package com.agrusi.backendapi.repository;

import com.agrusi.backendapi.model.EAccountRole;
import com.agrusi.backendapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByAuthority(EAccountRole name);
}
