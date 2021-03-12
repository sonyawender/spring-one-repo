package ru.geekbrains.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.geekbrains.persist.Role;
import ru.geekbrains.persist.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.*;

public class UserDTO {

    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @JsonIgnore
    @NotEmpty
    private String matchingPassword;

    @Email
    private String email;

    private Integer age;

    private Set<Role> roles;

    public UserDTO() {
    }

    public UserDTO(String username) {
        this.username = username;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.roles = new HashSet<>(user.getRoles());
    }

    public UserDTO(Role role) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id.equals(userDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
