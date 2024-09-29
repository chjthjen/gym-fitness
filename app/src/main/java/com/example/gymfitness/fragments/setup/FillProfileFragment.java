package com.example.gymfitness.fragments.setup;

import static android.app.Activity.RESULT_OK;
import static com.example.gymfitness.receivers.NotificationReceiver.sendWelcomeNotification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gymfitness.R;
import com.example.gymfitness.activities.HomeActivity;
import com.example.gymfitness.databinding.FragmentFillProfileBinding;
import com.example.gymfitness.helpers.ValidationHelpers;
import com.example.gymfitness.viewmodels.SetUpViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FillProfileFragment extends Fragment {

    private FragmentFillProfileBinding binding;
    private SetUpViewModel setUpViewModel;
    private String fullname, nickname, email, phonenumber;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ExecutorService executorService;
    private NavController navController;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private static final int REQUEST_CODE_READ_MEDIA_IMAGES = 100;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 100;

    public FillProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fill_profile, container, false);

        setUpViewModel = new ViewModelProvider(requireActivity()).get(SetUpViewModel.class);

        sharedPreferences = getActivity().getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        // thread
        executorService = Executors.newCachedThreadPool();
        // Khởi tạo ActivityResultLauncher
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            binding.imageView9.setImageURI(selectedImageUri);
                            binding.imageView9.setTag(selectedImageUri.toString()); // Save image path to tag
                            setUpViewModel.setImagePath(selectedImageUri.toString()); // Update image path in ViewModel
                        }
                    }
                }
        );

        return binding.getRoot();
    }

    private void requestReadMediaImagesPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted; request it
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.READ_MEDIA_IMAGES},
                        REQUEST_CODE_READ_MEDIA_IMAGES); // Sử dụng REQUEST_CODE_READ_MEDIA_IMAGES
            } else {
                // Permission has already been granted; proceed with the operation
                openImagePicker();
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted; request it
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_READ_EXTERNAL_STORAGE);
            } else {
                // Permission has already been granted; proceed with the operation
                openImagePicker();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE || requestCode == REQUEST_CODE_READ_MEDIA_IMAGES) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted; proceed with the operation
                openImagePicker(); // Mở trình chọn hình ảnh ngay lập tức
            } else {
                // Permission denied; inform the user
                Toast.makeText(requireContext(), "Permission denied to read media images", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        // Xử lý sự kiện cho nút thay đổi hình ảnh
        binding.btnChangeImage.setOnClickListener(v -> {
            requestReadMediaImagesPermission();
        });
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFillProfile();
                boolean validateRS = validateFields();
                if (validateRS) {
                    setData();
                    executorService.execute(() -> {
                        setUpViewModel.saveUserInformation();
                        sendWelcomeNotification(getContext(), fullname);
                    });
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }

    private boolean validateFields() {
        boolean fullnameVLD = ValidationHelpers.validateEmpty(fullname, binding.edtFullName, "Fullname cannot be empty");
        boolean nicknameVLD = ValidationHelpers.validateEmpty(nickname, binding.edtNickname, "Nickname cannot be empty");
        boolean emailVLD = ValidationHelpers.validateEmail(email, binding.edtEmail);
        boolean phoneNBVLD = ValidationHelpers.validatePhoneNumber(phonenumber, binding.edtPhoneNumber);
        return fullnameVLD && nicknameVLD && emailVLD && phoneNBVLD;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent); // Launch image picker using ActivityResultLauncher
    }

    private void setData() {
        setUpViewModel.setFullname(fullname.trim());
        setUpViewModel.setNickname(nickname.trim());
        setUpViewModel.setEmail(email.trim());
        setUpViewModel.setPhonenumber(phonenumber.trim());
        setUpViewModel.setImagePath(binding.imageView9.getTag() != null ? binding.imageView9.getTag().toString() : null);
        editor.putInt("done", 1);
        editor.apply();
    }

    private void setFillProfile() {
        fullname = binding.edtFullName.getText().toString();
        nickname = binding.edtNickname.getText().toString();
        email = binding.edtEmail.getText().toString();
        phonenumber = binding.edtPhoneNumber.getText().toString();
    }
}