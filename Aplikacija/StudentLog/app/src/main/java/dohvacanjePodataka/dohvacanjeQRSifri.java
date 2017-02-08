package dohvacanjePodataka;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.air.karlo.nikola.studentlog.DohvacanjeKodaInterface;
import com.air.karlo.nikola.studentlog.DohvacanjeKodaListener;
import com.air.karlo.nikola.studentlog.PrijavaDolaska;
import com.example.core.PreferenceManagerHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tipoviPodatka.Kod;

public class dohvacanjeQRSifri extends AppCompatActivity implements DohvacanjeKodaInterface {

    DohvacanjeKodaListener l = null;

    @Override
    public void dohvacanjeKoda(DohvacanjeKodaListener listener, Activity activity, Context c) {
        this.l = listener;
        
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Skeniraj");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "Prekinuli ste skeniranje.", Toast.LENGTH_SHORT).show();
            }
            else {
                l.DohvaceniKod(result.getContents().toString());
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
