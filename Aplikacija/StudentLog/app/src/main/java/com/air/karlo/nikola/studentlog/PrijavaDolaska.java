package com.air.karlo.nikola.studentlog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.lang.reflect.Type;
import java.util.List;
import tipoviPodatka.Kod;
import tipoviPodatka.Osoba;

/**
 * Created by Nikola on 27.1.2017..
 */

public class PrijavaDolaska extends AppCompatActivity implements DohvacanjeKodaInterface{

    Osoba osoba;
    Button btnSkenirajQR, btnPrijaviDolazak, btnUnesiRucnoKod;
    TextView edTxtKodDolaska;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.prijava_dolaska);
        final Activity activity = this;
        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba");

        btnSkenirajQR = (Button)findViewById(R.id.btnSkenirajQRPrijavaDolaska);
        btnPrijaviDolazak = (Button)findViewById(R.id.btnPrijaviDolazak);
        btnUnesiRucnoKod = (Button)findViewById(R.id.btnUnesiRucno);
        edTxtKodDolaska = (TextView) findViewById(R.id.edtxtUnesiteSifru);
        TextView txtKorisnik = (TextView) findViewById(R.id.txtImePrezimePrijavaDolaska);
        txtKorisnik.setText(osoba.ime  + " " + osoba.prezime);

        Gson gson = new Gson();
        Type type = new TypeToken<List<Kod>>(){}.getType();
        List<Kod> listaSvihKodova = gson.fromJson(PreferenceManagerHelper.getGeneriraniKod(context), type);

        DohvacanjeKodaInterface dohvacanjeKodaDolaska;
        dohvacanjeKodaDolaska = new PrijavaDolaska();


        btnUnesiRucnoKod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                final EditText edittext = new EditText(context);
                alert.setMessage("Unesite kod u prostor ispod.");
                alert.setTitle("Prijava dolaska putem koda.");
                alert.setView(edittext);
                alert.setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        edTxtKodDolaska.setText(edittext.getText().toString());
                    }
                });
                alert.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {}
                });
                alert.show();
            }
        });

        btnSkenirajQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Skeniraj");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        btnPrijaviDolazak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edTxtKodDolaska.getText().toString().matches("")){
                    Toast.makeText(context, "Unesite kod dolaska!", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "Prekinuli ste skeniranje.", Toast.LENGTH_SHORT).show();
            }
            else {
                edTxtKodDolaska.setText(result.getContents());
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Context contex = getApplicationContext();
        Intent intent = new Intent(contex, Meni.class);
        intent.putExtra("osoba", osoba);
        startActivity(intent);
        finish();
    }

    @Override
    public void dohvacanjeKoda(DohvacanjeKodaListener listener, Context c) {
        listener.DohaceniKod(edTxtKodDolaska.getText().toString());
    }
}
