package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nikola on 1.12.2016..
 */

public class KreiranjeKolegija extends AppCompatActivity {

    Osoba osoba;
    TextView txtImePrezime, txtNazivKolegija, txtEcstKolegija;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kreiranje_kolegija);
        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba");
        txtImePrezime = (TextView) findViewById(R.id.txtImePrezime);
        txtImePrezime.setText(osoba.ime  + " " + osoba.prezime);

        addListenerToButton();
    }

    private void addListenerToButton(){
        final Context context = getApplicationContext();
        Button kreiranjeKolegija = (Button) findViewById(R.id.btnSpremi);
        final Kolegiji kolegiji = new Kolegiji();

        kreiranjeKolegija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean regisOk = true;
                if(!txtNazivKolegija.getText().equals("")){
                    kolegiji.naziv = txtNazivKolegija.getText().toString();
                }else{
                    Toast.makeText(context, "Naziv je prazno polje",
                            Toast.LENGTH_LONG).show();
                    regisOk = false;
                }//naziv

                if(!txtEcstKolegija.getText().equals("")){
                    kolegiji.ects = Integer.parseInt(txtEcstKolegija.getText().toString());
                }else{
                    Toast.makeText(context, "Ects je prazno polje",
                            Toast.LENGTH_LONG).show();
                    regisOk = false;
                }//ects

                if(regisOk){
                    Intent intent = new Intent(context, PregledKolegija.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeKreOpenGlavni();

    }
    private void closeKreOpenGlavni(){
        Context contex = getApplicationContext();
        Intent intent = new Intent(contex, Meni.class);
        intent.putExtra("osoba", osoba);
        startActivity(intent);
        finish();
    }
}
