package com.roselyn.shoppe_mobile.model;

public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private RoleResponse role;

    public UserResponse(Integer id, String firstName, String lastName, String username, RoleResponse role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleResponse getRole() {
        return role;
    }

    public void setRole(RoleResponse role) {
        this.role = role;
    }
}
