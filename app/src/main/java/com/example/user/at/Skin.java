package com.example.user.at;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Skin extends Activity {
    public final String preference = "com.example.user.at.preference";
    public final String key = "skinStyle";
    public Context context;
    public int skinCode;

    Skin(Context context){
        this.context = context;
    }

    public int skinSetting(){
        skinCode = getPreferenceInt(key);
        int color = getResources().getColor(R.color.colorMint);
        switch(skinCode){
            case 1:
                context.setTheme(R.style.AppThemeVer1);
                color = getResources().getColor(R.color.colorMint);
                break;
            case 2:
                context.setTheme(R.style.AppThemeVer2);
                color = getResources().getColor(R.color.colorBlue);
                break;
            case 3:
                context.setTheme(R.style.AppThemeVer3);
                color = getResources().getColor(R.color.colorBlack);
                break;
        }
        return color;
    }

    @SuppressLint("ApplySharedPref")
    public void setPreference(String key, int value) {
        SharedPreferences pref = getSharedPreferences(preference, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getPreferenceInt(String key) {
        SharedPreferences pref = getSharedPreferences(preference, MODE_PRIVATE);
        return pref.getInt(key, 1);
    }

}
