package tipoviPodatka;

import java.util.Date;

//Klasa definiranja tipa podataka za dolaske, definira se id kolegija, id studenta i datum dolaska
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
