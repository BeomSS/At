package com.example.user.at.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.user.at.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class PostMarkingRequest extends StringRequest {
    final static private String URL = LoginActivity.ipAddress + ":800/At/AttentionPost.php";
    private Map<String, String> parameter;

    public PostMarkingRequest(int type,String postID,String userID, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameter = new HashMap<>();
        parameter.put("type",String.valueOf(type));
        parameter.put("postID",postID);
        parameter.put("userID",userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameter;
    }
}
