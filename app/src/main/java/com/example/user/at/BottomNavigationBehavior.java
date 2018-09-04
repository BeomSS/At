package com.example.user.at;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ravi on 21/12/17.
 */

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {    //바텀네비게이션 스크롤 숨김 기능

    private Boolean isScrolling = false;

    public BottomNavigationBehavior() {
        super();
    }

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
        return dependency instanceof FrameLayout;   //instanceof 연산자
                                                    //dependency 가 FrameLayout 타입으로 형변환 가능하면 참, 아니면 거짓
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        isScrolling = true;
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if(isScrolling && dy < 0){
            showBottomNavigationView(child);
        } else if(isScrolling && dy > 0){
            hideBottomNavigationView(child);
        }
        isScrolling = false;
    }

    private void hideBottomNavigationView(BottomNavigationView view) {
        view.animate().translationY(view.getHeight());
    }

    private void showBottomNavigationView(BottomNavigationView view) {
        view.animate().translationY(0);
    }
}
