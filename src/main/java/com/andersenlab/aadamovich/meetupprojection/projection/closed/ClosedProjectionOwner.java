package com.andersenlab.aadamovich.meetupprojection.projection.closed;

public interface ClosedProjectionOwner {

    String getFirstName();

// Так работать не будет (getCar() вернет null)! Это не владеющая сторона в отношении @OneToOne
    ClosedProjectionCar getCar();

    // Так работает, но при этом использует механизм работы открытой проекции
//    List<ClosedProjectionCar> getCar();
}
