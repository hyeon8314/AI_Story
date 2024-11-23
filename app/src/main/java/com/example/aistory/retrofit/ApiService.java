package com.example.aistory.retrofit;

import com.example.aistory.model.Story;
import com.example.aistory.model.ApiResponse;
import com.example.aistory.model.LoginRequest;
import com.example.aistory.model.PlotRequest;
import com.example.aistory.model.SignupRequest;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {

    @POST("/api/user/join")
    Call<ApiResponse> signUp(@Body SignupRequest request);

    @POST("/api/user/login")
    Call<ApiResponse> login(@Body LoginRequest request);

    @POST("/api/plot/")
    Call<ApiResponse> sendPlot(@Body PlotRequest plotRequest);

    @GET("/api/stories/")
    Call<List<Story>> getStories();

    // 특정 책의 ID로 책의 상세 정보를 가져오는 엔드포인트 추가
    @GET("/api/stories/{id}")  // {id}에 해당하는 책의 ID가 필요
    Call<Story> getBookDetails(@Path("id") int id);
}
