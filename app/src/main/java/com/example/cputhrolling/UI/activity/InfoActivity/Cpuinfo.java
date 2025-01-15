package com.example.cputhrolling.UI.activity.InfoActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cputhrolling.R;
import com.example.cputhrolling.UI.activity.adapter.CpuInformationAdapter;
import com.example.cputhrolling.UI.activity.adapter.NumbersViewAdapter;
import com.example.cputhrolling.UI.activity.model.cpuinfomodel;
import com.example.cputhrolling.UI.activity.model.itemlist;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Cpuinfo extends AppCompatActivity {
    ImageView back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpuinfo);
        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {

            String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder processBuilder = new ProcessBuilder(DATA);
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            byte[] byteArry = new byte[237];
            String output = "";
            final ArrayList<cpuinfomodel> arrayList = new ArrayList<cpuinfomodel>();
            for (int i=-1; inputStream.read(byteArry) != -1 ; i++) {
               arrayList.add(new cpuinfomodel(/*output =*//* output +*/ new String(byteArry))) ;
            }
            Log.d("CPU_INFO", output);
            CpuInformationAdapter CpuInformationAdapter = new CpuInformationAdapter(this, arrayList);
            ListView cpuinfomodel = findViewById(R.id.listView2);
            cpuinfomodel.setAdapter(CpuInformationAdapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}