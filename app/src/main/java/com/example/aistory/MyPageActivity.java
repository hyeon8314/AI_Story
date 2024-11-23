package com.example.aistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyPageActivity extends AppCompatActivity {

    private TextView myBookTextView, userModifyTextView, runInfoTextView, logoutTextView; // logoutTextView 추가
    private ImageView logo; // logo ImageView 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        // myBookTextView 클릭 이벤트 추가
        myBookTextView = findViewById(R.id.my_book);
        myBookTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MyBookActivity로 이동하도록 설정
                Intent intent = new Intent(MyPageActivity.this, MyBookActivity.class);
                startActivity(intent);
            }
        });

        // user_modify 클릭 이벤트 추가
        userModifyTextView = findViewById(R.id.user_modify);
        userModifyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, ModifyUserActivity.class);
                startActivity(intent);
            }
        });

        // run_info 클릭 이벤트 추가
        runInfoTextView = findViewById(R.id.run_info);
        runInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        // logo 클릭 이벤트 추가
        logo = findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, LoginHomeActivity.class);
                startActivity(intent);
            }
        });

        // logout 클릭 이벤트 추가
        logoutTextView = findViewById(R.id.logout);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    // 로그아웃 메서드 구현
    private void logoutUser() {
        // 세션이나 사용자 관련 데이터를 정리하는 코드가 필요하다면 여기에 추가
        Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();  // 현재 액티비티 종료
    }
}
