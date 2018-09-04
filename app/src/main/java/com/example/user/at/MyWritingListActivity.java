package com.example.user.at;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Objects;

/**
 * Created by johan on 2018-06-05.
 */

public class MyWritingListActivity extends AppCompatActivity {
    ListView my_writing_post_list;
    MyWritingListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_writing_post);
        Objects.requireNonNull(getSupportActionBar()).hide();
        my_writing_post_list=(ListView)findViewById(R.id.my_writing_post_list);
        adapter = new MyWritingListAdapter(this,0);
        my_writing_post_list.setAdapter(adapter);
    }

    /* 프래그먼트이던 시절
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.my_writing_post,container,false);
        my_writing_post_list=(ListView)view.findViewById(R.id.my_writing_post_list);
        adapter = new MyWritingListAdapter(getActivity(),0);
        my_writing_post_list.setAdapter(adapter);
        return view;
    }
    */
}
