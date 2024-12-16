package ch.fhnw.swa.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.fhnw.swa.library.entity.UserDTO;
import ch.fhnw.swa.library.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping()
	public ResponseEntity<Void> createUser(@RequestBody UserDTO user) {
		if (isUserValid(user)) {
			boolean userCreated = userService.createUser(user);
			if (userCreated) {
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	private boolean isUserValid(UserDTO user) {
		String username = user.username();
		String password = user.password();
		if (username.isBlank() || password.isBlank()) {
			return false;
		} else {
			return true;
		}
	}
}
