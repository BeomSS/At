package com.example.user.at;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Objects;

public class SplashActivity extends Activity {
    Skin skin;
    private final int SPLASH_DISPLAY_LENGTH = 2000; //2초간 스플래쉬 실행
    public final String preference = "com.example.user.at.preference";
    public final String key = "neverShow";
    private Boolean isNeverShow;
    ShowHelpDialog dlgStartHelp;
    CustomDialog dlg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        int color = skin.skinSetting();
        setContentView(R.layout.activity_splash);
        // SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (!getPreferenceBoolean(key)) {
                    dlgStartHelp = new ShowHelpDialog(SplashActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dlgStartHelp.dismiss();
                            isNeverShow = true;
                            setPreference(key, isNeverShow);
                            dlg = new CustomDialog(SplashActivity.this, "이용안내", "이용안내는 메뉴에서 언제든 다시 볼 수 있습니다.",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dlg.dismiss();
                                            goNext();
                                        }
                                    });
                            dlg.show();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dlgStartHelp.dismiss();
                            isNeverShow = false;
                            goNext();
                        }
                    });
                    dlgStartHelp.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            goNext();
                        }
                    });
                    dlgStartHelp.show();
                } else {
                    // 메뉴액티비티를 실행하고 로딩화면을 끝냄
                    goNext();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    void goNext(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center_translate, R.anim.stop_translate);
        finish();
    }

    @SuppressLint("ApplySharedPref")
    public void setPreference(String key, boolean value) {
        SharedPreferences pref = getSharedPreferences(preference, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getPreferenceBoolean(String key) {
        SharedPreferences pref = getSharedPreferences(preference, MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }
}
