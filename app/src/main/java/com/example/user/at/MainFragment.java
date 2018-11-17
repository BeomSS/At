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
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


public class MainFragment extends Fragment {
    View view;
    TextView tvBestTextTitle, tvBestTextWriter, tvBestTextRecomCnt, tvBestTextContent, tvBestPictureTitle, tvBestPictureWriter, tvBestPictureRecomCnt, tvBestMusicTitle, tvBestMusicWriter, tvBestMusicRecomCnt, tvTextWriteId, tvPictureWriteId,
            tvMusicWriteId, tvBestMusicContent, tvBestPictureContent, tvBestTextTime, tvBestPictureTime, tvBestMusicTime, tvBestTextRecom, tvBestPictureRecom, tvBestMusicRecom;
    ImageView imgBestPicture, btnGoBestTextBoard, btnGoBestPictureBoard, btnGoBestMusicBoard, btnBestMusicPause, btnBestMusicPlay, btnBestMusicRewind;
    ProgressBar pgbBestPictureLoading, pgbBestMusicLoading;
    String imageURL, musicURL, strUrl;
    URL url;
    Bitmap bitmap;
    private MediaPlayer mediaPlayer;
    ConstraintLayout parentConstraint, IoBestText, IoBestPicture, IoBestMusic, loBestTextHeader, loBestPictureHeader, loBestMusicHeader;
    Boolean musicCont = false;
    ConstraintSet constSet = new ConstraintSet();
    int interest;
    Skin skin;
    RequestQueue queue;


    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        skin = new Skin(getActivity());
        Intent gIntent = getActivity().getIntent();
        interest = gIntent.getIntExtra("interest", 0);

        parentConstraint = view.findViewById(R.id.parentConstraint);
        IoBestText = view.findViewById(R.id.loBestText);
        IoBestPicture = view.findViewById(R.id.IoBestPicture);
        IoBestMusic = view.findViewById(R.id.IoBestMusic);
        tvTextWriteId = view.findViewById(R.id.tvTextWriteId);
        tvPictureWriteId = view.findViewById(R.id.tvPictureWriteId);
        tvMusicWriteId = view.findViewById(R.id.tvMusicWriteId);
        tvBestTextTitle = view.findViewById(R.id.tvBestTextTitle);
        tvBestTextWriter = view.findViewById(R.id.tvBestTextWriter);
        tvBestTextRecomCnt = view.findViewById(R.id.tvBestTextRecomCnt);
        tvBestTextContent = view.findViewById(R.id.tvBestTextContent);
        tvBestTextTime = view.findViewById(R.id.tvBestTextTime);
        tvBestTextRecom = view.findViewById(R.id.tvBestTextRecom);
        tvBestPictureTitle = view.findViewById(R.id.tvBestPictureTitle);
        tvBestPictureWriter = view.findViewById(R.id.tvBestPictureWriter);
        tvBestPictureRecomCnt = view.findViewById(R.id.tvBestPictureRecomCnt);
        tvBestPictureContent = view.findViewById(R.id.tvBestPictureContent);
        tvBestPictureTime = view.findViewById(R.id.tvBestPictureTime);
        tvBestPictureRecom = view.findViewById(R.id.tvBestPictureRecom);
        tvBestMusicTitle = view.findViewById(R.id.tvBestMusicTitle);
        tvBestMusicWriter = view.findViewById(R.id.tvBestMusicWriter);
        tvBestMusicRecomCnt = view.findViewById(R.id.tvBestMusicRecomCnt);
        tvBestMusicContent = view.findViewById(R.id.tvBestMusicContent);
        tvBestMusicTime = view.findViewById(R.id.tvBestMusicTime);
        tvBestMusicRecom = view.findViewById(R.id.tvBestMusicRecom);
        btnGoBestTextBoard = view.findViewById(R.id.btnGoBestTextBoard);
        btnGoBestPictureBoard = view.findViewById(R.id.btnGoBestPictureBoard);
        btnGoBestMusicBoard = view.findViewById(R.id.btnGoBestMusicBoard);
        btnBestMusicPause = view.findViewById(R.id.btnBestMusicPause);
        btnBestMusicPlay = view.findViewById(R.id.btnBestMusicPlay);
        btnBestMusicRewind = view.findViewById(R.id.btnBestMusicRewind);
        imgBestPicture = view.findViewById(R.id.imgBestPicture);
        pgbBestPictureLoading = view.findViewById(R.id.pgbBestPictureLoading);
        pgbBestMusicLoading = view.findViewById(R.id.pgbBestMusicLoading);
        loBestTextHeader = view.findViewById(R.id.loBestTextHeader);
        loBestPictureHeader = view.findViewById(R.id.loBestPictureHeader);
        loBestMusicHeader = view.findViewById(R.id.loBestMusicHeader);

