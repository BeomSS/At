package com.example.user.at;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class MenuFragment extends Fragment {
    View view;
    Intent intent;
    ImageView btnSetting;
    Button btnNotice, btnLike, btnMyWrite, btnMyFeedback;
    TextView btnMessage, btnHelp, btnLogout;
    ConstraintLayout menuHeaderLayout;
    ShowHelpDialog dlgHelp;
    CustomDialog dlg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        btnSetting = view.findViewById(R.id.btnSetting);
        btnNotice = view.findViewById(R.id.btnNotice);
        btnLike = view.findViewById(R.id.btnLike);
        btnMyWrite = view.findViewById(R.id.btnMyWrite);
        btnMyFeedback = view.findViewById(R.id.btnMyFeedback);
        btnMessage = view.findViewById(R.id.btnMessage);
        btnHelp = view.findViewById(R.id.btnHelp);
        btnLogout = view.findViewById(R.id.btnLogout);
        menuHeaderLayout = view.findViewById(R.id.menuHeaderLayout);

        menuHeaderLayout.setBackgroundColor(((MainActivity)MainActivity.context).color);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),MyPreferencesActivity.class);
                startActivityInMenu();
            }
        });
        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), MyNoticeActivity.class);
                startActivityInMenu();
            }
        });
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), LikeActivity.class);
                startActivityInMenu();
            }
        });
        btnMyWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), MyWritingListActivity.class);
                startActivityInMenu();
            }
        });
        btnMyFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), MyWritingFeedbackActivity.class);
                startActivityInMenu();
            }
        });
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), LetterMainActivity.class);
                startActivityInMenu();
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlgHelp = new ShowHelpDialog(MainActivity.context, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlgHelp.dismiss();
                    }
                });
                dlgHelp.show();

            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg = new CustomDialog(MainActivity.context, "로그아웃", "정말 로그아웃 하시겠습니까?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlg.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(getActivity(), SplashActivity.class);
                        startActivityInMenu();
                        dlg.dismiss();
                        Objects.requireNonNull(getActivity()).finish();
                    }
                });
                dlg.show();
            }
        });

        return view;
    }

    void startActivityInMenu(){
        startActivity(intent);
        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.right_to_center_translate, R.anim.stop_translate);
    }
}
