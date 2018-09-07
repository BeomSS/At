package com.example.user.at;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;

public class HelpActivity extends Activity{

    private ArrayList<String> mGroupList = null;
    private ArrayList<ArrayList<String>> mChildList = null;
    private ArrayList<String> mChildListContent = null;
    private ExpandableListView mListView;
    Integer[] help_ImageID = {R.drawable.help_sliding, R.drawable.help_write, R.drawable.help_ban};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        mListView = (ExpandableListView) findViewById(R.id.elv_list);

        mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        mChildListContent = new ArrayList<String>();

        mGroupList.add("조작 TIPs");
        mGroupList.add("건의게시판");

        mChildListContent.add("게시판간 카테고리 이동");
        mChildListContent.add("게시판 글 작성");
        mChildListContent.add("댓글 차단하기");

        mChildList.add(mChildListContent);
        mChildList.add(mChildListContent);
        mChildList.add(mChildListContent);

        mListView.setAdapter(new BaseExpandableAdapter(this, mGroupList, mChildList));
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) { //상위 리스트 클릭 시
                //Toast.makeText(getApplicationContext(), "g click = " + groupPosition, Toast.LENGTH_SHORT).show();
                if(groupPosition == 1)    //건의게시판 클릭 시 하위메뉴 열리지 않음.
                    return true;
                return false;
            }
        });

        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {             //하위 리스트 클릭 시
                //Toast.makeText(getApplicationContext(), "c click = " + childPosition, Toast.LENGTH_SHORT).show();
                View dlgView = View.inflate(HelpActivity.this, R.layout.dialog_help, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(HelpActivity.this);
                ImageView ivHelp = (ImageView) dlgView.findViewById(R.id.iv_Help);
                ivHelp.setImageResource(help_ImageID[childPosition]);

                dlg.setTitle("TIPs");
                dlg.setView(dlgView);
                dlg.setNegativeButton("닫기", null);
                dlg.show();
                return false;
            }
        });

        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {                                        //상위 그룹을 닫았을 시
                //Toast.makeText(getApplicationContext(), "g Collapse = " + groupPosition, Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {                                          //상위 그룹을 열었을 시
                //Toast.makeText(getApplicationContext(), "g Expand = " + groupPosition, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }
}

