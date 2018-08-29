package com.example.user.at;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 2000; //2초간 스플래쉬 실행

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // 메뉴액티비티를 실행하고 로딩화면을 끝냄
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }//test
}
