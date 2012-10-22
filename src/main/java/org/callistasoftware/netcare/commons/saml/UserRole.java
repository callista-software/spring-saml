package org.callistasoftware.netcare.commons.saml;

import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String name;
	
	public UserRole(final String roleName) {
		this.name = roleName;
	}
	
	@Override
	public String getAuthority() {
		return this.name;
	}

}
