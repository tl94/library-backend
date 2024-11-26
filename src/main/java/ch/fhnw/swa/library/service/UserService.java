package ch.fhnw.swa.library.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.fhnw.swa.library.entity.User;
import ch.fhnw.swa.library.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User createUser(User user) {
		String password = user.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(password);
		user.setPassword(encodedPassword);
		userRepository.save(user);
		user.eraseCredentials();
		return user;
	}
}
