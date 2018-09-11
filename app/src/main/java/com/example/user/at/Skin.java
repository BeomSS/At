package com.example.user.at;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Skin {
    public final String preference = "com.example.user.at.preference";
    public final String key = "skinStyle";
    public Context context;
    public int skinCode;

    Skin(Context context){
        this.context = context;
    }

    public int skinSetting(){
        skinCode = getPreferenceInt(key);
        int color = 0;
        switch(skinCode){
            case 1:
                context.setTheme(R.style.AppThemeVer1);
                color = getColor(color, R.color.colorMint);
                break;
            case 2:
                context.setTheme(R.style.AppThemeVer2);
                color = getColor(color, R.color.colorBlue);
                break;
            case 3:
                context.setTheme(R.style.AppThemeVer3);
                color = getColor(color, R.color.colorDark);
                break;
        }
        return color;
    }

    public int colorSetting(){
        skinCode = getPreferenceInt(key);
        int color = 0;
        switch(skinCode){
            case 1:
                color = getColor(color, R.color.colorMint);
                break;
            case 2:
                color = getColor(color, R.color.colorBlue);
                break;
            case 3:
                color = getColor(color, R.color.colorDark);
                break;
        }
        return color;
    }

    public int getColor(int color, int colorID){
        color = context.getResources().getColor(colorID);
        return color;
    }

    @SuppressLint("ApplySharedPref")
    public void setPreference(String key, int value) {
        SharedPreferences pref = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getPreferenceInt(String key) {
        SharedPreferences pref = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return pref.getInt(key, 1);
    }

    public int getSkinCode(){
        return skinCode;
    }

}
