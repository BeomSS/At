package com.example.user.at;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.example.user.at.request.BoardRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {
    Skin skin;
    int color;
    TextView tvBoardTitle;
    ImageView btnBoardBack;
    ConstraintLayout loBoardHeader;
    RecyclerView boardRecycler;
    LinearLayoutManager layoutManager;
    ArrayList<MyInfoItem> items;
    MyInfoAdapter adapter;
    String num, time, title, writer, feedback, recommend, headerTitle;
    Intent intent;
    SwipeRefreshLayout swpBoardRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.board);

        boardRecycler = findViewById(R.id.board_recycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        swpBoardRefresh = findViewById(R.id.swpBoardRefresh);
        tvBoardTitle = findViewById(R.id.tvBoardTitle);
        btnBoardBack = findViewById(R.id.btnBoardBack);
        loBoardHeader = findViewById(R.id.loBoardHeader);

        loBoardHeader.setBackgroundColor(color);

        intent = getIntent();

        switch (intent.getIntExtra("category", 0)) {
            case 0:
                headerTitle = "글 게시판";
                break;
            case 1:
                headerTitle = "그림 게시판";
                break;
            case 2:
                headerTitle = "음악 게시판";
                break;
        }

        tvBoardTitle.setText(headerTitle);

        btnBoardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
            }
        });

        printBoardList();

        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        boardRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = boardRecycler.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    Intent cIntent = new Intent(BoardActivity.this, ShowPictureActivity.class);
                    TextView wTextView = boardRecycler.getChildViewHolder(child).itemView.findViewById(R.id.layout_writers);
                    TextView nTextView = boardRecycler.getChildViewHolder(child).itemView.findViewById(R.id.layout_num);
                    cIntent.putExtra("putter", "게시판");
                    cIntent.putExtra("category", intent.getIntExtra("category", 0));
                    cIntent.putExtra("writer", wTextView.getText().toString());
                    cIntent.putExtra("postid", nTextView.getText().toString());
                    Log.d("board put test", wTextView.getText().toString() + " || " + nTextView.getText().toString());
                    startActivity(cIntent);
                    overridePendingTransition(R.anim.left_to_center_translate, R.anim.stop_translate);
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

        swpBoardRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                printBoardList();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }

    void printBoardList() {
        Response.Listener bListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "JSONObj response=" + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("sign");

                    items = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject row = jsonArray.getJSONObject(i);
                        num = row.getString("post_id");
                        Log.d("post test2", num);
                        time = row.getString("create_time");
                        title = row.getString("post_title");
                        writer = row.getString("member_id");
                        feedback = row.getString("feedback_count");
                        recommend = String.valueOf(row.getInt("recommend"));
                        items.add(new MyInfoItem(0, num, null, time, title, writer, feedback, recommend));
                    }

                    boardRecycler.setLayoutManager(layoutManager);
                    boardRecycler.setItemAnimator(new DefaultItemAnimator());
                    adapter = new MyInfoAdapter(items);
                    boardRecycler.setAdapter(adapter);
                    swpBoardRefresh.setRefreshing(false);   //이 문장 기술 안할 시, 새로고침 이미지가 끝없이 빙글빙글 돔
                } catch (Exception e) {
                    Log.d("dberror", e.toString());
                }
            }
        };

        BoardRequest bRequest = new BoardRequest(intent.getIntExtra("category", 0), bListener);
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
}
