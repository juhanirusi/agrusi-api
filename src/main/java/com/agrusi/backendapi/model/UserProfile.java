package com.agrusi.backendapi.model;

import jakarta.persistence.*;

@Entity(name = "user_profile")
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_sequence")
    @SequenceGenerator(
            name = "user_profile_sequence",
            sequenceName = "user_profile_sequence",
            allocationSize = 1
    )
    @Column(updatable = false, nullable = false, unique = true)
    private Long id;

    // UserProfile is the inverse side, referencing Account

    @OneToOne(mappedBy = "userProfile") // No FK column here
    private Account account;

    public UserProfile() {
    }

    public UserProfile(
            Long id,
            Account account
    ) {
        this.id = id;
        this.account = account;
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
}
