package com.roselyn.shoppe_mobile;

import com.roselyn.shoppe_mobile.model.CategoryRequest;
import com.roselyn.shoppe_mobile.model.CategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CategoryApi {
    @POST("categories")
    Call<CategoryRequest> createCategory(@Body CategoryRequest request, @Header("Authorization") String authHeader);

    @GET("categories")
    Call<List<CategoryResponse>> fetchCategories(@Header("Authorization") String authHeader);
}
