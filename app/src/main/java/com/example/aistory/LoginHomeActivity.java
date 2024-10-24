package com.example.aistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginHomeActivity extends AppCompatActivity {

    private Button myBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_home);

        myBook = findViewById(R.id.my_book);
        myBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginHomeActivity.this, PlotActivity.class);
                startActivity(intent);
            }
        });
    }
}
