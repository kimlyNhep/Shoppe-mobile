package com.roselyn.shoppe_mobile.model;

public class CategoryResponse {
    private Integer id;
    private String categoryName;
    private UserResponse user;

    public CategoryResponse(Integer id, String categoryName, UserResponse user) {
        this.id = id;
        this.categoryName = categoryName;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
