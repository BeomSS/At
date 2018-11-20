package com.example.user.at;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Objects;

public class BoardFragment extends Fragment {

    ImageView btnTextBoard, btnMusicBoard, btnPictureBoard;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board, container, false);

        btnTextBoard = view.findViewById(R.id.btnTextBoard);
        btnPictureBoard =  view.findViewById(R.id.btnPictureBoard);
        btnMusicBoard = view.findViewById(R.id.btnMusicBoard);

        btnTextBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BoardActivity.class);
                intent.putExtra("category",0);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.left_to_center_translate, R.anim.stop_translate);
            }
        });
        btnPictureBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BoardActivity.class);
                intent.putExtra("category",1);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.left_to_center_translate, R.anim.stop_translate);
            }
        });
        btnMusicBoard.setOnClickListener(new View.OnClickListener() {
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
