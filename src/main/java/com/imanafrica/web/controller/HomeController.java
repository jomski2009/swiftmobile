package com.imanafrica.web.controller;

import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	Logger log = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping("/")
	public String index(Model model) {
		GregorianCalendar gc = new GregorianCalendar();
		String result = "Greetings from Iman Africa Mobile. The date is "
				+ gc.getTime().toString();
		model.addAttribute("testString", result);
		return "home";
	}
}
