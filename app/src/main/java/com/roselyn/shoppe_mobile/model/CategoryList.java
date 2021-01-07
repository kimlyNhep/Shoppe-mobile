package com.roselyn.shoppe_mobile.model;

public class CategoryList {
    private CategoryResponse[] categories;

    public CategoryList(CategoryResponse[] categories) {
        this.categories = categories;
    }

    public CategoryResponse[] getCategories() {
        return categories;
    }

    public void setCategories(CategoryResponse[] categories) {
        this.categories = categories;
    }
}
