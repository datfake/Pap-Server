package com.pap.service.dto;

import com.pap.service.dto.CourierDTO;
import javax.validation.constraints.Size;

/**
 * View Model extending the CourierDTO, which is meant to be used in the user management UI.
 */
public class CourierVM extends CourierDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public CourierVM() {
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
        return "CourierVM{" + super.toString() + "} ";
    }
}