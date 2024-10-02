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

import com.example.gymfitness.retrofit.ApiService;
import com.example.gymfitness.utils.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatAiFragment extends Fragment {
    private FragmentChatbotBinding binding;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    private ApiService apiService = new ApiService();

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

        apiService.callAPI(question, new ApiService.ApiServiceCallback() {
            @Override
            public void onSuccess(String response) {
                addResponse(response);
            }

            @Override
            public void onFailure(String errorMessage) {
                addResponse(errorMessage);
            }
        });
    }
}