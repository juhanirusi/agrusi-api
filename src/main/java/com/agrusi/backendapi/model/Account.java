package com.agrusi.backendapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity(name = "account")
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    @Size(
            min = 2,
            max = 255,
            message = "First name is required, maximum 255 characters."
    )
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(
            min = 2,
            max = 255,
            message = "Last name is required, maximum 255 characters."
    )
    private String lastName;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_link_role",
            joinColumns = { @JoinColumn(name = "fk_account_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "fk_role_id", referencedColumnName = "id") }
    )
    private Set<Role> authorities;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    @CreationTimestamp
    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    public Account() {
    }

    public Account(
            UUID id,
            String firstName,
            String lastName,
            String email,
            String password,
            Set<Role> authorities,
            boolean isVerified,
            LocalDateTime dateCreated,
            LocalDateTime lastUpdated
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.isVerified = isVerified;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }

    public UUID getId() {
        return id;
    }

    /*
    * Primary key values never change, so you shouldn't allow the
    * identifier property value to be modified. Hibernate and Spring
    * Data JPA using Hibernate as a provider won’t update a primary
    * key column, and you shouldn't expose a public identifier
    * setter method on an entity!
    */

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
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

    // Get user's full name

    public String getFullName() {
        return firstName + " " + lastName;
    }
}

