package com.example.aistory.retrofit;

import com.example.aistory.model.ApiResponse;
import com.example.aistory.model.LoginRequest;
import com.example.aistory.model.SignupRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/user/join")
    Call<ApiResponse> signUp(@Body SignupRequest request);

    @POST("/api/user/login")
    Call<ApiResponse> login(@Body LoginRequest request);
}
