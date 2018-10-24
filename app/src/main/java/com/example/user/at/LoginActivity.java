package com.example.user.at;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.at.request.LoginRequest;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class LoginActivity extends Activity {
    Skin skin;
    int color;
    public static String ipAddress;
    private AlertDialog dialog;
    Button loginButton;
    String userID, userPassword;
    Intent lintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ipAddress = getString(R.string.ip_address);
        super.onCreate(savedInstanceState);
        skin = new Skin(this);
        color = skin.skinSetting();
        setContentView(R.layout.activity_login);

        //권한 묻기
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }

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

        final EditText idInput = findViewById(R.id.idInput);
        idInput.setFilters(E_Filter);
        final EditText psInput = findViewById(R.id.passwordInput);
        psInput.setFilters(E_Filter); // 필터적용

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID = idInput.getText().toString();
                userPassword = psInput.getText().toString();

                Response.Listener<String> responseLister = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                //쉐어드프리퍼런스에 아이디 저장
                                Skin pId = new Skin(LoginActivity.this);
                                pId.setPreference("LoginId", userID);

                                Toast.makeText(LoginActivity.this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                lintent = new Intent(LoginActivity.this, MainActivity.class);
                                lintent.putExtra("userID", userID);
                                startActivity(lintent);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("계정을 다시 확인하세요")
                                        .setNegativeButton("다시시도", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseLister);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        Button signupButton = (Button) findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(signupIntent);
            }
        });

        psInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                loginButton.callOnClick();
                return false;
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
}
