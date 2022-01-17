package com.kg.task.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rentalId;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_facility_id")
    private Facility rentalFacility;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "return_facility_id")
    private Facility returnFacility;
    @NotNull
    private int depositAmount;

    @Version
    @Column(name = "version")
    private long version;

    public Rental(Date startDate, Date endDate, int depositAmount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.depositAmount = depositAmount;
    }
}
