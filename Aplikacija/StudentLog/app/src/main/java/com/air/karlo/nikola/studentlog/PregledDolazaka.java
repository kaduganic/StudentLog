package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tipoviPodatka.Dolasci;
import tipoviPodatka.Kolegiji;
import tipoviPodatka.Osoba;
import tipoviPodatka.StudentImaKolegij;

/**
 * Created by Nikola on 29.1.2017..
 */

public class PregledDolazaka extends AppCompatActivity {

    Osoba osoba;
    TextView txtKorisnik, txtBrojDolaska, txtPostotakDolaska;
    Button ucitajPodatke;
    DatePicker datum;
    String datm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregled_dolazaka);
        Context context = this;
        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba");

        txtKorisnik = (TextView) findViewById(R.id.txtImePrezimePregledDolazaka) ;
        txtBrojDolaska = (TextView) findViewById(R.id.txtBrojPrisStdPregDol) ;
        txtPostotakDolaska = (TextView) findViewById(R.id.txtPostoPrisStPregDol) ;
        ucitajPodatke = (Button)findViewById(R.id.btnUcitajPregledDolazaka) ;
        datum = (DatePicker) findViewById(R.id.datePicker);

        txtKorisnik.setText(osoba.ime  + " " + osoba.prezime);

        Gson gson = new Gson();
        Type typeKol = new TypeToken<List<Kolegiji>>(){}.getType();        //dohvacanje podataka iz lokalne baze
        final List<Kolegiji> listaSvihKolegija = gson.fromJson(PreferenceManagerHelper.getKolegije(context), typeKol);
        final List<String> listaTrenutnog = new ArrayList<>();
        Type typeDols = new TypeToken<List<Dolasci>>(){}.getType();
        final List<Dolasci> listaDolazaka = gson.fromJson(PreferenceManagerHelper.getDolasci(context), typeDols);
        final List<String> naziviKolegija = new ArrayList<>();
        final List<String> naziviKolStImaKol = new ArrayList<>();

        Set<Dolasci> hs = new HashSet<>();
        hs.addAll(listaDolazaka);
        listaDolazaka.clear();
        listaDolazaka.addAll(hs);

        Type typeStIKol = new TypeToken<List<StudentImaKolegij>>(){}.getType();
        final List<StudentImaKolegij> listaStudImaKol = gson.fromJson(PreferenceManagerHelper.getStudentImaKoleg(context), typeStIKol);
        Set<StudentImaKolegij> hss = new HashSet<>();
        hss.addAll(listaStudImaKol);
        listaStudImaKol.clear();
        listaStudImaKol.addAll(hss);

        if(listaSvihKolegija != null) {
            for (Kolegiji kol : listaSvihKolegija) {
                if (kol.idNositelj == osoba.oib){
                    listaTrenutnog.add(kol.naziv);
                }
                for (Dolasci dolasci:listaDolazaka) {
                    if (kol.id == dolasci.idKolegija){
                        naziviKolegija.add(kol.naziv);
                    }
                }
                for (StudentImaKolegij stImaKol:listaStudImaKol) {
                    if (kol.id == stImaKol.idKolegij){
                        naziviKolStImaKol.add(kol.naziv);
                    }
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTrenutnog);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems = (Spinner) findViewById(R.id.spnPopisKolegijaPregeldDolaska);
        sItems.setAdapter(adapter);
        int   day  = datum.getDayOfMonth();
        int   month= datum.getMonth();
        int   year = datum.getYear();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        datm = sdf.format(new Date(year, month, day));



        ucitajPodatke.setOnClickListener(new View.OnClickListener() {

            int pristuniStudenti = 0, sviStudenti = 0;

            @Override
            public void onClick(View v) {
                    for (Dolasci ds:listaDolazaka) {
                        for (String s:naziviKolegija) {
                            if(ds.datum.equals(datm) && s.equals(sItems.getSelectedItem().toString())){
                                pristuniStudenti++;
                        }
                        }
                }
                    for (String s:naziviKolStImaKol) {
                        if(s.equals(sItems.getSelectedItem().toString())){
                            sviStudenti++;
                        }
                }
                txtBrojDolaska.setText(pristuniStudenti + "");
                txtPostotakDolaska.setText((((double)pristuniStudenti/(double)sviStudenti)*100) + "% dolaska");
                pristuniStudenti = 0;
                sviStudenti = 0;
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
