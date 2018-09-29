package com.example.user.at;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.user.at.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class MyFeedbackRequest extends StringRequest {
    final static private String URL = MainActivity.ipAddress + ":800/At/SeeMyFeedback.php";
    private Map<String, String> parameter;

    public MyFeedbackRequest(String toid, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameter = new HashMap<>();
        parameter.put("id", toid);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameter;
    }
}
