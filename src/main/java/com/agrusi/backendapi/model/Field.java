package com.agrusi.backendapi.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

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

    @Column(name = "center", columnDefinition = "geometry(Point, 4326)")
    private Point center;

    @Column(name = "field_size")
    private Double fieldSize;

    public Field() {
    }

    public Field(
            Long id, String name, Polygon area,
            Point center, Double fieldSize
    ) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.center = center;
        this.fieldSize = fieldSize;
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

    public Double getFieldSize() {
        return fieldSize;
    }

    public void setFieldSize(Double fieldSize) {
        this.fieldSize = fieldSize;
    }
}
