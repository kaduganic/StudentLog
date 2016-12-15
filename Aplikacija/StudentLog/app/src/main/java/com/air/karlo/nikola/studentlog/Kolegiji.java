package com.air.karlo.nikola.studentlog;

/**
 * Created by Nikola on 1.12.2016..
 */

public class Kolegiji {

    public Kolegiji(){}

    int id;
    String naziv;
    int ects;
    int idNositelj;

    public Kolegiji(int i, String n, int e, int idNos) {
        id = i;
        naziv = n;
        ects = e;
        idNositelj = idNos;
    }
}
