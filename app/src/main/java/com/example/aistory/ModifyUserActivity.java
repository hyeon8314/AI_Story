package com.example.aistory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyUserActivity extends AppCompatActivity {

    private EditText editTextName, editTextPhone, editTextNewPassword;
    private TextView userEmailTextView;
    private Button btnSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_user);

        // EditText와 Button 초기화
        editTextName = findViewById(R.id.user_name);
        userEmailTextView = findViewById(R.id.user_email);
        editTextPhone = findViewById(R.id.user_phone);
        editTextNewPassword = findViewById(R.id.new_password);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        // 로고 클릭 시 이동 (예제)
        findViewById(R.id.logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyUserActivity.this, LoginHomeActivity.class);
                startActivity(intent);
            }
        });

        // 사용자 정보 불러오기
        loadUserInfo();

        // 저장 버튼 클릭 이벤트
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    // 사용자 정보 불러오기 (SharedPreferences 예제)
    private void loadUserInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "홍길동");
        String email = sharedPreferences.getString("email", "example@example.com");
        String phone = sharedPreferences.getString("phone", "010-1234-5678");

        editTextName.setText(name);
        userEmailTextView.setText(email);
        editTextPhone.setText(phone);
    }

    // 사용자 정보 수정내용 저장
    private void saveChanges() {
        String newName = editTextName.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String newPhone = editTextPhone.getText().toString().trim();

        if (newName.isEmpty() || newPhone.isEmpty()) {
            Toast.makeText(this, "이름과 전화번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // SharedPreferences에 저장
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", newName);
        editor.putString("phone", newPhone);
        editor.apply();

        // 비밀번호는 로컬 저장소에 저장하지 않는 것이 바람직함 (보안 이유)
        if (!newPassword.isEmpty()) {
            Toast.makeText(this, "비밀번호는 보안을 위해 서버에 저장해야 합니다.", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this, "변경 사항이 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }
}
