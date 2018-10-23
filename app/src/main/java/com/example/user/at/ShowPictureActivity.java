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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.at.request.AddFeedbackRequest;
import com.example.user.at.request.FeedbackLikingRequest;
import com.example.user.at.request.PostRequest;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowPictureActivity extends Activity implements Runnable {
    ImageView btnShowPictureBack, btnShowPictureLike, btnPictureFeedbackLike, postImageView;
    EditText edtPictureWriteFeedback;
    TextView titleTextView, explainTextView, tvBestFeedbackName, tvBestFeedbackContent, tvBestFeedbackCount, tvBestFeedbackId;
    ImageButton musicStartBtn, musicStopBtn, musicResetBtn;
    Button btnFeedbackUpload, btnFeedbackView;
    Boolean showPictureLiked, pictureFeedbackLiked;
    Bitmap bitmap;
    URL url = null;
    Intent pintent;
    int category, usingBestFeedback = 0;
    private MediaPlayer mediaPlayer;
    Skin pId = new Skin(ShowPictureActivity.this);

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
        tvBestFeedbackId = findViewById(R.id.tvPictureFeedbackId);
        tvBestFeedbackName = findViewById(R.id.tvPictureFeedbackUserName);
        tvBestFeedbackContent = findViewById(R.id.tvPictureFeedbackContent);
        tvBestFeedbackCount = findViewById(R.id.tvPictureFeedbackLikeCount);
        btnShowPictureBack = findViewById(R.id.btnShowPictureBack);
        btnShowPictureLike = findViewById(R.id.btnShowPictureLike);
        btnPictureFeedbackLike = findViewById(R.id.btnPictureFeedbackLike);
        edtPictureWriteFeedback = findViewById(R.id.edtPictureWriteFeedback);
        btnFeedbackUpload = findViewById(R.id.btnPictureWriteFeedback);
        titleTextView = findViewById(R.id.tvShowPictureTitle);
        explainTextView = findViewById(R.id.tvShowPictureContent);
        btnFeedbackView = findViewById(R.id.btnPictureMoreFeedback);
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
                    //TODO 추천버튼 다시 눌렀을 때 구현
                } else {
                    Response.Listener feedbackLikingListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "JSONObj response=" + response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.getBoolean("update") && jsonResponse.getBoolean("insert")) {
                                    Toast.makeText(ShowPictureActivity.this, "추천하였습니다.", Toast.LENGTH_SHORT).show();
                                    int recommend=Integer.parseInt(tvBestFeedbackCount.getText().toString());
                                    recommend++;
                                    tvBestFeedbackCount.setText(String.valueOf(recommend));
                                } else {
                                    Toast.makeText(ShowPictureActivity.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Log.d("feedbackDBerror", e.toString());
                            }
                        }
                    };
                    FeedbackLikingRequest fLikingRequest = new FeedbackLikingRequest(pintent.getStringExtra("postid"), pId.getPreferenceString("LoginId"), tvBestFeedbackId.getText().toString(), feedbackLikingListener);
                    RequestQueue queue = Volley.newRequestQueue(ShowPictureActivity.this);
                    queue.add(fLikingRequest);
                    btnPictureFeedbackLike.setImageResource(R.drawable.ic_thumb_up_color_30dp);
                    pictureFeedbackLiked = true;
                }
            }
        });

        btnFeedbackUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener feedbackListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "JSONObj response=" + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.getBoolean("success")) {
                                Toast.makeText(ShowPictureActivity.this, "피드백을 남겼습니다.", Toast.LENGTH_SHORT).show();
                                edtPictureWriteFeedback.setText("");
                            } else {
                                Toast.makeText(ShowPictureActivity.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.d("feedbackDBerror", e.toString());
                        }
                    }
                };
                AddFeedbackRequest feedbackRequest = new AddFeedbackRequest(pintent.getStringExtra("postid"), pId.getPreferenceString("LoginId"), edtPictureWriteFeedback.getText().toString(), feedbackListener);
                RequestQueue queue = Volley.newRequestQueue(ShowPictureActivity.this);
                queue.add(feedbackRequest);
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
                    usingBestFeedback = jsonResponse.getInt("f_use");
                    if (usingBestFeedback == 0) {
                        tvBestFeedbackName.setText("추천을 받은 피드백이 없습니다.");
                        tvBestFeedbackContent.setText("");
                        btnPictureFeedbackLike.setVisibility(View.INVISIBLE);
                        tvBestFeedbackCount.setVisibility(View.INVISIBLE);

                    } else if (usingBestFeedback == 1) {
                        tvBestFeedbackId.setText(jsonResponse.getString("feedback_id"));
                        tvBestFeedbackName.setText(jsonResponse.getString("f_member_id"));
                        tvBestFeedbackContent.setText(jsonResponse.getString("f_content"));
                        tvBestFeedbackCount.setText(jsonResponse.getString("f_recommend"));

                        //베스트 피드백이 추천 받았었는지 체크
                        if(jsonResponse.getString("best_feedback_liked")=="true"){
                            btnPictureFeedbackLike.setImageResource(R.drawable.ic_thumb_up_color_30dp);
                            pictureFeedbackLiked = true;
                        }
                    }
                    if (category == 1) {
                        String strUrl = LoginActivity.ipAddress + ":800/uploads/" + jsonResponse.getString("url");
                        url = new URL(strUrl);
                        Thread imgThread = new Thread(ShowPictureActivity.this);
                        imgThread.start();
                    } else if (category == 2) {
                        String strUrl = LoginActivity.ipAddress + ":800/uploads/" + jsonResponse.getString("url");
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

        PostRequest pRequest = new PostRequest(pintent.getStringExtra("postid"),pId.getPreferenceString("LoginId"), pListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(pRequest);

        btnFeedbackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedbackIntent = new Intent(ShowPictureActivity.this, MoreFeedback.class);
                feedbackIntent.putExtra("f_postId", pintent.getStringExtra("postid"));
                startActivity(feedbackIntent);
            }
        });

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
