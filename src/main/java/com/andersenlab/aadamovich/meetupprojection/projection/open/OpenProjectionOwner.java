package com.andersenlab.aadamovich.meetupprojection.projection.open;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface OpenProjectionOwner {

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();

    List<OpenProjectionCar> getCars();
}
