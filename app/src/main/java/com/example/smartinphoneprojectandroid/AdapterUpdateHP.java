package com.example.smartinphoneprojectandroid;

import static com.example.smartinphoneprojectandroid.utils.Credentials.PHONE_PHOTO;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartinphoneprojectandroid.adminAct.MainActivityAdmin;
import com.example.smartinphoneprojectandroid.adminAct.UpdateHPAdminActivity;
import com.example.smartinphoneprojectandroid.handphone.HandphoneModel;
import com.example.smartinphoneprojectandroid.userAct.UpdateHPUserActivity;
import com.example.smartinphoneprojectandroid.utils.DownloadImage;
import com.example.smartinphoneprojectandroid.utils.SessionManager;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class AdapterUpdateHP extends RecyclerView.Adapter<AdapterUpdateHP.MyViewHolder> {
    private List<HandphoneModel> dataList;
    SessionManager sm;

    public AdapterUpdateHP(List<HandphoneModel> dataList) {
        this.dataList = dataList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //Get a reference to the Views in the layout that respresents one row in the recyclerview
        public final View view;

        TextView nameTV,dateTV,platformTV;
        CardView cardHP;
        URL ImageUrl = null;
        Bitmap bmImg = null;
        ImageView imageHandphone = null;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view= itemView;

            nameTV = itemView.findViewById(R.id.nameTV);
            dateTV = itemView.findViewById(R.id.dateTV);
            platformTV = itemView.findViewById(R.id.platformTV);
            imageHandphone = itemView.findViewById(R.id.imageHandphone);
            cardHP = itemView.findViewById(R.id.cardHP);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_handphone,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        sm = new SessionManager(holder.view.getContext());
        HashMap<String, String> map = sm.getDetailLogin();
        holder.nameTV.setText(dataList.get(position).getName());
        holder.dateTV.setText(dataList.get(position).getLaunch().toString());
        holder.platformTV.setText(dataList.get(position).getPlatform());
        new DownloadImage(holder.imageHandphone).execute(PHONE_PHOTO+dataList.get(position).getPhone_photo());
        holder.cardHP.setOnClickListener(v->{
            Toast.makeText(v.getContext(), "Update HP "+dataList.get(position).getName(), Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            bundle.putString("id", dataList.get(position).getId().toString());
            bundle.putString("phone_photo", dataList.get(position).getPhone_photo());
            bundle.putString("name", dataList.get(position).getName());
            bundle.putString("launch", sdf.format(dataList.get(position).getLaunch()));
            bundle.putString("network", dataList.get(position).getNetwork());
            bundle.putString("body", dataList.get(position).getBody());
            bundle.putString("platform", dataList.get(position).getPlatform());
            bundle.putString("memory", dataList.get(position).getMemory());
            bundle.putString("maincam", dataList.get(position).getMaincam());
            bundle.putString("selfcam", dataList.get(position).getSelfcam());
            bundle.putString("sound", dataList.get(position).getSound());
            bundle.putString("comms", dataList.get(position).getComms());
            bundle.putString("features", dataList.get(position).getFeatures());
            bundle.putString("battery", dataList.get(position).getBattery());
            bundle.putString("tests", dataList.get(position).getTests());
            bundle.putString("display", dataList.get(position).getDisplay());
            if (map.get(SessionManager.KEY_ROLE).equals("admin")){
                Intent i = new Intent(v.getContext(), UpdateHPAdminActivity.class);
                i.putExtras(bundle);
                v.getContext().startActivity(i);
            } else {
                Intent i = new Intent(v.getContext(), UpdateHPUserActivity.class);
                i.putExtras(bundle);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
