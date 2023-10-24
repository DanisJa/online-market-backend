package com.onlinemarket.rest.dto.user;

import com.onlinemarket.core.model.User;

public class UserRequestDTO {
    private String username, password, email;

    public UserRequestDTO() {}

    public UserRequestDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
