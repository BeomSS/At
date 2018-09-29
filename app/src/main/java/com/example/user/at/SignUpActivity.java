package com.example.user.at;

import android.app.Activity;
import android.os.Bundle;

public class SignUpActivity extends Activity {
    Skin skin;
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.activity_signup);
    }
}
