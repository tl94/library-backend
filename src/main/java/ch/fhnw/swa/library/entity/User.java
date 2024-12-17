package ch.fhnw.swa.library.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails, CredentialsContainer {

	private static final long serialVersionUID = -7972231862009567627L;

	@Id
	private ObjectId id;

	private String username;

	private String password;

	private List<String> roles;

	private boolean isAccountNonLocked;

	private boolean isAccountNonExpired;

	private boolean isCredentialsNonExpired;

	private boolean isEnabled;

	// No-args constructor required by MongoDB
	public User() {}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.roles = new ArrayList<String>();
		roles.add("USER");
		this.isAccountNonLocked = true;
		this.isAccountNonExpired = true;
		this.isCredentialsNonExpired = true;
		this.isEnabled = true;
	}

	@Override
	public void eraseCredentials() {
		this.password = null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
	}
	
	public boolean addRole(String role) {
		if (!roles.contains(role)) {
			return roles.add(role);
		} else {
			return false;
		}
	}

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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
