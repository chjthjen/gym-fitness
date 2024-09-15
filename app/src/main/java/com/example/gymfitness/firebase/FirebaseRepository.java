package com.example.gymfitness.firebase;

import androidx.annotation.NonNull;

import com.example.gymfitness.data.entities.ArticleDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRepository {

    private final DatabaseReference databaseReference;

    public FirebaseRepository() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Articles");
    }

    public interface ArticleDetailsCallback {
        void onCallback(String title, String publishDate, String description, String thumbnail, List<ArticleDetail> articleDetails);
        void onError(DatabaseError error);
    }

    public void getArticleDetails(String articleTitle, ArticleDetailsCallback callback) {
        DatabaseReference articleRef = databaseReference.child(articleTitle);
        articleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String title = snapshot.getKey();
                    String publishDate = snapshot.child("publish_date").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String thumbnail = snapshot.child("thumbnail").getValue(String.class);

                    List<ArticleDetail> details = new ArrayList<>();
                    DataSnapshot contentSnapshot = snapshot.child("content");
                    for (DataSnapshot content : contentSnapshot.getChildren()) {
                        String header = content.getValue(String.class);
                        String contentText = content.getKey();
                        details.add(new ArticleDetail(header, contentText));
                    }
                    callback.onCallback(title, publishDate, description, thumbnail, details);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }
}