package com.imanafrica.rest.v1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.imanafrica.core.domain.SmsResponse;
import com.imanafrica.core.services.SmsService;
import com.imanafrica.rest.dto.SmsDTO;

@RestController
@RequestMapping("api/v1/sms")
public class SmsRestController {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SmsService smsService;

	@RequestMapping(value = "sendsingle", consumes = { "application/json" }, produces = { "application/json" })
	@ResponseBody
	public Map<String, Object> sendSMS(@RequestBody SmsDTO sms,
			@RequestHeader(value = "Authorization") String authString) {
		Map<String, Object> results = new HashMap<String, Object>();

		List<SmsResponse> response = smsService.sendSms(sms,
				SwiftMobileUtils.getUsername(authString));

		log.info("The returned data is : " + response.toString());

		results.put("response", response);
		return results;
	}

}
