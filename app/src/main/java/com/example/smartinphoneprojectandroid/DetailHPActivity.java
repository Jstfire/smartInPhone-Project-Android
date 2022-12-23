package com.example.smartinphoneprojectandroid;

import static com.example.smartinphoneprojectandroid.utils.Credentials.PHONE_PHOTO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartinphoneprojectandroid.adminAct.MainActivityAdmin;
import com.example.smartinphoneprojectandroid.userAct.MainActivityUser;
import com.example.smartinphoneprojectandroid.utils.DownloadImage;
import com.example.smartinphoneprojectandroid.utils.SessionManager;

import java.util.HashMap;
import java.util.Objects;

public class DetailHPActivity extends AppCompatActivity {
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hpactivity);

        sm = new SessionManager(DetailHPActivity.this);
        HashMap<String, String> map = sm.getDetailLogin();
        if (Objects.equals(map.get(SessionManager.KEY_ROLE), "admin")) {
            findViewById(R.id.back2Button).setOnClickListener(v->{
                Intent i = new Intent(DetailHPActivity.this, MainActivityAdmin.class);
                startActivity(i);
            });
        }
        if (Objects.equals(map.get(SessionManager.KEY_ROLE), "user")) {
            findViewById(R.id.back2Button).setOnClickListener(v->{
                Intent i = new Intent(DetailHPActivity.this, MainActivityUser.class);
                startActivity(i);
            });
        }
        if (!sm.Loggin()) {
            findViewById(R.id.back2Button).setOnClickListener(v->{
                Intent i = new Intent(DetailHPActivity.this, MainActivity.class);
                startActivity(i);
            });
        }

        TextView nameHP = (TextView) findViewById(R.id.nameHP);
        ImageView imageHP = (ImageView) findViewById(R.id.imageHP);
        TextView networkHP = (TextView) findViewById(R.id.networkHP);
        TextView launchHP = (TextView) findViewById(R.id.launchHP);
        TextView bodyHP = (TextView) findViewById(R.id.bodyHP);
        TextView displayHP = (TextView) findViewById(R.id.displayHP);
        TextView platformHP = (TextView) findViewById(R.id.platformHP);
        TextView memoryHP = (TextView) findViewById(R.id.memoryHP);
        TextView maincamHP = (TextView) findViewById(R.id.maincamHP);
        TextView selfcamHP = (TextView) findViewById(R.id.selfcamHP);
        TextView soundHP = (TextView) findViewById(R.id.soundHP);
        TextView commsHP = (TextView) findViewById(R.id.commsHP);
        TextView featuresHP = (TextView) findViewById(R.id.featuresHP);
        TextView batteryHP = (TextView) findViewById(R.id.batteryHP);
        TextView testsHP = (TextView) findViewById(R.id.testsHP);

        if(getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            new DownloadImage(imageHP).execute(PHONE_PHOTO+bundle.getString("phone_photo"));
            nameHP.setText(bundle.getString("name"));
            networkHP.setText(bundle.getString("network"));
            launchHP.setText(bundle.getString("launch"));
            bodyHP.setText(bundle.getString("body"));
            displayHP.setText(bundle.getString("display"));
            platformHP.setText(bundle.getString("platform"));
            memoryHP.setText(bundle.getString("memory"));
            maincamHP.setText(bundle.getString("maincam"));
            selfcamHP.setText(bundle.getString("selfcam"));
            soundHP.setText(bundle.getString("sound"));
            commsHP.setText(bundle.getString("comms"));
            featuresHP.setText(bundle.getString("features"));
            batteryHP.setText(bundle.getString("battery"));
            testsHP.setText(bundle.getString("tests"));
        }
    }
}