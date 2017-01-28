package tipoviPodatka;

import java.util.Date;

/**
 * Created by Nikola on 28.1.2017..
 */

public class Dolasci {

    public Dolasci() {}

    public int idKolegija;
    public int idStudenta;
    public String datum;

    public Dolasci(int idKolegija, int idStudenta, String datum) {
        this.idKolegija = idKolegija;
        this.idStudenta = idStudenta;
        this.datum = datum;
    }
}
