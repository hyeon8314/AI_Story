package com.example.aistory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aistory.model.ApiResponse;
import com.example.aistory.retrofit.NetworkManager;

public class PlotActivity extends AppCompatActivity {

    private Button plot_createBtn;
    private EditText storyInput;
    private NetworkManager networkManager;
    private ImageView logo; // 로고 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plot);

        storyInput = findViewById(R.id.story_input);  // EditText ID를 연결
        plot_createBtn = findViewById(R.id.plot_create);
        logo = findViewById(R.id.logo);  // 로고 ID를 연결

        networkManager = new NetworkManager();

        plot_createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storyIdea = storyInput.getText().toString();  // 텍스트박스에서 텍스트 가져오기

                if (storyIdea.isEmpty()) {
                    Toast.makeText(PlotActivity.this, "스토리 아이디어를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    // Django 서버로 데이터 전송
                    networkManager.sendPlot(storyIdea, new NetworkManager.NetworkCallback() {
                        @Override
                        public void onSuccess(ApiResponse response) {
                            // 서버 응답에서 스토리와 이미지 URL 리스트 가져오기
                            String story = response.getStory();
                            List<String> imageUrls = response.getImageUrls();

                            Log.d("PlotActivity", "Received story: " + story);
                            Log.d("PlotActivity", "Received imageUrls: " + imageUrls);

                            if (story != null && !story.isEmpty() && imageUrls != null && !imageUrls.isEmpty()) {
                                // PlotCreateActivity로 이동하여 데이터 전달
                                Intent intent = new Intent(PlotActivity.this, PlotCreateActivity.class);
                                intent.putExtra("story", story);  // 스토리를 Intent에 추가
                                intent.putStringArrayListExtra("imageUrls", new ArrayList<>(imageUrls));
                                startActivity(intent);  // 액티비티 시작
                            } else {
                                Log.e("PlotActivity", "스토리 또는 이미지 URL이 비어 있습니다. 서버 응답을 확인하십시오.");
                                Toast.makeText(PlotActivity.this, "스토리가 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(String errorMessage) {
                            // 에러가 발생했을 때의 처리
                            Log.e("PlotActivity", "Network error: " + errorMessage);
                            Toast.makeText(PlotActivity.this, "에러 발생: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // 로고 클릭 이벤트 추가
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlotActivity.this, LoginHomeActivity.class); // LoginHomeActivity로 이동
                startActivity(intent);
            }
        });
    }
}
