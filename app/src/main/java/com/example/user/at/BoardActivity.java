package com.example.user.at;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.toolbox.JsonObjectRequest;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {
    Skin skin;
    int color;
    String[] testTimes={"2018.04.30 14:20","2018.04.28 14:20","2018.04.27 14:20","2018.04.01 14:20","2018.04.01 14:20","2018.04.01 14:20","2018.04.01 14:20","2018.04.01 14:20","2018.04.01 14:20"};
    String[] testTitles={"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","bbbb","cccc","abcd","555","2","2","2","2"};
    String[] testWriters={"Tea","Coffee","Bean","Tom","behind","2","2","2","2"};
    String[] testfeedbacks={"2","3","2","1","4","2","2","2","2","2"};
    String[] testrecommends={"2","3","2","1","4","2","2","2","2","2"};

    String categoryName;
    RecyclerView boardRecycler;
    LinearLayoutManager layoutManager;
    ArrayList<MyInfoItem> items;
    MyInfoAdapter adapter;
    int num;
    JsonObjectRequest jsonRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.board);

        boardRecycler=findViewById(R.id.board_recycler);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        items=new ArrayList();
        for (num=0;num<=8;num++){
            items.add(new MyInfoItem(testTimes[num],testTitles[num],testWriters[num],testfeedbacks[num],testrecommends[num]));
        };

        boardRecycler.setLayoutManager(layoutManager);
        boardRecycler.setItemAnimator(new DefaultItemAnimator());
        adapter=new MyInfoAdapter(items);
        boardRecycler.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }
}
