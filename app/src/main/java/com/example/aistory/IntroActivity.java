package com.example.aistory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro); // 인트로 레이아웃 설정

        // 1.5초 후에 HomeActivity로 전환
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // IntroActivity 종료
            }
        }, 1500); // 1500 milliseconds = 1.5 seconds
    }
}
