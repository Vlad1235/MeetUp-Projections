package com.andersenlab.aadamovich.meetupprojection.projection.class_based;

import lombok.Value;

//Позволяет избежать стандартного, повторяемого кода
@Value
public class ClassBasedProjectionCar {

    String manufacturer;
    String model;
    String number;
}
