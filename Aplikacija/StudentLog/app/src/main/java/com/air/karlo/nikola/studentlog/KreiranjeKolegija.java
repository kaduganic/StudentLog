package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Nikola on 1.12.2016..
 */

public class KreiranjeKolegija extends AppCompatActivity {

    Osoba osoba;
    TextView txtImePrezime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kreiranje_kolegija);
        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba");
        txtImePrezime = (TextView) findViewById(R.id.txtImePrezime);
        txtImePrezime.setText(osoba.ime  + " " + osoba.prezime);
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
