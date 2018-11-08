package com.example.user.at;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.at.request.FeedbackLikingRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackViewHolder> {
    private ArrayList<FeedbackItem> items;
    ArrayList<Boolean> likeArray;
    Context context;
    Skin pId;

    FeedbackAdapter(ArrayList item, Context getContext) {
        context = getContext;
        items = item;
        pId = new Skin(context);
        likeArray = new ArrayList<>();
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        FeedbackViewHolder holder = new FeedbackViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FeedbackViewHolder holder, final int position) {
        holder.vhFeedbackId.setText(items.get(position).feedbackId);
        holder.vhFeedbackTime.setText(items.get(position).feedbacktime);
        holder.vhWriter.setText(items.get(position).writer);
        holder.vhContent.setText(items.get(position).contents);
        holder.vhLike.setText(items.get(position).likes);
        likeArray.add(position, items.get(position).f_liked);
        if (likeArray.get(position)) {
            holder.vhImage.setImageResource(R.drawable.ic_thumb_up_color_30dp);
        }
        Log.d("feedbackLikeTest", String.valueOf(position) + ", " + String.valueOf(likeArray.get(position)));

        holder.vhImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!likeArray.get(position)) {
                    final Response.Listener feedbackLikingListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "JSONObj response=" + response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.getBoolean("check")) {
                                    if (jsonResponse.getBoolean("update") && jsonResponse.getBoolean("insert")) {
                                        Toast.makeText(context, "추천하였습니다.", Toast.LENGTH_SHORT).show();
                                        int recommend = Integer.parseInt(items.get(position).likes);
                                        recommend++;
                                        holder.vhLike.setText(String.valueOf(recommend));
                                        holder.vhImage.setImageResource(R.drawable.ic_thumb_up_color_30dp);
                                        likeArray.set(position, true);
                                    } else {
                                        Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, "자신의 피드백은 추천하지 못합니다.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.d("feedbackDBerror", e.toString());
                            }
                        }
                    };
                    FeedbackLikingRequest fLikingRequest = new FeedbackLikingRequest("0", pId.getPreferenceString("LoginId"), items.get(position).feedbackId, feedbackLikingListener);
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(fLikingRequest);

                } else {
                    Toast.makeText(context, "이미 추천하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
