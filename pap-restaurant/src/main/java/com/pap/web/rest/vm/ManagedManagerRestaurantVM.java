package com.pap.web.rest.vm;

import com.pap.service.dto.ManagerRestaurantDTO;

import javax.validation.constraints.Size;

/**
 * View Model extending the ManagerRestaurantDTO, which is meant to be used in the user management UI.
 */
public class ManagedManagerRestaurantVM extends ManagerRestaurantDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedManagerRestaurantVM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManagedManagerRestaurantVM{" + super.toString() + "} ";
    }
}
