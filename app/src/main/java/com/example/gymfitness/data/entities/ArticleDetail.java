package com.example.gymfitness.data.entities;

public class ArticleDetail {
    private String header;
    private String content;

    public ArticleDetail() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public ArticleDetail(String content, String header) {
        this.content = content;
        this.header = header;
    }

}
