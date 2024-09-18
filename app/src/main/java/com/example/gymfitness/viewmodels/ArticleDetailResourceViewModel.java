package com.example.gymfitness.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.entities.ArticleDetail;
import com.example.gymfitness.firebase.FirebaseRepository;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class ArticleDetailResourceViewModel extends ViewModel {
    private final MutableLiveData<String> title = new MutableLiveData<>();
    private final MutableLiveData<String> publishDate = new MutableLiveData<>();
    private final MutableLiveData<String> description = new MutableLiveData<>();
    private final MutableLiveData<String> thumbnail = new MutableLiveData<>();
    private final MutableLiveData<List<ArticleDetail>> articleDetails = new MutableLiveData<>();

    private final FirebaseRepository firebaseRepository;

    public ArticleDetailResourceViewModel() {
        firebaseRepository = new FirebaseRepository();
    }

    public LiveData<String> getTitle() {
        return title;
    }

    public LiveData<String> getPublishDate() {
        return publishDate;
    }

    public LiveData<String> getDescription() {
        return description;
    }

    public LiveData<String> getThumbnail() {
        return thumbnail;
    }
    private final MutableLiveData<String> selectedArticleTitle = new MutableLiveData<>();

    public void setSelectedArticle(String articleTitle) {
        selectedArticleTitle.setValue(articleTitle);
    }

    public LiveData<List<ArticleDetail>> getArticleDetails() {
        return articleDetails;
    }

    public void loadArticleDetails(String articleTitle) {
        firebaseRepository.getArticleDetails(articleTitle, new FirebaseRepository.ArticleDetailsCallback() {
            @Override
            public void onCallback(String titleValue, String publishDateValue, String descriptionValue, String thumbnailValue, List<ArticleDetail> details) {
                title.setValue(titleValue);
                publishDate.setValue(publishDateValue);
                description.setValue(descriptionValue);
                thumbnail.setValue(thumbnailValue);
                articleDetails.setValue(details);
            }

            @Override
            public void onError(DatabaseError error) {

            }
        });
    }
}