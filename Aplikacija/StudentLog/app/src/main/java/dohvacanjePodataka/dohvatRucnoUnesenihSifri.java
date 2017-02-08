package dohvacanjePodataka;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.air.karlo.nikola.studentlog.DohvacanjeKodaInterface;
import com.air.karlo.nikola.studentlog.DohvacanjeKodaListener;
import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import tipoviPodatka.Kod;

import static com.air.karlo.nikola.studentlog.R.id.btnPrijaviDolazak;

public class dohvatRucnoUnesenihSifri implements DohvacanjeKodaInterface {

    String odgovor;
    @Override
    public void dohvacanjeKoda(final DohvacanjeKodaListener listener, Activity activity, Context c) {

        odgovor = new String();
        AlertDialog.Builder alert = new AlertDialog.Builder(c);   //stvori dialog za unos
        final EditText edittext = new EditText(c);
        alert.setMessage("Unesite kod u prostor ispod.");
        alert.setTitle("Prijava dolaska putem koda.");
        alert.setView(edittext);
        alert.setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                odgovor = edittext.getText().toString();
                listener.DohvaceniKod(odgovor);
            }
        });
        alert.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                odgovor = "";
                odgovor = edittext.getText().toString();
            }
        });
        alert.show();

    }

}
