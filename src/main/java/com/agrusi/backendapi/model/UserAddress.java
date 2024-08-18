package com.agrusi.backendapi.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "user_address")
@Table(name = "user_address")
public class UserAddress extends Address {

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "fk_account_id")
    private Account account;

    public UserAddress(
            Long id,
            String streetAddress,
            String city,
            String province,
            String postalCode,
            String country,
            boolean isDefault,
            Account account
    ) {
        super(id, streetAddress, city, province, postalCode, country);
        this.isDefault = isDefault;
        this.account = account;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // The account "ID" and the "public ID" need to be equal for the account to be same

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAddress userAddress)) return false;
        return Objects.equals(getId(), userAddress.getId()) &&
                Objects.equals(getAccount(), userAddress.getAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccount());
    }
}
