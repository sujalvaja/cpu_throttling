package com.example.cputhrolling.UI.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.cputhrolling.R;
import com.example.cputhrolling.UI.activity.InfoActivity.Cpuinfo;


public class MainActivity extends AppCompatActivity {
    Button button;
    Button button1;
    Button button2;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_main);
        button = findViewById(R.id.buttonBatteryTemp);
        button1 = findViewById(R.id.cpufre);
        button2 = findViewById(R.id.otherinfo);


        button2.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, com.example.cputhrolling.UI.activity.Cpu.class);
            startActivity(i);
        });

        button1.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, com.example.cputhrolling.UI.activity.cpufrequency.class);
            startActivity(i);
        });

        button.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, Cpuinfo.class);
            startActivity(i);
        });

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

}

