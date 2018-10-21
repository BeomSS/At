package com.example.user.at;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class FeedbackViewHolder extends RecyclerView.ViewHolder {
    public TextView vhFeedbackId,vhFeedbackTime,vhWriter,vhContent,vhLike;

    public FeedbackViewHolder(View itemView){
        super(itemView);
        vhFeedbackId=(TextView)itemView.findViewById(R.id.txvFeedbackId);
        vhFeedbackTime=(TextView)itemView.findViewById(R.id.txvFeedbackTime);
        vhWriter=(TextView)itemView.findViewById(R.id.txvFeedbackWriter);
        vhContent=(TextView)itemView.findViewById(R.id.txvFeedbackContent);
        vhLike=(TextView)itemView.findViewById(R.id.txvFeedbackLike);
    }
}
