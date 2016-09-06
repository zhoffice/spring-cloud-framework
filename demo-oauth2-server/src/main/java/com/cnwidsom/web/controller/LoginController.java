package com.cnwidsom.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@RequestMapping(value = "/authorize_callback")
	public ModelAndView authorizeCallback(@RequestParam(required = true) String code) {
		ModelAndView modelAndView = new ModelAndView("authorize_callback");
		modelAndView.addObject("code", code);
		return modelAndView;
	}

	@RequestMapping(value = "/security/authorize")
	public ModelAndView authorize(ModelAndView modelAndView) {
		return new ModelAndView("authorize");
	}

	// Spring Security see this :
	@RequestMapping(value = "/security/login")
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "success", required = false) String success) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}

		if (success != null) {
			model.addObject("msg", "You've been log in successfully.");
		}
		model.setViewName("login");

		return model;

	}
}
