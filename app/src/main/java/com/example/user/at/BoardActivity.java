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

public class BoardActivity extends AppCompatActivity {
    Skin skin;
    int color;
    RecyclerView boardRecycler;
    LinearLayoutManager layoutManager;
    ArrayList<MyInfoItem> items;
    MyInfoAdapter adapter;
    String time,title,writer,feedback,recommend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.board);

        boardRecycler=findViewById(R.id.board_recycler);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        Response.Listener bListener=new Response.Listener<String>(){
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
                        writer=row.getString("member_id");
                        feedback="0";
                        recommend=String.valueOf(row.getInt("recommend"));
                        items.add(new MyInfoItem(0,null,time,title,writer,feedback,recommend));
                    }

                    boardRecycler.setLayoutManager(layoutManager);
                    boardRecycler.setItemAnimator(new DefaultItemAnimator());
                    adapter=new MyInfoAdapter(items);
                    boardRecycler.setAdapter(adapter);

                }catch (Exception e){
                    Log.d("dberror",e.toString());
                }
            }
        };

        BoardRequest bRequest = new BoardRequest(0,bListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(bRequest);
    }
/*
    public void processResponse(JSONArray response) {
        try {
            for(int i=0;i<response.length();i++){
                JSONObject row=response.getJSONObject(i);
                time=row.getString("create_time");
                title=row.getString("post_title");
                writer=row.getString("member_id");
                feedback="0";
                recommend=String.valueOf(row.getInt("recommend"));
                items.add(new MyInfoItem(time,title,writer,feedback,recommend));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }
}
