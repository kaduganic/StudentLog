package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import tipoviPodatka.Kod;
import tipoviPodatka.Kolegiji;
import tipoviPodatka.Osoba;

/**
 * Created by Nikola on 27.1.2017..
 */

public class KreiranjeKoda extends AppCompatActivity{

    Osoba osoba;
    EditText unosQR;
    Button generateQR, spremiQR;
    ImageView imgQR;
    Kod kodDolaska = new Kod();
    DatePicker datum;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.kreiranje_koda);

        osoba = new Osoba();                    //preuzimanje podataka o trenutno ulogiranoj osobi
        osoba = getIntent().getExtras().getParcelable("osoba");
        TextView osobaImePrezime = (TextView) findViewById(R.id.txtImePrezimeKreiranjeKoda);
        osobaImePrezime.setText(osoba.ime  + " " + osoba.prezime);
        unosQR = (EditText)findViewById(R.id.edTxtUnosKreiranjaKoda);
        generateQR = (Button)findViewById(R.id.btnGenerirajQRcode);
        spremiQR = (Button) findViewById(R.id.btnSpremiQR);
        imgQR = (ImageView) findViewById(R.id.imgQRcode);
        datum = (DatePicker) findViewById(R.id.dtDatumKreirajKod);

        //preuzimanje i prikaz kolegija od trenutno ulogiranog profesora
        Gson gson = new Gson();
        Type typeKol = new TypeToken<List<Kolegiji>>(){}.getType();        //dohvacanje podataka iz lokalne baze
        final List<Kolegiji> listaSvihKolegija = gson.fromJson(PreferenceManagerHelper.getKolegije(context), typeKol);
        List<String> listaTrenutnog = new ArrayList<>();
        if(listaSvihKolegija != null) {
            for (Kolegiji kol : listaSvihKolegija) {
                if (kol.idNositelj == osoba.oib)
                    listaTrenutnog.add(kol.naziv);
            }
        }               //napuni spinner podacima
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTrenutnog);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems = (Spinner) findViewById(R.id.spnrPopisKolegijaKreiranjeKoda);
        sItems.setAdapter(adapter);
        //prikaz kolegija od trenutnog profesora


        generateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    if(!unosQR.getText().toString().matches("")){
                        BitMatrix bitMatrix = multiFormatWriter.encode(unosQR.getText().toString(), BarcodeFormat.QR_CODE,200,200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        imgQR.setImageBitmap(bitmap);
                    }else Toast.makeText(context, "Unesite sifru kako bi se generirao QR!", Toast.LENGTH_SHORT).show();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        spremiQR.setOnClickListener(new View.OnClickListener() {
            boolean statusUnosa = false;
            Gson gson = new Gson();
            Type type = new TypeToken<List<Kod>>(){}.getType();
            List<Kod> listaStarihKodova = gson.fromJson(PreferenceManagerHelper.getGeneriraniKod(context), type);
            List<Kod> listaNovihKodova = new ArrayList<Kod>();
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (sItems.getSelectedItem() != null) {       //povjera izabranog kolegija na dropdown
                    for (Kolegiji kol : listaSvihKolegija) {
                        if (kol.naziv.equals(sItems.getSelectedItem().toString())) {
                            kodDolaska.idKolegija = kol.id;
                            statusUnosa = true;
                        }
                    }
                }

                if (!unosQR.getText().toString().matches("")) {               //provjera unesene sifre
                    kodDolaska.sifraDolaska = unosQR.getText().toString();
                    int   day  = datum.getDayOfMonth();
                    int   month= datum.getMonth();
                    int   year = datum.getYear();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    kodDolaska.datum = sdf.format(new Date(year, month, day));
                } else {
                    Toast.makeText(context, "Morate unesti sifru dolaska!", Toast.LENGTH_SHORT).show();
                    statusUnosa = false;
                }

                if (imgQR.getDrawable() != null) {    //provjera da li je generirana slika
                    kodDolaska.qrImage = ((BitmapDrawable) imgQR.getDrawable()).getBitmap();
                }

                String jsonKodovi;
                if(listaStarihKodova != null){
                    for(Kod k : listaStarihKodova){
                        listaNovihKodova.add(k);
                    }
                    if (statusUnosa && imgQR.getDrawable() != null) {   //spremanje u bazu
                        listaNovihKodova.add(kodDolaska);
                        jsonKodovi = gson.toJson(listaNovihKodova);
                        PreferenceManagerHelper.spremiGeneriraniKod(jsonKodovi, context);
                        Toast.makeText(context, "Sifra i QR dolaska spremljeni su u bazu!", Toast.LENGTH_SHORT).show();
                    } else if (statusUnosa) {
                        listaNovihKodova.add(kodDolaska);
                        jsonKodovi = gson.toJson(listaNovihKodova);
                        PreferenceManagerHelper.spremiGeneriraniKod(jsonKodovi, context);
                        Toast.makeText(context, "Sifra dolaska spremljena je u bazu!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (statusUnosa && imgQR.getDrawable() != null) {   //spremanje u bazu
                        listaNovihKodova.add(kodDolaska);
                        jsonKodovi = gson.toJson(listaNovihKodova);
                        PreferenceManagerHelper.spremiGeneriraniKod(jsonKodovi, context);
                        Toast.makeText(context, "Sifra i QR dolaska spremljeni su u bazu!", Toast.LENGTH_SHORT).show();
                    } else if (statusUnosa) {
                        listaNovihKodova.add(kodDolaska);
                        jsonKodovi = gson.toJson(listaNovihKodova);
                        PreferenceManagerHelper.spremiGeneriraniKod(jsonKodovi, context);
                        Toast.makeText(context, "Sifra dolaska spremljena je u bazu!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
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
}
