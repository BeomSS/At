package com.example.user.at;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.at.request.FeedbackRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoreFeedback extends AppCompatActivity {
    Skin skin;
    int color;
    RecyclerView rclFeedback;
    LinearLayoutManager layoutManager;
    ArrayList<FeedbackItem> items;
    FeedbackAdapter adapter;
    String postId;
    String fId, fMemberId, time, fContent, recommend;
    Boolean feedbackLiked;
    SwipeRefreshLayout swpFeedbackRefresh;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.activity_more_feedback);

        rclFeedback = findViewById(R.id.rclFeedbackMore);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        swpFeedbackRefresh = findViewById(R.id.swpFeedbackRefresh);

        printFeedbackList();


        swpFeedbackRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                printFeedbackList();
            }
        });

    }

    void printFeedbackList() {
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

                        fId = row.getString("feedback_id");
                        fMemberId = row.getString("member_id");
                        time = row.getString("create_time");
                        fContent = row.getString("feedback_content");
                        recommend = String.valueOf(row.getInt("recommend"));
                        feedbackLiked = row.getBoolean("feedback_liked");
                        items.add(new FeedbackItem(fId, fMemberId, fContent, time, recommend, feedbackLiked));
                    }

                    rclFeedback.setLayoutManager(layoutManager);
                    rclFeedback.setItemAnimator(new DefaultItemAnimator());
                    adapter = new FeedbackAdapter(items, MoreFeedback.this);
                    rclFeedback.setAdapter(adapter);
                    swpFeedbackRefresh.setRefreshing(false);

                } catch (Exception e) {
                    Log.d("dberror", e.toString());
                }
            }
        };

        Intent fIntent = getIntent();
        postId = fIntent.getStringExtra("f_postId");
        FeedbackRequest fRequest = new FeedbackRequest(postId, skin.getPreferenceString("LoginId"), fListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(fRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }
}
