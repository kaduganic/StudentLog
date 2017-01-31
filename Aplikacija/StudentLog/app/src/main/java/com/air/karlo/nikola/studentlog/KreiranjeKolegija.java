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
        setContentView(R.layout.kreiranje_kolegija);    //povezi klasu sa layoutom
        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba"); //preuzimanje podataka o trenutno ulogiranom korisniku

        //spajanje varijabli sa viewima
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
            public void onClick(View v) {   //listener za button kreiranje kolegija
                Boolean regisOk = true;
                if(!txtNazivKolegija.getText().toString().matches("")){  //ako je unesen tekst za naziv kolegija
                    koleg.naziv = txtNazivKolegija.getText().toString();   //spremi naziv kolegija
                }else{
                    Toast.makeText(context, "Naziv je prazno polje!",
                            Toast.LENGTH_LONG).show();
                    regisOk = false;
                }//naziv

                //provjera jesu li ectsi prazno polje ili jesu li unesena slova
                if(!txtEcstKolegija.getText().toString().matches("") && !txtEcstKolegija.getText().toString().matches("[a-zA-Z]+")){
                    koleg.ects = Integer.parseInt(txtEcstKolegija.getText().toString()); //spremi ects
                }else{
                    Toast.makeText(context, "Ects je prazno polje ili nije numericka vrijednost!",
                            Toast.LENGTH_LONG).show();
                    regisOk = false;
                }//ects

                if(regisOk){    //ako je sve proslo ok
                    Gson gson = new Gson(); //gson za dohvat
                    Type type = new TypeToken<List<Kolegiji>>(){}.getType(); //tip podatka koji dohvaca
                    List<Kolegiji> listaKolegija = gson.fromJson(PreferenceManagerHelper.getKolegije(context), type); //dohvati kolegije
                    List<Kolegiji> kolegiji =  new ArrayList<Kolegiji>();

                    if(listaKolegija != null){  //ukoliko postoje kolegiji
                        brojac = listaKolegija.size();
                        for (Kolegiji ko:listaKolegija) { //prodi kroz sve kolegije
                            kolegiji.add(ko);       //dodaj kolegije
                            if(ko.naziv.equals(koleg.naziv)){
                                regisOk = false;
                                break;
                            }
                        }
                    }
                    if(regisOk){
                        koleg.idNositelj = osoba.oib;   //preuzmi id trenutno ulogirane osobe kao kreatora kolegija
                        koleg.id = brojac;              //dodjeli id kolegiju
                        kolegiji.add(koleg);            //dodaj kolegij


                        Intent intent = new Intent(context, DetaljiKolegija.class); //otvori klasu detalji kolegija
                        intent.putExtra("osoba", osoba);        //prosljedi podatke o osobi
                        intent.putExtra("kolegij",koleg);       //prosljedi podatke o kolegiju
                        startActivity(intent);                  //pokreni aktiviti
                        finish();                               //ugasi trenutni
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
