package com.andersenlab.aadamovich.meetupprojection.projection.open;

import org.springframework.beans.factory.annotation.Value;

public interface OpenProjectionCar {

    @Value("#{target.manufacturer + ' PROJECTION!'}")
    String getManufacturerEdited();

    String getNumber();

    OpenProjectionOwner getOwner();
}
