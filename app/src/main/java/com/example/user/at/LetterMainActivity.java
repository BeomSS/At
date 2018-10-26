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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.at.request.LetterListRequest;

import org.json.JSONArray;
import org.json.JSONObject;

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
    ArrayList<LetterItem> getitems;
    ArrayList<LetterItem> putitems;
    String letterGetUserId,letterPutUserId,letterTitle,letterTime,letterId,letterContent;
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

        getitems = new ArrayList<>();
        putitems = new ArrayList<>();

        Response.Listener LetterListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "JSONObj response=" + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray putLetterJson = jsonResponse.getJSONArray("putresult");
                    JSONArray getLetterJson = jsonResponse.getJSONArray("getresult");

                    //받은 쪽지 어레이리스트에 넣기
                    for (int i = 0; i < getLetterJson.length(); i++) {
                        JSONObject row = getLetterJson.getJSONObject(i);
                        letterPutUserId=row.getString("g_send_id");
                        letterGetUserId=row.getString("g_receive_id");
                        letterTitle=row.getString("g_title");
                        letterTime=row.getString("g_send_time");
                        letterId=row.getString("g_message_id");
                        letterContent=row.getString("g_content");
                        getitems.add(new LetterItem(letterPutUserId,letterTitle,letterTime,letterId,letterContent));
                    }
                    //보낸 쪽지 어레이리스트에 넣기
                    for (int i = 0; i < putLetterJson.length(); i++) {
                        JSONObject row = putLetterJson.getJSONObject(i);
                        letterPutUserId=row.getString("p_send_id");
                        letterGetUserId=row.getString("p_receive_id");
                        letterTitle=row.getString("p_title");
                        letterTime=row.getString("p_send_time");
                        letterId=row.getString("p_message_id");
                        letterContent=row.getString("p_content");
                        putitems.add(new LetterItem(letterGetUserId,letterTitle,letterTime,letterId,letterContent));
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(LetterMainActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rclLetter.setLayoutManager(layoutManager);
                    rclLetter.setItemAnimator(new DefaultItemAnimator());
                    letterAdapter = new LetterAdapter(LetterMainActivity.this, getitems);
                    rclLetter.setAdapter(letterAdapter);

                } catch (Exception e) {
                    Log.d("dberror", e.toString());
                }
            }
        };

        LetterListRequest lRequest = new LetterListRequest(skin.getPreferenceString("LoginId"), LetterListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(lRequest);


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
                LetterDialog dlg=new LetterDialog(LetterMainActivity.this,skin.getPreferenceString("LoginId"));
                dlg.show();
                Toast.makeText(LetterMainActivity.this, "편지쓰기", Toast.LENGTH_SHORT).show();
            }
        });

        spnLetterTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnLetterTitle.getSelectedItem().toString()=="보낸 쪽지함"){
                    letterAdapter = new LetterAdapter(LetterMainActivity.this, putitems);
                    rclLetter.setAdapter(letterAdapter);
                }else{
                    letterAdapter = new LetterAdapter(LetterMainActivity.this, getitems);
                    rclLetter.setAdapter(letterAdapter);
                }
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
