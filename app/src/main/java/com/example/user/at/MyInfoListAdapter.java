package com.example.user.at;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by johan on 2018-05-24.
 */


public class MyInfoListAdapter extends BaseAdapter {

    String[] myinfo={" 내가 쓴 글"," 내가 쓴 피드백"," 알림"," 환경설정"};

    Context context;

    public MyInfoListAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return myinfo.length;
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

        LinearLayout list_cont = new LinearLayout(context);
        list_cont.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        ImageView list_img = new ImageView(context);
        TextView list_text = new TextView(context);

        list_img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
        list_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
        list_img.setImageResource(R.drawable.arrow);
        list_text.setText(myinfo[i]);
        list_text.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
        list_img.setPadding(3,3,3,3);
        list_text.setPadding(3,4,0,0);

        list_cont.addView(list_img);
        list_cont.addView(list_text);

        return list_cont;
    }
}

