package com.example.gymfitness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.Notification;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class NotificationWorkoutRCVAdapter extends RecyclerView.Adapter<NotificationWorkoutRCVAdapter.NotificationWorkoutViewHolder>{
    private Context context;
    private List<Notification> listNotification;

    public NotificationWorkoutRCVAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Notification> listNotification) {
        this.listNotification = listNotification;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationWorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_workout, parent, false);
        return new NotificationWorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationWorkoutViewHolder holder, int position) {
        Notification notification = listNotification.get(position);
        if(notification == null)
            return;

        holder.imvNotificationWorkout.setImageResource(notification.getType());
        holder.tvNameNotificationWorkout.setText(notification.getName());

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd - hh:mm a", Locale.getDefault());
        String formattedDate = sdf.format(notification.getDate());
        holder.tvDateNotificationWorkout.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        if(listNotification != null)
            return listNotification.size();
        return 0;
    }

    public static class NotificationWorkoutViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvNotificationWorkout;
        private TextView tvNameNotificationWorkout;
        private TextView tvDateNotificationWorkout;

        public NotificationWorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            imvNotificationWorkout = itemView.findViewById(R.id.imv_notification_workout);
            tvNameNotificationWorkout = itemView.findViewById(R.id.tv_name_notification_workout);
            tvDateNotificationWorkout = itemView.findViewById(R.id.tv_date_notification_workout);
        }
    }
}
