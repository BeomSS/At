package com.example.user.at;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class ShowPictureActivity extends Activity {
    ImageView btnShowPictureBack, btnShowPictureLike, btnPictureFeedbackLike;
    EditText edtPictureWriteFeedback;
    Boolean showPictureLiked, pictureFeedbackLiked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);

        btnShowPictureBack = findViewById(R.id.btnShowPictureBack);
        btnShowPictureLike = findViewById(R.id.btnShowPictureLike);
        btnPictureFeedbackLike = findViewById(R.id.btnPictureFeedbackLike);
        edtPictureWriteFeedback = findViewById(R.id.edtPictureWriteFeedback);
        showPictureLiked = false;
        pictureFeedbackLiked = false;

        btnShowPictureBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
            }
        });

        btnShowPictureLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPictureLiked) {
                    btnShowPictureLike.setImageResource(R.drawable.ic_no_like_40dp);
                    showPictureLiked = false;
                } else {
                    btnShowPictureLike.setImageResource(R.drawable.ic_like_40dp);
                    showPictureLiked = true;
                }
            }
        });

        btnPictureFeedbackLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pictureFeedbackLiked) {
                    btnPictureFeedbackLike.setImageResource(R.drawable.ic_thumb_up_outline_30dp);
                    pictureFeedbackLiked = false;
                } else {
                    btnPictureFeedbackLike.setImageResource(R.drawable.ic_thumb_up_color_30dp);
                    pictureFeedbackLiked = true;
                }
            }
        });
    }
}