        btnBestMusicPause.setVisibility(View.INVISIBLE);
        btnBestMusicPlay.setVisibility(View.INVISIBLE);
        btnBestMusicRewind.setVisibility(View.INVISIBLE);

        loBestTextHeader.setBackgroundColor(((MainActivity) MainActivity.context).color);
        loBestPictureHeader.setBackgroundColor(((MainActivity) MainActivity.context).color);
        loBestMusicHeader.setBackgroundColor(((MainActivity) MainActivity.context).color);

        switch (interest) {
            case 1:
                constSet.clone(parentConstraint);
                constSet.connect(IoBestPicture.getId(), ConstraintSet.TOP, parentConstraint.getId(), ConstraintSet.TOP);
                constSet.connect(IoBestText.getId(), ConstraintSet.TOP, IoBestPicture.getId(), ConstraintSet.BOTTOM);
                constSet.connect(IoBestMusic.getId(), ConstraintSet.TOP, IoBestText.getId(), ConstraintSet.BOTTOM);
                constSet.applyTo(parentConstraint);
                break;
            case 2:
                constSet.clone(parentConstraint);
                constSet.connect(IoBestMusic.getId(), ConstraintSet.TOP, parentConstraint.getId(), ConstraintSet.TOP);
                constSet.connect(IoBestText.getId(), ConstraintSet.TOP, IoBestMusic.getId(), ConstraintSet.BOTTOM);
                constSet.connect(IoBestPicture.getId(), ConstraintSet.TOP, IoBestText.getId(), ConstraintSet.BOTTOM);
                constSet.applyTo(parentConstraint);
                break;
        }

        queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        String besturl = LoginActivity.ipAddress + ":800/At/SeeBest.php";

