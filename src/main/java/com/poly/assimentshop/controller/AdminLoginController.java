package com.poly.assimentshop.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.poly.assimentshop.domain.Account;
import com.poly.assimentshop.domain.Customer;
import com.poly.assimentshop.model.AdminLoginDto;
import com.poly.assimentshop.service.AccountService;

import groovyjarjarpicocli.CommandLine.Model;

@Controller
public class AdminLoginController {
	@Autowired
private AccountService accountService;
@Autowired
private HttpSession session;

@GetMapping("alogin")
public String login(ModelMap model) {
	model.addAttribute("account", new AdminLoginDto());
	return "/admin/accounts/login";
}

@PostMapping("alogin")
public ModelAndView login(ModelMap model,
		@Valid @ModelAttribute("account") AdminLoginDto dto,
		BindingResult result) {
	if (result.hasErrors()) {
		return new ModelAndView("/admin/accounts/login", model);
	}
	Account account = accountService.login(dto.getUsername(), dto.getPassword());
	if(account == null) {
		model.addAttribute("message", "Request to re-enter");
		return new ModelAndView("/admin/accounts/login", model);
	}
	session.setAttribute("username", account.getUsername());
	Object ruri = session.getAttribute("suri");
	if(ruri != null) {
		session.removeAttribute("suri");
		return new ModelAndView("redirect:" + ruri);
	}
	
	
	
	return new ModelAndView("forward:/admin/accounts", model);
}
}