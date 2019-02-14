package com.ezbus.main;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private SharedPreferences MySharedPref;


    public SharedPref (Context context) {
        MySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }

    //salvataggio tema
    void setNightModeState(Boolean state) {
        SharedPreferences.Editor editor =  MySharedPref.edit();
        editor.putBoolean("NightMode", state);
        editor.apply();
    }

    //caricamento stato tema notturno
    public Boolean loadNightModeState() {
        return MySharedPref.getBoolean("NightMode", false);
    }

    public void setClient(Boolean state) {
        SharedPreferences.Editor editor =  MySharedPref.edit();
        editor.putBoolean("Client", state);
        editor.apply();
    }

    public Boolean isClient() {
        return MySharedPref.getBoolean("Client", false);
    }

    public String getQuery() {
        if (isClient()) return "clients";
        else return "companies";
    }

}