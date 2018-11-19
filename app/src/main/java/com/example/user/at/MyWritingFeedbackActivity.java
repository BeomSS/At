package com.example.user.at;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import com.example.user.at.request.MyFeedbackRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by johan on 2018-06-05.
 */

public class MyWritingFeedbackActivity extends AppCompatActivity {
    Skin skin;
    int color;

    TextView tvMyFeedbackTitle;
    ImageView btnMyFeedbackBack;
    ConstraintLayout loMyFeedbackHeader;
    RecyclerView myInfoRecycler;
    LinearLayoutManager layoutManager;
    MyInfoAdapter adapter;
    ArrayList<MyInfoItem> items;
    String feedbackid, postid, memberid, fcontent, time, frecommend;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.my_writing_post);

        myInfoRecycler = (RecyclerView) findViewById(R.id.my_info_recycler);
        tvMyFeedbackTitle = findViewById(R.id.tvMyWriteTitle);
        btnMyFeedbackBack = findViewById(R.id.btnMyWriteBack);
        loMyFeedbackHeader = findViewById(R.id.loMyWriteHeader);

        tvMyFeedbackTitle.setText("내가 쓴 피드백");
        loMyFeedbackHeader.setBackgroundColor(color);

        btnMyFeedbackBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
            }
        });

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //목록 불러오기
        MyFeedbackRequest wRequest = new MyFeedbackRequest(skin.getPreferenceString("LoginId"), fListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(wRequest);

        //클릭시 이벤트
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
                    Intent cIntent = new Intent(MyWritingFeedbackActivity.this, MoreFeedback.class);
                    TextView nTextView = myInfoRecycler.getChildViewHolder(child).itemView.findViewById(R.id.layout_num);
                    cIntent.putExtra("putter", "게시판");
                    cIntent.putExtra("f_postId", nTextView.getText().toString());
                    Log.d("board put test", nTextView.getText().toString());
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
        MyFeedbackRequest wRequest = new MyFeedbackRequest(skin.getPreferenceString("LoginId"), fListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(wRequest);
    }
}
