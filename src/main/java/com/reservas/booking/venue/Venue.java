package com.reservas.booking.venue;

import com.reservas.booking.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "venues")
public class Venue extends BaseEntity {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String address;

    @NotBlank
    @Column(nullable = false)
    private String zipCode;

    @NotNull
    @Column(nullable = false)
    private Integer capacity;

    protected Venue(){

    }

    public Venue(String name, String address, String zipCode, Integer capacity) {
        if (capacity <= 0){
            throw new IllegalArgumentException("capacity precisa ser maior que zero");
        }

        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
