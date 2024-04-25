package com.agrusi.backendapi.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity(name = "role")
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incremented database column
    @Column(name = "role_id", updatable = false, nullable = false, unique = true)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private EAccountRole authority;

    public Role() {
    }

    public Role(EAccountRole authority) {
        this.authority = authority;
    }

    public Role(Integer id, EAccountRole authority) {
        this.id = id;
        this.authority = authority;
    }

    public Integer getId() {
        return id;
    }

    /*
     * https://stackoverflow.com/questions/12369641/is-there-any-reason-to-not-generate-setters-and-getters-for-id-fields-in-jpa
     *
     * To setter method I give protected access. I just don't want any
     * other pieces of code to have easy write access to such
     * an important field.
    */

    protected void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority.toString();
    }

    public void setAuthority(EAccountRole authority) {
        this.authority = authority;
    }
}
