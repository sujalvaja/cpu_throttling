package com.example.cputhrolling.UI.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.cputhrolling.R;
import com.example.cputhrolling.UI.activity.InfoActivity.BatteryInfo;
import com.example.cputhrolling.UI.activity.InfoActivity.Deviceinfo;
import com.example.cputhrolling.UI.activity.InfoActivity.Raminfo;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import java.util.regex.Pattern;

public class Cpu extends AppCompatActivity {
    CardView btnfilescan;
    CardView btndeviceinfo;
    CardView btnraminfo;
    CardView btnbatteryinfo;

    ImageView back;

    boolean keepReading = true;


    String pattern = "([0-9]{6,7})";
    Pattern pat = Pattern.compile(pattern);
    String patter2 = "([0-9]{1,2})";
    Pattern pat2 = Pattern.compile(patter2);

    int foo = 0;



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    LineGraphSeries<DataPoint> series;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);


        btndeviceinfo = findViewById(R.id.btndeviceinfo);
        btnraminfo = findViewById(R.id.btnRaminfo);
        btnbatteryinfo = findViewById(R.id.buttonBatteryTemp);
        btnfilescan = findViewById(R.id.btnfilescan);
        back = findViewById(R.id.back);

        back.setOnClickListener(v -> onBackPressed());



        btndeviceinfo.setOnClickListener(v -> {
            Intent i = new Intent(Cpu.this , Deviceinfo.class);
            startActivity(i);
        });
        btnraminfo.setOnClickListener(v -> {
            Intent i = new Intent(Cpu.this , Raminfo.class);
            startActivity(i);
        });

        btnbatteryinfo.setOnClickListener(v -> {
            Intent i = new Intent(Cpu.this , BatteryInfo.class);
            startActivity(i);
        });


        btnfilescan.setOnClickListener(v -> {
            Intent i = new Intent(Cpu.this , com.example.cputhrolling.UI.activity.Sensors.class);
            startActivity(i);
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        keepReading = false;
    }

    protected void onStop() {
        super.onStop();

    }

    protected void onResume() {
        super.onResume();
        keepReading = true;
    }

}