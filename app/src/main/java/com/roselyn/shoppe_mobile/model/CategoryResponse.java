package com.roselyn.shoppe_mobile.model;

import com.google.gson.annotations.SerializedName;

public class CategoryResponse {
    @SerializedName("id")
    private Integer id;
    @SerializedName("categoryName")
    private String categoryName;
    @SerializedName("user")
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
