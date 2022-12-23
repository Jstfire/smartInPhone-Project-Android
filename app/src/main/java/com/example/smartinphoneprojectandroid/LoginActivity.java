package com.example.smartinphoneprojectandroid;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.smartinphoneprojectandroid.adminAct.MainActivityAdmin;
import com.example.smartinphoneprojectandroid.databinding.ActivityLoginBinding;
import com.example.smartinphoneprojectandroid.databinding.ActivityRegisterBinding;
import com.example.smartinphoneprojectandroid.user.GetDataUser;
import com.example.smartinphoneprojectandroid.user.LoginResponse;
import com.example.smartinphoneprojectandroid.user.RegisterResponse;
import com.example.smartinphoneprojectandroid.user.UserModel;
import com.example.smartinphoneprojectandroid.userAct.MainActivityUser;
import com.example.smartinphoneprojectandroid.utils.RetrofitClient;
import com.example.smartinphoneprojectandroid.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            binding.emailInputLogin.setText(bundle.getString("email"));
        }

        sm = new SessionManager(LoginActivity.this);

        clickListenerLogin();
    }

    private void clickListenerLogin(){
        binding.submitLogin.setOnClickListener(v->{
            Log.e(TAG, "clickListener: disini blok");
            if (binding.emailInputLogin.getText().toString().isEmpty()) {
                binding.emailInputLogin.setError("Required");
            }
            if (binding.passwordInputLogin.getText().toString().isEmpty()) {
                binding.passwordInputLogin.setError("Required");
            }

            if (binding.emailInputLogin.getText().toString().isEmpty() || binding.passwordInputLogin.getText().toString().isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill the required field!", Toast.LENGTH_SHORT).show();
            } else {
                getLogged(binding.emailInputLogin.getText().toString(), binding.passwordInputLogin.getText().toString());
            }

        });

        binding.back2Button.setOnClickListener(v->{
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        });

        binding.toRegisterButton.setOnClickListener(v->{
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        });
    }

    public void getLogged(String email, String password) {
        Log.e(TAG, "getLogged: disini bois");
        GetDataUser service = RetrofitClient.myRetrofitInstance().create(GetDataUser.class);
        Call<LoginResponse> call = service.login(email, password);
        //Execute the request asynchronously
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
                        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                        getInfo(response.body().getToken());
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed 1", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed 2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getInfo(String token) {
        Log.e(TAG, "getInfo: disini woi");
        Log.e(TAG, "getInfo: "+ token );
        GetDataUser service = RetrofitClient.myRetrofitInstance().create(GetDataUser.class);
        Call<UserModel> call = service.getUserInfo("Bearer "+token);
        //Execute the request asynchronously
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.e(TAG, "onResponse2: "+ response.raw() );
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
                        Toast.makeText(getApplicationContext(), "Token Valid", Toast.LENGTH_SHORT).show();
                        if (response.body().getRole().toString().equals("admin")) {
                            sm.storeLoginAdmin(token,
                                    response.body().getId(),
                                    response.body().getEmail(),
                                    response.body().getName(),
                                    response.body().getUsername(),
                                    response.body().getAvatar(),
                                    response.body().getRole());
                            Intent i = new Intent(LoginActivity.this, MainActivityAdmin.class);
                            startActivity(i);
                        } else {
                            sm.storeLoginUser(token,
                                    response.body().getId(),
                                    response.body().getEmail(),
                                    response.body().getName(),
                                    response.body().getUsername(),
                                    response.body().getAvatar(),
                                    response.body().getRole());
                            Intent i = new Intent(LoginActivity.this, MainActivityUser.class);
                            startActivity(i);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Token Invalid 1", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Token Invalid 2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}