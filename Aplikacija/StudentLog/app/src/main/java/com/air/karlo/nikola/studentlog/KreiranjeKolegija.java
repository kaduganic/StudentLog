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

import tipoviPodatka.Kolegiji;
import tipoviPodatka.Osoba;

/**
 * Created by Nikola on 1.12.2016..
 */

public class KreiranjeKolegija extends AppCompatActivity {

    Osoba osoba;
    TextView txtImePrezime, txtNazivKolegija, txtEcstKolegija;
    final Kolegiji koleg = new Kolegiji();
    int brojac;

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

        brojac = 0;
        addListenerToButton();
    }

    private void addListenerToButton(){
        final Context context = getApplicationContext();
        Button kreiranjeKolegija = (Button) findViewById(R.id.btnSpremiKreiranjeKolegija);

        kreiranjeKolegija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean regisOk = true;
                if(!txtNazivKolegija.getText().toString().matches("")){
                    koleg.naziv = txtNazivKolegija.getText().toString();
                }else{
                    Toast.makeText(context, "Naziv je prazno polje!",
                            Toast.LENGTH_LONG).show();
                    regisOk = false;
                }//naziv

                if(!txtEcstKolegija.getText().toString().matches("") && !txtEcstKolegija.getText().toString().matches("[a-zA-Z]+")){
                    koleg.ects = Integer.parseInt(txtEcstKolegija.getText().toString());
                }else{
                    Toast.makeText(context, "Ects je prazno polje ili nije numericka vrijednost!",
                            Toast.LENGTH_LONG).show();
                    regisOk = false;
                }//ects

                if(regisOk){
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Kolegiji>>(){}.getType();
                    List<Kolegiji> listaKolegija = gson.fromJson(PreferenceManagerHelper.getKolegije(context), type);
                    List<Kolegiji> kolegiji =  new ArrayList<Kolegiji>();

                    if(listaKolegija != null){
                        brojac = listaKolegija.size();
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
                        koleg.id = brojac;
                        kolegiji.add(koleg);


                        Intent intent = new Intent(context, DetaljiKolegija.class);
                        intent.putExtra("osoba", osoba);
                        intent.putExtra("kolegij",koleg);
                        startActivity(intent);
                        finish();
                    }   else {
                        Toast.makeText(context, "Kolegij vec postoji!",
                                Toast.LENGTH_LONG).show();
                    }

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
