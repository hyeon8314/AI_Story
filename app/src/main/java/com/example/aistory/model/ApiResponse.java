package com.example.aistory.model;

import java.util.List;

public class ApiResponse {
    private String status;
    private String message;
    private String story;
    private List<String> imageUrls;  // 여러 이미지 URL을 위한 필드 추가

    // story 필드 getter와 setter
    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    // status 필드 getter와 setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // message 필드 getter와 setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // imageUrls 필드 getter와 setter 추가
    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
