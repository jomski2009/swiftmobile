package com.imanafrica.core.smsproviders;

import java.util.List;

import com.imanafrica.core.domain.Sms;
import com.imanafrica.core.domain.SmsResponse;

public interface SmsProvider {

	public List<SmsResponse> sendSMS(Sms sms);

	public double getCredits();

	public List<SmsResponse> sendJsonSMS(Sms sms);

}
