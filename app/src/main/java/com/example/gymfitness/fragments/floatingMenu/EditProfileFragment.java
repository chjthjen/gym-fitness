package com.example.gymfitness.fragments.floatingMenu;

import static android.app.Activity.RESULT_OK;

import static com.example.gymfitness.utils.StringUtils.capitalizeFirstLetter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.UserInformation;
import com.example.gymfitness.databinding.FragmentEditprofileBinding;
import com.example.gymfitness.receivers.NotificationReceiver;
import com.example.gymfitness.viewmodels.EditProfileViewModel;
import com.example.gymfitness.viewmodels.SharedViewModel;
import com.example.gymfitness.viewmodelsfactory.EditProfileViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class EditProfileFragment extends Fragment {

    private FragmentEditprofileBinding binding;
    private EditProfileViewModel editProfileViewModel;
    private SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private static final int REQUEST_CODE_READ_MEDIA_IMAGES = 100;
    private boolean isProfileChanged = false;
    private UserInformation initialUserInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_editprofile, container, false);

        UserInformationDAO userInformationDAO = FitnessDB.getInstance(getContext()).userInformationDAO();
        EditProfileViewModelFactory factory = new EditProfileViewModelFactory(userInformationDAO);
        editProfileViewModel = new ViewModelProvider(this, factory).get(EditProfileViewModel.class);
        editProfileViewModel.loadUserInformation();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        SimpleDateFormat dobFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        editProfileViewModel.getUserInformation().observe(getViewLifecycleOwner(), userInfo -> {
            if (userInfo != null) {
                initialUserInfo = userInfo;

                if (userInfo.getDob() != null) {
                    String dateOfBirth = dobFormat.format(userInfo.getDob());
                    binding.tvDateOfBirth.setText(dateOfBirth);
                    binding.edtDateOfBirth.setText(dateOfBirth);
                }

                int imageResId = "male".equalsIgnoreCase(userInfo.getGender()) ? R.drawable.man_profile :
                        "female".equalsIgnoreCase(userInfo.getGender()) ? R.drawable.wonman_profile : R.drawable.account_image;

                Glide.with(requireContext())
                        .load(userInfo.getImagePath() != null ? userInfo.getImagePath() : imageResId)
                        .placeholder(R.drawable.bgr_onboarding_2d)
                        .error(R.drawable.arrow_next)
                        .into(binding.imgProfile);

                binding.tvNameProfile.setText(capitalizeFirstLetter(userInfo.getFullname()));
                binding.tvEmail.setText(userInfo.getEmail());
                binding.tvWeight.setText(userInfo.getWeight() + " KG");
                binding.tvOld.setText(String.valueOf(userInfo.getAge()));
                binding.tvHeigh.setText(userInfo.getHeight() + " CM");
                binding.editFullname.setText(userInfo.getFullname());
                binding.edtEmail.setText(userInfo.getEmail());
                binding.edtPhoneNumber.setText(userInfo.getPhonenumber());
                binding.edtWeight.setText(String.valueOf(userInfo.getWeight()));
                binding.edtHeigh.setText(String.valueOf(userInfo.getHeight()));
            }
        });

        binding.btnUpDate.setEnabled(false);

        TextWatcher textWatcher = new SimpleTextWatcher();
        binding.editFullname.addTextChangedListener(textWatcher);
        binding.edtEmail.addTextChangedListener(textWatcher);
        binding.edtPhoneNumber.addTextChangedListener(textWatcher);
        binding.edtWeight.addTextChangedListener(textWatcher);
        binding.edtHeigh.addTextChangedListener(textWatcher);
        binding.edtDateOfBirth.addTextChangedListener(textWatcher);

        binding.btnUpDate.setOnClickListener(v -> {
            if (!isProfileChanged) {
                Toast.makeText(requireContext(), "No changes to update.", Toast.LENGTH_SHORT).show();
                return;
            }
            UserInformation updatedInfo = editProfileViewModel.getUserInformation().getValue();
            if (updatedInfo != null) {
                updatedInfo.setFullname(binding.editFullname.getText().toString());
                updatedInfo.setEmail(binding.edtEmail.getText().toString());
                updatedInfo.setPhonenumber(binding.edtPhoneNumber.getText().toString());

                try {
                    Date dateOfBirth = dobFormat.parse(binding.edtDateOfBirth.getText().toString());
                    updatedInfo.setDob(dateOfBirth);
                    updatedInfo.setAge(calculateAgeFromDob(dateOfBirth));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                updatedInfo.setHeight(Integer.parseInt(binding.edtHeigh.getText().toString()));
                updatedInfo.setWeight(Float.parseFloat(binding.edtWeight.getText().toString()));

                editProfileViewModel.updateUserInformation(updatedInfo);
                sharedViewModel.setUsername(updatedInfo);
                NotificationReceiver.sendProfileUpdateNotification(requireContext(), updatedInfo.getFullname());
                isProfileChanged = false;
                binding.btnUpDate.setEnabled(false);
            }
        });

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            UserInformation userInfo = editProfileViewModel.getUserInformation().getValue();
                            if (userInfo != null) {
                                userInfo.setImagePath(selectedImageUri.toString());
                                Glide.with(requireContext())
                                        .load(selectedImageUri)
                                        .placeholder(R.drawable.bgr_onboarding_2d)
                                        .error(R.drawable.arrow_next)
                                        .into(binding.imgProfile);


                                isProfileChanged = true;
                                binding.btnUpDate.setEnabled(true);
                            }
                        }
                    }
                }
        );

        binding.imgProfile.setOnClickListener(v -> requestReadMediaImagesPermission());
        binding.btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        return binding.getRoot();
    }

    private void requestReadMediaImagesPermission() {
        String permission = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU ?
                android.Manifest.permission.READ_MEDIA_IMAGES : android.Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{permission}, REQUEST_CODE_READ_MEDIA_IMAGES);
        } else {
            openImagePicker();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_MEDIA_IMAGES && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImagePicker();
        } else {
            Toast.makeText(requireContext(), "Permission denied to read images", Toast.LENGTH_SHORT).show();
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private int calculateAgeFromDob(Date dob) {
        if (dob == null) return 0;

        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(dob);

        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) &&
                        today.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }
        return age;
    }

    private class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (initialUserInfo == null) return;

            isProfileChanged = !s.toString().equals(initialUserInfo.getFullname()) &&
                    !s.toString().equals(initialUserInfo.getEmail()) &&
                    !s.toString().equals(initialUserInfo.getPhonenumber()) &&
                    !s.toString().equals(String.valueOf(initialUserInfo.getWeight())) &&
                    !s.toString().equals(String.valueOf(initialUserInfo.getHeight()));

            binding.btnUpDate.setEnabled(isProfileChanged);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        binding = null;
    }
}