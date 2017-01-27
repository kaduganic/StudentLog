package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikola on 27.1.2017..
 */

public class KreiranjeKoda extends AppCompatActivity{

    Osoba osoba;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        setContentView(R.layout.kreiranje_koda);

        osoba = new Osoba();                    //preuzimanje podataka o trenutno ulogiranoj osobi
        osoba = getIntent().getExtras().getParcelable("osoba");
        TextView osobaImePrezime = (TextView) findViewById(R.id.txtImePrezimeKreiranjeKoda);
        osobaImePrezime.setText(osoba.ime  + " " + osoba.prezime);

        //preuzimanje i prikaz kolegija od trenutno ulogiranog profesora
        Gson gson = new Gson();
        Type typeKol = new TypeToken<List<Kolegiji>>(){}.getType();        //dohvacanje podataka iz lokalne baze
        List<Kolegiji> listaSvihKolegija = gson.fromJson(PreferenceManagerHelper.getKolegije(context), typeKol);
        List<String> listaTrenutnog = new ArrayList<>();
        if(listaSvihKolegija != null) {
            for (Kolegiji kol : listaSvihKolegija) {
                if (kol.idNositelj == osoba.oib)
                    listaTrenutnog.add(kol.naziv);
            }
        }               //napuni spinner podacima
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTrenutnog);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spnrPopisKolegijaKreiranjeKoda);
        sItems.setAdapter(adapter);
        //prikaz kolegija od trenutnog profesora

    }
}
