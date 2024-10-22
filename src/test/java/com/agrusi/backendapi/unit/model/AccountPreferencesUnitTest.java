package com.agrusi.backendapi.unit.model;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.model.AccountPreferences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
public class AccountPreferencesUnitTest {

    private Account account;
    private AccountPreferences accountPreferences;

    @BeforeEach
    public void setUp() {
        account = new Account();

        accountPreferences = new AccountPreferences();
    }

    @Test
    @DisplayName("Test creating account preferences for account.")
    public void testCreateAccountPreferencesForAccount() {

        String languageCode = "fi";
        String currencyCode = "EUR";
        String timeZone = "Europe/Helsinki";
        EAreaUnit fieldAreaUnit = EAreaUnit.HECTARE;

        accountPreferences.setAccount(account);
        accountPreferences.setLanguage(languageCode);
        accountPreferences.setCurrency(currencyCode);
        accountPreferences.setTimeZone(timeZone);
        accountPreferences.setFieldAreaUnit(fieldAreaUnit);

        assertEquals(account, accountPreferences.getAccount());
        assertEquals(languageCode, accountPreferences.getLanguage());
        assertEquals(currencyCode, accountPreferences.getCurrency());
        assertEquals(timeZone, accountPreferences.getTimeZone());
        assertEquals(fieldAreaUnit.getUnitOfArea(), accountPreferences.getFieldAreaUnit().getUnitOfArea());
    }
}
