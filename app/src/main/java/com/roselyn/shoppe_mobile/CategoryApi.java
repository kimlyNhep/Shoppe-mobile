package com.roselyn.shoppe_mobile;

import com.roselyn.shoppe_mobile.model.CategoryRequest;
import com.roselyn.shoppe_mobile.model.CategoryResponse;
import com.roselyn.shoppe_mobile.model.ResolveCategory;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryApi {
    @POST("categories")
    Call<CategoryRequest> createCategory(@Body CategoryRequest request, @Header("Authorization") String authHeader);

    @GET("categories")
    Call<List<CategoryResponse>> fetchCategories(@Header("Authorization") String authHeader);

    @GET("categories/{id}")
    Call<ResolveCategory> fetchCategoryById(@Path("id") Integer id, @Header("Authorization") String authHeader);

    @DELETE("categories/{id}")
    Call<ResponseBody> deleteCategory(@Path("id") Integer id, @Header("Authorization") String authHeader);

    @PUT("categories/{id}")
    Call<ResponseBody> updateCategory(@Path("id") Integer id, @Body CategoryRequest request, @Header("Authorization") String authHeader);
}
