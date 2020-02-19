package com.naveed.sensortool;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.naveed.sensortool.Model.mainGridViewData;

import java.util.ArrayList;

public class MainDataAdapter extends RecyclerView.Adapter<MainDataAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<mainGridViewData> sensorList;
    InterstitialAd mInterstitialAd;

    public MainDataAdapter(Context context, ArrayList<mainGridViewData> sensorList) {
        this.context = context;
        this.sensorList = sensorList;
        reqNewInterstitial();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);

        return new MyViewHolder(view);
    }

    public void reqNewInterstitial() {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getResources().getString(R.string.Interstitial_ID));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tvTitle.setText(sensorList.get(position).getItemTitle());
        holder.ivIcon.setImageDrawable(sensorList.get(position).getItemIcon());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((mInterstitialAd.isLoaded()) && MainActivity.countClick >= 2) {
                    mInterstitialAd.show();

                } else {

                    Intent intent = new Intent(context, SensorOPDetail.class);
                    MainActivity.countClick++;
                    intent.putExtra("key", sensorList.get(position).getItemTitle());
                    context.startActivity(intent);
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        MainActivity.countClick = 0;
                        Intent intent = new Intent(context, SensorOPDetail.class);
                        intent.putExtra("key", sensorList.get(position).getItemTitle());
                        context.startActivity(intent);
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvTitle;
        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            mView = itemView;
        }
    }


}
