package com.agrusi.backendapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name = "account")
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @Column(updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "public_id", updatable = false, nullable = false, unique = true)
    private UUID publicId;

    @Size(
            min = 2,
            max = 255,
            message = "First name is required, maximum 255 characters."
    )
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(
            min = 2,
            max = 255,
            message = "Last name is required, maximum 255 characters."
    )
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "Please provide a valid email address.")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    // Store the phone number in E.164 format - the number starts
    // with a +, followed by digits, with a total length between 1 and
    // 15 digits, which adheres to the E.164 standard.

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    @Column(unique = true)
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_link_role",
            joinColumns = { @JoinColumn(name = "fk_account_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "fk_role_id", referencedColumnName = "id") }
    )
    private Set<Role> authorities = new HashSet<>();

    // Account owns the relationship

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id") // FK in Account
    private UserProfile userProfile;

    // Account owns the relationship

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_preferences_id", referencedColumnName = "id") // FK in Account
    private AccountPreferences accountPreferences;

    // mappedBy = "account", because "Account" is the owning entity !!!

    @OneToMany(
            mappedBy = "account", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true
    )
    private Set<Address> addresses = new HashSet<>();

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column(name = "phone_verified", nullable = false)
    private boolean phoneVerified = false;

    @CreationTimestamp
    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @PrePersist
    public void createPublicId() {

        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }

    public Account() {
    }

    public Account(
            Long id,
            String firstName,
            String lastName,
            String email,
            String password,
            Set<Role> authorities,
            boolean emailVerified,
            LocalDateTime dateCreated,
            LocalDateTime lastUpdated
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.emailVerified = emailVerified;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }

    public Account(
            Long id,
            UUID publicId,
            String firstName,
            String lastName,
            String email,
            String password,
            Set<Role> authorities,
            boolean emailVerified,
            LocalDateTime dateCreated,
            LocalDateTime lastUpdated
    ) {
        this.id = id;
        this.publicId = publicId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.emailVerified = emailVerified;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
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

    public UUID getPublicId() {
        return publicId;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public AccountPreferences getAccountPreferences() {
        return accountPreferences;
    }

    public void setAccountPreferences(AccountPreferences accountPreferences) {
        this.accountPreferences = accountPreferences;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
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

    // The account "ID" and the "public ID" need to be equal for the account to be same

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(getId(), account.getId()) &&
                Objects.equals(getPublicId(), account.getPublicId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPublicId());
    }
}