        StringRequest bestRequest = new StringRequest(Request.Method.GET, besturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("BEST_TAG", "JSONObj response=" + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.getString("w_post_id") != "null") {
                        tvTextWriteId.setText(jsonResponse.getString("w_post_id"));
                        tvBestTextTitle.setText(jsonResponse.getString("w_post_title"));
                        tvBestTextWriter.setText(jsonResponse.getString("w_member_id"));
                        tvBestTextRecomCnt.setText(jsonResponse.getString("w_recommend"));
                        tvBestTextContent.setText(jsonResponse.getString("w_explain"));
                        tvBestTextTime.setText(jsonResponse.getString("w_create_time"));
                    } else {
                        tvBestTextTitle.setText("추천을 받은 게시물이 없습니다.");
                        tvBestTextWriter.setText("");
                        tvBestTextRecomCnt.setText("");
                        tvBestTextTime.setText("");
                        tvBestTextRecom.setText("");
                        btnGoBestTextBoard.setVisibility(View.INVISIBLE);
                    }

                    if (jsonResponse.getString("i_post_id") != "null") {
                        tvPictureWriteId.setText(jsonResponse.getString("i_post_id"));
                        tvBestPictureTitle.setText(jsonResponse.getString("i_post_title"));
                        tvBestPictureWriter.setText(jsonResponse.getString("i_member_id"));
                        tvBestPictureRecomCnt.setText(jsonResponse.getString("i_recommend"));
                        tvBestPictureContent.setText(jsonResponse.getString("i_explain"));
                        tvBestPictureTime.setText(jsonResponse.getString("i_create_time"));
                    } else {
                        tvBestPictureTitle.setText("추천을 받은 게시물이 없습니다.");
                        tvBestPictureWriter.setText("");
                        tvBestPictureRecomCnt.setText("");
                        tvBestPictureContent.setText("");
                        tvBestPictureTime.setText("");
                        tvBestPictureRecom.setText("");
                        btnGoBestPictureBoard.setVisibility(View.INVISIBLE);

                    }

                    if (jsonResponse.getString("m_post_id") != "null") {
                        tvMusicWriteId.setText(jsonResponse.getString("m_post_id"));
                        tvBestMusicTitle.setText(jsonResponse.getString("m_post_title"));
                        tvBestMusicWriter.setText(jsonResponse.getString("m_member_id"));
                        tvBestMusicRecomCnt.setText(jsonResponse.getString("m_recommend"));
                        tvBestMusicContent.setText(jsonResponse.getString("m_explain"));
                        tvBestMusicTime.setText(jsonResponse.getString("m_create_time"));
                    } else {
                        tvBestMusicTitle.setText("추천을 받은 게시물이 없습니다.");
                        tvBestMusicWriter.setText("");
                        tvBestMusicRecomCnt.setText("");
                        tvBestMusicContent.setText("");
                        tvBestMusicTime.setText("");
                        tvBestMusicRecom.setText("");
                        btnGoBestMusicBoard.setVisibility(View.INVISIBLE);
                    }

                    imageURL = jsonResponse.getString("i_url");
                    musicURL = jsonResponse.getString("m_url");

                    //베스트이미지 불러오기
                    if (!jsonResponse.getString("i_url").equals("null")) {
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
                                    Toast.makeText(getActivity(), "이미지 불러오기 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).start();
                    } else {
                        pgbBestPictureLoading.setVisibility(View.GONE);
                    }
                    if (!jsonResponse.getString("m_url").equals("null")) {
                        //베스트 음악 불러오기
                        strUrl = LoginActivity.ipAddress + ":800/uploads/" + jsonResponse.getString("m_url");
                        btnBestMusicPause.setVisibility(View.VISIBLE);
                        btnBestMusicPlay.setVisibility(View.VISIBLE);
                        btnBestMusicRewind.setVisibility(View.VISIBLE);
                        pgbBestMusicLoading.setVisibility(View.GONE);
                    } else {
                        pgbBestMusicLoading.setVisibility(View.GONE);
                    }

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

        //글 베스트 게시물 이동 버튼 클릭
        btnGoBestTextBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cIntent = new Intent(getActivity(), ShowPictureActivity.class);
                cIntent.putExtra("putter", "게시판");
                cIntent.putExtra("category", 0);
                cIntent.putExtra("writer", tvBestTextWriter.getText().toString());
                cIntent.putExtra("postid", tvTextWriteId.getText().toString());
                Log.d("board put test", tvBestTextWriter.getText().toString() + " || " + tvTextWriteId.getText().toString());
                startActivity(cIntent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.left_to_center_translate, R.anim.stop_translate);
            }
        });
        //그림 베스트 게시물 이동 버튼 클릭
        btnGoBestPictureBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cIntent = new Intent(getActivity(), ShowPictureActivity.class);
                cIntent.putExtra("putter", "게시판");
                cIntent.putExtra("category", 1);
                cIntent.putExtra("writer", tvBestPictureWriter.getText().toString());
                cIntent.putExtra("postid", tvPictureWriteId.getText().toString());
                Log.d("board put test", tvBestTextWriter.getText().toString() + " || " + tvTextWriteId.getText().toString());
                startActivity(cIntent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.left_to_center_translate, R.anim.stop_translate);
            }
        });
        //음악 베스트 게시물 이동 버튼 클릭
        btnGoBestMusicBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cIntent = new Intent(getActivity(), ShowPictureActivity.class);
                cIntent.putExtra("putter", "게시판");
                cIntent.putExtra("category", 2);
                cIntent.putExtra("writer", tvBestMusicWriter.getText().toString());
                cIntent.putExtra("postid", tvMusicWriteId.getText().toString());
                startActivity(cIntent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.left_to_center_translate, R.anim.stop_translate);
            }
        });

        //베스트 음악 play 버튼 클릭시 이벤트
        btnBestMusicPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicCont) {//음악 prepare 했을시
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                } else {//기본값 false
                    musicStart();//음악 prepare
                }
            }
        });

        btnBestMusicPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicCont) {
                    mediaPlayer.pause();
                }
            }
        });

        btnBestMusicRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicCont) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
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
            imgBestPicture.setImageBitmap(bitmap);
            pgbBestPictureLoading.setVisibility(View.GONE);
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
        if (mediaPlayer != null && mediaPlayer.isPlaying())
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

    @Override
    public void onStop() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
        super.onStop();
    }

    private void musicStart() {//음악 재생 메소드
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnBestMusicPlay.setEnabled(false);
                            btnBestMusicPause.setEnabled(false);
                            btnBestMusicRewind.setEnabled(false);
                        }
                    });
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(strUrl);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnBestMusicPlay.setEnabled(true);
                                    btnBestMusicPause.setEnabled(true);
                                    btnBestMusicRewind.setEnabled(true);
                                }
                            });
                            mediaPlayer.start();
                            musicCont = true;
                        }
                    });
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        }).start();
    }
}
