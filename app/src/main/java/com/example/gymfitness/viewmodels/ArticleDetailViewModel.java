package com.example.gymfitness.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ArticleDetailViewModel extends ViewModel {
    private final MutableLiveData<String> header = new MutableLiveData<>();
    private final MutableLiveData<String> content = new MutableLiveData<>();

    public ArticleDetailViewModel() {

    }

    public LiveData<String> getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header.setValue(header);
    }

    public LiveData<String> getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content.setValue(content);
    }
}