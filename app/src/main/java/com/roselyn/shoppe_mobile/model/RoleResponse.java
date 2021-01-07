package com.roselyn.shoppe_mobile.model;

public class RoleResponse {
    private Integer roleId;
    private String roleName;
    private UserResponse user;

    public RoleResponse(Integer roleId, String roleName, UserResponse user) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.user = user;
    }

    public RoleResponse(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
}
