package com.example.aistory.retrofit;

import com.example.aistory.model.ApiResponse;

public interface NetworkCallback {
    void onSuccess(ApiResponse response);
    void onError(String errorMessage);
}
