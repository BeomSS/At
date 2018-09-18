package com.example.user.at;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class MyInfoViewHolder extends RecyclerView.ViewHolder {
    public TextView vhTime,vhWriter,vhTitle,vhFeedback,vhRecommend;
    public MyInfoViewHolder(View itemView){
        super(itemView);
        vhTime=(TextView)itemView.findViewById(R.id.layout_times);
        vhWriter=(TextView)itemView.findViewById(R.id.layout_writers);
        vhTitle=(TextView)itemView.findViewById(R.id.layout_titles);
        vhFeedback=(TextView)itemView.findViewById(R.id.layout_feedback);
        vhRecommend=(TextView)itemView.findViewById(R.id.layout_recommend);
    }
}
