package com.example.smartinphoneprojectandroid;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.smartinphoneprojectandroid.adminAct.MainActivityAdmin;
import com.example.smartinphoneprojectandroid.databinding.ActivityChangePasswordBinding;
import com.example.smartinphoneprojectandroid.databinding.ActivityChangeProfileBinding;
import com.example.smartinphoneprojectandroid.user.GetDataUser;
import com.example.smartinphoneprojectandroid.user.LoginResponse;
import com.example.smartinphoneprojectandroid.userAct.MainActivityUser;
import com.example.smartinphoneprojectandroid.utils.RealPathUtil;
import com.example.smartinphoneprojectandroid.utils.RetrofitClient;
import com.example.smartinphoneprojectandroid.utils.SessionManager;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    ActivityChangePasswordBinding binding;
    public String path;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sm = new SessionManager(ChangePasswordActivity.this);

        clickListener();
    }

    private void clickListener(){
        HashMap<String, String> map = sm.getDetailLogin();

        binding.submitRegister.setOnClickListener(v->{
            Log.e(TAG, "clickListener: disini blok");
            if (binding.passwordInput.getText().toString().isEmpty()) {
                binding.passwordInput.setError("Required");
            }
            if (binding.confpassInput.getText().toString().isEmpty()) {
                binding.confpassInput.setError("Required");
            }
            if (binding.passwordInput.getText().toString().isEmpty() || binding.confpassInput.getText().toString().isEmpty()){
                Toast.makeText(ChangePasswordActivity.this, "Please fill the required field!", Toast.LENGTH_SHORT).show();
            }  else {
                changePass(binding.passwordInput.getText().toString(), binding.confpassInput.getText().toString());
            }
        });

        binding.backButton.setOnClickListener(v->{
            if (map.get(SessionManager.KEY_ROLE).equals("admin")) {
                Intent i = new Intent(ChangePasswordActivity.this, MainActivityAdmin.class);
                startActivity(i);
            } else {
                Intent i = new Intent(ChangePasswordActivity.this, MainActivityUser.class);
                startActivity(i);
            }
        });
    }

    public void changePass(String password,String confpass){
        HashMap<String, String> map = sm.getDetailLogin();
        RequestBody reg_pass = RequestBody.create(MediaType.parse("multipart/form-data"), password);
        RequestBody reg_confpass = RequestBody.create(MediaType.parse("multipart/form-data"), confpass);

        GetDataUser service = RetrofitClient.myRetrofitInstance().create(GetDataUser.class);
        Call<LoginResponse> call = service.changePassword("Bearer "+ map.get(SessionManager.KEY_TOKEN), map.get(SessionManager.KEY_ID), reg_pass, reg_confpass);

        //Execute the request asynchronously
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
                        Toast.makeText(getApplicationContext(), "Change Password Successfully", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("email", map.get(SessionManager.KEY_EMAIL));
                        Intent i= new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Change Password Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Change Password Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}