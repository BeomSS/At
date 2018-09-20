package com.example.user.at;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    Skin skin;
    BottomNavigationView btNav;
    public static Context context;
    int color;
    BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        skin = new Skin(this);
        color = skin.skinSetting();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backPressCloseHandler = new BackPressCloseHandler(this);

        btNav = findViewById(R.id.btNavMain);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) btNav.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        MainFragment fragment = new MainFragment();
        loadFragment(fragment);

        btNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.show_Main:
                        fragment = new MainFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.show_board:
                        fragment = new BoardFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.write_board:
                        fragment = new WriteFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.show_menu:
                        fragment = new MenuFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });

    }

    void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frgMain, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
