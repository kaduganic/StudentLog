package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tipoviPodatka.Kolegiji;


public class CustomAdapterOdabirKolegija extends ArrayAdapter<Kolegiji> {


    public CustomAdapterOdabirKolegija(Context context, List<Kolegiji> kolegiji) {
        super(context, 0,  kolegiji);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Kolegiji singleKolegij = getItem(position); //svaki kolegij ima svoju poziciju

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_adapter_kolegij, parent, false);
        }       //pronalazi layout koji ce koristiti

        TextView nazivKolegija = (TextView) convertView.findViewById(R.id.txtNazivAdapter);
        TextView ects = (TextView) convertView.findViewById(R.id.txtEctsAdapter);

        nazivKolegija.setText(singleKolegij.naziv); //naziv se sprema u txt naziva
        ects.setText(singleKolegij.ects + " ects boda"); //ects se sprema u txt ects

        return convertView; //vrati view
    }
}
