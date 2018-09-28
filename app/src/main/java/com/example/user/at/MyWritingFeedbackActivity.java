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
import com.example.user.at.request.MyFeedbackRequest;
import com.example.user.at.request.MyWritingRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by johan on 2018-06-05.
 */

public class MyWritingFeedbackActivity extends AppCompatActivity {
    Skin skin;
    int color;

    RecyclerView myInfoRecycler;
    LinearLayoutManager layoutManager;
    MyInfoAdapter adapter;
    ArrayList<MyInfoItem> items;
    String feedbackid, postid, memberid, fcontent, time, frecommend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.my_writing_post);

        myInfoRecycler = (RecyclerView) findViewById(R.id.my_info_recycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        Response.Listener fListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "JSONObj response=" + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("sign");

                    items = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject row = jsonArray.getJSONObject(i);
                        feedbackid = row.getString("feedback_id");
                        postid = row.getString("post_id");
                        memberid = row.getString("member_id");
                        fcontent = row.getString("f_content");
                        time = row.getString("create_time");
                        frecommend = row.getString("f_recommend");
                        items.add(new MyInfoItem(2, postid, null, time, fcontent, null, null, frecommend));
                    }

                    myInfoRecycler.setLayoutManager(layoutManager);
                    myInfoRecycler.setItemAnimator(new DefaultItemAnimator());
                    adapter = new MyInfoAdapter(items);
                    myInfoRecycler.setAdapter(adapter);

                } catch (Exception e) {
                    Log.d("dberror", e.toString());
                }
            }
        };

        MyFeedbackRequest wRequest = new MyFeedbackRequest("test", fListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(wRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }
}
