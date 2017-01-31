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
        setContentView(R.layout.kreiranje_koda); //spoji javu sa layoutom

        osoba = new Osoba();                    //preuzimanje podataka o trenutno ulogiranoj osobi
        osoba = getIntent().getExtras().getParcelable("osoba"); //preuzimanje podataka o trenutno ulogiranoj osobi
        TextView osobaImePrezime = (TextView) findViewById(R.id.txtImePrezimeKreiranjeKoda);
        osobaImePrezime.setText(osoba.ime  + " " + osoba.prezime); //ispis imena trenutno ulogirane osobe

        //spoji varijable sa view-ima
        unosQR = (EditText)findViewById(R.id.edTxtUnosKreiranjaKoda);
        generateQR = (Button)findViewById(R.id.btnGenerirajQRcode);
        spremiQR = (Button) findViewById(R.id.btnSpremiQR);
        imgQR = (ImageView) findViewById(R.id.imgQRcode);
        datum = (DatePicker) findViewById(R.id.dtDatumKreirajKod);

        //preuzimanje i prikaz kolegija od trenutno ulogiranog profesora
        Gson gson = new Gson();     //gson za dohvacanje iz preferencesa
        Type typeKol = new TypeToken<List<Kolegiji>>(){}.getType();       //odredivanje tipa podatka koji ce se dohvatiti
        final List<Kolegiji> listaSvihKolegija = gson.fromJson(PreferenceManagerHelper.getKolegije(context), typeKol); //dohvat
        List<String> listaTrenutnog = new ArrayList<>();
        if(listaSvihKolegija != null) { //ukoliko postoji neki zapis u prefrences
            for (Kolegiji kol : listaSvihKolegija) {    //prodi kroz sve zapise
                if (kol.idNositelj == osoba.oib)        //ukoliko je nositelj kolegija ujedno i ulogirana osoba
                    listaTrenutnog.add(kol.naziv);      //dodaj kolegij
            }
        }               //napuni spinner podacima
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTrenutnog);    //adapter za napunit spiner kolegijima
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //spinner
        final Spinner sItems = (Spinner) findViewById(R.id.spnrPopisKolegijaKreiranjeKoda); //povezi sa spinnerom na layout
        sItems.setAdapter(adapter); //napuni spinner kolegijima od trenutno ulogiranog profesora
        //prikaz kolegija od trenutnog profesora


        generateQR.setOnClickListener(new View.OnClickListener() {  //listener za generiranje QR koda
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    if(!unosQR.getText().toString().matches("")){    //ukoliko je sifra unesena
                        BitMatrix bitMatrix = multiFormatWriter.encode(unosQR.getText().toString(), BarcodeFormat.QR_CODE,200,200); //kreiranje bitMatrix
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();   //Barcode koji ce omogucit kreiranje QR
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix); //kreiraj QR
                        imgQR.setImageBitmap(bitmap);       //prikazi QR na ekranu
                    }else Toast.makeText(context, "Unesite sifru kako bi se generirao QR!", Toast.LENGTH_SHORT).show();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        spremiQR.setOnClickListener(new View.OnClickListener() {    //spremi promjene u preferences
            boolean statusUnosa = false;
            Gson gson = new Gson(); //gson za spremanje
            Type type = new TypeToken<List<Kod>>(){}.getType(); //tip podatka koji cemo spremiti
            List<Kod> listaStarihKodova = gson.fromJson(PreferenceManagerHelper.getGeneriraniKod(context), type); //dphvati generirani kod
            List<Kod> listaNovihKodova = new ArrayList<Kod>();
            @Override
            public void onClick(View v) {
                if (sItems.getSelectedItem() != null) {       //povjera izabrani kolegija na dropdown
                    for (Kolegiji kol : listaSvihKolegija) {    //prolaz kroz sve kolegije
                        if (kol.naziv.equals(sItems.getSelectedItem().toString())) { //ukoliko naziv kolegija iz liste odgovara onom izabranom na ekranu
                            kodDolaska.idKolegija = kol.id; //preuzmi id izabranog kolegija
                            statusUnosa = true;
                        }
                    }
                }

                if (!unosQR.getText().toString().matches("")) {          //provjera je li sifra unesena
                    kodDolaska.sifraDolaska = unosQR.getText().toString();  //preuzmi sifru dolaska
                    int   day  = datum.getDayOfMonth();     //datum
                    int   month= datum.getMonth();
                    int   year = datum.getYear();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");  //format datuma
                    kodDolaska.datum = sdf.format(new Date(year, month, day));  //spremi datum
                } else {
                    Toast.makeText(context, "Morate unesti sifru dolaska!", Toast.LENGTH_SHORT).show();
                    statusUnosa = false;
                }

                if (imgQR.getDrawable() != null) {    //provjera da li je generirana slika
                    kodDolaska.qrImage = ((BitmapDrawable) imgQR.getDrawable()).getBitmap(); //ukoliko postoji slika spremi ju
                }

                String jsonKodovi;
                if(listaStarihKodova != null){          //provjera postoje li stare sifre
                    for(Kod k : listaStarihKodova){     //prolaz kroz sve stare sifre
                        listaNovihKodova.add(k);
                    }
                    if (statusUnosa && imgQR.getDrawable() != null) {   //ukoliko je sve uneseno
                        listaNovihKodova.add(kodDolaska);               //dodaj sifru
                        jsonKodovi = gson.toJson(listaNovihKodova);     //kreiraj gson za spremanje
                        PreferenceManagerHelper.spremiGeneriraniKod(jsonKodovi, context);   //spremi u preferences
                        Toast.makeText(context, "Sifra i QR dolaska spremljeni su u bazu!", Toast.LENGTH_SHORT).show();
                    } else if (statusUnosa) {
                        listaNovihKodova.add(kodDolaska);   //dodaj kod bez QR
                        jsonKodovi = gson.toJson(listaNovihKodova); //pretvori listu u gson
                        PreferenceManagerHelper.spremiGeneriraniKod(jsonKodovi, context);   //spremi u prefereces
                        Toast.makeText(context, "Sifra dolaska spremljena je u bazu!", Toast.LENGTH_SHORT).show();
                    }
                }else { //ukoliko je ovo prvi zapis
                    if (statusUnosa && imgQR.getDrawable() != null) {   //ukoliko je sve uneseno
                        listaNovihKodova.add(kodDolaska);               //dodaj sifru
                        jsonKodovi = gson.toJson(listaNovihKodova);     //kreiraj gson za spremanje
                        PreferenceManagerHelper.spremiGeneriraniKod(jsonKodovi, context);  //spremi u preferences
                        Toast.makeText(context, "Sifra i QR dolaska spremljeni su u bazu!", Toast.LENGTH_SHORT).show();
                    } else if (statusUnosa) {
                        listaNovihKodova.add(kodDolaska);                      //dodaj kod bez QR
                        jsonKodovi = gson.toJson(listaNovihKodova);                  //pretvori listu u gson
                        PreferenceManagerHelper.spremiGeneriraniKod(jsonKodovi, context);  //spremi u prefereces
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
