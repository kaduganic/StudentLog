package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tipoviPodatka.Kolegiji;
import tipoviPodatka.Osoba;
import tipoviPodatka.StudentImaKolegij;

import static android.graphics.Color.LTGRAY;

public class OdabirKolegija extends AppCompatActivity {


    Osoba osoba;
    TextView txtImePrezime;
    Button odaberiKolegije, ucitajMojePodatke;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        setContentView(R.layout.odabir_kolegija); //povezivanje sa layout

        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba"); //dohvacanje podataka o ulogiranoj osobi

        txtImePrezime = (TextView) findViewById(R.id.txtImePrezimeOdabirKolegija);
        txtImePrezime.setText(osoba.ime + " " + osoba.prezime); //ispis imena i prezimena ulogirane osobe

        final ListView listView = (ListView) findViewById(R.id.lstOdabirKolegija);
        odaberiKolegije = (Button) findViewById(R.id.btnOdaberiKolegije);
        ucitajMojePodatke = (Button) findViewById(R.id.btnMojiPodaciOdaberiKolegije) ;

        Gson gson1 = new Gson(); //gson za komunikaciju sa preferences
        Type type1 = new TypeToken<List<Kolegiji>>() {}.getType();  //tip podatka koji se dohvaca
        final List<Kolegiji> listaSvihKolegija = gson1.fromJson(PreferenceManagerHelper.getKolegije(context), type1); //dphvaceni kolegiji iz preferences

        if (listaSvihKolegija != null) { //ukoliko postoje zapisi
            CustomAdapterOdabirKolegija odabirKolegija = new CustomAdapterOdabirKolegija(this, listaSvihKolegija); //custom adapter za prikaz kolegija
            listView.setAdapter(odabirKolegija); //povezi adapter sa list view
            listView.setTextFilterEnabled(true);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //klik na clana liste
                    for (Kolegiji kol : listaSvihKolegija) {    //za svaki kolegij
                        if (kol.id == position)
                            if (listView.getChildAt(position).isEnabled()) {    //ako je list item dostupan onda
                                listView.getChildAt(position).setEnabled(false);    //onemoguci ga
                                listView.getChildAt(position).setBackgroundColor(LTGRAY);   //posjencaj ga
                                Toast.makeText(context, "Odabrali ste " + kol.naziv + ".", Toast.LENGTH_SHORT).show(); //ispis odabranog
                            } else if (!listView.getChildAt(position).isEnabled()) { //ako nije dostupan
                                listView.getChildAt(position).setEnabled(true);       //omoguci ga
                                listView.getChildAt(position).setBackgroundColor(0);    //ukloni boju
                                Toast.makeText(context, "Uklonili ste " + kol.naziv + ".", Toast.LENGTH_SHORT).show();
                            }
                    }
                }
            });



            odaberiKolegije.setOnClickListener(new View.OnClickListener() {     //button odaberi kolegij
                Gson gson2 = new Gson();    //gson za dohvat iz preferences
                Type type2 = new TypeToken<List<StudentImaKolegij>>() {}.getType();     //tip podatka za dohvat iz preferences
                List<StudentImaKolegij> listaStaraStImaKole = gson2.fromJson(PreferenceManagerHelper.getStudentImaKoleg(context), type2);
                List<StudentImaKolegij> studImaKolList = new ArrayList<>();
                StudentImaKolegij stImaKol;
                @Override
                public void onClick(View v) {
                    if(listaStaraStImaKole != null)
                    for (StudentImaKolegij stIK:listaStaraStImaKole) {  //kroz sve studente koji imaju kolegij
                        studImaKolList.add(stIK);
                    }
                    for (int i = 0; i < listaSvihKolegija.size(); i++) {    //prodi kroz sve kolegije
                        stImaKol = new StudentImaKolegij();
                        if (!listView.getChildAt(i).isEnabled()) {           //ukoliko je kolegij onemogucen (odabran)
                            stImaKol.idStudent = osoba.oib;         //preuzmi id ulogirane osobe
                            stImaKol.idKolegij = i;                 //preuzmi id kolegija
                            studImaKolList.add(stImaKol);           //dodaj kolegij
                            Gson gson = new Gson();                 //gson za spremanje
                            String jsonStImKo = gson.toJson(studImaKolList);       //kreiraj gson i spremi gson u bazu
                            PreferenceManagerHelper.spremiStudentImaKolegij(jsonStImKo, context);

                        }
                    }
                    Toast.makeText(context, "Kolegiji su odabrani!", Toast.LENGTH_SHORT).show();
                }
            });


            ucitajMojePodatke.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {   //ucitaj podatke studenta
                    Gson gson3 = new Gson();                //gson za dohvat podataka
                    Type type3 = new TypeToken<List<StudentImaKolegij>>() {}.getType();     //tip podataka za dohvat
                    List<StudentImaKolegij> listaStudImaKol = gson3.fromJson(PreferenceManagerHelper.getStudentImaKoleg(context), type3);   //dohvat iz baze
                    for (Kolegiji kol : listaSvihKolegija) {        //za sve kolegije
                        listView.getChildAt(kol.id).setEnabled(true);       //omoguci sve kolegije na listi da su dostupni za odabir
                        listView.getChildAt(kol.id).setBackgroundColor(0);  //ukloni boju
                        if (listaStudImaKol != null) {                      //ukoliko ima odabranih u preferences
                            for (StudentImaKolegij stImK : listaStudImaKol) {   //za svaki spremljeni u bazi oznaci ga na ekranu
                                if (kol.id == stImK.idKolegij && stImK.idStudent == osoba.oib) { //ukoliko id osobe i id kolegija odgovara
                                    listView.getChildAt(kol.id).setEnabled(false);              //onemoguci ga i stavi boju sivu
                                    listView.getChildAt(kol.id).setBackgroundColor(LTGRAY);
                                }
                            }
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
