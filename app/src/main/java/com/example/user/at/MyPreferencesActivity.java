package com.example.user.at;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by johan on 2018-06-05.
 */

public class MyPreferencesActivity extends AppCompatActivity {
    Spinner spnSkin;
    Button btnSaveMyPreference;
    Skin skin;
    int skinCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        int color = skin.skinSetting();
        skinCode = skin.getSkinCode();
        setContentView(R.layout.my_preferences);

        btnSaveMyPreference = findViewById(R.id.btnSaveMyPreference);
        spnSkin = findViewById(R.id.spnSkin);
        spnSkin.setSelection(skinCode - 1);

        btnSaveMyPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skinCode != spnSkin.getSelectedItemPosition() + 1) {
                    skin.setPreference(skin.key, spnSkin.getSelectedItemPosition() + 1);
                    Intent intent = new Intent(MyPreferencesActivity.this, SplashActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_to_center_translate, R.anim.stop_translate);
                    ((MainActivity) MainActivity.context).finish();
                    finish();
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }

    /*프래그먼트였을때
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_preferences,container,false);
    }*/
}
