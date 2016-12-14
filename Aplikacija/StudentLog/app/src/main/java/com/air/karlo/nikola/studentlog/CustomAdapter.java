package com.air.karlo.nikola.studentlog;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class CustomAdapter extends ArrayAdapter<Kolegiji> {


    public CustomAdapter(Context context, ArrayList<Kolegiji> kolegiji) {
        super(context, 0,  kolegiji);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Kolegiji singleKolegij = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_adapter_kolegij, parent, false);


        TextView nazivKolegija = (TextView) convertView.findViewById(R.id.txtNaziv);
        TextView ects = (TextView) convertView.findViewById(R.id.txtEcts);

        nazivKolegija.setText(singleKolegij.naziv);
        ects.setText(singleKolegij.ects);

        return convertView;
    }
}
