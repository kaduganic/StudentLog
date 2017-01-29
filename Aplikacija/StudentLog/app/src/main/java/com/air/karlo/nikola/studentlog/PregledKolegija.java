package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tipoviPodatka.Kolegiji;
import tipoviPodatka.Osoba;
import tipoviPodatka.StudentImaKolegij;

import static android.graphics.Color.LTGRAY;

/**
 * Created by Nikola on 14.12.2016..
 */

public class PregledKolegija extends AppCompatActivity {

    Osoba osoba;
    TextView txtImePrezime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        setContentView(R.layout.pregled_kolegija);
        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba");
        txtImePrezime = (TextView) findViewById(R.id.txtImePrezime);
        txtImePrezime.setText(osoba.ime  + " " + osoba.prezime);

        Gson gson = new Gson();
        Type typeKol = new TypeToken<List<Kolegiji>>(){}.getType();
        Type typeStImKol = new TypeToken<List<StudentImaKolegij>>(){}.getType();
        final List<Kolegiji> listaSvihKolegija = gson.fromJson(PreferenceManagerHelper.getKolegije(context), typeKol);
        List<Kolegiji> listaTrenutnog = new ArrayList<>();
        List<StudentImaKolegij> listStudnImaKol = gson.fromJson(PreferenceManagerHelper.getStudentImaKoleg(context), typeStImKol);

        if(listaSvihKolegija != null){
                for (Kolegiji kol : listaSvihKolegija) {
                    if(kol.idNositelj == osoba.oib )
                        listaTrenutnog.add(kol);
                    if(osoba.uloga.equals("Student")){
                        for(StudentImaKolegij stImKo : listStudnImaKol) {
                            if (stImKo.idKolegij == kol.id && stImKo.idStudent == osoba.oib){
                                listaTrenutnog.add(kol);
                                Set<Kolegiji> hs = new HashSet<>();
                                hs.addAll(listaTrenutnog);
                                listaTrenutnog.clear();
                                listaTrenutnog.addAll(hs);
                            }
                        }
                }
            }
            CustomAdapter pregledKolegija = new CustomAdapter(this, listaTrenutnog);



            final ListView listView = (ListView) findViewById(R.id.lstPregledKolegija);
            listView.setAdapter(pregledKolegija);
            listView.setTextFilterEnabled(true);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Kolegiji kolegijii = (Kolegiji) parent.getItemAtPosition(position);
                    listView.getChildAt(position).setEnabled(true);
                    for (Kolegiji kol : listaSvihKolegija) {
                        if (kol.naziv == kolegijii.naziv){
                            Intent intent = new Intent(context, DetaljiKolegija.class);
                            intent.putExtra("osoba", osoba);
                            intent.putExtra("kolegij",kol);
                            startActivity(intent);
                            finish();
                        }

                    }

                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closePreglOpenMeni();
    }
    private void closePreglOpenMeni(){
        Context contex = getApplicationContext();
        Intent intent = new Intent(contex, Meni.class);
        intent.putExtra("osoba", osoba);
        startActivity(intent);
        finish();
    }

}
