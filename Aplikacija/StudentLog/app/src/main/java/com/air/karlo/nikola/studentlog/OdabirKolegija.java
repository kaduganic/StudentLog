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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.air.karlo.nikola.studentlog.R.styleable.View;

/**
 * Created by Nikola on 25.1.2017..
 */

public class OdabirKolegija extends AppCompatActivity {


    Osoba osoba;
    TextView txtImePrezime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        setContentView(R.layout.odabir_kolegija);
        osoba = new Osoba();
        osoba = getIntent().getExtras().getParcelable("osoba");
        txtImePrezime = (TextView) findViewById(R.id.txtImePrezimeOdabirKolegija);
        txtImePrezime.setText(osoba.ime  + " " + osoba.prezime);

         final Gson gson1 = new Gson();
         final Type type = new TypeToken<List<Kolegiji>>(){}.getType();
         final List<Kolegiji> listaSvihKolegija = gson1.fromJson(PreferenceManagerHelper.getKolegije(context), type);

         if(listaSvihKolegija != null){
            CustomAdapterOdabirKolegija odabirKolegija = new CustomAdapterOdabirKolegija(this, listaSvihKolegija);
            ListView listView = (ListView) findViewById(R.id.lstOdabirKolegija);
            listView.setAdapter(odabirKolegija);
            listView.setTextFilterEnabled(true);


            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                List<StudentImaKolegij> studImaKolList = new ArrayList<StudentImaKolegij>();
                StudentImaKolegij stImaKol = new StudentImaKolegij();
                boolean status = false;
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Gson gson2 = new Gson();
                    List<StudentImaKolegij> studentImaKolegijList = gson2.fromJson(PreferenceManagerHelper.getStudentImaKoleg(context), type);
                    for (Kolegiji kol : listaSvihKolegija) {
                        if (kol.id == id) {
                            if(studentImaKolegijList != null) {
                                for (StudentImaKolegij stIK:studentImaKolegijList) {
                                    if(osoba.oib == stIK.idStudent){
                                        if(stIK.idKolegij == kol.id){
                                            Toast.makeText(getApplicationContext(), "Kolegij " + kol.naziv + " je vec odabran!",
                                                    Toast.LENGTH_SHORT).show();
                                            break;
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Odabrali ste kolegij " + kol.naziv + ".",
                                                    Toast.LENGTH_SHORT).show();
                                            stImaKol.idStudent = osoba.oib;
                                            stImaKol.idKolegij = kol.id;
                                            status = true;
                                            break;
                                        }
                                    }
                                }
                            }else{
                                stImaKol.idStudent = osoba.oib;
                                stImaKol.idKolegij = kol.id;
                                studImaKolList.add(stImaKol);
                                String jsonStImaKol = gson2.toJson(studImaKolList);
                                PreferenceManagerHelper.spremiStudentImaKoleg(jsonStImaKol,context);
                            }
                        }
                    }
                    if(status == true) {
                        studImaKolList.add(stImaKol);
                        String jsonStImaKol = gson2.toJson(studImaKolList);
                        PreferenceManagerHelper.spremiStudentImaKoleg(jsonStImaKol,context);
                    }
                    return true;
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
