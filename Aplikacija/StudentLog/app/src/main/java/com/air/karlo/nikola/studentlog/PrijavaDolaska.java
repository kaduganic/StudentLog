package com.air.karlo.nikola.studentlog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dohvacanjePodataka.dohvacanjeQRSifri;
import dohvacanjePodataka.dohvatRucnoUnesenihSifri;
import tipoviPodatka.Dolasci;
import tipoviPodatka.Kod;
import tipoviPodatka.Kolegiji;
import tipoviPodatka.Osoba;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.LTGRAY;


public class PrijavaDolaska extends AppCompatActivity{

    Osoba osoba;
    DatePicker datum;
    DohvacanjeKodaInterface dohvacanjeKodaDolaska;      //interface za modularno
    Button btnSkenirajQR, btnPrijaviDolazak, btnUnesiRucnoKod;
    TextView edTxtKodDolaska;
    List<Dolasci> listaStarihDolazaka;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.prijava_dolaska); //povezi sa layoutom
        final Activity activity = this;
        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba");     //dohvati podatke o osobi trenutno ulogiranoj

        datum = (DatePicker) findViewById(R.id.dtIzaberiDatumPrijavaDolaska);
        btnSkenirajQR = (Button)findViewById(R.id.btnSkenirajQRPrijavaDolaska);
        btnPrijaviDolazak = (Button)findViewById(R.id.btnPrijaviDolazak);
        btnPrijaviDolazak.setEnabled(false);
        btnUnesiRucnoKod = (Button)findViewById(R.id.btnUnesiRucno);
        edTxtKodDolaska = (TextView) findViewById(R.id.edtxtUnesiteSifru);
        TextView txtKorisnik = (TextView) findViewById(R.id.txtImePrezimePrijavaDolaska);
        txtKorisnik.setText(osoba.ime  + " " + osoba.prezime);

        btnUnesiRucnoKod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {           //rucni unos koda
                dohvacanjeKodaDolaska = new dohvatRucnoUnesenihSifri(); //modularno za rucno unesene sifre
                btnPrijaviDolazak.setEnabled(true);
            }
        });

        btnSkenirajQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dohvacanjeKodaDolaska = new dohvacanjeQRSifri();        //modularno za qr kodove
                btnPrijaviDolazak.setEnabled(true);
            }
        });


        Gson gson = new Gson(); //za dohvat iz prefrencesa
        Type type = new TypeToken<List<Dolasci>>(){}.getType(); //tip podakta koji se dohvaca
        listaStarihDolazaka = gson.fromJson(PreferenceManagerHelper.getDolasci(context), type); //dohvati dolaske
        final List<Dolasci> listaNovihDolazaka = new ArrayList<>();

        btnPrijaviDolazak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //prijavi dolazak
                if (edTxtKodDolaska.getText().toString().matches("")) {
                    Toast.makeText(context, "Unesite kod dolaska!", Toast.LENGTH_SHORT).show();
                } else {
                    dohvacanjeKodaDolaska.dohvacanjeKoda(new DohvacanjeKodaListener() {
                        @Override
                        public void DohvaceniKod(String dohvatKoda) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<Kod>>(){}.getType();
                            List<Kod> kodovi = gson.fromJson(PreferenceManagerHelper.getGeneriraniKod(context), type);
                            List<Kod> listaSvihKodova = new ArrayList<Kod>();
                            for (Kod k: kodovi) {
                            if(k.qrImage != null){
                            listaSvihKodova.add(k);
                            }
                            }
                            int day = datum.getDayOfMonth();        //dohvati datum
                            int month = datum.getMonth();
                            int year = datum.getYear();
                            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy"); //format datuma
                            String datumDolaskaStudenta = sdf.format(new Date(year, month, day));
                            Dolasci dolasci = new Dolasci();
                            Boolean statusDolaska = false;
                            edTxtKodDolaska.setText(dohvatKoda);
                            if (listaStarihDolazaka == null) { //ukoliko postoje dolasci
                                for (Kod k : listaSvihKodova) {     //prodi kroz sve kodove
                                    if (k.sifraDolaska.equals(edTxtKodDolaska.getText().toString()) && k.datum.equals(datumDolaskaStudenta)) {  //pronadi sifru i datum za studenta
                                        dolasci.idKolegija = k.idKolegija;  //spremi id kolegija
                                        dolasci.idStudenta = osoba.oib;     //spremi id osobe
                                        dolasci.datum = datumDolaskaStudenta; //spremi datum
                                        listaNovihDolazaka.add(dolasci);        //dodaj u listu
                                        Set<Dolasci> hs = new HashSet<>();
                                        hs.addAll(listaNovihDolazaka);          //ukloni duplikate
                                        listaNovihDolazaka.clear();
                                        listaNovihDolazaka.addAll(hs);
                                        Gson gson1 = new Gson();                 //za spremanje dolazaka
                                        String jsonDolaska = gson1.toJson(listaNovihDolazaka);
                                        PreferenceManagerHelper.spremiDolaske(jsonDolaska, context);    //spremi dolaske
                                        Toast.makeText(context, "Dolazak je uspijesno prijavljen.", Toast.LENGTH_SHORT).show();

                                        break;
                                    }
                                }
                            } else {
                                if (listaSvihKodova != null) {
                                    for (Kod k : listaSvihKodova) {
                                        listaNovihDolazaka.addAll(listaStarihDolazaka); //prepisemo dosadanje dolaske studenta
                                        Set<Dolasci> hss = new HashSet<>();     //
                                        hss.addAll(listaNovihDolazaka);         //ukloni duplikate
                                        listaNovihDolazaka.clear();             //
                                        listaNovihDolazaka.addAll(hss);         //
                                        if (k.sifraDolaska.equals(edTxtKodDolaska.getText().toString()) && k.datum.equals(datumDolaskaStudenta)) { //provjera dal se vec upisao
                                            dolasci.idKolegija = k.idKolegija;
                                            dolasci.idStudenta = osoba.oib;
                                            dolasci.datum = datumDolaskaStudenta;
                                            Set<Dolasci> hs = new HashSet<>();
                                            hs.addAll(listaNovihDolazaka);      //ukloni duplikate
                                            listaNovihDolazaka.clear();         //ukloni duplikate
                                            listaNovihDolazaka.addAll(hs);
                                            listaNovihDolazaka.add(dolasci);
                                            Gson gson2 = new Gson();     //za spremanje dolazaka
                                            String jsonDolaska = gson2.toJson(listaNovihDolazaka);
                                            PreferenceManagerHelper.spremiDolaske(jsonDolaska, context); //spremi dolaske
                                            Toast.makeText(context, "Dolazak je uspijesno prijavljen.", Toast.LENGTH_SHORT).show();
                                            statusDolaska = false;
                                            break;
                                        } else statusDolaska = true;
                                    }
                                }
                            }
                            if(statusDolaska) Toast.makeText(context, "Pogresni podaci!", Toast.LENGTH_SHORT).show();
                        } //modularno - metoda za dohvacanje sifri ovisno o unosu
                    }, activity, context);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Context contex = getApplicationContext();
        Intent intent = new Intent(contex, Meni.class);
        intent.putExtra("osoba", osoba);
        startActivity(intent);
        finish();
    }
}
