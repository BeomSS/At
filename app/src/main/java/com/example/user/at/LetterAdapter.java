package com.example.user.at;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class LetterAdapter extends RecyclerView.Adapter<LetterViewHolder> {
    Context context;
    ArrayList<LetterItem> items;

    LetterAdapter(Context context, ArrayList item){
        this.context = context;
        items = item;
    }

    @NonNull
    @Override
    public LetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_letter, parent, false);
        LetterViewHolder holder = new LetterViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LetterViewHolder holder, final int position) {
        holder.tvLetterRclId.setText(items.get(position).getSentUser());
        holder.tvLetterRclTitle.setText(items.get(position).getLetterTitle());
        holder.tvLetterRclTime.setText(items.get(position).getLetterTime());
        holder.tvLetterRclHiddenId.setText(items.get(position).getLetterId());

        holder.btnLetterRclReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, items.get(position).getSentUser() + "님에게 답장", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnLetterRclDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                items.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, items.get(position).getLetterTitle() + " 자세히 보기", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
