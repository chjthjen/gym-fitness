package com.example.gymfitness.fragments.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gymfitness.retrofit.GymApi;
import com.example.gymfitness.R;
import com.example.gymfitness.data.UserAccount;
import com.example.gymfitness.data.UserInfo;
import com.example.gymfitness.data.Users;
import com.example.gymfitness.activities.HomeActivity;
import com.example.gymfitness.databinding.FragmentLoginBinding;
import com.example.gymfitness.helpers.ValidationEmail;
import com.example.gymfitness.retrofit.RetrofitInstance;
import com.example.gymfitness.utils.Resource;
import com.example.gymfitness.viewmodels.LoginViewModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {

    // Xai viewmodel
    private LoginViewModel viewModel;
    private FragmentLoginBinding binding;
    private NavController navController;
    private ImageButton imgGG;
    private ImageButton imgFB;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private GoogleSignInClient googleSignInClient;
    private ProgressDialog progressDialog;
    private CallbackManager callbackManager;
    private GymApi apiService;

    private static final int RC_SIGN_IN = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(requireContext().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        apiService = RetrofitInstance.getApiService();
        navController = NavHostFragment.findNavController(this);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFirebase();
        initializeGoogleSignIn();
        initializeFacebookLogin();
        setupUI();
    }

    private void initializeFirebase() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    private void initializeGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    private void initializeFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                viewModel.handleFacebookAccessToken(loginResult.getAccessToken());  // This method should be in LoginViewModel
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Đăng nhập bị hủy.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getContext(), "Đăng nhập Facebook không thành công.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupUI() {
        imgGG = binding.btnGoogle;
        imgFB = binding.btnFacebook;
        progressDialog = new ProgressDialog(getContext());

        imgGG.setOnClickListener(v -> {
            progressDialog.setMessage("Logging in...");
            progressDialog.show();
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        imgFB.setOnClickListener(v ->
                LoginManager.getInstance().logInWithReadPermissions(LoginFragment.this, Arrays.asList("email", "public_profile"))
        );

        TextView tvSignUp = binding.tvSignUp;
        tvSignUp.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_signUpFragment));

        binding.tvForgotPassword.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_forgottenPasswordFragment22));

        Button btnLogin = binding.btnLogin;
        btnLogin.setOnClickListener(v -> {
            String usernameOrEmail = binding.edtUsername.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();
            if (ValidationEmail.validateInputEmail(usernameOrEmail, password, binding.edtUsername, binding.edtPassword)) {
                viewModel.loginWithEmail(usernameOrEmail, password);
            }
        });

        viewModel.getCurrentUser().observe(getViewLifecycleOwner(), resource -> {
            if (resource instanceof Resource.Success) {
                FirebaseUser user = ((Resource.Success<FirebaseUser>) resource).getData();
                if (user != null) {
                    saveUserToDatabase(user);
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            } else if (resource instanceof Resource.Loading) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            } else if (resource instanceof Resource.Unspecified) {

            }

            progressDialog.dismiss();
        });
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), resource -> {
            if (resource instanceof Resource.Error) {
                Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                viewModel.clearErrorMessage();
            }
        });
    }

    private void saveUserToDatabase(FirebaseUser user) {
        Users users = new Users();
        users.setUserId(user.getUid());
        users.setName(user.getDisplayName());
        users.setProfile(user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "default");
        saveUser(user);
        database.getReference().child("Users").child(user.getUid()).setValue(users);
    }

    private void saveUser(FirebaseUser user) {
        // Tạo đối tượng UserAccount
        UserAccount userAccount = new UserAccount(
                user.getUid(),              // user_id
                user.getDisplayName(),      // user_fullname
                user.getEmail(),            // user_email
                "",                         // user_phone (có thể để trống hoặc thêm thông tin nếu có)
                "",                         // user_password (có thể để trống hoặc thêm thông tin nếu có)
                0                        // isNormalUser (hoặc false tùy vào logic ứng dụng của bạn)
        );

        // Tạo đối tượng UserInfo
        UserInfo userInfo = new UserInfo(
                null,               // gender
                0,                  // weight
                0,                  // height
                0,                  // age
                null,               // DoB
                user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "default", // user_image
                user.getUid(),      // user_id
                5,                  // goal_id
                1                   // level_id
        );

        // Lưu UserAccount
        Call<ResponseBody> accountCall = apiService.saveUserAccount(userAccount);
        accountCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.e("user account:", "Success");
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("user account", "Error: " + errorBody);
                    } catch (IOException e) {
                        Log.e("SaveUserAccount", "Error", e);
                    }
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.e("Network Error", "Please check your connection.");
                } else {
                    Log.e("Conversion Error", "An unexpected error occurred.");
                }
            }
        });

        // Lưu UserInfo
        Call<ResponseBody> infoCall = apiService.saveUserInfo(userInfo);
        infoCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.e("User info:", "Success");
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("User info", "Error: " + errorBody);
                    } catch (IOException e) {
                        Log.e("User info", "Error", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("User info", "Failure: " + t.getMessage());
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            viewModel.handleGoogleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}

