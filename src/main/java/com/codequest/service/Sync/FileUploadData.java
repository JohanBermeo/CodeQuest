package com.codequest.service.Sync;

public class FileUploadData {
    private String title;
    private String content;
    private String type;
    private String challengeTitle; // Nullable

    public FileUploadData(String title, String content, String type, String challengeTitle) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.challengeTitle = challengeTitle;
    }

    public FileUploadData(String title, String content, String type) {
        this(title, content, type, null);
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getChallengeTitle() {
        return challengeTitle;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setChallengeTitle(String challengeTitle) {
        this.challengeTitle = challengeTitle;
    }

    @Override
    public String toString() {
        return "FileUploadData{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", challengeTitle='" + challengeTitle + '\'' +
                '}';
    }
}
