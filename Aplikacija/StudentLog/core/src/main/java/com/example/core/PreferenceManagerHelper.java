package com.example.core;

import android.content.Context;
import java.util.ArrayList;

public class PreferenceManagerHelper {
    public static void spremiOsoba(int oib,String ime, String prezime, String korime, String lozinka, String uloga, Context context)
    {
        android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt("oib", oib)
                .putString("ime", ime)
                .putString("prezime", prezime)
                .putString("korime", korime)
                .putString("lozinka", lozinka)
                .putString("uloga", uloga)
                .commit();
    }

    public static String getLozinka(Context context)
    {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString("lozinka", "");
    }
    public static int getOib(Context context)
    {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getInt("oib", 1);
    }
    public static String getIme(Context context)
    {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString("ime", "");
    }
    public static String getPrezime(Context context)
    {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString("prezime", "");
    }
    public static String getKorime(Context context)
    {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString("korime", "");
    }
    public static String getUloga(Context context)
    {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString("uloga", "");
    }

}
