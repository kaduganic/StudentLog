package com.air.karlo.nikola.studentlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.LTGRAY;
import static com.air.karlo.nikola.studentlog.R.styleable.View;

/**
 * Created by Nikola on 25.1.2017..
 */

public class OdabirKolegija extends AppCompatActivity {


    Osoba osoba;
    TextView txtImePrezime;
    Button odaberiKolegije, ucitajMojePodatke;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        setContentView(R.layout.odabir_kolegija);
        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba");
        txtImePrezime = (TextView) findViewById(R.id.txtImePrezimeOdabirKolegija);
        txtImePrezime.setText(osoba.ime + " " + osoba.prezime);
        final ListView listView = (ListView) findViewById(R.id.lstOdabirKolegija);
        odaberiKolegije = (Button) findViewById(R.id.btnOdaberiKolegije);
        ucitajMojePodatke = (Button) findViewById(R.id.btnMojiPodaciOdaberiKolegije) ;

        Gson gson1 = new Gson();
        Type type1 = new TypeToken<List<Kolegiji>>() {}.getType();
        Type type2 = new TypeToken<List<StudentImaKolegij>>() {}.getType();
        final List<Kolegiji> listaSvihKolegija = gson1.fromJson(PreferenceManagerHelper.getKolegije(context), type1);
        List<StudentImaKolegij> listaStudImaKol = gson1.fromJson(PreferenceManagerHelper.getStudentImaKoleg(context), type2);

        if (listaSvihKolegija != null) {
            CustomAdapterOdabirKolegija odabirKolegija = new CustomAdapterOdabirKolegija(this, listaSvihKolegija);
            listView.setAdapter(odabirKolegija);
            listView.setTextFilterEnabled(true);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    for (Kolegiji kol : listaSvihKolegija) {
                        if (kol.id == position)
                            if (listView.getChildAt(position).isEnabled()) {
                                listView.getChildAt(position).setEnabled(false);
                                listView.getChildAt(position).setBackgroundColor(LTGRAY);
                                Toast.makeText(context, "Odabrali ste " + kol.naziv + ".", Toast.LENGTH_SHORT).show();
                            } else if (!listView.getChildAt(position).isEnabled()) {
                                listView.getChildAt(position).setEnabled(true);
                                listView.getChildAt(position).setBackgroundColor(0);
                                Toast.makeText(context, "Uklonili ste " + kol.naziv + ".", Toast.LENGTH_SHORT).show();
                            }
                    }
                }
            });



            odaberiKolegije.setOnClickListener(new View.OnClickListener() {
                List<StudentImaKolegij> studImaKolList = new ArrayList<>();
                StudentImaKolegij stImaKol;

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < listaSvihKolegija.size(); i++) {
                        stImaKol = new StudentImaKolegij();
                        if (!listView.getChildAt(i).isEnabled()) {           //spremi sve odabrane kolegije sa liste
                            stImaKol.idStudent = osoba.oib;
                            stImaKol.idKolegij = i;
                            studImaKolList.add(stImaKol);

                            Gson gson = new Gson();
                            String jsonStImKo = gson.toJson(studImaKolList);       //kreiraj gson i spremi gson u bazu
                            PreferenceManagerHelper.spremiStudentImaKolegij(jsonStImKo, context);

                        }
                    }
                    Toast.makeText(context, "Kolegiji su odabrani!", Toast.LENGTH_SHORT).show();
                }
            });


            ucitajMojePodatke.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Gson gson3 = new Gson();
                    Type type3 = new TypeToken<List<StudentImaKolegij>>() {}.getType();
                    List<StudentImaKolegij> listaStudImaKol = gson3.fromJson(PreferenceManagerHelper.getStudentImaKoleg(context), type3);
                    for (Kolegiji kol : listaSvihKolegija) {
                        listView.getChildAt(kol.id).setEnabled(true);
                        listView.getChildAt(kol.id).setBackgroundColor(0);
                        if (listaStudImaKol != null) {
                            for (StudentImaKolegij stImK : listaStudImaKol) {
                                if (kol.id == stImK.idKolegij) {
                                    listView.getChildAt(kol.id).setEnabled(false);
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
