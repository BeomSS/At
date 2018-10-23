package com.example.user.at.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.user.at.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class FeedbackLikingRequest extends StringRequest {
    final static private String URL = LoginActivity.ipAddress + ":800/At/FeedbackLiking.php";
    private Map<String, String> parameter;

    public FeedbackLikingRequest(String postId, String userId, String feedbackId, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameter = new HashMap<>();
        parameter.put("postId", postId);
        parameter.put("userId", userId);
        parameter.put("feedbackId", feedbackId);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameter;
    }
}
