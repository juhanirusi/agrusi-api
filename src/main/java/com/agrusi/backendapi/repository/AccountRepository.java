package com.agrusi.backendapi.repository;

import com.agrusi.backendapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> { }
