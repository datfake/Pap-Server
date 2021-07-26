package com.pap.service.dto;

import javax.validation.constraints.NotBlank;

public class OtpDTO {

    @NotBlank
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
