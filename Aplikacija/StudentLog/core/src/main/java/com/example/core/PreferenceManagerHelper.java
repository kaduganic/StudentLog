package com.example.core;

import android.content.Context;
import java.util.ArrayList;

public class PreferenceManagerHelper {

    public static void spremiOsoba(String osobe, Context context)
    {
        android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString("osobe", osobe)
                .commit();
    }

    public static void spremiKolegij(String kolegiji, Context context){
        android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString("kolegiji", kolegiji)
                .commit();
    }


    public static String getOsobe(Context context){
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString("osobe","");
    }

    public static String getKolegije(Context context){
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString("kolegiji", "");
    }


}
