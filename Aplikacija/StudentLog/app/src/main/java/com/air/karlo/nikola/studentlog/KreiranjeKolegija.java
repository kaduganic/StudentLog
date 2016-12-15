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

import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        txtNazivKolegija = (TextView) findViewById(R.id.txtNazivKolegijaKreiraj);
        txtEcstKolegija = (TextView) findViewById(R.id.txtEctsKreirajKolegij);

        addListenerToButton();
    }

    private void addListenerToButton(){
        final Context context = getApplicationContext();
        Button kreiranjeKolegija = (Button) findViewById(R.id.btnSpremiKreiranjeKolegija);
        final Kolegiji koleg = new Kolegiji();

        kreiranjeKolegija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean regisOk = true;
                if(!txtNazivKolegija.getText().equals("")){
                    koleg.naziv = txtNazivKolegija.getText().toString();
                }else{
                    Toast.makeText(context, "Naziv je prazno polje",
                            Toast.LENGTH_LONG).show();
                    regisOk = false;
                }//naziv

                if(!txtEcstKolegija.getText().equals("")){
                    koleg.ects = Integer.parseInt(txtEcstKolegija.getText().toString());
                }else{
                    Toast.makeText(context, "Ects je prazno polje",
                            Toast.LENGTH_LONG).show();
                    regisOk = false;
                }//ects

                if(regisOk){
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Kolegiji>>(){}.getType();
                    List<Kolegiji> listaKolegija = gson.fromJson(PreferenceManagerHelper.getKolegije(context), type);
                    List<Kolegiji> kolegiji =  new ArrayList<Kolegiji>();

                    if(listaKolegija != null){
                        for (Kolegiji ko:listaKolegija) {
                            kolegiji.add(ko);
                            if(ko.naziv.equals(koleg.naziv)){
                                regisOk = false;
                                break;
                            }
                        }
                    }
                    if(regisOk){
                        koleg.idNositelj = osoba.oib;
                        kolegiji.add(koleg);
                        String jsonKolegij = gson.toJson(kolegiji);
                        PreferenceManagerHelper.spremiKolegij(jsonKolegij,context);
                    }   else {
                        Toast.makeText(context, "Kolegij vec postoji!",
                                Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent(context, PregledKolegija.class);
                    intent.putExtra("osoba", osoba);
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
