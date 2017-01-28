package tipoviPodatka;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.sql.Date;

/**
 * Created by Nikola on 27.1.2017..
 */

public class Kod {

    public int  idProfesora;
    public int idKolegija;
    public String sifraDolaska;
    public Bitmap qrImage;
    public String datum;

    public Kod() {}

    public Kod(int idProfesora, int idKolegija, String sifraDolaska, Bitmap qrImage, String datum) {
        this.idProfesora = idProfesora;
        this.idKolegija = idKolegija;
        this.sifraDolaska = sifraDolaska;
        this.qrImage = qrImage;
        this.datum = datum;
    }
}
