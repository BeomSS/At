package com.example.user.at;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LetterMainActivity extends Activity {
    Skin skin;
    int color;
    ConstraintLayout loLetterHeader;
    ImageView btnLetterBack;
    RecyclerView rclLetter;
    Button btnWriteLetter;
    TextView btnLetterAllDelete;
    AppCompatSpinner spnLetterTitle;
    LetterAdapter letterAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.activity_letter);

        loLetterHeader = findViewById(R.id.loLetterHeader);
        btnLetterBack = findViewById(R.id.btnLetterBack);
        rclLetter = findViewById(R.id.rclLetter);
        btnWriteLetter = findViewById(R.id.btnWriteLetter);
        btnLetterAllDelete = findViewById(R.id.btnLetterAllDelete);
        spnLetterTitle = findViewById(R.id.spnLetterTitle);

        loLetterHeader.setBackgroundColor(color);
        btnWriteLetter.setBackgroundColor(color);

        String letterTitleList[] = {"받은 쪽지함", "보낸 쪽지함"};
        SpinnerAdapter adapter = new SpinnerAdapter(LetterMainActivity.this, android.R.layout.simple_spinner_item, letterTitleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLetterTitle.setAdapter(adapter);

        ArrayList<LetterItem> items = new ArrayList<>();
        //테스트용 데이터
        items.add(new LetterItem("user123", "userABC", "테스트1", "2018.10.29", "test123"));
        items.add(new LetterItem("user456", "userDEF", "테스트2", "2018.10.29", "test456"));
        items.add(new LetterItem("user789", "userGHI", "테스트3", "2018.10.29", "test789"));
        items.add(new LetterItem("user156861", "userJKL", "테스트4", "2018.10.29", "test123457"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rclLetter.setLayoutManager(layoutManager);
        rclLetter.setItemAnimator(new DefaultItemAnimator());
        letterAdapter = new LetterAdapter(this, items);
        rclLetter.setAdapter(letterAdapter);

        btnLetterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
            }
        });

        btnLetterAllDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LetterMainActivity.this, "전체삭제", Toast.LENGTH_SHORT).show();
            }
        });

        btnWriteLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LetterMainActivity.this, "편지쓰기", Toast.LENGTH_SHORT).show();
            }
        });

        spnLetterTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LetterMainActivity.this, spnLetterTitle.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class SpinnerAdapter extends ArrayAdapter<String> {
        Context context;
        String items[] = new String[]{};

        public SpinnerAdapter(final Context context,
                              final int textViewResourceId, final String[] objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {     //기본 Spinner View 정의
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            }

            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(items[position]);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
            tv.setTextSize(20);
            return convertView;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }
}
