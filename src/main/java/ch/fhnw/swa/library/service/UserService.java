package ch.fhnw.swa.library.service;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ch.fhnw.swa.library.entity.UserDTO;
import ch.fhnw.swa.library.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User %s not found", username));
		}
		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
				.password(user.getPassword()).authorities(user.getAuthorities()).build();
	}

	public boolean createUser(UserDTO user) {
		User prevUser = userRepository.findByUsername(user.username());
		if (prevUser != null) {
			return false;
		} else {
			String password = user.password();
			String encodedPassword = passwordEncoder.encode(password);
			ArrayList<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("USER"));
			User newUser = new User(user.username(), encodedPassword, authorities);
			userRepository.save(newUser);
			newUser.eraseCredentials();
			return true;
		}
	}


}
