package com.roselyn.shoppe_mobile.model;

import com.google.gson.annotations.SerializedName;

public class CategoryRequest {
    @SerializedName("categoryName")
    private String categoryName;

    public CategoryRequest(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
