package com.example.aistory.model;

import java.util.List;

public class Story {
    private int id;
    private String title;
    private String content;
    private String keywords;
    private String imageUrl; // 단일 이미지 URL 필드
    private List<String> imageUrls; // 여러 이미지 URL을 저장하는 필드 추가
    private String ttsUrl; // TTS URL 필드 추가

    // 생성자, Getter, Setter
    public Story(int id,String title, String content, String keywords, String imageUrl, List<String> imageUrls, String ttsUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.keywords = keywords;
        this.imageUrl = imageUrl;
        this.imageUrls = imageUrls;
        this.ttsUrl = ttsUrl;
    }

    // Getter와 Setter
    public int getId() { return id; } // getId 메서드 추가
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<String> getImageUrls() { return imageUrls; } // 여러 이미지 URL Getter
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; } // 여러 이미지 URL Setter

    public String getTtsUrl() { return ttsUrl; } // TTS URL Getter
    public void setTtsUrl(String ttsUrl) { this.ttsUrl = ttsUrl; } // TTS URL Setter
}
