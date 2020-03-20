package com.andersenlab.aadamovich.meetupprojection.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String manufacturer;
    private String model;
    private String number;

    @OneToOne
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    private Owner owner;
}
