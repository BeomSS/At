package com.example.user.at;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.at.request.LetterDeleteRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class LetterAdapter extends RecyclerView.Adapter<LetterViewHolder> {
    Context context;
    ArrayList<LetterItem> items;
    Skin skin;

    LetterAdapter(Context context, ArrayList item){
        this.context = context;
        items = item;
    }

    @NonNull
    @Override
    public LetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_letter, parent, false);
        LetterViewHolder holder = new LetterViewHolder(v);
        skin=new Skin(context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LetterViewHolder holder, final int position) {
        holder.tvLetterRclId.setText(items.get(position).getSentUser());
        holder.tvLetterRclTitle.setText(items.get(position).getLetterTitle());
        holder.tvLetterRclTime.setText(items.get(position).getLetterTime());
        holder.tvLetterRclHiddenId.setText(items.get(position).getLetterId());

        if(items.get(position).flag==1){
            holder.btnLetterRclReply.setVisibility(View.INVISIBLE);
        }

        //TODO
        holder.btnLetterRclReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        //리스트 선택 삭제시
        holder.btnLetterRclDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener LetterDeleteListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", context.getResources().getString(R.string.log_json_response) + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(context, context.getResources().getString(R.string.str_delete_message), Toast.LENGTH_SHORT).show();
                                items.remove(position);
                                notifyDataSetChanged();
                            }else{
                                Toast.makeText(context, context.getResources().getString(R.string.str_delete_fail_message), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.d("dberror", e.toString());
                        }
                    }
                };

                LetterDeleteRequest dRequest = new LetterDeleteRequest(0,skin.getPreferenceString("LoginId"),holder.tvLetterRclHiddenId.getText().toString(), LetterDeleteListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(dRequest);

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getResources().getString(R.string.str_writer_label)+items.get(position).getSentUser());
                builder.setMessage(items.get(position).getLetterContent());
                builder.setNegativeButton(context.getResources().getString(R.string.str_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
