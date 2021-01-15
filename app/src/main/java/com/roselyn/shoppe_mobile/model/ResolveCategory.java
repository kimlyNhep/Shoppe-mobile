package com.roselyn.shoppe_mobile.model;

import com.google.gson.annotations.SerializedName;

public class ResolveCategory {
    @SerializedName("category")
    private CategoryResponse categoryResponse;

    public ResolveCategory(CategoryResponse categoryResponse) {
        this.categoryResponse = categoryResponse;
    }

    public CategoryResponse getCategoryResponse() {
        return categoryResponse;
    }

    public void setCategoryResponse(CategoryResponse categoryResponse) {
        this.categoryResponse = categoryResponse;
    }
}
