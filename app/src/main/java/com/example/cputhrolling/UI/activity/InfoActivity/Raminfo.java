package com.example.cputhrolling.UI.activity.InfoActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cputhrolling.R;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Raminfo extends AppCompatActivity {
    TextView total , free , used , RuntimeMaxMemory ,RuntimeFreeMemory;
    ImageView back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raminfo);
        total = (TextView) findViewById(R.id.total);
        free = (TextView) findViewById(R.id.free);
        used = (TextView) findViewById(R.id.RuntimeTotalMemory);
        RuntimeMaxMemory = (TextView) findViewById(R.id.RuntimeMaxMemory);
        RuntimeFreeMemory = (TextView) findViewById(R.id.RuntimeFreeMemory);
        back = (ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

     getMemoryInfo();
    }

    private String getMemoryInfo() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);

        Runtime runtime = Runtime.getRuntime();

        String strMemInfo =  bytesIntoHumanReadable(memoryInfo.availMem)  + "\n";
        String strTotalMemory = bytesIntoHumanReadable( memoryInfo.totalMem )+ "\n";
        String strRuntimeMaxMemory =  bytesIntoHumanReadable(runtime.maxMemory()) + "\n";
        String strRuntimeTotalMemory =  bytesIntoHumanReadable(runtime.totalMemory()) + "\n";
        String strRuntimeFreeMemory =  bytesIntoHumanReadable(runtime.freeMemory()) + "\n";

        total.setText(strTotalMemory);
        free.setText(strMemInfo);
        used.setText(strRuntimeTotalMemory);
        RuntimeFreeMemory.setText(strRuntimeFreeMemory);
        RuntimeMaxMemory.setText(strRuntimeMaxMemory);
        return strMemInfo;
    }

    private String bytesIntoHumanReadable(long bytes) {
        long kilobyte = 1024;
        long megabyte = kilobyte * 1024;
        long gigabyte = megabyte * 1024;
        long terabyte = gigabyte * 1024;

        if ((bytes >= 0) && (bytes < kilobyte)) {
            return bytes + " B";

        } else if ((bytes >= kilobyte) && (bytes < megabyte)) {
            return (bytes / kilobyte) + " KB";

        } else if ((bytes >= megabyte) && (bytes < gigabyte)) {
            return (bytes / megabyte) + " MB";

        } else if ((bytes >= gigabyte) && (bytes < terabyte)) {
            return (bytes / gigabyte) + " GB";

        } else if (bytes >= terabyte) {
            return (bytes / terabyte) + " TB";

        } else {
            return bytes + " Bytes";
        }
    }
}
