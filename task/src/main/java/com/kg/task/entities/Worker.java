package com.kg.task.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kg.task.utils.WorkerPosition;
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
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long workerId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private WorkerPosition position;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "worker_car", joinColumns = @JoinColumn(name = "worker_id"), inverseJoinColumns = @JoinColumn(name = "car_id"))
    @JsonIgnore
    private List<Car> cars;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @Version
    @Column(name = "version")
    private long version;

    public Worker(WorkerPosition position) {
        this.position = position;
    }
}
