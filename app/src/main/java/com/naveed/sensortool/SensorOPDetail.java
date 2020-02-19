package com.naveed.sensortool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class SensorOPDetail extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    private boolean plotData = true;
    Sensor accelerometer;
    Sensor gravity;
    Sensor gyroscope;
    Sensor linearAcceleration;
    Sensor rotationVector;
    Sensor ambientTemperature;
    Sensor light;
    Sensor pressure;
    Sensor relativeHumidity;
    Sensor gameRotationVector;
    Sensor geomagneticRotationVector;
    Sensor magneticField;
    Sensor proximity;
    Sensor stepC;

    float[] acc;
    float[] gra;
    float[] gyr;
    float[] linAcc;
    float[] rotVec;
    float[] ambTem;
    float[] lig;
    float[] pre;
    float[] relHum;
    float[] step;
    float[] gamRotVec;
    float[] geoRotVec;
    float[] magFie;
    float[] pro;

    boolean accCheck;
    boolean graCheck;
    boolean gyrCheck;
    boolean linAccCheck;
    boolean rotVecCheck;
    boolean ambTemCheck;
    boolean ligCheck;
    boolean preCheck;
    boolean relHumCheck;
    boolean stepB;
    boolean gamRotVecCheck;
    boolean geoRotVecCheck;
    boolean magFieCheck;
    boolean proCheck;

    TextView tvTitle;
    TextView xValue;
    TextView yValue;
    TextView zValue;
    TextView fourthValue;

    ImageView ivBack;
    private static final String TAG = "MainActivity";
    private LineChart mChart;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_opdetail);

        initialWork();
        Listeners();
        BannerAd();
    }


    private void initialWork() {

        accelerometer = null;
        gravity = null;
        gyroscope = null;
        linearAcceleration = null;
        rotationVector = null;
        ambientTemperature = null;
        light = null;
        pressure = null;
        relativeHumidity = null;
        gameRotationVector = null;
        geomagneticRotationVector = null;
        magneticField = null;
        proximity = null;

        accCheck = false;
        graCheck = false;
        gyrCheck = false;
        linAccCheck = false;
        rotVecCheck = false;
        ambTemCheck = false;
        ligCheck = false;
        preCheck = false;
        relHumCheck = false;
        gamRotVecCheck = false;
        geoRotVecCheck = false;
        magFieCheck = false;
        proCheck = false;
        stepB = false;

        ivBack = (ImageView) findViewById(R.id.ivBack);
        xValue = (TextView) findViewById(R.id.xValue);
        yValue = (TextView) findViewById(R.id.yValue);
        zValue = (TextView) findViewById(R.id.zValue);
        fourthValue = (TextView) findViewById(R.id.fourthValue);
        fourthValue.setVisibility(View.GONE);


        mChart = (LineChart) findViewById(R.id.chart1);

        // enable description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        mChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        XAxis xl = mChart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMaximum(20f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(true);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(true);
        mChart.setDrawBorders(true);

        feedMultiple();


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

    private void Listeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorOPDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (plotData) {
            addEntry(event);
            plotData = false;
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            acc = event.values;
            accCheck = true;
            xValue.setText("x = " + acc[0]);
            yValue.setText("y = " + acc[1]);
            zValue.setText("z = " + acc[2]);
        }
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            gra = event.values;
            graCheck = true;
            xValue.setText("x = " + gra[0]);
            yValue.setText("y = " + gra[1]);
            zValue.setText("z = " + gra[2]);
        }
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyr = event.values;
            gyrCheck = true;
            xValue.setText("x = " + gyr[0]);
            yValue.setText("y = " + gyr[1]);
            zValue.setText("z = " + gyr[2]);
        }
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            linAcc = event.values;
            linAccCheck = true;
            xValue.setText("x = " + linAcc[0]);
            yValue.setText("y = " + linAcc[1]);
            zValue.setText("z = " + linAcc[2]);
        }

        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            rotVec = event.values;
            rotVecCheck = true;
            xValue.setText("x * sin(θ/2) = " + rotVec[0]);
            yValue.setText("y * sin(θ/2) = " + rotVec[1]);
            zValue.setText("z * sin(θ/2) = " + rotVec[2]);
            fourthValue.setText("cos(θ/2) = " + rotVec[3]);
            fourthValue.setVisibility(View.VISIBLE);
        }

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            lig = event.values;
            ligCheck = true;
            xValue.setText("LIGHT [lx] : " + lig[0]);
            yValue.setVisibility(View.GONE);
            zValue.setVisibility(View.GONE);
            fourthValue.setVisibility(View.GONE);
        }
        if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
            gamRotVec = event.values;
            gamRotVecCheck = true;
            xValue.setText("x * sin(θ/2) = " + gamRotVec[0]);
            yValue.setText("y * sin(θ/2) = " + gamRotVec[1]);
            zValue.setText("z * sin(θ/2) = " + gamRotVec[2]);
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magFie = event.values;
            magFieCheck = true;
            xValue.setText("x = " + magFie[0]);
            yValue.setText("y = " + magFie[1]);
            zValue.setText("z = " + magFie[2]);
        }
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            pro = event.values;
            proCheck = true;
            xValue.setText("PROXIMITY [cm] : " + pro[0]);
            yValue.setVisibility(View.GONE);
            zValue.setVisibility(View.GONE);
            fourthValue.setVisibility(View.GONE);
            mChart.setVisibility(View.GONE);
        }
        if (event.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {
            geoRotVec = event.values;
            geoRotVecCheck = true;
            xValue.setText("x * sin(θ/2) = " + geoRotVec[0]);
            yValue.setText("y * sin(θ/2) = " + geoRotVec[1]);
            zValue.setText("z * sin(θ/2) = " + geoRotVec[2]);
        }
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            ambTem = event.values;
            ambTemCheck = true;
            xValue.setText("Ambient Temperature [°C] : " + ambTem[0]);
            yValue.setVisibility(View.GONE);
            zValue.setVisibility(View.GONE);
            fourthValue.setVisibility(View.GONE);
            mChart.setVisibility(View.GONE);
        }
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            pre = event.values;
            preCheck = true;
            xValue.setText("Pressure [hPa] : " + pre[0]);
            yValue.setVisibility(View.GONE);
            zValue.setVisibility(View.GONE);
            fourthValue.setVisibility(View.GONE);
            mChart.setVisibility(View.GONE);
        }
        if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            relHum = event.values;
            relHumCheck = true;
            xValue.setText("Relative Humidity [%] : " + relHum[0]);
            yValue.setVisibility(View.GONE);
            zValue.setVisibility(View.GONE);
            fourthValue.setVisibility(View.GONE);
            mChart.setVisibility(View.GONE);
        }

        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            step = event.values;
            stepB = true;
            xValue.setText("Steps : " + step[0]);
            yValue.setVisibility(View.GONE);
            zValue.setVisibility(View.GONE);
            fourthValue.setVisibility(View.GONE);
            mChart.setVisibility(View.GONE);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onResume() {
        super.onResume();

        tvTitle = findViewById(R.id.tvTitle);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        startsensing();
    }


    private void addEntry(SensorEvent event) {

        LineData data = mChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            ILineDataSet set1 = data.getDataSetByIndex(1);
            ILineDataSet set2 = data.getDataSetByIndex(2);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            if (set1 == null) {
                set1 = createSet1();
                data.addDataSet(set1);
            }

            if (set2 == null) {
                set2 = createSet2();
                data.addDataSet(set2);
            }

            //check for those sensor which are not using Graph
            if (getResources().getString(R.string.proximity) != tvTitle.getText() && getResources().getString(R.string.pressure) != tvTitle.getText() && getResources().getString(R.string.ambientTemperature) != tvTitle.getText() && getResources().getString(R.string.relativeHumidity) != tvTitle.getText() && getResources().getString(R.string.stepcounter) != tvTitle.getText()) {
                data.addEntry(new Entry(set.getEntryCount(), event.values[0] + 5), 0);
                if (getResources().getString(R.string.light) != tvTitle.getText()) {
                    data.addEntry(new Entry(set1.getEntryCount(), event.values[1] + 5), 1);
                    data.addEntry(new Entry(set2.getEntryCount(), event.values[2] + 5), 2);
                }
            }

            data.notifyDataChanged();

            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(150);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewToX(data.getEntryCount());

        }

    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(3f);
        set.setColor(Color.BLUE);
        set.setHighlightEnabled(false);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.2f);
        return set;
    }

    private LineDataSet createSet1() {

        LineDataSet set1 = new LineDataSet(null, "Dynamic Data");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setLineWidth(3f);
        set1.setColor(Color.YELLOW);
        set1.setHighlightEnabled(false);
        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
        return set1;
    }

    private LineDataSet createSet2() {

        LineDataSet set2 = new LineDataSet(null, "Dynamic Data");
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setLineWidth(3f);
        set2.setColor(Color.RED);
        set2.setHighlightEnabled(false);
        set2.setDrawValues(false);
        set2.setDrawCircles(false);
        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set2.setCubicIntensity(0.2f);
        return set2;
    }

    private void feedMultiple() {

        if (thread != null) {
            thread.interrupt();
        }

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    plotData = true;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

    private void startsensing() {

        String title = getIntent().getStringExtra("key");

        switch (title) {
            case "Accelerometer":
                this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                if (accelerometer != null) {
                    tvTitle.setText(getResources().getString(R.string.Accelerometer));
                    sensorManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.Accelerometer));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Gravity":
                this.gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                if (gravity != null) {
                    tvTitle.setText(getResources().getString(R.string.gravity));
                    sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.gravity));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Gyroscope":
                this.gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                if (gyroscope != null) {
                    tvTitle.setText(getResources().getString(R.string.gyroscope));
                    sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.gyroscope));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Linear Acceleration":
                this.linearAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                if (linearAcceleration != null) {
                    tvTitle.setText(getResources().getString(R.string.linear_acceleration));
                    sensorManager.registerListener(this, linearAcceleration, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.linear_acceleration));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Rotation Vector":
                this.rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                if (rotationVector != null) {
                    tvTitle.setText(getResources().getString(R.string.rotation_vector));
                    sensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.rotation_vector));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Light":
                this.light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                if (light != null) {
                    tvTitle.setText(getResources().getString(R.string.light));
                    sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.light));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Game Rotation Vector":
                this.gameRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
                if (gameRotationVector != null) {
                    tvTitle.setText(getResources().getString(R.string.gameRotationVector));
                    sensorManager.registerListener(this, gameRotationVector, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.gameRotationVector));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Magnetic Field [μT]":
                this.magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                if (magneticField != null) {
                    tvTitle.setText(getResources().getString(R.string.magneticField));
                    sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.magneticField));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Proximity":
                this.proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                if (proximity != null) {
                    tvTitle.setText(getResources().getString(R.string.proximity));
                    sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.proximity));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Geomagnetic Rotation Vector":
                this.geomagneticRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
                if (geomagneticRotationVector != null) {
                    tvTitle.setText(getResources().getString(R.string.geomagneticRotationVector));
                    sensorManager.registerListener(this, gameRotationVector, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.geomagneticRotationVector));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Ambient Temperature [°C]":
                this.ambientTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                if (ambientTemperature != null) {
                    tvTitle.setText(getResources().getString(R.string.ambientTemperature));
                    sensorManager.registerListener(this, ambientTemperature, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.ambientTemperature));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Pressure":
                this.pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                if (pressure != null) {
                    tvTitle.setText(getResources().getString(R.string.pressure));
                    sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.pressure));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Relative Humidity":
                this.relativeHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
                if (relativeHumidity != null) {
                    tvTitle.setText(getResources().getString(R.string.relativeHumidity));
                    sensorManager.registerListener(this, relativeHumidity, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.relativeHumidity));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

            case "Step Counter":
                this.stepC = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                if (stepC != null) {
                    tvTitle.setText(getResources().getString(R.string.stepcounter));
                    sensorManager.registerListener(this, stepC, SensorManager.SENSOR_DELAY_GAME);
                } else {
                    tvTitle.setText(getResources().getString(R.string.stepcounter));
                    xValue.setText(getResources().getString(R.string.unavailable));
                    yValue.setVisibility(View.GONE);
                    zValue.setVisibility(View.GONE);
                    fourthValue.setVisibility(View.GONE);
                    mChart.setVisibility(View.GONE);
                }
                break;

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER));
    }

    @Override
    protected void onDestroy() {
        thread.interrupt();
        super.onDestroy();
    }
}
