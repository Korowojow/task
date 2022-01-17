package com.kg.task.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientId;
    @NotNull
    private String address;
    @NotNull
    private Date birthday;
    @NotNull
    private int creditCard;
    @OneToMany(mappedBy = "client", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    //@JsonIgnore
    private List<Rental> rentals;

    @Version
    @Column(name = "version")
    private long version;

    public Client(String address, Date birthday, int creditCard) {
        this.address = address;
        this.birthday = birthday;
        this.creditCard = creditCard;
    }
}
