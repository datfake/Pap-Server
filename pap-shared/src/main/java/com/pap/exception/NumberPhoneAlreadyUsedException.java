package com.pap.exception;

public class NumberPhoneAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public NumberPhoneAlreadyUsedException() {
        super(ErrorConstants.NUMBER_PHONE_ALREADY_USED_TYPE, "Number phone already used!", "userManagement", "userexists");
    }
}
