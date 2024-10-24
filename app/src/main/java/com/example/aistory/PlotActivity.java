package com.example.aistory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aistory.model.ApiResponse;
import com.example.aistory.retrofit.NetworkManager;

public class PlotActivity extends AppCompatActivity {

    private Button plot_createBtn;
    private EditText storyInput;
    private NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plot);

        storyInput = findViewById(R.id.story_input);  // EditText ID를 연결
        plot_createBtn = findViewById(R.id.plot_create);

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
                            // 성공적으로 서버로부터 스토리를 받았을 때 처리
                            String story = response.getStory();  // 서버 응답에서 스토리 내용 가져오기

                            Log.d("PlotActivity", "Received story: " + story);

//                            // 새로운 화면(PlotCreateActivity)으로 이동하여 스토리를 보여줌
//                            Intent intent = new Intent(PlotActivity.this, PlotCreateActivity.class);
//                            intent.putExtra("story", story);  // 스토리를 Intent에 추가
//                            startActivity(intent);  // 액티비티 시작

                            if (story != null && !story.isEmpty()) {
                                // 새로운 화면(PlotCreateActivity)으로 이동하여 스토리를 보여줌
                                Intent intent = new Intent(PlotActivity.this, PlotCreateActivity.class);
                                intent.putExtra("story", story);  // 스토리를 Intent에 추가
                                startActivity(intent);  // 액티비티 시작
                            } else {
                                Log.e("PlotActivity", "스토리가 비어 있습니다. 서버 응답을 확인하십시오.");
                                Toast.makeText(PlotActivity.this, "스토리가 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(String errorMessage) {
                            // 에러가 발생했을 때의 처리
                            Toast.makeText(PlotActivity.this, "에러 발생: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
