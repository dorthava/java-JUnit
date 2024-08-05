package edu.school21.models;

import java.util.Objects;

public class User {
    private Long identifier;
    private String login;
    private String password;
    private boolean authentication;

    public User(Long identifier, String login, String password, boolean authentication) {
        this.identifier = identifier;
        this.login = login;
        this.password = password;
        this.authentication = authentication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return authentication == user.authentication && Objects.equals(identifier, user.identifier) && Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, login, password, authentication);
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }
}
