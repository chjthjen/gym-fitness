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
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gymfitness.R;
import com.example.gymfitness.data.SignUpCallback;
import com.example.gymfitness.data.UserSignUp;
import com.example.gymfitness.data.Users;
import com.example.gymfitness.databinding.FragmentSignUpBinding;
import com.example.gymfitness.viewmodels.SignUpViewModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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

public class SignUpFragment extends Fragment {
    private SignUpViewModel viewModel;
    FragmentSignUpBinding binding;
    NavController navController;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;
    private ProgressDialog progressDialog;
    private static final int RC_SIGN_IN = 40;

    public SignUpFragment() {
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        navController = NavHostFragment.findNavController(SignUpFragment.this);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Đăng ký");
        progressDialog.setMessage("Đang đăng ký...");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        callbackManager = CallbackManager.Factory.create();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpWithGoogle();
            }
        });

        binding.facebookSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpWithFacebook();
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Đăng ký bị hủy.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getContext(), "Đăng ký Facebook không thành công.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signUpFragment_to_loginFragment);
            }
        });


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signUpFragment_to_loginFragment);

            }
        });
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    UserSignUp userSignUp =new UserSignUp(binding.edtEmailOrMobile.getText().toString(),binding.edtPassword.getText().toString(),binding.edtFullname.getText().toString());
                    viewModel.setUser(userSignUp);
                    if(viewModel.checkUserStatus()){
                        Toast.makeText(getActivity(), "Vui lòng đăng xuất tài khoản!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        viewModel.signUp(new SignUpCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getActivity(),"Đăng kí thành công",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(int errorCode) {
                                if(errorCode==-999){
                                    Toast.makeText(getActivity(),"Gmail này đã được dùng cho tài khoản khác",Toast.LENGTH_LONG).show();

                                }
                                else
                                    Toast.makeText(getActivity(),"Đăng kí thất bại. vui lòng thử lại",Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                } else {
                    // Nếu dữ liệu không hợp lệ, hiển thị thông báo lỗi
                    Toast.makeText(getActivity(), "Invalid input, Sign up failed", Toast.LENGTH_SHORT).show();
                }
            }
            private boolean validateInput() {
                String fullName = binding.edtFullname.getText().toString().trim();
                String emailOrMobile = binding.edtEmailOrMobile.getText().toString().trim();
                String password = binding.edtPassword.getText().toString().trim();
                String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();

                // Kiểm tra Full Name
                if (fullName.isEmpty()) {
                    binding.edtFullname.setError("Full Name cannot be empty");
                    return false;
                }

                // Kiểm tra Email hoặc Mobile Number
                if (emailOrMobile.isEmpty()) {
                    binding.edtEmailOrMobile.setError("Email or Mobile Number cannot be empty");
                    return false;
                }

                // Nếu input có ký tự '@', kiểm tra định dạng email
                if (emailOrMobile.contains("@")) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(emailOrMobile).matches()) {
                        binding.edtEmailOrMobile.setError("Invalid email format");
                        return false;
                    }
                } else {
                    // Nếu không phải email, kiểm tra định dạng số điện thoại
                    if (!Patterns.PHONE.matcher(emailOrMobile).matches()) {
                        binding.edtEmailOrMobile.setError("Invalid mobile number format");
                        return false;
                    }
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

                // Kiểm tra Confirm Password
                if (!confirmPassword.equals(password)) {
                    binding.edtConfirmPassword.setError("Passwords do not match");
                    return false;
                }

                // Nếu tất cả đều hợp lệ
                binding.edtFullname.setError(null);
                binding.edtEmailOrMobile.setError(null);
                binding.edtPassword.setError(null);
                binding.edtConfirmPassword.setError(null);
                return true;
            }
        });
    }

    private void signUpWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signUpWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Đăng ký không thành công.", Toast.LENGTH_SHORT).show();
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

                            navController.navigate(R.id.action_signUpFragment_to_homeFragment);

                        } else {
                            Toast.makeText(getContext(), "Xác thực không thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

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
                            navController.navigate(R.id.action_signUpFragment_to_homeFragment);

                        } else {
                            Toast.makeText(getContext(), "Xác thực không thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}