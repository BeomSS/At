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
    String userId,sentUser=null;
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

        //답장일 경우
        if(sentUser!=null){
            edtReceiverId.setText(sentUser);
        }

        //아이디 있는지 체크
        btnCheckReceiverId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //적힌 아이디가 있는지 확인하는 volley(회원가입 재활용)
                Response.Listener vListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", cont.getResources().getString(R.string.log_json_response) + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                //아이디가 없을 경우
                                sendPossible=false;
                                btnLetterDlgSend.setEnabled(false);
                                Toast.makeText(cont,cont.getResources().getString(R.string.str_not_found_id_message),Toast.LENGTH_SHORT).show();
                            } else {
                                //아이디가 있을경우
                                edtReceiverId.setEnabled(false);
                                btnCheckReceiverId.setText(cont.getResources().getString(R.string.str_edit));
                                sendPossible=true;
                                btnLetterDlgSend.setEnabled(true);
                                Toast.makeText(cont,cont.getResources().getString(R.string.str_overlap_id_message),Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("check log", e.toString());
                        }
                    }
                };

                //아이디 조회하기 전에 누르는 경우와 조회한 뒤에 누르는 경우
                if(!sendPossible) {
                    ValidateRequest validateRequest = new ValidateRequest(edtReceiverId.getText().toString(), vListener);
                    RequestQueue queue = Volley.newRequestQueue(cont);
                    queue.add(validateRequest);
                }else{
                    edtReceiverId.setEnabled(true);
                    sendPossible=false;
                    btnCheckReceiverId.setText(cont.getResources().getString(R.string.str_check_id));
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
                        Log.d("TAG", cont.getResources().getString(R.string.log_json_response) + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                //성공시
                                Toast.makeText(cont,cont.getResources().getString(R.string.str_send_success_message),Toast.LENGTH_LONG).show();
                                dismiss();
                            } else {
                                //실패시
                                Toast.makeText(cont,cont.getResources().getString(R.string.str_send_fail_message),Toast.LENGTH_SHORT).show();
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

    //글쓰기 눌렀을 때 생성자
    public LetterDialog(Context context, String id) {
        //다이얼로그 형태 설정
        super(context,android.R.style.Theme_Translucent_NoTitleBar);
        userId=id;
        cont=context;
    }
    //답장쓰기 눌렀을 때 생성자
    public LetterDialog(Context context, String id, String sentUser) {
        //다이얼로그 형태 설정
        super(context,android.R.style.Theme_Translucent_NoTitleBar);
        userId=id;
        cont=context;
        this.sentUser=sentUser;
    }
}
