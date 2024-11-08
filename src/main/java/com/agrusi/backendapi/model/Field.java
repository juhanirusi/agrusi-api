package com.agrusi.backendapi.model;

import com.agrusi.backendapi.enums.ESoilType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "field")
@Table(name = "field")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "field_sequence")
    @SequenceGenerator(
            name = "field_sequence",
            sequenceName = "field_sequence",
            allocationSize = 1
    )
    @Column(updatable = false, nullable = false, unique = true)
    private Long id;

    @Size(
            min = 2,
            max = 255,
            message = "Field name is required, maximum 255 characters."
    )
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "area", columnDefinition = "geometry(Polygon, 4326)", nullable = false)
    private Polygon area;

    @Column(name = "center", columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point center;

    @Column(name = "size")
    private BigDecimal size;

    @Enumerated(EnumType.STRING)
    @Column(name = "soil_type") // Nullable by default if farmer doesn't want to input it !!!
    private ESoilType soilType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_farm_id")
    private Farm farm;

    public Field() {
    }

    public Field(
            Long id,
            String name,
            Polygon area,
            Point center,
            BigDecimal size,
            Farm farm
    ) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.center = center;
        this.size = size;
        this.farm = farm;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Polygon getArea() {
        return area;
    }

    public void setArea(Polygon area) {
        this.area = area;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public ESoilType getSoilType() {
        return soilType;
    }

    public void setSoilType(ESoilType soilType) {
        this.soilType = soilType;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field field)) return false;
        return Objects.equals(getId(), field.getId()) &&
                Objects.equals(getFarm(), field.getFarm());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFarm());
    }
}
