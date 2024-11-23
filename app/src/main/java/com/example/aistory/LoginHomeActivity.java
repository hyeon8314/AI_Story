package com.example.aistory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginHomeActivity extends AppCompatActivity {

    private Button myBook, guideBtn; // guideBtn 추가
    private ImageView mypage_btn; // mypage_btn 추가
    private TextView userNameTextView; // 사용자 이름을 표시할 TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_home);

        // TextView 연결
        userNameTextView = findViewById(R.id.user_name);

        // SharedPreferences에서 사용자 이름 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "리코딩"); // 기본값은 "리코딩"

        // TextView에 사용자 이름 설정
        userNameTextView.setText(userName);

        // myBook 버튼 설정
        myBook = findViewById(R.id.my_book);
        myBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginHomeActivity.this, PlotActivity.class);
                startActivity(intent);
            }
        });

        // guideBtn 클릭 이벤트 추가
        guideBtn = findViewById(R.id.guide_btn);
        guideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginHomeActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        // mypage_btn 클릭 이벤트 추가
        mypage_btn = findViewById(R.id.mypage_btn);
        mypage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginHomeActivity.this, MyPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
