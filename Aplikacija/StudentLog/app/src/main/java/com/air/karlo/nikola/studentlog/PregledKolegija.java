package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.type;

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
        List<Kolegiji> listaSvihKolegija = gson.fromJson(PreferenceManagerHelper.getKolegije(context), typeKol);
        List<Kolegiji> listaTrenutnog = new ArrayList<>();
        List<StudentImaKolegij> listStudnImaKol = gson.fromJson(PreferenceManagerHelper.getStudentImaKoleg(context), typeStImKol);

        if(listaSvihKolegija != null){
                for (Kolegiji kol : listaSvihKolegija) {
                    if(kol.idNositelj == osoba.oib )
                        listaTrenutnog.add(kol);
                    if(osoba.uloga.equals("Student")){
                        for(StudentImaKolegij stImKo : listStudnImaKol) {
                            if (stImKo.idKolegij == kol.id){
                                listaTrenutnog.add(kol);
                            }
                        }
                }
            }
            CustomAdapter pregledKolegija = new CustomAdapter(this, listaTrenutnog);

            ListView listView = (ListView) findViewById(R.id.lstPregledKolegija);
            listView.setAdapter(pregledKolegija);
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
