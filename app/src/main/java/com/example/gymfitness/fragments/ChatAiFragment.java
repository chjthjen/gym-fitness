package com.example.gymfitness.fragments;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.gymfitness.adapters.help.MessageAdapter;
import com.example.gymfitness.databinding.FragmentChatbotBinding;
import com.example.gymfitness.utils.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatAiFragment extends Fragment {
    private FragmentChatbotBinding binding;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatbotBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageList = new ArrayList<>();

        // Setup RecyclerView
        messageAdapter = new MessageAdapter(messageList);
        binding.recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setStackFromEnd(true);
        binding.recyclerView.setLayoutManager(llm);

        binding.sendBtn.setOnClickListener(v -> {
            String question = binding.messageEditText.getText().toString().trim();
            addToChat(question, Message.SENT_BY_ME);
            binding.messageEditText.setText("");
            callAPI(question);
            binding.welcomeText.setVisibility(View.GONE);
        });
    }


    void addToChat(String message, String sentBy) {
        getActivity().runOnUiThread(() -> {
            messageList.add(new Message(message, sentBy));
            messageAdapter.notifyDataSetChanged();
            binding.recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }

    void addResponse(String response) {
        messageList.remove(messageList.size() - 1);
        addToChat(response, Message.SENT_BY_BOT);
    }

    void callAPI(String question) {

        messageList.add(new Message("Typing...", Message.SENT_BY_BOT));

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
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=YOUR_API_KEY")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("API Error", "Request failed: " + e.getMessage());
                addResponse("Sorry, something went wrong. Please try again later.");

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("API Response", "Response code: " + response.code());
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        Log.d("Raw Response Body:", responseBody);

                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray candidates = jsonObject.getJSONArray("candidates"); // Get the "candidates" array

                        if (candidates.length() > 0) {
                            JSONObject firstCandidate = candidates.getJSONObject(0); // Get the first candidate object
                            JSONObject content = firstCandidate.getJSONObject("content"); // Get the "content" object
                            JSONArray parts = content.getJSONArray("parts"); // Get the "parts" array

                            if (parts.length() > 0) {
                                JSONObject firstPart = parts.getJSONObject(0); // Get the first part object
                                String result = firstPart.getString("text");  // Extract the "text"
                                addResponse(result.trim());
                            } else {
                                addResponse("No response text found.");
                            }

                        } else {
                            addResponse("No candidates found in the response.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        addResponse("Error parsing response: " + e.getMessage());
                    }
                } else {
                    String errorBody = response.body() != null ? response.body().string() : "No response body";
                    Log.e("API Error", "Response failed: " + response.message() + ", Error body: " + errorBody);
                    addResponse("Failed to load response due to " + response.message() + ": " + errorBody);
                }
            }
        });
    }

}
