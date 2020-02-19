package com.naveed.sensortool;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naveed.sensortool.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.naveed.sensortool.Model.mainGridViewData;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    RecyclerView mainRecylcerView;
    ArrayList<mainGridViewData> sensorList;
    static int NumberOfCoulmns = 2;
    public static int countClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialWork();
        Listeners();
        BannerAd();
    }

    private void BannerAd() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    public void initialWork() {

        mainRecylcerView = findViewById(R.id.mainRecylcerView);
        GridLayoutManager manager = new GridLayoutManager(this, NumberOfCoulmns);
        mainRecylcerView.setLayoutManager(manager);
        sensorList = new ArrayList<mainGridViewData>();

        addDataToArray();

        MainDataAdapter mainDataAdapter = new MainDataAdapter(this, sensorList);
        mainRecylcerView.setAdapter(mainDataAdapter);

    }

    private void Listeners() {

    }

    private void addDataToArray() {

        //adding Sensor Title and Icon to arrayList
        mainGridViewData obj0 = new mainGridViewData();
        obj0.setItemIcon((ContextCompat.getDrawable(MainActivity.this, R.drawable.accelero_meter)));
        obj0.setItemTitle(getResources().getString(R.string.Accelerometer));
        sensorList.add(obj0);

        mainGridViewData obj1 = new mainGridViewData();
        obj1.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.gravity));
        obj1.setItemTitle(getResources().getString(R.string.gravity));
        sensorList.add(obj1);

        mainGridViewData obj2 = new mainGridViewData();
        obj2.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.gyroscope));
        obj2.setItemTitle(getResources().getString(R.string.gyroscope));
        sensorList.add(obj2);

        mainGridViewData obj3 = new mainGridViewData();
        obj3.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.linear));
        obj3.setItemTitle(getResources().getString(R.string.linear_acceleration));
        sensorList.add(obj3);

        mainGridViewData obj4 = new mainGridViewData();
        obj4.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.rotation));
        obj4.setItemTitle(getResources().getString(R.string.rotation_vector));
        sensorList.add(obj4);

        mainGridViewData obj5 = new mainGridViewData();
        obj5.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.light_lux));
        obj5.setItemTitle(getResources().getString(R.string.light));
        sensorList.add(obj5);

        mainGridViewData obj6 = new mainGridViewData();
        obj6.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.game_roattion));
        obj6.setItemTitle(getResources().getString(R.string.gameRotationVector));
        sensorList.add(obj6);

        mainGridViewData obj7 = new mainGridViewData();
        obj7.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.magnetic));
        obj7.setItemTitle(getResources().getString(R.string.magneticField));
        sensorList.add(obj7);

        mainGridViewData obj8 = new mainGridViewData();
        obj8.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.proximity));
        obj8.setItemTitle(getResources().getString(R.string.proximity));
        sensorList.add(obj8);

        mainGridViewData obj9 = new mainGridViewData();
        obj9.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.geomatric_rotation));
        obj9.setItemTitle(getResources().getString(R.string.geomagneticRotationVector));
        sensorList.add(obj9);

        mainGridViewData obj10 = new mainGridViewData();
        obj10.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.pressure));
        obj10.setItemTitle(getResources().getString(R.string.pressure));
        sensorList.add(obj10);

        mainGridViewData obj11 = new mainGridViewData();
        obj11.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.temprature));
        obj11.setItemTitle(getResources().getString(R.string.ambientTemperature));
        sensorList.add(obj11);

        mainGridViewData obj12 = new mainGridViewData();
        obj12.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.humidity));
        obj12.setItemTitle(getResources().getString(R.string.relativeHumidity));
        sensorList.add(obj12);

        mainGridViewData obj13 = new mainGridViewData();
        obj13.setItemIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.step));
        obj13.setItemTitle(getResources().getString(R.string.stepcounter));
        sensorList.add(obj13);

    }

}
