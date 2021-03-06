package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import tipoviPodatka.Osoba;

public class Prijava extends AppCompatActivity {

    TextView korisnickoIme,lozinka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prijava); //povezivanje sa xmlom prijava

        addListenerToButton();
        korisnickoIme = (TextView) findViewById(R.id.txtKorIme);
        lozinka = (TextView)findViewById(R.id.txtLozinka);
    }

    public void addListenerToButton() {
        final Context context = getApplicationContext();

        Button registracija = (Button) findViewById(R.id.registration_button);
        Button prijava = (Button) findViewById(R.id.btnPrijava);

        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Registracija.class); //priprema aktivitia koja se otvara
                startActivity(intent);  //otvori aktiviti
                finish();   //zatvori trenutni ekran
            }
        });
        prijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Meni.class); //

                //dohvacanje iz baze
                Gson gson = new Gson();
                Type type = new TypeToken<List<Osoba>>(){}.getType();
                List<Osoba> osobe = gson.fromJson(PreferenceManagerHelper.getOsobe(context), type);
                //

                final String kIme = korisnickoIme.getText().toString();
                final String loz = lozinka.getText().toString();
                boolean statusPronadenog = true;

                if(osobe != null){  //ako postoje osobe
                    for (Osoba os:osobe) { //prodi sve trenutne osobe
                        if(os.korime.equals(kIme)  && os.lozinka.equals(loz)){  //ako je sifra dobra i ako je sifra dobra
                            intent.putExtra("osoba", os);      //prosljedi ulogiranu osobu na sljedeci ekran
                            startActivity(intent);  //pokreni aktivnost meni
                            finish();               //ugasi trenutni aktiviti
                            statusPronadenog = false;
                            break;
                        }else statusPronadenog = true;  //nije pronaden korisnik
                    }
                    if(statusPronadenog) Toast.makeText(context, "Korisnik ne postoji!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
