package com.example.user.at;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.at.request.RegisterRequest;
import com.example.user.at.request.ValidateRequest;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class SignUpActivity extends Activity {
    Skin skin;
    int color;
    ArrayAdapter adapter;
    Spinner spinner;
    String userID;
    String userPassword;
    String userFavorite;
    AlertDialog dialog;
    boolean validate = false;
    EditText idSignupEdt;
    EditText psSignupEdt;
    EditText psConfirmEdt;
    Button validateBtn, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.activity_signup);
        class CustomFilter implements InputFilter {
            final int ENGLISH_NUMBER = 100;
            int value = 0;

            public CustomFilter(int value) {
                this.value = value;
            }

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (value == ENGLISH_NUMBER) {
                    Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
                    if (!ps.matcher(source).matches()) {
                        return "";
                    }
                }
                return null;
            }
        }

        InputFilter[] E_Filter = {new CustomFilter(100), new InputFilter.LengthFilter(15)}; // 필터


        spinner = findViewById(R.id.favSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.favorite, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        idSignupEdt = findViewById(R.id.idSignup);
        idSignupEdt.setFilters(E_Filter); // 필터적용
        psSignupEdt = findViewById(R.id.psSignup);
        psSignupEdt.setFilters(E_Filter);
        psConfirmEdt = findViewById(R.id.psConfirm);
        psConfirmEdt.setFilters(E_Filter);
        validateBtn = (Button) findViewById(R.id.validateButton);

        validateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                userID = idSignupEdt.getText().toString();

                if (userID.equals("")) {//빈칸일시
                    Toast.makeText(SignUpActivity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    validate = false;
                    idSignupEdt.setFocusable(true);

                } else {//빈칸이 아닐시

                    Response.Listener vListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "JSONObj response=" + response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                    dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    idSignupEdt.setEnabled(false);
                                    validate = true;
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                    dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                            } catch (Exception e) {
                                Log.d("validTest", e.toString());
                            }
                        }
                    };

                    ValidateRequest validateRequest = new ValidateRequest(userID, vListener);
                    RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                    queue.add(validateRequest);
                }
                if (validate) {
                    return;
                }

            }
        });

        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID = idSignupEdt.getText().toString();
                userPassword = psSignupEdt.getText().toString();
                userFavorite = spinner.getSelectedItem().toString();

                if (!validate)

                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    dialog = builder.setMessage("중복체크를 해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                if (userID.equals("") || userPassword.equals("") || userFavorite.equals(""))

                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    dialog = builder.setMessage("빈칸없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "JSONObj response=" + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                dialog = builder.setMessage("회원등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                finish();
                                overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                dialog = builder.setMessage("회원등록에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userFavorite, rListener);
                RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                queue.add(registerRequest);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stop_translate, R.anim.center_to_right_translate);
    }
}
