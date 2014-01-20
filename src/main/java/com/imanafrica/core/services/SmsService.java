package com.imanafrica.core.services;

import java.util.List;

import com.imanafrica.core.domain.Sms;
import com.imanafrica.core.domain.SmsResponse;
import com.imanafrica.rest.dto.SmsDTO;

public interface SmsService {
	
	public List<SmsResponse> sendSms(Sms sms, String username);
	
	public double getCredits();

	public List<SmsResponse> sendSms(SmsDTO sms, String username);

}
