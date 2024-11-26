package ch.fhnw.swa.library.controller;

import org.springframework.web.bind.annotation.RestController;

import ch.fhnw.swa.library.service.UserService;

@RestController
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
}
