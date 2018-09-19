package com.example.user.at;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by johan on 2018-06-05.
 */

public class MyWritingListActivity extends AppCompatActivity {
    Skin skin;
    int color;

    RecyclerView myInfoRecycler;
    LinearLayoutManager layoutManager;
    MyInfoAdapter adapter;
    ArrayList<MyInfoItem> items;
    String category,time,title,feedback,recommend,writer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.my_writing_post);

        myInfoRecycler = (RecyclerView) findViewById(R.id.my_info_recycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        Response.Listener wListener=new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "JSONObj response=" + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray=jsonResponse.getJSONArray("sign");

                    items=new ArrayList<>();

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject row=jsonArray.getJSONObject(i);
                        time=row.getString("create_time");
                        title=row.getString("post_title");
                        category=row.getString("category");
                        writer=row.getString("member_id");
                        feedback="0";
                        recommend=String.valueOf(row.getInt("recommend"));
                        items.add(new MyInfoItem(1,category,time,title,writer,feedback,recommend));
                    }

                    myInfoRecycler.setLayoutManager(layoutManager);
                    myInfoRecycler.setItemAnimator(new DefaultItemAnimator());
                    adapter = new MyInfoAdapter(items);
                    myInfoRecycler.setAdapter(adapter);

                }catch (Exception e){
                    Log.d("dberror",e.toString());
                }
            }
        };

        MyRequest wRequest = new MyRequest("test",wListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(wRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }
}
