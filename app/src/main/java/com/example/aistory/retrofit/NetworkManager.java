package com.example.aistory.retrofit;

import com.example.aistory.model.ApiResponse;
import com.example.aistory.model.LoginRequest;
import com.example.aistory.model.SignupRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManager {

    private ApiService apiService;

    public NetworkManager() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void signUp(SignupRequest request, NetworkCallback callback) {
        Call<ApiResponse> call = apiService.signUp(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("응답 실패: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callback.onError("네트워크 오류: " + t.getMessage());
            }
        });
    }

    public void login(LoginRequest request, NetworkCallback callback) {
        Call<ApiResponse> call = apiService.login(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("응답실패: "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                callback.onError("네트워크오류: " + throwable.getMessage());
            }
        });
    }



    // 네트워크 콜백 인터페이스 정의
    public interface NetworkCallback {
        void onSuccess(ApiResponse response);
        void onError(String errorMessage);
    }
}
