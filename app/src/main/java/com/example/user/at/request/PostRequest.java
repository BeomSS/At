package com.example.user.at.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.user.at.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class PostRequest extends StringRequest {
    final static private String URL = MainActivity.ipAddress + ":800/At/PostView.php";
    private Map<String, String> parameter;

    public PostRequest(String postnum, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameter = new HashMap<>();
        parameter.put("PostNum",postnum);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameter;
    }
}
