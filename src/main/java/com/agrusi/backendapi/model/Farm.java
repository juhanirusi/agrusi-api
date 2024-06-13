package com.agrusi.backendapi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "farm", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true
    )
    private Set<Field> fields = new HashSet<>();

    @CreationTimestamp
    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @PrePersist
    public void prePersist() {

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

    public Set<Field> getFields() {
        return fields;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
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
