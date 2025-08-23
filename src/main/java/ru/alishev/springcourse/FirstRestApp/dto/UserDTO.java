package ru.alishev.springcourse.FirstRestApp.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserDTO {
    @NotEmpty(message = "Username не должно быть пустым!")
    @Size(min = 3, max = 50, message = "Username должно быть от 3 до 50 символов!")
    private String username;

    @NotEmpty(message = "Пароль не должен быть пустым!")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов!")
    private String password;

    @NotEmpty(message = "Роль не должна быть пустой!")
    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

