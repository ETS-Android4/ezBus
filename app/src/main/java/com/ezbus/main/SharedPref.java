package com.ezbus.main;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    SharedPreferences MySharedPref;


    public SharedPref (Context context) {
        MySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }

    //salvataggio tema
    public void setNightModeState(Boolean state) {
        SharedPreferences.Editor editor =  MySharedPref.edit();
        editor.putBoolean("NightMode", state);
        editor.commit();
    }

    //caricamento stato tema notturno
    public Boolean loadNightModeState() {
        Boolean state = MySharedPref.getBoolean("NightMode", false);
        return state;
    }

    public void setClient(Boolean state) {
        SharedPreferences.Editor editor =  MySharedPref.edit();
        editor.putBoolean("Client", state);
        editor.commit();
    }

    public Boolean isClient() {
        Boolean state = MySharedPref.getBoolean("Client", false);
        return state;
    }

    public String getQuery() {
        if (isClient()) return "clients";
        else return "companies";
    }

}