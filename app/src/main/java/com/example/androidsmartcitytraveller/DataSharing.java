package com.example.androidsmartcitytraveller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DataSharing {

    SharedPreferences sharedPreferences;
    Context mcontext;

    DataSharing(Context mcontext){
        this.mcontext=mcontext;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(mcontext);
    }


    public void add(String key, String value){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        }

     public String retrieve(String key)
     {
         String response=sharedPreferences.getString(key,"No response detected");
         return response;
     }

}
