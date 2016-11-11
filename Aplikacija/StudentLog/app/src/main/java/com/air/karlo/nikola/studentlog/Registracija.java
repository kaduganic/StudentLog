package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.PreferenceManagerHelper;


public class Registracija extends AppCompatActivity {

    TextView oib,ime,prezime, korime,lozinka;
    Spinner uloga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registracija);

        addListenerToButton();


        oib=(TextView)findViewById(R.id.txtoib);
        ime=(TextView)findViewById(R.id.txtIme);
        prezime=(TextView)findViewById(R.id.txtPrezime);
        korime=(TextView)findViewById(R.id.txtKorime);
        uloga=(Spinner)findViewById(R.id.spinner);
        lozinka=(TextView)findViewById(R.id.txtPasword);
    }

    public void addListenerToButton() {
        final Context context = getApplicationContext();
        Button registracija = (Button) findViewById(R.id.btnRegistracija);

        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Osoba osoba=new Osoba();

                if(oib.getText() != null){
                    try {
                        osoba.oib = Integer.parseInt(oib.getText().toString());
                    } catch(NumberFormatException nfe) {
                        Toast.makeText(context, "OIB moraju biti brojevi",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(context, "OIB je prazno polje",
                            Toast.LENGTH_LONG).show();
                }//oib

                if(ime.getText() != null){
                        osoba.ime =ime.getText().toString();
                }else{
                    Toast.makeText(context, "Ime je prazno polje",
                            Toast.LENGTH_LONG).show();
                }//ime

                if(prezime.getText() != null){
                    osoba.prezime =prezime.getText().toString();
                }else{
                    Toast.makeText(context, "Prezime je prazno polje",
                            Toast.LENGTH_LONG).show();
                }//prezime

                if(korime.getText() != null){
                    osoba.korime =korime.getText().toString();
                }else{
                    Toast.makeText(context, "Korisnicko ime je prazno polje",
                            Toast.LENGTH_LONG).show();
                }//korisnicko ime

                if(uloga.getSelectedItem() != null){
                    osoba.uloga =uloga.getSelectedItem().toString();
                }else{
                    Toast.makeText(context, "Uloga je prazno polje",
                            Toast.LENGTH_LONG).show();
                }//uloga

                if(lozinka.getText() != null){
                    osoba.lozinka =lozinka.getText().toString();
                }else{
                    Toast.makeText(context, "Lozinka je prazno polje",
                            Toast.LENGTH_LONG).show();
                }//lozinka
               PreferenceManagerHelper.spremiOsoba(osoba.oib,osoba.ime,osoba.prezime,osoba.korime,osoba.lozinka,osoba.uloga,context);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Context contex = getApplicationContext();
        Intent intent = new Intent(contex, Prijava.class);
        startActivity(intent);
        finish();
    }

}
