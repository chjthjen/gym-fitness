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

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymfitness.R;
import com.example.gymfitness.Users;
import com.example.gymfitness.databinding.FragmentLoginBinding;
import com.example.gymfitness.viewmodels.AuthViewModel;
import com.facebook.AccessToken;
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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class LoginFragment extends Fragment {

    private AuthViewModel viewModel;
    private FragmentLoginBinding binding;
    private NavController navController;
    ImageButton imgGG;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient nGoogleSignInClient;
    ProgressDialog progressDialog;

    private CallbackManager callbackManager;
    private ImageButton imgFB;


    int RC_SIGN_IN = 40;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(requireContext().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        navController = NavHostFragment.findNavController(LoginFragment.this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (validateInput()) {
                    // Nếu dữ liệu hợp lệ, hiển thị thông báo thành công + xử lí tiếp...
                    Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu dữ liệu không hợp lệ, hiển thị thông báo lỗi
                    Toast.makeText(getActivity(), "Invalid input, login failed", Toast.LENGTH_SHORT).show();
                }
            }
            private boolean validateInput () {
                String usernameOrEmail = binding.edtUsername.getText().toString().trim();
                String password = binding.edtPassword.getText().toString().trim();

                // Kiểm tra Username hoặc Email
                if (usernameOrEmail.isEmpty()) {
                    binding.edtUsername.setError("Username or Email cannot be empty");
                    return false;
                }

                // Kiểm tra định dạng email nếu input có chứa ký tự '@'
                if (usernameOrEmail.contains("@") && !Patterns.EMAIL_ADDRESS.matcher(usernameOrEmail).matches()) {
                    binding.edtUsername.setError("Invalid email format");
                    return false;
                }

                // Kiểm tra Password
                if (password.isEmpty()) {
                    binding.edtPassword.setError("Password cannot be empty");
                    return false;
                }

                if (password.length() < 6) {
                    binding.edtPassword.setError("Password must be at least 6 characters long");
                    return false;
                }

                // Nếu tất cả đều hợp lệ
                binding.edtUsername.setError(null);
                binding.edtPassword.setError(null);
                return true;
            }
        });

        imgGG = binding.btnGoogle;
        imgFB = binding.btnFacebook;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Đăng nhập");
        progressDialog.setMessage("Đang đăng nhập...");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        nGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        imgGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // Khởi tạo CallbackManager
        callbackManager = CallbackManager.Factory.create();

        // Facebook Login
        imgFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginFragment.this, Arrays.asList("email", "public_profile"));
            }
        });


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
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

        // Thiết lập sự kiện cho TextView để chuyển đến SignUpFragment
        TextView tvSignUp = binding.tvSignUp;
        tvSignUp.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

    }

    private void login() {
        Intent intent = nGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Xử lý kết quả đăng nhập Facebook
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }


        // Xử lý kết quả đăng nhập Google
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Đăng nhập không thành công.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Users users = new Users();
                            users.setUserId(user.getUid());
                            users.setName(user.getDisplayName());
                            users.setProfile(user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "default");

                            database.getReference().child("Users").child(user.getUid()).setValue(users);

                            navController.navigate(R.id.action_loginFragment_to_homeFragment);

                        } else {
                            Toast.makeText(getContext(), "Xác thực không thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
//
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Users users = new Users();
                            users.setUserId(user.getUid());
                            users.setName(user.getDisplayName());
                            users.setProfile(user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "default");

                            database.getReference().child("Users").child(user.getUid()).setValue(users);
                            navController.navigate(R.id.action_loginFragment_to_homeFragment);

                        } else {
                            Toast.makeText(getContext(), "Xác thực không thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}


// Facebook Login
//        callbackManager = CallbackManager.Factory.create();
//
//        imgFB.setOnClickListener(new View.OnClickListener() {
//        binding.btnLogin.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick (View view){
//                if (validateInput()) {
//                    // Nếu dữ liệu hợp lệ, hiển thị thông báo thành công + xử lí tiếp...
//                    Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Nếu dữ liệu không hợp lệ, hiển thị thông báo lỗi
//                    Toast.makeText(getActivity(), "Invalid input, login failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//                private boolean validateInput () {
//                String usernameOrEmail = binding.edtUsername.getText().toString().trim();
//                String password = binding.edtPassword.getText().toString().trim();
//
//                // Kiểm tra Username hoặc Email
//                if (usernameOrEmail.isEmpty()) {
//                    binding.edtUsername.setError("Username or Email cannot be empty");
//                    return false;
//                }
//
//                // Kiểm tra định dạng email nếu input có chứa ký tự '@'
//                if (usernameOrEmail.contains("@") && !Patterns.EMAIL_ADDRESS.matcher(usernameOrEmail).matches()) {
//                    binding.edtUsername.setError("Invalid email format");
//                    return false;
//                }
//
//                // Kiểm tra Password
//                if (password.isEmpty()) {
//                    binding.edtPassword.setError("Password cannot be empty");
//                    return false;
//                }
//
//                if (password.length() < 6) {
//                    binding.edtPassword.setError("Password must be at least 6 characters long");
//                    return false;
//                }
//
//                // Nếu tất cả đều hợp lệ
//                binding.edtUsername.setError(null);
//                binding.edtPassword.setError(null);
//                return true;
//            }
//            });
//        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener()
//
//            {
//
//                @Override
//                public void onClick (View v){
//                LoginManager.getInstance().logInWithReadPermissions(
//                        getActivity(), Arrays.asList("email", "public_profile")
//                );
//            }
//            });

//        LoginManager.getInstance().
//
//            registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//                @Override
//                public void onSuccess (LoginResult loginResult){
//                    handleFacebookAccessToken(loginResult.getAccessToken());
//                }
//
//                @Override
//                public void onCancel () {
//                    Toast.makeText(getContext(), "Đăng nhập bị hủy.", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onError (FacebookException error){
//                    Toast.makeText(getContext(), "Đăng nhập Facebook không thành công.", Toast.LENGTH_SHORT).show();
//                }
//            });



//

