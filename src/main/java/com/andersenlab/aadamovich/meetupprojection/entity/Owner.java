package com.andersenlab.aadamovich.meetupprojection.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private Car car;

    public void addCarToOwner(Car car) {
        this.car = car;
        car.setOwner(this);
    }
   }
