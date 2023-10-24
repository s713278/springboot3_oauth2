package com.nbc.oauth2.rest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	
	
	@GetMapping(value="/login/oauth2/code/google")
	public ModelAndView defaultPage(@AuthenticationPrincipal OidcUser principal) {
		ModelAndView mav = new ModelAndView("userdetails");
		mav.addObject("user", principal);
		return mav;
	}

	@GetMapping(value={"/login","/"})
	public String login() {
		return "login";
	}
}