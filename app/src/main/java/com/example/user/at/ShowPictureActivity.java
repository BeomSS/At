package com.example.user.at;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.at.request.PostRequest;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowPictureActivity extends Activity implements Runnable {
    ImageView btnShowPictureBack, btnShowPictureLike, btnPictureFeedbackLike, postImageView;
    EditText edtPictureWriteFeedback;
    TextView titleTextView, explainTextView;
    ImageButton musicStartBtn, musicStopBtn, musicResetBtn;
    Boolean showPictureLiked, pictureFeedbackLiked;
    Bitmap bitmap;
    URL url = null;
    Intent pintent;
    int category;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pintent = getIntent();
        category = pintent.getIntExtra("category", 0);
        //어느 게시판인지에 따라 다른 레이아웃을 가져온다
        if (category == 0) {
            setContentView(R.layout.activity_show_write);
        } else if (category == 1) {
            setContentView(R.layout.activity_show_picture);
            postImageView = findViewById(R.id.ivShowPictureImg);
        } else if (category == 2) {
            setContentView(R.layout.activity_show_music);
            musicStartBtn = findViewById(R.id.musicStart);
            musicStopBtn = findViewById(R.id.musicStop);
            musicResetBtn = findViewById(R.id.musicReset);

            musicStartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                }
            });
            musicStopBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.pause();
                }
            });
            musicResetBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
            });
        }

        btnShowPictureBack = findViewById(R.id.btnShowPictureBack);
        btnShowPictureLike = findViewById(R.id.btnShowPictureLike);
        btnPictureFeedbackLike = findViewById(R.id.btnPictureFeedbackLike);
        edtPictureWriteFeedback = findViewById(R.id.edtPictureWriteFeedback);
        titleTextView = findViewById(R.id.tvShowPictureTitle);
        explainTextView = findViewById(R.id.tvShowPictureContent);
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

        Response.Listener pListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "JSONObj response=" + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    titleTextView.setText(jsonResponse.getString("post_title"));
                    explainTextView.setText(jsonResponse.getString("explain"));
                    if (category == 1) {
                        String strUrl = MainActivity.ipAddress + ":800/uploads/" + jsonResponse.getString("url");
                        url = new URL(strUrl);
                        Thread imgThread = new Thread(ShowPictureActivity.this);
                        imgThread.start();
                    } else if (category == 2) {
                        String strUrl = MainActivity.ipAddress + ":800/uploads/" + jsonResponse.getString("url");
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(strUrl);
                        mediaPlayer.prepare();
                    }

                } catch (Exception e) {
                    Log.d("dberror", e.toString());
                }
            }
        };

        PostRequest pRequest = new PostRequest(pintent.getStringExtra("postid"), pListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(pRequest);

    }

    //서버에서 받아온 이미지를 핸들러를 경유해서 이미지뷰에 넣는다
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            postImageView.setImageBitmap(bitmap);
        }
    };

    @Override
    public void run() {
        if (url != null) {
            try {
                // url에 접속 시도
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                // 스트림 생성
                InputStream iStream = conn.getInputStream();

                // 스트림에서 받은 데이터를 비트맵 변환
                // 인터넷에서 이미지 가져올 때는 Bitmap을 사용해야함
                bitmap = BitmapFactory.decodeStream(iStream);

                // 핸들러에게 화면 갱신을 요청한다.
                handler.sendEmptyMessage(0);

                // 연결 종료
                iStream.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "이미지 불러오기 오류", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        if (category == 2) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onStop();
    }
}
