package com.example.user.at;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.at.request.AddFeedbackRequest;
import com.example.user.at.request.FeedbackLikingRequest;
import com.example.user.at.request.PostDeleteRequest;
import com.example.user.at.request.PostLikingRequest;
import com.example.user.at.request.PostMarkingRequest;
import com.example.user.at.request.PostRequest;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowPictureActivity extends Activity implements Runnable {
    Skin skin;
    int color,likeCount;
    ConstraintLayout loHeaderShowPicture;
    ImageView btnShowPictureBack, btnPictureFeedbackLike, postImageView, ivShowPictureLike, ivShowPictureBookmark, btnShowPictureDelete;
    EditText edtPictureWriteFeedback;
    TextView titleTextView, explainTextView, tvBestFeedbackName, tvBestFeedbackContent, tvBestFeedbackCount, tvBestFeedbackId, tvShowPictureLikeCount, tvShowPictureWriter, tvShowPictureTime;
    ImageButton musicStartBtn, musicStopBtn, musicResetBtn;
    Button btnPictureWriteFeedback, btnPictureMoreFeedBack;
    LinearLayout btnShowPictureBookmark, btnShowPictureLike;
    ProgressBar pgbShowPictureLoading;
    Boolean showPictureBookMarked, pictureFeedbackLiked, showPictureLiked;
    Bitmap bitmap;
    URL url = null;
    Intent pIntent;
    int category, usingBestFeedback = 0;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        pIntent = getIntent();
        category = pIntent.getIntExtra("category", 0);
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
        loHeaderShowPicture = findViewById(R.id.loHeaderShowPicture);
        tvBestFeedbackId = findViewById(R.id.tvPictureFeedbackId);
        tvBestFeedbackName = findViewById(R.id.tvPictureFeedbackUserName);
        tvBestFeedbackContent = findViewById(R.id.tvPictureFeedbackContent);
        tvBestFeedbackCount = findViewById(R.id.tvPictureFeedbackLikeCount);
        tvShowPictureLikeCount = findViewById(R.id.tvShowPictureLikeCount);
        tvShowPictureWriter = findViewById(R.id.tvShowPictureWriter);
        tvShowPictureTime = findViewById(R.id.tvShowPictureTime);
        btnShowPictureBack = findViewById(R.id.btnShowPictureBack);
        btnShowPictureBookmark = findViewById(R.id.btnShowPictureBookmark);
        btnPictureFeedbackLike = findViewById(R.id.btnPictureFeedbackLike);
        edtPictureWriteFeedback = findViewById(R.id.edtPictureWriteFeedback);
        btnPictureWriteFeedback = findViewById(R.id.btnPictureWriteFeedback);
        titleTextView = findViewById(R.id.tvShowPictureTitle);
        explainTextView = findViewById(R.id.tvShowPictureContent);
        btnPictureMoreFeedBack = findViewById(R.id.btnPictureMoreFeedback);
        btnShowPictureLike = findViewById(R.id.btnShowPictureLike);
        ivShowPictureBookmark = findViewById(R.id.ivShowPictureBookmark);
        ivShowPictureLike = findViewById(R.id.ivShowPictureLike);
        btnShowPictureDelete = findViewById(R.id.btnShowPictureDelete);
        pgbShowPictureLoading = findViewById(R.id.pgbShowPictureLoading);
        showPictureBookMarked = false;
        pictureFeedbackLiked = false;
        showPictureLiked = false;

        loHeaderShowPicture.setBackgroundColor(color);
        btnPictureMoreFeedBack.setBackgroundColor(color);
        btnPictureWriteFeedback.setBackgroundColor(color);
        btnShowPictureLike.setBackgroundColor(color);
        btnShowPictureBookmark.setBackgroundColor(color);


        btnShowPictureBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
            }
        });

        //관심작품 등록, 해제
        btnShowPictureBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPictureBookMarked) { //관심작품 해제시
                    Response.Listener markingListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "JSONObj response=" + response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.getBoolean("success")) {
                                    Toast.makeText(ShowPictureActivity.this, "관심작품을 해제하였습니다.", Toast.LENGTH_SHORT).show();
                                    ivShowPictureBookmark.setImageResource(R.drawable.ic_no_like_40dp);
                                    showPictureBookMarked = false;
                                } else {
                                    Toast.makeText(ShowPictureActivity.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.d("markingDBerror", e.toString());
                            }
                        }
                    };
                    PostMarkingRequest postMarkingRequest = new PostMarkingRequest(0,pIntent.getStringExtra("postid"), skin.getPreferenceString("LoginId"), markingListener);
                    RequestQueue queue = Volley.newRequestQueue(ShowPictureActivity.this);
                    queue.add(postMarkingRequest);
                } else { //관심작품 등록시
                    Response.Listener markingListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "JSONObj response=" + response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.getBoolean("success")) {
                                    Toast.makeText(ShowPictureActivity.this, "관심작품으로 등록하였습니다.", Toast.LENGTH_SHORT).show();
                                    ivShowPictureBookmark.setImageResource(R.drawable.ic_like_white_40dp);
                                    showPictureBookMarked = true;
                                } else {
                                    Toast.makeText(ShowPictureActivity.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.d("markingDBerror", e.toString());
                            }
                        }
                    };
                    PostMarkingRequest postMarkingRequest = new PostMarkingRequest(1,pIntent.getStringExtra("postid"), skin.getPreferenceString("LoginId"), markingListener);
                    RequestQueue queue = Volley.newRequestQueue(ShowPictureActivity.this);
                    queue.add(postMarkingRequest);
                }
            }
        });

        //게시물 추천
        btnShowPictureLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeCount = Integer.parseInt(tvShowPictureLikeCount.getText().toString());

                if (showPictureLiked) {//이미 추천했을시
                    Toast.makeText(ShowPictureActivity.this,"이미 추천하였습니다.",Toast.LENGTH_SHORT).show();
                } else { //추천을 하지 않았을시
                    Response.Listener postLikingListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "JSONObj response=" + response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.getBoolean("check")) {
                                    if (jsonResponse.getBoolean("update") && jsonResponse.getBoolean("insert")) {
                                        Toast.makeText(ShowPictureActivity.this, "추천하였습니다.", Toast.LENGTH_SHORT).show();
                                        ivShowPictureLike.setImageResource(R.drawable.ic_thumb_up_white_40dp);
                                        showPictureLiked = true;
                                        likeCount++;
                                        tvShowPictureLikeCount.setText(String.valueOf(likeCount));
                                    } else {
                                        Toast.makeText(ShowPictureActivity.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(ShowPictureActivity.this, "자신의 게시물은 추천하지 못합니다.", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Log.d("feedbackDBerror", e.toString());
                            }
                        }
                    };
                    PostLikingRequest fLikingRequest = new PostLikingRequest(pIntent.getStringExtra("postid"), skin.getPreferenceString("LoginId"), postLikingListener);
                    RequestQueue queue = Volley.newRequestQueue(ShowPictureActivity.this);
                    queue.add(fLikingRequest);
                }
            }
        });

        //게시물 삭제 버튼
        btnShowPictureDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener PostDeleteListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "JSONObj response=" + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(MainActivity.context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(ShowPictureActivity.this, "게시물 삭제에 실패하였습니다..", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("dberror", e.toString());
                        }
                    }
                };
                PostDeleteRequest dRequest = new PostDeleteRequest(pIntent.getStringExtra("postid"),skin.getPreferenceString("LoginId"), PostDeleteListener);
                RequestQueue queue = Volley.newRequestQueue(ShowPictureActivity.this);
                queue.add(dRequest);
            }
        });

        //베스트 피드백 추천
        btnPictureFeedbackLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pictureFeedbackLiked) {
                    Toast.makeText(ShowPictureActivity.this, "이미 추천한 피드백입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Response.Listener feedbackLikingListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "JSONObj response=" + response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.getBoolean("check")) {
                                    if (jsonResponse.getBoolean("update") && jsonResponse.getBoolean("insert")) {
                                        Toast.makeText(ShowPictureActivity.this, "추천하였습니다.", Toast.LENGTH_SHORT).show();
                                        int recommend = Integer.parseInt(tvBestFeedbackCount.getText().toString());
                                        recommend++;
                                        tvBestFeedbackCount.setText(String.valueOf(recommend));
                                        btnPictureFeedbackLike.setImageResource(R.drawable.ic_thumb_up_color_30dp);
                                        btnPictureFeedbackLike.setEnabled(false);
                                        pictureFeedbackLiked = true;
                                    } else {
                                        Toast.makeText(ShowPictureActivity.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(ShowPictureActivity.this, "자신의 피드백은 추천하지 못합니다.", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Log.d("feedbackDBerror", e.toString());
                            }
                        }
                    };
                    FeedbackLikingRequest fLikingRequest = new FeedbackLikingRequest(pIntent.getStringExtra("postid"), skin.getPreferenceString("LoginId"), tvBestFeedbackId.getText().toString(), feedbackLikingListener);
                    RequestQueue queue = Volley.newRequestQueue(ShowPictureActivity.this);
                    queue.add(fLikingRequest);

                }
            }
        });

        //피드백 남기기
        btnPictureWriteFeedback.setOnClickListener(new View.OnClickListener() {
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
                AddFeedbackRequest feedbackRequest = new AddFeedbackRequest(pIntent.getStringExtra("postid"), skin.getPreferenceString("LoginId"), edtPictureWriteFeedback.getText().toString(), feedbackListener);
                RequestQueue queue = Volley.newRequestQueue(ShowPictureActivity.this);
                queue.add(feedbackRequest);
            }
        });

        //게시물 내용 불러오기
        Response.Listener pListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "JSONObj response=" + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    titleTextView.setText(jsonResponse.getString("post_title"));
                    tvShowPictureWriter.setText(jsonResponse.getString("member_id"));
                    tvShowPictureTime.setText(jsonResponse.getString("create_time"));
                    tvShowPictureLikeCount.setText(jsonResponse.getString("recommend"));
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
                        if (jsonResponse.getString("best_feedback_liked") == "true") {
                            btnPictureFeedbackLike.setImageResource(R.drawable.ic_thumb_up_color_30dp);
                            pictureFeedbackLiked = true;
                        }
                    }
                    //삭제버튼 활성화/비활성화
                    if(!tvShowPictureWriter.getText().toString().equals(skin.getPreferenceString("LoginId"))){
                        btnShowPictureDelete.setVisibility(View.INVISIBLE);
                    }

                    //게시물이 추천 받았었는지 체크
                    if(jsonResponse.getString("post_liked") == "true") {
                        ivShowPictureLike.setImageResource(R.drawable.ic_thumb_up_white_40dp);
                        showPictureLiked = true;
                    }

                    //TODO 관심작품 등록 되어있는지 체크
                    if(jsonResponse.getString("attention") == "true") {
                        ivShowPictureBookmark.setImageResource(R.drawable.ic_like_white_40dp);
                        showPictureBookMarked = true;
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
                        pgbShowPictureLoading.setVisibility(View.GONE);
                        musicStartBtn.setVisibility(View.VISIBLE);
                        musicStopBtn.setVisibility(View.VISIBLE);
                        musicResetBtn.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    Log.d("dberror", e.toString());
                }
            }
        };

        PostRequest pRequest = new PostRequest(pIntent.getStringExtra("postid"), skin.getPreferenceString("LoginId"), pListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(pRequest);

        btnPictureMoreFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedbackIntent = new Intent(ShowPictureActivity.this, MoreFeedback.class);
                feedbackIntent.putExtra("f_postId", pIntent.getStringExtra("postid"));
                startActivity(feedbackIntent);
                overridePendingTransition(R.anim.left_to_center_translate, R.anim.stop_translate);
            }
        });

    }

    //서버에서 받아온 이미지를 핸들러를 경유해서 이미지뷰에 넣는다
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            postImageView.setImageBitmap(bitmap);
            pgbShowPictureLoading.setVisibility(View.GONE);
            postImageView.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void run() {
        if (url != null) {
            try {
                //이미지가 지멋대로 돌아가는 경우가 발생하므로 ExifInterface를 먼저 받아온다.
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                InputStream iStream = conn.getInputStream();

                ExifInterface ei = new ExifInterface(iStream);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                iStream.close();
                conn.disconnect();

                //이미지를 받은 뒤 위에서 받은 회전값으로 수정을 해준 뒤 핸들러에 전달
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                iStream = conn.getInputStream();

                bitmap = BitmapFactory.decodeStream(iStream);

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        bitmap = rotate(bitmap, 90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        bitmap = rotate(bitmap, 180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        bitmap = rotate(bitmap, 270);
                        break;
                    case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                        bitmap = flip(bitmap, true, false);
                        break;
                    case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                        bitmap = flip(bitmap, false, true);
                        break;
                }
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }

    @Override
    protected void onStop() {
        if (category == 2) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
        super.onStop();
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
