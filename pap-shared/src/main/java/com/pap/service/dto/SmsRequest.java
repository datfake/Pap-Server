package com.pap.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class SmsRequest {

	@NotBlank
	private String mobileNumber;
	@NotBlank
	private String message;


	public SmsRequest(@JsonProperty("mobileNumber")String mobileNumber,
                      @JsonProperty("message")String message) {
		this.mobileNumber = mobileNumber;
		this.message = message;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "SmsRequest [mobileNumber=" + mobileNumber + ", message=" + message + "]";
	}

}
