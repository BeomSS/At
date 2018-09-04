package com.example.user.at;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Objects;

/**
 * Created by johan on 2018-05-24.
 */

public class MyInfoListActivity extends AppCompatActivity {
    View view;
    ListView myInfoList;
    MyInfoListAdapter adapter;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        myInfoList = findViewById(R.id.my_info_list);
        adapter = new MyInfoListAdapter(this);
        myInfoList.setAdapter(adapter);

        myInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        intent = new Intent(MyInfoListActivity.this,MyWritingListActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MyInfoListActivity.this,MyWritingFeedbackActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MyInfoListActivity.this,MyNoticeActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MyInfoListActivity.this,MyPreferencesActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}


