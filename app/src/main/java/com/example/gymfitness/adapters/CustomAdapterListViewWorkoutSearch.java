package com.example.gymfitness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gymfitness.R;

public class CustomAdapterListViewWorkoutSearch extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public CustomAdapterListViewWorkoutSearch(Context context, String[] values) {
        super(context, R.layout.list_item_workoutsearch, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item_workoutsearch, parent, false);
        }

        TextView textView = rowView.findViewById(R.id.title);
        ImageView imageView = rowView.findViewById(R.id.icon);

        textView.setText(values[position]);
        imageView.setImageResource(R.drawable.baseline_search_24);

        return rowView;
    }

    @Override
    public int getCount() {
        if (values.length < 3) {
            return values.length;
        }
        return 3;
    }
}
