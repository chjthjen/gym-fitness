package com.example.gymfitness.retrofit;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gymfitness.utils.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class ApiService {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public void callAPI(String question, ApiServiceCallback callback) {
        JSONObject jsonBody = new JSONObject();
        try {
            JSONArray contentsArray = new JSONArray();
            JSONObject contentObject = new JSONObject();
            JSONArray partsArray = new JSONArray();
            JSONObject partObject = new JSONObject();
            partObject.put("text", question);
            partsArray.put(partObject);
            contentObject.put("parts", partsArray);
            contentsArray.put(contentObject);
            jsonBody.put("contents", contentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, jsonBody.toString());
        Request request = new Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("API Error", "Request failed: " + e.getMessage());
                callback.onFailure("Sorry, something went wrong. Please try again later.");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("API Response", "Response code: " + response.code());
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        Log.d("Raw Response Body:", responseBody);

                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray candidates = jsonObject.getJSONArray("candidates");

                        if (candidates.length() > 0) {
                            JSONObject firstCandidate = candidates.getJSONObject(0);
                            JSONObject content = firstCandidate.getJSONObject("content");
                            JSONArray parts = content.getJSONArray("parts");

                            if (parts.length() > 0) {
                                JSONObject firstPart = parts.getJSONObject(0);
                                String result = firstPart.getString("text");
                                callback.onSuccess(result.trim());
                            } else {
                                callback.onFailure("No response text found.");
                            }

                        } else {
                            callback.onFailure("No candidates found in the response.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onFailure("Error parsing response: " + e.getMessage());
                    }
                } else {
                    String errorBody = response.body() != null ? response.body().string() : "No response body";
                    Log.e("API Error", "Response failed: " + response.message() + ", Error body: " + errorBody);
                    callback.onFailure("Failed to load response due to " + response.message() + ": " + errorBody);
                }
            }
        });
    }

    public interface ApiServiceCallback {
        void onSuccess(String response);
        void onFailure(String errorMessage);
    }
}
