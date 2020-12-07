package com;

import java.io.Serializable;
    
        /**
	 * A class that have properties and setter and getter methods.
	 */

public class LoginBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
}