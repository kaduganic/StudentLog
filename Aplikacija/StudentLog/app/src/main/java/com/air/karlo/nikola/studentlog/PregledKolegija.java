package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nikola on 14.12.2016..
 */

public class PregledKolegija extends AppCompatActivity {

    Osoba osoba;
    TextView txtImePrezime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregled_kolegija);
        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba");
        txtImePrezime = (TextView) findViewById(R.id.txtImePrezime);
        txtImePrezime.setText(osoba.ime  + " " + osoba.prezime);


        ArrayList<Kolegiji> kolegiji = new ArrayList<>();
        Kolegiji kol1 = new Kolegiji(1 , "Baze znanja 1", 5);
        Kolegiji kol2 = new Kolegiji(2, "Programiranje" , 3);
        kolegiji.add(kol1);
        kolegiji.add(kol2);

        CustomAdapter pregledKolegija = new CustomAdapter(this, kolegiji);

        ListView listView = (ListView) findViewById(R.id.lstPregledKolegija);
        listView.setAdapter(pregledKolegija);
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
