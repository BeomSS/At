package com.example.user.at;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.at.request.SendMessageRequest;
import com.example.user.at.request.ValidateRequest;

import org.json.JSONObject;

public class LetterDialog extends Dialog{
    EditText edtReceiverId;
    Button btnCheckReceiverId,btnLetterDlgSend,btnLetterDlgCancel;
    EditText edtMessageTitle,edtMessageContent;
    boolean sendPossible=false;
    String userId;
    private Context cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_letter_dialog);

        edtReceiverId = findViewById(R.id.edtReceiverId);
        btnCheckReceiverId = findViewById(R.id.btnCheckReceiverId);
        edtMessageTitle=findViewById(R.id.edtMessageTitle);
        edtMessageContent = findViewById(R.id.edtMessageContent);
        btnLetterDlgSend=findViewById(R.id.btnLetterDlgSend);
        btnLetterDlgCancel=findViewById(R.id.btnLetterDlgCancel);

        //아이디 있는지 체크
        btnCheckReceiverId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //적힌 아이디가 있는지 확인하는 volley(회원가입 재활용)
                Response.Listener vListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "JSONObj response=" + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                //아이디가 없을 경우
                                sendPossible=false;
                                btnLetterDlgSend.setEnabled(false);
                                Toast.makeText(cont,"해당하는 아이디는 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                            } else {
                                //아이디가 있을경우
                                edtReceiverId.setEnabled(false);
                                btnCheckReceiverId.setText("수정");
                                sendPossible=true;
                                btnLetterDlgSend.setEnabled(true);
                                Toast.makeText(cont,"존재하는 아이디 입니다.",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("check log", e.toString());
                        }
                    }
                };

                //아이디 조회하기 전에 누르는 경우와 조회한 뒤에 누르는 경우
                if(sendPossible==false) {
                    ValidateRequest validateRequest = new ValidateRequest(edtReceiverId.getText().toString(), vListener);
                    RequestQueue queue = Volley.newRequestQueue(cont);
                    queue.add(validateRequest);
                }else{
                    edtReceiverId.setEnabled(true);
                    sendPossible=false;
                    btnCheckReceiverId.setText("조회");
                    btnLetterDlgSend.setEnabled(false);
                }

                //버튼 클릭시 키보드 내려가게
                try {
                    InputMethodManager imm = (InputMethodManager)cont.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //쪽지 보내기 버튼 클릭
        btnLetterDlgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //쪽지 보내기
                Response.Listener sListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "JSONObj response=" + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                //성공시
                                Toast.makeText(cont,"쪽지가 전송되었습니다.",Toast.LENGTH_LONG).show();
                                dismiss();
                            } else {
                                //실패시
                                Toast.makeText(cont,"쪽지 전송에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("send log", e.toString());
                        }
                    }
                };
                SendMessageRequest validateRequest = new SendMessageRequest(userId,edtReceiverId.getText().toString(),
                        edtMessageTitle.getText().toString(),edtMessageContent.getText().toString(), sListener);
                RequestQueue queue = Volley.newRequestQueue(cont);
                queue.add(validateRequest);

            }
        });

        //취소버튼 클릭
        btnLetterDlgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


    public LetterDialog(Context context, String id) {
        //다이얼로그 형태 설정
        super(context,android.R.style.Theme_Translucent_NoTitleBar);
        userId=id;
        cont=context;
    }
}
