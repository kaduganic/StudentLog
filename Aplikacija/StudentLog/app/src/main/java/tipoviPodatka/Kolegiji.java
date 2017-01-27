package tipoviPodatka;

/**
 * Created by Nikola on 1.12.2016..
 */

public class Kolegiji {

    public Kolegiji(){}

    public int id;
    public String naziv;
    public int ects;
    public int idNositelj;

    public Kolegiji(int i, String n, int e, int idNos) {
        id = i;
        naziv = n;
        ects = e;
        idNositelj = idNos;
    }
}
