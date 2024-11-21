package com.example.finalProject.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "finance_news_articles") // MySQL 테이블 이름과 매핑
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "press_source", columnDefinition = "TEXT")
    private String pressSource;

    @Column(name = "img_url", columnDefinition = "TEXT")
    private String imgUrl;

    @Column(columnDefinition = "TEXT")
    private String url; // 새로 추가된 필드

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPressSource() {
        return pressSource;
    }

    public void setPressSource(String pressSource) {
        this.pressSource = pressSource;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() { // Getter for url
        return url;
    }

    public void setUrl(String url) { // Setter for url
        this.url = url;
    }
}