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
import com.example.aistory.model.LoginRequest;
import com.example.aistory.retrofit.NetworkManager;

public class LoginActivity extends AppCompatActivity {

    private EditText emailText, passwordText;
    private Button loginBtn, signupBtn;
    private NetworkManager networkManager;


    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        emailText = findViewById(R.id.email_edit);
        passwordText = findViewById(R.id.password_edit);

        networkManager = new NetworkManager();

        signupBtn = findViewById(R.id.signUp_btn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("home","회원가입 버튼 클릭");
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            }
        });

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(view -> {
            Log.d("LoginBtn", "로그인버튼클릭");

            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                LoginRequest request = new LoginRequest(email, password);
                networkManager.login(request, new NetworkManager.NetworkCallback() {
                    @Override
                    public void onSuccess(ApiResponse response) {
                        Intent intent = new Intent(LoginActivity.this, LoginHomeActivity.class);
                        startActivity(intent);
                        finish();  // 로그인 화면을 종료하여 백버튼으로 돌아가지 않게 합니다.
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(LoginActivity.this, "에러: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void Login() {

    }
}
