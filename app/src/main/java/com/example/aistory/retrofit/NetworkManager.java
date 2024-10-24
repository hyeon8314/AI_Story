package com.example.aistory.retrofit;

import com.example.aistory.model.ApiResponse;
import com.example.aistory.model.LoginRequest;
import com.example.aistory.model.PlotRequest;
import com.example.aistory.model.SignupRequest;

import org.json.JSONObject;

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

    public void sendPlot(String storyIdea, final NetworkCallback callback) {
        PlotRequest plotRequest = new PlotRequest(storyIdea);
        Call<ApiResponse> call = apiService.sendPlot(plotRequest);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    // 응답이 실패했을 경우, 응답 바디를 파싱하여 에러 처리
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject errorJson = new JSONObject(errorBody);

                        // OpenAI의 에러 코드 'content_policy_violation' 확인
                        String errorCode = errorJson.getJSONObject("error").getString("code");
                        if (errorCode.equals("content_policy_violation")) {
                            callback.onError("부적절한 콘텐츠를 생성할 수 없습니다.");
                        } else {
                            callback.onError("Request failed: " + response.code());
                        }
                    } catch (Exception e) {
                        callback.onError("오류 처리 중 문제가 발생했습니다.");
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }



    // 네트워크 콜백 인터페이스 정의
    public interface NetworkCallback {
        void onSuccess(ApiResponse response);
        void onError(String errorMessage);
    }
}
