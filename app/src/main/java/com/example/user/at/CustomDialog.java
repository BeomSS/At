package com.example.user.at;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class CustomDialog extends Dialog {
    Context context;
    String title, content;
    View.OnClickListener singleListener, leftListener, rightListener;

    private TextView tvDlgTitle, tvDlgContent;
    private Button btnLeft, btnRight, btnSingle;

    public CustomDialog(@NonNull Context context, String title, String content) {
        super(context);
        this.context = context;
        this.title = title;
        this.content = content;
    }

    public CustomDialog(@NonNull Context context, String title, String content, View.OnClickListener singleListener) {
        super(context);
        this.context = context;
        this.title = title;
        this.content = content;
        this.singleListener = singleListener;

    }

    public CustomDialog(@NonNull Context context, String title, String content, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        super(context);
        this.context = context;
        this.title = title;
        this.content = content;
        this.leftListener = leftListener;
        this.rightListener = rightListener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (singleListener == null) {
            setContentView(R.layout.dialog_layout_two_btn);
            btnLeft = findViewById(R.id.btnDlgTwoBtnCancle);
            btnRight = findViewById(R.id.btnDlgTwoBtnOk);

            btnLeft.setOnClickListener(leftListener);
            btnRight.setOnClickListener(rightListener);

            tvDlgTitle = findViewById(R.id.tvDlgTwoTitle);
            tvDlgContent = findViewById(R.id.tvDlgTwoContent);

            tvDlgTitle.setText(title);
            tvDlgContent.setText(content);


        } else {
            setContentView(R.layout.dialog_layout_one_btn);
            btnSingle = findViewById(R.id.btnDlgOneButtonOk);

            btnSingle.setOnClickListener(singleListener);

            tvDlgTitle = findViewById(R.id.tvDlgOneTitle);
            tvDlgContent = findViewById(R.id.tvDlgOneContent);

            tvDlgTitle.setText(title);
            tvDlgContent.setText(content);
        }

    }
}
