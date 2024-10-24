package com.example.aistory;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;

public class PlotCreateActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private TextView storyOutput;
    private ImageView storyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plot_create);

        // ViewFlipper, TextView, ImageView 연결
        viewFlipper = findViewById(R.id.viewFlipper);
        storyOutput = findViewById(R.id.story_output);
        storyImage = findViewById(R.id.story_image);

        // Intent로 전달된 스토리를 받음
        String story = getIntent().getStringExtra("story");

        if (story != null) {
            storyOutput.setText(story);  // 스토리 설정
        }

        // 애니메이션 없이 뷰 전환
        viewFlipper.setInAnimation(null);
        viewFlipper.setOutAnimation(null);

        // 스토리를 클릭하면 그림으로 변경
        storyOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showNext();  // 즉시 그림으로 전환
            }
        });

        // 그림을 클릭하면 다시 스토리로 변경
        storyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showPrevious();  // 즉시 스토리로 전환
            }
        });
    }
}
