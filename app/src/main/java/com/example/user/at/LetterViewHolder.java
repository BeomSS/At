package com.example.user.at;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LetterViewHolder extends RecyclerView.ViewHolder {
    public TextView tvLetterRclId, tvLetterRclTitle, tvLetterRclTime, tvLetterRclHiddenId;
    public ImageView btnLetterRclDelete, btnLetterRclReply;

    public LetterViewHolder(View itemView){
        super(itemView);
        tvLetterRclId = itemView.findViewById(R.id.tvLetterRclId);
        tvLetterRclTitle = itemView.findViewById(R.id.tvLetterRclTitle);
        tvLetterRclTime = itemView.findViewById(R.id.tvLetterRclTime);
        tvLetterRclHiddenId = itemView.findViewById(R.id.tvLetterRclHiddenId);
        btnLetterRclDelete = itemView.findViewById(R.id.btnLetterRclDelete);
        btnLetterRclReply = itemView.findViewById(R.id.btnLetterRclReply);
    }
}
