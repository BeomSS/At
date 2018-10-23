package com.example.user.at;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackViewHolder> {
    private ArrayList<FeedbackItem> items;

    FeedbackAdapter(ArrayList item){
        items=item;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback,parent,false);
        FeedbackViewHolder holder=new FeedbackViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        holder.vhFeedbackId.setText(items.get(position).feedbackId);
        holder.vhFeedbackTime.setText(items.get(position).feedbacktime);
        holder.vhWriter.setText(items.get(position).writer);
        holder.vhContent.setText(items.get(position).contents);
        holder.vhLike.setText(items.get(position).likes);
        if(items.get(position).f_liked){
            holder.vhImage.setImageResource(R.drawable.ic_thumb_up_color_30dp);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
