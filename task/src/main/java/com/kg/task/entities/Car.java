package com.kg.task.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carId;
    @NotNull
    private String type;
    @NotNull
    private String carMake;
    @NotNull
    private String color;
    @NotNull
    private Date productionYear;
    @NotNull
    private String engineCapacity;
    @NotNull
    private String power;
    @NotNull
    private String mileage;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "worker_car", joinColumns = @JoinColumn(name = "car_id"), inverseJoinColumns = @JoinColumn(name="worker_id"))
    @JsonIgnore
    private List<Worker> workers;

    @OneToMany(mappedBy = "car", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    //@JsonIgnore
    private List<Rental> rentals;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    @Version
    @Column (name = "version")
    private long version;

    public Car(String type, String color, Date productionYear, String engineCapacity, String power, String mileage, String carMake) {
        this.type = type;
        this.color = color;
        this.productionYear = productionYear;
        this.engineCapacity = engineCapacity;
        this.power = power;
        this.mileage = mileage;
        this.carMake = carMake;
    }
}
