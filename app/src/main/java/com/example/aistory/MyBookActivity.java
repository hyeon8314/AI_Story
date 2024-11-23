package com.example.aistory;

import com.example.aistory.retrofit.ApiService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.aistory.model.Story;
import com.example.aistory.retrofit.ApiClient;
import androidx.cardview.widget.CardView;


import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookActivity extends AppCompatActivity {

    private ImageView story1Image, story2Image, story3Image;
    private TextView story1Title, story2Title, story3Title;
    private ImageView logo; // 추가된 로고 ImageView
    private ImageView newStory; // 추가된 new_story ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_book);

        // 뷰 초기화
        story1Image = findViewById(R.id.story1_image);
        story2Image = findViewById(R.id.story2_image);
        story3Image = findViewById(R.id.story3_image);

        story1Title = findViewById(R.id.story1_title);
        story2Title = findViewById(R.id.story2_title);
        story3Title = findViewById(R.id.story3_title);

        // 추가된 ImageView 초기화
        logo = findViewById(R.id.logo);
        newStory = findViewById(R.id.imageView4);
        CardView story1Card = findViewById(R.id.story1);


        // Retrofit 서비스 초기화
        ApiService apiService = ApiClient.getApiService();

        // 서버에서 동화 목록 가져오기
        apiService.getStories().enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Story> stories = response.body();

                    // 동화 데이터 설정
                    if (stories.size() > 0) {
                        Story story1 = stories.get(0);
                        story1Title.setText(story1.getTitle());
                        if (story1.getImageUrl() != null && !story1.getImageUrl().isEmpty()) {
                            Log.d("MyBookActivity", "Loading Image URL for Story 1: " + story1.getImageUrl());
                            Glide.with(MyBookActivity.this)
                                    .load(story1.getImageUrl())
                                    .placeholder(R.drawable.error) // 로딩 중 보여줄 이미지
                                    .error(R.drawable.error) // 로딩 실패 시 보여줄 이미지
                                    .into(story1Image);
                        } else {
                            Log.e("MyBookActivity", "Story 1 Image URL is null or empty");
                        }
                    }
                    if (stories.size() > 1) {
                        Story story2 = stories.get(1);
                        story2Title.setText(story2.getTitle());
                        if (story2.getImageUrl() != null && !story2.getImageUrl().isEmpty()) {
                            Glide.with(MyBookActivity.this).load(story2.getImageUrl()).into(story2Image);
                        } else {
                            Log.e("MyBookActivity", "Story 2 Image URL is null or empty");
                        }
                    }
                    if (stories.size() > 2) {
                        Story story3 = stories.get(2);
                        story3Title.setText(story3.getTitle());
                        if (story3.getImageUrl() != null && !story3.getImageUrl().isEmpty()) {
                            Glide.with(MyBookActivity.this).load(story3.getImageUrl()).into(story3Image);
                        } else {
                            Log.e("MyBookActivity", "Story 3 Image URL is null or empty");
                        }
                    }
                } else {
                    Log.e("MyBookActivity", "Response is unsuccessful or empty");
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                // 에러 처리
                Log.e("MyBookActivity", "Failed to load stories", t);
                t.printStackTrace();
            }
        });

        // 로고를 클릭하면 LoginHomeActivity로 이동
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyBookActivity.this, LoginHomeActivity.class);
                startActivity(intent);
            }
        });

        // new_story를 클릭하면 PlotActivity로 이동
        newStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyBookActivity.this, PlotActivity.class);
                startActivity(intent);
            }
        });

        // story1 CardView 클릭 이벤트 추가
        story1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyBookActivity.this, FinishedBookActivity.class);
                startActivity(intent);
            }
        });
    }
}

