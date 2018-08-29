package com.example.user.at;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by johan on 2018-06-05.
 */

public class MyWritingListAdapter extends BaseAdapter {
    Context context;
    int type;
    Boolean notice=true;
    String[] testTimes={"2018.04.30 14:20","2018.04.28 14:20","2018.04.27 14:20","2018.04.01 14:20"};
    String[] testTitles={"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","bbbb","cccc","abcd"};
    String[] testWriters={"Tea","Coffee","Bean","Tom"};

    public MyWritingListAdapter(Context context, int i){this.context = context;type=i;}

    @Override
    public int getCount() {
        return testTimes.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LinearLayout list_cont= new LinearLayout(context);
        TextView test_Time;
        TextView test_Writer;
        TextView test_Title;
        TextView test_feedbackcount;

        switch (type){
            case 0:
                list_cont.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                list_cont.setPadding(5,0,0,0);
                list_cont.setOrientation(LinearLayout.VERTICAL);

                test_Time = new TextView(context);
                test_Title = new TextView(context);
                test_feedbackcount = new TextView(context);

                test_Time.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
                test_Title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
                test_feedbackcount.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

                test_Title.setEllipsize(TextUtils.TruncateAt.END);
                test_Title.setSingleLine();

                test_Time.setText(testTimes[i]);
                test_Title.setText("제목: "+testTitles[i]);
                test_feedbackcount.setText("피드백()");

                test_Time.setTextSize(10);
                test_feedbackcount.setTextSize(10);
                test_Time.setPadding(0,10,0,5);
                test_Title.setPadding(0,5,0,5);
                test_feedbackcount.setPadding(0,5,0,10);

                list_cont.addView(test_Time);
                list_cont.addView(test_Title);
                list_cont.addView(test_feedbackcount);
                break;
            case 1:
                list_cont.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                list_cont.setPadding(5,0,0,0);
                list_cont.setOrientation(LinearLayout.VERTICAL);

                test_Time = new TextView(context);
                test_Writer = new TextView(context);
                test_Title = new TextView(context);
                test_feedbackcount = new TextView(context);

                test_Time.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
                test_Writer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
                test_Title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
                test_feedbackcount.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

                test_Title.setEllipsize(TextUtils.TruncateAt.END);
                test_Title.setSingleLine();

                test_Time.setText(testTimes[i]);
                test_Writer.setText("작성자: "+testWriters[i]);
                test_Title.setText("제목: "+testTitles[i]);
                test_feedbackcount.setText("피드백()");

                test_Time.setTextSize(10);
                test_feedbackcount.setTextSize(10);
                test_Writer.setTextSize(10);
                test_Writer.setPadding(0,0,0,5);
                test_Time.setPadding(0,10,0,5);
                test_Title.setPadding(0,5,0,5);
                test_feedbackcount.setPadding(0,5,0,10);

                list_cont.addView(test_Time);
                list_cont.addView(test_Writer);
                list_cont.addView(test_Title);
                list_cont.addView(test_feedbackcount);
                break;
            case 2:
                list_cont.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                list_cont.setPadding(5,0,0,0);
                list_cont.setOrientation(LinearLayout.VERTICAL);

                test_Time = new TextView(context);
                test_Title = new TextView(context);

                test_Time.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
                test_Title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

                test_Time.setTextSize(10);
                test_Time.setPadding(0,5,0,5);
                test_Title.setPadding(0,5,0,5);

                test_Time.setText(testTimes[i]);

                if (notice)
                    test_Title.setText(testWriters[i]+"님이 게시물에 피드백을 남겼습니다.");
                else
                    test_Title.setText(testWriters[i]+"님이 쪽지를 보냈습니다.");

                list_cont.addView(test_Time);
                list_cont.addView(test_Title);
                break;
        }
        return list_cont;
    }
}
