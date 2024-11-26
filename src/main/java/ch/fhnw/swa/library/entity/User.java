package ch.fhnw.swa.library.entity;

import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class User {
	@Id
	private ObjectId id;

	private String username;

	private String password;

	private Set<UserRole> roles;

	private boolean active;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
