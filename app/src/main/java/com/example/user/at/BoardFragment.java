package com.example.user.at;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.Objects;

public class BoardFragment extends Fragment {
    ImageButton text, music, picture;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board, container, false);

        text = (ImageButton) view.findViewById(R.id.text);
        picture = (ImageButton) view.findViewById(R.id.picture);
        music = (ImageButton) view.findViewById(R.id.music);


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BoardActivity.class);
                intent.putExtra("category",0);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.left_to_center_translate, R.anim.stop_translate);
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BoardActivity.class);
                intent.putExtra("category",1);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.left_to_center_translate, R.anim.stop_translate);
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BoardActivity.class);
                intent.putExtra("category",2);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.left_to_center_translate, R.anim.stop_translate);
            }
        });
        return view;
    }
}
