package com.example.user.at;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment {
    View view;
    TextView tvScrollingTest;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        tvScrollingTest = view.findViewById(R.id.tvScrollingTest);
        for (int i = 1; i <= 50; i++){
            tvScrollingTest.setText(tvScrollingTest.getText() + String.valueOf(i) + "\n");
        }
        return view;
    }
}
