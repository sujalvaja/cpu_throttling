package com.example.cputhrolling.UI.activity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cputhrolling.R;
import com.example.cputhrolling.UI.activity.adapter.NumbersViewAdapter;
import com.example.cputhrolling.UI.activity.model.itemlist;

import java.util.ArrayList;
import java.util.List;

public class Sensors extends AppCompatActivity {
    private SensorManager mSensorManager;
    ImageView back;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        back = (ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> mList= mSensorManager.getSensorList(Sensor.TYPE_ALL);



        final ArrayList<itemlist> arrayList = new ArrayList<itemlist>();
        for (int i = 1; i < mList.size(); i++) {
            arrayList.add(new itemlist("{Sensor Name : " + mList.get(i).getName() + "  "+ "Vendor : " + mList.get(i).getVendor()+ "  " + " Version :" + mList.get(i).getVersion()+ "  " + "Type : " +
                    mList.get(i).getType() + "  "+ "MaximumRange :" + mList.get(i).getMaximumRange()+ "  "+ "Resolution :" + mList.get(i).getResolution()+ "  "+ "Power : " + mList.get(i).getPower()+ "  "
                    + "MinDelay :" + mList.get(i).getMinDelay() + "  "+"MaxDelay : " + mList.get(i).getMaxDelay()+"}"));
        }
        NumbersViewAdapter numbersArrayAdapter = new NumbersViewAdapter(this, arrayList);
        ListView numbersListView = findViewById(R.id.listView1);
        numbersListView.setAdapter(numbersArrayAdapter);
    }
}



