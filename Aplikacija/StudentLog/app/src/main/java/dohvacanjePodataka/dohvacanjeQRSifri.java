package dohvacanjePodataka;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.air.karlo.nikola.studentlog.DohvacanjeKodaInterface;
import com.air.karlo.nikola.studentlog.DohvacanjeKodaListener;
import com.air.karlo.nikola.studentlog.PregledDolazaka;
import com.air.karlo.nikola.studentlog.PrijavaDolaska;
import com.air.karlo.nikola.studentlog.citanjeKoda;
import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import tipoviPodatka.Kod;

public class dohvacanjeQRSifri implements DohvacanjeKodaInterface {

    @Override
    public void dohvacanjeKoda(DohvacanjeKodaListener listener, Activity activity, Context c) {
        this.listener = listener;

        Intent intent = new Intent(c, citanjeKoda.class);
        activity.startActivity(intent);
    }

    public static DohvacanjeKodaListener listener;
}
