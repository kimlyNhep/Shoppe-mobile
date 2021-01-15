package com.roselyn.shoppe_mobile.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<Integer> categoryId;
    private MutableLiveData<String> categoryName;

    public CategoryViewModel() {
        categoryName = new MutableLiveData<>();
        categoryName.setValue("");
        categoryId = new MutableLiveData<>();
        categoryId.setValue(-1);
    }


    public LiveData<String> getText() {
        return categoryName;
    }

    public void setText(String name) {
        this.categoryName.setValue(name);
    }

    public LiveData<Integer> getId() {
        return categoryId;
    }

    public void setCategoryId(Integer id) {
        this.categoryId.setValue(id);
    }
}