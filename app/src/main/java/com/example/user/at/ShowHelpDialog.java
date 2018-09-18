package com.example.user.at;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.pm10.library.CircleIndicator;

import java.util.ArrayList;
import java.util.Objects;

public class ShowHelpDialog extends Dialog {
    private Context context;
    private View.OnClickListener leftListener, rightListener, singleListener;

    ShowHelpDialog(@NonNull Context context, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        super(context);
        this.context = context;
        this.leftListener = leftListener;
        this.rightListener = rightListener;

    }

    ShowHelpDialog(@NonNull Context context, View.OnClickListener singleListener) {
        super(context);
        this.context = context;
        this.singleListener = singleListener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_show_help);
        Button btnLeft = findViewById(R.id.btnHelpForeverClose);
        Button btnRight = findViewById(R.id.btnHelpClose);

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(R.drawable.help_ban);
        arrayList.add(R.drawable.help_sliding);
        arrayList.add(R.drawable.help_write);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vpHelpViewer);
        HelpInfoViewPagerAdapter adapter = new HelpInfoViewPagerAdapter(getLayoutInflater(), arrayList);
        viewPager.setAdapter(adapter);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicatorHelpViewer);
        indicator.setupWithViewPager(viewPager);
        Objects.requireNonNull(getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (leftListener == null) {
            btnLeft.setVisibility(View.GONE);
            btnRight.setOnClickListener(singleListener);
        } else {
            btnLeft.setOnClickListener(leftListener);
            btnRight.setOnClickListener(rightListener);
        }
    }
}
