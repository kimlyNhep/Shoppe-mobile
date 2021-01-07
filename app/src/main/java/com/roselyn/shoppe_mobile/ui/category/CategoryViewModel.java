package com.roselyn.shoppe_mobile.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<String> categoryName;

    public CategoryViewModel() {
        categoryName = new MutableLiveData<>();
        categoryName.setValue("Test");
    }


    public LiveData<String> getText() {
        return categoryName;
    }


}