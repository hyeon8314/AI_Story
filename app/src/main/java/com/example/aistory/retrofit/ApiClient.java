package com.example.aistory.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8000/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // OkHttpClient 빌더에 타임아웃 설정
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(180, TimeUnit.SECONDS)  // 연결 타임아웃 60초
                    .readTimeout(180, TimeUnit.SECONDS)     // 읽기 타임아웃 60초
                    .writeTimeout(180, TimeUnit.SECONDS)    // 쓰기 타임아웃 60초
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)  // OkHttpClient 설정 적용
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
