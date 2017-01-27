package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tipoviPodatka.Osoba;

/**
 * Created by Nikola on 25.11.2016..
 */

public class Meni extends AppCompatActivity {

    Osoba osoba;
    Button btnKolegij,btnPregKol,btnDolasci,btnQRKod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meni);
        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba");

        TextView txtKorisnik = (TextView) findViewById(R.id.txtKorisnik) ;
        btnKolegij = (Button)findViewById(R.id.btnKolegij);
        btnPregKol = (Button)findViewById(R.id.btnPregledKolegija);
        btnDolasci = (Button)findViewById(R.id.btnDolasci);
        btnQRKod = (Button)findViewById(R.id.btnQRkod);

        txtKorisnik.setText(osoba.ime  + " " + osoba.prezime);
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
        addListenerToButton();
    }

    private void addListenerToButton(){
        final Context context = getApplicationContext();
        btnKolegij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KreiranjeKolegija.class);
                Intent intentst = new Intent(context, OdabirKolegija.class);
                if(osoba.uloga.equals("Profesor")){
                    intent.putExtra("osoba", osoba);
                    startActivity(intent);
                    finish();
                }else if(osoba.uloga.equals("Student")){
                    //odabir kolegija
                    intentst.putExtra("osoba", osoba);
                    startActivity(intentst);
                    finish();
                }
            }
        });

        btnPregKol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PregledKolegija.class);
                if(osoba.uloga.equals("Profesor")){
                    intent.putExtra("osoba", osoba);
                    startActivity(intent);
                    finish();
                }else if(osoba.uloga.equals("Student")){
                    //pregled kolegija
                    intent.putExtra("osoba", osoba);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnDolasci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(osoba.uloga.equals("Profesor")){
                    //pregled dolazaka
                }else if(osoba.uloga.equals("Student")){
                    //prijava dolazaka
                    Intent intent = new Intent(context, PrijavaDolaska.class);
                    intent.putExtra("osoba", osoba);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnQRKod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //profesor kreira kod
                Intent intent = new Intent(context, KreiranjeKoda.class);
                intent.putExtra("osoba",osoba);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        final Intent intent = new Intent(this, Prijava.class);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Zatvaranje aplikacije")
                .setMessage("Da li ste sigurni da želite izaći i odjaviti se?")
                .setPositiveButton("Da", new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent.putExtra("osoba",osoba);
                        startActivity(intent);
                        finish();
                    }

                })
                .setNegativeButton("Ne", null)
                .show();
    }
}
