package com.example.aistory;

import android.speech.tts.TextToSpeech;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PlotCreateActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private ViewFlipper viewFlipper;
    private TextView storyOutput;
    private ImageView storyImage;
    private ImageView plotEdit;
    private ImageView preButton, nextButton;
    private ImageView logo;  // 로고 ImageView 추가
    private Button ttsButton;
    private TextToSpeech tts;

    private String title;
    private List<String> storyPages;
    private ArrayList<String> imageUrls;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plot_create);

        viewFlipper = findViewById(R.id.viewFlipper);
        storyOutput = findViewById(R.id.story_output);
        storyImage = findViewById(R.id.story_image);
        plotEdit = findViewById(R.id.plot_edit);
        preButton = findViewById(R.id.preButton);
        nextButton = findViewById(R.id.nextButton);
        ttsButton = findViewById(R.id.Sound_btn);
        logo = findViewById(R.id.logo);  // 로고 ImageView 초기화

        tts = new TextToSpeech(this, this);

        title = getIntent().getStringExtra("title");
        String story = getIntent().getStringExtra("story");
        imageUrls = getIntent().getStringArrayListExtra("imageUrls");

        if (story != null) {
            storyPages = Arrays.asList(story.split("\n\n")); // 문단별로 나눔
        } else {
            storyPages = Arrays.asList("스토리가 없습니다.");
        }

        if (imageUrls == null || imageUrls.isEmpty()) {
            imageUrls = new ArrayList<>(Arrays.asList("default_image_url")); // 기본 이미지 URL 설정
        }

        updatePage();

        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 0) {
                    currentPage--;
                    updatePage();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage < storyPages.size() - 1) {
                    currentPage++;
                    updatePage();
                }
            }
        });

        if (title == null || title.isEmpty()) {
            title = "기본 제목";
        }

        Log.d("PlotCreateActivity", "Received title: " + title);
        Log.d("PlotCreateActivity", "Received story: " + story);
        Log.d("PlotCreateActivity", "Received imageUrls: " + imageUrls);

        SharedPreferences sharedPreferences = getSharedPreferences("storyData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("story", story);
        editor.putString("imageUrls", String.join(",", imageUrls));
        editor.putString("title", title);
        editor.apply();

        ttsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storyPages.get(currentPage) != null && !storyPages.get(currentPage).isEmpty()) {
                    tts.speak(storyPages.get(currentPage), TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    Log.e("PlotCreateActivity", "현재 페이지의 스토리가 비어있습니다.");
                }
            }
        });

        plotEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlotCreateActivity.this, FinishedBookActivity.class);
                String savedTitle = sharedPreferences.getString("title", "기본 제목");
                intent.putExtra("title", savedTitle);
                intent.putExtra("story", sharedPreferences.getString("story", ""));
                intent.putStringArrayListExtra("imageUrls", imageUrls);
                startActivity(intent);
            }
        });

        // 로고 클릭 시 LoginHomeActivity로 이동
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlotCreateActivity.this, LoginHomeActivity.class);
                startActivity(intent);
            }
        });

        viewFlipper.setInAnimation(null);
        viewFlipper.setOutAnimation(null);

        storyOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showNext();
            }
        });

        storyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showPrevious();
            }
        });
    }

    private void updatePage() {
        storyOutput.setText(storyPages.get(currentPage));
        if (currentPage < imageUrls.size()) {
            Glide.with(this)
                    .load(imageUrls.get(currentPage))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(storyImage);
        } else {
            storyImage.setImageResource(R.drawable.error);
        }
        viewFlipper.setDisplayedChild(0);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.KOREAN);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("PlotCreateActivity", "TTS 언어 지원 안됨");
            }
        } else {
            Log.e("PlotCreateActivity", "TTS 초기화 실패");
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
