package com.example.cputhrolling.UI.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cputhrolling.R;
import com.example.cputhrolling.UI.activity.model.cpuinfomodel;

import java.util.ArrayList;

public class CpuInformationAdapter extends ArrayAdapter<cpuinfomodel> {

    public CpuInformationAdapter(@NonNull Context context, ArrayList<cpuinfomodel> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_cpuinfomodel, parent, false);
        }
        cpuinfomodel currentNumberPosition = getItem(position);

        TextView textView1 = currentItemView.findViewById(R.id.title);
        textView1.setText(currentNumberPosition.getMprocess());
        return currentItemView;
    }
}

