package com.hhpn.tata.util;

/**
 * Created by hiantohendry on 8/7/15.
 */

import android.content.Context;
import android.content.SharedPreferences;


public class TataPreference {
    private static TataPreference 		sinPref;
    private static SharedPreferences sharedPreference;

    public static TataPreference getInstance(Context context){
        if(sinPref==null)
            sinPref 			= new TataPreference();
        sharedPreference 	= context.getSharedPreferences("TataPreference", Context.MODE_PRIVATE);
        return sinPref;
    }

    public String getString(String key, String defValue){
        return sharedPreference.getString(key, defValue);
    }

    public int getInt(String key, int defValue){
        return sharedPreference.getInt(key, defValue);
    }

    public void setString(String key, String value){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setInt(String key, int value){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setBoolean(String key, boolean value){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defValue){
        return sharedPreference.getBoolean(key, defValue);
    }
}
