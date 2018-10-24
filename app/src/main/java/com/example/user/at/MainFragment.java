package com.example.user.at;

import android.annotation.SuppressLint;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainFragment extends Fragment {
    View view;
    TextView text1, text2, text3, content, picture1, picture2, picture3, music1, music2, music3, txvTextWriteId, txvImageWriteId, txvMusicWriteId;
    Button textbtn, btnPicture, musicbtn, btnBestMusicPause, btnBestMusicPlay, btnBestMusicStop;
    ImageView imageView;
    NestedScrollView scrollView1;
    ScrollView scrollView2;
    String imageURL,musicURL,strUrl;
    URL url;
    Bitmap bitmap;
    private MediaPlayer mediaPlayer;

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        txvTextWriteId = view.findViewById(R.id.txvTextWriteId);
        txvImageWriteId = view.findViewById(R.id.txvImageWriteId);
        txvMusicWriteId = view.findViewById(R.id.txvMusicWriteId);
        text1 = view.findViewById(R.id.Text1_1);
        text2 = view.findViewById(R.id.Text2_1);
        text3 = view.findViewById(R.id.Text3_1);
        content = view.findViewById(R.id.Content);
        picture1 = view.findViewById(R.id.Picture1_1);
        picture2 = view.findViewById(R.id.Picture2_1);
        picture3 = view.findViewById(R.id.Picture3_1);
        music1 = view.findViewById(R.id.Music1_1);
        music2 = view.findViewById(R.id.Music2_1);
        music3 = view.findViewById(R.id.Music3_1);
        textbtn = view.findViewById(R.id.Textbtn);
        btnPicture = view.findViewById(R.id.btnPicture);
        musicbtn = view.findViewById(R.id.Musicbtn);
        btnBestMusicPause = view.findViewById(R.id.btnBestMusicPause);
        btnBestMusicPlay = view.findViewById(R.id.btnBestMusicPlay);
        btnBestMusicStop = view.findViewById(R.id.btnBestMusicStop);
        imageView = view.findViewById(R.id.Picture4);
        scrollView1 = view.findViewById(R.id.scrollView);
        scrollView2 = view.findViewById(R.id.text4);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String besturl = LoginActivity.ipAddress + ":800/At/SeeBest.php";

        StringRequest bestRequest = new StringRequest(Request.Method.GET, besturl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("BEST_TAG", "JSONObj response=" + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            txvTextWriteId.setText(jsonResponse.getString("w_post_id"));
                            text1.setText(jsonResponse.getString("w_post_title"));
                            text2.setText(jsonResponse.getString("w_member_id"));
                            text3.setText(jsonResponse.getString("w_recommend"));
                            txvImageWriteId.setText(jsonResponse.getString("i_post_id"));
                            picture1.setText(jsonResponse.getString("i_post_title"));
                            picture2.setText(jsonResponse.getString("i_member_id"));
                            picture3.setText(jsonResponse.getString("i_recommend"));
                            txvMusicWriteId.setText(jsonResponse.getString("m_post_id"));
                            music1.setText(jsonResponse.getString("m_post_title"));
                            music2.setText(jsonResponse.getString("m_member_id"));
                            music3.setText(jsonResponse.getString("m_recommend"));
                            imageURL=jsonResponse.getString("i_url");
                            musicURL=jsonResponse.getString("m_url");

                            content.setText(jsonResponse.getString("w_explain"));

                            //베스트이미지 불러오기
                            strUrl = LoginActivity.ipAddress + ":800/uploads/" + jsonResponse.getString("i_url");
                            url = new URL(strUrl);
                            new Thread(new Runnable() {
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
                                                    bitmap=rotate(bitmap, 90);
                                                    break;

                                                case ExifInterface.ORIENTATION_ROTATE_180:
                                                    bitmap=rotate(bitmap, 180);
                                                    break;

                                                case ExifInterface.ORIENTATION_ROTATE_270:
                                                    bitmap=rotate(bitmap, 270);
                                                    break;

                                                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                                                    bitmap=flip(bitmap, true, false);
                                                    break;

                                                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                                                    bitmap=flip(bitmap, false, true);
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
                                        Toast.makeText(getActivity(), "이미지 불러오기 오류", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).start();

                            //베스트 음악 불러오기
                            String strUrl = LoginActivity.ipAddress + ":800/uploads/" + jsonResponse.getString("m_url");
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDataSource(strUrl);
                            mediaPlayer.prepare();

                        } catch (Exception e) {
                            Log.d("BestDBerror", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(bestRequest);

        scrollView2.setOnTouchListener(new View.OnTouchListener()

        {                  //이중 스크롤을 사용할때 안쪽 스크롤 터치시 바깥쪽 스크롤 터치이벤트 정지
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    scrollView1.requestDisallowInterceptTouchEvent(false);
                else
                    scrollView1.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //글 베스트 게시물 이동 버튼 클릭
        textbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cIntent = new Intent(getActivity(), ShowPictureActivity.class);
                cIntent.putExtra("putter", "게시판");
                cIntent.putExtra("category", 0);
                cIntent.putExtra("writer",text2.getText().toString());
                cIntent.putExtra("postid",txvTextWriteId.getText().toString());
                Log.d("board put test", text2.getText().toString() + " || " + txvTextWriteId.getText().toString());
                startActivity(cIntent);
            }
        });
        //그림 베스트 게시물 이동 버튼 클릭
        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cIntent = new Intent(getActivity(), ShowPictureActivity.class);
                cIntent.putExtra("putter", "게시판");
                cIntent.putExtra("category", 1);
                cIntent.putExtra("writer",picture2.getText().toString());
                cIntent.putExtra("postid",txvImageWriteId.getText().toString());
                Log.d("board put test", text2.getText().toString() + " || " + txvTextWriteId.getText().toString());
                startActivity(cIntent);
            }
        });
        //음악 베스트 게시물 이동 버튼 클릭
        musicbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cIntent = new Intent(getActivity(), ShowPictureActivity.class);
                cIntent.putExtra("putter", "게시판");
                cIntent.putExtra("category", 2);
                cIntent.putExtra("writer",music2.getText().toString());
                cIntent.putExtra("postid",txvMusicWriteId.getText().toString());
                startActivity(cIntent);
            }
        });

        //베스트 음악 버튼 클릭시 이벤트
        btnBestMusicPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        });

        btnBestMusicPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

        btnBestMusicStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
        });

        return view;
    }
    //이미지 핸들러
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            imageView.setImageBitmap(bitmap);
        }
    };

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

    @Override
    public void onPause() {
        mediaPlayer.pause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroyView();
    }
}
