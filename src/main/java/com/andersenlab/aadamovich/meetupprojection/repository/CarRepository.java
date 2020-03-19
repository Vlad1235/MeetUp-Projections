package com.andersenlab.aadamovich.meetupprojection.repository;

import com.andersenlab.aadamovich.meetupprojection.entity.Car;
import com.andersenlab.aadamovich.meetupprojection.projection.class_based.ClassBasedProjectionCar;
import com.andersenlab.aadamovich.meetupprojection.projection.closed.ClosedProjectionCar;
import com.andersenlab.aadamovich.meetupprojection.projection.open.OpenProjectionCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository <Car, Long> {

    List<ClosedProjectionCar> findClosedProjectionBy();

    List<OpenProjectionCar> findOpenProjectionBy();

    List<ClassBasedProjectionCar> findClassBasedBy();
}
