package com.agrusi.backendapi.integration.repository;

import com.agrusi.backendapi.IntegrationTest;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * @IntegrationTest --> Our custom annotation allowing us to run only
 * integration tests if we want to
 *
 * @DirtiesContext --> Allowing us to tell Spring to close and re-create
 * the ApplicationContext for each test class or method, effectively
 * resetting the database, because we had problems with tests
 * failing due to already existing entities.
*/

@IntegrationTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DataJpaTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class AccountRepositoryIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void setUp() {

        account = new Account();
        account.setFirstName("Jack");
        account.setLastName("Farmer");
        account.setEmail("jack.farmer@example.com");
        account.setPassword("password123");
        accountRepository.save(account);
    }

    @Test
    @DisplayName("Find account by email.")
    public void testFindAccountByEmail() {

        Optional<Account> foundAccount = accountRepository.findAccountByEmail("jack.farmer@example.com");

        assertTrue(foundAccount.isPresent());
        assertEquals(account.getEmail(), foundAccount.get().getEmail());
    }

    @Test
    @DisplayName("Find account by public ID.")
    public void testFindAccountByPublicId() {

        Optional<Account> foundAccount = accountRepository.findAccountByPublicId(account.getPublicId());

        assertTrue(foundAccount.isPresent());
        assertEquals(account.getPublicId(), foundAccount.get().getPublicId());
    }

    @Test
    @DisplayName("Test that account exists by email.")
    public void testExistsByEmail() {

        Boolean exists = accountRepository.existsByEmail("jack.farmer@example.com");

        assertTrue(exists);
    }
}
