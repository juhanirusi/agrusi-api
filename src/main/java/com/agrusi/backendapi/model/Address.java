package com.agrusi.backendapi.model;

import com.agrusi.backendapi.enums.EAddressType;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "address")
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence")
    @SequenceGenerator(
            name = "address_sequence",
            sequenceName = "address_sequence",
            allocationSize = 1
    )
    @Column(updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "country", nullable = false)
    private String country;

    // Point is nullable by default...

    @Column(name = "location", columnDefinition = "geometry(Point, 4326)")
    private Point location;

    // Many-to-one relationship to Account
    // (nullable when assigning only a farm related addresses)

    @ManyToOne
    @JoinColumn(name = "fk_account_id")
    private Account account;

    // Many-to-one relationship to Farm
    // (nullable when assigning only a home/user related addresses)

    @ManyToOne
    @JoinColumn(name = "fk_farm_id")
    private Farm farm;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "address_types",
            joinColumns = @JoinColumn(name = "address_id")
    )
    @Column(name = "address_type")
    private Set<EAddressType> addressTypes = new HashSet<>();

    public Address() {
    }

    // Constructor with all the fields...

    public Address(
            Long id,
            String streetAddress,
            String city,
            String province,
            String postalCode,
            String country,
            Point location,
            Account account,
            Farm farm,
            boolean isDefault,
            Set<EAddressType> addressTypes
    ) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
        this.location = location;
        this.account = account;
        this.farm = farm;
        this.isDefault = isDefault;
        this.addressTypes = addressTypes;
    }

    // Constructor without Point and Farm...

    public Address(
            Long id,
            String streetAddress,
            String city,
            String province,
            String postalCode,
            String country,
            Account account,
            boolean isDefault,
            Set<EAddressType> addressTypes
    ) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
        this.account = account;
        this.isDefault = isDefault;
        this.addressTypes = addressTypes;
    }

    // Constructor without Point and Account...

    public Address(
            Long id,
            String streetAddress,
            String city,
            String province,
            String postalCode,
            String country,
            Farm farm,
            boolean isDefault,
            Set<EAddressType> addressTypes
    ) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
        this.farm = farm;
        this.isDefault = isDefault;
        this.addressTypes = addressTypes;
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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Set<EAddressType> getAddressTypes() {
        return addressTypes;
    }

    public void setAddressTypes(Set<EAddressType> addressTypes) {
        this.addressTypes = addressTypes;
    }
}
