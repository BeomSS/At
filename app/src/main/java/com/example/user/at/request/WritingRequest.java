package com.example.user.at.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WritingRequest extends StringRequest {
    final static private String URL="http://218.55.127.203:800/At/Writing.php";
    private Map<String,String> parameter;

    public WritingRequest(String userID, int category, String post_title, String explain, Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);
        parameter = new HashMap<>();
        parameter.put("userID",userID);
        parameter.put("category",String.valueOf(category));
        parameter.put("postTitle",post_title);
        parameter.put("explain",explain);
    }

    @Override
    protected Map<String,String> getParams() throws AuthFailureError{
        return parameter;
    }
}
