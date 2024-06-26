package com.agrusi.backendapi.model;

import com.agrusi.backendapi.enums.EAccountRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity(name = "role")
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1
    )
    @Column(updatable = false, nullable = false, unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private EAccountRole authority;

    public Role() {
    }

    public Role(EAccountRole authority) {
        this.authority = authority;
    }

    public Role(Long id, EAccountRole authority) {
        this.id = id;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    /*
     * Primary key values never change, so you shouldn't allow the
     * identifier property value to be modified. Hibernate and Spring
     * Data JPA using Hibernate as a provider won’t update a primary
     * key column, and you shouldn't expose a public identifier
     * setter method on an entity!
    */

    @Override
    public String getAuthority() {
        return authority.toString();
    }

    public void setAuthority(EAccountRole authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
