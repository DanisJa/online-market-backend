package com.onlinemarket.rest.dto.user;

import com.onlinemarket.core.model.User;
import com.onlinemarket.core.model.enums.UserType;

import java.util.Date;

public class UserRequestDTO {
    private String username, password, email;
    private UserType userType;

    public UserRequestDTO() {}

    public UserRequestDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }

    public User toEntity() {
        User user = new User();
        user.setEmail(this.email);
        user.setUsername(this.username);
        user.setUserType(this.userType);
        user.setCreatedAt(new Date());
        return user;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
