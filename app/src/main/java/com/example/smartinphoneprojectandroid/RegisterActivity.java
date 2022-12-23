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

import com.example.smartinphoneprojectandroid.databinding.ActivityRegisterBinding;
import com.example.smartinphoneprojectandroid.user.GetDataUser;
import com.example.smartinphoneprojectandroid.user.RegisterResponse;
import com.example.smartinphoneprojectandroid.utils.RealPathUtil;
import com.example.smartinphoneprojectandroid.utils.RetrofitClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    public String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clickListener();
    }

    private void clickListener(){
        binding.selectAvatarButton.setOnClickListener(v->{
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 10);
            } else {
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        });

        binding.submitRegister.setOnClickListener(v->{
            Log.e(TAG, "clickListener: disini blok");
            if (binding.emailInput.getText().toString().isEmpty()) {
                binding.emailInput.setError("Required");
            }
            if (binding.nameInput.getText().toString().isEmpty()) {
                binding.nameInput.setError("Required");
            }
            if (binding.usernameInput.getText().toString().isEmpty()) {
                binding.usernameInput.setError("Required");
            }
            if (binding.passwordInput.getText().toString().isEmpty()) {
                binding.passwordInput.setError("Required");
            }
            if (binding.confpassInput.getText().toString().isEmpty()) {
                binding.confpassInput.setError("Required");
            }
            if (binding.emailInput.getText().toString().isEmpty() || binding.nameInput.getText().toString().isEmpty() || binding.usernameInput.getText().toString().isEmpty() || binding.passwordInput.getText().toString().isEmpty() || binding.confpassInput.getText().toString().isEmpty()){
                Toast.makeText(RegisterActivity.this, "Please fill the required field!", Toast.LENGTH_SHORT).show();
            } else if (path == null) {
                Toast.makeText(RegisterActivity.this, "Please select avatar", Toast.LENGTH_SHORT).show();
            } else {
                addUser(binding.emailInput.getText().toString(), binding.nameInput.getText().toString(), binding.usernameInput.getText().toString(), binding.passwordInput.getText().toString(), binding.confpassInput.getText().toString());
            }
        });

        binding.backButton.setOnClickListener(v->{
            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(i);
        });

        binding.toLoginButton.setOnClickListener(v->{
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Context context = RegisterActivity.this;
            path = RealPathUtil.getRealPath(context, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            binding.avatarRegister.setImageBitmap(bitmap);
        }
    }

    public void addUser(String email, String name, String username, String password, String confpassword){
        Log.e(TAG, "clickListener: disini blok 2"+ path.toString());
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        Log.e(TAG, "clickListener: disini blok 3"+ body.toString());
        RequestBody reg_email = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody reg_name = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody reg_username = RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody reg_password = RequestBody.create(MediaType.parse("multipart/form-data"), password);
        RequestBody reg_confpassword = RequestBody.create(MediaType.parse("multipart/form-data"), confpassword);

        GetDataUser service = RetrofitClient.myRetrofitInstance().create(GetDataUser.class);
        Call<RegisterResponse> call = service.register(body, reg_email,reg_name,reg_username,reg_password,reg_confpassword);

        //Execute the request asynchronously
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
                        Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("email", binding.emailInput.getText().toString());
                        Intent i= new Intent(RegisterActivity.this, LoginActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}