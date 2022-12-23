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
import com.example.smartinphoneprojectandroid.databinding.ActivityAddHpactivityBinding;
import com.example.smartinphoneprojectandroid.databinding.ActivityUpdateHpuserBinding;
import com.example.smartinphoneprojectandroid.handphone.GetData;
import com.example.smartinphoneprojectandroid.handphone.UpdateHPResponse;
import com.example.smartinphoneprojectandroid.user.GetDataUser;
import com.example.smartinphoneprojectandroid.user.LoginResponse;
import com.example.smartinphoneprojectandroid.userAct.MainActivityUser;
import com.example.smartinphoneprojectandroid.userAct.UpdateHPUserActivity;
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

public class AddHPActivity extends AppCompatActivity {
    ActivityAddHpactivityBinding binding;
    public String path;
    String id;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddHpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sm = new SessionManager(AddHPActivity.this);

        clickListener();
    }

    public void clickListener() {
        HashMap<String, String> map = sm.getDetailLogin();
        binding.back2Button.setOnClickListener(v->{
            if (map.get(SessionManager.KEY_ROLE).equals("admin")) {
                Intent i = new Intent(AddHPActivity.this, MainActivityAdmin.class);
                startActivity(i);
            } else {
                Intent i = new Intent(AddHPActivity.this, MainActivityUser.class);
                startActivity(i);
            }
        });

        binding.selectImageHP.setOnClickListener(v->{
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 10);
            } else {
                ActivityCompat.requestPermissions(AddHPActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        });

        binding.submitUpdateHP.setOnClickListener(v->{
            if (binding.nameInput.getText().toString().isEmpty()) {
                binding.nameInput.setError("Required");
            }
            if (binding.networkInput.getText().toString().isEmpty()) {
                binding.networkInput.setError("Required");
            }
            if (binding.launchInput.getText().toString().isEmpty()) {
                binding.launchInput.setError("Required");
            }
            if (binding.bodyInput.getText().toString().isEmpty()) {
                binding.bodyInput.setError("Required");
            }
            if (binding.displayInput.getText().toString().isEmpty()) {
                binding.displayInput.setError("Required");
            }
            if (binding.platformInput.getText().toString().isEmpty()) {
                binding.platformInput.setError("Required");
            }
            if (binding.memoryInput.getText().toString().isEmpty()) {
                binding.memoryInput.setError("Required");
            }
            if (binding.maincamInput.getText().toString().isEmpty()) {
                binding.maincamInput.setError("Required");
            }
            if (binding.selfcamInput.getText().toString().isEmpty()) {
                binding.selfcamInput.setError("Required");
            }
            if (binding.soundInput.getText().toString().isEmpty()) {
                binding.soundInput.setError("Required");
            }
            if (binding.commsInput.getText().toString().isEmpty()) {
                binding.commsInput.setError("Required");
            }
            if (binding.featuresInput.getText().toString().isEmpty()) {
                binding.featuresInput.setError("Required");
            }
            if (binding.batteryInput.getText().toString().isEmpty()) {
                binding.batteryInput.setError("Required");
            }
            if (binding.testsInput.getText().toString().isEmpty()) {
                binding.testsInput.setError("Required");
            }
            if (binding.nameInput.getText().toString().isEmpty() ||
                    binding.networkInput.getText().toString().isEmpty() ||
                    binding.launchInput.getText().toString().isEmpty() ||
                    binding.bodyInput.getText().toString().isEmpty() ||
                    binding.displayInput.getText().toString().isEmpty() ||
                    binding.platformInput.getText().toString().isEmpty() ||
                    binding.memoryInput.getText().toString().isEmpty() ||
                    binding.maincamInput.getText().toString().isEmpty() ||
                    binding.selfcamInput.getText().toString().isEmpty() ||
                    binding.soundInput.getText().toString().isEmpty() ||
                    binding.commsInput.getText().toString().isEmpty() ||
                    binding.featuresInput.getText().toString().isEmpty() ||
                    binding.batteryInput.getText().toString().isEmpty() ||
                    binding.testsInput.getText().toString().isEmpty())
            {
                Toast.makeText(AddHPActivity.this, "Please fill the required field!", Toast.LENGTH_SHORT).show();
            } else if (path == null){
                Toast.makeText(AddHPActivity.this, "Please select image!", Toast.LENGTH_SHORT).show();
            } else {
                submitFunc(
                        binding.nameInput.getText().toString(),
                        binding.networkInput.getText().toString(),
                        binding.launchInput.getText().toString(),
                        binding.bodyInput.getText().toString(),
                        binding.displayInput.getText().toString(),
                        binding.platformInput.getText().toString(),
                        binding.memoryInput.getText().toString(),
                        binding.maincamInput.getText().toString(),
                        binding.selfcamInput.getText().toString(),
                        binding.soundInput.getText().toString(),
                        binding.commsInput.getText().toString(),
                        binding.featuresInput.getText().toString(),
                        binding.batteryInput.getText().toString(),
                        binding.testsInput.getText().toString()
                );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Context context = AddHPActivity.this;
            path = RealPathUtil.getRealPath(context, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            binding.imageHP.setImageBitmap(bitmap);
        }
    }

    public void submitFunc(String  name, String  network, String  launch, String  bodies, String  display, String platform, String memory, String maincam, String selfcam, String sound, String comms, String features, String battery, String tests){
        HashMap<String, String> map = sm.getDetailLogin();
        Log.e(TAG, "clickListener: disini blok 2"+ path.toString());
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part gambar = MultipartBody.Part.createFormData("phone_photo", file.getName(), requestFile);
        Log.e(TAG, "clickListener: disini blok 3"+ gambar.toString());
        RequestBody reg_name = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody reg_network = RequestBody.create(MediaType.parse("multipart/form-data"), network);
        RequestBody reg_launch = RequestBody.create(MediaType.parse("multipart/form-data"), launch);
        RequestBody reg_body = RequestBody.create(MediaType.parse("multipart/form-data"), bodies);
        RequestBody reg_display = RequestBody.create(MediaType.parse("multipart/form-data"), display);
        RequestBody reg_platform = RequestBody.create(MediaType.parse("multipart/form-data"), platform);
        RequestBody reg_memory = RequestBody.create(MediaType.parse("multipart/form-data"), memory);
        RequestBody reg_maincam = RequestBody.create(MediaType.parse("multipart/form-data"), maincam);
        RequestBody reg_selfcam = RequestBody.create(MediaType.parse("multipart/form-data"), selfcam);
        RequestBody reg_sound = RequestBody.create(MediaType.parse("multipart/form-data"), sound);
        RequestBody reg_comms = RequestBody.create(MediaType.parse("multipart/form-data"), comms);
        RequestBody reg_features = RequestBody.create(MediaType.parse("multipart/form-data"), features);
        RequestBody reg_battery = RequestBody.create(MediaType.parse("multipart/form-data"), battery);
        RequestBody reg_tests = RequestBody.create(MediaType.parse("multipart/form-data"), tests);

        GetData service = RetrofitClient.myRetrofitInstance().create(GetData.class);
//        Log.e(TAG, "onResponse1: "+ map.get(SessionManager.KEY_TOKEN) );
        Call<UpdateHPResponse> call = service.addHP("Bearer "+ map.get(SessionManager.KEY_TOKEN), gambar, reg_name, reg_network, reg_launch, reg_body, reg_display, reg_platform, reg_memory, reg_maincam, reg_selfcam, reg_sound, reg_comms, reg_features, reg_battery, reg_tests);

        //Execute the request asynchronously
        call.enqueue(new Callback<UpdateHPResponse>() {
            @Override
            public void onResponse(Call<UpdateHPResponse> call, Response<UpdateHPResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
                        Toast.makeText(getApplicationContext(), "Add HP Successfully", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(AddHPActivity.this, MainActivityAdmin.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Add HP Failed 1", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "onResponse3: "+ response );
                    Toast.makeText(getApplicationContext(), "Add HP Failed 2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateHPResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}