package com.example.user.at;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.at.request.MyLikeRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LikeActivity extends Activity {
    Skin skin;
    int color;
    int numCategory;
    TextView tvLikeTitle;
    ImageView btnLikeBack;
    ConstraintLayout loLikeHeader;
    RecyclerView myInfoRecycler;
    LinearLayoutManager layoutManager;
    MyInfoAdapter adapter;
    ArrayList<MyInfoItem> items;
    String postid, time, title, feedback, writer,category;
    SwipeRefreshLayout swpLikeRefresh;
    Response.Listener likeListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.my_writing_post);

        myInfoRecycler = (RecyclerView) findViewById(R.id.my_info_recycler);
        tvLikeTitle = findViewById(R.id.tvMyWriteTitle);
        btnLikeBack = findViewById(R.id.btnMyWriteBack);
        loLikeHeader = findViewById(R.id.loMyWriteHeader);
        swpLikeRefresh = findViewById(R.id.swpMyWriteRefresh);

        tvLikeTitle.setText(getResources().getString(R.string.str_like_work_string));
        loLikeHeader.setBackgroundColor(color);

        btnLikeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
            }
        });

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        printList();

        swpLikeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                printList();
            }
        });

        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        myInfoRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = myInfoRecycler.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    Intent cIntent = new Intent(LikeActivity.this, ShowPictureActivity.class);
                    TextView cTextView = myInfoRecycler.getChildViewHolder(child).itemView.findViewById(R.id.layout_category);
                    TextView nTextView = myInfoRecycler.getChildViewHolder(child).itemView.findViewById(R.id.layout_num);
                    Log.d("내 게시물 보기", cTextView.getText().toString());
                    cIntent.putExtra("putter", "게시판");
                    String category=cTextView.getText().toString();
                    if(category.equals("글 ")){
                        numCategory=0;
                    }else if(category.equals("그림 ")){
                        numCategory=1;
                    }else if(category.equals("음악 ")){
                        numCategory=2;
                    }
                    cIntent.putExtra("category",numCategory);
                    cIntent.putExtra("postid", nTextView.getText().toString());
                    Log.d("board put test", String.valueOf(numCategory) + " || " + nTextView.getText().toString());
                    startActivity(cIntent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLikeRequest mlRequest = new MyLikeRequest(skin.getPreferenceString("LoginId"), likeListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(mlRequest);
    }

    void printList() {

        likeListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "JSONObj response=" + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("sign");

                    items = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject row = jsonArray.getJSONObject(i);
                        postid = row.getString("post_id");
                        time = row.getString("create_time");
                        title = row.getString("post_title");
                        writer = row.getString("member_id");
                        category = row.getString("category");
                        feedback = row.getString("feedback_count");
                        items.add(new MyInfoItem(postid,time, title, writer, feedback, category));
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

        MyLikeRequest mlRequest = new MyLikeRequest(skin.getPreferenceString("LoginId"), likeListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(mlRequest);
        swpLikeRefresh.setRefreshing(false);
    }
}
