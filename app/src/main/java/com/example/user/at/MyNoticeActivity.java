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
import com.example.user.at.request.NoticeCategoryRequest;
import com.example.user.at.request.NoticeRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by johan on 2018-06-05.
 */

public class MyNoticeActivity extends AppCompatActivity {
    Skin skin;
    int color;
    View view;

    TextView tvNoticeTitle;
    ImageView btnNoticeBack;
    ConstraintLayout loNoticeHeader;
    RecyclerView myInfoRecycler;
    LinearLayoutManager layoutManager;
    MyInfoAdapter adapter;
    ArrayList<MyInfoItem> items;
    int noticeCategory;
    String noticeId, noticeValue, noticeUserId, noticeMessage, noticeTime, noticeDirect, url;
    Intent nIntent;
    TextView nTextView;
    RequestQueue queue;
    NoticeCategoryRequest ncRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.my_writing_post);

        myInfoRecycler = (RecyclerView) findViewById(R.id.my_info_recycler);
        tvNoticeTitle = findViewById(R.id.tvMyWriteTitle);
        btnNoticeBack = findViewById(R.id.btnMyWriteBack);
        loNoticeHeader = findViewById(R.id.loMyWriteHeader);

        tvNoticeTitle.setText("알림");
        loNoticeHeader.setBackgroundColor(color);

        btnNoticeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
            }
        });

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //알림 목록 서버에서 받아오기
        Response.Listener nListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "JSONObj response=" + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("sign");

                    items = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject row = jsonArray.getJSONObject(i);
                        noticeId = row.getString("notice_id");
                        noticeValue = row.getString("notice_value");
                        noticeUserId = row.getString("notice_user");
                        noticeMessage = row.getString("notice_message");
                        noticeTime = row.getString("notice_time");
                        noticeDirect = row.getString("notice_direct");
                        items.add(new MyInfoItem(3, noticeId, noticeValue, noticeTime, noticeMessage, noticeUserId, noticeDirect, null));
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

        skin.getPreferenceString("LoginId");

        NoticeRequest nRequest = new NoticeRequest(skin.getPreferenceString("LoginId"), nListener);
        queue = Volley.newRequestQueue(this);
        queue.add(nRequest);

        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        //알림 목록 클릭이벤트
        myInfoRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                //카테고리를 받아오는 리스너
                Response.Listener noticeCategoryListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("noticeCategoryListener TAG", "JSONObj response=" + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            noticeCategory = jsonResponse.getInt("category");
                            Log.d("noticeClickError", String.valueOf(noticeCategory));
                        } catch (Exception e) {
                            Log.d("noticeClickError", e.toString());
                        }
                    }
                };

                View child = myInfoRecycler.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    TextView cTextView = myInfoRecycler.getChildViewHolder(child).itemView.findViewById(R.id.layout_category);
                    int noticeValue = Integer.parseInt(cTextView.getText().toString());
                    switch (noticeValue) {
                        case 1://쪽지로 이동
                            nIntent = new Intent(MyNoticeActivity.this, LetterMainActivity.class);
                            startActivity(nIntent);
                            break;
                        case 2://해당 게시물로 이동
                            nIntent = new Intent(MyNoticeActivity.this, ShowPictureActivity.class);
                            nTextView = myInfoRecycler.getChildViewHolder(child).itemView.findViewById(R.id.layout_num);

                            ncRequest = new NoticeCategoryRequest(nTextView.getText().toString(), noticeCategoryListener);
                            queue = Volley.newRequestQueue(MyNoticeActivity.this);
                            queue.add(ncRequest);

                            nIntent.putExtra("category", noticeCategory);
                            nIntent.putExtra("postid", nTextView.getText().toString());
                            startActivity(nIntent);
                            break;
                        case 3://해당 게시물로 이동
                            nIntent = new Intent(MyNoticeActivity.this, ShowPictureActivity.class);
                            nTextView = myInfoRecycler.getChildViewHolder(child).itemView.findViewById(R.id.layout_num);

                            ncRequest = new NoticeCategoryRequest(nTextView.getText().toString(), noticeCategoryListener);
                            queue = Volley.newRequestQueue(MyNoticeActivity.this);
                            queue.add(ncRequest);

                            nIntent.putExtra("category", noticeCategory);
                            nIntent.putExtra("postid", nTextView.getText().toString());
                            startActivity(nIntent);
                            break;
                    }
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) { }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }
        });
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
