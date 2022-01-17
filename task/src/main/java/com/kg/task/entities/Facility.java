package com.kg.task.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long facilityId;
    @NotNull
    private String address;
    @NotNull
    private String contact;

    @OneToMany(mappedBy = "facility", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    //@JsonIgnore
    private List<Worker> workers;

    @OneToMany(mappedBy = "facility", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    //@JsonIgnore
    private List<Car> cars;

    @OneToMany(mappedBy = "rentalFacility", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    //@JsonIgnore
    private List<Rental> rentals;

    @OneToMany(mappedBy = "returnFacility", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    //@JsonIgnore
    private List<Rental> returns;

    @Version
    @Column(name = "version")
    private long version;

    public Facility(String address, String contact) {
        this.address = address;
        this.contact = contact;
    }
}
