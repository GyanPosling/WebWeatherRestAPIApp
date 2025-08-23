package ru.alishev.springcourse.FirstRestApp.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "MyUser")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    @NotEmpty(message = "Username не должно быть пустым!")
    @Size(min = 3, max = 50, message = "Username должно быть от 3 до 50 символов!")
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Пароль не должен быть пустым!")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов!")
    private String password;

    @Column(name = "role", nullable = false)
    @NotEmpty(message = "Роль не должна быть пустой!")
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

