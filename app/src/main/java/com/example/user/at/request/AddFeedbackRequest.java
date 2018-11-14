package com.example.user.at.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.user.at.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class AddFeedbackRequest extends StringRequest {
    final static private String URL = LoginActivity.ipAddress + ":800/At/AddFeedback.php";
    private Map<String, String> parameter;

    public AddFeedbackRequest(String postID, String userID, String content,String post_writer,String post_title, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameter = new HashMap<>();
        parameter.put("postID", postID);
        parameter.put("userID", userID);
        parameter.put("content", content);
        parameter.put("post_writer", post_writer);
        parameter.put("post_title", post_title);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameter;
    }
}
