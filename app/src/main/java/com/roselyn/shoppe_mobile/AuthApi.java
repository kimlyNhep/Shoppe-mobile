package com.roselyn.shoppe_mobile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("users/login")
    Call<TokenResponse> loginUser(@Body LoginRequest request);

    @POST("users/register")
    Call<TokenResponse> registerUser(@Body RegisterRequest request);
}
