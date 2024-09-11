package com.example.gymfitness.viewmodels;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SetUpViewModel extends ViewModel {
    private final MutableLiveData<String> gender = new MutableLiveData<>();
    private final MutableLiveData<Integer> age = new MutableLiveData<>();
    private final MutableLiveData<Float> weight = new MutableLiveData<>();
    private final MutableLiveData<Integer> height = new MutableLiveData<>();
    private final MutableLiveData<String> goal = new MutableLiveData<>();
    private final MutableLiveData<String> level = new MutableLiveData<>();

    public void setGender(String gender)
    {
        this.gender.setValue(gender);
    }

    public LiveData<String> getGender()
    {
        return gender;
    }

    public void setAge(int age)
    {
        this.age.setValue(age);
    }

    public LiveData<Integer> getAge()
    {
        return age;
    }

    public void setWeight(float weight)
    {
        this.weight.setValue(weight);
    }

    public LiveData<Float> getWeight()
    {
        return weight;
    }

    public void setHeight(int height)
    {
        this.height.setValue(height);
    }

    public LiveData<Integer> getHeight()
    {
        return height;
    }

    public void setGoal(String goal)
    {
        this.goal.setValue(goal);
    }

    public LiveData<String> getGoal()
    {
        return goal;
    }

    public void setLevel(String level)
    {
        this.level.setValue(level);
    }

    public LiveData<String> getLevel()
    {
        return level;
    }


}
