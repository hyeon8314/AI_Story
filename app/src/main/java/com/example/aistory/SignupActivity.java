package com.example.aistory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aistory.databinding.SignUpBinding;
import com.example.aistory.model.ApiResponse;
import com.example.aistory.model.SignupRequest;
import com.example.aistory.retrofit.NetworkManager;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private EditText nameText, emailText, passwordText, phoneText;
    private Button signupBtn;
    private NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        nameText = findViewById(R.id.name_edit);
        emailText = findViewById(R.id.email_edit);
        passwordText = findViewById(R.id.password_edit);
        phoneText = findViewById(R.id.phone_edit);

        networkManager = new NetworkManager();

        signupBtn = findViewById(R.id.signUp_btn);
        signupBtn.setOnClickListener(view -> {
            Log.d("SignupBtn", "회원가입버튼클릭");

            String userName = nameText.getText().toString();
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            String phone = phoneText.getText().toString();

            // 유효성 검사
            if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                Toast.makeText(SignupActivity.this, "모든 필드를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(email)) {
                Toast.makeText(SignupActivity.this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("SignupRequest", "userName: " + userName + ", email: " + email + ", password: " + password + ", phone: " + phone);

                SignupRequest request = new SignupRequest(userName, password, email, phone);
                networkManager.signUp(request, new NetworkManager.NetworkCallback() {
                    @Override
                    public void onSuccess(ApiResponse response) {
                        if ("success".equals(response.getStatus())) {
                            Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignupActivity.this, "회원가입에러: " + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(SignupActivity.this, "에러: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean isValidEmail(String email) {
        // 이메일 유효성 검사
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }
}
