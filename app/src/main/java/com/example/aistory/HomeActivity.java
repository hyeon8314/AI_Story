package com.example.aistory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {
    private Button login_button, signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        login_button = findViewById(R.id.Login_btn);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test","로그인 클릭");
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });



        signup_button = findViewById(R.id.signUp_btn);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }


    // 이름 이메일 비밀번호 전화번호
    private String signUp(String postData) {
        String result = "";
        String apiUrl = "http://127.0.0.1:8000/api/user/join/"; // Django 회원가입 API URL을 입력하세요.
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                br.close();

                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String status = jsonResponse.getString("status");

                    if (status.equals("success")) {
                        Log.d("SignUpActivity", "회원 가입 성공");
                    } else {
                        Log.d("SignUpActivity", "회원 가입 실패");
                    }
                } catch (JSONException e) {
                    Log.e("SignUpActivity", "JSONException 발생", e);
                }

            } else {
                InputStream errorStream = conn.getErrorStream();
                if (errorStream != null) {
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        result += errorLine;
                    }
                    errorReader.close();
                } else {
                    result = "Error: " + responseCode;
                }
            }

            conn.disconnect();
        } catch (IOException e) {
            Log.e("SignUpActivity", "IOException 발생", e);
            result = "IOException 발생: " + e.getMessage();
        } catch (Exception e) {
            Log.e("SignUpActivity", "예외 발생", e);
            result = "예외 발생: " + e.getMessage();
        }

        return result;
    }
}
