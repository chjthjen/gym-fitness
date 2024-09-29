package com.example.gymfitness.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.data.entities.UserInformation;

public class SetUpViewModel extends ViewModel {
    private final MutableLiveData<String> gender = new MutableLiveData<>();
    private final MutableLiveData<Integer> age = new MutableLiveData<>();
    private final MutableLiveData<Float> weight = new MutableLiveData<>();
    private final MutableLiveData<Integer> height = new MutableLiveData<>();
    private final MutableLiveData<String> goal = new MutableLiveData<>();
    private final MutableLiveData<String> level = new MutableLiveData<>();
    private final MutableLiveData<String> fullname = new MutableLiveData<>();
    private final MutableLiveData<String> nickname = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> phonenumber = new MutableLiveData<>();
    private final MutableLiveData<String> imagePath = new MutableLiveData<>();


    private final UserInformationDAO userInformationDAO;

    public SetUpViewModel(UserInformationDAO userInformationDAO)
    {
        this.userInformationDAO = userInformationDAO;
    }

    public void saveUserInformation()
    {
        UserInformation userInformation = new UserInformation();
        userInformation.setGender(getGender().getValue());
        userInformation.setAge(getAge().getValue());
        userInformation.setWeight(getWeight().getValue());
        userInformation.setHeight(getHeight().getValue());
        userInformation.setGoal(getGoal().getValue());
        userInformation.setLevel(getLevel().getValue());
        userInformation.setFullname(getFullname().getValue());
        userInformation.setNickname(getNickname().getValue());
        userInformation.setEmail(getEmail().getValue());
        userInformation.setPhonenumber(getPhonenumber().getValue());
        userInformation.setImagePath(getImagePath().getValue());
        userInformationDAO.upsertWorkout(userInformation);
        Log.d("SetUpViewModel", "Saving user information with image path: " + getImagePath().getValue());

        userInformationDAO.upsertWorkout(userInformation);

        // Log after saving
        Log.d("SetUpViewModel", "User information saved successfully");

    }
    public void setImagePath(String imagePath) {
        this.imagePath.setValue(imagePath);
    }

    public LiveData<String> getImagePath() {
        return imagePath;
    }
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

    public void setFullname(String fullname)
    {
        this.fullname.setValue(fullname);
    }

    public LiveData<String> getFullname()
    {
        return fullname;
    }

    public void setNickname(String nickname)
    {
        this.nickname.setValue(nickname);
    }

    public LiveData<String> getNickname()
    {
        return nickname;
    }

    public void setEmail(String email)
    {
        this.email.setValue(email);
    }

    public LiveData<String> getEmail()
    {
        return email;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber.setValue(phonenumber);
    }

    public LiveData<String> getPhonenumber()
    {
        return phonenumber;
    }




}
