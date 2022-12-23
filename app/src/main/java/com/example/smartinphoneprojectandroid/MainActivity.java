package com.example.smartinphoneprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartinphoneprojectandroid.handphone.GetData;
import com.example.smartinphoneprojectandroid.handphone.HandphoneModel;
import com.example.smartinphoneprojectandroid.utils.RetrofitClient;
import com.example.smartinphoneprojectandroid.utils.SessionManager;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private AdapterListHP adapterHP;
    private RecyclerView hpRV;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        sm = new SessionManager(MainActivity.this);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        // Retrofit
//        GetData service = RetrofitClient.myRetrofitInstance().create(GetData.class);
//        Call<List<HandphoneModel>> call = service.getHandphone();
//
//        //Execute the request asynchronously
//        call.enqueue(new Callback<List<HandphoneModel>>() {
//            @Override
//            //Handle the successful response
//            public void onResponse(Call<List<HandphoneModel>> call, Response<List<HandphoneModel>> response) {
//                loadMyList(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<HandphoneModel>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Gagal Menampilkan List!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    public void loadMyList(List<HandphoneModel> list) {
//        //Get a reference to the RecyclerView and the Adapter.
//        hpRV =findViewById(R.id.hp_rv);
//        adapterHP = new AdapterListHP(list);
//
//        //set the RecyclerView to the LinearLayoutManager with default vertical orientation
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//        hpRV.setLayoutManager(layoutManager);
//
//        //Set the Adapter to the RecyclerView
//        hpRV.setAdapter(adapterHP);
//    }
}