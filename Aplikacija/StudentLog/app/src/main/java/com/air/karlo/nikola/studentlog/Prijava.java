package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Prijava extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prijava);
        addListenerToButton();
    }

    public void addListenerToButton() {
        final Context context = getApplicationContext();
        Button registracija = (Button) findViewById(R.id.registration_button);

        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Registracija.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
