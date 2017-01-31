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

public class DetaljiKolegija extends AppCompatActivity {

    Osoba osoba;
    Kolegiji kolegij;
    TextView txtImePrezime, txtDetaljiKolegija, txtEctsDetKol;
    EditText txtOpisKolegija, txtUvijetiKolegija;
    Button spremiKolegij;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detalji_kolegija);  //spajanje aktivitia s xmlom detalji kolegija

        osoba = getIntent().getExtras().getParcelable("osoba"); //dohvacanje podataka koji su poslani sa proslog aktivitia
        kolegij = getIntent().getExtras().getParcelable("kolegij"); //dohvacanje podatak koji su poslani sa proslog aktivitia

        //spajanje varijabli sa xml toolsima
        txtImePrezime = (TextView)findViewById(R.id.txtImePrezimeDetaljiKolegija);
        txtDetaljiKolegija =(TextView)findViewById(R.id.txtNazivKolegijaDetaljKolegija);
        txtEctsDetKol = (TextView)findViewById(R.id.txtEctsDetKol);
        txtOpisKolegija = (EditText)findViewById(R.id.txtOpisKolDetKol);
        txtUvijetiKolegija = (EditText) findViewById(R.id.txtUvijetiKolDetKol);
        spremiKolegij = (Button) findViewById(R.id.btnSpremiDetaljeKolegija);

        //ispis imena i prezima te naziva kolegija i ects-a
        txtImePrezime.setText(osoba.ime  + " " + osoba.prezime);
        txtDetaljiKolegija.setText(kolegij.naziv + "");
        txtEctsDetKol.setText(kolegij.ects + "");

        if(osoba.uloga.equals("Student")){      //ukoliko se ulogirao student
            spremiKolegij.setEnabled(false);    //disable dugme spremi kolegij koje je namjenjeno profesoru
            spremiKolegij.setVisibility(View.GONE); //ukloni dugme sa ekrana
            txtUvijetiKolegija.setEnabled(false);   //onemoguci uredivanje uvijeta kolegija, student samo vidi ispis
            txtUvijetiKolegija.setTextColor(BLACK); //postavi boju slova na crno
            txtOpisKolegija.setEnabled(false);  //onemoguci uredivanje opisa kolegija, student samo vidi ispis
            txtOpisKolegija.setTextColor(BLACK);    //postavi boju slova na crno
        }

        if(kolegij.uvijeti != null && kolegij.opisKolegija != null){ //Ukoliko postoji neki zapis o uvijetima i opisu kolegija
            txtUvijetiKolegija.setText(kolegij.uvijeti + "");   //Ispisi uvijete kolegija
            txtOpisKolegija.setText(kolegij.opisKolegija + ""); //Ispisi opis kolegija
        }

        spremiKolegij.setOnClickListener(new View.OnClickListener() {   //button spremi kolegij listener
            final Context context = getApplicationContext();
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();     //gson koji sluzi za dohvat kolegija iz preferencesa
                Type type = new TypeToken<List<Kolegiji>>(){}.getType();    //tip podatka koji dohvacamo iz preferencesa
                List<Kolegiji> listaKolegija = gson.fromJson(PreferenceManagerHelper.getKolegije(context), type); //dohvat kolegija
                List<Kolegiji> kolegiji =  new ArrayList<Kolegiji>();

                kolegij.opisKolegija = txtOpisKolegija.getText().toString(); //dohvati opis koji je profesor upisao i spremi ga u varijablu
                kolegij.uvijeti = txtUvijetiKolegija.getText().toString();  //dohvati uvijete koje je profesor upisai i spremi ga u varijablu
                boolean novi = true;
                if(listaKolegija != null){  //ukoliko postoji kolegij u bazi
                for (Kolegiji ko:listaKolegija) {   //prolaz kroz sve kolegije
                    kolegiji.add(ko);   //dodaj sve spremljene kolegije
                    if(ko.naziv.equals(kolegij.naziv)){ //pronadi isto ime
                        kolegiji.set(kolegij.id, kolegij);  //ukoliko je isto ime azuriraj podatke o kolegiju (opis i uvijete)
                        novi = false;
                    }
                }}
                if(novi) kolegiji.add(kolegij); //ukoliko nema postojecih zapisa o kolegiju smatra se novim te se sprema

                String jsonKolegij = gson.toJson(kolegiji);     //gson za spremanje u preferences
                PreferenceManagerHelper.spremiKolegij(jsonKolegij,context); //spremi sve kolegije

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
