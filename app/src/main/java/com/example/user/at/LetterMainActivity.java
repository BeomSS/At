package com.example.user.at;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.user.at.request.LetterDeleteRequest;
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
    SwipeRefreshLayout swpLetterRefresh;
    ArrayList<LetterItem> getitems;
    ArrayList<LetterItem> putitems;
    String letterGetUserId, letterPutUserId, letterTitle, letterTime, letterId, letterContent;
    int where = 0; //전체삭제시 어느 쪽지함인지 분별하는 변수

    @Override
    protected void onResume() {
        super.onResume();
        printLetterList();
    }

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
        swpLetterRefresh = findViewById(R.id.swpLetterRefresh);

        loLetterHeader.setBackgroundColor(color);
        btnWriteLetter.setBackgroundColor(color);

        String letterTitleList[] = {getResources().getString(R.string.str_receive_letter), getResources().getString(R.string.str_send_letter)};
        final SpinnerAdapter adapter = new SpinnerAdapter(LetterMainActivity.this, android.R.layout.simple_spinner_item, letterTitleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLetterTitle.setAdapter(adapter);

        btnLetterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
            }
        });

        //전체삭제 버튼
        btnLetterAllDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener LetterDeleteListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", getResources().getString(R.string.log_json_response) + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(LetterMainActivity.this, getResources().getString(R.string.str_delete_message), Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(LetterMainActivity.this, getResources().getString(R.string.str_delete_fail_message), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("dberror", e.toString());
                        }
                    }
                };
                LetterDeleteRequest dRequest = new LetterDeleteRequest(1, skin.getPreferenceString("LoginId"), where, LetterDeleteListener);
                RequestQueue queue = Volley.newRequestQueue(LetterMainActivity.this);
                queue.add(dRequest);
            }
        });


        //편지쓰기 버튼 클릭 이벤트
        btnWriteLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeLetterIntent = new Intent(LetterMainActivity.this,LetterDialog.class);
                startActivityForResult(writeLetterIntent,0);
                onStop();
            }
        });

        spnLetterTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spnLetterTitle.getSelectedItem().toString().equals(getResources().getString(R.string.str_send_letter))) {
                    where = 1; //보낸쪽지함이면 1
                    letterAdapter = new LetterAdapter(LetterMainActivity.this, putitems);
                    rclLetter.setAdapter(letterAdapter);
                } else {
                    where = 0; //받은쪽지함이면 0
                    letterAdapter = new LetterAdapter(LetterMainActivity.this, getitems);
                    rclLetter.setAdapter(letterAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        swpLetterRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                printLetterList();
            }
        });
    }

    void printLetterList() {
        getitems = new ArrayList<>();
        putitems = new ArrayList<>();
        Response.Listener LetterListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", getResources().getString(R.string.log_json_response) + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray putLetterJson = jsonResponse.getJSONArray("putresult");
                    JSONArray getLetterJson = jsonResponse.getJSONArray("getresult");

                    //받은 쪽지 어레이리스트에 넣기
                    for (int i = 0; i < getLetterJson.length(); i++) {
                        JSONObject row = getLetterJson.getJSONObject(i);
                        letterPutUserId = row.getString("g_send_id");
                        letterGetUserId = row.getString("g_receive_id");
                        letterTitle = row.getString("g_title");
                        letterTime = row.getString("g_send_time");
                        letterId = row.getString("g_message_id");
                        letterContent = row.getString("g_content");
                        getitems.add(new LetterItem(0, letterPutUserId, letterTitle, letterTime, letterId, letterContent));
                    }
                    //보낸 쪽지 어레이리스트에 넣기
                    for (int i = 0; i < putLetterJson.length(); i++) {
                        JSONObject row = putLetterJson.getJSONObject(i);
                        letterPutUserId = row.getString("p_send_id");
                        letterGetUserId = row.getString("p_receive_id");
                        letterTitle = row.getString("p_title");
                        letterTime = row.getString("p_send_time");
                        letterId = row.getString("p_message_id");
                        letterContent = row.getString("p_content");
                        putitems.add(new LetterItem(1, letterGetUserId, letterTitle, letterTime, letterId, letterContent));
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(LetterMainActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rclLetter.setLayoutManager(layoutManager);
                    rclLetter.setItemAnimator(new DefaultItemAnimator());
                    if(spnLetterTitle.getSelectedItem().toString().equals(getResources().getString(R.string.str_send_letter))) {
                        where = 1;
                        letterAdapter = new LetterAdapter(LetterMainActivity.this, putitems);
                    } else {
                        where = 0;
                        letterAdapter = new LetterAdapter(LetterMainActivity.this, getitems);
                    }

                    rclLetter.setAdapter(letterAdapter);
                    swpLetterRefresh.setRefreshing(false);

                } catch (Exception e) {
                    Log.d("dberror", e.toString());
                }
            }
        };

        LetterListRequest lRequest = new LetterListRequest(skin.getPreferenceString("LoginId"), LetterListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(lRequest);
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
