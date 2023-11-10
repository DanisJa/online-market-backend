package com.onlinemarket.rest.dto.user;

public class UserLoginDTO {
    private String jwt;

    public UserLoginDTO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
