package dohvacanjePodataka;

import android.content.Context;

import com.air.karlo.nikola.studentlog.DohvacanjeKodaInterface;
import com.air.karlo.nikola.studentlog.DohvacanjeKodaListener;
import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tipoviPodatka.Kod;

/**
 * Created by Nikola on 28.1.2017..
 */

public class dohvacanjeQRSifri implements DohvacanjeKodaInterface {
    @Override
    public void dohvacanjeKoda(DohvacanjeKodaListener listener, Context c) {

        Gson gson = new Gson();
        Type type = new TypeToken<List<Kod>>(){}.getType();
        List<Kod> kodovi = gson.fromJson(PreferenceManagerHelper.getGeneriraniKod(c), type);
        List<Kod> qrUneseniKodovi = new ArrayList<>();

        for (Kod k: kodovi) {
            if(k.qrImage != null){
                qrUneseniKodovi.add(k);
            }
        }
        listener.DohvaceniKod(qrUneseniKodovi);
    }
}
