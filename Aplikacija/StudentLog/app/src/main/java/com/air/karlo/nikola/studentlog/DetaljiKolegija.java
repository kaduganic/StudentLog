package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static android.graphics.Color.BLACK;

/**
 * Created by Nikola on 29.1.2017..
 */

public class DetaljiKolegija extends AppCompatActivity {

    Osoba osoba;
    Kolegiji kolegij;
    TextView txtImePrezime, txtDetaljiKolegija, txtEctsDetKol;
    EditText txtOpisKolegija, txtUvijetiKolegija;
    Button spremiKolegij;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detalji_kolegija);

        osoba = getIntent().getExtras().getParcelable("osoba");
        kolegij = getIntent().getExtras().getParcelable("kolegij");

        txtImePrezime = (TextView)findViewById(R.id.txtImePrezimeDetaljiKolegija);
        txtDetaljiKolegija =(TextView)findViewById(R.id.txtNazivKolegijaDetaljKolegija);
        txtEctsDetKol = (TextView)findViewById(R.id.txtEctsDetKol);
        txtOpisKolegija = (EditText)findViewById(R.id.txtOpisKolDetKol);
        txtUvijetiKolegija = (EditText) findViewById(R.id.txtUvijetiKolDetKol);
        spremiKolegij = (Button) findViewById(R.id.btnSpremiDetaljeKolegija);

        txtImePrezime.setText(osoba.ime  + " " + osoba.prezime);
        txtDetaljiKolegija.setText(kolegij.naziv + "");
        txtEctsDetKol.setText(kolegij.ects + "");

        if(osoba.uloga.equals("Student")){
            spremiKolegij.setEnabled(false);
            spremiKolegij.setVisibility(View.GONE);
            txtUvijetiKolegija.setEnabled(false);
            txtUvijetiKolegija.setTextColor(BLACK);
            txtOpisKolegija.setEnabled(false);
            txtOpisKolegija.setTextColor(BLACK);
        }
        if(kolegij.uvijeti != null && kolegij.opisKolegija != null){
            txtUvijetiKolegija.setText(kolegij.uvijeti + "");
            txtOpisKolegija.setText(kolegij.opisKolegija + "");
        }

        spremiKolegij.setOnClickListener(new View.OnClickListener() {
            final Context context = getApplicationContext();
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Kolegiji>>(){}.getType();
                List<Kolegiji> listaKolegija = gson.fromJson(PreferenceManagerHelper.getKolegije(context), type);
                List<Kolegiji> kolegiji =  new ArrayList<Kolegiji>();

                kolegij.opisKolegija = txtOpisKolegija.getText().toString();
                kolegij.uvijeti = txtUvijetiKolegija.getText().toString();
                boolean novi = true;
                if(listaKolegija != null){
                for (Kolegiji ko:listaKolegija) {
                    kolegiji.add(ko);
                    if(ko.naziv.equals(kolegij.naziv)){
                        kolegiji.set(kolegij.id, kolegij);
                        novi = false;
                    }
                }}
                if(novi) kolegiji.add(kolegij);

                String jsonKolegij = gson.toJson(kolegiji);
                PreferenceManagerHelper.spremiKolegij(jsonKolegij,context);

                Toast.makeText(context, "Kolegij je uspijesno spremljen.",
                        Toast.LENGTH_SHORT).show();
                izlaz();
            }
        });
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        izlaz();
}

    public void izlaz(){
        Context contex = getApplicationContext();
        Intent intent = new Intent(contex, Meni.class);
        intent.putExtra("osoba", osoba);
        startActivity(intent);
        finish();
    }
}
