package com.example.user.at;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by johan on 2018-06-05.
 */

public class MyNoticeActivity extends AppCompatActivity {
    Skin skin;
    int color;
    View view;
    String[] testTimes={"2018.04.30 14:20","2018.04.28 14:20","2018.04.27 14:20","2018.04.01 14:20","2018.04.01 14:20"};
    String[] testTitles={"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","bbbb","cccc","abcd","555"};
    String[] testWriters={"Tea","Coffee","Bean","Tom","behind"};
    String[] testfeedbacks={"2","3","2","1","4"};

    RecyclerView myInfoRecycler;
    LinearLayoutManager layoutManager;
    MyInfoAdapter adapter;
    ArrayList<MyInfoItem> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.my_writing_post);

        myInfoRecycler=(RecyclerView) findViewById(R.id.my_info_recycler);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        items=new ArrayList();
        for (int num=0;num<=4;num++){
            items.add(new MyInfoItem(testTimes[num],testTitles[num],testWriters[num],testfeedbacks[num]));
        };

        myInfoRecycler.setLayoutManager(layoutManager);
        myInfoRecycler.setItemAnimator(new DefaultItemAnimator());
        adapter=new MyInfoAdapter(items);
        myInfoRecycler.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
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
