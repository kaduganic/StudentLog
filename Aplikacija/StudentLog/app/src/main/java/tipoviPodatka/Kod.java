package tipoviPodatka;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.sql.Date;

//Klasa definiranja tipa podataka za kod dolaska, profesor koji je kreirao kod, kolegij, sifra i qr kod ukoliko postoji i datum
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
