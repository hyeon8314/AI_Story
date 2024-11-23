package com.example.aistory.retrofit;

import com.example.aistory.model.ApiResponse;
import com.example.aistory.model.LoginRequest;
import com.example.aistory.model.PlotRequest;
import com.example.aistory.model.SignupRequest;
import com.example.aistory.model.Story;

import java.util.List;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManager {

    private ApiService apiService;

    public NetworkManager() {
        apiService = ApiClient.getApiService();  // ApiClient.getClient() 대신 ApiClient.getApiService() 사용
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
                    callback.onError("응답실패: " + response.code());
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
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject errorJson = new JSONObject(errorBody);
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

    public void getStories(final GetStoriesCallback callback) {
        Call<List<Story>> call = apiService.getStories();
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("응답 실패: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                callback.onError("네트워크 오류: " + t.getMessage());
            }
        });
    }

    public interface NetworkCallback {
        void onSuccess(ApiResponse response);
        void onError(String errorMessage);
    }

    public interface GetStoriesCallback {
        void onSuccess(List<Story> stories);
        void onError(String errorMessage);
    }
}
