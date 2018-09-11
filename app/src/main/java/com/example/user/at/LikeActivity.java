package com.example.user.at;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class LikeActivity extends Activity {
    Skin skin;
    int color;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.activity_like);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }
}
