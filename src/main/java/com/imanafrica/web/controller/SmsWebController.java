package com.imanafrica.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imanafrica.core.domain.Sms;

@Controller
public class SmsWebController {
	
	@RequestMapping(value="/send/sms", method=RequestMethod.GET)
	public String sendSMSForm(Model model){
		model.addAttribute("sms", new Sms());
		
		return "sendsms";
	}
}
