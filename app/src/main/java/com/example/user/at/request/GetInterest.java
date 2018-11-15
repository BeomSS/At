package com.example.user.at.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.user.at.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class GetInterest extends StringRequest {
    final static private String URL = LoginActivity.ipAddress + ":800/At/GetInterest.php";
    private Map<String, String> parameter;

    public GetInterest(String userId, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameter = new HashMap<>();
        parameter.put("userId",userId);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameter;
    }
}
