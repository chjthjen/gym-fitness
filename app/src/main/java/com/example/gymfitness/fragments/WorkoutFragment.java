    package com.example.gymfitness.fragments;

    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.databinding.DataBindingUtil;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;

    import com.example.gymfitness.R;
    import com.example.gymfitness.adapters.WorkoutAdapter;
    import com.example.gymfitness.databinding.FragmentWorkoutBinding;

    public class WorkoutFragment  extends Fragment {
        private FragmentWorkoutBinding binding;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_workout, container, false);
            View view = binding.getRoot();
           // test du lieu chua hien thi
            //tao adapter
            WorkoutAdapter workoutAdapter = new WorkoutAdapter();
            binding.rvWorkoutItem.setAdapter(workoutAdapter);
            // bo cuc
            binding.rvWorkoutItem.setLayoutManager(new LinearLayoutManager(getContext()));
            return  view;
        }

    }
