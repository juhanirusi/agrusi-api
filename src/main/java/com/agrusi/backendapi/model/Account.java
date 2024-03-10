package com.agrusi.backendapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = "account")
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Change to UUID?
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_verified")
    private boolean isVerified;

    @CreationTimestamp
    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public Account() {
    }

    public Account(
            String email, String password, boolean isVerified,
            LocalDateTime dateCreated, LocalDateTime lastUpdated
    ) {
        this.email = email;
        this.password = password;
        this.isVerified = isVerified;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    /*
    * https://stackoverflow.com/questions/12369641/is-there-any-reason-to-not-generate-setters-and-getters-for-id-fields-in-jpa
    *
    * To setter method I give protected access. I just don't want any
    * other pieces of code to have easy write access to such
    * an important field.
    */

    protected void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

