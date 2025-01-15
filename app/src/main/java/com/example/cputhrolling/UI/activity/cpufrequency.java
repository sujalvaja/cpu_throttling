package com.example.cputhrolling.UI.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cputhrolling.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cpufrequency extends AppCompatActivity implements OnSeekBarChangeListener, OnChartValueSelectedListener {

    private final SensorEventListener temperatureSensor = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            System.out.println("on sensor changed called");
            float temp = event.values[0];
            System.out.println("Temperature sensor: " + temp);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    protected Typeface tfRegular;
    protected Typeface tfLight;
    boolean keepReading = true;
    String[] cpu0 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq"};
    String[] cpu1 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu1/cpufreq/scaling_cur_freq"};
    String[] cpu2 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu2/cpufreq/scaling_cur_freq"};
    String[] cpu3 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu3/cpufreq/scaling_cur_freq"};
    String[] cpu4 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu3/cpufreq/scaling_cur_freq"};
    String[] cpu7 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu2/cpufreq/scaling_cur_freq"};
    String[] cpu5 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu3/cpufreq/scaling_cur_freq"};
    String[] cpu6 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu3/cpufreq/scaling_cur_freq"};
    String[] maxFreq = {"/system/bin/cat", "/sys/devices/system/cpu/cpu3/cpufreq/cpuinfo_max_freq"};
    String[] temp = {"/system/bin/cat", "sys/devices/virtual/thermal/thermal1_zone0/temp"};
    String[] batterytemp = {"/system/bin/cat", "/sys/class/power_supply/battery/temp"};
    String[] thermal = {"/system/bin/cat", "/sys/devices/platform/tmu/temperature"};
    String[] cputemp = {"/system/bin/cat", "sys/class/thermal/thermal_zone0/temp"};
    String pattern = "([0-9]{6,7})";
    Pattern pat = Pattern.compile(pattern);
    String patter2 = "([0-9]{1,2})";
    Pattern pat2 = Pattern.compile(patter2);
    int foo = 0;
    Button sendNotice;
    ImageView back;
    private LineChart chart;
    private TextView tvCount;

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cpufrequency);
        back = findViewById(R.id.back);

        back.setOnClickListener(v -> onBackPressed());

        chart = findViewById(R.id.chart1);
        chart.setBackgroundColor(Color.WHITE);
        chart.getDescription().setEnabled(false);
        chart.setOnChartValueSelectedListener(this);
        chart.setDrawGridBackground(false);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setScaleXEnabled(true);
        chart.setScaleYEnabled(true);
        chart.setPinchZoom(false);

        XAxis xAxis;
        {
            xAxis = chart.getXAxis();
            xAxis.enableGridDashedLine(10f, 10f, 0f);
            xAxis.setAxisMaximum(50f);
            xAxis.setAxisMinimum(0f);
        }

        YAxis yAxis;
        {
            yAxis = chart.getAxisLeft();
            chart.getAxisRight().setEnabled(true);
            yAxis.enableGridDashedLine(10f, 10f, 0f);
            yAxis.setAxisMaximum(4000f);
            yAxis.setAxisMinimum(0f);
        }


        {
            LimitLine llXAxis = new LimitLine(100f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);
            llXAxis.setTypeface(tfRegular);

            LimitLine ll1 = new LimitLine(4000f, "Upper Limit");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);
            ll1.setTypeface(tfRegular);

            LimitLine ll2 = new LimitLine(0f, "Lower Limit");
            ll2.setLineWidth(4f);
            ll2.enableDashedLine(10f, 10f, 0f);
            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            ll2.setTextSize(10f);
            ll2.setTypeface(tfRegular);


            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);


            yAxis.addLimitLine(ll1);
            yAxis.addLimitLine(ll2);

        }


        chart.animateX(4000);

        Legend l = chart.getLegend();

        l.setForm(Legend.LegendForm.LINE);

        SensorManager mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor TempSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorManager.registerListener(temperatureSensor, TempSensor, SensorManager.SENSOR_DELAY_NORMAL);


        final TextView tv_core0 = findViewById(R.id.core0freq);
        final TextView tv_core1 = findViewById(R.id.core1freq);
        final TextView tv_core2 = findViewById(R.id.core2freq);
        final TextView tv_core3 = findViewById(R.id.core3freq);
        final TextView tv_core4 = findViewById(R.id.core4freq);
        final TextView tv_core5 = findViewById(R.id.core5freq);
        final TextView tv_core6 = findViewById(R.id.core6freq);
        final TextView tv_core7 = findViewById(R.id.core7freq);
        getMaxCPUFreqMHz();

        final File cpu0file = new File("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
        final File cpu1file = new File("/sys/devices/system/cpu/cpu1/cpufreq/scaling_cur_freq");
        final File cpu2file = new File("/sys/devices/system/cpu/cpu2/cpufreq/scaling_cur_freq");
        final File cpu3file = new File("/sys/devices/system/cpu/cpu3/cpufreq/scaling_cur_freq");
        final File cpu4file = new File("/sys/devices/system/cpu/cpu4/cpufreq/scaling_cur_freq");
        final File cpu5file = new File("/sys/devices/system/cpu/cpu5/cpufreq/scaling_cur_freq");
        final File cpu6file = new File("/sys/devices/system/cpu/cpu6/cpufreq/scaling_cur_freq");
        final File cpu7file = new File("/sys/devices/system/cpu/cpu7/cpufreq/scaling_cur_freq");
        final File cputempfile = new File("sys/class/thermal/thermal_zone0/temp");


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (keepReading) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {

                                if (cpu0file.exists()) {
                                    tv_core0.setText(formatCPUFreq(ReadCPU0(cpu0)) + " MHz");
                                    chart.resetTracking();
                                    setData(Integer.parseInt(formatCPUFreq(ReadCPU0(cpu0))), 2000f);
                                    chart.invalidate();


                                } else {
                                    tv_core0.setText("Offline");
                                }

                                if (cpu1file.exists()) {
                                    tv_core1.setText(formatCPUFreq(ReadCPU0(cpu1)) + " MHz");

                                } else {
                                    tv_core1.setText("Offline");
                                }

                                if (cpu2file.exists()) {
                                    tv_core2.setText(formatCPUFreq(ReadCPU0(cpu2)) + " MHz");

                                } else {
                                    tv_core2.setText("Offline");
                                }

                                if (cpu3file.exists()) {
                                    tv_core3.setText(formatCPUFreq(ReadCPU0(cpu3)) + " MHz");


                                } else {
                                    tv_core3.setText("Offline");
                                }
                                if (cpu4file.exists()) {
                                    tv_core4.setText(formatCPUFreq(ReadCPU0(cpu4)) + " MHz");

                                } else {
                                    tv_core4.setText("Offline");
                                }

                                if (cpu5file.exists()) {
                                    tv_core5.setText(formatCPUFreq(ReadCPU0(cpu5)) + " MHz");

                                } else {
                                    tv_core5.setText("Offline");
                                }

                                if (cpu6file.exists()) {
                                    tv_core6.setText(formatCPUFreq(ReadCPU0(cpu6)) + " MHz");

                                } else {
                                    tv_core6.setText("Offline");
                                }

                                if (cpu7file.exists()) {
                                    tv_core7.setText(formatCPUFreq(ReadCPU0(cpu7)) + " MHz");


                                } else {
                                    tv_core7.setText("Offline");
                                }


                                System.out.println("Battery " + ReadCPU0(batterytemp));
                                System.out.println("Thermal " + ReadCPU0(thermal));

                                foo = Integer.parseInt(formatTemp(ReadCPU0(cputemp)));
                                System.out.println(formatTemp(ReadCPU0(cputemp)));
                                System.out.println("foo is " + foo);


                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) - 30;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "");

            set1.enableDashedLine(5f, 5f, 0f);
            set1.setLineWidth(1f);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{5f, 5f}, 0f));
            set1.setFormSize(15.f);
            set1.setValueTextSize(2f);
            set1.enableDashedHighlightLine(5f, 5f, 0f);
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });
            if (Utils.getSDKInt() >= 18) {

                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets
            LineData data = new LineData(dataSets);
            chart.setData(data);
        }
    }

    private String ReadCPU0(String[] input) {
        ProcessBuilder pB;
        String result = "";

        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq"};
            pB = new ProcessBuilder(input);
            pB.redirectErrorStream(false);
            Process process = pB.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024];
            while (in.read(re) != -1) //default -1
            {
                result = new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private String formatCPUFreq(String cpuFreq) {

        String uwot = "";
        Matcher m = pat.matcher(cpuFreq);
        if (m.find()) {
            uwot = m.group(0);
            return uwot.substring(0, uwot.length() - 3);
        } else return "Error";
    }

    private String formatTemp(String cpuFreq) {

        String uwot = "";
        Matcher m = pat2.matcher(cpuFreq);
        if (m.find()) {
            uwot = m.group(0);
            return uwot.substring(0, uwot.length() - 0);
        } else return "0";
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public String getMaxCPUFreqMHz() {
        try {
            Process process = Runtime.getRuntime().exec("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                int frequency = Integer.parseInt(line.trim());
                String cpuFrequency = (frequency / 1000) + " MHz"; // convert to MHz
                // set the CPU frequency in a TextView
                TextView textView = findViewById(R.id.cpu);
                textView.setText("" + cpuFrequency);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
