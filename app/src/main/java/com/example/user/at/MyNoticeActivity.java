package com.example.user.at;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
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
    String noticeId, noticeValue, noticeUserId, noticeMessage, noticeTime, noticeDirect;

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
                        //(int fl, String idnum, String cate, String time, String title, String writer, String feed, String recommend)
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

        Skin pId=new Skin(MyNoticeActivity.this);
        pId.getPreferenceString("LoginId");

        NoticeRequest nRequest = new NoticeRequest(pId.getPreferenceString("LoginId"), nListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(nRequest);
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
