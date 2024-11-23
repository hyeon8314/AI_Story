package com.example.aistory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FinishedBookActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextView bookTitle;
    private TextView bookStory;
    private ImageView imagePlace;
    private ViewFlipper viewFlipper;
    private ImageView ttsButton, nextButton, logo; // logo ImageView 추가
    private Button soundBtn;
    private TextToSpeech tts;

    private List<String> storyPages;
    private ArrayList<String> imageUrls;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finished_book);

        // UI 요소 초기화
        bookTitle = findViewById(R.id.bookTitle);
        bookStory = findViewById(R.id.bookStory);
        imagePlace = findViewById(R.id.imagePlace);
        viewFlipper = findViewById(R.id.viewFlipper);
        ttsButton = findViewById(R.id.ttsButton);
        nextButton = findViewById(R.id.nextButton);
        soundBtn = findViewById(R.id.Sound_btn);
        logo = findViewById(R.id.logo); // logo 초기화

        // TTS 초기화
        tts = new TextToSpeech(this, this);

        // Intent에서 데이터 가져오기
        try {
            String title = getIntent().getStringExtra("title");
            String story = getIntent().getStringExtra("story");
            imageUrls = getIntent().getStringArrayListExtra("imageUrls");

            Log.d("FinishedBookActivity", "Received title: " + title);
            Log.d("FinishedBookActivity", "Received story: " + story);
            Log.d("FinishedBookActivity", "Received imageUrls: " + imageUrls);

            // 제목 설정
            bookTitle.setText(title != null ? title : "");

            // 스토리 페이지 설정
            if (story != null && !story.isEmpty()) {
                storyPages = new ArrayList<>(Arrays.asList(story.split("\n\n")));
            } else {
                storyPages = new ArrayList<>(Collections.singletonList("내용이 없습니다."));
            }

            // 이미지 URL 설정
            if (imageUrls == null || imageUrls.isEmpty()) {
                imageUrls = new ArrayList<>(Collections.singletonList("default_image_url"));
            }

            updatePage();

        } catch (Exception e) {
            Log.e("FinishedBookActivity", "Error loading data: " + e.getMessage());
            e.printStackTrace();
        }

        // 이전 버튼
        ttsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 0) {
                    currentPage--;
                    updatePage();
                }
            }
        });

        // 다음 버튼
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage < storyPages.size() - 1) {
                    currentPage++;
                    updatePage();
                }
            }
        });

        // TTS 버튼
        soundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage < storyPages.size() && storyPages.get(currentPage) != null) {
                    tts.speak(storyPages.get(currentPage), TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });

        // logo 클릭 시 LoginHomeActivity로 이동
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishedBookActivity.this, LoginHomeActivity.class);
                startActivity(intent);
            }
        });

        // ViewFlipper 설정
        viewFlipper.setInAnimation(null);
        viewFlipper.setOutAnimation(null);

        bookStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showNext();
            }
        });

        imagePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showPrevious();
            }
        });
    }

    private void updatePage() {
        try {
            if (currentPage >= 0 && currentPage < storyPages.size()) {
                bookStory.setText(storyPages.get(currentPage));

                if (currentPage < imageUrls.size()) {
                    Glide.with(this)
                            .load(imageUrls.get(currentPage))
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.error)
                            .into(imagePlace);
                } else {
                    imagePlace.setImageResource(R.drawable.placeholder);
                }

                // 버튼 가시성 업데이트
                ttsButton.setVisibility(currentPage > 0 ? View.VISIBLE : View.INVISIBLE);
                nextButton.setVisibility(currentPage < storyPages.size() - 1 ? View.VISIBLE : View.INVISIBLE);
            }
            viewFlipper.setDisplayedChild(0);
        } catch (Exception e) {
            Log.e("FinishedBookActivity", "Error updating page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.KOREAN);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("FinishedBookActivity", "TTS 언어가 지원되지 않습니다.");
            }
        } else {
            Log.e("FinishedBookActivity", "TTS 초기화 실패");
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
