package com.agrusi.backendapi.model;

import com.agrusi.backendapi.enums.EFarmType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name = "farm")
@Table(name = "farm")
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farm_sequence")
    @SequenceGenerator(
            name = "farm_sequence",
            sequenceName = "farm_sequence",
            allocationSize = 1
    )
    @Column(updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "public_id", updatable = false, nullable = false, unique = true)
    private UUID publicId;

    @Size(
            min = 2,
            max = 255,
            message = "Farm name is required, maximum 255 characters."
    )
    @Column(name = "name", nullable = false)
    private String name;

    // Collection of different farm types the farm belongs to...

    @ElementCollection(targetClass = EFarmType.class)
    @CollectionTable(
            name = "farm_types", // old --> farm_farm_type
            joinColumns = @JoinColumn(name = "farm_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "farm_type")
    private Set<EFarmType> farmTypes = new HashSet<>();

    // mappedBy = "farm", because "Farm" is the owning entity !!!

    @OneToMany(
            mappedBy = "farm", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true
    )
    private Set<Field> fields = new HashSet<>();

    // mappedBy = "farm", because "Farm" is the owning entity !!!

    @OneToMany(
            mappedBy = "farm", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true
    )
    private Set<Address> addresses = new HashSet<>();

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

    public Farm() {
    }

    public Farm(
            Long id,
            UUID publicId,
            String name,
            Set<Field> fields,
            Set<Address> addresses,
            LocalDateTime dateCreated,
            LocalDateTime lastUpdated
    ) {
        this.id = id;
        this.publicId = publicId;
        this.name = name;
        this.fields = fields;
        this.addresses = addresses;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }

    public Farm(
            Long id,
            String name,
            LocalDateTime dateCreated,
            LocalDateTime lastUpdated
    ) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }

    public Farm(
            Long id,
            UUID publicId,
            String name,
            LocalDateTime dateCreated,
            LocalDateTime lastUpdated
    ) {
        this.id = id;
        this.publicId = publicId;
        this.name = name;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
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

    public UUID getPublicId() {
        return publicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EFarmType> getFarmTypes() {
        return farmTypes;
    }

    public void setFarmTypes(Set<EFarmType> farmTypes) {
        this.farmTypes = farmTypes;
    }

    public Set<Field> getFields() {
        return fields;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Farm farm)) return false;
        return Objects.equals(getId(), farm.getId()) &&
                Objects.equals(getPublicId(), farm.getPublicId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPublicId());
    }
}
