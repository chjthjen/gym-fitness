package com.example.gymfitness.viewmodels;

import androidx.lifecycle.ViewModel;

public class ArticleDetailViewModel extends ViewModel {
    private String header;
    private String content;

    public ArticleDetailViewModel(String header, String content) {
        this.header = header;
        this.content = content;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
