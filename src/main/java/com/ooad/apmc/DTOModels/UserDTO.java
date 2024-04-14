package com.ooad.apmc.DTOModels;

import com.ooad.apmc.Models.User;

public class UserDTO {

    private Long userId;
    private String username;
    private String email;
    private String password;

    // private String role;

    public UserDTO() {
    }

    public UserDTO(Long userId, String userName, String email) {
        this.userId = userId;
        this.username = userName;
        this.email = email;
        // this.role = role;
    }

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // public String getRole() {
    //     return role;
    // }

    // public void setRole(String role) {
    //     this.role = role;
    // }

    public static UserDTO mapEntityToDto(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail()

        );
    }
}
