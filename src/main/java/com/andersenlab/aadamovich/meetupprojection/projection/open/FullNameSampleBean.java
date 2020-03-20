package com.andersenlab.aadamovich.meetupprojection.projection.open;

import com.andersenlab.aadamovich.meetupprojection.entity.Owner;
import org.springframework.stereotype.Component;

@Component
public class FullNameSampleBean {
    public String getFullName(Owner owner) {
        return owner.getFirstName() + ' ' + owner.getLastName();
    }

}
