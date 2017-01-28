package com.example.core;

import android.content.Context;
import java.util.ArrayList;

public class PreferenceManagerHelper {

    public static void spremiOsoba(String osobe, Context context)
    {
        android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString("osobe", osobe)
                .apply();       //commit odmah sprema u bazu dok apply to odradi u pozadini
    }

    public static void spremiKolegij(String kolegiji, Context context){
        android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString("kolegiji", kolegiji)
                .apply();
    }

    public static void spremiStudentImaKolegij(String studImaK, Context context)
    {
        android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString("studImaK", studImaK)
                .apply();
    }

    public static void spremiGeneriraniKod (String generKod, Context context){
        android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString("generKod",generKod)
                .apply();
    }

    public static void spremiDolaske (String dolasci, Context context){
        android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString("dolasci",dolasci)
                .apply();
    }

    public static String getOsobe(Context context){
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString("osobe","");
    }

    public static String getKolegije(Context context){
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString("kolegiji", "");
    }

    public static String getStudentImaKoleg(Context context){
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString("studImaK", "");
    }

    public static String getGeneriraniKod(Context context){
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString("generKod","");
    }

    public static String getDolasci(Context context){
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString("dolasci","");
    }
}
