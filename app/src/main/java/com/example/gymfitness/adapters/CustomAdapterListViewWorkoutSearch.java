package com.example.gymfitness.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gymfitness.R;

import java.util.HashSet;
import java.util.List;

public class CustomAdapterListViewWorkoutSearch extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> searchHistory;
    private final SharedPreferences sharedPreferences;

    public CustomAdapterListViewWorkoutSearch(Context context, List<String> searchHistory, SharedPreferences sharedPreferences) {
        super(context, R.layout.list_item_workoutsearch, searchHistory);
        this.context = context;
        this.searchHistory = searchHistory;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item_workoutsearch, parent, false);
        }

        TextView textView = rowView.findViewById(R.id.title);
        ImageView imageView = rowView.findViewById(R.id.icon);
        ImageView deleteButton = rowView.findViewById(R.id.delete_button);

        textView.setText(searchHistory.get(position));
        imageView.setImageResource(R.drawable.baseline_search_24);

        deleteButton.setOnClickListener(v -> {
            searchHistory.remove(position);
            saveSearchHistory(searchHistory);
            notifyDataSetChanged();
        });

        return rowView;
    }

    private void saveSearchHistory(List<String> history) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("history", new HashSet<>(history));
        editor.apply();
    }
}