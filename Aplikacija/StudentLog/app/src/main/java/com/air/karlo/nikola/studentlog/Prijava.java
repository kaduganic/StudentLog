package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.RippleDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import webservice.StudentLogApi;


public class Prijava extends AppCompatActivity {

    TextView korisnickoIme,lozinka;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prijava);
        addListenerToButton();
        korisnickoIme = (TextView) findViewById(R.id.txtKorIme);
        lozinka = (TextView)findViewById(R.id.txtLozinka);
    }

    public void addListenerToButton() {
        final Context context = getApplicationContext();
        Button registracija = (Button) findViewById(R.id.registration_button);
        Button prijava = (Button) findViewById(R.id.btnPrijava);
        final Osoba osob = new Osoba();
        final ArrayList<Osoba> osobe = new ArrayList<>();

        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Registracija.class);
                startActivity(intent);
                finish();
            }
        });
        prijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Meni.class);
                Gson gson = new Gson();
                Type type = new TypeToken<List<Osoba>>(){}.getType();
                List<Osoba> osobe = gson.fromJson(PreferenceManagerHelper.getOsobe(context), type);

                final String kIme = korisnickoIme.getText().toString();
                final String loz = lozinka.getText().toString();
                boolean statusPronadenog = true;
                if(osobe != null){
                    for (Osoba os:osobe) {
                        if(os.korime.equals(kIme)  && os.lozinka.equals(loz)){
                            intent.putExtra("osoba", os);
                            startActivity(intent);
                            finish();
                            statusPronadenog = false;
                            break;
                        }else statusPronadenog = true;
                    }
                    if(statusPronadenog) Toast.makeText(context, "Korisnik ne postoji!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
