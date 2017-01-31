package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Type;

import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tipoviPodatka.Osoba;

public class Registracija extends AppCompatActivity {

    TextView oib,ime,prezime, korime,lozinka;
    Spinner uloga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registracija);  //spajanje aktivitia sa xmlom registracija

        addListenerToButton();

        //dohvacamo textViewove sa layouta
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
                Osoba osoba = new Osoba();
                Boolean regisOk = true;

                //validacije podataka
                if(oib.getText() != null){
                    try {
                        osoba.oib = Integer.parseInt(oib.getText().toString());
                    } catch(NumberFormatException nfe) {
                        Toast.makeText(context, "OIB moraju biti brojevi",
                                Toast.LENGTH_LONG).show();
                                regisOk = false;
                    }
                }else{
                    Toast.makeText(context, "OIB je prazno polje",
                            Toast.LENGTH_LONG).show();
                            regisOk = false;
                }//oib

                if(!ime.getText().equals("")){
                        osoba.ime =ime.getText().toString();
                }else{
                    Toast.makeText(context, "Ime je prazno polje",
                            Toast.LENGTH_LONG).show();
                            regisOk = false;
                }//ime

                if(!prezime.getText().equals("")){
                    osoba.prezime =prezime.getText().toString();
                }else{
                    Toast.makeText(context, "Prezime je prazno polje",
                            Toast.LENGTH_LONG).show();
                            regisOk = false;
                }//prezime

                if(!korime.getText().equals("")){
                    osoba.korime =korime.getText().toString();
                }else{
                    Toast.makeText(context, "Korisnicko ime je prazno polje",
                            Toast.LENGTH_LONG).show();
                            regisOk = false;
                }//korisnicko ime

                if(uloga.getSelectedItem() != null){
                    osoba.uloga =uloga.getSelectedItem().toString();
                }else{
                    Toast.makeText(context, "Uloga je prazno polje",
                            Toast.LENGTH_LONG).show();
                            regisOk = false;
                }//uloga

                if(!lozinka.getText().equals("")){
                    osoba.lozinka =lozinka.getText().toString();
                }else{
                    Toast.makeText(context, "Lozinka je prazno polje",
                            Toast.LENGTH_LONG).show();
                            regisOk = false;
                }//lozinka

                //dohvacanje iz baze  vec registrirane osobe
                Gson gson = new Gson();
                Type type = new TypeToken<List<Osoba>>(){}.getType();
                List<Osoba> listaOsoba = gson.fromJson(PreferenceManagerHelper.getOsobe(context), type);
                //
                List<Osoba> osobe =  new ArrayList<Osoba>();    //sluzi za spremanje liste postojecih podataka
                if(listaOsoba != null){
                    for (Osoba os:listaOsoba) { //cita stare osobe spremljene u bazi
                        osobe.add(os);          //sprema stare osobe
                        if(os.korime.equals(osoba.korime)){ //provjera da li postoji vec ta osoba u bazi
                            regisOk = false;
                            break;
                        }
                    }
                }

                if(regisOk){ //ako je sve proslo dobro spremi
                    osobe.add(osoba);      //dodajemo novu osobu
                    String jsonOsobe = gson.toJson(osobe);  //kreiramo string jsona
                    PreferenceManagerHelper.spremiOsoba(jsonOsobe,context); //spremamo u bazu
                    closeRegOpenPrij();
                }   else {
                    Toast.makeText(context, "Korisnicko ime vec postoji!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeRegOpenPrij();
    }


    private void closeRegOpenPrij(){
        Context contex = getApplicationContext();
        Intent intent = new Intent(contex, Prijava.class);
        startActivity(intent);
        finish();
    }

}
