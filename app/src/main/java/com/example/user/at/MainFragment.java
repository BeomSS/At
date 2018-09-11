package com.example.user.at;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainFragment extends Fragment {
    View view;
    TextView text1, text2, text3, content, picture1, picture2, picture3, music1, music2, music3;
    Button textbtn, picturebtn, musicbtn, pause, play, stop;
    ImageView imageView;
    NestedScrollView scrollView1;
    ScrollView scrollView2;

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        text1 = view.findViewById(R.id.Text1_1);
        text2 = view.findViewById(R.id.Text2_1);
        text3 = view.findViewById(R.id.Text3_1);
        content = view.findViewById(R.id.Content);
        picture1 = view.findViewById(R.id.Picture1_1);
        picture2 = view.findViewById(R.id.Picture2_1);
        picture3 = view.findViewById(R.id.Picture3_1);
        music1 = view.findViewById(R.id.Music1_1);
        music2 = view.findViewById(R.id.Music2_1);
        music3 = view.findViewById(R.id.Music3_1);
        textbtn = view.findViewById(R.id.Textbtn);
        picturebtn = view.findViewById(R.id.Picturebtn);
        musicbtn = view.findViewById(R.id.Musicbtn);
        pause = view.findViewById(R.id.Pause);
        play = view.findViewById(R.id.Play);
        stop = view.findViewById(R.id.Stop);
        imageView = view.findViewById(R.id.Picture4);
        scrollView1 = view.findViewById(R.id.scrollView);
        scrollView2 = view.findViewById(R.id.text4);

        scrollView2.setOnTouchListener(new View.OnTouchListener() {                  //이중 스크롤을 사용할때 안쪽 스크롤 터치시 바깥쪽 스크롤 터치이벤트 정지
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    scrollView1.requestDisallowInterceptTouchEvent(false);
                else
                    scrollView1.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        textbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        picturebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        musicbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

}
