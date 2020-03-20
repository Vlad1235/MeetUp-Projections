package com.andersenlab.aadamovich.meetupprojection.projection.closed;

public interface ClosedProjectionCar {

    //Названия методов должны точно совпадать с названиями полей класса сущности
    String getManufacturer();

    String getNumber();

    ClosedProjectionOwner getOwner();
}
