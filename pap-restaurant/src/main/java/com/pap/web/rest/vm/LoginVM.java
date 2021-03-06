package com.pap.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * View Model object for storing a manager restaurant's credentials.
 */
public class LoginVM {

    @NotNull
    @Size(min = 1, max = 50)
    private String phone;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    private Boolean rememberMe;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "LoginVM{" +
            "phone='" + phone + '\'' +
            ", password='" + password + '\'' +
            ", rememberMe=" + rememberMe +
            '}';
    }
}
