package com.example.user.at;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by johan on 2018-06-05.
 */

public class MyWritingListActivity extends AppCompatActivity {
    String[] testTimes={"2018.04.30 14:20","2018.04.28 14:20","2018.04.27 14:20","2018.04.01 14:20","2018.04.01 14:20"};
    String[] testTitles={"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","bbbb","cccc","abcd","555"};
    String[] testWriters={"Tea","Coffee","Bean","Tom","behind"};
    String[] testfeedbacks={"2","3","2","1","4"};
    int num;

    RecyclerView myInfoRecycler;
    LinearLayoutManager layoutManager;
    MyInfoAdapter adapter;
    ArrayList<MyInfoItem> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_writing_post);
        Objects.requireNonNull(getSupportActionBar()).hide();

        myInfoRecycler=(RecyclerView) findViewById(R.id.my_info_recycler);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        items=new ArrayList();
        for (num=0;num<=4;num++){
            items.add(new MyInfoItem(testTimes[num],testTitles[num],testWriters[num],testfeedbacks[num]));
        };
        myInfoRecycler.setLayoutManager(layoutManager);
        myInfoRecycler.setItemAnimator(new DefaultItemAnimator());
        adapter=new MyInfoAdapter(items);
        myInfoRecycler.setAdapter(adapter);

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
