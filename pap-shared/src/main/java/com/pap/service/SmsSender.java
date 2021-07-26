package com.pap.service;

import com.pap.service.dto.SmsRequest;

public interface SmsSender {

	void sendSms(SmsRequest smsRequest);

}
