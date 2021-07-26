package com.pap.service;

import com.pap.service.dto.SmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service()
public class SmsService {

	private SmsSender smsSender;

	@Autowired
	public SmsService(@Qualifier("twilio")SmsSender smsSender) {
		this.smsSender = smsSender;
	}

	public void sendSms(SmsRequest smsRequest) {
		smsSender.sendSms(smsRequest);
	}

}
