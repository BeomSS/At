package com.example.user.at;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    //    Skin skin;
    BottomNavigationView btNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*skin = new Skin(this);
        int color = skin.skinSetting();
        int skinCode = skin.skinCode;*/
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeVer1);
        setContentView(R.layout.activity_main);

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

        /*switch(skinCode){
            case 1:
                headerLayout.setBackground(getResources().getDrawable(R.drawable.side_nav_bar_mint));
                break;
            case 2:
                headerLayout.setBackground(getResources().getDrawable(R.drawable.side_nav_bar_blue));
                break;
            case 3:
                headerLayout.setBackground(getResources().getDrawable(R.drawable.side_nav_bar_black));
                break;
        }*/
    }
    void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frgMain, fragment);
        transaction.commit();
    }

    /*public void my_info(View v){            //내정보(네비게이션 드로어 헤더 부분) 클릭 시
        Intent intent = new Intent(this, MyInfoListActivity.class);
        startActivity(intent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {         //옵션메뉴 생성
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {           //옵션아이템(홈버튼) 클릭 시
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        *//*if (id == R.id.action_GoHome) {         //홈버튼 클릭 시
            FragmentTest fragmentTest = new FragmentTest();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentTest).commit();     //처음화면으로
        }*//*

        return super.onOptionsItemSelected(item);
    }*/

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {        //네비게이션 아이템 클릭 시
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.nav_board) {          //글 클릭 시
            // Handle the camera action
            intent = new Intent(this, BoardFragment.class);
        } else if (id == R.id.nav_message) {    //쪽지 클릭 시
            intent = new Intent(this, LetterMainActivity.class);
        } else if (id == R.id.nav_like) {       //관심 있는 작품 클릭 시
            intent = new Intent(this, LikeActivity.class);
        } else if (id == R.id.nav_help) {       //고객센터 클릭 시
            intent = new Intent(this, HelpActivity.class);
        } else if (id == R.id.nav_setting) {    //설정 클릭 시
            intent = new Intent(this, SettingActivity.class);
        } else if (id == R.id.nav_logout) {     //로그아웃 클릭 시
            intent = new Intent(this, SplashActivity.class);
            finish();
        }
        startActivity(intent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);        //네비게이션 아이템 클릭 후에는 드로우어 닫음
        return true;
    }*/

    /*public void changeSkin(View v){
        switch(v.getId()){
            case R.id.btn1:
                skin.setPreference(skin.key, 1);
                break;
            case R.id.btn2:
                skin.setPreference(skin.key, 2);
                break;
            case R.id.btn3:
                skin.setPreference(skin.key, 3);
                break;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }*/

}
