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
import com.example.smartinphoneprojectandroid.databinding.ActivityChangeProfileBinding;
import com.example.smartinphoneprojectandroid.databinding.ActivityRegisterBinding;
import com.example.smartinphoneprojectandroid.user.GetDataUser;
import com.example.smartinphoneprojectandroid.user.LoginResponse;
import com.example.smartinphoneprojectandroid.user.RegisterResponse;
import com.example.smartinphoneprojectandroid.userAct.MainActivityUser;
import com.example.smartinphoneprojectandroid.utils.RealPathUtil;
import com.example.smartinphoneprojectandroid.utils.RetrofitClient;
import com.example.smartinphoneprojectandroid.utils.SessionManager;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfileActivity extends AppCompatActivity {
    ActivityChangeProfileBinding binding;
    public String path;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sm = new SessionManager(ChangeProfileActivity.this);
        HashMap<String, String> map = sm.getDetailLogin();
        binding.emailInput.setText(map.get(SessionManager.KEY_EMAIL));
        binding.nameInput.setText(map.get(SessionManager.KEY_NAME));
        binding.usernameInput.setText(map.get(SessionManager.KEY_USERNAME));


        clickListener();
    }

    private void clickListener(){
        HashMap<String, String> map = sm.getDetailLogin();
        binding.selectAvatarButton.setOnClickListener(v->{
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 10);
            } else {
                ActivityCompat.requestPermissions(ChangeProfileActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
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
            if (binding.emailInput.getText().toString().isEmpty() || binding.nameInput.getText().toString().isEmpty() || binding.usernameInput.getText().toString().isEmpty()){
                Toast.makeText(ChangeProfileActivity.this, "Please fill the required field!", Toast.LENGTH_SHORT).show();
            }  else {
                changeUser(binding.emailInput.getText().toString(), binding.nameInput.getText().toString(), binding.usernameInput.getText().toString());
            }
        });

        binding.backButton.setOnClickListener(v->{
            if (map.get(SessionManager.KEY_ROLE).equals("admin")) {
                Intent i = new Intent(ChangeProfileActivity.this, MainActivityAdmin.class);
                startActivity(i);
            } else {
                Intent i = new Intent(ChangeProfileActivity.this, MainActivityUser.class);
                startActivity(i);
            }
        });

        binding.submitAvatarButton.setOnClickListener(v->{
            if (path == null) {
                Toast.makeText(ChangeProfileActivity.this, "Please select avatar!", Toast.LENGTH_SHORT).show();
            } else {
                changeAvatar();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Context context = ChangeProfileActivity.this;
            path = RealPathUtil.getRealPath(context, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            binding.avatarRegister.setImageBitmap(bitmap);
        }
    }

    public void changeAvatar(){
        HashMap<String, String> map = sm.getDetailLogin();
        Log.e(TAG, "clickListener: disini blok 2"+ path.toString());
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        Log.e(TAG, "clickListener: disini blok 3"+ body.toString());

        GetDataUser service = RetrofitClient.myRetrofitInstance().create(GetDataUser.class);
        Call<LoginResponse> call = service.changeAvatar("Bearer "+ map.get(SessionManager.KEY_TOKEN), map.get(SessionManager.KEY_ID), body);

        //Execute the request asynchronously
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
                        Toast.makeText(getApplicationContext(), "Change Avatar Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Change Avatar Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Change Avatar Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changeUser(String email, String name, String username){
        HashMap<String, String> map = sm.getDetailLogin();
        RequestBody reg_email = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody reg_name = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody reg_username = RequestBody.create(MediaType.parse("multipart/form-data"), username);

        GetDataUser service = RetrofitClient.myRetrofitInstance().create(GetDataUser.class);
        Call<LoginResponse> call = service.changeProfile("Bearer "+ map.get(SessionManager.KEY_TOKEN), map.get(SessionManager.KEY_ID), reg_email,reg_name,reg_username);

        //Execute the request asynchronously
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
                        Toast.makeText(getApplicationContext(), "Change Profile Successfully", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("email", binding.emailInput.getText().toString());
                        Intent i= new Intent(ChangeProfileActivity.this, LoginActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Change Profile Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Change Profile Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}