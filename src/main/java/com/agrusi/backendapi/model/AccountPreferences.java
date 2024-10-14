package com.agrusi.backendapi.model;

import com.agrusi.backendapi.enums.*;
import jakarta.persistence.*;

@Entity(name = "account_preferences")
@Table(name = "account_preferences")
public class AccountPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_preferences_sequence")
    @SequenceGenerator(
            name = "account_preferences_sequence",
            sequenceName = "account_preferences_sequence",
            allocationSize = 1
    )
    @Column(updatable = false, nullable = false, unique = true)
    private Long id;

    // AccountPreferences is the inverse side, referencing Account
    @OneToOne(mappedBy = "accountPreferences") // No FK column here
    private Account account;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "time_zone", nullable = false)
    private String timeZone;

    @Enumerated(EnumType.STRING)
    private EAreaUnit fieldAreaUnit;

    public AccountPreferences() {
    }

    public AccountPreferences(
            Long id,
            Account account,
            String language,
            String currency,
            String timeZone,
            EAreaUnit fieldAreaUnit
    ) {
        this.id = id;
        this.account = account;
        this.language = language;
        this.currency = currency;
        this.timeZone = timeZone;
        this.fieldAreaUnit = fieldAreaUnit;
    }

    /*
     * Primary key values never change, so we shouldn't allow the
     * identifier property value to be modified. Hibernate and Spring
     * Data JPA using Hibernate as a provider won’t update a primary
     * key column, and you shouldn't expose a public identifier
     * setter method on an entity!
    */

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public EAreaUnit getFieldAreaUnit() {
        return fieldAreaUnit;
    }

    public void setFieldAreaUnit(EAreaUnit fieldAreaUnit) {
        this.fieldAreaUnit = fieldAreaUnit;
    }
}
