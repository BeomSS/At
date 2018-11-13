package com.example.user.at;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.at.request.PreferenceRequest;

/**
 * Created by johan on 2018-06-05.
 */

public class MyPreferencesActivity extends AppCompatActivity {
    Spinner spnSkin;
    Button btnSaveMyPreference;
    Skin skin;
    int skinCode;
    CustomDialog dlg;
    RadioGroup rgChan;
    String userFavorite;
    Boolean changeTF = false;

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
        rgChan = findViewById(R.id.rgChange);

        rgChan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.radioGul){
                    userFavorite = Character.toString('0');
                    changeTF = true;}

                else if (checkedId == R.id.radioGrim){
                    userFavorite = Character.toString('1');
                    changeTF = true;}

                else if (checkedId == R.id.radioMusic) {
                    userFavorite = Character.toString('2');
                    changeTF = true;
                }
            }
        });

        btnSaveMyPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skinCode != spnSkin.getSelectedItemPosition() + 1) {
                    dlg = new CustomDialog(MyPreferencesActivity.this, "스킨 색상 변경", "스킨 색상을 변경하시면 앱이 재시작됩니다.\n계속 진행 하시겠습니까?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dlg.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Response.Listener rListener = new Response.Listener<String>(){
                                @Override
                                public void onResponse(String response) {
                                }
                            };
                            PreferenceRequest pRequest = new PreferenceRequest(skin.getPreferenceString("LoginId"),userFavorite, rListener);
                            RequestQueue queue = Volley.newRequestQueue(MyPreferencesActivity.this);
                            queue.add(pRequest);

                            skin.setPreference(skin.key, spnSkin.getSelectedItemPosition() + 1);
                            Intent intent = new Intent(MyPreferencesActivity.this, SplashActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_to_center_translate, R.anim.stop_translate);
                            ((MainActivity) MainActivity.context).finish();
                            dlg.dismiss();
                            finish();
                        }
                    });
                    dlg.show();

                } else {
                    Response.Listener rListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                        }
                    };
                    PreferenceRequest pRequest = new PreferenceRequest(skin.getPreferenceString("LoginId"),userFavorite, rListener);
                    RequestQueue queue = Volley.newRequestQueue(MyPreferencesActivity.this);
                    queue.add(pRequest);

                    finish();
                    overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
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
