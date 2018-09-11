package com.example.user.at;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by johan on 2018-06-06.
 */

public class LetterMainActivity extends Activity {
    Skin skin;
    int color;
    Spinner letterSpinner;
    TextView letterText;
    int position;
    FloatingActionButton letterFloating;
    ListView letterList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        int color = skin.skinSetting();
        setContentView(R.layout.activity_letter);

        letterSpinner = (Spinner) findViewById(R.id.letter_spinner);
        letterText = (TextView) findViewById(R.id.letter_text);
        letterFloating = (FloatingActionButton) findViewById(R.id.letter_floting);
        letterList = (ListView) findViewById(R.id.letter_list);

        letterFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dlgView = View.inflate(LetterMainActivity.this, R.layout.dialog_letter, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(LetterMainActivity.this);
                builder.setView(dlgView);
                builder.setPositiveButton("보내기", null);
                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });

        letterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                switch (position) {
                    case 0:
                        letterText.setText("받은쪽지함");
                        break;
                    case 1:
                        letterText.setText("보낸쪽지함");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }
}
