package com.agrusi.backendapi.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.math.BigDecimal;

@Entity(name = "field")
@Table(name = "field")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "area", columnDefinition = "geometry(Polygon, 4326)", nullable = false)
    private Polygon area;

    @Column(name = "center", columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point center;

    @Column(name = "size")
    private BigDecimal size;

    @ManyToOne
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

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
