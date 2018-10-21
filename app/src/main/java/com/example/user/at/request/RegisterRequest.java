package com.example.user.at.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.user.at.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL =  LoginActivity.ipAddress + ":800/At/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userFavorite, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        if(userFavorite.equals("글")){
            parameters.put("userFavorite", "0");
        }else if (userFavorite.equals("그림")){
            parameters.put("userFavorite", "1");
        }else if (userFavorite.equals("음악")){
            parameters.put("userFavorite", "2");
        }
        Log.d("registerTest",parameters.toString());
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
