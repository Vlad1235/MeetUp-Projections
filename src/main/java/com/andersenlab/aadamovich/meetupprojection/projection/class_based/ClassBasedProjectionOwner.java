package com.andersenlab.aadamovich.meetupprojection.projection.class_based;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassBasedProjectionOwner {

    private String firstName;
    private String lastName;

    //Имена параметров конструктора должны точно совпадать с названиями полей класса сущности
    public ClassBasedProjectionOwner(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
