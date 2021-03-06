package com.isii.systock.Herramientas;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class PreferenciasManager {
	
	

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // Modo
    int PRIVATE_MODE = 0;
    // Nombre de la preferenciS


    public PreferenciasManager(Context context) {

        this._context = context;
        pref = _context.getSharedPreferences(Utils.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getValueString(String tag) {
        return pref.getString(tag, "");
    }

    public int getValueInt(String tag) {
        return pref.getInt(tag, 0);
    }

    public void setValue(String tag, String value) {
        editor.putString(tag, value);
        editor.commit();
    }

    public void setValue(String tag, int value) {
        editor.putInt(tag, value);
        editor.commit();
    }

    public boolean getValue(String tag) {
        return pref.getBoolean(tag, true);
    }

    public void setValue(String tag, boolean value) {
        editor.putBoolean(tag, value);
        editor.commit();
    }

}