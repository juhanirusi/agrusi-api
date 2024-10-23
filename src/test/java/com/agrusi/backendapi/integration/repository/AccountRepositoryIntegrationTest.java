package com.agrusi.backendapi.integration.repository;

import com.agrusi.backendapi.IntegrationTest;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @IntegrationTest --> Our custom annotation allowing us to run only
 * integration tests if we want to
*/

@IntegrationTest
@DataJpaTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class AccountRepositoryIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    private String existingEmail;

    private String nonExistingEmail;

    @BeforeEach
    public void setUp() {

        existingEmail = "jack.farmer@example.com";
        String firstName = "Jack";
        String lastName = "Farmer";
        String password = "password123";

        nonExistingEmail = "nonexistent@example.com";

        account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(existingEmail);
        account.setPassword(password);

        accountRepository.save(account);
    }

    @Test
    @DisplayName("Get an account by email.")
    public void testFindAccountByEmail() {

        Optional<Account> foundAccount = accountRepository.findAccountByEmail(existingEmail);

        assertTrue(foundAccount.isPresent());
        assertEquals(account.getEmail(), foundAccount.get().getEmail());
    }

    @Test
    @DisplayName("Account not found by email.")
    public void testFindAccountByEmail_NotFound() {

        Optional<Account> foundAccount = accountRepository.findAccountByEmail(nonExistingEmail);

        assertTrue(foundAccount.isEmpty());
    }

    @Test
    @DisplayName("Get an account by public ID.")
    public void testFindAccountByPublicId() {

        Optional<Account> foundAccount = accountRepository.findAccountByPublicId(account.getPublicId());

        assertTrue(foundAccount.isPresent());
        assertEquals(account.getPublicId(), foundAccount.get().getPublicId());
    }

    @Test
    @DisplayName("Account not found by public ID.")
    public void testFindAccountByPublicId_NotFound() {

        Optional<Account> foundAccount = accountRepository.findAccountByPublicId(UUID.randomUUID());

        assertTrue(foundAccount.isEmpty());
    }

    @Test
    @DisplayName("Account exists by email.")
    public void testExistsByEmail() {

        Boolean exists = accountRepository.existsByEmail(existingEmail);

        assertTrue(exists);
    }

    @Test
    @DisplayName("Account doesn't exists by email")
    public void testExistsByEmail_NonExistingAccount() {

        Boolean exists = accountRepository.existsByEmail(nonExistingEmail);

        assertFalse(exists);
    }
}
