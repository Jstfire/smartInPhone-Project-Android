package com.example.smartinphoneprojectandroid.userAct;

import static com.example.smartinphoneprojectandroid.utils.Credentials.PHONE_PHOTO;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.smartinphoneprojectandroid.R;
import com.example.smartinphoneprojectandroid.databinding.ActivityUpdateHpuserBinding;
import com.example.smartinphoneprojectandroid.handphone.GetData;
import com.example.smartinphoneprojectandroid.handphone.UpdateHPResponse;
import com.example.smartinphoneprojectandroid.utils.DownloadImage;
import com.example.smartinphoneprojectandroid.utils.RetrofitClient;
import com.example.smartinphoneprojectandroid.utils.SessionManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateHPUserActivity extends AppCompatActivity {
    ActivityUpdateHpuserBinding binding;
    String id;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateHpuserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sm = new SessionManager(UpdateHPUserActivity.this);

        if(getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            new DownloadImage(binding.imageHP).execute(PHONE_PHOTO+bundle.getString("phone_photo"));
            id = bundle.getString("id");
            binding.nameInput.setText(bundle.getString("name"));
            binding.networkInput.setText(bundle.getString("network"));
            binding.launchInput.setText(bundle.getString("launch"));
            binding.bodyInput.setText(bundle.getString("body"));
            binding.displayInput.setText(bundle.getString("display"));
            binding.platformInput.setText(bundle.getString("platform"));
            binding.memoryInput.setText(bundle.getString("memory"));
            binding.maincamInput.setText(bundle.getString("maincam"));
            binding.selfcamInput.setText(bundle.getString("selfcam"));
            binding.soundInput.setText(bundle.getString("sound"));
            binding.commsInput.setText(bundle.getString("comms"));
            binding.featuresInput.setText(bundle.getString("features"));
            binding.batteryInput.setText(bundle.getString("battery"));
            binding.testsInput.setText(bundle.getString("tests"));
        }

        binding.back2Button.setOnClickListener(v->{
            finish();
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
                Toast.makeText(UpdateHPUserActivity.this, "Please fill the required field!", Toast.LENGTH_SHORT).show();
            } else {
                submitFunc(id,
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

    public void submitFunc(String id, String  name, String  network, String  launch, String  body, String  display, String platform, String memory, String maincam, String selfcam, String sound, String comms, String features, String battery, String tests){
        HashMap<String, String> map = sm.getDetailLogin();
        GetData service = RetrofitClient.myRetrofitInstance().create(GetData.class);
        Call<UpdateHPResponse> call = service.updateHP("Bearer "+map.get(SessionManager.KEY_TOKEN), id,  name,  network,  launch,  body,  display, platform, memory, maincam, selfcam, sound, comms, features, battery, tests);
        //Execute the request asynchronously
        call.enqueue(new Callback<UpdateHPResponse>() {
            @Override
            public void onResponse(Call<UpdateHPResponse> call, Response<UpdateHPResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
                        Toast.makeText(getApplicationContext(), "Submit Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Submit Failed 1", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Submit Failed 2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateHPResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}