package com.example.user.at;

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
                        MyWritingListFragment myWritingListFragment = new MyWritingListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myWritingListFragment).addToBackStack(null).commit();
                        break;
                    case 1:
                        MyWritingFeedbackFragment myWritingFeedbackFragment = new MyWritingFeedbackFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myWritingFeedbackFragment).addToBackStack(null).commit();
                        break;
                    case 2:
                        MyNoticeFragment my_Notice_fragment = new MyNoticeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, my_Notice_fragment).addToBackStack(null).commit();
                        break;
                    case 3:
                        MyPreferencesFragment myPreferencesFragment = new MyPreferencesFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myPreferencesFragment).addToBackStack(null).commit();
                        break;
                }
            }
        });
    }
}


