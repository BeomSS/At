package com.example.user.at;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by www on 2017-01-03.
 */

public class BackPressCloseHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finishAffinity();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity, activity.getResources().getString(R.string.str_back_press_message), Toast.LENGTH_SHORT);
        toast.show();
    }
}
