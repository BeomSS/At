package com.example.user.at;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.Objects;

/**
 * Created by johan on 2018-06-05.
 */

public class MyNoticeActivity extends AppCompatActivity {
    View view;
    ListView my_notice_list;
    MyWritingListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_notice);
        Objects.requireNonNull(getSupportActionBar()).hide();
        my_notice_list=(ListView)findViewById(R.id.my_notice_list);
        adapter = new MyWritingListAdapter(this,2);
        my_notice_list.setAdapter(adapter);
    }

    /*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.my_notice,container,false);
        my_notice_list=(ListView)view.findViewById(R.id.my_notice_list);
        adapter = new MyWritingListAdapter(getActivity(),2);
        my_notice_list.setAdapter(adapter);
        return view;
    }
    */
}
