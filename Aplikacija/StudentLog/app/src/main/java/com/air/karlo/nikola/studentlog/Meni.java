package com.air.karlo.nikola.studentlog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Nikola on 25.11.2016..
 */

public class Meni extends AppCompatActivity {

    Osoba osoba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meni);
        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba");

        TextView txtKorisnik = (TextView) findViewById(R.id.txtKorisnik) ;
        Button btnKolegij = (Button)findViewById(R.id.btnKolegij);
        Button btnPregKol = (Button)findViewById(R.id.btnPregledKolegija);
        Button btnDolasci = (Button)findViewById(R.id.btnDolasci);
        Button btnQRKod = (Button)findViewById(R.id.btnQRkod);

        txtKorisnik.setText(osoba.ime + " " + osoba.prezime);
        if(osoba.uloga.equals("Profesor")){
            btnKolegij.setText("Kreiranje kolegija");
            btnPregKol.setText("Pregled kolegija");
            btnDolasci.setText("Pregled dolazaka");
            btnQRKod.setText("Kreiranje koda");
        }else if(osoba.uloga.equals("Student")){
            btnKolegij.setText("Odabir kolegija");
            btnPregKol.setText("Pregled kolegija");
            btnDolasci.setText("Prijava dolazaka");
            btnQRKod.setVisibility(View.GONE);
        }
    }



}
