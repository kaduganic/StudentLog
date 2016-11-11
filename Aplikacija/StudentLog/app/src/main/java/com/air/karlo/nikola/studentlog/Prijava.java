package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.RippleDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.PreferenceManagerHelper;

import org.w3c.dom.Text;


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

        final String korIme = PreferenceManagerHelper.getKorime(context);
        final String sharedLozinka = PreferenceManagerHelper.getLozinka(context);

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
                final String kIme = korisnickoIme.getText().toString();
                final String loz = lozinka.getText().toString();

                if(korIme.equals(kIme)  && sharedLozinka.equals(loz)){
                    Toast.makeText(context, "Korisnik postoji!",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Korisnik ne postoji!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
