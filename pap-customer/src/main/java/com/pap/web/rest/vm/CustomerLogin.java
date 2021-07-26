package com.pap.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * View Model object for storing a user's credentials.
 */
public class CustomerLogin {

    @NotNull
    @Size(min = 1, max = 50)
    private String phone;

    @NotNull
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    // prettier-ignore

    @Override
    public String toString() {
        return "CustomerLogin{" +
            "phone='" + phone + '\'' +
            '}';
    }
}
