package webservice;

import tipoviPodatka.Kolegiji;
import tipoviPodatka.Osoba;

import java.util.List;

import retrofit2.Callback;
import retrofit2.http.POST;

public interface StudentLogApi {

    @POST("osoba.json")
    public void getOsoba(Callback<List<Osoba>> response);

    @POST("kolegij.json")
    public void getKolegiji(Callback<List<Kolegiji>> response);
}
