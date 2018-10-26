package com.example.user.at.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.user.at.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class SendMessageRequest extends StringRequest {
    final static private String URL = LoginActivity.ipAddress + ":800/At/SendMessage.php";
    private Map<String, String> parameter;

    public SendMessageRequest(String sendUserId, String receiveUserId, String title, String content, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameter = new HashMap<>();
        parameter.put("sendId", sendUserId);
        parameter.put("receiveId", receiveUserId);
        parameter.put("content", content);
        parameter.put("title", title);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameter;
    }
}
