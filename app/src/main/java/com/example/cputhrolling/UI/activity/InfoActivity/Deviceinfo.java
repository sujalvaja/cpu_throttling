package com.example.cputhrolling.UI.activity.InfoActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cputhrolling.R;

public class Deviceinfo extends AppCompatActivity {
    ImageView back;
    private TextView txtmodel;
    private TextView txtid;
    private TextView txtmanufacturer;
    private TextView txtBRAND;
    private TextView txtTYPE;
    private TextView txtUSER;
    private TextView txtINCREMENTAL;
    private TextView txtSDK;
    private TextView txtBOARD;
    private TextView txtHOST;
    private TextView txtFINGERPRINT;
    private TextView txtRELEASE;
    @SuppressLint({"MissingInflatedId", "HardwareIds"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deveiceinfo);
        TextView textViewSetInformation = (TextView) findViewById(R.id.textViewSetInformation);
        txtmodel = (TextView) findViewById(R.id.txtmodel);
        txtid = (TextView) findViewById(R.id.txtid);
        txtmanufacturer = (TextView) findViewById(R.id.txtmanufacturer);
        txtBRAND = (TextView) findViewById(R.id.txtBRAND);
        txtTYPE = (TextView) findViewById(R.id.txtTYPE);
        txtUSER = (TextView) findViewById(R.id.txtUSER);

        txtINCREMENTAL = (TextView) findViewById(R.id.txtINCREMENTAL);
        txtSDK = (TextView) findViewById(R.id.txtSDK);
        txtBOARD = (TextView) findViewById(R.id.txtBOARD);
        txtHOST = (TextView) findViewById(R.id.txtHOST);
        txtFINGERPRINT = (TextView) findViewById(R.id.txtFINGERPRINT);
        txtRELEASE = (TextView) findViewById(R.id.txtRELEASE);

        textViewSetInformation.setText(Build.SERIAL);
        txtmodel.setText(Build.MODEL);
        txtid.setText(Build.ID);
        txtmanufacturer.setText(Build.MANUFACTURER);
        txtBRAND.setText(Build.BRAND);
        txtTYPE.setText(Build.TYPE);
        txtUSER.setText(Build.USER);

        txtINCREMENTAL.setText(Build.VERSION.INCREMENTAL);
        txtSDK.setText(Build.VERSION.SDK);
        txtBOARD.setText(Build.BOARD);
        txtHOST.setText(Build.HOST);
        txtFINGERPRINT.setText(Build.FINGERPRINT);
        txtRELEASE.setText(Build.VERSION.RELEASE);


        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}


